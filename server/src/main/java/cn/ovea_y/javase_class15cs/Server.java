package cn.ovea_y.javase_class15cs;


import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server  {
    boolean started = false;
    ServerSocket ss = null;

    List<Client> clients = new ArrayList<Client>();

    public void start() {
        try {
            ss = new ServerSocket(18097);
            started = true;
        } catch (BindException e) {
            System.err.println("服务器建立失败! —— 端口被占用");
            System.exit(10);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(904);
        }
        try {
            started = true;
            while (started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("| a client connected! |");
                new Thread(c).start();
                clients.add(c);
                //dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataOutputStream dos;
        private DataInputStream dis;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str){
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for(int i = 0; i < clients.size(); i++){
                        Client c = clients.get(i);
                        if (c != this)
                            c.send(str);
                    }
                }
            } catch (EOFException e) {
                System.err.println("Client closed");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clients.remove(this);
                    if (dos != null) {
                        dos.close();
                        dos = null;
                    }
                    if (dis != null) {
                        dis.close();
                        dis = null;
                    }
                    if (s != null) {
                        s.close();
                        s = null;
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
