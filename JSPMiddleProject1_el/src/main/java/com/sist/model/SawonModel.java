package com.sist.model;

import javax.servlet.http.HttpServletRequest;

public class SawonModel {
	public void sawonInfo(HttpServletRequest request)
	{
		SawonVO vo=new SawonVO();
		vo.setSabun(1);
		vo.setName("홍길동");
		vo.setDept("개발부");
		vo.setJob("대리");
		vo.setPay(3500);
		
		request.setAttribute("vo",vo);
		request.setAttribute("vo", vo);
	}
}
