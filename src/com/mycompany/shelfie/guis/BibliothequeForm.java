/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shelfie.guis;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.category;
import com.mycompany.myapp.services.ServiceCategorie;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class BibliothequeForm extends Form{

    private Button listBooksBtn;
   private Container mainContainer;
   Form mainForm = new Form();
   public static int id;
              
                SpanLabel lb;
                Label label;
                String url;
                EncodedImage enc;
                URLImage uRLImage;
                ImageViewer imgV;
                Container oneEvent;
   public BibliothequeForm(){
      
//       
//       listBooksBtn = new Button("Hellow in my library you can choise un livre ou reservee bienvenue ");
//       listBooksBtn.getUnselectedStyle().setFgColor(5542241);
//       mainContainer = new Container();
//       
//       mainContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//       mainContainer.add(listBooksBtn);
//       //mainForm.setLayout(new BorderLayout());
//       mainForm.add(mainContainer);
//
//       listBooksBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                
//                new ServiceCategorie().findAllBooks();
//            }
//        });
 com.codename1.ui.List uiLibsList = new com.codename1.ui.List();

  mainForm = new Form("Tous Les categories",new BoxLayout(BoxLayout.Y_AXIS));
        ArrayList<category> lis= ServiceCategorie.getList2();
         ArrayList<String> libsNoms = new ArrayList<>();
        for(category l:lis){
                         libsNoms.add(l.getLibelle());}

             com.codename1.ui.list.DefaultListModel<String> listModel = new com.codename1.ui.list.DefaultListModel<>(libsNoms);
                uiLibsList.setModel(listModel);
                uiLibsList.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                       category  currentBook = lis.get(uiLibsList.getCurrentSelected());
                       new LivreForm(currentBook.getId()).show();
                             

                    }
                });
                
      mainForm.setLayout(new BorderLayout());
                mainForm.add(BorderLayout.NORTH,uiLibsList);
 mainForm.getToolbar().addCommandToLeftBar("back", FontImage.createMaterial(FontImage.MATERIAL_HOME, new Style()), (evt) -> {
           mainForm.showBack();
        });
   }
 
   public Form getForm(){
   return mainForm;
   }
    
}
