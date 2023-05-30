package com.sist.dao;
// => 카테고리 출력 => 카테고리별 맛집 => 맛집에 대한 상세보기 (+지도출력)

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
	
	//4. 싱글턴 설정 ==> static은 메모리공간이 1개만 가지고 있다
	// 메모리 누수현상
	// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
	// 싱글턴은 데이터베이스에서는 필수 조건
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	//5. 기능
	//5-1 카테고리 출력
	public List<CategoryVO> food_category_list()
	{
		// 카테고리 1개의 정보(번호, 이미지, 제목, 제목)==CategoryVO
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try
		{
			getConnection();
			// ODER BY cno ASC 대신=> index이용 /*INDEX_ASC(food_category fc_cno_pk)*/
			// --+INDEX_ASC(food_category fc_cno_pk)
			String sql="SELECT cno,title,subject,poster "
					+ "FROM food_category "
					+ "ORDER BY cno ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				CategoryVO vo=new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setPoster(rs.getString(4));
				list.add(vo);
			}
			rs.close();
			// list=> 받아서 브라우저로 전송 실행
			//             -------------->할 수 있는 ? Servlet,JSP
										// Spring에서도 전송하려면 Servlet이 필요함.
					
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
	//5-1-1 카테고리 정도
	public CategoryVO food_category_info(int cno)
	{
		CategoryVO vo=new CategoryVO();
		
		try
		{
			getConnection();
			String sql="SELECT title,subject FROM food_category "
						+"WHERE cno="+cno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setSubject(rs.getString(2));
			rs.close();
						
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
	
	
	//5-2 카테고리별 맛집 출력
	 public List<FoodVO> food_category_data(int cno)
	 {
		 List<FoodVO> list=new ArrayList<FoodVO>();
		 try
		 {
			 getConnection();
			 String sql="SELECT fno,name,poster,address,phone,type,score "
			 		+"FROM food_house "
					+"WHERE cno="+cno;
			 ps=conn.prepareStatement(sql);
			 ResultSet rs=ps.executeQuery();
			 while(rs.next())
			 {
				 FoodVO vo=new FoodVO();
				 vo.setFno(rs.getInt(1));
				 vo.setName(rs.getString(2));
				 
				 String poster=rs.getString(3);
				 poster=poster.substring(0,poster.indexOf("^"));
				 poster=poster.replace('#','&');
				 vo.setPoster(poster);
				 
				 String address=rs.getString(4);
				 address=address.substring(0,address.lastIndexOf("지"));
				 
				 vo.setAddress(address.trim());
				 vo.setPhone(rs.getString(5));
				 vo.setType(rs.getString(6));
				 vo.setScore(rs.getDouble(7));
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
	
	//5-3 맛집 상세보기
	public FoodVO foodDetailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try
		{
			getConnection();
			String sql="SELECT fno,cno,name,poster,phone,type,address,time,parking,menu,price,score "
					+ "FROM food_house "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setCno(rs.getInt(2));
			vo.setName(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setPhone(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setAddress(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setParking(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setPrice(rs.getString(11));
			vo.setScore(rs.getDouble(12));
			
			
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
	 
	 
	//5-4 맛집 검색
	public List<FoodVO> foodFindData(String addr,int page)
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try
		{
			getConnection();
			/*String sql="SELECT fno,name,score,type,poster "
					+ "FROM food_house "
					+ "WHERE address LIKE '%'||?||'%'"; */
			// => mySQL, mariaDB : LIKE CONCAT('%',?,'%')
			String sql="SELECT fno,name,poster,score,num "
					+ "FROM (SELECT fno,name,poster,score,rownum as num "
					+ "FROM (SELECT fno,name,poster,score "
					+ "FROM food_location "
					+ "WHERE address LIKE '%'||?||'%')) " //데이터가져오기 with 인라인뷰
					+ "WHERE num BETWEEN ? AND ?"; // 페이지 나누기까지
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page-1)*rowSize;
			int end=rowSize*page;
			ps.setString(1, addr);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				String poster=rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#","&");
				vo.setPoster(poster);
				vo.setScore(rs.getDouble(4));
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
	//5-4-1 총페이지 
	public int foodRowCount(String addr)
	{
		int count=0;
		try
		{
			getConnection();
			String sql="SELECT count(*) FROM food_location "
					+ "WHERE address LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, addr);
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
	
	//5-5. 댓글 (CURD 가능) + 로그인 기능
	 
	
	
	
}
