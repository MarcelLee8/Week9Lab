package models;

import java.io.Serializable;

/**
 *
 * @author marce
 */
public class User implements Serializable  {
    private String email;
    private String fname;
    private String lname;
    private String pword;
    private Role role;

    public User()   {
        
    }

    public User(String email, String fname, String lname, String pword, Role role) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.pword = pword;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastName() {
        return lname;
    }

    public String getPassword() {
        return pword;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstname) {
        this.fname = fname;
    }

    public void setLastName(String lname) {
        this.lname = lname;
    }

    public void setPassword(String pword) {
        this.pword = pword;
    }

    public void setRole(Role role) {
        this.role = role;
    }   
}