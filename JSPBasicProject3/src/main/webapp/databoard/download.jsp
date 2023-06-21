<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*,java.net.*,com.sist.dao.*"%>
<%
	/*
		%E%EDF => 디코딩 시 방식
		  = POST : setCharacterEncoding("UTF-8")
		  = GET : window10은 자동처리가 됨. 
	*/
	String fno=request.getParameter("fno");
	//System.out.println(fno);
	
	try
	{
		File file=new File("c:\\download\\"+fno);
		response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fno,"UTF-8"));
		response.setContentLength((int)file.length());
		BufferedInputStream bis= new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos= new BufferedOutputStream(response.getOutputStream());
		
		int i=0;
		byte[] buffer=new byte[1024];
		while((i=bis.read(buffer,0,1024))!=-1)
		{
			bos.write(buffer,0,i);
		}
			bis.close();
			bos.close();
			
			out.clear();
			pageContext.pushBody();
			
	}catch(Exception ex){}
	
%>