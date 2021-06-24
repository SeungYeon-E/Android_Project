<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
  request.setCharacterEncoding("utf-8");
  String Client_cId = request.getParameter("cId");

	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
  String WhereDefault = "SELECT c.cartCode, c.cartEA, i.iName, i.iCapacity, i.iUnit, i.iPrice, m.mName, m.mImagePath "+
                        "from cart c LEFT join buy b on c.CartCode = b.buyCartCode "+
                        "join menu m on c.Menu_mCode = m.mCode "+
                        "join ingredient i on c.Ingredient_iCode = i.iCode "+
                        "where c.Client_cId = '" + Client_cId + "' "+
                        "AND b.buyCartCode is null "+
                        "AND c.cartDelDay is null";
    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
%>
		{
  			"carts_info"  : [
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
			"cartCode" : "<%=rs.getInt(1) %>",
			"cartEA" : "<%=rs.getInt(2) %>",
			"iName" : "<%=rs.getString(3) %>",
			"iCapacity" : "<%=rs.getString(4) %>",
			"iUnit" : "<%=rs.getString(5) %>",
			"iPrice" : "<%=rs.getString(6) %>",
      "mName" : "<%=rs.getString(7) %>",
      "mImagePath" : "<%=rs.getString(8) %>"
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
