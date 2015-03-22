<%-- 
    Document   : patientDoctorReview
    Created on : 21-Mar-2015, 1:43:02 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Doctor Review</title>
    </head>
    <body> 
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>View Doctor Review</h1>
        (accessible from view doctor profile). For one review display the name of the
doctor and the review details: date, star rating, and free-form comments. There should also be links to
the previous and next reviews for that doctor in the chronological ordering.

        <% } %>
    </body>
</html>
