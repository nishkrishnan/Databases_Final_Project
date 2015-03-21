/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthcareSystem;

/**
 *
 * @author Nish
 */
public class Patient {
    public int id;
    public String province;
    public String city;
    
    Patient(int patientID, String patientProvince, String patientCity) {
        this.id = patientID;
        this.province = patientProvince;
        this.city = patientCity;
    }
}
