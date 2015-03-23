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
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Doctor Review</title>
    </head>
    <body>
        <center>
        <% 
            boolean patientNotLoggedIn = false;
            boolean doctorNotLoggedIn  = false;
            
            if ((session.getAttribute("patient")== null) || (session.getAttribute("patient") == "")) {
                patientNotLoggedIn = true;
            }
            if ((session.getAttribute("doctor")== null) || (session.getAttribute("doctor") == "")) {
                doctorNotLoggedIn = true;
            }
            if (patientNotLoggedIn && doctorNotLoggedIn) { %>
        You are not logged in<br/>
        <a href="index.jsp">Please Login</a>
        <% } else { %>
        
            <% Review currentReview = (Review)request.getAttribute("CurrentReview");
               Review lastReview = (Review)request.getAttribute("LastReview");
               Review nextReview = (Review)request.getAttribute("NextReview");
               String docAlias = (String)request.getAttribute("doc");
            %>

            <h1>Review </h1>
            <h2>Doctor: <%=docAlias%></h2>
            <table width="500px">
                <tr>
                    <th>Review ID</th>
                    <th>Review Date</th>
                    <th>Rating</th>
                    <th>Reviewer</th>
                </tr>
                <tr>
                    <td><center><%=currentReview.review_ID%></center></td>
                    <td><center><%=currentReview.review_date%></center></td>
                    <td><center><%=currentReview.rating%></center></td>
                    <td><center><%=currentReview.patient_alias%></center></td>
                </tr>
                <tr>
                    <td colspan="4"><%=currentReview.text%></td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table width="100%">
                            <tr>
                                <% if (lastReview != null) { %>
                                <td width="50%">
                                    <center>
                                        <a href="ViewReviewServlet?ID=<%= lastReview.review_ID%>&doc=<%=docAlias%>"><< Previous Review</a>
                                    </center>
                                </td>
                                <% } else { %>
                                    <td width="50%">
                                        <center>
                                            ---
                                        </center>
                                    </td>
                                <% } %>

                                <% if (nextReview != null) { %>
                                    <td width="50%">
                                        <center>
                                            <a href="ViewReviewServlet?ID=<%= nextReview.review_ID%>&doc=<%=docAlias%>">Next Review >></a>
                                        </center>
                                    </td>
                                <% } else { %>
                                    <td width="50%">
                                        <center>
                                            ---
                                        </center>
                                    </td>
                                <% } %>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table> 
                    
                <br><br>
                
        <% if (patientNotLoggedIn) {%>
            <form method="post" action="DoctorProfileServlet">
                <input type="submit" value="Back to Profile">
                <!--input action="action" type="button" value="Go Back"/-->
            </form>
        <% } else { %>
            <form method="post" action="PatientDoctorProfileServlet?ID=<%=docAlias%>">
                <input type="submit" value="Back to Profile">
                <!--input action="action" type="button" value="Go Back"/-->
            </form>
        <% }%>
        
        <!--(accessible from view doctor profile). For one review display the name of the
        doctor and the review details: date, star rating, and free-form comments. There should also be links to
        the previous and next reviews for that doctor in the chronological ordering.-->
        
        <% } %>
        </center>
    </body>
</html>
