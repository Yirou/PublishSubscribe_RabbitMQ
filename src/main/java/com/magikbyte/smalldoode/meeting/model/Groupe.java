/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magikbyte.smalldoode.meeting.model;

import com.magikbyte.smalldoode.meeting.time.TimerDeadLine;
import com.magikbyte.smalldoode.meeting.Manager;
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

    private Agent admin;
    private String name;
    private String password;
    private Date begin;
    private int deadLine;
    private List<Agent> users = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private List<String> dateProposeParAdmin = new ArrayList<>();
    private Map<String, List<String>> dateProposeParLesAgents = new HashMap<>();
    private int maxChoixPossiblePourUneReunion = 5;
    private TimerDeadLine timer;

    private Connection connection;
    private Channel channel;

    public Groupe(String name, String password, int deadLine, Agent admin, TimerDeadLine timer) {
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.timer = timer;
        this.deadLine = deadLine;
    }

    public void setTimer(TimerDeadLine timer) {
        this.timer = timer;
    }

    public TimerDeadLine getTimer() {
        return timer;
    }

    public Agent getAdmin() {
        return admin;
    }

    public List<String> getDateProposeParAdmin() {
        return dateProposeParAdmin;
    }

    public void setDateProposeParAdmin(List<String> dateProposeParAdmin) {
        this.dateProposeParAdmin = dateProposeParAdmin;
    }

    public Map<String, List<String>> getDateProposeParLesAgents() {
        return dateProposeParLesAgents;
    }

    public void setDateProposeParLesAgents(Map<String, List<String>> dateProposeParLesAgents) {
        this.dateProposeParLesAgents = dateProposeParLesAgents;
    }

    public int getMaxChoixPossiblePourUneReunion() {
        return maxChoixPossiblePourUneReunion;
    }

    public void setMaxChoixPossiblePourUneReunion(int maxChoixPossiblePourUneDate) {
        this.maxChoixPossiblePourUneReunion = maxChoixPossiblePourUneDate;
    }

    public void setAdmin(Agent admin) {
        this.admin = admin;
    }

    public Groupe(String name, String password, Date begin, int deadLine) {
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

    public int getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(int deadLine) {
        this.deadLine = deadLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Agent> getUsers() {
        return users;
    }

    public void setUsers(List<Agent> users) {
        this.users = users;
    }

    public void sendMessage(String msg) {
        try {
            channel.basicPublish(Manager.QUEUE_NAME, "", null, msg.getBytes("UTF-8"));
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

                        if (dateProposeParAdmin.size() < maxChoixPossiblePourUneReunion) {
                            message = message.replace("dateAdmin,", "");
                            dateProposeParAdmin.add(message.split(",")[1]);
                        }

                    } else if (message.startsWith("dateUser")) {
                        message = message.replace("dateUser,", "");
                        msg = message.split(",");
                        List<String> mesMessages = dateProposeParLesAgents.get(msg[0].trim());
                        if (mesMessages == null) {
                            mesMessages = new ArrayList<>();
                        }
                        mesMessages.add(msg[1]);
                        dateProposeParLesAgents.put(msg[0].trim(), mesMessages);
                    }

                    setChanged();
                    notifyObservers();
                    System.out.println(" [x] Received " + message);
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void acceptConnexion(Agent user) {
        user.setOnline(true);
        user.setCurrentGroupe(this);
        user.init();
        user.connectToGroupeTopic();
        this.getUsers().add(user);

    }

}
