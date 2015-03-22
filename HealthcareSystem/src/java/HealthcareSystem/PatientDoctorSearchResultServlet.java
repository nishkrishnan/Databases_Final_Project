/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author mfarova
 */
public class PatientDoctorSearchResultServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = "/patientDoctorSearchResult.jsp";
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

            ArrayList doctors = getDoctors(con, request);
            request.setAttribute("doctorList", doctors);
            
            con.close();
            
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
    }

    public static ArrayList<Doctor> getDoctors(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {
       
        PreparedStatement stmt = null;
        ArrayList<Doctor> ret = null;
        try {
            
            stmt = generateQuery(con, request);
            ResultSet resultSet = stmt.executeQuery();
            
            ret = new ArrayList<Doctor>();
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                
                doctor.alias = resultSet.getString("doc_alias");
                doctor.firstName = resultSet.getString("first_name");
                doctor.lastName = resultSet.getString("last_name");
                doctor.gender = resultSet.getString("gender");
                doctor.star_rating = resultSet.getInt("avg_rating");
                doctor.num_reviews = resultSet.getInt("num_reviews");
                     
                ret.add(doctor);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    public static PreparedStatement generateQuery(Connection con, HttpServletRequest request)
    {
        boolean allEmpty = true;
        
        boolean addFirstName = false;
        boolean addLastName = false;
        boolean addYearsLicensed = false;
        boolean addStarRating = false;
        boolean addWorkAddress = false;
        boolean addSpecialization = false;
        boolean addReviewedByFriend = false;
        boolean addReviewKeyword = false;
        
        String query = "Select \n" +
            "Doctor.doc_alias, \n" +
            "first_name, \n" +
            "last_name,  \n" +
            "gender, \n" +
            "(Select avg(rating) from Review Where Review.doc_alias = Doctor.doc_alias) as avg_rating,\n" +
            "(Select count(*) from Review Where Review.doc_alias = Doctor.doc_alias) as num_reviews\n" +
            "from Doctor Inner join Person \n" +
            "on Doctor.doc_alias = Person.person_alias\n" +
            "Where ";
        
        if(!request.getParameter("firstName").isEmpty())
        {
            query += "first_name like ?";
            allEmpty = false;
            addFirstName = true;
        }
        
        if(!request.getParameter("lastName").isEmpty())
        {
            if(!allEmpty)
            {
                query += " AND ";
            }
            query += "last_name like ?";
            allEmpty = false;
            addLastName = true;
        }
        
        if(!request.getParameter("gender").equalsIgnoreCase("none"))
        {
            if(!allEmpty) {
                query += " AND ";
            }
            
            query += "gender = "; 
            if(request.getParameter("gender").equalsIgnoreCase("male")){
                query += "'M'"; 
            } else {
                query += "'F'"; 
            }
            allEmpty = false;
        }
        
        int years_licensed = 0;
        if(!request.getParameter("years_licensed").isEmpty())
        {
            try
            {
                years_licensed = Integer.parseInt(request.getParameter("years_licensed"));
            
                if(!allEmpty)
                {
                    query += " AND ";
                }
                query += "(YEAR(CURRENT_TIMESTAMP) - license_year) = ? ";
                allEmpty = false;
                addYearsLicensed = true;
            }
            catch(Exception e)
            {
                addYearsLicensed = false;
            }
        }
        
        double star_rating = 0.0;
        if(!request.getParameter("star_rating").isEmpty())
        {
            try
            {
                star_rating = Double.parseDouble(request.getParameter("star_rating"));
            
                if(!allEmpty)
                {
                    query += " AND ";
                }
                query += "(Select avg(rating) from Review Where Review.doc_alias = Doctor.doc_alias) > ? ";
                allEmpty = false;
                addStarRating = true;
            }
            catch(Exception e)
            {
                addStarRating = false;
            }
        }
        
        int work_ID = 0;
        if(!request.getParameter("work_ID").equalsIgnoreCase("none"))
        {
            try
            {
                work_ID = Integer.parseInt(request.getParameter("work_ID"));
            
                if(!allEmpty)
                {
                    query += " AND ";
                }
                query += "exists " +
                        "( " +
                        "Select * " +
                        "from doctorAddress inner join workAddress " +
                        "on doctorAddress.work_ID = workAddress.work_ID " +
                        "Where workAddress.work_ID = ? " +
                        "AND doctorAddress.doc_alias = Doctor.doc_alias " +
                        ") ";
                allEmpty = false;
                addWorkAddress = true;
            }
            catch(Exception e)
            {
                addWorkAddress = false;
            }
        }
        
        
        int spec_ID = 0;
        if(!request.getParameter("spec_ID").equalsIgnoreCase("none"))
        {
            try
            {
                spec_ID = Integer.parseInt(request.getParameter("spec_ID"));
            
                if(!allEmpty)
                {
                    query += " AND ";
                }
                query += "exists " +
                    "( " +
                    "Select * " +
                    "from doctorSpec inner join Specialization " +
                    "on doctorSpec.spec_ID = Specialization.spec_ID " +
                    "Where Specialization.spec_ID = ? " +
                    "AND doctorSpec.doc_alias = Doctor.doc_alias " +
                    ")";
                allEmpty = false;
                addSpecialization = true;
            }
            catch(Exception e)
            {
                addSpecialization = false;
            }
        }
        
        
        if(request.getParameter("reviewed_by_friend") != null)
        {
            if(!allEmpty)
            {
                query += " AND ";
            }
            query += "exists  " +
                "( " +
                "select " +
                "distinct w.pat_added_alias, Review.doc_alias " +
                "from (select b.* from Friend as a " +
                "join Friend as b on a.pat_alias = b.pat_added_alias " +
                "and (b.pat_alias = a.pat_added_alias AND b.pat_added_alias = a.pat_alias)) as w " +
                "inner join Review on pat_added_alias = Review.pat_alias " +
                "where w.pat_alias = ? and Review.doc_alias = Doctor.doc_alias " +
                ") ";
            allEmpty = false;
            addReviewedByFriend = true;
        }
        
        if(!request.getParameter("reviewKeyword").isEmpty())
        {
            query += "exists " +
                "( " +
                "Select * " +
                "from Review " +
                "Where Review.doc_alias = Doctor.doc_alias " +
                "And LOWER(text) like LOWER( ? ) " +
                ")";
            allEmpty = false;
            addReviewKeyword = true;
        }
        
        
        if(allEmpty)
        {
            query += "1=1";
        }
        
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query); 
            
            int num = 0;
            if(addFirstName)
            {
                stmt.setString(++num, "%"+request.getParameter("firstName")+"%");
            }
            if(addLastName)
            {
                stmt.setString(++num, "%"+request.getParameter("lastName")+"%");
            }
            if(addYearsLicensed)
            {
                stmt.setInt(++num, years_licensed);
            }
            if(addStarRating)
            {
                stmt.setDouble(++num, star_rating);
            }
            if(addWorkAddress)
            {
                stmt.setInt(++num, work_ID);
            }
            if(addSpecialization)
            {
                stmt.setInt(++num, spec_ID);
            }
            if(addReviewedByFriend)
            {
                String patientName = String.valueOf(request.getSession().getAttribute("patient"));
                stmt.setString(++num, patientName);
            }
            if(addReviewKeyword)
            {
                stmt.setString(++num, "%"+request.getParameter("reviewKeyword")+"%");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PatientDoctorSearchResultServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stmt;
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
