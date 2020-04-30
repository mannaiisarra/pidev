/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.myapp.entities.Livre;
import com.mycompany.myapp.entities.category;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class ServiceCategorie {
 public static ServiceCategorie instance=null;
   
      private ConnectionRequest connectionRequest;
    public static Form listOfBooks;
    public static Form listOfLivre;
     public static ServiceCategorie getInstance() {
        if (instance == null) {
            instance = new ServiceCategorie();
        }
        return instance;
    }

     public static ArrayList<category> getList2() {

        ArrayList<category> listEvents = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost/shelfie_phpScripts/getbooks.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listEvents = getListEvent(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> Events = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println("roooooot:" +Events.get("root"));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) Events.get("root");
                    System.out.println(list);
                    for (Map<String, Object> obj : list) {
                        category event = new category();
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        event.setId((int) id);
                        event.setLibelle(obj.get("libelle").toString());
                        
                        event.setDescription(obj.get("description").toString());
                        event.setNom_image((String) obj.get("nom_image"));

                       
                        listEvents.add(event);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listEvents;
    }
     public static ArrayList<Livre> getMyListcl() {

        ArrayList<Livre> listLivre = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://localhost/shelfie_phpScripts/getlivre.php/");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listLivre = getListEvent(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> Events = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println("roooooot:" +Events.get("root"));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) Events.get("root");
                    
                    for (Map<String, Object> obj : list) {
                        Livre event = new Livre();


                       event.setNom(obj.get("nom").toString());
                        listLivre.add(event);}

                    
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listLivre;
    }
     public static Livre getDetailEvent(int id) {
                               Livre event = new Livre();

        ConnectionRequest con2 = new ConnectionRequest();
        con2.setUrl("http://localhost/shelfie_phpScripts/getlivre.php/"+id);
        con2.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con2.getResponseData()).toCharArray()));
                    System.out.println("roooooot:" + tasks);

                    //List<Map<String, Object>> list = (List<Map<String, Object>>) tasks;
                     
                     float nombredepage = Float.parseFloat(tasks.get("nombredepage").toString());
                     Map<String, Object> userMap= (Map<String, Object>) tasks.get("id_category");
                                          float id_category = Float.parseFloat(userMap.get("id_category").toString());
                                            event.setId_category((int) id_category);
                            event.setId((int) id);
                            event.setNom((String) tasks.get("nom"));
                            event.setDescription((String) tasks.get("description"));
                            event.setAuteur((String) tasks.get("auteur"));
                            event.setNombredepage((int) tasks.get("nombredepage"));
                            event.setNom_image((String) tasks.get("nom_image"));
                            
                            
                } catch (IOException ex) {
                }

            }
        });
                                System.out.println(event);

        NetworkManager.getInstance().addToQueueAndWait(con2);
        return event;

    }    
           }

