<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>
<%
	String no=request.getParameter("no");	// 출력만 할거라면 ${param.no}
	ReplyBoardBean vo=dao.boardUpdateData(Integer.parseInt(no));

	request.setAttribute("vo",vo);  //EL 사용하기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
	margin-top:50px;
}
.row{
	margin:0px auto;
	width:800px
}
</style>
</head>
<body>
  <div class="container">
    <div class="row">
     <form method=post action="update_ok.jsp">
     <div class="text-center">
      <img src="image/qna.jpg" style="width:450px;height:100px">
	 </div>
	 <div style="height:20px"></div>
	 <div class="row">
	   <table class="table">
	     <tr>
	      <th width=20% class="text-right">이름</th>
	      <td width=80%>
	       <input type=text name=name size=20 class="input-sm" value=${vo.name}>
	      </td>
	     </tr>
	     <tr>
	      <th width=20% class="text-right">제목</th>
	      <td width=80%>
	       <input type=text name=subject size=20 class="input-sm" value=${vo.subject}>
	      </td>
	     </tr>
	     <tr>
	      <th width=20% class="text-right">내용</th>
	      <td width=80%>
	       <textarea rows="10" cols="52" name=content>${vo.content}</textarea>
	      </td>
	     </tr>
	     <tr>
	      <th width=20% class="text-right">비밀번호</th>
	      <td width=80%>
	       <input type=password name=pwd size=15 class="input-sm">
	      </td>
	     </tr>
	     <tr>
	       <td colspan="2" class="text-center">
	        <input type=submit value="수정" class="btn btn-sm btn-primary">
	        <input type=button value="취소" class="btn btn-sm btn-info" 
	         onclick="javascript:history.back()">
	       </td>
	     </tr>
	   </table>
	  </form>
	 </div>
  </div>
</body>
</html>













