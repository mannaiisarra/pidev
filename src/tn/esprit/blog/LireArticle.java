/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.blog;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.entite.Article;
import tn.esprit.entite.CommentaireB;
import tn.esprit.entite.Tag;
import tn.esprit.services.BlogService;
import tn.esprit.widgets.SideMenuBaseForm;
import com.codename1.io.Util;
import com.codename1.ui.Dialog;
import java.io.IOException;
import tn.esprit.securite.Authenticator;

/**
 *
 * @author aminos
 */
public class LireArticle extends SideMenuBaseForm {

    Resources res;
    Toolbar tb;
    Container articleContainer = new Container(BoxLayout.y());
    Container commentairesContainer = new Container(BoxLayout.y());
    Container searchTagContainer = new Container(BoxLayout.y());
    Container wholeContainer = new Container(BoxLayout.y());
    TextArea commentaireTextArea = new TextArea();
    Article article;
    private boolean modifying;
    private CommentaireB commentaireModified = null;
    private Label commentaireModifiedT;

    public LireArticle(Resources res, Article article) {
        this.res = res;
        this.tb = getToolbar();
        this.article = article;
        setupSideMenu(res);
        commentaireTextArea.setHint("Ajouter un commentaire...");
        setArticle(article);
        wholeContainer.add(searchTagContainer);
        wholeContainer.add(articleContainer);
        wholeContainer.add(commentairesContainer);
        add(wholeContainer);
    }

