/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.model;

import com.belogical.publishsubscribe_rabbitmq.meeting.Manager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author yirou
 */
public class User {
    
    private int id;
    private String name;
    List<String> messages = new ArrayList<>();
    List<Groupe> abonnes = new ArrayList<>();
    private Consumer consumer;
    private boolean online;
    Connection connection;
    Channel channel;
    
    public User(int id, String name) {
        this.id = id;
        this.name = name;
        init();
    }
    
    public User(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public List<String> getMessages() {
        return messages;
    }
    
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
    public List<Groupe> getAbonnes() {
        return abonnes;
    }
    
    public void setAbonnes(List<Groupe> abonnes) {
        this.abonnes = abonnes;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void sendMessage(String msg) {
        try {
            channel.basicPublish(Manager.EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
        }
    }
    
    public void creerGroupe(String grp) {
        String message = Manager.MSG_NEW_GROUPE + "," + this.id + "," + grp;
        sendMessage(message);
    }
    
    private void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(Manager.EXCHANGE_NAME, "fanout");
            String message = Manager.MSG_NEW_USER + "," + this.id + "," + this.name;
            sendMessage(message);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    
}
