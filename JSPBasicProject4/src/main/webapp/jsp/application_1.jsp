<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
		application: ServletContext
		=> 서버 관리 (서버에 대한 정보, 로그에 정보 전달, 자원 관리)
		   = 서버 버전: servlet에 대한 버전
		     1) 서버이름: getServerInfo() : TomCat
		     2) getMajorVersion():
		     3) getMinorVersion():
		        17.01  , 3.5 ...
		      ---  ---
		     major minor
		     4) 로그 정보: log
		     5) 자원 관리: 파일이나 이미지파일 등으로 묶음
		                getRealPath(); 
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  서버이름: <%=application.getServerInfo() %><br>
  버전: <%=application.getMajorVersion() %><br>
  부버전: <%=application.getMinorVersion() %><br>
  <%-- MIME: <%=application.getMimeType("application_1.jsp") %><br> --%>
</body>
</html>










