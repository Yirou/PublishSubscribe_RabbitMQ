/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belogical.publishsubscribe_rabbitmq.meeting.views;

import com.belogical.publishsubscribe_rabbitmq.meeting.model.Groupe;
import com.belogical.publishsubscribe_rabbitmq.meeting.model.User;
import com.belogical.publishsubscribe_rabbitmq.meeting.utils.SearchUtils;
import com.toedter.calendar.JDateChooser;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yirou
 */
public class ConnetedView extends javax.swing.JFrame implements Observer {

    User user;
    Groupe groupe;
    ManagerView managerView;
    JDateChooser dateChooser;
    JDateChooser datePropose;

    /**
     * Creates new form ConnetedView
     */
    public ConnetedView(String grp, String user, ManagerView managerView) {
        initComponents();
        this.managerView = managerView;
        this.user = SearchUtils.findUser(user);
        userNameTxt.setText(this.user.getName());
        this.groupe = SearchUtils.findGroupe(grp);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        proposePanelDate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        dateChooser = new JDateChooser();
        datePropose = new JDateChooser();
        jPanel2.add(dateChooser);
        proposePanelDate.add(datePropose);

        checkAccess();
    }

    private void initPaneldate() {

    }

    public void setManagerView(ManagerView managerView) {
        this.managerView = managerView;
    }

    public ManagerView getManagerView() {
        return managerView;
    }

    private void checkAccess() {
        if (user.isOnline()) {
            JOptionPane.showMessageDialog(null, "Utilisateur dejà connecté");
        } else if (groupe.getAdmin().equals(this.user)) {
            String pwd = JOptionPane.showInputDialog("veuillez saisir le mot de passe");
            if (pwd.equalsIgnoreCase(groupe.getPassword())) {
                acceptConnexion();
            } else {
                JOptionPane.showMessageDialog(null, "Mot de passe incorrecte");
                this.dispose();
            }
        } else {
            dateChooser.setEnabled(false);
            acceptConnexion();
        }
    }

    private void acceptConnexion() {
        this.setVisible(true);
        user.setOnline(true);
        this.user.setCurrentGroupe(groupe);
        groupe.addObserver(this);
        this.user.init();
        user.connectToGroupeTopic();
        this.groupe.getUsers().add(user);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDate = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        msgTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listMsg = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        proposedDateTxt = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        userNameTxt = new javax.swing.JLabel();
        proposePanelDate = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelDate.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout panelDateLayout = new javax.swing.GroupLayout(panelDate);
        panelDate.setLayout(panelDateLayout);
        panelDateLayout.setHorizontalGroup(
            panelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        panelDateLayout.setVerticalGroup(
            panelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDateLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        msgTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgTxtActionPerformed(evt);
            }
        });

        jButton1.setText("Add date");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        listMsg.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Coucou" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listMsg);

        jLabel2.setFont(new java.awt.Font("URW Chancery L", 0, 15)); // NOI18N
        jLabel2.setText("your message");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        proposedDateTxt.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        proposedDateTxt.setForeground(new java.awt.Color(29, 155, 228));
        proposedDateTxt.setText("jLabel1");

        jLabel1.setText("Dates possibles");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(proposedDateTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(proposedDateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        userNameTxt.setText("jLabel1");

        proposePanelDate.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout proposePanelDateLayout = new javax.swing.GroupLayout(proposePanelDate);
        proposePanelDate.setLayout(proposePanelDateLayout);
        proposePanelDateLayout.setHorizontalGroup(
            proposePanelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );
        proposePanelDateLayout.setVerticalGroup(
            proposePanelDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        jButton2.setText("Propose");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Calcul resultat");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proposePanelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2)))
                .addContainerGap(287, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(userNameTxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(msgTxt)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userNameTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(msgTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2))
                        .addComponent(proposePanelDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msgTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgTxtActionPerformed
        String msg = "msg," + user.getName() + " >> " + msgTxt.getText();
        msgTxt.setText("");
        user.sendMsgToTopic(msg);
    }//GEN-LAST:event_msgTxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (dateChooser.getDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date d = dateChooser.getDate();
            String msg = "dateAdmin," + user.getName() + " , " + formatter.format(d);
            user.sendMsgToTopic(msg);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (datePropose.getDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date d = datePropose.getDate();
            String msg = "dateUser," + user.getName() + " , " + formatter.format(d);
            user.sendMsgToTopic(msg);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Map<String, Integer> result = new HashMap<>();
        List<String> userProposition;
        for (String key : groupe.getSelectedDate().keySet()) {
            userProposition = groupe.getSelectedDate().get(key);
            for (String date : userProposition) {
                if (result.get(date) == null) {
                    result.put(date, 1);
                } else {
                    int occurence = result.get(date);
                    occurence++;
                    result.put(date, occurence);
                }
            }
        }

        String dateRetenue = findMax(result);
        String msg = "msg," + user.getName() + " >> Date retenue : " + dateRetenue;
        user.sendMsgToTopic(msg);


    }//GEN-LAST:event_jButton3ActionPerformed
    private String findMax(Map<String, Integer> result) {
        int max = 0;
        String date = "";
        for (String key : result.keySet()) {
            if (result.get(key) > max) {
                max = result.get(key);
                date = key;
            }
        }
        return date;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Groupe) {
            System.out.println("update msg");
            displayMsgs();
            displayDates();
        }
    }

    private void displayDates() {
        StringBuilder msg = new StringBuilder();
        List<String> dateUser;
        String[] column = {"User", "Date1", "Date2", "Date3"};
        jTable1.setModel(new DefaultTableModel(column, groupe.getSelectedDate().size()));
        for (String m : this.groupe.getProposedDate()) {
            msg.append(m + " | ");
        }
        proposedDateTxt.setText(msg.toString());

        for (User u : groupe.getUsers()) {
            dateUser = this.groupe.getSelectedDate().get(u.getName());
            if (dateUser != null) {
                displayInTable(u, dateUser);
            }

        }

    }

    private void displayInTable(User u, List<String> dateUser) {
        int line = u.getId() - 1;
        jTable1.setValueAt(u.getName(), line, 0);
        for (int i = 0; i < dateUser.size(); i++) {
            jTable1.setValueAt(dateUser.get(i), line, i + 1);
        }
    }

    private String[] objectListToStringArray(List<?> liste, String toDelete) {
        String[] result = new String[liste.size()];
        for (int i = 0; i < liste.size(); i++) {
            String msg = liste.get(i).toString();
            if (msg.startsWith(toDelete)) {

                result[i] = msg.replace(toDelete, "");
            }

        }
        return result;
    }

    private void displayMsgs() {
        String[] msg = objectListToStringArray(groupe.getMessages(), "msg,");
        listMsg.setListData(msg);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnetedView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnetedView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnetedView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnetedView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnetedView(null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JList<String> listMsg;
    private javax.swing.JTextField msgTxt;
    private javax.swing.JPanel panelDate;
    private javax.swing.JPanel proposePanelDate;
    private javax.swing.JLabel proposedDateTxt;
    private javax.swing.JLabel userNameTxt;
    // End of variables declaration//GEN-END:variables

}