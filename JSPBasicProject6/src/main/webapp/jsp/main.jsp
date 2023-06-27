<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 
		301page: Session vs Cookie
		Session/Cookie의 차이점 (GET/POST) => MVC
		---------------------
		Session                                 Cookie
클래스명  (내장객체)                                 (필요시마다 생성)
        HttpSession                             Cookie
=> HttpSession session= request.getSession() 
==> interface로 되어있다(new로 객체생성하지 않는다)      => Cookie cookie=new Cookie() => 일반 클래스
      ----------------------------------------------------------------------------------
저장값	자바에서 사용되는 모든 클래스                    문자열만 저장 가능
         (Object)
저장위치   서버안에 저장
        => 구분(sessionId)
           getId() => WebSocket
보안     보안 (O)                                  보안(X)
사용처    로그인 (사용자 일부 정보)                      최신 방문
        장바구니		                             
-------------------------------------------------------------------------------------------

	Cookie
	   1) Cookie 생성
	      Cookie Cookie=new Cookie(String key,String value) 
	                    -----------------------------------쿠키는 항상 생성자를 통해 생성된다
	   2) 저장 기간 설정
	      cookie.setMaxAge(초) => 0이면 삭제
	   3) 저장 path지정
	      cookie.setPath("/"
	   4) 저장된 쿠키를 클라이언트 브라우저로 전송
	      response.addCookie(cookie)
	   5) 쿠키 읽기
	      Cookie[] cookies=request.getCookie() 
	                       -----------------쿠키에 저장된 정보 전부를 클라이언트에게 요청한다
	   6) key 읽기 : getName()
	   7) value 읽기 : getValue()
	   --------------------------
	   *** 쿠키/세션은 => 상태를 지속적으로 유지함
	                  ---- 값 변경(react/vue에 보면 state라는 변수가 있음.)
	                       데이터 관리 프로그램
	                       
	   Session (HttpSession 인터페이스)
	   ------------------------------ 내장 객체
	   서블릿 : request.getSession()
	   JSP : 내장객체( session)
	   --------------------------서버에 저장
	   => 전역변수 (모든 JSP에서 사용)
	   => 서버에 저장 (보안이 좋다)
	   
***	   1) 세션 저장
	      session.setAttribute(String key,Object obj)
	   2) 세션 저장 기간 
	      session.setMaxInactiveInterval() => 30분(1800초)==default
***	   3) 세션 읽기
	      Object session.getAttribute(String key) 
	      *** 형변환이 필요하다(저장할때 Object이니까)
***	   4) 세션 일부 삭제
	      session.removeAttribute(String key)
***	   5) 세션 전체 삭제
	      session.invalidate()
	   6) 생성여부 확인
	      session.isNew()
	   7) 세션에 등록된 sessionId (각 클라이언트마다 1개씩만 생성==primary key) => 이에 대한 모든 정보 (IP~PORT 등)
	      session.getId() 
	   ---------------------------------------------------------
	     저장방식 => Map방식(key,value)
	              value는 중복이 가능
	              key는 중복이 불가능 ===> 덮어쓴다
	              Web에서 사용 => Map방식 (request, response, session, application)
	              **getParameter(String key) 
	              
	              

 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>