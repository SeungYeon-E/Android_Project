<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String cPw = request.getParameter("userPw");
	String cName = request.getParameter("userName");
	String cTelno = request.getParameter("userTel");
	String cPostNum = request.getParameter("userPostNum");
	String cAddress1 = request.getParameter("userAddress1");	
	String cAddress2 = request.getParameter("userAddress2");
	String cEmail = request.getParameter("userEmail");
	String cId = request.getParameter("userId");
		
//------
	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "UPDATE `Honey`.`Client` SET `cPw` = ?, `cName` = ?, `cTelno` = ?, `cPostNum` = ?, `cAddress1` = ?, `cAddress2` = ?, `cEmail` = ? WHERE (`cId` = ?);";
 
	    ps = conn_mysql.prepareStatement(A);
	    ps.setString(1, cPw);
	    ps.setString(2, cName);
	    ps.setString(3, cTelno);
		ps.setString(4, cPostNum);
	    ps.setString(5, cAddress1);
	    ps.setString(6, cAddress2);
	    ps.setString(7, cEmail);
	    ps.setString(8, cId);
		
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

