<%-- 
    Document   : patientLogin
    Created on : 21-Mar-2015, 1:25:24 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Login</title>
    </head>
    <body>
        <center>
        <h1>Patient Login</h1>
        
        <%
        if(request.getAttribute("msg") != null)
        {
            String msg = (String) request.getAttribute("msg");
        %>
            <font class="error">
            <%=msg%>
            </font>
        <%  
        }
        %>
        
        <form method="post" action="PatientLoginServlet">
            <p>
            ID: <br/><input type="text" name="username" size="20" autofocus><br/>
            Password: <br/><input type="password" name="password" size="20"><br/>
            <p> 
            <input type="submit">
        </form>
        
        
            <a href="index.jsp">Go Back</a>
        </center>
    </body>
</html>
