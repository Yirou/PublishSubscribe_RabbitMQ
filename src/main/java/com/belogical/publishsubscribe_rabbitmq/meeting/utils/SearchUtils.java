/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.utils;

import com.belogical.publishsubscribe_rabbitmq.meeting.Manager;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.Groupe;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.User;

/**
 *
 * @author yirou
 */
public class SearchUtils {
    
    public static User findUser(int id) {
        for (User u : Manager.getInstance().getUsers()) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public static User findUser(String name) {
        for (User u : Manager.getInstance().getUsers()) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    public static Groupe findGroupe(String name) {
        for (Groupe g : Manager.getInstance().getGroupes()) {
            if (g.getName().equalsIgnoreCase(name)) {
                return g;
            }
        }
        return null;
    }
}
