package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
public class ReplyBoardDAO {
	// 연결 객체
	private Connection conn;
	// SQL 송수신 객체
	private PreparedStatement ps;
	//싱글턴
	public static ReplyBoardDAO dao;
	public static ReplyBoardDAO newInstance()
	{
		if(dao==null)
			dao=new ReplyBoardDAO();
		return dao;
	}
	
	// 주소값 얻기
	public void getConnection()
	{
		try
		{
			Context init=new InitialContext(); //탐색기 열기
			Context c=(Context)init.lookup("java://comp/env"); // Cdrive열기
			DataSource ds=(DataSource)c.lookup("jdbc/oracle"); // jdbc/oracle 열기
			conn=ds.getConnection(); // 연결하기
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	// 반환하기
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	// 기능 수행
	// 1. 목록 출력
	public List<ReplyBoardBean> boardListData(int page)
	{
		List<ReplyBoardBean> list=new ArrayList<ReplyBoardBean>();
		try
		{
			getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+ "FROM replyBoard ORDER BY group_id DESC,group_step ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(page-1)*rowSize+1;
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ReplyBoardBean vo=new ReplyBoardBean();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
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
	
	// 1-1 총페이지
	public int boardRowCount()
	{
		int count=0;
		try
		{
			getConnection();
			String sql="SELECT COUNT(*) FROM replyBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return count;
	}
	
	// 2. 상세보기
	// 3. 추가 기능
	// 4. 수정 기능
	// 5. 삭제 기능
	// 6. 답변하기
	// 7. 검색
}




















