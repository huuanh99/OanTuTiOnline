/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm;

import java.awt.event.MouseEvent;
import model.User;
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
import javax.swing.table.DefaultTableModel;
import model.sendObject;

/**
 *
 * @author huuan
 */
public class table extends javax.swing.JFrame {

    /**
     * Creates new form table
     */
    ObjectOutputStream oos;
    Socket socket;
    ObjectInputStream ois;
    static User user;
    static home home = null;
    static bxh bangxephang = null;

    public table(User user1) throws IOException, ClassNotFoundException, InterruptedException {
        initComponents();
        setLayout(null);
        setSize(700, 450);
        ImageIcon background_login = new ImageIcon("background-login.png");
        JLabel back = new JLabel("", background_login, JLabel.CENTER);
        back.setBounds(-300, -220, 1200, 700);
        add(back);
        socket = new Socket("localhost", 1999);
        oos = new ObjectOutputStream(socket.getOutputStream());
        user = user1;
        sendObject object = new sendObject(user, "connect");
        oos.writeObject(object);
        ois = new ObjectInputStream(socket.getInputStream());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
                User user1 = new User(1, username, username, 1, 1, username);
                sendObject object1 = new sendObject(user, "challenge");
                sendObject object2 = new sendObject(user1, "challenge");
                try {
                    oos.writeObject(object2);
                    oos.writeObject(object1);
                } catch (IOException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        Thread clientRead;
        clientRead = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        ArrayList<sendObject> list = (ArrayList<sendObject>) ois.readObject();
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        if (list.get(0).getMessage().equals("connect")) {
                            model.setRowCount(0);
                            for (int i = 0; i < list.size(); i++) {
                                if (!list.get(i).getUser().getUserName().equals(user.getUserName())) {
                                    String data[] = {list.get(i).getUser().getUserName(),
                                        String.valueOf(list.get(i).getUser().getPoint()),
                                        list.get(i).getUser().getStatus()};
                                    model.addRow(data);
                                }
                            }
                            System.out.println("Receive connect");
                        } else if (list.get(0).getMessage().equals("challenge")) {
                            if (list.get(0).getUser().getUserName().equals(user.getUserName())) {
                                int confirm = JOptionPane.showConfirmDialog(rootPane, "Bạn có muốn nhận lời thách đấu từ "
                                        + list.get(1).getUser().getUserName());
                                if (confirm == JOptionPane.YES_OPTION) {
                                    if (home == null) {
                                        home = new home(user);
                                    }
                                    home.setVisible(true);
                                    home.timer.start();
                                    thoat.doClick();
                                    sendObject object1 = new sendObject(list.get(1).getUser(), "accept");
                                    oos.writeObject(object1);   
                                }
                            }
                        } else if (list.get(0).getMessage().equals("accept")
                                && list.get(0).getUser().getUserName().equals(user.getUserName())) {
                            if (home == null) {
                                home = new home(user);
                            }
                            home.setVisible(true);
                            home.timer.start();
                            thoat.doClick();
                            
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

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        thoat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        bxh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Tổng số điểm", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(table);

        thoat.setText("Thoát");
        thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thoatActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DANH SÁCH ONLINE");

        bxh.setText("BXH");
        bxh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bxhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(thoat)
                        .addGap(101, 101, 101)
                        .addComponent(bxh, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thoat)
                    .addComponent(bxh))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_thoatActionPerformed

    private void bxhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bxhActionPerformed
        try {
            // TODO add your handling code here:
            if (bangxephang == null) {
                bangxephang = new bxh(user);
            }
            bangxephang.setVisible(true);
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bxhActionPerformed

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
            java.util.logging.Logger.getLogger(table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new table(user).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(table.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bxh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JButton thoat;
    // End of variables declaration//GEN-END:variables
}
