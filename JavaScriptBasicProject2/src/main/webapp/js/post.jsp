<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%--
	 _jspService() => 브라우저에 실행하는 메소드 => service
	 {
	    ==========
	      JSP
	    ==========
	 }    
 --%>
<%
	// 자바 영역
	// DAO
	MemberDAO dao=MemberDAO.newInstance();
	request.setCharacterEncoding("UTF-8");
	String dong=request.getParameter("dong");
	int count=0;
	List<ZipcodeVO> list=null;
	if(dong!=null)
	{
		count=dao.postfindCount(dong);
		list=dao.postfind(dong);
	}
	
	//Model1(JSP) => MV구조 => MVC 
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
.container{
  margin-top:50px;
}
.row{
  margin:0px auto;
  width:450px;
}
h1{
  text-align:center
}
td,th{
	font-size:9px;
}
a {
	text-decoration:none;
	color:black
}
a:hover{
	text-decoration:underline;
	color:cyan;
}
</style>
<script type="text/javascript">
let ok=(zip,addr)=>{
	// opener => js8.html ( 팝업창을 열어주는 파일 )
	opener.frm.post1.value=zip.substring(0,3);
	opener.frm.post2.value=zip.substring(4,7);
	opener.frm.addr.value=addr;
	self.close();  //자신을 닫는다 => post.jsp를 닫는다
}
</script>
</head>
<body>
  <div class="container">
    <h1>우편번호 검색</h1>
    <div class="row">
     <form method=post action="post.jsp">
     <table class="table">
      <tr>
        <td>
         우편번호: <input type=text name=dong size=15 
                 class="input-sm">
                <input type=submit value="검색" 
                 class="btn btn-sm btn-danger">
        </td>
      </tr>
     </table>
     </form>
     <div style="height:20px"></div>
     <%
     	if(list!=null)
     	{
     %>
     	 <table class="table">
     	   <tr>
     	     <th width=20% class="text-center">우편번호</th>
     	     <th width=80% class="text-center">주소</th>
     	   </tr>
     	   <%	
     	   		if(count==0)
     	   		{
     	   %>
     	   		<tr>
     	   		 <td colspan="2" class="text-center">
     	   		  <h3>검색된 결과가 없습니다</h3>
     	   		 </td>
     	   		</tr>
     	   <%
     	   		}
     	   		else
     	   		{
     	   			for(ZipcodeVO vo:list)
     	   			{
     	   %>
						<tr>
						 <td width="20%" class="text-center">
						 <%=vo.getZipcode()%>
						 </td>
						 <td width="80%">
						   <a href="javascript:ok('<%=vo.getZipcode()%>','<%=vo.getAddress()%>')"><%=vo.getAddress()%></a>
						 </td>
						</tr>
     	   <%
     	   			}
     	   		}
     	   %>
     	 </table>
     <%
     	}
     %>
    </div>
  </div>
</body>
</html>












