<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
	String msg="Hello JSP!!";
	public String display()
	{
		return "Hello JSP!!";
	}
	/*
		public class page_jsp extends HttpServlet
		{
			String msg="Hello JSP!!!";
			public String display()
			{
				return msg;
			}
			public void _jspService()
			{
				String msg=""
			    ------------------
			     this.msg
			    -------------------
			}
		}
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String msg="Hello JSP(지역변수)";
		//page = Object데이터형이라 사용하고프면 변환 (거의 안 쓸..)
		
	%>
	<%=this.msg %>
</body>
</html>