<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	request.setCharacterEncoding("utf-8");
	String userId = request.getParameter("userId");


	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String select = "select A.Client_cId, A.Menu_mCode, B.mName, A.tipContent, A.tipAddDay from tip as A inner join menu as B on A.Menu_mCode = B.mCode where A.tipDeleteDay is null and A.Client_cId = '"+userId+"'";

    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(select); //
%>
		{
  			"MypageCart"  : [
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
			"Client_cId" : "<%=rs.getString(1) %>",
			"mCode" : "<%=rs.getString(2) %>",
			"mName" : "<%=rs.getString(3) %>",
			"tipContent" : "<%=rs.getString(4) %>",
			"tipAddDay" : "<%=rs.getString(5) %>"
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
