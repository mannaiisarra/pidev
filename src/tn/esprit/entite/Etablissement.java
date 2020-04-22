/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entite;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ASUS
 */

public class Etablissement  implements Serializable{

    private Integer id;
    private String nom;
    private String adresse;
    private String gouvernorat;
    private String ville;
    private Double note;
    private Date horraire;
    private Double longitude;
    private Double latitude;
    private Boolean estActive;
    private String type;    
    private String description;
    private String photo;
    private Date horraire_f;
    
    

    public Etablissement() {
    }

    public Etablissement(String nom, String adresse, String gouvernorat, String ville, Double note, Date horraire, Double longitude, Double latitude, Boolean estActive, String type, String description, String photo) {
        this.nom = nom;
        this.adresse = adresse;
        this.gouvernorat = gouvernorat;
        this.ville = ville;
        this.note = note;
        this.horraire = horraire;
        this.longitude = longitude;
        this.latitude = latitude;
        this.estActive = estActive;
        this.type = type;
        this.description = description;
        this.photo = photo;
    }
    //parser
    public Etablissement(Map<String, Object> obj) {
        Float f= Float.parseFloat(obj.get("id").toString());
        this.id = f.intValue();  
        if(obj.get("nom")!=null)
        this.nom = obj.get("nom").toString();
        if(obj.get("adresse")!=null)
        this.adresse = obj.get("adresse").toString();
        if(obj.get("gouvernorat")!=null)
        this.gouvernorat = obj.get("gouvernorat").toString();
        if(obj.get("ville")!=null)
        this.ville = obj.get("ville").toString();
        if(obj.get("note")!=null)
        this.note = Double.parseDouble(obj.get("note").toString());
        /*DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.horraire =(Date)formatter.parse(obj.get("horraire").toString());
        } catch (ParseException ex) {
           System.out.println(ex.getMessage());
        }*/
        this.horraire=new Date();
        if(obj.get("longitude")!=null)
        this.longitude = Double.parseDouble(obj.get("longitude").toString());
        if(obj.get("latitude")!=null)
        this.latitude = Double.parseDouble(obj.get("latitude").toString());
        this.estActive =  Boolean.parseBoolean(obj.get("estActive").toString());//.booleanValue();   
        if(obj.get("description")!=null)
        this.description = obj.get("description").toString();
        if(obj.get("type")!=null)
        this.type = obj.get("type").toString();
        if(obj.get("photo")!=null)
        this.photo = obj.get("photo").toString();       
        /*try {
            this.horraire_f =(Date)formatter.parse(obj.get("horraire_f").toString());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }*/
        this.horraire_f=new Date();
    }

    
    
    public Etablissement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Date getHorraire() {
        return horraire;
    }

    public void setHorraire(Date horraire) {
        this.horraire = horraire;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getEstActive() {
        return estActive;
    }

    public void setEstActive(Boolean estActive) {
        this.estActive = estActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getHorraire_f() {
        return horraire_f;
    }

    public void setHorraire_f(Date horraire_f) {
        this.horraire_f = horraire_f;
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
        if (!(object instanceof Etablissement)) {
            return false;
        }
        Etablissement other = (Etablissement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Etablissement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", gouvernorat='" + gouvernorat + '\'' +
                ", ville='" + ville + '\'' +
                ", note=" + note +
                ", horraire=" + horraire +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", estActive=" + estActive +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", horraire_f=" + horraire_f +              
                '}';
    }
}
