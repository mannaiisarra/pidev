/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.profil;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

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
        Label profilePicLabel = new Label("  Ben Saleh Zain", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ARCHIVE, e -> gotoProfile(res));
        getToolbar().addMaterialCommandToSideMenu("  Evenements", FontImage.MATERIAL_ACCESS_TIME, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Blog", FontImage.MATERIAL_BOOK, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Paramétres", FontImage.MATERIAL_SETTINGS, e -> gotoStats(res));
        getToolbar().addMaterialCommandToSideMenu("  Déconnecter", FontImage.MATERIAL_EXIT_TO_APP, e -> gotoLogin(res));
    }

    protected abstract void showOtherForm(Resources res);

    protected abstract void gotoProfile(Resources res);

    protected abstract void gotoStats(Resources res);

    protected abstract void gotoEvents(Resources res);

    protected abstract void gotoBlog(Resources res);

    protected abstract void gotoLogin(Resources res);
}

