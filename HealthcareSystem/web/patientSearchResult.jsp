<%-- 
    Document   : patientSearchResult.jsp
    Created on : Mar 21, 2015, 4:20:03 PM
    Author     : Nish
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="HealthcareSystem.Patient"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Search Results</title>
    </head>
    <body> 
    <center>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        
            <h1>Patient Friend Search</h1>
        
            <%! ArrayList<Patient> patientList;%>
            <% patientList = (ArrayList<Patient>) request.getAttribute("PatientList");%>

            <table><tr><th>Patient Alias</th><th>Province</th><th>City</th><th>Number of Reviews</th><th>Last Review</th><th>Add Friend</th></tr>
                <%
                    for (Patient p : patientList) {
                %>
                <tr>
                    <td><center><%= p.alias%></center></td>
                    <td><center><%= p.province%></center></td>
                    <td><center><%= p.city%></center></td>
                    <td><center><%= p.numReviews%></center></td>
                    <td>
                    <center>
                    <% if (p.dateOfLastReview == null) {%>
                        N/A
                    <% } else { %>
                        <%= p.dateOfLastReview%>
                    <% } %>
                    </center>
                    </td>
                    <td>
                    <center>
                    <% if(p.isFriend) {%>
                        Friend
                    <% } else if (p.requestedToBeFriend) {%>
                        Request Sent
                    <% } else {%>
                        <a href="AddFriendServlet?alias=<%=p.alias%>">Add Friend</a>
                     <% } %>
                     </center>
                     </td>
                     
                </tr>
                <%
                    }
                %>
            </table>

            <br><br><a href="patientSearch.jsp">Go Back</a>

        <% } %>
        </center>
    </body>
</html>
