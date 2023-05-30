package com.sist.dao;
import java.util.*;
import java.sql.*;

public class FoodDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//싱글턴: FoodDAO 클래스가 고정되도록
	private static FoodDAO dao;
	
	//1.드라이버 등록 ==> 시작과 동시에 한번만 수행! OR 멤버변수 초기화할때(생성자)
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException =>CheckedException
			// java.io, java.net, java.sql =>CheckedException
		}catch(Exception ex){}
	}

	//2. 오라클 연결
	private void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// conn hr/happy 해당 명령어를 오라클에 전송한다
		}catch(Exception ex) {}
	}
	
	//3. 오라클 해제
	private void disConnection()
	{
		try
		{
			if(ps!=null) ps.close(); //통신이 열려있으면 닫는다
			if(conn!=null) conn.close();
			// == exit (오라클 닫는다)
		}catch(Exception ex) {}
	}
	
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	public List<FoodVO> foodAllData()
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try {
			getConnection();
			String sql="SELECT name,address,poster,phone,type "
					+ "FROM food_house "
					+ "ORDER BY fno ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setName(rs.getString(1));
				String addr=rs.getString(2);
				addr=addr.substring(0,addr.lastIndexOf("지번"));
				vo.setAddress(addr.trim());
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				// 데이터베이스에 값을 넣을때는 1개만 => 지금처럼 불필요하게 인덱스오브를 사용하며 구분자를 나눌 필요없이!
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setTel(rs.getString(4));
				vo.setType(rs.getString(5));
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
	
}
