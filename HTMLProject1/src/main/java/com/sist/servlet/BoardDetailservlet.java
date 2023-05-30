package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;

@WebServlet("/BoardDetailservlet") //클래스명이 아닌 URL주소로 이동 (클래스명 변경하더라도 ㄱㅊ) URL주소 원하는대로 변경 가능
public class BoardDetailservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송방식
		response.setContentType("text/html;charset=UTF-8");
		// 클라이언트가 보낸 값을 받는다
		// => BoardDetailServlet?no=1 
		// a.jsp?page=1
		String no=request.getParameter("no");
		/*
		    ?no=1&page=10&name= (두개이상 데이터를 보내는 경우 &로 구분한다)
		    Ajax => {"no":1}
		    react/vue => params:{"no":1}
		 */
		// 오라클에서 값을 얻어온다
		BoardDAO dao=BoardDAO.newInstance();
		BoardVO vo=dao.boardDetailData(Integer.parseInt(no));
		
		// 브라우저가 읽을 수 있게 출력
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>내용보기</h1>");
		out.println("<table width=700 class=table_content>");
		
		out.println("<tr>");
		out.println("<th width=20%>번호</th>");
		out.println("<td width=30% align=center>"+vo.getNo()+"</td>");
		out.println("<th width=20%>작성일</th>");
		out.println("<td width=30% align=center>"+vo.getDbday()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=20%>이름</th>");
		out.println("<td width=30% align=center>"+vo.getName()+"</td>");
		out.println("<th width=20%>조회수</th>");
		out.println("<td width=30% align=center>"+vo.getHit()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=20%>제목</th>");
		out.println("<td colspan=3>"+vo.getSubject()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=4 height=200 align=left valign=top>");
		out.println("<pre style=\"white-space:pre-wrap\">"+vo.getContent()+"</pre>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan=4 align=right>");
		out.println("<a href=BoardEditServlet?no=>수정</a>&nbsp;");
		out.println("<a href=BoardDeleteServlet?no="+vo.getNo()+">삭제</a>&nbsp;");
		out.println("<a href=BoardListServlet>목록</a>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
