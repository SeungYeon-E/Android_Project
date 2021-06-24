<%@page import="java.sql.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
  request.setCharacterEncoding("utf-8");
  String buyNum = request.getParameter("buyNum");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
  String WhereDefault = "SELECT i.iName, i.iCapacity, i.iUnit, b.buyPostNum, b.buyAddress1, b.buyAddress2, b.buyRequests, b.buyDeliveryPrice, count(*) "+
                        "from buy b LEFT join ingredient i on b.Ingredient_iCdoe = i.iCode "+
                        "where b.buyNum = '" + buyNum + "'";

    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"buy_info"  :[
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
			"iName" : "<%=rs.getString(1) %>",
			"iCapacity" : "<%=rs.getString(2) %>",
			"iUnit" : "<%=rs.getString(3) %>",
      "buyPostNum" : "<%=rs.getString(4) %>",
      "buyAddress1" : "<%=rs.getString(5) %>",
      "buyAddress2" : "<%=rs.getString(6) %>",
      "buyRequests" : "<%=rs.getString(7) %>",
      "buyDeliveryPrice" : "<%=rs.getString(8) %>",
      "count" : "<%=rs.getString(9) %>"
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
