<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    
	request.setCharacterEncoding("utf-8");
	String text = request.getParameter("searchVlaue");
	String searchVlaue = null;
	String where = null;
	
	String url_mysql = "jdbc:mysql://localhost/Honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String select = "SELECT A.mCode, A.mCategory, A.mName, A.mImagePath, A.mAddDay, A.mImage2 ";
    String from = " FROM Honey.Menu AS A INNER JOIN Honey.MnA AS B ON A.mCode = B.Menu_mCode ";
    where = " WHERE B.maDeleteDay IS NULL ";
	if(!text.equals("")){
		searchVlaue = request.getParameter("searchVlaue");
        where = " WHERE B.maDeleteDay IS NULL AND (A.mCategory like '%"+searchVlaue+"%' or mName like '%"+searchVlaue+"%') ";
    }
    String orderBy = " ORDER BY (CASE WHEN ASCII(SUBSTRING(A.mName, 1)) BETWEEN 48 AND 57 THEN 3 WHEN ASCII(SUBSTRING(A.mName, 1)) < 128 THEN 2 ELSE 1 END) , A.mName, A.mCode ASC;";
 
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(select + from + where + orderBy); // 
%>
		{ 
  			"kfood"  : [ 
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
			"mCode" : "<%=rs.getString(1) %>", 
			"mCategory" : "<%=rs.getString(2) %>",   
			"mName" : "<%=rs.getString(3) %>",  
			"mImagePath" : "<%=rs.getString(4) %>",
			"mAddDay" : "<%=rs.getString(5) %>",
			"mImage2" : "<%=rs.getString(6) %>"
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
