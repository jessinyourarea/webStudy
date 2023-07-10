<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.row{
	maring:0px auto;
	width:860px;
}
</style>
</head>
<body>
  <div class="wrapper row3">
   <main class="container clear">
    <div class="row">
     <table class="table">
       <tr>
         <td width=40% class="text-center" rowspan="8">
          <img src="${vo.goods_poster }" style="width: 100%">
         </td>
         <td width=60%><h3>${vo.goods_name }</h3></td>
       </tr>
       <tr>
        <td><span style="color:gray">${vo.goods_sub }</span></td>
       </tr>
       <tr>
        <td class="inline"><span style="color:red">${vo.goods_discount }%</span>
          &nbsp;<h3 style="display: inline;">${vo.goods_price }</h3>
        </td>
       </tr>
       <tr>
        <td><span style="color:green;font-size:8px">첫구매할인가</span>&nbsp;<span style="color:green;">${vo.goods_first_price}</span></td>
       </tr>
       <tr>
        <td>배송&nbsp;&nbsp;<span style="color:gray">${vo.goods_delivery }</span></td>
       </tr>
       <tr>
        <td class="inline">
         수량:<select name="account" class="input-sm" style="width:350px">
             <c:forEach var="i" begin="1" end="${vo.account }">
               <option value="${i }">${i }개</option>
             </c:forEach>
         </select>
        </td>
       </tr>
       <tr>
         <td class="text-right"></td>
       </tr>
       <tr>
        <td class="inline">
          <input type=button class="btn btn-lg btn-success" value="장바구니" style="width: 150px">
          <input type=button class="btn btn-lg btn-info" value="바로구매" style="width: 150px">
        </td>
       </tr>
     </table>
    </div>
   </main>
  </div>
</body>
</html>