package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class FoodDAO {
	// 연결
	private Connection conn;
	// 송수신 (SQL문장을 보내고 => 결과값(데이터값)을 받아옴)
	private PreparedStatement ps;
	// Singleton
	private static FoodDAO dao;
	//static => 저장 공간이 한개

	//출력 갯수
	private final int ROW_SIZE=20;
	
	
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
	
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	// 동일 => 오라클 연결 후 SQL문장 실행
	public List<FoodBean> foodListData(int page)
	{
		List<FoodBean> list= new ArrayList<FoodBean>();
		try
		{
			// Connection의 주소를 얻어온다
			getConnection();
			
			// SQL문장 전송
			String sql="SELECT fno, poster,name,num "
					+ "FROM (SELECT fno,poster,name,rownum as num "
					+ "FROM (SELECT /*+ INDEX_ASC(food_location) fl_fno_pk */fno,poster,name "
					+ "FROM food_location)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=ROW_SIZE;
			int start=(page-1)*rowSize+1; //rownum은 1번부터 시작
			int end=page*rowSize;
			
			// ?에 값을 채운다
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			//실행 요청
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodBean vo=new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(3));
				String poster=rs.getString(2);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				
				// list에 20개를 채워라
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			// 에러 처리
			ex.printStackTrace();
		}
		finally
		{
			disConnection(); // 반환
		}
		return list;
	}
	
	
	//
	public int foodTotalPage()
	{
		int totalpage=0;
		try
		{
			// 주소값을 얻어온다
			getConnection();
			
			// SQL문장 전송
			String sql="SELECT CEIL(COUNT(*)/"+ROW_SIZE+") "
					+ "FROM food_location";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection(); // 사용하면 반드시 반환
		}
		return totalpage;
	}
	
	// 상세보기
	public FoodBean foodDetailData(int fno)
	{
		FoodBean vo=new FoodBean();
		try
		{
			getConnection();
			String sql="SELECT fno,poster,name,tel,score,time,parking,type,price,menu,address "
					+ "FROM food_location "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setPoster(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setTel(rs.getString(4));
			vo.setScore(rs.getDouble(5));
			vo.setTime(rs.getString(6));
			vo.setParking(rs.getString(8));
			vo.setType(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setAddress(rs.getString(11));
			rs.close();
			// BLOB, BFILE : rs.getInputStream() 으로 처리
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	
	
	
}
