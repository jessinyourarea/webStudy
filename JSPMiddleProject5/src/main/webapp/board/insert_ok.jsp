<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sist.dao.*" %>

<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>

<%
	//데이터받기 (한글 => 디코딩)
	request.setCharacterEncoding("UTF-8");
%>

<%-- 사용자가 보내준 데이터를 vo객체에 첨부 --%>
<jsp:useBean id="vo" class="com.sist.dao.ReplyBoardBean">
  <jsp:setProperty name="vo" property="*"/>
</jsp:useBean>
<%
	// DAO => 오라클에 데이터 첨부
	
	dao.boardInsert(vo);
	
	// 화면 이동 => list.jsp
%>
<c:redirect url="list.jsp"/>