<%-- 
    Document   : patientDoctorAddReview
    Created on : 21-Mar-2015, 1:44:38 PM
    Author     : mfarova
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Write Doctor Review</title>
    </head>
    <body>
        <h1>Write Doctor Review</h1>
        <!(accessible from view doctor profile). The patient enters a star rating and freeform
        text comments. The application should compute the date of the review internally. Once the user
        submits the review the interface should return to the doctor’s profile, where the average star rating and
        number of reviews should reflect the new review.>
         
        <% String docID = (String) request.getParameter("ID"); %>
        
        <form method="post" action="PatientDoctorAddReviewResultServlet">
            <input type="hidden" name="doctor_ID" value="<%=docID%>"><br/><br>
            Rating (0-5): <input type="numeric" min="0" max="5" step="0.5" name="rating" size="5" autofocus><br/>
            Text: <input type="text" name="text" size="20"><br/>
            <input type="submit">
        </form>
        
    </body>
</html>
