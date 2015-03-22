/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Mickey
 */
public class patientDoctorProfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/patientMain.jsp";
        try 
        {
            InitialContext cxt = new InitialContext();
            if (cxt == null) 
            {
                throw new RuntimeException("Unable to create naming context!");
            }
            Context dbContext = (Context) cxt.lookup("java:comp/env");
            DataSource ds = (DataSource) dbContext.lookup("jdbc/myDatasource");
            if (ds == null) {
                throw new RuntimeException("Data source not found!");
            }
            Connection con = ds.getConnection();

            Doctor doctorData = getDoctorData(con, request);
            request.setAttribute("doctorData", doctorData);
            
            ArrayList workAddresses = getWorkAddresses(con, request);
            request.setAttribute("workAddresses", workAddresses);
            
            ArrayList specializations = getSpecializations(con, request);
            request.setAttribute("specializations", specializations);
            
            ArrayList reviews = getReviews(con, request);
            request.setAttribute("reviews", reviews);
            
            con.close();
            
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
    }

     public static Doctor getDoctorData(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<WorkAddress> ret = null;
        try {
            
            stmt = generateDoctorQuery(con, request);
            ResultSet resultSet = stmt.executeQuery();
            
            Doctor doctor = new Doctor();
            
            doctor.firstName = resultSet.getString("first_name");
            doctor.lastName = resultSet.getString("last_name");
            doctor.gender = resultSet.getString("gender");
            doctor.star_rating = resultSet.getInt("avg_rating");
            doctor.num_reviews = resultSet.getInt("num_reviews");

            return doctor;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

     public static ArrayList<Specialization> getSpecializations(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<Specialization> ret = null;
        try {
            
            stmt = generateSpecializationQuery(con, request);
            ResultSet resultSet = stmt.executeQuery();
            
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

     public static ArrayList<Review> getReviews(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<Review> ret = null;
        try {
            
            stmt = generateReviewsQuery(con, request);
            ResultSet resultSet = stmt.executeQuery();
            
            ret = new ArrayList<Review>();
            while (resultSet.next()) {
                Review review = new Review();
                
                review.review_date = resultSet.getDate("review_date");
                review.rating = resultSet.getInt("rating");
                review.text = resultSet.getString("text");
                review.patient_alias = resultSet.getString("patient_alias");
                        
                ret.add(review);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
     
     public static ArrayList<WorkAddress> getWorkAddresses(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<WorkAddress> ret = null;
        try {
            
            stmt = generateWorkAddressQuery(con, request);
            ResultSet resultSet = stmt.executeQuery();
            
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
     
     
    public static PreparedStatement generateDoctorQuery(Connection con, HttpServletRequest request)
    {
        String query = "Select \n" +
            "first_name, \n" +
            "last_name,  \n" +
            "gender, \n" +
            "(YEAR(CURDATE()) - license_year) as years_licensed, \n" +
            "(Select avg(rating) from Review Where Review.doc_alias = Doctor.doc_alias) as avg_rating,\n" +
            "(Select count(*) from Review Where Review.doc_alias = Doctor.doc_alias) as num_reviews \n" +
            "from Doctor Inner join Person \n" +
            "on Doctor.doc_alias = Person.person_alias \n" +
            "Where Doctor.doc_alias = ?";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, request.getParameter("ID"));
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
    
    public static PreparedStatement generateWorkAddressQuery(Connection con, HttpServletRequest request)
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
            stmt.setString(1, request.getParameter("ID"));
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
    public static PreparedStatement generateSpecializationQuery(Connection con, HttpServletRequest request)
    {
        String query = "Select spec_name \n" +
            "from doctorSpec inner join Specialization \n" +
            "on doctorSpec.spec_ID = Specialization.spec_ID \n" +
            "Where doc_alias = ?";
        
        PreparedStatement stmt = null;
        try
        {
            stmt = con.prepareStatement(query); 
            stmt.setString(1, request.getParameter("ID"));
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
    public static PreparedStatement generateReviewsQuery(Connection con, HttpServletRequest request)
    {
        String query = "Select \n" +
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
            stmt.setString(1, request.getParameter("ID"));
        }
        catch(Exception e)
        {
        }
        
        return stmt;
    }
     
     
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
