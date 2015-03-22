<%-- 
    Document   : doctorProfile
    Created on : 21-Mar-2015, 1:43:32 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Profile</title>
    </head>
    <body>
        
        <%
            if ((session.getAttribute("doctor") == null) || (session.getAttribute("doctor") == "")) {
        %>
        You are not logged in as a Doctor<br/>
        <a href="doctorLogin.jsp">Please Login</a>
        <%} 
            else {
        %>
        
        <h1>Doctor Profile</h1>
        Similar to the view doctor profile feature for patients, but shows full
profile information including e-mail address. The list of reviews for this doctor should also be displayed
with links to individual reviews, as in the interface for patients.
        
<%
            }
        %>
    </body>
</html>
