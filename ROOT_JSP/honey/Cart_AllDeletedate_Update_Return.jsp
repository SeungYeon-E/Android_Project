<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
  request.setCharacterEncoding("utf-8");
  String Client_cId = request.getParameter("Client_cId");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력확인

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		  Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "UPDATE cart c LEFT JOIN buy b ON c.cartCode = b.buyCartCode SET cartDelDay = now() WHERE c.Client_cId = ? AND c.cartDelDay is null AND b.buyCartCode is null";

      ps = conn_mysql.prepareStatement(A);
      ps.setString(1, Client_cId);

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
