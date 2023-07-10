package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class GoodsDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private String[] tab= {"","goods_all","goods_best","goods_new","goods_special"};
	private static GoodsDAO dao;
	
	//싱글턴
	public static GoodsDAO newInstance()
	{
		if(dao==null)
			dao=new GoodsDAO();
		return dao;
	}
	
	// 목록 출력
	public List<GoodsVO> goodsListData(int page,int type)
	{
		List<GoodsVO> list=new ArrayList<GoodsVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,goods_poster,goods_price,goods_name,num "
					+ "FROM (SELECT no,goods_poster,goods_price,goods_name,rownum as num "
					+ "FROM (SELECT no,goods_poster,goods_price,goods_name "
					+ "FROM "+tab[type]+" ORDER BY no ASC)) "
				    + "WHERE num BETWEEN ? AND ?";
			
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page-1)*rowSize+1;
			int end=page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GoodsVO vo=new GoodsVO();
				vo.setNo(rs.getInt(1));
				vo.setGoods_poster(rs.getString(2));
				vo.setGoods_first_price(rs.getString(3));
				vo.setGoods_name(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	// 총페이지
	public int goodsTotalpage(int type)
	{
		int total=0;
		try
		{
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/12.0) FROM "+tab[type];
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
			db.disConnection(conn, ps);
		}
		return total;
	}
	
	// goods_ detail 
	// type에 해당되는 테이블에서 goods_no에 해당하는 상세정보를 가져온다~
	//private int no,goods_discount,hit,account;
	//private String goods_name,goods_sub,goods_price,goods_first_price,goods_delivery,goods_poster;
	
	public GoodsVO goodsDetailData(int no,int type)
	{
		GoodsVO vo=new GoodsVO();
		try
		{
			conn=db.getConnection();
			// 조회수 증가
			String sql="UPDATE "+tab[type]+" SET "
					+ "hit=hit+1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			// 상세보기
			sql="SELECT no,goods_poster,goods_name,goods_sub,goods_price,goods_first_price,goods_discount,account,goods_delivery "
					+ "FROM "+tab[type]+" WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setGoods_poster(rs.getString(2));
			vo.setGoods_name(rs.getString(3));
			vo.setGoods_sub(rs.getString(4));
			vo.setGoods_price(rs.getString(5));
			vo.setGoods_first_price(rs.getString(6));
			vo.setGoods_discount(rs.getInt(7));
			vo.setAccount(rs.getInt(8));
			vo.setGoods_delivery(rs.getString(9));
			rs.close();
			String temp=vo.getGoods_price();
			// temp= "19,900원" => 19900
			
			temp=temp.replaceAll("[^0-9]",""); // 0-9 : 모든 숫자, ^:제외하고
			vo.setPrice(Integer.parseInt(temp));
					
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
}




















