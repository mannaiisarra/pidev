/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.events;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import tn.esprit.entite.Etablissement;
import tn.esprit.entite.Evenements;
import tn.esprit.profil.LoginForm;
import tn.esprit.profil.ProfileForm;
import tn.esprit.profil.StatsForm;
import tn.esprit.services.EventService;
import tn.esprit.widgets.SideMenuBaseForm;

//lat 36.8992739
//long 10.189555799999994

/**
 *
 * @author Nayer Jaber
 */
public class detailEvent extends SideMenuBaseForm {
    private static final String api = "AIzaSyA4DlUF_3JwkK1t4dk2IEwoC1tRGS20QY4";


    detailEvent(Resources res, int id) {
        
         EventService es = new EventService(); 
                ArrayList<Evenements> lis=es.getEvent(id);
                System.out.println(lis.get(0).getNom());
                System.out.println(lis.get(0).getDateF());
                System.out.println(lis.get(0).getAdresse());
                ArrayList<Etablissement> loc = es.getCood(lis.get(0).getAdresse());
              Double lat = loc.get(0).getLatitude();
              Double ln = loc.get(0).getLongitude();

               
        Form hi = new Form();
        hi.setLayout(new BorderLayout());
        final MapContainer cnt = new MapContainer();

          Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
                         FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        cnt.zoom(new Coord(lat, ln), 15);
        cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCameraPosition(),
                    "Hi marker",
                    "Optional long description",
                     evt -> {
                             ToastBar.showMessage("L'evenements est ici", FontImage.MATERIAL_PLACE);
                     }
            );
      

        
         Button me = new Button("ma location");
        InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        //Cancel after 20 seconds
        Location l= LocationManager.getLocationManager().getCurrentLocationSync(2000);
              ipDlg.dispose();
        if (loc != null) {
            double lti = l.getLatitude();
            double lngi = l.getLongitude();
        me.addActionListener(e->{
        cnt.setCameraPosition(new Coord(lti, lngi));
        });
             cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    new Coord(lti, lngi),
                    "Hi marker",
                    "Optional long description",
                     evt -> {
                             ToastBar.showMessage("Vous etes ici ", FontImage.MATERIAL_PLACE);
                     }
            );
          
            
        } else {
            Dialog.show("GPS error", "Your location could not be found, please try going outside for a better GPS signal", "Ok", null);
        }
  
           
        
         
         

     
  
       

        

        Button btnMoveCamera = new Button("Move Camera");
        btnMoveCamera.addActionListener(e->{
            cnt.setCameraPosition(new Coord(lat, ln));
        });
 

     
       



        Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e->{
            cnt.clearMapLayers();
        });

        cnt.addTapListener(e->{
            TextField enterName = new TextField();
            Container wrapper = BoxLayout.encloseY(new Label("Name:"), enterName);
            InteractionDialog dlg = new InteractionDialog("Add Marker");
            dlg.getContentPane().add(wrapper);
            enterName.setDoneListener(e2->{
                String txt = enterName.getText();
                cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(e.getX(), e.getY()),
                        enterName.getText(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+txt, FontImage.MATERIAL_PLACE);
                        }
                );
                dlg.dispose();
            });
            dlg.showPopupDialog(new Rectangle(e.getX(), e.getY(), 10, 10));
            enterName.startEditingAsync();
        });

        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt),
                BorderLayout.south(
                        FlowLayout.encloseBottom(btnMoveCamera,me)
                )
        );

        hi.add(BorderLayout.CENTER, root);
       

     add(hi);
           setupSideMenu(res);
    }
    @Override
    public void setupSideMenu(Resources res) {
           
        Toolbar b = getToolbar();
        b.removeAll();
        
        // Ajouter boutton a son container
        Button menuButton = new Button("Click me");
        
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton)             
        );
      
      
        titleCmp.setScrollableY(false);
        titleCmp.setScrollableX(false);
        // AJOUTER le container du BOUTTON OU toolbar
        b.setTitleComponent(titleCmp);
//        b.setTitleComponent(fab.bindFabToContainer(titleCmp,  CENTER, BOTTOM));
        //add(titleCmp);
        menuButton.setUIID("Title");
        menuButton.addActionListener((e) -> b.openSideMenu());
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        b.addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> gotoProfile(res));
        b.addMaterialCommandToSideMenu("  Evenements", FontImage.MATERIAL_ACCESS_TIME, e -> {
            try {
                gotoEvents(res);
            } catch (IOException ex) {
            
            }
        });
        b.addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_BOOK, e -> gotoBlog(res));
        b.addMaterialCommandToSideMenu("  Paramétres", FontImage.MATERIAL_SETTINGS, e -> gotoStats(res));
        b.addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> gotoLogin(res));

    }

    
    
    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    @Override
    protected void gotoProfile(Resources res) {
        new ProfileForm(res).show();
    }

    @Override
    protected void gotoStats(Resources res) {
        new StatsForm(res).show();
    }

    @Override
    protected void gotoEvents(Resources res) throws IOException {
        new ReadEvents(res).show();
    }


    @Override
    protected void gotoLogin(Resources res) {
        new LoginForm(res).show();
    }

    private void gotoajoutEvent(Resources res) {
       new ajoutEvent(res).show();
    }
    

    
}
