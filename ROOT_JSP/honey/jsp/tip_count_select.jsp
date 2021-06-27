<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    request.setCharacterEncoding("utf-8");
	int mCode = Integer.parseInt(request.getParameter("code"));

    String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

 	String query = "select count(tipCode) from Tip where Menu_mCode =" + mCode + " and tipDeleteDay is null";
 	int count = 0;

 	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(query); //
    %>

    	{
    	"tipcount"  : [
    <%
    	if (rs.next()) {
            if (count == 0) {

            }else{
   	%>
   			,
   	<%
   			}
    %>
    	{
    		"count" : "<%=rs.getString(1) %>"

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
