/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * @author Tyler
 */
public class ViewFriendRequestsServlet extends HttpServlet {

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

        String url = "/patientViewFriendRequests.jsp";

        try {
            InitialContext cxt = new InitialContext();
            if (cxt == null) {
                throw new RuntimeException("Unable to create naming context!");
            }
            Context dbContext = (Context) cxt.lookup("java:comp/env");
            DataSource ds = (DataSource) dbContext.lookup("jdbc/myDatasource");
            if (ds == null) {
                throw new RuntimeException("Data source not found!");
            }
            Connection con = ds.getConnection();

            ArrayList friendRequests = getFriendRequests(con, request);
            request.setAttribute("friendRequestsList", friendRequests);

            con.close();

        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    public static ArrayList<String> getFriendRequests(Connection con, HttpServletRequest request)
            throws ClassNotFoundException, SQLException {

        Statement stmt = null;
        ArrayList<String> ret = null;
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select pat_added_alias, email from (select distinct a.pat_alias, a.pat_added_alias from Friend a where pat_added_alias not in (select b.pat_alias from Friend b where b.pat_added_alias = a.pat_alias)) as c inner join Person on pat_added_alias = Person.person_alias where pat_alias = 'pat_anne'");
            ret = new ArrayList<String>();
            while (resultSet.next()) {
                ret.add(resultSet.getString("pat_added_alias"));
                ret.add(resultSet.getString("email"));
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
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
