<%-- 
    Document   : patientMain
    Created on : 21-Mar-2015, 1:49:07 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Main Page</title>
    </head>
    <body> 
    <center>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>Patient Main Page</h1>
        
        <h2>Please select one of the following:</h2>
        <ul>      
        <li><a href="patientSearch.jsp">Friend Search</a></li>
        <li><a href="ViewFriendRequestsServlet">View Friend Requests</a></li>
        <li><a href="PatientDoctorSearchServlet">Doctor Search</a></li>
        <li><a href="LogoutServlet">Logout</a></li>
        </ul>
    
        <% } %>
        </center>
    </body>
</html>
