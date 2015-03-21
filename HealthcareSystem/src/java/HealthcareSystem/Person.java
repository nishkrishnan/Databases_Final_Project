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
public class Person {
    
    public String alias;
    public String firstName;
    public String lastName;
    public String email;
    public String gender;
    
    Person(String alias, String firstName, String lastName, String email, String gender) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }
}
