package com.sist.dao;
import java.util.*;
import java.sql.*;

public class GoodsDAO2 {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public GoodsDAO2()
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e){}
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
	
	public List<Integer> goodsGetNoData(String tab)
	{
		List<Integer> list=new ArrayList<Integer>();
		try
		{
			getConnection();
			String sql="SELECT no FROM "+tab
					+ " ORDER BY no ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				int no=rs.getInt(1);
				list.add(no);
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
	public void goodsAccoutnInsert(int account,int no,String tab)
	{
		try
		{
			getConnection();
			String sql="UPDATE "+tab
					+ " SET"
					+ " account=?"
					+ " WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, account);
			ps.setInt(2, no);
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
	public static void main(String[] args) {
		GoodsDAO2 dao=new GoodsDAO2();
		List<Integer> list=dao.goodsGetNoData("goods_new");
		for(int no:list)
		{
			int account=(int)(Math.random()*50)+11;
			dao.goodsAccoutnInsert(account, no, "goods_new");
		}
		System.out.println("끝~~");
	}
	
	
}












