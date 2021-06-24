<%@page import="java.sql.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  request.setCharacterEncoding("utf-8");
  String Client_cId = request.getParameter("Client_cId");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
  String WhereDefault = "SELECT b.buyNum, b.buyDeliveryPrice, b.buyDay, b.buyCencelDay, i.iName, i.iCapacity, i.iUnit, count(*) "+
                        "FROM buy b LEFT JOIN ingredient i ON b.Ingredient_iCdoe = i.iCode "+
                        "WHERE Client_cId = '" + Client_cId + "' " +
                        "GROUP BY b.buyNum " +
                        "ORDER BY b.buyDay DESC, b.buyCencelDay DESC ";

    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"paymentHistory_info"  :[
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
			"buyNum" : "<%=rs.getString(1) %>",
			"buyDeliveryPrice" : "<%=rs.getString(2) %>",
			"buyDay" : "<%=rs.getString(3) %>",
      "buyCencelDay" : "<%=rs.getString(4) %>",
      "iName" : "<%=rs.getString(5) %>",
      "iCapacity" : "<%=rs.getString(6) %>",
      "iUnit" : "<%=rs.getString(7) %>",
      "count" : "<%=rs.getString(8) %>"
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
