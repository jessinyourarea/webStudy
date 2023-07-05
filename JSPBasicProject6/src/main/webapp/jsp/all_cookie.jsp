<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%
Cookie[] cookies=request.getCookies();
	FoodDAO dao=FoodDAO.newInstance();
	List<FoodBean> cList=new ArrayList<FoodBean>();
	if(cookies!=null)
	{
		for(int i=0;i<cookies.length;i++)
		{
	if(cookies[i].getName().startsWith("food_"))
	{
		String fno=cookies[i].getValue();
		FoodBean vo=dao.foodDetailData(Integer.parseInt(fno));
		cList.add(vo);
	}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <div class="container">
    <h1>쿠키보기</h1>
    <div class="row">
      <%-- 맛집 목록 출력 위치 --%>
      <%
      for(FoodBean vo:cList)
            	{
      %>
      		<div class="col-md-3">
   			 <div class="thumbnail">
                 <img src="<%=vo.getPoster() %>" alt="Nature" style="width:100px;height:100px">
                   <div class="caption">
		          <p><%=vo.getName() %></p>
		        </div>
		    </div>
		  </div>
      <%
      	}
      %>
    </div>
    </div>
  </div>
</body>
</html>