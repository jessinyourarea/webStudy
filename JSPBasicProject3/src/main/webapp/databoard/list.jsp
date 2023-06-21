<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,com.sist.vo.*, java.util.*"%>
<%@ page import="java.text.*" %>
<%
	// 1. 사용자가 보내준 요청값을 받는다 => page
	String strPage=request.getParameter("page");
	/*
		list.jsp   ==> null
		list.jsp?page=  ==> "" 공백
		list.jsp?page=2 ==> 2
				
	*/
	if(strPage==null)
		strPage="1"; //default 설정
	int curPage=Integer.parseInt(strPage);
	// 2. DAO에서 요청한 page의 값을 읽어온다
	DataBoardDAO dao=DataBoardDAO.newInstance();
	List<DataBoardVO> list=dao.dataBoardListData(curPage);
	int count=dao.dataBoardRowCount();
	int totalPage=(int)(Math.ceil(count/10.0));
	
	final int BLOCK=5;
	int startPage=((curPage-1)/BLOCK*BLOCK)+1;
	// 5페이지까지는 0 (5-1)/5*5 => 0 
	int endPage=((curPage-1)/BLOCK*BLOCK)+BLOCK;			
	if(endPage>totalPage)
		endPage=totalPage;
	
	// 3. 오라클에서 가지고 온 데이터를 화면에 출력
	// ====> 자바로 이동(Model)
	count=(count-((curPage*10)-10));
	/*
		지난 페이지*rowcount에 대해 빼고, 새로운 count로 출력하기
		
	*/
	
	//오늘 날짜 구하기
	String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	
	
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
</head>
<body>
  <div class="container">
    <h1>자료실</h1>
    <div class="row">
      <table class="table">
        <tr>
          <td>
            <a href="insert.jsp" class="btn btn-sm btn-warning">새글</a>
          </td>
        </tr>
      </table>
      <table class="table table-hover">
        <tr class="danger">
         <th class="text-center" width=10%>번호</th>
         <th class="text-center" width=45%>제목</th>
         <th class="text-center" width=15%>이름</th>
         <th class="text-center" width=20%>작성일</th>
         <th class="text-center" width=10%>조회수</th>
        </tr>
        <%
        	// 데이터 출력
        	for(DataBoardVO vo:list)
        	{
       	%>
       			 <tr>
		         <td class="text-center" width=10%><%=count-- %></td>
		         <td width=45%>
		         <a href="detail.jsp?no=<%=vo.getNo()%>"><%=vo.getSubject() %></a>
		         &nbsp;
		         <%
		         	if(today.equals(vo.getDbday()))
		         	{
		         %>
		         		<sup style="color:red">new</sup>
		         <%
		         	}
		         %>
		         </td>
		         <td class="text-center" width=15%><%=vo.getName() %></td>
		         <td class="text-center" width=20%><%=vo.getDbday() %></td>
		         <td class="text-center" width=10%><%=vo.getHit() %></td>
		         </tr>
       	<%
        	}
        %>
      </table>
    </div>
    <div class="row">
      <div class="text-center">
        <ul class="pagination">
        <%
        	if(startPage>1)
        	{
        		
                %>
		  <li><a href="list.jsp?page=<%= startPage-1%>">&lt;</a></li>
		<%
        	}
		%>
		   <%
		   		for(int i=startPage;i<=endPage;i++)
		   		{
		   			
		   %>
		   			<li <%=curPage==i?"class=active":"" %>><a href="list.jsp?page=<%=i%>"><%=i %></a></li>
		   <%
		   		}
		  
		   	 if(endPage<totalPage)
		   	 {
		   %>
		  
		  <li><a href="list.jsp?page=<%=endPage+1%>">&gt;</a></li>
		  <%
		   	 }
		  %>
		</ul>
      </div>
    </div>
  </div>
</body>
</html>















