<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<String> list=new ArrayList<String>();
	list.add("red");
	list.add("green");
	list.add("blue");
	list.add("cyan");
	list.add("orange");
%>
<c:set var="list" value="<%=list %>"/>
<%--
	JSTL에서 제공하는 태그는 XML로 만들어져 있다
	=> 1. 태그, 속성은 대소문 구분
	=> 2. 속성값은 반드시 ""
	=> 3. 여는 태그 / 닫는 태그를 반드시 사용한다
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>자바</h1>
  <ul>
    <li><%=list.get(0) %></li>
    <li><%=list.get(1) %></li>
    <li><%=list.get(2) %></li>
    <li><%=list.get(3) %></li>
    <li><%=list.get(4) %></li>
  </ul>
  
  <h1>자바에서 for문</h1>
  <ul>
    <%
    	for(int i=0;i<list.size();i++)
    	{
    %>
    		<li><%=list.get(i) %></li>
    <%		
    	}
    %>
  </ul>
  
  <h1>자바에서 forEach</h1>
  <ul>
   <%
   		for(String color:list)
   		{
   %>	
   			<li><%=color %></li>
   <%
   		}   
   %>
  </ul>
  
  <h1>EL 이용</h1>
  <ul>
    <li>${list[0]}</li>
    <li>${list[1]}</li>
    <li>${list[2]}</li>
    <li>${list[3]}</li>
    <li>${list[4]}</li>
  </ul>
  
  <h1>JSTL</h1>
  <ul>
    <c:forEach var="color" items="${list }">
      <li>${color }</li>
    </c:forEach>
  </ul>
  
  <h1>JSTL 2</h1>
  <ul>
  	<%-- varStatus: list의 index번호를 읽어온다 --%>
    <c:forEach var="color" items="${list }" varStatus="s">
		<%--<li>${s.index+1}.${color }</li> --%>
		<li>${list[s.index ]}</li>
    </c:forEach>
  </ul>
  
  <h1>JSTL 이용3</h1>
  <%
  	List<String> names=new ArrayList<String>();
    names.add("홍길동");
    names.add("심청이");
    names.add("박문수");
    names.add("춘향이");
    names.add("강감찬");
    
    List<String> genders=new ArrayList<String>();
    genders.add("남자");
    genders.add("여자");
    genders.add("남자");
    genders.add("여자");
    genders.add("남자");
  %>
  
  <c:set var="names" value="<%=names %>"/>
  <c:set var="genders" value="<%=genders %>"/>
  <ul>
    <c:forEach var="name" items="${names }" varStatus="s">
		<li>${name }${genders[s.index] }</li>    
    </c:forEach>
  </ul>
</body>
</html>









