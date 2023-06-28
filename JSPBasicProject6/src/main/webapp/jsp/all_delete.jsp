<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		// Cookie 읽기
	Cookie[] cookies=request.getCookies();
	if(cookies!=null)
	{
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().startsWith("food_"))
			{
				cookies[i].setPath("/");
				// 삭제 == 저장기간을 0으로 바꿔준다
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
	}
	response.sendRedirect("main1.jsp");
	

%>