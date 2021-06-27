<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String searchVlaue = request.getParameter("userId");

	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String select1 = "SELECT CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cId from Client where cId = '"+searchVlaue+"') end as cId,";
String select2 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cPw from Client where cId = '"+searchVlaue+"') end as cPw, ";
String select3 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cName from Client where cId = '"+searchVlaue+"') end as cName,  ";
String select4 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cTelno from Client where cId = '"+searchVlaue+"') end as cTelno, ";
String select5 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cPostNum from Client where cId = '"+searchVlaue+"') end as cPostNum, ";  
String select6 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cAddress1 from Client where cId = '"+searchVlaue+"') end as cAddress1, ";
String select7 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cAddress2 from Client where cId = '"+searchVlaue+"') end as cAddress2, ";
String select8 = "CASE WHEN (count(c.Client_cId)=0 or count(c.Client_cId)!=0) THEN (select cEmail from Client where cId = '"+searchVlaue+"') end as cEmail, count(c.Client_cId) ";
String select9 = "FROM cart c LEFT JOIN buy b ON c.CartCode = b.buyCartCode JOIN menu m ON c.Menu_mCode = m.mCode JOIN ingredient i ON c.Ingredient_iCode = i.iCode JOIN Client AS A ON A.cId = c.client_cId WHERE c.Client_cId = '"+searchVlaue+"' AND b.buyCartCode IS NULL AND c.cartDelDay IS NULL";
    int count = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(select1+select2+select3+select4+select5+select6+select7+select8+select9); //
%>
		{
  			"myPage"  : [
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
			"userId" : "<%=rs.getString(1) %>",
			"userPw" : "<%=rs.getString(2) %>",
			"userName" : "<%=rs.getString(3) %>",
			"userTel" : "<%=rs.getString(4) %>",
			"userPostNum" : "<%=rs.getString(5) %>",
			"userAddress1" : "<%=rs.getString(6) %>",
			"userAddress2" : "<%=rs.getString(7) %>",
			"userEmail" : "<%=rs.getString(8) %>",
			"cartCount" : "장바구니 <%=rs.getString(9) %>건"
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
