<%-- 
    Document   : patientAddFriend
    Created on : 21-Mar-2015, 1:39:49 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Friend</title>
    </head>
    <body>
        <% if ((session.getAttribute("patient") == null) || (session.getAttribute("patient") == "")) { %>
        You are not logged in as a Patient<br/>
        <a href="patientLogin.jsp">Please Login</a>
        <% } else { %>
        
        <h1>Add Friend</h1>
        (accessible form patient search). The application tells the patient whether a
friendship has been formed (i.e., A added B and B added A) or not yet (i.e., A added B but B has not yet
added A).

        <% } %>
    </body>
</html>
