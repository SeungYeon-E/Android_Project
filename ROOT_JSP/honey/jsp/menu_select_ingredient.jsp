<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
 	int code = Integer.parseInt(request.getParameter("code"));

 	// PreparedStatement ps = null;
 	String query = "select iCode, iName, iCapacity, iUnit, iPrice from Ingredient where mCode = " + code;

 	int count = 0;

 	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();


        // ps = conn_mysql.prepareStatement(query);

	    // ps.setInt(1, code);

        ResultSet rs = stmt_mysql.executeQuery(query); //
    %>
    	{
    	"ingredient"  : [
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
    		"name" : "<%=rs.getString(2) %>",
			"capacity" : "<%=rs.getString(3) %>",
			"unit" : "<%=rs.getString(4) %>",
			"price" : "<%=rs.getString(5) %>"
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
