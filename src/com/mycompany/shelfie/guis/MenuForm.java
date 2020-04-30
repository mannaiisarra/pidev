/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shelfie.guis;

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.services.ServiceCategorie;
import java.io.IOException;
import javafx.scene.control.ToolBar;


/**
 *
 * @author HP
 */
public  class MenuForm  extends Form{

    private Resources theme;
    public static Form f2;
  public   MenuForm() {
      

        //getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> {});
        getToolbar().addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_BOOK, e->new BibliothequeForm().getForm().show());
       //getToolbar().addMaterialCommandToSideMenu("  Paramétres", FontImage.MATERIAL_SETTINGS, e -> new LivreForm().getForm().show());
        getToolbar().addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> {});


         }
   public Form getForm1(){
   return f2;
   }

    

    
    
}


   

   

   
