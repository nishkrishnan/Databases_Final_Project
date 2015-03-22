<%-- 
    Document   : patientViewFriendRequests
    Created on : 21-Mar-2015, 1:40:19 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Friend Requests</title>
    </head>
    <body> 
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>View Friend Requests</h1>
        A patient views a list of other patients who have added them as a
friend, but whom this patient has not yet added as a friend. For each patient the alias and e-mail are
shown, as well as a link to add that patient as a friend, thus confirming the friendship.

        <%! ArrayList<String> friendrequests;%>
    <%
        friendrequests = (ArrayList<String>) request.getAttribute("friendRequestsList");
        int row_counter = 0;
        for (String friendrequest : friendrequests) {

    %>

    <%= friendrequest%>
    <% if (row_counter == 2){
        row_counter = 0;
    %>
    <br>
    <%
        row_counter++;
        }
    %>
    <%
        }
    %>
    

        <% } %>
    </body>
</html>
