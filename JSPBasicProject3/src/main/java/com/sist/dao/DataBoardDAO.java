package com.sist.dao;

import java.util.*;

import com.sist.vo.DataBoardVO;

import java.sql.*;

//서버가 아니라, 클라이언트 프로그램을 짜는 것. (실제서버는 오라클)
public class DataBoardDAO {
	// 연결객체 => 이 안에는 socket이 심어져 있음
	private Connection conn;
	// 송수신 (SQL문장을 보내고 => 결과값(데이터값)을 받아옴)
	private PreparedStatement ps;
	// URL 주소
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	// Singleton
	private static DataBoardDAO dao;
	//static => 저장 공간이 한개
	// 드라이버 등록
	public DataBoardDAO()
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
	public static DataBoardDAO newInstance() {
		if(dao==null)
		{
			dao=new DataBoardDAO();
		}
		return dao;
	}
	
	// 기능
	// 1. 목록 => 페이징(인라인뷰)
	// 2. => 블록별로 => 1 2 3 4 5 => 다음 이전 페이지
	public List<DataBoardVO> dataBoardListData(int page)
	{
		List<DataBoardVO> list=new ArrayList<DataBoardVO>();
		try 
		{
			getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,rownum as num "
					+ "FROM (SELECT /*+ INDEX_DESC(jspDataBoard jd_no_pk) */no,subject,name,regdate,hit "
					+ "FROM jspDataBoard)) "
					+ "WHERE num BETWEEN ? AND ?";
			// Top-N : rownum은 중간값을 잘라올 수 없다
			ps=conn.prepareStatement(sql); 
			
			// ?에 값을 채운다
			int rowSize=10;
			// (rowSize*page)-(rowSize-1)  = Page*rowSize-rowSize+1 = (page-1)*rowsize+1 
			int start=(page-1)*rowSize+1;
			int end=rowSize*page;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				DataBoardVO vo=new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			   rs.close();
			   
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return list;
	}
	
	// 총페이지
	public int dataBoardRowCount()
	{
		int count=0;
		try
		{
			getConnection();
			String sql="SELECT count(*) FROM jspDataBoard";
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
			disConnetion();
		}
		return count;
	}
	
	// 데이터 추가 insert
	
	public void dataBoardInsert(DataBoardVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO jspDataBoard VALUES("
					+ "jd_no_seq.nextval,?,?,?,?,SYSDATE,0,?,?)";
			ps=conn.prepareStatement(sql);
			//?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setString(5, vo.getFilename());
			ps.setInt(6, vo.getFilesize());
			
			//실행 요청
			ResultSet rs=ps.executeQuery();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
	}
	
	// 내용보기
	public DataBoardVO dataBoardDetailData(int no)
	{
		DataBoardVO vo=new DataBoardVO();
		try
		{
			getConnection();
			// 조회수 증가
			String sql="UPDATE jspDataBoard SET "
					+ "hit=hit+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			//
			sql="SELECT no,name,subject,content,TO_CHAR(regdate,'YYYY-MM-DD'),hit,filename,filesize "
					+ "FROM jspDataBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			vo.setFilename(rs.getString(7));
			vo.setFilesize(rs.getInt(8));
			
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return vo;
	}
}



















