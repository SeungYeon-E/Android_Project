<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String cId = request.getParameter("cId");
	String cPw = request.getParameter("cPw");
	String cName = request.getParameter("cName");
	String cTelno = request.getParameter("cTelno");
              String cEmail = request.getParameter("cEmail");
              String cAddress1 = request.getParameter("cAddress1");
              String cAddress2 = request.getParameter("cAddress2");
              String cPostNum = request.getParameter("cPostNum");	
		
//------
	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into client (cId, cPw, cName, cTelno, cEmail, cAddress1, cAddress2, cPostNum";
	    String B = ") values (?,?,?,?,?,?,?,?)";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, cId);
	    ps.setString(2, cPw);
	    ps.setString(3, cName);
	    ps.setString(4, cTelno);
                  ps.setString(5, cEmail);
                  ps.setString(6, cAddress1);
                  ps.setString(7, cAddress2);
                  ps.setString(8, cPostNum);
		
		result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%		
	    conn_mysql.close();
	} 
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%		
	    e.printStackTrace();
	} 
	
%>

