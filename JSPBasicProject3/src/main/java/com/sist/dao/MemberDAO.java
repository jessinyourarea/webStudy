package com.sist.dao;

import java.util.*;

import com.sist.vo.MemberVO;

import java.sql.*;

public class MemberDAO {
		// 연결객체 => 이 안에는 socket이 심어져 있음
		private Connection conn;
		// 송수신 (SQL문장을 보내고 => 결과값(데이터값)을 받아옴)
		private PreparedStatement ps;
		// URL 주소
		private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
		// Singleton
		private static MemberDAO dao;
		//static => 저장 공간이 한개
		// 드라이버 등록
		public MemberDAO()
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}catch(Exception ex){}
		}
		// 오라클 연결
		public void getConnection() {
			try
			{
				conn=DriverManager.getConnection(URL,"hr","happy");
						
			}catch(Exception ex) {}
		}
		
		// 오라클 해제
		public void disConnetion() {
			try {
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
			}
		}
		// 싱글턴 처리
		public static MemberDAO newInstance() {
			if(dao==null)
			{
				dao=new MemberDAO();
			}
			return dao;
		}
		
		// 로그인 처리
		public MemberVO isLogin(String id,String pwd)
		{
			MemberVO vo=new MemberVO();
			try
			{
				getConnection();
				String sql="SELECT count(*) FROM jspMember "
						+ "WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1,id);
				ResultSet rs=ps.executeQuery();
				rs.next();
				int count=rs.getInt(1);
				rs.close();
				
				if(count==0) // ID가 없는 상태
				{
					vo.setMsg("NOID");
				}
				else // ID가 있는 상태
				{
					sql="SELECT id,name,gender,pwd "
							+ "FROM jspMember "
							+ "WHERE id=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, id);
					rs=ps.executeQuery();
					rs.next();
					String db_id=rs.getString(1);
					String name=rs.getString(2);
					String gender=rs.getString(3);
					String db_pwd=rs.getString(4);
					rs.close();
					
					if(db_pwd.equals(pwd)) // 로그인 성공(비번 일치)
					{
						vo.setId(db_id);
						vo.setName(name);
						vo.setGender(gender);
						vo.setMsg("OK");
					}
					else // 비밀번호 불일치
					{
						vo.setMsg("NOPWD");						
					}
					
				}
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally
			{
				
			}
			return vo;
		}

		
}
