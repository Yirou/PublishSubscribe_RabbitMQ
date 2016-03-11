/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.model;

import com.belogical.publishsubscribe_rabbitmq.meeting.Manager;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author yirou
 */
public class Groupe extends Observable {

    private List<User> users = new ArrayList<>();
    private User admin;
    private String name;
    private String password;
    private Date begin;
    private Date deadLine;
    private List<String> messages = new ArrayList<>();
    private List<String> proposedDate = new ArrayList<>();
    private Map<String, List<String>> selectedDate = new HashMap<>();
    private Connection connection;
    private Channel channel;

    public Groupe(String name, String password, User admin) {
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    public User getAdmin() {
        return admin;
    }

    public List<String> getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(List<String> proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Map<String, List<String>> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Map<String, List<String>> selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Groupe(String name, String password, Date begin, Date deadLine) {
        this.name = name;
        this.password = password;
        this.begin = begin;
        this.deadLine = deadLine;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void sendMessage(String msg) {
        try {
            channel.basicPublish(Manager.EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
        }
    }

    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(this.name, "topic");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, this.name, this.name);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String[] msg;
                    String message = new String(body, "UTF-8");
                    if (message.startsWith("msg,")) {
                        messages.add(message);
                        
                    } else if (message.startsWith("dateAdmin")) {
                        message = message.replace("dateAdmin,", "");
                        proposedDate.add(message.split(",")[1]);
                    } else if (message.startsWith("dateUser")) {
                        System.out.println("YEs yes yes ");
                        message = message.replace("dateUser,", "");
                        msg = message.split(",");
                        List<String> mesMessages = selectedDate.get(msg[0].trim());
                        if (mesMessages == null) {
                            mesMessages = new ArrayList<>();
                        }
                        mesMessages.add(msg[1]);
                        selectedDate.put(msg[0].trim(), mesMessages);
                    }

                    setChanged();
                    notifyObservers();
//                    System.out.println(message);
                    System.out.println(" [x] Received " + message);
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}
