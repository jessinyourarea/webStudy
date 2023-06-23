<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*" %>
<%--
		JSP
		---
		1) 지시자 : page / include / taglib
		                -----------------
		                  page지시자: JSP 파일에 대한 정보
		                  1. contentType: 브라우저에서 실행하는 타입 지정
		                                  - html
		                                  - xml
		                                  - json => Ajax / Vue / React 에서 주로 사용 --- Restful을 사용해서
		                  2. import: 여러번 사용 가능, 라이브러리 읽을 때 사용
		                  3. errorPage: 에러 시에 에러 출력 화면으로 이동
		                  4. buffer : html태그를 저장하는 공간
		                              소스미리보기 기능
		                              => 버퍼는 사용자마다 한개만 생성(브라우저가 연결해서 읽어가는 공간)
		2) 스크립트 : 자바/HTML을 분리해서 소스코딩을 할 때
		           자바언어 코딩하는 위치(벗어나면 일반 텍스트로 인식함.)
		   <%!  %> : 선언문(멤버변수,메소드선언)
		             = 클래스 제작 시 클래스 블록 안에 => 별로 사용빈도가 높지않다
		             = 자바클래스를 만들어서 메소드를 호출해도 된다.
		   <%   %> : 일반 메소드 영역
		             _jspService()
		             {  
		                ---------------
		                 JSP에 첨부하는 소스
		                ---------------
		             } 
		   <%=  %> : 화면 출력 ( 변수, 문자열 등..) == out.println()
		             out객체
		3) 내장객체 : 미리 객체를 생성해서 필요한 위치에서 사용 가능하게 
			request : HttpServletRequest
			          사용자 정보(요청정보, 추가정보, 브라우저 정보)
			          = 요청 정보는
			            * getParameter() : 사용자 전송한 데이터를 한개만 받는다
			              => 문자열 : String
			            * getParameterValues() : 여러개를 동시에 받을 경우
			              => String[] : 체크박스 select의 멀티..
			            * setCharacterEncoding() : 디코딩 역할
			              => POST에서만 사용
			              => GET방식은 자동화 처리해줌. (window10이상 부터)
			                 =================servel.xml(한글처리,포트)
			                 8080은 프록시 번호
			          = 추가정는(MVC,MV) => 오라클에서 받은 값을 추가 => JSP
			            * setAttribute(): 기존영역에 있는 request값에 출력에 필요한 데이터를 추가해서 전송
			            * getAttribute(): 추가해서 보낸 데이터를 받을 경우 사용
			            ------------------session에도 있다
			          = 브라우저 정보
			            * getRemoteAddr(): 접속자의 IP
			            * getRequestURL(), getRequestURI(), getContextPath()
			response : 응답정보나 화면 이동 정보 -> HTTPServletResponse
			           ------
			           = setContextType() => HTML,XML
			             => page 지시자
			           = addCookie : 쿠키 전송
			             ------------- 한번만 수행할 수 있다
			             화면 이동 정보
			             ----------- 필요 시에 서버에서 화면을 요청화면이 아닌 다른 화면 이동
			                                -----------------------------------
			                               = login => main
			                               = insert => list
			                               = delete => lst
			                               = update => detail
			                         => sendRedirect(!) => GET방식, request를 초기화 한 다음에 사용ㅇ
			                Header 정보: 실제 데이터 처리 전에 한꺼번에 처리
			                 setHeader(): 한글변환/ 다운로드 시에 파일명,크기 
			out	: Jspprintr (출력 버퍼 관리)
			      출력(메모리)
			       println() <%= %>
			         | 
			       메모리 확인
			      getBufferSize() : 총 버퍼 크기
			      getRemaining() : 사용중인 버퍼 크기
			      close() : 남은거 지우기
			      
			session
			application
			pageContext 
			-----------필수 영역
			
		    config : web.xml처리(환경 설정)
		    exception : try~catch
		    page : this대신
			              
 --%>
   <%-- out출력(복잡한 HTML구조) print--%>
   
   
 <%--
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  
</body>
</html>
 --%>
<%
	EmpDAO dao=new EmpDAO();
	List<EmpVO> list=dao.empListData();
	
	
	  out.print("<!DOCTYPE html> ");
	  out.print("<html> ");
	  
	  out.print("<head>");
	  out.print("<meta charset=\"UTF-8\">");
	  out.print("<title>Insert title here</title>");
	  out.print("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
	  out.print("<style>");
	  out.print(".container{margin-top:50px}");
	  out.print(".row{margin:0px auto;width:800px}");
	  out.print("h1{text-align:center}");
	  out.print("</style>");
	  out.print("</head>");
	  out.print("<body>");
	  out.print("<div class=container>");
	  out.print("<h1>사원 목록</h1>");
	  out.print("<div class=row>");
	  
	  out.print("<table class=\"table table-striped\">");
	  
	  out.print("<tr class=success>");
	  out.print("<th class=text-center>사번</th>");
	  out.print("<th class=text-center>이름</th>");
	  out.print("<th class=text-center>직위</th>");
	  out.print("<th class=text-center>입사일</th>");
	  out.print("<th class=text-center>급여</th>");
	  out.print("<th class=text-center>성과급</th>");
	  out.print("<th class=text-center>부서명</th>");
	  out.print("<th class=text-center>근무지</th>");
	  out.print("<th class=text-center>호봉</th>");
	  out.print("</tr>");
	  
	  for(EmpVO vo:list)
	  {
		  out.print("<tr>");
	      out.print("<td class=text-center>"+vo.getEmpno()+"</td>");
	      out.print("<td class=text-center><a href=\"MainServlet?mode=2&empno="+vo.getEmpno()+"\">"+vo.getEname()+"</a></td>");
	      out.print("<td class=text-center>"+vo.getJob()+"</td>");
	      out.print("<td class=text-center>"+vo.getDbday()+"</td>");
	      out.print("<td class=text-center>"+vo.getDbsal()+"</td>");
	      out.print("<td class=text-center>"+vo.getComm()+"</td>");
	      out.print("<td class=text-center>"+vo.getDvo().getDname()+"</td>");
	      out.print("<td class=text-center>"+vo.getDvo().getLoc()+"</td>");
	      out.print("<td class=text-center>"+vo.getSvo().getGrade()+"</td>");
	      out.print("</tr>");
	  }
	  
	  out.print("</table>");
	  
	  out.print("</div>");
	  out.print("</div>");
	  
	  out.print("</body>");
	  out.print("</html>");

%>













