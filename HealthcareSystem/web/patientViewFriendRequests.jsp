<%-- 
    Document   : patientViewFriendRequests
    Created on : 21-Mar-2015, 1:40:19 PM
    Author     : mfarova
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css" /> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Friend Requests</title>
    </head>
    <body>
    <center>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>View Friend Requests</h1>
        <!A patient views a list of other patients who have added them as a
friend, but whom this patient has not yet added as a friend. For each patient the alias and e-mail are
shown, as well as a link to add that patient as a friend, thus confirming the friendship.!>
        <table>
        <tr>
        <%! ArrayList<String> friendrequests;%>
    <%
         String alias = null;
        friendrequests = (ArrayList<String>) request.getAttribute("friendRequestsList");
        int col_counter = 0;
        for (String friendrequest : friendrequests) {
    %>

    <td><%= friendrequest%></td>
    <% if (col_counter == 1){
        col_counter = 0;    %>
        <td><a href="AcceptFriendRequestServlet?ID=<%= alias%>">Accept</a></td></tr><tr>
    <%
        }
     else {
        alias = friendrequest;
        col_counter++;
    }
    %>
    <%
        }
    %>
    
        </table>

        <br><br><a href="patientMain.jsp">Go Back</a>
        
        <% } %>
        </center>
    </body>
</html>
