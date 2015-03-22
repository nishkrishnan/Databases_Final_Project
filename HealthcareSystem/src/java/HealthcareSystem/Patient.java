/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;
import java.util.Date;
/**
 *
 * @author Nish
 */
public class Patient extends Person{
    public String province;
    public String city;
    public int numReviews;
    public Date dateOfLastReview;
    public boolean isFriend;
    public boolean requestedToBeFriend;
}
