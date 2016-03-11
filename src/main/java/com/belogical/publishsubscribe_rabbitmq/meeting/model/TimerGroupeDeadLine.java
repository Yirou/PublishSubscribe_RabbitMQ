/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.model;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yirou
 */
public class TimerGroupeDeadLine extends Observable implements Runnable {

    private Thread thread;
    private Groupe groupe;
    private boolean alive = true;

    public TimerGroupeDeadLine(Groupe groupe) {
        thread = new Thread(this);
        this.groupe = groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Thread getThread() {
        return thread;
    }

    public boolean isAlive() {
        return alive;
    }
    

    @Override
    public void run() {
        try {
            while (alive) {
                Thread.sleep(1000);
                int date = this.groupe.getDeadLine();
                if (date > 0) {
                    this.groupe.setDeadLine(date - 1);
                } else {
                    alive = false;
                }
                this.setChanged();
                this.notifyObservers();

            }

        } catch (InterruptedException ex) {
            Logger.getLogger(TimerGroupeDeadLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
