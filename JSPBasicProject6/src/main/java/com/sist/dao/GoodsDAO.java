package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GoodsDAO {
	// 연결
	private Connection conn;
	// 송수신 (SQL문장을 보내고 => 결과값(데이터값)을 받아옴)
	private PreparedStatement ps;
	// Singleton
	private static GoodsDAO dao;
	//static => 저장 공간이 한개

	//출력 갯수
	private final int ROW_SIZE=12;
	
	
	// Pool 영역에서  Connetion 객체를 얻어온다
	public void getConnection()
	{
		//Connection 객체 주소를 메모리에 저장
		// 저장 공간 => POOL 영역에 (디렉토리형식으로 저장) => JNDI 가상 형식 java naming directory interface
		// 루트 > 저장 공간
		// java://env/comp => C드라이버 => jdbc/oracle
		// 1. 탐색기
		try
		{
			// 1. 탐색기를 연다
			Context init=new InitialContext();
			
			// 2. C드라이버 연결
			Context cdriver=(Context)init.lookup("java://comp/env");
			// lookup => 문자열(key) => 객체 찾기(RMI)..?
			
			// 3. Connection 객체 찾기
			DataSource ds=(DataSource)cdriver.lookup("jdbc/oracle");
			
			//4. Connection 주소를 연결하기
			conn=ds.getConnection();
			
			// 282
			// 오라클 연결 시간을 줄인다
			// Connection 객체가 일정하게 생성 & 관리
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			
		}catch(Exception ex) {}
	}
	
	// 싱글턴
	public static GoodsDAO newInstance()
	{
		if(dao==null)
			dao=new GoodsDAO();
		return dao;
	}
	
	// 로그인
	public String isLogin(String id,String pwd)
	{
		String result="";
		try
		{
			getConnection();
			//1. id존재여부 확인
			String sql="SELECT COUNT(*) FROM jspMember "
					  +"WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			//2. 비밀번호 확인 
			if(count==0)
			{
				result="NOID";
			}
			else //ID가 존재 
			{
				sql="SELECT pwd,name "
						   +"FROM jspMember "
						   +"WHERE id=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				rs=ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				String name=rs.getString(2);
				rs.close();
				
				if(db_pwd.equals(pwd))//로그인 
				{
					result=name;
				}
				else
				{
					result="NOPWD";
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return result;
	}
	
	// 목록
	public List<GoodsBean> goodsListData(int page)
	{
		List<GoodsBean> list=new ArrayList<GoodsBean>();
		try
		{
			getConnection();
			String sql="SELECT no,goods_name,goods_poster,num "
					+ "FROM (SELECT no,goods_name,goods_poster,rownum as num "
					+ "FROM (SELECT /*+ INDEX_ASC() (goods_all ga_no_pk)*/no,goods_name,goods_poster "
					+ "FROM goods_all)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int start=(page-1)*ROW_SIZE+1;
			int end=page*ROW_SIZE;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GoodsBean vo=new GoodsBean();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	
	}
	
	
	public int goodsTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(Count(*)/"+ROW_SIZE 
					+") FROM goods_all";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
			

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;
	}
	
	// 상세보기
	public GoodsBean goodsDetailData(int no)
	{
		GoodsBean vo=new GoodsBean();
		try
		{
			// 주소값을 얻어온다
			getConnection();
			String sql="SELECT no,goods_name,goods_sub,goods_price,goods_discount,"
					+ "goods_first_price,goods_delivery,goods_poster "
					+ "FROM goods_all "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSub(rs.getString(3));
			vo.setPrice(rs.getString(4));
			vo.setDiscount(rs.getInt(5));
			vo.setFp(rs.getString(6));
			vo.setDelivery(rs.getString(7));
			vo.setPoster(rs.getString(8));
			
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection(); // 사용하면 반드시 반환
		}
		return vo;
	}
	// 장바구니 ==> session 이용
	
	// 구매(X)
	
}
