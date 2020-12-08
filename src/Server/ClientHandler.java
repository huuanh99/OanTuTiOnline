/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ltm.DAO;
import model.User;
import model.sendObject;

/**
 *
 * @author huuan
 */
public class ClientHandler extends Thread {

    private Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    DataInputStream dis;
    static ArrayList<sendObject> listConnect = new ArrayList<>();
    static ArrayList<sendObject> listChallenge = new ArrayList<>();
    static ArrayList<sendObject> listAccept = new ArrayList<>();
    static ArrayList<sendObject> listPlay = new ArrayList<>();
    static ArrayList<sendObject> listResult = new ArrayList<>();
    static ArrayList<sendObject> listExit = new ArrayList<>();
    DAO dao = new DAO();

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendObject object = (sendObject) ois.readObject();
                if (object.getMessage().equals("connect")) {
                    int a = 0;
                    for (int i = 0; i < listConnect.size(); i++) {
                        if (listConnect.get(i).getUser().getUserName().equals(object.getUser().getUserName())) {
                            a = 1;
                            listConnect.get(i).getUser().setStatus(object.getUser().getStatus());
                            listConnect.get(i).getUser().setPoint(object.getUser().getPoint());
                        }
                    }
                    if (a == 0) {
                        listConnect.add(object);
                    }
                    for (ClientHandler aClient : ServerSerialization.clients) {
                        aClient.oos.reset();
                        aClient.oos.writeObject(listConnect);
                    }
                    System.out.println("Send list connect");
                } else if (object.getMessage().equals("challenge")) {
                    System.out.println("Receive challenge:" + object.getUser().getUserName() + object.getMessage());
                    listChallenge.add(object);
                    System.out.println("List challenge size:" + listChallenge.size());
                    if (listChallenge.size() >= 2) {
                        for (ClientHandler aClient : ServerSerialization.clients) {
                            aClient.oos.reset();
                            aClient.oos.writeObject(listChallenge);
                        }
                        System.out.println("Send challenge to user" + listChallenge.get(0).getUser().getUserName()
                                + listChallenge.get(1).getUser().getUserName());
                        listChallenge.clear();
                    }
                } else if (object.getMessage().equals("accept")) {
                    listAccept.add(object);
                    for (ClientHandler aClient : ServerSerialization.clients) {
                        aClient.oos.reset();
                        aClient.oos.writeObject(listAccept);
                    }
                    listAccept.clear();
                } else if (object.getMessage().equals("bua") || object.getMessage().equals("keo")
                        || object.getMessage().equals("bao") || object.getMessage().equals("none")) {
                    listPlay.add(object);
                    if (listPlay.size() >= 2) {
                        sendObject object1;
                        sendObject object2;
                        if (listPlay.get(0).getMessage().equals("none")) {
                            if (!listPlay.get(1).getMessage().equals("none")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "lose");
                                object2 = new sendObject(listPlay.get(1).getUser(), "win");
                            } else {
                                object1 = new sendObject(listPlay.get(0).getUser(), "lose");
                                object2 = new sendObject(listPlay.get(1).getUser(), "lose");
                            }
                        } else if (listPlay.get(0).getMessage().equals("bua")) {
                            if (listPlay.get(1).getMessage().equals("keo") || listPlay.get(1).getMessage().equals("none")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "win");
                                object2 = new sendObject(listPlay.get(1).getUser(), "lose");
                            } else if (listPlay.get(1).getMessage().equals("bua")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "draw");
                                object2 = new sendObject(listPlay.get(1).getUser(), "draw");
                            } else {
                                object1 = new sendObject(listPlay.get(0).getUser(), "lose");
                                object2 = new sendObject(listPlay.get(1).getUser(), "win");
                            }
                        } else if (listPlay.get(0).getMessage().equals("keo")) {
                            if (listPlay.get(1).getMessage().equals("bao") || listPlay.get(1).getMessage().equals("none")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "win");
                                object2 = new sendObject(listPlay.get(1).getUser(), "lose");
                            } else if (listPlay.get(1).getMessage().equals("bua")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "lose");
                                object2 = new sendObject(listPlay.get(1).getUser(), "win");
                            } else {
                                object1 = new sendObject(listPlay.get(0).getUser(), "draw");
                                object2 = new sendObject(listPlay.get(1).getUser(), "draw");
                            }
                        } else {
                            if (listPlay.get(1).getMessage().equals("bua") || listPlay.get(1).getMessage().equals("none")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "win");
                                object2 = new sendObject(listPlay.get(1).getUser(), "lose");
                            } else if (listPlay.get(1).getMessage().equals("keo")) {
                                object1 = new sendObject(listPlay.get(0).getUser(), "lose");
                                object2 = new sendObject(listPlay.get(1).getUser(), "win");
                            } else {
                                object1 = new sendObject(listPlay.get(0).getUser(), "draw");
                                object2 = new sendObject(listPlay.get(1).getUser(), "draw");
                            }
                        }
                        listResult.add(object1);
                        listResult.add(object2);
                        for (ClientHandler aClient : ServerSerialization.clients) {
                            aClient.oos.reset();
                            aClient.oos.writeObject(listResult);
                        }
                        listPlay.clear();
                        listResult.clear();
                    }
                } else if (object.getMessage().equals("exit")) {
                    System.out.println("Receive exit:" + object.getUser().getUserName() + object.getMessage());
                    listExit.add(object);
                    System.out.println("List exit size:" + listExit.size());
                    if (listExit.size() >= 1) {
                        for (ClientHandler aClient : ServerSerialization.clients) {
                            aClient.oos.reset();
                            aClient.oos.writeObject(listExit);
                        }
                        listExit.clear();
                    }
                } else if (object.getMessage().equals("bxh")) {
                    ArrayList<User> listUser = dao.selectAllUser();
                    oos.reset();
                    oos.writeObject(listUser);
                } else if (object.getMessage().equals("point")) {
                    ArrayList<User> listUser = dao.selectAllUserPointDesc();
                    oos.reset();
                    oos.writeObject(listUser);
                } else if (object.getMessage().equals("win")) {
                    ArrayList<User> listUser = dao.selectAllUserWinDesc();
                    oos.reset();
                    oos.writeObject(listUser);
                } else if (object.getMessage().equals("update")) {
                    dao.updateScore(object.getUser());
                }
            } catch (IOException | ClassNotFoundException ex) {
                break;
            } catch (SQLException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
