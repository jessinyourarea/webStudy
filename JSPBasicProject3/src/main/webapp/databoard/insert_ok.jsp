
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,com.sist.vo.*,java.io.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%
	// _ok.jsp : 기능처리 (member_ok -회원가입 처리 /update-ok 등등)
	// 데이터베이스 처리 => 후에 list.jsp로 이동시켜야 함.
	
	// 1. 한글 처리
	request.setCharacterEncoding("UTF-8");
	
	// 1-1. 파일 업로드 클래스 생성 (241page)
	String path="c:\\download";
	int size=1024*1024*100;
	String enctype="UTF-8";
	//DefaultFileRenamePolicy() 이름 재사용을 막는 
	MultipartRequest mr=new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());
	
	// 2. 요청 데이터 받아오기
	String name=mr.getParameter("name");
	String subject=mr.getParameter("subject");
	String content=mr.getParameter("content");
	String pwd=mr.getParameter("pwd");
	
	// 3. VO에 묶는다
	DataBoardVO vo=new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	String filename=mr.getOriginalFileName("upload");
	if(filename==null) // 업로드가 안된 상태
	{
		vo.setFilename("");
		vo.setFilesize(0);
	}
	else // 업로드가 된 상태
	{
		File file=new File(path+"\\"+filename);
		vo.setFilename(filename);
		vo.setFilesize((int)file.length());
	}
	
	// 4. DAO로 전송
	DataBoardDAO dao=DataBoardDAO.newInstance();
	dao.dataBoardInsert(vo);

	
	// 5. 화면 이동 to list.jsp
	response.sendRedirect("list.jsp");
%>