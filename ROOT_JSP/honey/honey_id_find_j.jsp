<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%        
             
	
             
              request.setCharacterEncoding("utf-8");
              String cTelno_client = request.getParameter("cTelno");
            

      String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";


    String WhereDefault ="select cId, cName, cTelno from client where cTelno = '"+cTelno_client +"'";
    int count = 0;
    

          
   
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"id_find_info"  : [ 
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
                                          "cName" : "<%=rs.getString(2) %>",
                                          "cTelno" : "<%=rs.getString(3) %>"  
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
