<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>pageContext(pageContext):181page</h1>
   <table class="table">
    <tr>
      <th width=20% class="text-center">클래스명</th>
      <td width=80%>ServletContext</td>
    </tr>
    <tr>
      <th width=20% class="text-center">주요기능</th>
      <td width=80%>
        <ul>
         <li>페이지 연결</li>
         <li>내부객체 정버</li>
        </ul>
	  </td>
    </tr>
    <tr>
      <th width=20% class="text-center">주요메소드</th>
      <td width=80%>
        <ul>
         <li>
          페이지 연결
          <ul>
           <li>include(): 여러개의 JSP를 조리:&lt; jsp:include&gt;</li>
           <li>forward(): HTML만 덮어쓰는 역할:&lt;MVC주로 &gt;</li>
           <li></li>
          </ul>
         </li>
         <li>
          내부 객체 얻기
           <ul>
            <li>getRequest()=> request객체 얻기 </li>
            <li>getResponse()=> response객체 얻기 </li>
            <li>getSession()=> session객체 얻기 </li>
         <li>
        </ul>
	  </td>
    </tr>
  </table>
</body>
</html>