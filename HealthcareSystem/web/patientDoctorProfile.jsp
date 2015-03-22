<%-- 
    Document   : patientDoctorProfile
    Created on : 21-Mar-2015, 1:42:24 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Doctor Profile</title>
    </head>
    <body> 
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>View Doctor Profile</h1>
        <!(accessible from doctor search). For one doctor display profile information
        (name, gender, work address(es), area(s) of specialization, and number of years licensed), the average
        star rating, the number of reviews, as well as a list of links to the individual reviews in reverse
        chronological order (i.e., most recent reviews on top). There should also be a link to write a new review.>
        
        
        <% Doctor doctorData = (Doctor)request.getAttribute("doctorData"); %>
        
        <table>
            <tr>
                <td>Name</td>
                <td>Gender</td>
                <td>Average Rating</td>
                <td># Reviews</td>
                <td>Years Licensed</td>
            </tr>
            <tr>
                <td><%= doctorData.firstName%> <%= doctorData.lastName%></td>
                <td><%= doctorData.gender%></td>
                <td><%= doctorData.star_rating%></td>
                <td><%= doctorData.num_reviews%></td>
                <td><%= doctorData.years_licensed%></td>
            </tr>
        </table>
        
            
        <br><br>
        
        <table>
            <tr>
                <td>Specializations</td>
            </tr>
        
        <%! ArrayList<Specialization> specializations;%>
        <%
            specializations = (ArrayList<Specialization>) request.getAttribute("specializations");
            for (Specialization spec : specializations) 
            {
        %>
            <tr>
                <td><%= spec.spec_name%></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
            
        <br><br>
        
        <table>
            <tr>
                <td>Street</td>
                <td>City</td>
                <td>Province</td>
                <td>Postal Code</td>
            </tr>
        
        <%! ArrayList<WorkAddress> workAddresses;%>
        <%
            workAddresses = (ArrayList<WorkAddress>) request.getAttribute("workAddresses");
            for (WorkAddress address : workAddresses) 
            {
        %>
            <tr>
                <td><%= address.street_num%> <%= address.street_name%></td>
                <td><%= address.city%></td>
                <td><%= address.province%></td>
                <td><%= address.postal_code%></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
        <br><br>
        
        <table>
            <tr>
                <td>Date</td>
                <td>Link</td>
            </tr>
        
            
        <a href="patientDoctorAddReview.jsp?ID=<%= doctorData.alias%>">Add Review</a>
        <%! ArrayList<Review> reviews;%>
        <%
            reviews = (ArrayList<Review>) request.getAttribute("reviews");
            for (Review review : reviews) 
            {
        %>
            <tr>
                <td><%= review.review_date.toString()%></td>
                <td><a href="ViewReviewServlet?ID=<%= review.review_ID%>">View Review</a></td>
            </tr>
        <%
            }
        %>
        
        </table>
        
        <br><br>
        
        <% } %>
    </body>
</html>
