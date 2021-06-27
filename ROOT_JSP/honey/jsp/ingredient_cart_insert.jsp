<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%

	request.setCharacterEncoding("utf-8");
	String cId = request.getParameter("id");
	int iCode = Integer.parseInt(request.getParameter("iCode"));
	int mCode = Integer.parseInt(request.getParameter("mCode"));

	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();

	    String query = "insert into Cart (Client_cId, Ingredient_iCode, Menu_mCode, cartAddDay, cartEA) value ('" + cId + "', " + iCode + ", " + mCode + ", date_format(now(), '%Y-%m-%d'), 1);";

	    ps = conn_mysql.prepareStatement(query);
	    /* ps.setString(1, name);
	    ps.setString(2, telno);
	    ps.setString(3, group);
		ps.setString(4, memo); */

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
