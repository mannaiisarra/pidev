/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author ASUS
 */

public class Utilisateur   {
    private static final Map<String, Utilisateur> USERS = new HashMap<String, Utilisateur>();
    public static Utilisateur of(String userIdentity) {
        Utilisateur user = USERS.get(userIdentity);
        if (user == null) {
            user = new Utilisateur(userIdentity);
            USERS.put(userIdentity, user);

        }
        return user;
    }

    private Integer id;
    private String photoProfil;
    private Double langitude;
    private Double latitude;
    private String username;
    private String usernameCanonical;
    private String email;
    private String emailCanonical;
    private boolean enabled;
    private String salt;
    private String password;
    private Date lastLogin;
    private String confirmationToken;
    private Date passwordRequestedAt;
    private String roles;
    private String prenom;   
    private Etablissement etablissement;


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Utilisateur(Map<String, Object> obj){
        Float f= Float.parseFloat(obj.get("id").toString());
        this.id = f.intValue(); 
        if(obj.get("photoProfil")!=null)
        this.photoProfil = obj.get("photoProfil").toString();
        if(obj.get("langitude")!=null)
        this.langitude = Double.parseDouble(obj.get("langitude").toString());       
        if(obj.get("latitude")!=null)
        this.latitude = Double.parseDouble(obj.get("latitude").toString());
        this.username = obj.get("username").toString();
        this.usernameCanonical = obj.get("usernameCanonical").toString();             
        this.email = obj.get("email").toString();
        this.emailCanonical = obj.get("emailCanonical").toString();   
        
        this.enabled = Boolean.parseBoolean(obj.get("enabled").toString());//.booleanValue();    
        this.salt = obj.get("salt").toString();
        this.password = obj.get("password").toString();
        // TODO : il faut prendre le role exacte POUR le moment que le client va utiliser l'app mobile
        java.util.List<String> arr = (java.util.List<String>)obj.get("roles");
        this.roles=arr.get(0);
       // this.roles = "ROLE_CLIENT";
        if(obj.get("prenom")!=null)
        this.prenom = obj.get("prenom").toString();
        // TODO : change etablissement by the real etab from the map
        this.etablissement = null;
    }   

    public Utilisateur() {
    }
    public Utilisateur(String userIdentity ) {
        this.id=id;
    }

    public Utilisateur(Integer id, String photoProfil, Double langitude, Double latitude, String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String salt, String password, String roles) {
        this.id = id;
        this.photoProfil = photoProfil;
        this.langitude = langitude;
        this.latitude = latitude;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.salt = salt;
        this.password = password;
        this.roles = roles;
    }
    public Utilisateur(String photoProfil, Double langitude, Double latitude, String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String salt, String password/*, String roles*/) {
        this.photoProfil = photoProfil;
        this.langitude = langitude;
        this.latitude = latitude;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.salt = salt;
        this.password = password;
        this.roles = roles;
    }

    public Utilisateur(Integer id, String photoProfil, Double langitude, Double latitude, int etablissement_id, String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String salt, String password, Date lastLogin, String confirmationToken, Date passwordRequestedAt, String roles,String prenom) {
        this.id = id;
        this.photoProfil = photoProfil;
        this.langitude = langitude;
        this.latitude = latitude;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.salt = salt;
        this.password = password;
        this.lastLogin = lastLogin;
        this.confirmationToken = confirmationToken;
        this.passwordRequestedAt = passwordRequestedAt;
        this.roles = roles;
        etablissement=new Etablissement();
        this.etablissement.setId(etablissement_id);
        this.prenom=prenom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public Double getLangitude() {
        return langitude;
    }

    public void setLangitude(Double langitude) {
        this.langitude = langitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameCanonical() {
        return usernameCanonical;
    }

    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCanonical() {
        return emailCanonical;
    }

    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(Date passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }



    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", photoProfil='" + photoProfil + '\'' +
                ", langitude=" + langitude +
                ", latitude=" + latitude +
                ", username='" + username + '\'' +
                ", usernameCanonical='" + usernameCanonical + '\'' +
                ", email='" + email + '\'' +
                ", emailCanonical='" + emailCanonical + '\'' +
                ", enabled=" + enabled +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", passwordRequestedAt=" + passwordRequestedAt +
                ", roles='" + roles + '\'' +
                
                ", etablissement=" + etablissement +
                
                '}'+"\n";
    }


}
