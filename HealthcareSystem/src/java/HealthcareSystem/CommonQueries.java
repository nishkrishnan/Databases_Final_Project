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

/**
 *
 * @author mfarova
 */
public class CommonQueries {
    
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
     
     public static ArrayList<Patient> getPatients(Connection con, Patient pat, String query)
             throws ClassNotFoundException, SQLException, Exception {
         PreparedStatement stmt = null;
        ArrayList<Patient> ret = new ArrayList<>();
         try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, pat.alias);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Patient p = new Patient();
                
                p.alias = resultSet.getString("pat_alias");
                p.province = resultSet.getString("province");
                p.city = resultSet.getString("city");
                //p.numReviews = resultSet.getInt("Reviewdata.numReviews");
                //p.dateOfLastReview = resultSet.getDate("Reviewdata.lastReview");
                        
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
}
