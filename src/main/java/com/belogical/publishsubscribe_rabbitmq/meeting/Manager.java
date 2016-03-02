/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting;

import com.belogical.publishsubscribe_rabbitmq.meeting.model.Groupe;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author yirou
 */
public class Manager {
    
    public static final String EXCHANGE_NAME = "groupes";
    public static final String MSG_NEW_USER = "new_user";
    public static final String MSG_NEW_GROUPE = "new_groupe";
    private List<Groupe> groupes = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private final Groupe defaultGroupe;
    private static final Manager instance = new Manager(null);
    private Thread thread;
    private Consumer consumer;
    
    private Manager(Groupe defaultGroupe) {
        this.defaultGroupe = defaultGroupe;
//        thread = new Thread(this);
//        thread.start();
    }
    
    public static Manager getInstance() {
        return instance;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public Groupe getDefaultGroupe() {
        return defaultGroupe;
    }
    
    public List<Groupe> getGroupes() {
        return groupes;
    }
    
    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
    
    private void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    dispatchMessage(message);
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException | TimeoutException e) {
        }
    }
    
    private void dispatchMessage(String message) {
        String[] messages;
        if (message.startsWith(MSG_NEW_USER)) {
            messages = message.split(",");
            User user = new User(messages[2]);
            user.setId(this.users.size() + 1);
            users.add(user);
            System.out.println(" [x] New User '" + user.getName() + "'");
        }
    }


    public static void main(String[] args) {
        Manager manager = Manager.getInstance();
        manager.init();
    }
}
