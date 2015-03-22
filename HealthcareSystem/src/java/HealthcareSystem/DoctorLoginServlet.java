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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author mfarova
 */
public class DoctorLoginServlet extends HttpServlet {

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
        
        String url = "/doctorProfile.jsp";
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

            boolean success = isLoginSuccessful(con, request);
            
            con.close();
            
            if(success)
            {
                String docID = request.getParameter("username");
                
                HttpSession session = request.getSession(true);
                session.setAttribute("doctor",docID);
            }
            else
            {
                url = "/doctorLogin.jsp";
            }
            
        } catch (Exception e) {
            url = "/error.jsp";
        }
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
        
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
