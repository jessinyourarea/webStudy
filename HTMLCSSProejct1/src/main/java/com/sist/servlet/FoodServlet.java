package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
/*
                                              SQL 문장 전송
 		브라우저  <===================> 자바  <======================> 오라클
 		            HTML로 변환         |       실행된 데이터 읽기
 		                        Servlet이나 JSP를 이용해야함.(Web파일)
 		                        -------    --- 자바+HTML
 		                        자바로만 사용      
 		                      Server + let
 		                      서버에서 실행되는 가벼운 프로그램 형태
 		                      => 수정 시마다 컴파일을 해야함(번거로운)
 		                      => ---------------------> 보완 버전이 JSP 
 		                      Servlet과 JSP는 톰캣에 의해 호출됨..through URL
 		          => new FoodServlet() => 톰캣 호출 
 		             1) 가장 먼저 init됨 (변수 초기화, 필요한 값을 읽는 경우)
 		                               예) 자동 로그인  , 보안 ...
 		             -----------------------------------------------
 		             2) service() : 사용자가 요청한 HTML을 만드는 위치
 		                   = doGET() 
 		                   = doPOST()
 		                   = 브라우저로 HTML을 전송하는 위치
 		             ----------------------------------------------- JSP(메소드 영역)
 		             3) destroy() : 메모리 해제
 		                => 자동 해제되는 시점은? 페이지 이동/ 새로고침/ 브라우저 종료
 		     JSP는 호출 시에 자동으로 Servlet으로 변경되어, 한 개의 클래스를 만든다.
 		     스프링 라이브러리가 서블릿으로 제작되어 있어...
 */

@WebServlet("/FoodServlet")
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HTML => 브라우저 전송
		response.setContentType("text/html;charset=UTF-8");
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodAllData();
		PrintWriter out=response.getWriter();
		// 브라우저에 출력하는 내용물 =>여기 안에 HTML을 넣어서 보내줘야한다 (그래서 out.println() 하는거임)
		out.println("<html>");
		out.println("<head>");
		out.println("<script src=\"http://code.jquery.com/jquery.js\"></script>");
		
		out.println("<script>");
		out.println("$(function(){");
		out.println("$('#keyword').keyup(function(){");

		out.println("let k=$(this).val();");
		out.println("console.log(k)");
		out.println("$('#user-table > tbody > tr').hide()");
		
		out.println("let temp=$('#user-table > tbody > tr > td:nth-child(5n+2):contains(\"'+k+'\")');");  //5n(col갯수)+2(2번째열 위치)

		out.println("$(temp).parent().show();");
		out.println("})");
		out.println("})");
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>맛집 목록</h1>");

		out.println("<table border=1 bordercolor=black width=1024px>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<input type=text size=30 id=keyword>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("<table border=1 bordercolor=black width=1024px id=user-table>");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th></th>");
		out.println("<th>맛집명</th>");
		out.println("<th>주소</th>");
		out.println("<th>전화</th>");
		out.println("<th>음식 종류</th>");
		out.println("</tr>");
		out.println("</thead>");

		out.println("<tbody>"); // Vue, React는 이게 없으면 table출력이 안됨. (thead먼저 tbody다음)
		
		for(FoodVO vo:list)
		{
			out.println("<tr>");
			out.println("<td><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td>"+vo.getName()+"</td>");
			out.println("<td>"+vo.getAddress()+"</td>");
			out.println("<td>"+vo.getTel()+"</td>");
			out.println("<td>"+vo.getType()+"</td>");
			out.println("</tr>");
		}
		
		out.println("</tbody>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
