/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting;

import com.belogical.publishsubscribe_rabbitmq.meeting.model.Groupe;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.Agent;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.TimerDeadLine;
import com.belogical.publishsubscribe_rabbitmq.meeting.utils.SearchUtils;
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
import java.util.Observable;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author yirou
 */
public class Manager extends Observable {

    public static final String QUEUE_NAME = "groupes";
    public static final String MSG_NEW_USER = "new_user";
    public static final String MSG_NEW_GROUPE = "new_groupe";
    private List<Groupe> groupes = new ArrayList<>();
    private List<Agent> users = new ArrayList<>();
    private static final Manager INSTANCE = new Manager();
    private Consumer consumer;

    private Manager() {
    }

    public static Manager getInstance() {
        return INSTANCE;
    }

    public List<Agent> getUsers() {
        return users;
    }

    public void setUsers(List<Agent> users) {
        this.users = users;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                        AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    dispatchMessage(message);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException | TimeoutException e) {
        }
    }

    private void dispatchMessage(String message) {
        String[] messages = message.split(",");
        if (message.startsWith(MSG_NEW_USER)) {
            users.add(new Agent(users.size() + 1, messages[1]));
            this.setChanged();
            this.notifyObservers();
            System.out.println(" [x] New User '" + messages[1] + "'");
        } else if (message.startsWith(MSG_NEW_GROUPE)) {
            System.out.println(message);
            int deaLine = Integer.parseInt(messages[3]);
            Agent admin = SearchUtils.findUser(messages[2]);
            TimerDeadLine timer = new TimerDeadLine(null);
            Groupe groupe = new Groupe(messages[1], "", deaLine * 60, admin, timer);
            groupe.init();
            timer.setGroupe(groupe);
            timer.getThread().start();
            this.getGroupes().add(groupe);
            this.setChanged();
            this.notifyObservers();
        }
    }

}
