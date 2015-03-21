/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                                 
                          
        StringBuilder query = new StringBuilder();
        /* DISPLAYS ALL PATIENTS FOR NOW, SEARCH NEEDS TO BE REWORKED*/
        query.append("select Review_data.pat_alias, city, province, Review_data.numReviews, "
                + "Review_data.lastReview from (select pat_alias, count(*) as numReviews, MAX(review_date)"
                + "lastReview from Review Group by pat_alias) as Review_data "
                + "INNER JOIN Patient ON Review_data.pat_alias=Patient.pat_alias"
                + "WHERE Patient.pat_alias =");
        query.append(pat.alias);
                
        /*select Review_data.pat_alias, city, province, Review_data.numReviews,

Review_data.lastReview from (select pat_alias, count(*) as numReviews, MAX(review_date)

as lastReview

from Review

Group by pat_alias) as Review_data

INNER JOIN Patient ON Review_data.pat_alias=Patient.pat_alias */
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
