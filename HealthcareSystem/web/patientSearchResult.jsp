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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Search Results</title>
    </head>
    <body>
        
        <%! ArrayList<Patient> patientList;%>
    <% patientList = (ArrayList<Patient>) request.getAttribute("PatientList");%>
    
    <table border=1><tr><th>"Patient Alias"</th><th>"Province"</th><th>"City"</th><th>"Number of Reviews"</th><th>"Last Review"</th><th>Add Friend</th></tr>
            <%
                for (Patient p : patientList) {
            %>
            <tr>
                <td><%= p.alias%></td>
                <td><%= p.province%></td>
                <td><%= p.city%></td>
                <td><%= p.numReviews%></td>
                <td><%= p.dateOfLastReview%></td>
                <% if(p.isFriend) {%>
                 <td>FRIEND</td>
                 <% } else { %>
                 <% %>
                 <td><a href="AddFriendServlet?alias=<%=p.alias%>">Add Friend</a></td>
                 <% } %>
                
            </tr>
            <%
                }
            %>
        </table>
        
        <a href="patientSearch.jsp">return to main page</a>
    </body>
</html>
