/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import tn.esprit.entite.Article;
import tn.esprit.entite.CommentaireB;
import tn.esprit.entite.Tag;

/**
 *
 * @author aminos
 */
public class BlogService {

    Article retF = null;
    ArrayList<Article> retTgArt = new ArrayList<>();
    private ArrayList<Article> retTxtArt = new ArrayList<>();
    private ArrayList<Article> retAllArt = new ArrayList<>();
    private String retCModified;
    private String retSupp;

    public Article find(int id) {
        retF = null;
        
        String url = "http://127.0.0.1:8000/blog/lireArticle/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url + id);
        cR.addArgument("mobile", "1");
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                if (testStr.read() == 0) {
                    return;
                }

                Map<String, Object> resultat = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8"));
                if (!resultat.isEmpty()) {
                    retF = parseArticle(resultat);

                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
        NetworkManager.getInstance().addToQueueAndWait(cR);
        }
        catch (NullPointerException e) {
            
        }
        return retF;

    }

    public ArrayList<Article> findAll() {
        retAllArt = new ArrayList<>();
        String url = "http://127.0.0.1:8000/blog/listeArticles/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url);
        cR.addArgument("mobile", "1");
        cR.addArgument("all", "1");
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                if (testStr.read() == 0) {
                    return;
                }

                Map<String, Object> resultat = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8"));
                if (!resultat.isEmpty()) {

                    retAllArt = parseArticles((ArrayList< Map< String, Object>>) resultat.get("root"));
                    //System.out.println(resultat.get("root"));

                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
        NetworkManager.getInstance().addToQueueAndWait(cR);
        }
        catch (NullPointerException e) {
            
        }
        return retAllArt;
    }

    public ArrayList<Article> findByTag(Tag t) {
        retTgArt = new ArrayList<>();
        String url = "http://127.0.0.1:8000/blog/listeArticles/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url);
        cR.addArgument("mobile", "1");
        cR.addArgument("rech", "1");
        cR.addArgument("tag", t.getName());
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                if (testStr.read() == 0) {
                    return;
                }

                Map<String, Object> resultat = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8"));
                if (!resultat.isEmpty()) {

                    retTgArt = parseArticles((ArrayList< Map< String, Object>>) resultat.get("root"));
                    //System.out.println(resultat.get("root"));

                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cR);
        return retTgArt;

    }

