<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
  request.setCharacterEncoding("utf-8");
  String buyNum = request.getParameter("buyNum");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
  String WhereDefault = "SELECT i.iName, i.iCapacity, i.iUnit, i.iPrice, m.mName, m.mImagePath, b.buyPostNum, b.buyAddress1, b.buyAddress2, b.buyRequests, b.buyDeliveryPrice, b.buyEA, b.buyDay, b.buyCencelDay "+
                        "FROM buy b, menu m, ingredient i "+
                        "WHERE b.Menu_mCode = m.mCode "+
                        "AND b.Ingredient_iCdoe = i.iCode "+
                        "AND b.buyNum = '" + buyNum + "'";
    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);

%>
		{
  			"paymentDetail_info"  : [
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
			"iPrice" : "<%=rs.getString(4) %>",
			"mName" : "<%=rs.getString(5) %>",
			"mImagePath" : "<%=rs.getString(6) %>",
      "buyPostNum" : "<%=rs.getString(7) %>",
      "buyAddress1" : "<%=rs.getString(8) %>",
      "buyAddress2" : "<%=rs.getString(9) %>",
      "buyRequests" : "<%=rs.getString(10) %>",
      "buyDeliveryPrice" : "<%=rs.getString(11) %>",
      "buyEA" : "<%=rs.getInt(12) %>",
      "buyDay" : "<%=rs.getString(13) %>",
      "buyCencelDay" : "<%=rs.getString(14) %>"
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
