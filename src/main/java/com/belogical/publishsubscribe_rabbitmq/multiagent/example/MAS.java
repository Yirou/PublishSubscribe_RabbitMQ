package com.belogical.publishsubscribe_rabbitmq.multiagent.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MAS {

	private static final String EXCHANGE_NAME = "group";

	public MAS() {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection;
		try {
			connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		    String message = "Welcome to group";

		    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");

		    message = "START";

		    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
		    System.out.println(" [x] Sent '" + message + "'");

		} 
		catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MAS();
	}
}
