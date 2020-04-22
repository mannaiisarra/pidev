/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.blog;

import com.codename1.components.FloatingActionButton;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.util.EasyThread;
import java.io.IOException;
import java.util.ArrayList;
import tn.esprit.entite.Article;
import tn.esprit.entite.CommentaireB;
import tn.esprit.entite.Tag;
import tn.esprit.services.BlogService;
import tn.esprit.widgets.SideMenuBaseForm;

/**
 *
 * @author aminos
 */
public class ListeArticles extends SideMenuBaseForm {

    Toolbar tb;
    Container listeContainer = new Container(BoxLayout.y());
    Container searchContainer = new Container(BoxLayout.x());
    Container wholeContainer = new Container(BoxLayout.y());
    Resources res;

    //Container globalC = new Container(BoxLayout.y());
    public ListeArticles(Resources res) {
        this.res = res;
        this.tb = getToolbar();
        setupSideMenu(res);

        //Recovering objects
        EasyThread th = EasyThread.start("Hi");
        ArrayList<Article> articles = th.run(() -> {
            BlogService bS = new BlogService();
            return bS.findAll();
        });


        /*
        for (Article a : articles) {
            Label titre = new Label(a.getTitre());
            
            titre.addPointerPressedListener((evt) -> {
                Form article = new Form();
                
                BrowserComponent browser = new BrowserComponent();
                browser.setPreferredSize(new Dimension(150, 300));
                browser.setScrollableY(true);
                browser.setPage(a.getTexte(), "");
                
                Container c2 = BoxLayout.encloseY(
                        new Label(a.getTitre()),
                        browser
                );
                
              //  c2.addComponent();
               // c2.addComponent(BorderLayout.CENTER, browser);
               
                c2.add(new Label("Ajouter un commentaire"));
                c2.add(new TextArea());
                 c2.add(new Button("Commenter"));
                 
                if (a.getCommentaireBCollection() != null && !a.getCommentaireBCollection().isEmpty()) {
                    for (CommentaireB commentaire : a.getCommentaireBCollection()) {
                    Container commentC = new Container(BoxLayout.y());
                
                           commentC.add(new Label(commentaire.getAuteurn()));
               
                    Label texteC = new Label(commentaire.getText());
                    texteC.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                    commentC.add(texteC);
                    c2.add(commentC);
                    }
                }

                article.add(c2);
               
                article.show();
            });

            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            Label infos = new Label("Crée par " + a.getAuteurn()
                    + " le " + formatter.format(a.getCreated()));
            titre.getAllStyles().setFgColor(0x360ce4);
            infos.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
            Container articleInfoConainer = BoxLayout.encloseY(
                    titre,
                    infos
            );
            listeContainer.add(articleInfoConainer);
        }
         */
        //c.setScrollableX(false);
        //c.setScrollableY(false);
        //c.setScrollable(false);
        Label srchText = new Label("Recherche:");
        TextField searchFiled = new TextField("", "rechercher...");
        searchFiled.getAllStyles().setFgColor(0x000000);
        searchFiled.addDataChangedListener((int d1, int d2) -> {
            BlogService bS = new BlogService();
            if (!searchFiled.getText().equals("")) {
                setArticles(bS.findByText(searchFiled.getText()));
            } else {
                setArticles(bS.findAll());

            }
        });
        searchContainer.add(srchText);
        searchContainer.add(searchFiled);
        wholeContainer.add(searchContainer);

        wholeContainer.add(listeContainer);
        setArticles(articles);
        //wholeContainer.add(listeContainer);

        //Service testing
        Button ret = new Button("retrieve");
        wholeContainer.add(ret);
        add(wholeContainer);
        ret.addActionListener((e) -> {
            listeContainer.removeAll();
            BlogService bS = new BlogService();
            Tag t = new Tag();
            t.setName("come");
            //System.out.println(bS.findByTag(t));

            setArticles(bS.findByTag(t));
            /*
            System.out.println(bS.findByTag(t));
            System.out.println(bS.findByText("thrd"));
            System.out.println(bS.findAll());
             */
            //bS.find(3);
        });

    }

    @Override
    public void setupSideMenu(Resources res) {
        Toolbar b = getToolbar();
        b.removeAll();
        // Ajouter boutton a son container
        Button menuButton = new Button("");
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

    public void setArticles(ArrayList<Article> articles) {
        //System.out.println(articles);

        listeContainer.removeAll();

        for (Article article : articles) {
            Label titre = new Label(article.getTitre());
            titre.getAllStyles().setFgColor(0x0c42c0);
            titre.addPointerPressedListener((evt) -> {
                LireArticle lireArticle = new LireArticle(res, article);
                lireArticle.show();

            });

            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            Label informations = new Label("Crée par " + article.getAuteurn()
                    + " le " + formatter.format(article.getCreated()));
            informations.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            //System.out.println(article);
            Container articleInfoContainer = new Container(BoxLayout.y());
            articleInfoContainer.add(titre);
            articleInfoContainer.add(informations);
            listeContainer.add(articleInfoContainer);
        }

        repaint();
    }
}