    @Override
    public void setupSideMenu(Resources res) {
        Toolbar b = getToolbar();
        b.removeAll();
        // Ajouter boutton a son container
        Button menuButton = new Button();
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

    public void setArticle(Article article) {

        articleContainer.removeAll();
        commentairesContainer.removeAll();
        BrowserComponent browser = new BrowserComponent();
        browser.setPreferredSize(new Dimension(300, 300));
        browser.setScrollableY(true);
        browser.setPage(article.getTexte(), "");
        Label titreA = new Label(article.getTitre());
        titreA.getAllStyles().setFgColor(0x000000);
        articleContainer.add(titreA);
        Button pdfBut = new Button("Exporter PDF");
        pdfBut.addActionListener((evt) -> {
            String filename = article.getId() + "" + article.getAuteurn() + ".pdf";
            getFile(filename, article.getId());
            Dialog.show("Succes", "Fichier enregistré en " + FileSystemStorage.getInstance().getAppHomePath() + filename, "OK", null);
        });
        articleContainer.add(pdfBut);
        int i = 1;
        Container tagContainer = new Container();
        tagContainer.setLayout(new FlowLayout());
        if (article.getTagCollection().size() > 0) {
            Label tg = new Label("tags:");
            tg.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
            tagContainer.add(tg);
            for (Tag tag : article.getTagCollection()) {

                System.out.println(tag + " " + article.getTagCollection().indexOf(tag));
                Label tagLabel = new Label(tag.getName());
                tagLabel.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                tagLabel.getAllStyles().setFgColor(0xFA023C);
                tagLabel.addPointerPressedListener((evt) -> {
                    ListeArticles blog = new ListeArticles(res);
                    BlogService bS = new BlogService();

                    blog.setArticles(bS.findByTag(tag));
                    blog.show();
                });
                tagContainer.add(tagLabel);

            }
        }

        articleContainer.add(tagContainer);
        articleContainer.add(browser);
        commentairesContainer.add(new Label("Ajouter un commentaire:"));
        commentaireTextArea.setPreferredH(100);
        commentairesContainer.add(commentaireTextArea);
        Button commenterButton = new Button("Commenter");
        //bind actions to comment
        commentairesContainer.add(commenterButton);
        commenterButton.addActionListener((evt) -> {
            if (commentaireTextArea.getText().length() != 0) {
            if (commentaireModified != null && commentaireModifiedT != null) {
                System.out.println("modifying!");
                BlogService bS = new BlogService();
                commentaireModified.setText(commentaireTextArea.getText());
                bS.modifierCommentaire(commentaireModified);
                commentaireModifiedT.setText(commentaireTextArea.getText());
                commentaireModified = null;
                commentaireModifiedT = null;
                commentaireTextArea.setText("");
                commentaireTextArea.setHint("Ajouter un commentaire...");
            } else {
                BlogService bS = new BlogService();
                CommentaireB commentaire = new CommentaireB(commentaireTextArea.getText(), Authenticator.getCurrentAuth().getUsername(), Authenticator.getCurrentAuth().getId());
                commentaire.setId(-1);

                CommentaireB returned = bS.ajouterCommentaireB(article, commentaire);
                if (returned != null) {
                    Container commentC = new Container(BoxLayout.y());
                    Container modSupC = new Container(BoxLayout.x());
                    commentC.add(new Label(commentaire.getAuteurn()));

                    Label texteC = new Label(commentaire.getText());
                    Label modifier = new Label("Modifier");
                    modifier.getAllStyles().setFgColor(0x00bfff);
                    modifier.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                    Label supprimer = new Label("Supprimer");
                    supprimer.getAllStyles().setFgColor(0xFA023C);
                    supprimer.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                    modifier.addPointerPressedListener((evt1) -> {
                        commentaireTextArea.setText(commentaire.getText());
                        commentaireModified = commentaire;
                        commentaireModifiedT = texteC;
                    });
                    supprimer.addPointerPressedListener((evt2) -> {
                        BlogService bS1 = new BlogService();
                        if (bS1.supprimerCommentiareB(commentaire.getId())) {
                            commentC.remove();
                            repaint();
                        }
                    });
                    if (commentaire.getAuteur() == Authenticator.getCurrentAuth().getId()) {
                        modSupC.add(modifier);
                        modSupC.add(supprimer);
                        commentC.add(modSupC);
                    }
                    texteC.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
                    texteC.getAllStyles().setFgColor(0x000000);
                    commentC.add(texteC);
                    commentairesContainer.add(commentC);
                    commentaireTextArea.setText("");
                    commentaireTextArea.setHint("Ajouter un commentaire...");
                    repaint();
                }
            }
            }
            else {
                Dialog.show("Erreur", "Le commentaire est vide", "OK", null);
            }
        });
        if (article.getCommentaireBCollection() != null && !article.getCommentaireBCollection().isEmpty()) {
            for (CommentaireB commentaire : article.getCommentaireBCollection()) {
                Container commentC = new Container(BoxLayout.y());
                Container modSupC = new Container(BoxLayout.x());
                commentC.add(new Label(commentaire.getAuteurn()));

                Label texteC = new Label(commentaire.getText());

                Label modifier = new Label("Modifier");
                modifier.getAllStyles().setFgColor(0x00bfff);
                modifier.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                Label supprimer = new Label("Supprimer");
                supprimer.getAllStyles().setFgColor(0xFA023C);
                supprimer.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
                modifier.addPointerPressedListener((evt) -> {
                    commentaireTextArea.setText(commentaire.getText());
                    commentaireModified = commentaire;
                    commentaireModifiedT = texteC;
                });
                supprimer.addPointerPressedListener((evt) -> {
                    BlogService bS = new BlogService();
                    if (bS.supprimerCommentiareB(commentaire.getId())) {
                        commentC.remove();
                        repaint();
                    }
                });
                if (commentaire.getAuteur() == Authenticator.getCurrentAuth().getId()) {
                    modSupC.add(modifier);
                    modSupC.add(supprimer);
                    commentC.add(modSupC);
                }
                texteC.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
                texteC.getAllStyles().setFgColor(0x000000);
                commentC.add(texteC);
                commentairesContainer.add(commentC);
                // TO REMOVE
                //commentaireModified = commentaire;
            }
        }

        repaint();

    }

    public void getFile(String filename, int id) {

        if (filename == null || filename.length() < 1) {
            return;
        }

        String fullPathToFile = FileSystemStorage.getInstance().getAppHomePath() + filename;
        Util.downloadUrlToFileSystemInBackground("http://127.0.0.1:8000/blog/pdf/" + id, fullPathToFile);
    }
}
