package com.sist.dao;
// 오라클만 연결 => SELECT , UPDATE , INSERT , DELETE
import java.util.*;
import java.sql.*;
public class BoardDAO {
	// 연결 객체
	private Connection conn;
	// 송수신 객체 (오라클에 SQL 문장 전송), 실행 결과값을 읽어온다
	private PreparedStatement ps;
	// 모든 사용자가 1개의 DAO만 사용하도록 한다 (싱글턴)
	private static BoardDAO dao;
	// 오라클 연결 주소 => 상수형 
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	//1. 드라이버 등록
	public BoardDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	//2. 싱글턴 => 없다면? new를 계속 생성해서 Heap에 계속 누적 => 오라클 연결 오버헤드
	// 메모리 누수 , Connection객체 생성 갯수를 제한
	// 한개의 메모리만 사용 가능하게 만든다
	// 서버프로그램 , 데이터베이스 프로그램에서 주로 사용
	// ***Spring은 모든 객체가 싱글턴이다
	public static BoardDAO newInstance()
	{
		if(dao==null)
			dao=new BoardDAO();
		return dao;
	}
	//3. 오라클 연결 프로그램
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex){}
	}
	//4. 오라클 해제 프로그램
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	////////////////////==> 1~4는 필수 과정이므로 이후 클래스화할 예정(라이브러리로)
	//5. 기능
	//5-1. 목록 출력 (페이지 나누기 - 인라인뷰 이용) => SELECT
	// 1page => 10개
	// BoardVO (게시물 1개)
	public List<BoardVO> boardListData(int page)
	{
		List<BoardVO> list=new ArrayList<BoardVO>();
		try
		{
			//1. 연결
			getConnection();
			//2. SQL문장 생성
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
					   + "FROM(SELECT no,subject,name,regdate,hit,rownum as num "
					   + "FROM (SELECT no,subject,name,regdate,hit "
					   + "FROM freeboard ORDER BY no DESC)) "
					   + "WHERE num BETWEEN ? AND ?";
			// rownum은 중간에서 데이터를 추출할 수 없다는 단점..! 
			//3. SQL문장 전송
			ps=conn.prepareStatement(sql);
			//4. 사용자가 요청한 데이터를 ?에 첨부
			//4-1. ?에 값을 채운다
			int rowSize=10;
			int start=(page-1)*rowSize;
			int end=page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			//5. 실행 요청 후 결과값을 받는다
			ResultSet rs=ps.executeQuery();
			//6. 받은 결과값을 list에 첨부
			while(rs.next())
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
			//
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
	//5-1-1. 총 페이지 구하기
	public int boardTotalPage()
	{
		int total=0;
		try
		{
			getConnection();//
			String sql="SELECT CEIL(count(*)/10.0) FROM freeboard"; 
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
	//5-2. 상세 보기 => 조회수 증가 =>  UPDATE, 상세 볼 게시물 읽기 => SELECT
	public BoardVO boardDetailData(int no)
	{
		BoardVO vo=new BoardVO();
		try
		{
			getConnection();
			String sql="UPDATE freeboard SET "
					   +"hit=hit+1 "
				 	   +"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-MM-dd'),hit "
					+ "FROM freeboard "
					+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
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
	
	
	//5-3. 게시물 등록 => INSERT
	// SQL문장 사용법 , HTML 태그==> 어떤 상황에 어떤 문장을 써야하는지 용도 파악 ==> 웹사이트 !!!!!!!!!!
	public void boardInsert(BoardVO vo)
	{
		try {
			  getConnection();
			  String sql="INSERT INTO freeboard(no,name,subject,content,pwd) "
			  		+"VALUES(fb_no_seq.nextval,?,?,?,?)";
			  ps=conn.prepareStatement(sql);
			  //실행 요청 전에 ?에 값을 채운다
			  ps.setString(1,vo.getName());
			  ps.setString(2,vo.getSubject());
			  ps.setString(3,vo.getContent());
			  ps.setString(4,vo.getPwd());
			  
			  //실행
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
	//5-4. 수정하기 => UPDATE *먼저 입력된 게시물 읽기 + 실제 수정(비밀번호 검색)*
	//5-4-1
	public BoardVO boardEditData(int no)
	{
		BoardVO vo=new BoardVO();
		try
		{
			getConnection();
			String sql="SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-MM-dd'),hit "
					+ "FROM freeboard "
					+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
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
	
	public boolean boardEdit(int no,String pwd)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
			String sql="SELECT pwd FROM freeboard "
					+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				sql="UPDATE name,subject,content "
						+ "FROM freeboard "
						+ "WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return bCheck;
	}
	
	//5-5. 삭제 =>DELETE *비밀번호 검색*
	public boolean boardDelete(int no,String pwd) 
	{	
		boolean bCheck=false; // 비밀번호 => 본인 여부 확인
		try
		{
			getConnection();
			String sql="SELECT pwd FROM freeboard "
					   +"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				//삭제
				sql="DELETE FROM freeboard "
						+ "WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
				
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		
		return bCheck;
	}
	
	//5-6. 찾기 *이름/제목/내용 으로 검색* => LIKE
	
	
}



















