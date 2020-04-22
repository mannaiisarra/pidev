/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.securite;

/**
 *
 * @author ASUS
 */
public class User {
    
    private String login;
    private String plain;

    public User( String login, String plain) {
        
        this.login = login;
        this.plain = plain;
    }

    public User() {
    }

    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", plain=" + plain + '}';
    }

   
    
}
