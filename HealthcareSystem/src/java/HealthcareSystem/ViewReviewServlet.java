/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.sql.Connection;
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
public class ViewReviewServlet extends HttpServlet {

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
        
        String url = "/viewReview.jsp";
        ArrayList<Review> reviews;
        
        try 
        {
            if(request.getParameter("ID").isEmpty())
            {
                throw new Exception();
            }
            if (request.getParameter("doc").isEmpty()) {
                throw new Exception();
                
            }
            int reviewID = Integer.parseInt(request.getParameter("ID"));
            String docAlias = request.getParameter("doc");
            
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
            
            reviews = CommonQueries.getReviews(con,docAlias);
            
            for (int i = 0; i < reviews.size(); i++) {
                if (reviewID == reviews.get(i).review_ID) {
                    request.setAttribute("CurrentReview", reviews.get(i));
                    if (i > 0) {
                        request.setAttribute("LastReview", reviews.get(i - 1));
                    }
                    else {
                        request.setAttribute("LastReview", null);
                    }
                    if (i < (reviews.size() - 1)) {
                        request.setAttribute("NextReview", reviews.get(i + 1));
                    }
                    else {
                        request.setAttribute("NextReview", null);
                    }
                            
                }
            }
            request.setAttribute("doc", docAlias);
            con.close();

        } catch (Exception e) {
            url = "/error.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
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
