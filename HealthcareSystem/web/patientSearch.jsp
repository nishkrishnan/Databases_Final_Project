<%-- 
    Document   : patientSearch
    Created on : 21-Mar-2015, 1:39:21 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Search</title>
    </head>
    <body> 
    <center>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>Patient Search</h1>
        <form method="post" action="PatientSearchServlet">
            <p>
            ID: <br/><input type="text" name="patientID" size="20" autofocus><br/>
            Province: <br/><input type="text" name="patientProvince" size="20"><br/>
            City: <br/><input type="text" name="patientCity" size="20">
            <p> 
            <input type="submit">
        </form>
        
            <a href="patientMain.jsp">Go Back</a>
        
        <%--One patient searches for another patient by any combination of alias, province and
city. For each patient returned the interface shows the alias and home address, the number of reviews
written by that patient, and the date of the last review. A link is presented to add the patient as a
friend. --%>
        
        <% } %>
        </center>
    </body>
</html>
