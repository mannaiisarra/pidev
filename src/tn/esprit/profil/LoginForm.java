/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.profil;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.securite.Authenticator;


import tn.esprit.securite.FOSJCrypt;
import tn.esprit.securite.Sha512;
import tn.esprit.securite.User;
import tn.esprit.services.UtilisateurService;



public class LoginForm extends Form {

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }
    
    TextField login ;
    TextField password; ;
    Database db=null;
    Cursor cur=null;   
    User storedUser=null;
    boolean created=false;
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Bienvenue", ""),
                new Label("", "WelcomeBlue")
        );         
             
        created = Database.exists("MyDb");
        
        try {
              db = Database.openOrCreate("MyDb");
              if (created == false) {       
                db.execute("create table user ( id INTEGER,login TEXT, pass TEXT);");
               
            }

        } catch (IOException ex) {
            System.out.println("Erreur");
        }
        getTitleArea().setUIID("Container");       
                      Cursor s;
                try {                   
                    s = db.executeQuery("select * from user");
                    while (s.next()) {
                        Row r = s.getRow();                        
                         storedUser=new User( r.getString(1), r.getString(2));      
                         System.out.println(storedUser);
                    }                  
                } catch (IOException ex) {
                    System.out.println("Erreur");
                }
                System.out.println(created+" base creéer ");
        Image profilePic = theme.getImage("user-picture.jpg");        
        Image mask = theme.getImage("round-mask.png");        
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());      
        /*UtilisateurService es=new UtilisateurService();
        ArrayList<Utilisateur> users = es.selectAllEnabled();*/
          login = new TextField("admin", "Login", 20,TextField.ANY) ;login.getStyle().setFgColor(00000);
          password = new TextField("admin", "Password", 20, TextField.PASSWORD) ;   password.getStyle().setFgColor(00000);    
        if(storedUser!=null){
         login = new TextField(storedUser.getLogin(), "", 20,TextField.ANY) ;
         password = new TextField(storedUser.getPlain(), "Password", 20, TextField.PASSWORD) ;  
        }        
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        CheckBox spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new CheckBox(" Remember me");
        } else {
            spaceLabel = new CheckBox("Remember me ");
        }         
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                if(true){
                    if (spaceLabel.isSelected()   &&   !login.getText().isEmpty()   &&  !password.getText().isEmpty() ){
                        System.out.println(login.getText());
            try {
                System.out.println("tawa"+created);
                //db.beginTransaction();
                db.openOrCreate("MyDb");
                db.execute("insert into user (login, pass) values ('" + login.getText() + "', '" + password.getText() + "' );");
                
            } catch (IOException ex) {
                System.out.println(ex.getCause() +" "+ex.getMessage());
            }
        }
                    if(userLogging(login.getText(), password.getText())) {                
                     Toolbar.setGlobalToolbar(false);
                     new WalkthruForm(theme).show();
                     Toolbar.setGlobalToolbar(true);
                    }
                }              
            } catch (Exception ex) {
                    System.out.println("ex ne passse pas par login non auth");
            }
            }
        });
           

        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("");
        createNewAccount.addActionListener(e -> Display.getInstance().execute("http://127.0.0.1:8000/register/"));
        // We remove the extra space for low resolution devices so things fit better
        


        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);      
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }    
    public boolean userLogging(String userIdentity, String password) throws Exception  {
        UtilisateurService us=new UtilisateurService();
    if (Authenticator.validate(userIdentity, password)) {
        return true;
    } else {
        return false;
        }
    }
}
