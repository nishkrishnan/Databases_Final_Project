<%-- 
    Document   : patientDoctorSearch
    Created on : 21-Mar-2015, 1:41:35 PM
    Author     : mfarova
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="HealthcareSystem.WorkAddress"%>
<%@page import="HealthcareSystem.Specialization"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Search</title>
    </head>
    <body>
        <h1>Doctor Search</h1>
    <!Patient searches for doctors by several criteria: doctor profile attributes
    (name or part thereof, gender, work address, specialization, and number of years licensed), average star
    rating greater than a user-specified threshold, whether the doctor has been reviewed by a friend, and
    review keyword (case insensitive). The search should support the conjunction of any subset of these
    criteria. Example queries:
    a) Find a podiatrist in any postal code beginning with N2L who has been reviewed by a friend.
    b) Find an allergologist whose last name contains the substring “Aikenhead”.
    c) Find a female obstetrician in Kitchener who has held a medical license held for at least 10 years.
    d) Find all doctors in Ontario who have an average star rating of at least 2½.
    e) Find Waterloo cardiologists whose review includes the keyword “great”.
    The search should output a list of doctors, and for each doctor the following details: doctor’s name,
    gender, average star rating, and number of reviews. For each doctor there should also be a link to view
    the doctor’s profile.>
    
    <br><br>
    
    <form method="post" action="PatientDoctorSearchResultServlet">
        <p>
        First Name: <input type="text" name="firstName" size="20" autofocus><br/>
        Last Name: <input type="text" name="lastName" size="20"><br/>
        Gender: 
        <select name="gender">
            <option value="none">---</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
        </select><br/>
        Address: 
        <select name="work_ID">
            <option value="none">---</option>
            <%! ArrayList<WorkAddress> workAddresses;%>
            <%
                workAddresses = (ArrayList<WorkAddress>) request.getAttribute("workAddressesList");
                for (WorkAddress address : workAddresses) {
            %>
            <option value="<%= address.work_ID%>"><%=address.street_num%> <%=address.street_name%>, <%=address.city%></option>
            <%
                }
            %>
        </select><br/>
        Specialization: 
        <select name="spec_ID">
            <option value="none">---</option>
            <%! ArrayList<Specialization> specializations;%>
            <%
                specializations = (ArrayList<Specialization>) request.getAttribute("specList");
                for (Specialization spec : specializations) {
            %>
            <option value="<%= spec.spec_ID%>"><%=spec.spec_name%></option>
            <%
                }
            %>
        </select><br/>
        Years Licensed: <input type="yearsLicensed" name="lastName" type="number"><br/>
        
        <p> 
        <input type="submit">
    </form>
    
    </body>
    
</html>