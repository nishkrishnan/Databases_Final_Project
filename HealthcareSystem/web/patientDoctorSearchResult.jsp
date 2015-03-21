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
        
        <%! ArrayList<Doctor> doctors;%>
        <%
            doctors = (ArrayList<Doctor>) request.getAttribute("doctorList");
            for (Doctor doctor : doctors) {
        %>
        
        <%= doctor.license_year%>
        
    <%
            }
        %>
        
    </body>
</html>
