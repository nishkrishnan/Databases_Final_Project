/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class PatientDoctorAddReviewResultServlet extends HttpServlet {

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
        
        String url = "/patientDoctorAddReviewResult.jsp";
        
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

            addReview(con, request);

            con.close();

        } catch (Exception e) {
            url = "/error.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    public static boolean addReview(Connection con, HttpServletRequest request)
        throws ClassNotFoundException, SQLException 
    {
        PreparedStatement stmt = null;
        String msg = "Review added successfuly!";
        try
        {
            String query = "insert into Review " +
                "(review_date,rating,text,pat_alias,doc_alias) " +
                "values (CURDATE(), ? , ? , ? , ? )";
            
            stmt = con.prepareStatement(query); 
            if(request.getParameter("rating").isEmpty())
            {
                msg = "Please add a rating";
                throw new Exception(); 
            }
            int rating = Integer.parseInt(request.getParameter("rating"));
            if(rating < 0 || rating > 5)
            {
                msg = "Rating is out of range";
                throw new Exception();
            }
            stmt.setInt(1, rating);
              
            if(request.getParameter("text").isEmpty())
            {
                msg = "Please add a message to the review";
                throw new Exception();
            }
            stmt.setString(2, request.getParameter("text"));
            
            // CURRENTLY DOESNT WORK, NEED SESSION
            //stmt.setString(3, request.getParameter("patient_alias"));
            stmt.setString(3, "pat_anne");
            // FIX
            
            if(request.getParameter("doctor_ID").isEmpty())
            {
                msg = "Something went wrong";
                throw new Exception();
            }
            stmt.setString(4, request.getParameter("doctor_ID"));
            
            stmt.executeUpdate(query);
        }
        catch(Exception e)
        {
            return false;
        } 
        finally 
        {
            request.setAttribute("msg", msg);
            
            if (stmt != null) {
                stmt.close();
            }
        }
        
        return true;
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
