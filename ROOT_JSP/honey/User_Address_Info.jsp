<%@page import="java.sql.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  request.setCharacterEncoding("utf-8");
  String cId = request.getParameter("cId");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
  String WhereDefault = "SELECT cPostNum, cAddress1, cAddress2 FROM client WHERE cId = '" + cId + "'";

    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"userAddress_info"  :[
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            ,
<%
            }
%>
			{
			"cPostNum" : "<%=rs.getString(1) %>",
			"cAddress1" : "<%=rs.getString(2) %>",
			"cAddress2" : "<%=rs.getString(3) %>"
			}

<%
        count++;
        }
%>
		  ]
		}
<%
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
