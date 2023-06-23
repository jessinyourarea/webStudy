package com.sist.dao;

import java.util.*;

import com.sist.vo.*;

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
	public DataBoardVO dataBoardDetailData(int no,int type)
	{
		DataBoardVO vo=new DataBoardVO();
		try
		{
			getConnection();
			if(type==0)
			{
				// 조회수 증가
				String sql="UPDATE jspDataBoard SET "
						+ "hit=hit+1 "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
			//
			String sql="SELECT no,name,subject,content,TO_CHAR(regdate,'YYYY-MM-DD'),hit,filename,filesize "
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
	
	// 인기 순위 10개 출력
	public List<DataBoardVO> dataBoardTop10()
	{
		List<DataBoardVO> list= new ArrayList<DataBoardVO>();
		try
		{
			getConnection();
			String sql="SELECT no,name,subject,rownum "
					+ "FROM (SELECT no,name,subject "
					+ "FROM jspDataBoard ORDER BY hit DESC) "
					+ "WHERE rownum<=10";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				DataBoardVO vo=new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return list;
	}

	// 게시물 삭제
	public DataBoardVO dataBoardFileInfo(int no)
	{
		DataBoardVO vo=new DataBoardVO();
		try
		{
			getConnection();
			String sql="SELECT filename,filesize FROM jspDataBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFilename(rs.getString(1));
			vo.setFilesize(rs.getInt(2));
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
	
	public boolean dataBoardDelete(int no,String pwd)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
			String sql="SELECT pwd FROM jspDataBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			if(db_pwd.equals(pwd))
			{
				bCheck=true;
				// 참조하는 해당 게시물의 댓글을 먼저 삭제
				sql="DELETE FROM jspReply "
						+ "WHERE bno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				
				// 댓글 삭제 후 게시물 삭제 실행
				sql="DELETE FROM jspDataBoard "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return bCheck;
	}
	
	// 수정하기
	public boolean dataBoardUpdate(DataBoardVO vo)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
			// 비밀번호 검색
			String sql="SELECT pwd FROM jspDataBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd()))
			{
				bCheck=true;
				//수정
				sql="UPDATE jspDataBoard SET "
						+ "name=?,subject=?,content=? "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				
				ps.executeUpdate();
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return bCheck;
	}
	
	
	// 댓글
	// 1. 댓글 추가
	public void replyInsert(ReplyVO vo) 
	{
		try
		{
			getConnection();
			String sql="INSERT INTO jspReply VALUES("
					+ "jr_no_seq.nextval,?,?,?,?,SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			
			ps.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
	}
	
	// 2. 댓글 읽기
	public List<ReplyVO> replyListData(int bno)
	{
		List<ReplyVO> list=new ArrayList<ReplyVO>();
		try
		{
			getConnection();
			String sql="SELECT /*+ INDEX_DESC(jspReply jr_no_pk) */no,bno,id,name,msg,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') "
					+ "FROM jspReply "
					+ "WHERE bno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ReplyVO vo=new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setName(rs.getString(4));
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
				list.add(vo);
			}
			rs.close();
			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		return list;
	}
	// 3. 댓글 수정 => JQuery
	public void replyUpdate(int no,String msg)
	{
		try
		{
			getConnection();
			String sql="UPDATE jspReply SET "
					+ "msg=? "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, no);
			ps.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
		
	}
	
	
	// 4. 댓글 삭제 
	public void replyDelete(int no)
	{
		try
		{
			getConnection();
			String sql="DELETE FROM jspReply "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnetion();
		}
	}
	
}



















