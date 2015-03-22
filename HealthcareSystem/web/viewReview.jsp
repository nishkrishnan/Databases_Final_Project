<%-- 
    Document   : patientDoctorReview
    Created on : 21-Mar-2015, 1:43:02 PM
    Author     : mfarova
--%>
<%@page import="HealthcareSystem.Review"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Doctor Review</title>
    </head>
     
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "") &&
                (session.getAttribute("doctor") == null) || (session.getAttribute("doctor") == "")) { %>
        You are not logged in<br/>
        <a href="index.jsp">Please Login</a>
        <% } else { %>
        
            <% Review currentReview = (Review)request.getAttribute("CurrentReview");
               Review lastReview = (Review)request.getAttribute("LastReview");
               Review nextReview = (Review)request.getAttribute("NextReview");
               String docAlias = (String)request.getAttribute("doc");
            %>

            <h1>Review </h1>
            <table>
                <tr>
                    <th>Previous Review </th>
                    <th>Review ID</th>
                    <th>Review Date</th>
                    <th>Rating</th>
                    <th>Text</th>
                    <th>Reviewer</th>
                    <th>Next Review</th>
                </tr>
                <tr>
                    <% if (lastReview != null) { %>
                        <td><a href="ViewReviewServlet?ID=<%= lastReview.review_ID%>&doc=<%=docAlias%>">Previous Review</a></td>
                    <% } else { %>
                        <td>N/A</td>
                    <% } %>
                    <td><%=currentReview.review_ID%></td>
                    <td><%=currentReview.review_date%></td>
                    <td><%=currentReview.rating%></td>
                    <td><%=currentReview.text%></td>
                    <td><%=currentReview.patient_alias%></td>
                     <% if (nextReview != null) { %>
                        <td><a href="ViewReviewServlet?ID=<%= nextReview.review_ID%>&doc=<%=docAlias%>">Next Review</a></td>
                    <% } else { %>
                        <td>N/A</td>
                    <% } %>
                    
                </tr>
            </table> <br>
            
            
            
            
        <!--(accessible from view doctor profile). For one review display the name of the
        doctor and the review details: date, star rating, and free-form comments. There should also be links to
        the previous and next reviews for that doctor in the chronological ordering.--!>
        
        
        
        
        
        

        <% } %>
    
</html>