    public boolean supprimerCommentiareB(int id) {
        retSupp = null;
        String url = "http://127.0.0.1:8000/blog/supprimerC/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url + id);
        cR.addArgument("mobile", "1");
        
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                System.out.println(buff.length);
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");
                String resultat = new String(buff, "UTF-8");
                if (! resultat.isEmpty()) {
                    retSupp = resultat;
                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                System.out.println("UnsupportedEncoding");
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                System.out.println("IOException");
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cR);
        if (retSupp.equals("1")) {
        return  true;
        }
        else return false;

    }
    public CommentaireB ajouterCommentaireB(Article article, CommentaireB commentaireB) {
        if (commentaireB.getText().equals(""))
            return null;
        String url = "http://127.0.0.1:8000/blog/lireArticle/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url + article.getId());
        cR.addArgument("mobile", "1");
        cR.addArgument("ctext", commentaireB.getText());
        cR.addArgument("userid", new Integer(commentaireB.getAuteur()).toString());
        cR.addArgument("username", commentaireB.getAuteurn());
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                System.out.println(buff.length);


                String resultat = new String(buff, "UTF-8");
                System.out.println(resultat);
                if (! resultat.isEmpty()) {
                  
                   
                    commentaireB.setId(Integer.parseInt(resultat));
                    System.out.println(commentaireB.getId());
                  

                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cR);
        if (commentaireB.getId() > -1) {
            System.out.println(commentaireB);
        return  commentaireB;
        }
        else return null;

    }
    public boolean modifierCommentaire(CommentaireB commentaire) {
        retCModified = null;
        String url = "http://127.0.0.1:8000/blog/modifierC/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url + commentaire.getId());
        cR.addArgument("mobile", "1");
        cR.addArgument("ctext", commentaire.getText());
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                if (testStr.read() == 0) {
                    return;
                }

                String resultat = new String(buff, "UTF-8");
                if (!resultat.isEmpty()) {
                    //System.out.println(resultat);
                    retCModified = resultat;

                }
            } catch (UnsupportedEncodingException ex) {
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cR);
        return retCModified.equals("1") ? true : false;
    }

    public ArrayList<Article> findByText(String s) {
        retTxtArt = new ArrayList<>();
        String url = "http://127.0.0.1:8000/blog/listeArticles/";
        ConnectionRequest cR;
        cR = new ConnectionRequest(url);
        cR.addArgument("mobile", "1");
        cR.addArgument("rech", "1");
        cR.addArgument("text", s);
        cR.addResponseListener((e) -> {
            try {
                byte[] buff = cR.getResponseData();
                InputStreamReader testStr = new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8");

                if (testStr.read() == 0) {
                    return;
                }

                Map<String, Object> resultat = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(buff), "UTF-8"));
                //System.out.println(resultat);
                if (!resultat.isEmpty()) {

                    retTxtArt = parseArticles((ArrayList< Map< String, Object>>) resultat.get("root"));
                    //System.out.println(resultat.get("root"));

                    //System.out.println(resultat);
                    //System.out.println(((ArrayList)resultat.get("commentaires")).get(0));
                }
            } catch (UnsupportedEncodingException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);ystem.out.println(resultat.get("id"));
            } catch (IOException ex) {
                //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cR);
        return retTxtArt;

    }

    private ArrayList<CommentaireB> parseCommentaires(ArrayList<Map<String, Object>> obj, Article a) {
        ArrayList<CommentaireB> ret = null;
        if (obj.size() > 0) {
            ret = new ArrayList<>();
        }
        for (Map<String, Object> e : obj) {
            ret.add(parseCommentaire(e, a));
        }
        return ret;
    }

    private CommentaireB parseCommentaire(Map<String, Object> e, Article a) {
        CommentaireB c = new CommentaireB();
        c.setId(((Double) e.get("id")).intValue());
        c.setAuteur(((Double) e.get("auteur")).intValue());
        c.setAuteurn((String) e.get("auteurN"));
        c.setText((String) e.get("text"));
        c.setArticle(a);
        return c;
    }

    private Article parseArticle(Map<String, Object> resultat) {
        Article ret = new Article();
        ret.setId(((Double) resultat.get("id")).intValue());
        ret.setAuteurn((String) resultat.get("auteurN"));
        ret.setAuteur(((Double) resultat.get("auteur")).intValue());
        ret.setTexte((String) resultat.get("texte"));
        ret.setTitre((String) resultat.get("titre"));
        SimpleDateFormat dParser = new SimpleDateFormat("yy-mm-dd");
        try {
            ret.setCreated(dParser.parse((String) (resultat.get("created"))));
            //System.out.println(ret.getCreated());
        } catch (ParseException ex) {
            //Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultat.get("tags") != null) {
            ArrayList<Tag> tags = new ArrayList<>();
            ret.setTagCollection(parseTags((ArrayList<Map<String,Object>>) resultat.get("tags"), ret));

        }
        if (resultat.get("commentaires") != null) {
            ArrayList<CommentaireB> commentaires = new ArrayList<>();
            //System.out.println(((ArrayList<CommentaireB>)resultat.get("commentaires")).size());
            commentaires = parseCommentaires((ArrayList<Map<String, Object>>) resultat.get("commentaires"), ret);
            ret.setCommentaireBCollection(commentaires);
            //System.out.println(commentaires);
        }

        return ret;

    }

    private ArrayList<Article> parseArticles(ArrayList<Map<String, Object>> resultat) {
        ArrayList<Article> ret = new ArrayList<>();
        for (Map<String, Object> e : resultat) {
            ret.add(parseArticle(e));
        }
        return ret;
    }

    private ArrayList<Tag> parseTags(ArrayList<Map<String, Object>> obj, Article a) {
                ArrayList<Tag> ret = null;
        if (obj.size() > 0) {
            ret = new ArrayList<>();
        }
        for (Map<String, Object> e : obj) {
            ret.add(parseTag(e, a));
        }
        return ret;
    }

    private Tag parseTag(Map<String, Object> e, Article a) {
        Tag t = new Tag();
        t.setId(((Double) e.get("id")).intValue());
        t.setName((String)e.get("name"));
        return t;
    }

}
