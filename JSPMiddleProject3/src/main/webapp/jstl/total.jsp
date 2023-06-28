<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%--
	EL => <%= %>을 대체해서, 가급적이면 자바코딩 제거
	-- 표현식(화면 출력) == System.out.println()
	형식)
	      ${표현식}
	
	표현식)
	      연산자
	        산술연산자 (+ , - , * , / , % )
		            +는 산술만 처리 (문자열 결합은 X => +=)
		            /는 정수/정수=실수 , 0으로 나눌 수 없다
		            => null값은 0으로 취급
		                / (div) ${10 div 2} , ${10/2}
		                % (mod) ${10 mod 2} , ${10$2} 
		           "1" => 자동으로 Integer.parseInt() 정수
		    비교연산자
		        ** 날짜, 문자열을 포함한다
		                ----(한글: 가나다라 순으로 크기 비교)
		    삼항연산자 : 예) 페이지, select, radio ...
		              ${gender=='남자'?"checked":""}
	      내장 객체 (일반 JSP ==> 자바/HTML 분리)
	              -------     -------------> 모든 처리는 자바: 결과값만 출력
	               |
	             자바/HTML 혼합
	        =requestScope =>
	         기존의 request + 추가 데이터 설정 => 
	         request.setAttribute("key",값) : 오라클에서 얻은 값을 추가
	         request.getAttribute("key")
	          |
	         ${requestScope.key} => ${key}
	        =sessionScope => 
	         session에 저장된 값을 읽기
	         ${sessionScope.key} => ${key} => 우선순위: request=> session
	        =param : ${param.key} => request.getParameter("key")
	        =paramValues : ${paramValues.key} => request.getParameterValues()
	        
	        ${자바의 일반 변수 사용이 안된다}
	        String name="";
	        ${name} => 출력이 안됨
	        request.setAttribute("name","")
	         | 
	        ${name}
	         
		 EL => 표현문을
		 JSTL => 태그를 가지고 제어하는 언어
		 
		 => 제어 : JSTL
		 
		 core= 제어문, URL관련, 변수 설정(request.setAttribute())
		       제어문
		       조건문
		       -----
		        <c:if test="조건문">
 	            조건문이 true일때 실행
 	            </c:if> 
 	            => 다중 조건문, if~else가 없다
 	           선택문 
 	           ---
 	              <c:choose>
		 	        <c:when test="">
		 	        </c:when>
		 	        <c:when test="">
		 	        </c:when>
		 	        <c:when test="">
		 	        </c:when>
		 	        <c:otherwise></c:otherwise> => else/default
		 	      </c:choose> 
		       반복문
		          <c:forEach> => for문
		             for(int i=0;i<=10;i++)
		             => <c:forEach var="i" begin="0" end="10" step="1">
		                                             -------
		                                             포함된다 
		            ** 단점
		            for(int i=10;i>=0;i--)
		            => <c:forEach>가 없음!! 감소식은 없다~~  
		          <c:forEach> => 향상된 for문(for-each)
		              for(BoardVO vo:list)
		              => <c:forEach var="vo" items="list"> (가장 많이 사용)
		          
		          <c:forTokens> => StringTokenizer
		          
		          URL : 화면 이동
		          ---------------
		            response.sendRedirect(url)
		             => <c:redirect url="url">
		            request.setAttribute("a",값)
		             => <c:set var="a" value="값">
		            out.println()
		             => <c:out value="">
		             => 자바스크립트에서 자바데이터를 받아서 출력할때
		                jquery => $
		                          ${}
		      fmt
		      fn : String 메소드 처리
		           ${fn:length(문자열)}
		           ${fn:substring(문자열,start,end}
		      sql : DAO
		      xml : OXM
		            => 자바 자체에서 처리 (사용빈도가 거의 없다)
		            

 --%>
 <%-- import --%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%-- format --%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%-- function : fn --%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 <%--
 		prefix 는 사용자가 결정 가능
 		
  --%>
<%
	//데이터 설정=> 오라클
	List<String> names=new ArrayList<String>();
	names.add("홍길동");
	names.add("심청이");
	names.add("이순신");
	names.add("박문수");
	names.add("강감찬");
	// EL 사용 불가.. (일반변수) => request,session에 저장해야 사용 가능
	request.setAttribute("names",names);
	
%>  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>자바를 이용한 for문</h1>
  <h3>이름 목록</h3>
    <ul>
  <% 
  	for(String name:names)
  	{
  %>
  		<li><%=name %></li>
  <%	
  	}
  %>
    </ul>
   
   <h3>JSTL 출력</h3> 
   <ul>
	<c:forEach var="name" items="${names }">
	  <li>${name }</li>
	</c:forEach>
   </ul> 
    
   <h3>자바(StringTokenizer)</h3> 
   <ul>
   <%
   		String color="red,blue,green,yellow,black";
   		StringTokenizer st=new StringTokenizer(color,",");
   		while(st.hasMoreTokens())
   		{
   %>
   			<li><%=st.nextToken() %></li>
   <%
   		}
   %>
   </ul>
   
   <h3>JSTL (forTokens)</h3>
   <ul>
     <c:forTokens var="color" items="red,blue,green,yellow,black"
                  delims=",">
                  <li>${color }</li>
     </c:forTokens>
   </ul>
</body>
</html>






