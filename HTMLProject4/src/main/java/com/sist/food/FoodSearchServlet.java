package com.sist.food;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

/*
  @GetMapping => GET
  @PostMapping => POST
  @RequestMapping => Get/Post both
  -------------------------------error시 400
 */

@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html:charset=UTF-8");
		// 사용자 요청값을 받는다
		String addr=request.getParameter("addr");
		if(addr==null)
			addr="마포";
		
		String strPage=request.getParameter("page");
		if(strPage==null)
			strPage="1";
		
		int curpage=Integer.parseInt(strPage);
	
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list=dao.foodFindData(addr, curpage);
		int totalpage=(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);
		final int BLOCK=5;
		
		
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		/*
		 <ul class="pagination">
		  <li><a href="#">1</a></li>
		  <li class="active"><a href="#">2</a></li>
		  <li><a href="#">3</a></li>
		  <li><a href="#">4</a></li>
		  <li><a href="#">5</a></li>
		 </ul>
		 */
		// 화면만 출력
		PrintWriter out=response.getWriter();
		
	}

}
