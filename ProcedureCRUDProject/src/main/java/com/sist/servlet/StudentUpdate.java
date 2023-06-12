package com.sist.servlet;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.StudentDAO;
import com.sist.dao.StudentVO;


@WebServlet("/StudentUpdate")
public class StudentUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		//사용자가 보내준 값 받기
		String hakbun=request.getParameter("hakbun");
		StudentDAO dao=StudentDAO.newInstance();
		StudentVO vo=dao.studentDetail(Integer.parseInt(hakbun));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{margin:0px auto;width:350px;}");
		out.println("h1{text-align:center}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div class=container>");

		out.println("<h1>학생 수정</h1>");
		out.println("<div class=row>");
		out.println("<form method=post action=StudentUpdate>");
		// post=> doPost
		// 나머지는 doGet
		/*
			<form method=get> doGet()
			<form method=aaa> doGet()
			<form> doGet() 오류가 나도 GET방식
			if(method.equals("post")||method.equals("POST"))
			 	doPost()
			else 
			    doGet()
			 (해당 서블릿을 실행하면 post가 null이기 때문에 else의 doGet이 가장 먼저 구현됨. 그 후로 form을 채우면 doPost()진행됨)
			
		 */
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=35%>이름</td>");
		out.println("<td width=65%>");
		out.println("<input type=hidden name=hakbun value="+hakbun+">");
		// 출력 없이 데이터만 전송할 목적>번호와 페이지
		out.println("<input type=text name=name size=20 class=input-sm value="+vo.getName()+">");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=35%>국어</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=kor size=20 class=input-sm max=100 min=0 step=5 value="+vo.getKor()+">");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=35%>영어</td>");
		out.println("<td width=65%>");
		
		out.println("<input type=number name=eng size=20 class=input-sm max=100 min=0 step=5 value="+vo.getEng()+">");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=35%>수학</td>");
		out.println("<td width=65%>");
		out.println("<input type=number name=math size=20 class=input-sm max=100 min=0 step=5 value="+vo.getMath()+">");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit value=수정 class=\"btn btn-sm btn-warning\">");
		out.println("<input type=button value=취소 class=\"btn btn-sm btn-warning\" onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("</table>");
		out.println("</form>"); // 호출될때? submit버튼을 클릭했을때
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

		
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			// 한글이 있을때는 디코딩~
			// 사용자가 보내준 값을 받는다 
			// <input type=number name=math => name속성을 설정하지 않으면 null값임.
			// 이거슨, servlet에서만 적용되는 것이 아니라 jsp/spring포함
			
			String name=request.getParameter("name");
			String kor=request.getParameter("kor");
			String eng=request.getParameter("eng");
			String math=request.getParameter("math");
			String hakbun=request.getParameter("hakbun");
			// public String insert(StudentVO vo) => 스프링은 이거 한줄로 끝~!
			StudentVO vo=new StudentVO();
			vo.setName(name);
			vo.setKor(Integer.parseInt(kor));
			vo.setEng(Integer.parseInt(eng));
			vo.setMath(Integer.parseInt(math));
			vo.setHakbun(Integer.parseInt(hakbun));

			//추가
			StudentDAO dao=StudentDAO.newInstance();
			dao.studentUpdate(vo);
			
			//이동
			response.sendRedirect("StudentList");
			
		
	}

}
