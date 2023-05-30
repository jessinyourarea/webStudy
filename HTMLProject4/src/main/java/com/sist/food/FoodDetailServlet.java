package com.sist.food;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
@WebServlet("/FoodDetailServlet")
public class FoodDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 전송 => 브라우저 전송(response)
	response.setContentType("text/html;charset=UTF-8");
	
	//전 페이지에서 전송된 값을 받는다 => fno
	String fno=request.getParameter("fno");
	// request는 사용자가 전송한 데이터를 받을 때 사용 (요청정보) 
	// 웹 객체(request, response, session-서버에 저장) => cookie-클라우드에 저장
	
	//DB에서 값을 가져오기
	FoodDAO dao=FoodDAO.newInstance();
	FoodVO vo=dao.foodDetailData(Integer.parseInt(fno));
	String addr=vo.getAddress();
	String addr1=addr.substring(0,addr.lastIndexOf("지번"));
	String addr2=addr.substring(addr.lastIndexOf("지")+3);
	
	//vo에 저장된 데이터를 HTML에서 출력
	PrintWriter out=response.getWriter();
	
	// HTML을 출력 => 오라클에서 받은 결과값을 출력
	out.println("<html>");
	out.println("<head>");
	out.println("<script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=6a1ffbe66e5a8ba9af15d82e0b41ceac&libraries=services\"></script>");
	
	out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
	out.println("<style>");
	out.println(".container{margin-top:50px}"); //브라우저 내에서 간격을 띌 때:margin , div내에서 간격을 띌 때:padding
	out.println(".row{");
	out.println("margin:0px auto;"); //가운데 정렬
	out.println("width:800px}</style>");
	out.println("</head>");
	out.println("<body>");
	out.println("<div class=container>");
	out.println("<div class=row>");
	
	//이미지 다섯개
	out.println("<div>");
	out.println("<div class=row>");
	
	String poster=vo.getPoster();
	poster=poster.replace("#","&");
	StringTokenizer st=new StringTokenizer(poster,"^");
	
	out.println("<table class=table>");
	out.println("<tr>");
	while(st.hasMoreTokens())
	{
		out.println("<td>");
		out.println("<img src="+st.nextToken()+" style=\"width:100%\">");
		out.println("</td>");
	}
	out.println("</tr>");
	out.println("</table>");
	
	//lg, sm, md, xs 
	
	out.println("<div class=col-sm-8>");
	out.println("</div>");
	
	// 상세보기
	out.println("<table class=table>");
	out.println("<tr>");
	out.println("<td>");
	out.println("<h3>"+vo.getName()+"&nbsp;<span style=\"color:orange\">"+vo.getScore()+"</span></h3>");
	out.println("</td>");
	out.println("</tr>");
	out.println("</table>");
	
	out.println("<div style=\"height:20px\"></div>");

	out.println("<table class=table>");
	out.println("<tr>");
	out.println("<th width=20%>주소</th>");
	out.println("<td width=80%>"+addr1+"<br><sub>지번:"+addr2+"<sub>"+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	out.println("<th width=20%>전화</th>");
	out.println("<td width=80%>"+vo.getPhone()+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	out.println("<th width=20%>음식종류</th>");
	out.println("<td width=80%>"+vo.getType()+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	out.println("<th width=20%>가격대</th>");
	out.println("<td width=80%>"+vo.getPrice()+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	out.println("<th width=20%>주차</th>");
	out.println("<td width=80%>"+vo.getParking()+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	out.println("<th width=20%>영업시간</th>");
	out.println("<td width=80%>"+vo.getTime()+"</td>");
	out.println("</tr>");
	
	out.println("<tr>");
	if(!vo.getMenu().equals("no"))
	{ 	
		out.println("<th width=20%>메뉴</th>");
		out.println("<td width=80%>");
		st=new StringTokenizer(vo.getMenu(),"원");
		
		out.println("<ul>");
		while(st.hasMoreTokens())
		{
			out.println("<li>");
			out.println(st.nextToken()+"원");
			out.println("</li>");
		}
		out.println("</ul>");
		out.println("</td>");
		out.println("</tr>");
	}
		
	out.println("<tr>");
	out.println("<td class=text-right colspan=2>");
	out.println("<a href=# class=\"btn btn-xs btn-danger\">예약하기</a>");
	out.println("<a href=# class=\"btn btn-xs btn-success\">찜하기</a>");
	out.println("<a href=# class=\"btn btn-xs btn-warning\">좋아요</a>");
	out.println("<a href=\"FoodListServlet?cno="+vo.getCno()+"\" class=\"btn btn-xs btn-info\">목록</a>");
	out.println("</td>");
	out.println("</tr>");
	
	
	out.println("</table>");
	
	out.println("</div>");
	out.println("<div class=col-sm-4>");
	
	// 지도출력
    out.write("        <div id=\"map\" style=\"width:100%;height:350px;\"></div>\r\n");
    out.write("		<script>\r\n");
    out.write("		var mapContainer = document.getElementById('map'), // 지도를 표시할 div \r\n");
    out.write("		    mapOption = {\r\n");
    out.write("		        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표\r\n");
    out.write("		        level: 3 // 지도의 확대 레벨\r\n");
    out.write("		    };  \r\n");
    out.write("		\r\n");
    out.write("		// 지도를 생성합니다    \r\n");
    out.write("		var map = new kakao.maps.Map(mapContainer, mapOption); \r\n");
    out.write("		\r\n");
    out.write("		// 주소-좌표 변환 객체를 생성합니다\r\n");
    out.write("		var geocoder = new kakao.maps.services.Geocoder();\r\n");
    out.write("		\r\n");
    out.write("		// 주소로 좌표를 검색합니다\r\n");
    out.write("		geocoder.addressSearch('");
    out.write(addr1);
    out.write("', function(result, status) {\r\n");
    out.write("		\r\n");
    out.write("		    // 정상적으로 검색이 완료됐으면 \r\n");
    out.write("		     if (status === kakao.maps.services.Status.OK) {\r\n");
    out.write("		\r\n");
    out.write("		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);\r\n");
    out.write("		\r\n");
    out.write("		        // 결과값으로 받은 위치를 마커로 표시합니다\r\n");
    out.write("		        var marker = new kakao.maps.Marker({\r\n");
    out.write("		            map: map,\r\n");
    out.write("		            position: coords\r\n");
    out.write("		        });\r\n");
    out.write("		\r\n");
    out.write("		        // 인포윈도우로 장소에 대한 설명을 표시합니다\r\n");
    out.write("		        var infowindow = new kakao.maps.InfoWindow({\r\n");
    out.write("		            content: '<div style=\"width:150px;text-align:center;padding:6px 0;\">");
    out.write(vo.getName());
    out.write("</div>'\r\n");
    out.write("		        });\r\n");
    out.write("		        infowindow.open(map, marker);\r\n");
    out.write("		\r\n");
    out.write("		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다\r\n");
    out.write("		        map.setCenter(coords);\r\n");
    out.write("		    } \r\n");
    out.write("		});    \r\n");
    out.write("		</script>\r\n");
		
	out.println("</div>");
	
	out.println("</div>"); // row 닫고
	out.println("</div>"); // container 닫고
	out.println("</body>");
	out.println("</html>");
	
	}
	

}
