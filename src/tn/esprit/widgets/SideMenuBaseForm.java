/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.widgets;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

import tn.esprit.blog.ListeArticles;
import tn.esprit.events.ReadEvents;
import tn.esprit.profil.BonPlan;
import tn.esprit.profil.LoginForm;
import tn.esprit.profil.ProfileForm;
import tn.esprit.profil.StatsForm;
import tn.esprit.securite.Authenticator;

public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(Authenticator.getCurrentAuth().getUsername(), profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> gotoProfile(res));
        getToolbar().addMaterialCommandToSideMenu("  Evenements", FontImage.MATERIAL_ACCESS_TIME, e -> {
            try {
                gotoEvents(res);
            } catch (IOException ex) {
            }
        });
        getToolbar().addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_BOOK, e -> gotoBlog(res));
        getToolbar().addMaterialCommandToSideMenu("  BonPlan", FontImage.MATERIAL_SEARCH, e -> gotoBonPlan(res));
        getToolbar().addMaterialCommandToSideMenu("  Paramétres", FontImage.MATERIAL_SETTINGS, e -> gotoStats(res));
        getToolbar().addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> gotoLogin(res));
    }

    protected  void showOtherForm(Resources res) {
        
    };

    protected void gotoProfile(Resources res) {
        new ProfileForm(res).show();
    };

    protected void gotoStats(Resources res) {
        new StatsForm(res).show();
    };

    protected void gotoEvents(Resources res) throws IOException {
           new ReadEvents(res).show();
        
    };

    protected void gotoBlog(Resources res) {
        ListeArticles blog = new ListeArticles(res);
        //blog.setupSideMenu(res);
        blog.show();
    };

    protected void gotoLogin(Resources res) {
        new LoginForm(res).show();
    };

    protected void gotoBonPlan(Resources res) {
        new BonPlan(res).show();
    }
}

