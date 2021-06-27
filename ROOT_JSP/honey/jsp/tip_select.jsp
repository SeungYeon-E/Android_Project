<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%

	request.setCharacterEncoding("utf-8");
	int mCode = Integer.parseInt(request.getParameter("mCode"));

	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	String query = "select T.tipCode, T.Client_cId, C.cName, T.tipContent from Tip as T" +
" join Client as C on T.Client_cId = C.cId" +
" where Menu_mCode = " + mCode + " and tipDeleteDay is null";
 	int count = 0;

 	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(query); //
    %>
    	{
    	"tip"  : [
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
        "id" : "<%=rs.getString(2) %>",
        "name" : "<%=rs.getString(3) %>",
    		"content" : "<%=rs.getString(4) %>"

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
