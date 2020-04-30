/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shelfie.guis;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Livre;
import com.mycompany.myapp.entities.category;
import com.mycompany.myapp.services.ServiceCategorie;
import static com.mycompany.myapp.services.ServiceCategorie.listOfBooks;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class LivreForm  extends Form{
   public static int id;
                Form f;
                SpanLabel lb;
                Label label;
                String url;
                EncodedImage enc;
                URLImage uRLImage;
                ImageViewer imgV;
                Container oneEvent;
    public LivreForm(int id){
   
                        
        f = new Form("Tous Les Categories",new BoxLayout(BoxLayout.Y_AXIS));
        ArrayList<Livre> lis= ServiceCategorie.getMyListcl();
        for(Livre l:lis){
                         oneEvent=new Container(new BoxLayout(BoxLayout.X_AXIS));
            Label lab3=new Label(l.getNom());
            oneEvent.add(lab3);
            System.out.println(l.getNom());
            Button event_btn=new Button("Détail");
            oneEvent.add(event_btn);
            f.add(oneEvent);
             event_btn.addActionListener((e)->{
            
           });
        }
          //f.getToolbar().addCommandToLeftBar(null, theme.getImage("cal_left_arrow.png"), (ev)->{HomeForm h=new HomeForm(theme);
         
          //});
         // f.setLayout(new BorderLayout());
   
       
    }

   

  
     public Form getForm(){
   return f;
   }

    
}

