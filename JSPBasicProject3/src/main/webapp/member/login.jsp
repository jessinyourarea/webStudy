<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	width:450px;
}
h1{
	text-align:center;
	font-family: 'Gasoek One', sans-serif;
}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
// 자바스크립트 라이브러리 => jquery : 통합성이 좋음(모두 동일한 형식을 이용함)
/*
 	let id=document.querySelector("#id")
 	       ----------------------> 이 부분이 $로 바뀌었다! 
 	       => 값을 읽을 때 
 	          id.value => $('#id').val() //값을 가져올 때
 	          --------
 	          id.textContent => $('#id').text() //문자를 가져올때
 	          --------------
 	          id.innerHTML => $('#id').html() //html을 읽어올때
 	          --------------  $('#id').attr() //속성값을 읽어올때
 	JQUERY=> $('#id')
 */
$(function(){ //window.onload=function(){}
	/* $('#logBtn').click(function(){
	   alert("Hello JQuery!!")		
	}) */
	$('#logBtn').on('click',function(){
		//alert("Hello JQuery!!")
		let id=$('#id').val();
		
		if(id.trim()=="")
		{
			$('#id').focus();
			return;
		}

		let pwd=$('#pwd').val();
		
		if(pwd.trim()=="")
		{
			$('#pwd').focus();
			return;
		}
		//$('#frm').submit();
 		$.ajax({
			type:'post',
			url:'login_ok.jsp',
			data:{"id":id,"pwd":pwd},
			success:function(result)
			{
				let res=result.trim();
				if(res=='NOID')
				{
					$('#id').val("");
					$('#pwd').val("");
					$('#id').focus();
					$('#print').text("아이디가 존재하지 않습니다");
				}
				else if(res=='NOPWD')
				{
					$('#pwd').val("");
					$('#pwd').focus();
					$('#print').text("비밀번호가 틀립니다");					
				}
				else
				{
					location.href="../databoard/list.jsp";
				}
			}
		}) 
	})
})

</script>
</head>
<body>
  <div class="container">
    <h1>Log-in</h1>
    <div class="row">
      <form method=post action=login_ok.jsp id="frm">
      <table class="table">
        <tr>
          <td width=20%>ID</td>
          <td width=80%>
            <input type=text name="ID" size=15 class="input-sm" id="id">
          </td>
        </tr>
        <tr>
          <td width=20%>Password</td>
          <td width=80%>
            <input type=password name="pwd" size=15 class="input-sm" id="pwd">
          </td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
           <span id="print" style="color:red"></span>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="text-center">
            <input type=button class="btn btn-sm btn-danger" value=로그인
             id="logBtn"
            >
            <a href="../databoard/list.jsp" class="btn btn-sm btn-success">게시판</a>
          </td>
        </tr>
      </table>
      </form>
    </div>
  </div>
</body>
</html>




























