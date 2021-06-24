<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
	request.setCharacterEncoding("utf-8");
	String cartCode = request.getParameter("cartCode");
  String cartEA = request.getParameter("cartEA");

//------
	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력확인

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		  Statement stmt_mysql = conn_mysql.createStatement();

	    String A = "update cart set cartEA = ? where cartCode = ?";

	    ps = conn_mysql.prepareStatement(A);
	    ps.setString(1, cartEA);
      ps.setString(2, cartCode);

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
