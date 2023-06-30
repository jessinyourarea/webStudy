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
	boolean bCheck=dao.boardUpdate(vo);
	request.setAttribute("bCheck",bCheck);
	
	request.setAttribute("vo",vo);	
	
	// 화면 이동 => 
%>
<c:choose>
  <c:when test="${bCheck==true }">
    <c:redirect url="detail.jsp?no=${vo.no }"/>
  </c:when>
  <c:otherwise>
    <script>
      alert("비밀번호가 틀립니다!")
      history.back()
    </script>
  </c:otherwise>
</c:choose>
