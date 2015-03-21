<%-- 
    Document   : patientDoctorSearchResult
    Created on : 21-Mar-2015, 3:48:36 PM
    Author     : mfarova
--%>

<%@page import="HealthcareSystem.Doctor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Doctor Search Results</title>
    </head>
    <body>
        <h1>Patient Doctor Search Results</h1>
        
        <!The search should output a list of doctors, and for each doctor the following details: doctor’s name,
        gender, average star rating, and number of reviews. For each doctor there should also be a link to view
        the doctor’s profile.>
        
        
        <%! ArrayList<Doctor> doctors;%>
        <%
            doctors = (ArrayList<Doctor>) request.getAttribute("doctorList");
            for (Doctor doctor : doctors) 
            {
        %>
        
        <%= doctor.firstName%> <%= doctor.lastName%><br>
        
        <%
            }
        %>
        
    </body>
</html>
