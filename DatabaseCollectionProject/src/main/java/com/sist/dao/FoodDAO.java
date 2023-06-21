package com.sist.dao;
import java.util.*;
import java.sql.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static FoodDAO dao;
	
	public FoodDAO(){
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}		
	}
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}	
	}
	
	// 싱글턴 만들기
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	//category 추가
	public void foodCategoryInsert(CategoryVO vo)
	{
		
		try
		{
			getConnection();
			String sql="INSERT INTO foodCategory VALUES("
					+ "pc_cno_seq.nextval,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			
			ps.executeUpdate();
			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	
	
	public List<CategoryVO> food_category_data()
	{
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try
		{
			getConnection();
			String sql="SELECT /*+ INDEX_ASC(food_category pc_cno_pk)*/ cno,title,subject,poster,link "
					+ "FROM food_category";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String p=rs.getString(4);
				p=p.replace("#","&");
				vo.setPoster(p);
						
				vo.setLink(rs.getString(5));
				list.add(vo);
			}
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
	
	
	
	
}









