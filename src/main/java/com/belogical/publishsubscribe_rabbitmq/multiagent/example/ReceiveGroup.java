package com.belogical.publishsubscribe_rabbitmq.multiagent.example;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveGroup implements Runnable {

	private Long id;
	private SimpleAgent parent; 
	
	public ReceiveGroup(SimpleAgent parent, Long id, String group_name) throws Exception {
		this.id = id;
		this.parent = parent;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(group_name, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, group_name, "");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				handleMessages(message);
				
			}
		};
		channel.basicConsume(queueName, true, consumer);
				
	}

	private void handleMessages(String message) {
		System.out.println(" [x] Received '" + message + "'");
		
		if (message.equals("Welcome to group")) {
			parent.broadcast("group", id + " enters the group");
		}
		else if (message.equals("START")) {
			
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			
			try {
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
