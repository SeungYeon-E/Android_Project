<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%        
             
	
             
              request.setCharacterEncoding("utf-8");
              String cId_client = request.getParameter("cId");
	String cPw_client = request.getParameter("cPw");
            

      String url_mysql = "jdbc:mysql://localhost/honey?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";


    String WhereDefault ="select cId, cPw from client where cId = '"+cId_client+"' and cPw = '"+cPw_client +"'";
    int count = 0;
    

          
   
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
%>
		{ 
  			"login_info"  : [ 
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
                                          "cPw" : "<%=rs.getString(2) %>"  
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
