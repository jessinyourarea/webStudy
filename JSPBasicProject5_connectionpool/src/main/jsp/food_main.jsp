<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%--
		293page DBCP => DataBase Connection Pool
		1. 연결 / 해제 반복 
		   => 시간 소모가 많다 (오라클 연결 시에 시간이 많이 소요된다!)
		      -------------
		   => 연결 시 시간 소모를 절약하기 위해서!
		      미리 Connection객체를 생성해서 저장 후에 사용~
		   => 일반적으로 웹 프로그램에서는 일반화
		   => Connection객체 생성을 제한
		   => 사용 후에는 재사용이 가능
		2. Connection을 미리 생성하기 때문에 => Connection객체 관리가 쉽다
		3. 쉽게 서버가 다운되지 않는다
		4. 라이브러리가 만들어져 있다(commons-dbcp,commons-pool)
		
		원하는 갯수만큼 커넥션 객체를 만들어둠 => 몇개~몇천개도 다 괜찮음! (pool안에)
		==> 이걸 오라클과 연결할 수 있게 만들어둠. 오라클에서 언제든 객체를 얻어서 사용할 수 있도록.
		매번 커넥션을 새로 연결하면 속도가 느려짐!
		
		<사용 방식>
		1. 생성 Connection 얻기
		2. 사용 (Statement)
		3. 반환 (재사용 할 수 있도록 돌려줌=> 그래서 증감하지 않음)
		
		280page
		목적: 웹 사용자는 응답 시간을 빠르게 하는것
		     ------------------------
		     DB연결을 원활하게 하면=> BackEnd가 빨라진다(Front도 속도를 같이 맞춰줘야하니 생겨난게 VueJS 등)
		     효율적으로 데이터베이스 연동을 원활하게 해주는 역할~
		
		DBCP
		---- 
		방법 => 1. Connection 객체 생성(maxActive,maxIdle)
		                            최대 생성가능 갯수, 최대 풀 안에..
		       2. POOL영역에 저장
		       3. 사용자가 요청 시에 Connection의 주소를 얻어온다
		       4. 사용자의 요청에 따라 수행
		       5. 반드시 Pool안에 Connection객체를 반환해준다  
               ---------------------------------------------------------	
               1. server.xml => Connection 객체 생성
               2. 코딩 방법
                  -------
                  1) getConnection(): 미리 생성된 Connection객체를 가져온다
                  2) disConnection(): 반환
                  3. 기능 : 이전하고 동일
                  *** 보안이 좋다
                  
--%>
<%
	// 1. 사용자가 전송한 데이터를 받는다 (page)
	String strPage=request.getParameter("page");
	// 2. 실행과 동시에 페이지전송은 못함 => 첫페이지는 디폴트를 설정해야함
	if(strPage==null)
		strPage="1";
	
	// 3. 현재 페이지를 지정
	int curpage=Integer.parseInt(strPage);
	
	// 4. 현재 페이지에 대한 데이터 읽기(DAO=> 오라클)
	FoodDAO dao=FoodDAO.newInstance();
	List<FoodBean> list=dao.foodListData(curpage);
	
	// 5. 총 페이지 읽기
	int totalpage=dao.foodTotalPage();
	
	// 6. 블록별 처리
	final int BLOCK=10;
	// [1] ~ [10]
	
	// 7. 시작 위치
	int startPage=(curpage-1)/BLOCK*BLOCK+1;
	
	// 8. 끝 위치
	int endPage=(curpage-1)/BLOCK*BLOCK+BLOCK;
	
	if(endPage>totalpage)
	{
		endPage=totalpage;
	}
	
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
	width:960px
}

</style>
</head>
<body>
  <div class="container">
    <div class="row">
      <%
         for(FoodBean vo:list)
         {
      %>
      	  <div class="col-md-4">
   			 <div class="thumbnail">
     		   <a href="/w3images/nature.jpg">
                 <img src="<%=vo.getPoster() %>" alt="Nature" style="width:100%">
                   <div class="caption">
		          <p><%=vo.getName() %></p>
		        </div>
		      </a>
		    </div>
		  </div>
      <%
         }
      %>
    </div>
  </div>

</body>
</html>



















