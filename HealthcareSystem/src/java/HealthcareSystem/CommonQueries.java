/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mfarova
 */
public class CommonQueries {
    
    
     public static Doctor getDoctorData(Connection con, String docID)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<WorkAddress> ret = null;
        try {
            
            stmt = generateDoctorQuery(con, docID);
            ResultSet resultSet = stmt.executeQuery();
            
            Doctor doctor = new Doctor();
            if(resultSet.next())
            {
                doctor.alias = resultSet.getString("doc_alias");
                doctor.firstName = resultSet.getString("first_name");
                doctor.lastName = resultSet.getString("last_name");
                doctor.gender = resultSet.getString("gender");
                doctor.email = resultSet.getString("email");
                doctor.star_rating = resultSet.getInt("avg_rating");
                doctor.num_reviews = resultSet.getInt("num_reviews");
                doctor.years_licensed = resultSet.getInt("years_licensed");
            }
            
            return doctor;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

     public static ArrayList<Specialization> getSpecializations(Connection con, String docID)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<Specialization> ret = null;
        try {
            
            stmt = generateSpecializationQuery(con, docID);
            ResultSet resultSet = stmt.executeQuery();
            
            ret = new ArrayList<Specialization>();
            while (resultSet.next()) {
                Specialization spec = new Specialization();
                
                spec.spec_name = resultSet.getString("spec_name");
                        
                ret.add(spec);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

     public static ArrayList<Review> getReviews(Connection con, String docID)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<Review> ret = null;
        try {
            
            stmt = generateReviewsQuery(con, docID);
            ResultSet resultSet = stmt.executeQuery();
            
            ret = new ArrayList<Review>();
            while (resultSet.next()) {
                Review review = new Review();
                
                review.review_ID = resultSet.getInt("review_ID");
                review.review_date = resultSet.getDate("review_date");
                review.rating = resultSet.getInt("rating");
                review.text = resultSet.getString("text");
                review.patient_alias = resultSet.getString("pat_alias");
                        
                ret.add(review);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
     
     public static ArrayList<WorkAddress> getWorkAddresses(Connection con, String docID)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<WorkAddress> ret = null;
        try {
            
            stmt = generateWorkAddressQuery(con, docID);
            ResultSet resultSet = stmt.executeQuery();
            
            ret = new ArrayList<WorkAddress>();
            while (resultSet.next()) {
                WorkAddress address = new WorkAddress();
                
                address.street_num = resultSet.getInt("street_num");
                address.street_name = resultSet.getString("street_name");
                address.city = resultSet.getString("city");
                address.province = resultSet.getString("province");
                address.postal_code = resultSet.getString("postal_code");
                        
                ret.add(address);
            }
            return ret;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
     
     
    public static PreparedStatement generateDoctorQuery(Connection con, String doctorID)
    {
        String query = "Select " +
            "doc_alias, " +
            "first_name, " +
            "last_name,  " +
            "gender, " +
            "email, " +
            "(YEAR(CURDATE()) - license_year) as years_licensed, " +
            "(Select avg(rating) from Review Where Review.doc_alias = Doctor.doc_alias) as avg_rating, " +
            "(Select count(*) from Review Where Review.doc_alias = Doctor.doc_alias) as num_reviews " +
            "from Doctor Inner join Person " +
            "on Doctor.doc_alias = Person.person_alias " +
            "Where Doctor.doc_alias = ?";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, doctorID);
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
    
    public static PreparedStatement generateWorkAddressQuery(Connection con, String docID)
    {
        String query = "Select \n" +
            "street_num, \n" +
            "street_name, \n" +
            "city, \n" +
            "province, \n" +
            "postal_code \n" +
            "from doctorAddress inner join workAddress \n" +
            "on doctorAddress.work_ID = workAddress.work_ID \n" +
            "Where doc_alias = ?";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, docID);
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
    public static PreparedStatement generateSpecializationQuery(Connection con, String docID)
    {
        String query = "Select spec_name \n" +
            "from doctorSpec inner join Specialization \n" +
            "on doctorSpec.spec_ID = Specialization.spec_ID \n" +
            "Where doc_alias = ?";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, docID);
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
    public static PreparedStatement generateReviewsQuery(Connection con, String docID)
    {
        String query = "Select \n" +
            "review_ID, " + 
            "review_date, \n" +
            "rating, \n" +
            "text, \n" +
            "pat_alias \n" +
            "from Review \n" +
            "where doc_alias = ? \n" +
            "order by review_date desc";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, docID);
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
     public static ArrayList<WorkAddress> getWorkAddresses(Connection con)
            throws ClassNotFoundException, SQLException {
       
        Statement stmt = null;
        ArrayList<WorkAddress> ret = null;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM workAddress");
            ret = new ArrayList<WorkAddress>();
            while (resultSet.next()) {
                WorkAddress address = new WorkAddress();
                
                address.work_ID = resultSet.getInt("work_ID");
                address.street_num = resultSet.getInt("street_num");
                address.street_name = resultSet.getString("street_name");
                address.city = resultSet.getString("city");
                address.province = resultSet.getString("province");
                address.postal_code = resultSet.getString("postal_code");
                        
                ret.add(address);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
     public static ArrayList<Specialization> getSpecializations(Connection con)
            throws ClassNotFoundException, SQLException {
       
        Statement stmt = null;
        ArrayList<Specialization> ret = null;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Specialization");
            ret = new ArrayList<Specialization>();
            while (resultSet.next()) {
                Specialization spec = new Specialization();
                
                spec.spec_ID = resultSet.getInt("spec_ID");
                spec.spec_name = resultSet.getString("spec_name");
                        
                ret.add(spec);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    public static void AddFriend(Connection con, String alias, HttpServletRequest request)
        throws ClassNotFoundException, SQLException, Exception {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            StringBuilder sb = new StringBuilder();
            String current_user_alias = String.valueOf(request.getSession().getAttribute("patient"));
            sb.append("insert into Friend values('" + current_user_alias
                    + "', '" + alias + "');");
            stmt.execute(sb.toString()); 
        }
        catch(Exception e) {
            throw new SQLException(e);
        }
        finally{
            if (stmt != null)
                stmt.close();
        }
                
    }
     
    public static ArrayList<String> getFriends(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException, Exception {
        Statement stmt = null;
        ArrayList<String> ret = null;
        try {
            stmt = con.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("select distinct w.pat_added_alias from (select b.* from Friend as a\n" +
                    "\n" +
                    "join Friend as b on a.pat_alias = b.pat_added_alias\n" +
                    "\n" +
                    "and (b.pat_alias = a.pat_added_alias AND b.pat_added_alias = a.pat_alias)) as w\n" +
                    "\n" +
                    "where w.pat_alias = '");
            
            String patientName = String.valueOf(request.getSession().getAttribute("patient"));
            sb.append(patientName);
            sb.append("'");
            
            ResultSet resultSet = stmt.executeQuery(sb.toString());
            
            ret = new ArrayList<>();
            while(resultSet.next()) {
                ret.add(resultSet.getString("pat_added_alias"));
            }
            
        
        }
        catch(Exception e) {
            throw new Exception(e);
        }
        finally {
            if (stmt != null)
                stmt.close();
            return ret;
        }
    }
    public static ArrayList<String> getRequestedFriends(Connection con, HttpServletRequest request) 
            throws ClassNotFoundException, SQLException, Exception  {
        
        Statement stmt = null;
        ArrayList<String> ret = null;
        try {
            stmt = con.createStatement();
            StringBuilder sb = new StringBuilder();
            sb.append("select pat_added_alias from "
                    + "(select distinct a.pat_alias, a.pat_added_alias from Friend a \n" 
                    + "where pat_added_alias not in (select b.pat_alias from Friend b "
                    + "where b.pat_added_alias = a.pat_alias)) as c \n"
                    + "where pat_alias = '");
            String patientName = String.valueOf(request.getSession().getAttribute("patient"));
            sb.append(patientName).append("'");
            
            ResultSet resultSet = stmt.executeQuery(sb.toString());
            
            ret = new ArrayList<>();
            while(resultSet.next()) {
                ret.add(resultSet.getString("pat_added_alias"));
            }
            
        
        }
        catch(Exception e) {
            throw new Exception(e);
        }
        finally {
            if (stmt != null)
                stmt.close();
            return ret;
        }
    }
    public static boolean requestedToBeFriend(Connection con, String alias, HttpServletRequest request)
            throws ClassNotFoundException, SQLException, Exception {
        ArrayList<String> requested;
        requested = getRequestedFriends(con, request);
        
        for (String patient: requested) {
            if (patient.equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return false;

    }
    
    public static boolean isFriend(Connection con, String alias, HttpServletRequest request)
            throws ClassNotFoundException, SQLException, Exception {
        ArrayList<String> friends;
        
        friends = getFriends(con, request);
        
        for (String friend: friends) {
            if (friend.equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return false;
    }
     

     public static ArrayList<Patient> getPatients(Connection con,PreparedStatement stmt, HttpServletRequest request)
             throws ClassNotFoundException, SQLException, Exception {
         //PreparedStatement stmt = null;
        ArrayList<Patient> ret = new ArrayList<>();
         try {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Patient p = new Patient();
                
                // This ensures that current user does not appear on the list.
                if (resultSet.getString("pat_alias").equalsIgnoreCase(String.valueOf
                   (request.getSession().getAttribute("patient")))) {
                    continue;
                }
                p.alias = resultSet.getString("pat_alias");
                p.province = resultSet.getString("province");
                p.city = resultSet.getString("city");
                p.requestedToBeFriend = requestedToBeFriend(con, p.alias, request);
                p.isFriend = isFriend(con,p.alias, request);
                p.numReviews = resultSet.getInt("num_reviews");
                p.dateOfLastReview = resultSet.getDate("last_review");
                        
                ret.add(p);
            }
        } 
        catch(Exception e) {
             throw new Exception(e);
        }
             
         finally {
            if (stmt != null) {
                stmt.close();
            }
        }
            return ret;
         
     }
     
    public static boolean isLoginSuccessful(Connection con, HttpServletRequest request)
        throws ClassNotFoundException, SQLException 
    {
        Boolean success = true;
        PreparedStatement stmt = null;
        String msg = "";
        try
        {
            String query = "SELECT CASE " +
                "WHEN EXISTS (" +
                "SELECT * " +
                "FROM Person " +
                "WHERE person_alias = ? " +
                "AND password = SHA2(CONCAT((SELECT salt FROM Person WHERE person_alias = ?), ?), 224) " +
                ") " +
                "THEN 1 " +
                "ELSE 0 " +
                "END AS Success";
            
            stmt = con.prepareStatement(query); 
             
            if(request.getParameter("username").isEmpty())
            {
                msg = "Username is missing";
                throw new Exception();
            }
            stmt.setString(1, request.getParameter("username"));
            stmt.setString(2, request.getParameter("username"));
            
            if(request.getParameter("password").isEmpty())
            {
                msg = "Password is Missing";
                throw new Exception();
            }
            stmt.setString(3, request.getParameter("password"));
            
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                switch (resultSet.getInt("Success"))
                {
                    case 1:
                        success = true;
                        break;
                    case 0:
                        success = false;
                        break;
                }
                return success;
            }
        }
        catch(Exception e)
        {
            return false;
        } 
        finally 
        {
            if(!success)
            {
                msg = "Username and password do not match";
            }
            
            request.setAttribute("msg", msg);
            
            if (stmt != null) {
                stmt.close();
            }
        }
        
        return true;
    }
    
}
