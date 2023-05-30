package com.sist.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;
// BoardDetailServlet , BoardDeleteServlet
@WebServlet("/BoardDeleteServlet")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 화면 출력= HMTL => 비밀번호 입력(맞는지 확인)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식 => HTML
		response.setContentType("text/html;charset=UTF-8");
		
		//클라이언트가 전송한 값을 받는다
		String no=request.getParameter("no");
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>삭제하기</h1>");
		out.println("<form method=post action=BoardDeleteServlet>");
		out.println("<table width=300 class=table_content>");
		
		
		out.println("<tr>");
		out.println("<th width=30%>비밀번호</th>");
		out.println("<td width=70%><input type=password name=pwd size=15 required>");
		out.println("<input type=hidden name=no value="+no+">");
		// 사용자에게 보여주면 안되는 데이터 => 화면 출력없이 데이터 전송할 수 있게 만드는 히든!!!
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=삭제>");
		out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	
	}
	// 요청에 대한 처리 담당(삭제 처리)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// 사용자가 전송한 값 받기 (int no, String pwd)
		String no=request.getParameter("no");
		String pwd=request.getParameter("pwd");
		
		PrintWriter out=response.getWriter();
		//DB 연동
		
		//오라클로 DELETE 요청
		BoardDAO dao=BoardDAO.newInstance();
		boolean bCheck=dao.boardDelete(Integer.parseInt(no), pwd);
		if(bCheck==true)
		{
			// 목록으로 이동
			response.sendRedirect("BoardListServlet?no="+no);
		}
		else
		{
			//삭제창으로 다시 이동
			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다!\");");
			out.println("history.back()");
			out.println("</script>");
		}
		
		//화면 이동
	
	}

}
