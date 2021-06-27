<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%        
             
	
             
              request.setCharacterEncoding("utf-8");
              String cId_client = request.getParameter("cId");
            

      String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";


    String WhereDefault ="select cId, cPw, cName, cTelno from client where cId = '"+cId_client +"'";
    int count = 0;
    

          
   
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"pw_find_info"  : [ 
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
			"cId" : "<%=rs.getString(1) %>",
                                          "cPw" : "<%=rs.getString(2) %>",
                                          "cName" : "<%=rs.getString(3) %>",
                                          "cTelno" : "<%=rs.getString(4) %>"  
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
