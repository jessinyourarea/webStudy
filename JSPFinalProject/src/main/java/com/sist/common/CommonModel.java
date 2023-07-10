package com.sist.common;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
public class CommonModel {
	public static void commonRequestData(HttpServletRequest request)
	{
		// footer
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> fList=dao.foodTop7();
		
		// => 공지사항 => 최신뉴스
		// => 방문 맛집 
		
		request.setAttribute("fList", fList);
		
	}
}
