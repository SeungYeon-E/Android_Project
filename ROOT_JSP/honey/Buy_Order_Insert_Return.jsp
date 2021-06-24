<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%
	request.setCharacterEncoding("utf-8");

	String Client_cId = request.getParameter("Client_cId");
	String buyNum = request.getParameter("buyNum");
  String buyPostNum = request.getParameter("buyPostNum");
  String buyAddress1 = request.getParameter("buyAddress1");
  String buyAddress2 = request.getParameter("buyAddress2");
  String buyRequests = request.getParameter("buyRequests");
  String buyDeliveryPrice = request.getParameter("buyDeliveryPrice");

//------
	String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();

	    // String A = "INSERT INTO buy (Client_cId, Menu_mCode, Ingredient_iCdoe, buyNum, buyEA, buyDay, buyCartCode, buyPostNum, buyAddress1, buyAddress2, buyRequests, buyDeliveryPrice) "+
      //             "SELECT '"+ Client_cId +"', c.Menu_mCode, c.Ingredient_iCode, '"+ buyNum +"', c.cartEA, now(), c.cartCode, '"+ buyPostNum +"', '"+ buyAddress1 +"', '"+ buyAddress2 +"', '"+ buyRequests +"', '"+ buyDeliveryPrice +"' "+
      //             "from cart c LEFT join buy b on c.CartCode = b.buyCartCode "+
      //             "join menu m on c.Menu_mCode = m.mCode "+
      //             "join ingredient i on c.Ingredient_iCode = i.iCode "+
      //             "where c.Client_cId = '" + Client_cId + "' "+
      //             "AND b.buyCartCode is null "+
      //             "AND c.cartDelDay is null";

      String A = "INSERT INTO buy (Client_cId, Menu_mCode, Ingredient_iCdoe, buyNum, buyEA, buyDay, buyCartCode, buyPostNum, buyAddress1, buyAddress2, buyRequests, buyDeliveryPrice) "+
                  "SELECT ?, c.Menu_mCode, c.Ingredient_iCode, ?, c.cartEA, now(), c.cartCode, ?, ?, ?, ?, ? "+
                  "from cart c LEFT join buy b on c.CartCode = b.buyCartCode "+
                  "join menu m on c.Menu_mCode = m.mCode "+
                  "join ingredient i on c.Ingredient_iCode = i.iCode "+
                  "where c.Client_cId = ? "+
                  "AND b.buyCartCode is null "+
                  "AND c.cartDelDay is null";

		ps = conn_mysql.prepareStatement(A);
		ps.setString(1, Client_cId);
		ps.setString(2, buyNum);
		ps.setString(3, buyPostNum);
		ps.setString(4, buyAddress1);
		ps.setString(5, buyAddress2);
    ps.setString(6, buyRequests);
    ps.setString(7, buyDeliveryPrice);
    ps.setString(8, Client_cId);

		result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%
	    conn_mysql.close();
	}
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%
	    e.printStackTrace();
	}

%>
