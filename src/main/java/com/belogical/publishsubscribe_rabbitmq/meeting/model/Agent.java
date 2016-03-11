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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yirou
 */
public class Agent {

    private int id;
    private String name;
    private List<String> messages = new ArrayList<>();
    private Consumer consumer;
    private boolean online;
    private Connection connection;
    private Channel channel;
    private Groupe currentGroupe;

    public Agent(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public Agent(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setCurrentGroupe(Groupe currentGroupe) {
        this.currentGroupe = currentGroupe;
    }

    public Groupe getCurrentGroupe() {
        return currentGroupe;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
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
            channel.basicPublish(Manager.QUEUE_NAME, "", null, msg.getBytes("UTF-8"));
//            channel.close();
//            connection.close();

        } catch (IOException e) {
        }
    }


    public void logout() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void creerGroupe(String grp, String pwd) {
        String message = Manager.MSG_NEW_GROUPE + "," + this.id + "," + grp + "," + pwd;
        sendMessage(message);
    }

    public void connectToGroupeTopic() {
        try {
            channel.exchangeDeclare(this.currentGroupe.getName(), "topic");
//            consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope,
//                        AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    String message = new String(body, "UTF-8");
//                    System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
//                }
//            };
//            channel.basicConsume(this.currentGroupe.getName(), true, consumer);
        } catch (IOException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMsgToTopic(String msg) {
        try {

            channel.basicPublish(this.currentGroupe.getName(), this.currentGroupe.getName(), null, msg.getBytes());
            System.out.println("send " + msg);
        } catch (IOException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(Manager.QUEUE_NAME, "fanout");
            String message = "msg," + this.name + " est connecté au groupe";
            sendMsgToTopic(message);
        } catch (IOException | TimeoutException e) {
        }
    }

}
