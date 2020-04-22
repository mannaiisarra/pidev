/*


 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.events;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;

import com.codename1.ui.EncodedImage;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
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

/**
 *
 * @author Nayer Jaber
 */
public class ReadEvents extends SideMenuBaseForm {

    public ReadEvents(Resources res) throws IOException {
        Form f = new Form();
        Form fo = new Form("Liste des evenements", new BorderLayout());
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener(e -> gotoajoutEvent(res));

        fab.bindFabToContainer(fo.getContentPane());

        Container List = new Container(BoxLayout.y());
        List.setScrollableY(true);
        EventService es = new EventService();
        ArrayList<Evenements> lis = es.getEvents();
        Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
        FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
        EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
        for (int i = 0; i < lis.size(); i++) {
            Container cc = new Container();
            final int c = lis.get(i).getId();
            System.out.println(c);
            Label Adresse = new Label();
            Label nom = new Label();
            Label D = new Label();
            Image image = URLImage.createToStorage(placeholder, lis.get(i).getBrochure(), "http://127.0.0.1/PIDEV - Copy/web/bundles/blog/template/images/" + lis.get(i).getBrochure());
            MultiButton mb = new MultiButton(lis.get(i).getNom());
            mb.setTextLine3(lis.get(i).getAdresse());
            mb.setTextLine4(lis.get(i).getDateF());
            mb.setIcon(image);

            List.add(mb);
            mb.addActionListener((al) -> gotoDetails(res, c));

        };
        fo.add(CENTER, List);
        add(fo);
        

        setupSideMenu(res);
        repaint();

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

    private void gotoDetails(Resources res, int id) {
        new detailEvent(res, id).show();
    }

}
