package com.sist.dao;
import java.sql.*;
import java.util.*;
import com.sist.vo.*;
/*
 	1. 드라이버 등록
 	   ----- 오라클 연결하는 라이브러리(ojdbc8.jar안에 있음)
 	   OracleDrvier => 메모리 할당> 동작
 	2. 오라클 연결
 	   Connection 
 	3. SQL문장을 전송
 	   PreparedStatement
 	4. SQL문장 실행 요청
 	   = executeUpdate() => INSERT UPDATE DELETE 수행
 	     -------------> AUTO-COMMIT (ROLLBACK 불가)
 	   = executeQuery() => SELECT문장 수행
 	     -------------> 검색 결과값을 가지고 온다
 	                    --------
 	                    ResultSet
 	     ResultSet
 	     String sql-"SELECT id,name,sex,age FROM ~~"
 	     -------------------------------------------------
 	      id        name           sex            age
 	     ---------------------------------------------------
 	      aaa       홍길동            남자           20   | first() => next()
 	   	                                                커서 위치 변경=> 위치 변경 후 데이터 읽기
 	   	getString(1) getString(2) getString(3) getInt(4)
 	   	getString("id") => Mybatis version                                                
 	     --------------------------------------------------
 	      bbb       심청이            여자            23
 	     ---------------------------------------------------
 	      ccc       박문수            남자            27  | last() => previous
 	     ---------------------------------------------------
 	     | 출력후 커서위치 
 	     
 	     
 	5. 닫기
 	   생성 반대로 닫는다 
 	   rs.close() , ps.close() conn.close()
 ---------------------------------------------------오라클 연결 과정 (Servlet=JSP)
 	   
 */
public class FoodDAO {
	// 기능 => INSERT => 데이터 수집(파일) 
	private Connection conn; // 오라클 연결 객체(데이터베이스 연결)
	private PreparedStatement ps; //SQL문장 전송/ 결과값 읽기
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// mySQL => jdbc:mysql://localhost/mydb
	private static FoodDAO dao; // 싱글턴 패턴 *메모리 다중 할당 방지*
	// DAO객체를 한개만 사용 가능하게 만든다
	// 드라이버 설치 => 소프트웨어 ( 메모리 할당 요청) new대신  Class.forName()
	// 클래스의 정보를 전송
	// 드라이버 설치는 1번만 수행. 
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	//오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
			// => 오라클 전송 : conn hr/happy
		}catch(Exception ex) {}
	}
	// 오라클 연결 종료
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
			// 오라클 전송 : exit 
		}catch(Exception ex) {}
	}
	//DAO객체를 1개만 생성해서 사용 => 메모리 누수현상 방지(싱글턴)
	// 싱글턴 / 팩토리 패턴 (스프링:8개 패턴; ) 
	public static FoodDAO newInstance()
	{
		//newInstance(), getInstance() => 싱글턴
		if(dao==null) 
			dao=new FoodDAO();
		return dao;
	}
	// ========================= 기본 셋팅 (모든 DAO )
	// 기능
	// 1. 데이터 수집 (INSERT)
	/* 
	 	Statement => SQL => 생성과 동시에 데이터 추가
	 	PreparedStatement => 미리 SQL문장을 만들어 놓고 나중에 값을 채운다
	 	CallableStatement => 함수(Procedure) 호출 시에 사용 
	 */
	public void foodCategoryInsert(CategoryVO vo)
	{
		try
		{
			// 연결
			getConnection();
			// SQL문장 제작
			String sql="INSERT INTO food_category VALUES("
					   +"fc_cno_seq.nextval,?,?,?,?)";
			
			// SQL문장 오라클로 전송 
			ps=conn.prepareStatement(sql);
			// ?? 에 값읉채운다
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			// 단점: 번호가 잘못되면 오류를 발생 , 데이터형 틀려도 오류 
			// IN,OUT~
			// SQL문장을 실행 명령 => SQL문장을 작성하고 => ENTER
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
	//1-1 => 실제 맛집 정보 저장
	/*
		fno NUMBER,
	cno NUMBER,
	name VARCHAR2(100) CONSTRAINT fh_name_nn NOT NULL,
	score NUMBER(2,1),
	address VARCHAR2(300) CONSTRAINT fh_addr_nn NOT NULL,
	phone VARCHAR2(20) CONSTRAINT fh_phone_nn NOT NULL,
	type VARCHAR2(30) CONSTRAINT fh_type_nn NOT NULL,
	price VARCHAR2(30),
	parking VARCHAR2(30),
	time VARCHAR2(20),
	menu CLOB,
	good NUMBER,
	soso NUMBER,
	bad NUMBER,
	poster VARCHAR2(4000) CONSTRAINT fh_poster_nn NOT NULL,
	 */
	public void foodDataInsert(FoodVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO food_house VALUES("
					   +"fh_fno_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//오라클로 전송
			ps=conn.prepareStatement(sql);
			// ?에 값 채우기
			ps.setInt(1,vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3,vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
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
	// 2. SELECT => 전체 데이터 읽기 => 30개(하나당 categoryVO)
	// 콜렉션으로 묶어서 브라우저로 30개를 전송
	// 브라우저 <==> 오라클 ... (직접 연결은 X)
	// 브라우저 <==>  자바  <==> 오라클
	// list, 메소드 제작 방법 
	public List<CategoryVO> foodCategoryData()
	{
		List<CategoryVO> list=new ArrayList<CategoryVO>();
		try
		{
			//1. 오라클 연결 확인
			getConnection();;
			//2.SQL 문장 
			String sql="SELECT cno,title,subject,poster,link "
					   +"FROM food_category";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				CategoryVO vo= new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster=rs.getString(4);
			    poster=poster.replace("#", "&");
			    vo.setPoster(poster);
			    vo.setLink("https://www.mangoplate.com"+rs.getString(5));
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
	
	// 3. 상세보기 => (WHERE)
}










