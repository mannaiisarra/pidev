/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.util;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import tn.esprit.securite.User;

/**
 *
 * @author ASUS
 */
public class Mydb {
    Database db;
    Cursor cur;
    final String createStr="create table user ( id INTEGER,login TEXT, pass TEXT);";
    
    public Mydb() {
    }

    public Mydb(Database db, Cursor cur) {
        this.db = db;
        this.cur = cur;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public Cursor getCur() {
        return cur;
    }

    public void setCur(Cursor cur) {
        this.cur = cur;
    }
    public void insert(User u){
        
    }
    public void  create(){
                
    }
}
