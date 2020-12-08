/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ltm.DAO;
import model.Account;
import model.User;

/**
 *
 * @author huuan
 */
public class ClientLogin extends Thread{
    private Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    DAO dao=new DAO();
    public ClientLogin(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {            
            try {
                Account account=(Account)ois.readObject();
                User user=dao.logIn(account.getUsername(), account.getPassword());
                oos.writeObject(user);
            } catch (IOException | ClassNotFoundException ex) {
                break;
            }
        }
    }
    
    
}
