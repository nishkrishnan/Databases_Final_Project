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
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Doctor Search Results</title>
    </head>
    <body> 
    <center>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>Patient Doctor Search Results</h1>
        
        <!The search should output a list of doctors, and for each doctor the following details: doctor’s name,
        gender, average star rating, and number of reviews. For each doctor there should also be a link to view
        the doctor’s profile.>
        
        <table>
            <tr>
                <td><b>Name</b></td>
                <td><b>Gender</b></td>
                <td><b>Average Rating</b></td>
                <td><b># Reviews</b></td>
                <td><b>Link</b></td>
            </tr>
        
        
        <%! ArrayList<Doctor> doctors;%>
        <%
            doctors = (ArrayList<Doctor>) request.getAttribute("doctorList");
            for (Doctor doctor : doctors) 
            {
        %>
            <tr>
                <td><%= doctor.firstName%> <%= doctor.lastName%></td>
                <td><center><%= doctor.gender%></center></td>
                <td><center><%= doctor.star_rating%></center></td>
                <td><center><%= doctor.num_reviews%></center></td>
                <td><center><a href="PatientDoctorProfileServlet?ID=<%= doctor.alias%>">Profile</a></center></td>
            </tr>
        <%
            }
        %>
        
        
        
        </table>
        <br><br><a href="PatientDoctorSearchServlet">Go Back To Search</a>
        
        <% } %>
        
        </center>
    </body>
</html>
