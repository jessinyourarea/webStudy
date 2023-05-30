package com.sist.servlet;

import java.io.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

@WebServlet("/BoardEditServlet")
public class BoardEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String no=request.getParameter("no"); 

		// 오라클에서 값을 얻어온다 
		BoardDAO dao=BoardDAO.newInstance(); 
		BoardVO vo=dao.boardEditData(Integer.parseInt(no));
				
		// 브라우저가 읽을 수 있게 출력
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>수정하기</h1>");
		out.println("<form method=post action=BoardEditServlet>");
		out.println("<table width=700 class=table_content>");
		
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required>"+vo.getName()+">");
		out.println("<input type=hidden name=no value="+vo.getNo()+"></td>");
		out.println("</tr>");
	
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required>"+vo.getSubject()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea>"+vo.getContent()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=수정하기>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
			
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
    	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String no=request.getParameter("no"); 
		String pwd=request.getParameter("pwd"); 

		BoardDAO dao=BoardDAO.newInstance(); 
		Boolean bCheck=dao.boardEdit(Integer.parseInt(no),pwd);
		
		
	}

}
