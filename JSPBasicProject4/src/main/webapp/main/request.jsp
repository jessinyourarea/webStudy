<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>HttpServletRequest(request):169page</h1>
  <table class="table">
    <tr>
      <th width=20% class="text-center">클래스명</th>
      <td width=80% class="text-center">HttpServletRequest</td>
    </tr>
    <tr>
      <th width=20% class="text-center">주요기능</th>
      <td width=80%>
        <ul>
         <li>사용자가 요청한 데이터 읽기</li>
         <li>한글변환(디코딩)</li>
         <li>데이터 추가가 가능</li>
         <li>사용자 정보나 서버 정보를 가지고 있음.</li>
        </ul>
	  </td>
    </tr>
    <tr>
      <th width=20% class="text-center">주요메소드</th>
      <td width=80%>
        <ul>
         <li>
          사용자 요청 정보
          <ul>
           <li>***String getParameter(String key): 단일값을 받는 경우에 사용</li>
           <li>String[] getParameterValues(String key): 다중값을 받는 경우에 사용</li>
           <li>***void setCharacterEncoding(""): 디코딩(인코딩 변경)</li>
          </ul>
         </li>
         <li>
          브라우저 정보/ 서버 정보
           <ul>
            <li>***String getRemoteAddr(): 사용자의 IP를 얻어온다</li>
            <li>String getServerName(): 서버명을 가져온다</li>
            <li>***String getRequestURL(): URL주소 읽기</li>
            <li>***String getRequestURI(): 사용자가 요청한 파일 읽기</li>
            <li>***String getContextPath(): 사용자 요청 폴더의 ROOT 읽기</li>
           </ul>
         </li>
         <li>
          추가정보
           <ul>
            <li>***void setAttribute(): 데이터를 request에 추가해서 전송</li>
            <li>***Object getAttribute(): 추가된 데이터 읽기</li>
           </ul>
         </li>
         
        </ul>
	  </td>
    </tr>
  </table>
</body>
</html>













