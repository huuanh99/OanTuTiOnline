/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author huuan
 */
public class ServerLogin {

    public static ArrayList<ClientLogin>clients;
    public ServerLogin() throws IOException {
        clients=new ArrayList<>();
    }

    public void serve() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("ServerLogin is listening .....");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Đã kết nối với "+socket);
            ClientLogin clientLogin=new ClientLogin(socket);
            clients.add(clientLogin);
            clientLogin.start();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerLogin server = new ServerLogin();
        server.serve();
    }
}
