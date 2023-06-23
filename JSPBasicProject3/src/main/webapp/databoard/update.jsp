<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,com.sist.vo.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no=request.getParameter("no");
	DataBoardVO vo=dao.dataBoardDetailData(Integer.parseInt(no), 1);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Gasoek+One&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
	margin-top:50px;
}
.row{
	margin: 0px auto;
	width:900px;
}
h1{
	text-align:center;
	font-family: 'Gasoek One', sans-serif;
}
</style>
<script type="text/javascript">
function board_write(){
	// 유효성 검사
	// form 읽기
	let frm=document.frm;
	
	//오라클의 NOT NULL이 설정된 것만, 빈칸인 경우 값 필수로 입력하도록 다시 돌려보내기
	if(frm.name.value=="")
	{
		frm.name.focus();
		return;
	}
	if(frm.subject.value=="")
	{
		frm.subject.focus();
		return;
	}
	if(frm.content.value=="")
	{
		frm.content.focus();
		return;
	}
	if(frm.pwd.value=="")
	{
		frm.pwd.focus();
		return;
	}
	
	frm.submit(); // submit 버튼을 함수화 !!!
	
}
</script>
</head>
<body>
  <div class="container">
    <h1>수정하기</h1>
    <div class="row">
      <form method=post action="update_ok.jsp" name=frm
      	enctype="multipart/form-data"
      >
      <!-- 
      	multipart/form-data : fileupload 프로토콜
      	=> POST
       -->
      <table class='table'>
        <tr>
          <th class="text-center danger" width=15%>이름</th>
          <td width=85%>
          <input type=text name=name class="input-sm" size=15 value=<%=vo.getName() %>>
          <input type=hidden name=no value=<%=no %>>
          </td>
        </tr>
        <tr>
          <th class="text-center danger" width=15%>제목</th>
          <td width=85%>
          <input type=text name=subject class="input-sm" size=50 value=<%=vo.getSubject() %>>
          </td>
        </tr>
        <tr>
          <th class="text-center danger" width=15%>내용</th>
          <td width=85%>
          <textarea rows="10" cols="50" name=content ><%=vo.getContent() %></textarea>
          </td>
        </tr>
        <!--
        <tr>
          <th class="text-center danger" width=15%>첨부파일</th>
          <td width=85%>
          <input type=file name=upload class="input-sm" size=20>
          </td>
        </tr> 
         -->
        <tr>
          <th class="text-center danger" width=15%>비밀번호</th>
          <td width=85%>
          <input type=password name=pwd class="input-sm" size=20>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
          <input type=button value="수정" class="btn btn-sm btn-primary" onclick="board_write()">
          <input type=button value="취소" class="btn btn-sm btn-info" 
           onclick="javascript:history.back()" >
          </td>
        </tr>
      </table>
      </form>
    </div>
  </div>
</body>
</html>