<%-- 
    Document   : doctorProfile
    Created on : 21-Mar-2015, 1:43:32 PM
    Author     : mfarova
--%>

<%@page import="HealthcareSystem.Review"%>
<%@page import="HealthcareSystem.Specialization"%>
<%@page import="HealthcareSystem.Doctor"%>
<%@page import="HealthcareSystem.WorkAddress"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Profile</title>
    </head>
    <body>
    <center>
        
        <%
            if ((session.getAttribute("doctor") == null) || (session.getAttribute("doctor") == "")) {
        %>
        You are not logged in as a Doctor<br/>
        <a href="doctorLogin.jsp">Please Login</a>
        <%} 
            else {
        %>
        
        <h1>Doctor Profile</h1>
        <!Similar to the view doctor profile feature for patients, but shows full
        profile information including e-mail address. The list of reviews for this doctor should also be displayed
        with links to individual reviews, as in the interface for patients.>
        
        
        <a href="LogoutServlet">Logout</a>
        
        <% Doctor doctorData = (Doctor)request.getAttribute("doctorData"); %>
        
        
        <h2>Doctor Information</h2>
        <table>
            <tr>
                <td><b>Name</b></td>
                <td><b>Gender</b></td>
                <td><b>Email</b></td>
                <td><b>Average Rating</b></td>
                <td><b># Reviews</b></td>
                <td><b>Years Licensed</b></td>
                
            </tr>
            <tr>
                <td><center><%= doctorData.firstName%> <%= doctorData.lastName%></center></td>
                <td><center><%= doctorData.gender%></center></td>
                <td><center><%= doctorData.email%></center></td>
                <td><center><%= doctorData.star_rating%></center></td>
                <td><center><%= doctorData.num_reviews%></center></td>
                <td><center><%= doctorData.years_licensed%></center></td>
            </tr>
        </table>
            
        <h2>Specializations</h2>
        <table>
        <%! ArrayList<Specialization> specializations;%>
        <%
            specializations = (ArrayList<Specialization>) request.getAttribute("specializations");
            for (Specialization spec : specializations) 
            {
        %>
            <tr>
                <td><center><%= spec.spec_name%></center></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
        <h2>Work Addresses</h2>
        <table>
            <tr>
                <td><b>Street</b></td>
                <td><b>City</b></td>
                <td><b>Province</b></td>
                <td><b>Postal Code</b></td>
            </tr>
        
        <%! ArrayList<WorkAddress> workAddresses;%>
        <%
            workAddresses = (ArrayList<WorkAddress>) request.getAttribute("workAddresses");
            for (WorkAddress address : workAddresses) 
            {
        %>
            <tr>
                <td><center><%= address.street_num%> <%= address.street_name%></center></td>
                <td><center><%= address.city%></center></td>
                <td><center><%= address.province%></center></td>
                <td><center><%= address.postal_code%></center></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
        <h2>Reviews</h2>
        <table>
            <tr>
                <td><b>Date</b></td>
                <td><b>Link</b></td>
            </tr>
        
        <%! ArrayList<Review> reviews;%>
        <%
            reviews = (ArrayList<Review>) request.getAttribute("reviews");
            for (Review review : reviews) 
            {
        %>
            <tr>
                <td><center><%= review.review_date.toString()%></center></td>
                <td><center><a href="ViewReviewServlet?ID=<%= review.review_ID%>&doc=<%=doctorData.alias%>">View Review</a></center></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
        <br><br>
        
<%
            }
        %>
        </center>
    </body>
</html>
