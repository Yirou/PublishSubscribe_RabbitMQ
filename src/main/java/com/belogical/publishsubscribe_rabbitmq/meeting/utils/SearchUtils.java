/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.utils;

import com.belogical.publishsubscribe_rabbitmq.meeting.Manager;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.Groupe;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.Agent;

/**
 *
 * @author yirou
 */
public class SearchUtils {
    
    public static Agent findUser(int id) {
        for (Agent u : Manager.getInstance().getUsers()) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public static Agent findUser(String name) {
        for (Agent u : Manager.getInstance().getUsers()) {
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
