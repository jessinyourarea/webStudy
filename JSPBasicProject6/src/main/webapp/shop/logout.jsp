<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//세션 삭제
session.invalidate();

// goods_jsp(메인) 재접속
response.sendRedirect("goods.jsp");

%>