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
 * @author Nish
 */
public class PatientSearchServlet extends HttpServlet {

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
        Patient pat = new Patient();
        pat.alias = request.getParameter("patientID");
        pat.province = request.getParameter("patientProvince");
        pat.city = request.getParameter("patientCity");
                                 

        /* DISPLAYS ALL PATIENTS FOR NOW, SEARCH NEEDS TO BE REWORKED*/
        
        
        /*query.append("select Review_data.pat_alias, city, province, Review_data.numReviews, "
                + "Review_data.lastReview from (select pat_alias, count(*) as numReviews, MAX(review_date)"
                + "lastReview from Review Group by pat_alias) as Review_data "
                + "INNER JOIN Patient ON Review_data.pat_alias=Patient.pat_alias"
                + "WHERE Patient.pat_alias = ?");*/
        
        
        String url = "/patientSearchResult.jsp";
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

            Statement stmt = null;
            
            
        ArrayList<Patient> patList = new ArrayList<>();
        
        patList = CommonQueries.getPatients(con, generateQuery(pat, con));
        request.setAttribute("PatientList", patList);
        
        con.close();
        
        }
        catch(Exception e) {
            url = "/error.jsp";
        
        }
        
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
                
        //response.setContentType("text/html;charset=UTF-8");
        /*try (PrintWriter out = response.getWriter()) {
            TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PatientSearchServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PatientSearchServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }
    
    public static PreparedStatement generateQuery(Patient pat, Connection con) 
            throws Exception {
        
        PreparedStatement stmt = null;
                                  
        StringBuilder query = new StringBuilder();
        boolean allempty = true;
        boolean addAlias = false;
        boolean addProvince = false;
        boolean addCity = false;
        query.append("Select pat_alias, city, province,\n" +
                     "(Select count(*) from Review where Review.pat_alias = Patient.pat_alias) as num_reviews,\n" +
                     "(Select MAX(review_date)from Review where Review.pat_alias = Patient.pat_alias) as last_review\n" +
                     "from Patient\n" +
                     "Where ");
        if (!pat.alias.isEmpty()) {
            if (allempty) {
                query.append("pat_alias like ?");
                allempty = false;
            }
            addAlias = true;
            
        }
        if (!pat.province.isEmpty()) {
            if (!allempty) {
                query.append(" AND ");
            }
            else {
                allempty = false;
            }
            addProvince = true;
            query.append("province like ?");
        }
        if (!pat.city.isEmpty()) {
            if (!allempty) {
                query.append(" AND ");
            }
            else {
                allempty = false;
            }
            addCity = true;
            query.append("city like ?");
        }
        
        
        int numParams = 0;
        try {
            if (!allempty) {
                numParams++;
                stmt = con.prepareStatement(query.toString());
                if (addAlias) {
                    stmt.setString(numParams, "%" + pat.alias + "%");
                    numParams++;
                }
                
                if (addProvince) {
                    stmt.setString(numParams, "%" + pat.province + "%");
                    numParams++;
                }
                if (addCity) {
                    stmt.setString(numParams, "%" + pat.city + "%");
                }
            }
        }
        catch(Exception e) {
            throw new Exception(e);
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
