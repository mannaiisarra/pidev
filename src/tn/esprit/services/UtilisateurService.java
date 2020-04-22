/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entite.Utilisateur;

/**
 *
 * @author ASUS
 */
public class UtilisateurService {
    ConnectionRequest con;
    public  ArrayList<Utilisateur> selectAllEnabled() {
        String strUrl="http://127.0.0.1:8000/utilisateur/alljson";
        ArrayList<Utilisateur> lstUsers = new ArrayList<>();
        System.out.println("Service Utilisateur");
        try {
           
                 con = new ConnectionRequest(strUrl);                
                 con.setPost(false);        
                con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> users = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                  
                    List<Map<String, Object>> list = (List<Map<String, Object>>) users.get("root");
                    for (Map<String, Object> obj : list) {
                        Utilisateur user = new Utilisateur(obj);                        
                        lstUsers.add(user);                  
                    }
                } catch (IOException ex) {
                    System.out.println("exxxx");
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
            System.out.println(lstUsers);
        return lstUsers;
        } catch (Exception err) {
            
                  Log.e(err);
            return null;
        }               
    }   
}
