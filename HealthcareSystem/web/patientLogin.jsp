<%-- 
    Document   : patientLogin
    Created on : 21-Mar-2015, 1:25:24 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Login</title>
    </head>
    <body>
        <h1>Patient Login</h1>
        <form method="post" action="PatientLoginServlet">
            <p>
            ID: <input type="text" name="patientID" size="20" autofocus><br/>
            Password: <input type="password" name="patientPassword" size="20" autofocus><br/>
            <p> 
            <input type="submit">
        </form>
    </body>
</html>
