<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

 	String query = "select mCode, mCategory, mName, mImagePath from Menu where mCategory = '분식'";
 	int count = 0;

 	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(query); //
    %>
    	{
    	"food"  : [
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
    		"code" : "<%=rs.getString(1) %>",
			"category" : "<%=rs.getString(2) %>",
			"name" : "<%=rs.getString(3) %>",
			"image1" : "<%=rs.getString(4) %>"
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
