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
public class ServerSerialization {

    public static ArrayList<ClientHandler>clients;
    public ServerSerialization() throws IOException {
        clients=new ArrayList<>();

    }

    public void serve() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1999);
        System.out.println("ServerSerialization is listening .....");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Đã kết nối với "+socket);
            ClientHandler clientHandler=new ClientHandler(socket);
            clients.add(clientHandler);
            clientHandler.start();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSerialization server = new ServerSerialization();
        server.serve();
    }
}
