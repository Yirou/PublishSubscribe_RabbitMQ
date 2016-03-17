/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magikbyte.samlldoode.meeting.main;

import com.magikbyte.samlldoode.meeting.Manager;
import com.magikbyte.samlldoode.meeting.views.ManagerView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author yirou besançon y'a rien, normandi
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        ManagerView view = ManagerView.getInstance();
        view.init();
        Manager.getInstance().init();
        Manager.getInstance().addObserver(view);

    }

}
