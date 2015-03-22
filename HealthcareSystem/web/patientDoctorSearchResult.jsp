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
        
        <table>
            <tr>
                <td>Name</td>
                <td>Gender</td>
                <td>Average Rating</td>
                <td># Reviews</td>
                <td>Link</td>
            </tr>
        
        
        <%! ArrayList<Doctor> doctors;%>
        <%
            doctors = (ArrayList<Doctor>) request.getAttribute("doctorList");
            for (Doctor doctor : doctors) 
            {
        %>
            <tr>
                <td><%= doctor.firstName%> <%= doctor.lastName%></td>
                <td><%= doctor.gender%></td>
                <td><%= doctor.star_rating%></td>
                <td><%= doctor.num_reviews%></td>
                <td><a href="PatientDoctorProfileServlet?ID=<%= doctor.alias%>">Profile</a></td>
            </tr>
        <%
            }
        %>
        
        </table>
    </body>
</html>
