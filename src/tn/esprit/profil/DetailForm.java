/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.profil;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.entite.Etablissement;
import tn.esprit.widgets.SideMenuBaseForm;

/**
 *
 * @author ASUS
 */
public class DetailForm extends SideMenuBaseForm{
    private Etablissement currentEtab;
    
    public DetailForm(Resources res,Etablissement etab) {
        super(BoxLayout.y());
        currentEtab=etab;
        formEtab();
        setupSideMenu(res);
    }
    public void formEtab(){        
        setTitle("Détails");
        Container cnt = GridLayout.encloseIn(CENTER, new Label(""));
       
        TextArea ta = new TextArea(currentEtab.getAdresse());
        ta.setUIID("");
        ta.setEditable(false);
        Label nomlbl = new Label(currentEtab.getNom());
        nomlbl.setTextPosition(RIGHT);
        Label villelbl = new Label(currentEtab.getVille());
        villelbl.setTextPosition(LEFT);
        if(currentEtab.getNote()!=null)
        cnt.add(new Label(currentEtab.getNote().toString()));
        else
            cnt.add(new Label("No notes here"));
        if(currentEtab.getDescription()!=null)
        cnt.add(new TextArea(currentEtab.getDescription().toString()));
        else
            cnt.add(new Label("No Desc here"));
        add(cnt);
    }
    @Override    
    public void setupSideMenu(Resources res) {
        Toolbar b = getToolbar();
        b.setGlobalToolbar(true);
        b.removeAll();
        // Ajouter boutton a son container
        Button menuButton = new Button("");
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton)
        );
        
       
        titleCmp.setScrollableY(false);
        titleCmp.setScrollableX(false);       
        b.setTitleComponent(titleCmp);
        menuButton.setUIID("Title");
       
        menuButton.addActionListener((e) -> b.openSideMenu());
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        b.addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> gotoProfile(res));
        b.addMaterialCommandToSideMenu("  Evenements", FontImage.MATERIAL_ACCESS_TIME, e -> {
            try {
                gotoEvents(res);
            } catch (IOException ex) {
                System.out.println("");
            }
        });
        b.addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_BOOK, e -> gotoBlog(res));
        b.addMaterialCommandToSideMenu("  BonPlan", FontImage.MATERIAL_SEARCH, e -> gotoBonPlan(res));
        b.addMaterialCommandToSideMenu("  Paramétres", FontImage.MATERIAL_SETTINGS, e -> gotoStats(res));
        b.addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> gotoLogin(res));
        
    }
    
}
