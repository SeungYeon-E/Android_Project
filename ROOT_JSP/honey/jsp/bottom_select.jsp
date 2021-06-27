<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    request.setCharacterEncoding("utf-8");
	String cId = request.getParameter("cId");

    String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

 	String query = "select C.cName, B.Menu_mCode, M.mName, B.buyDay from Client as C join Buy as B on C.cId = B.Client_cId" +
" join Menu as M on B.Menu_mCode = M.mCode" +
" where B.Client_cId = '" + cId + "' order by B.buyDay desc limit 1";
 	 int count = 0;

 	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(query); //
    %>

    	{
    	"bottomsheet"  : [
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
    		"name" : "<%=rs.getString(1) %>",
			"code" : "<%=rs.getString(2) %>",
      "mname" : "<%=rs.getString(3) %>",
			"buyday" : "<%=rs.getString(4) %>"

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
