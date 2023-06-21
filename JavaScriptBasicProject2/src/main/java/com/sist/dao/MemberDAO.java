package com.sist.dao;
import java.util.*;
import java.sql.*;

public class MemberDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//싱글턴: MemberDAO 클래스가 고정되도록
	private static MemberDAO dao;
	
	//1.드라이버 등록 ==> 시작과 동시에 한번만 수행! OR 멤버변수 초기화할때(생성자)
	public MemberDAO()
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
	public static MemberDAO newInstance()
	{
		if(dao==null)
			dao=new MemberDAO();
		return dao;
	}
	
	
	// 5. 우편번호 검색 기능
	public List<ZipcodeVO> postfind(String dong)
	{
		List<ZipcodeVO> list= new ArrayList<ZipcodeVO>();
		try
		{
			getConnection();
			String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ZipcodeVO vo=new ZipcodeVO();
				vo.setZipcode(rs.getString(1));
				vo.setSido(rs.getString(2));
				vo.setGugun(rs.getString(3));
				vo.setDong(rs.getString(4));
				vo.setBunji(rs.getString(5));
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
	
	// 검색 결과값이 있는지 확인용
	public int postfindCount(String dong)
	{
		int count=0;
		try
		{
			getConnection();
			String sql="SELECT count(*) "
					+ "FROM zipcode "
					+ "WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return count;
	}
}








