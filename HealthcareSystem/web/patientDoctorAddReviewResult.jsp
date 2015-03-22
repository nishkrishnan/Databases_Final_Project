<%-- 
    Document   : patientDoctorAddReviewResult
    Created on : Mar 21, 2015, 11:10:05 PM
    Author     : Mickey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Write Doctor Review </title>
    </head>
    <body>
        <h1>Write Doctor Review</h1>
        
        <%
        if(request.getAttribute("msg") != null)
        {
            String msg = (String) request.getAttribute("msg");
        %>
            <font size="3" color="red">
            <%=msg%>
            </font>
        <%  
        }
        %>
        <br><br>
        
    </body>
</html>
