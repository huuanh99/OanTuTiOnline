/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.User;
import model.sendObject;

/**
 *
 * @author huuan
 */
public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    String choice = "none";
    static User user;
    double score = 0;
    ObjectOutputStream oos;
    Socket socket;
    ObjectInputStream ois;
    int time = 15;
    Timer timer;
    ArrayList<sendObject> list;

    public home(User user1) throws IOException {
        initComponents();
        setSize(700, 450);
        ImageIcon background_login = new ImageIcon("background-login.png");
        JLabel back = new JLabel("", background_login, JLabel.CENTER);
        back.setBounds(-300, -220, 1200, 700);
        add(back);
        ImageIcon icon1 = new ImageIcon("bao.png");
        bao.setIcon(icon1);
        ImageIcon icon2 = new ImageIcon("keo.png");
        keo.setIcon(icon2);
        ImageIcon icon3 = new ImageIcon("bua.png");
        bua.setIcon(icon3);
        user = user1;
        socket = new Socket("localhost", 1999);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        Thread clientRead;
        clientRead = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        list = (ArrayList<sendObject>) ois.readObject();
                        if (list.get(0).getMessage().equals("win") || list.get(0).getMessage().equals("lose")
                                || list.get(0).getMessage().equals("draw")) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getUser().getUserName().equals(user.getUserName())) {
                                    if (list.get(i).getMessage().equals("win")) {
                                        JOptionPane.showMessageDialog(rootPane, "Bạn đã giành chiến thắng");
                                        score = 1;
                                        user = new User(user.getId(), user.getUserName(), user.getPassword(),
                                                user.getPoint() + score, user.getWin() + 1, user.getStatus());
                                        sendObject object = new sendObject(user, "update");
                                        oos.writeObject(object);
                                        sendObject object1 = new sendObject(user, "connect");
                                        oos.writeObject(object1);
                                    } else if (list.get(i).getMessage().equals("lose")) {
                                        JOptionPane.showMessageDialog(rootPane, "Bạn đã thất bại");
                                        score = 0;
                                        user = new User(user.getId(), user.getUserName(), user.getPassword(),
                                                user.getPoint() + score, user.getWin(), user.getStatus());
                                        sendObject object = new sendObject(user, "update");
                                        oos.writeObject(object);
                                        sendObject object1 = new sendObject(user, "connect");
                                        oos.writeObject(object1);
                                    } else {
                                        JOptionPane.showMessageDialog(rootPane, "Bạn đã hòa với đối thủ");
                                        score = 0.5;
                                        user = new User(user.getId(), user.getUserName(), user.getPassword(),
                                                user.getPoint() + score, user.getWin(), user.getStatus());
                                        sendObject object = new sendObject(user, "update");
                                        oos.writeObject(object);
                                        sendObject object1 = new sendObject(user, "connect");
                                        oos.writeObject(object1);
                                    }
                                    score = 0;
                                    time = 15;
                                    timer.start();
                                }
                            }
                        } else if (list.get(0).getMessage().equals("exit")
                                && !list.get(0).getUser().getUserName().equals(user.getUserName())) {
                            timer.stop();
                            JOptionPane.showMessageDialog(rootPane, "Đối phương đã thoát");
                            if (table.bangxephang == null) {
                                table.bangxephang = new bxh(user);
                            }
                            table.bangxephang.setVisible(true);
                            time = 15;
                            close.doClick();
                            
                            
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        clientRead.start();
        clock.setText(String.valueOf(time));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clock.setText(String.valueOf(time));
                time--;
                if (time == 0) {
                    timer.stop();
                    clock.setText("HẾT GIỜ");
                    send.doClick();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bao = new javax.swing.JButton();
        keo = new javax.swing.JButton();
        bua = new javax.swing.JButton();
        send = new javax.swing.JButton();
        thoat = new javax.swing.JButton();
        clock = new javax.swing.JLabel();
        close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baoActionPerformed(evt);
            }
        });

        keo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keoActionPerformed(evt);
            }
        });

        bua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buaActionPerformed(evt);
            }
        });

        send.setText("GỬI");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        thoat.setText("THOÁT");
        thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thoatActionPerformed(evt);
            }
        });

        clock.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        clock.setForeground(new java.awt.Color(255, 255, 255));

        close.setText("CLOSE");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(send)
                    .addComponent(bao, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(keo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(bua, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(thoat)
                                .addGap(102, 102, 102)
                                .addComponent(close)))))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(keo, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(bao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(99, 99, 99)
                .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(send)
                    .addComponent(thoat)
                    .addComponent(close))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void baoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baoActionPerformed
        choice = "bao";
    }//GEN-LAST:event_baoActionPerformed

    private void keoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keoActionPerformed
        // TODO add your handling code here:
        choice = "keo";
    }//GEN-LAST:event_keoActionPerformed

    private void buaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buaActionPerformed
        // TODO add your handling code here:
        choice = "bua";
    }//GEN-LAST:event_buaActionPerformed

    private void thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thoatActionPerformed
        try {
            // TODO add your handling code here:
            timer.stop();
            time = 15;
            sendObject object1 = new sendObject(user, "exit");
            oos.writeObject(object1);
            this.dispose();
            if (table.bangxephang == null) {
                table.bangxephang = new bxh(user);
            }
            table.bangxephang.setVisible(true);
            
        } catch (IOException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_thoatActionPerformed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        try {
            // TODO add your handling code here:
            timer.stop();
            time = 15;
            sendObject object = new sendObject(user, choice);
            oos.writeObject(object);
            choice = "none";
        } catch (IOException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_sendActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeActionPerformed

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
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new home(user).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bao;
    private javax.swing.JButton bua;
    private javax.swing.JLabel clock;
    private javax.swing.JButton close;
    private javax.swing.JButton keo;
    private javax.swing.JButton send;
    private javax.swing.JButton thoat;
    // End of variables declaration//GEN-END:variables

}
