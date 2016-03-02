package com.belogical.publishsubscribe_rabbitmq.multiagent.example;

import com.belogical.publishsubscribe_rabbitmq.multiagent.core.Agent;
import java.util.Scanner;

public class SimpleAgent extends Agent {

    public SimpleAgent(long id, String name, String group_name) {
        super(id, name);
        try {
            new Thread(new ReceiveGroup(this, id, group_name));
            new Thread(new ReceiveSingle(this, id, name));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void run() {
        while (isRunning) {
            //checking messages
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        1,yirou,group
        System.out.println("Enter command");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String[] commands = command.split(",");
        new SimpleAgent(new Long(commands[0]), commands[1], commands[2]);
    }
}
