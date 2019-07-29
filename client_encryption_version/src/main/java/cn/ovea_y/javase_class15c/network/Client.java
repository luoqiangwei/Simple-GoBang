package cn.ovea_y.javase_class15c.network;

import cn.ovea_y.javase_class15c.controller.MainController;
import cn.ovea_y.javase_class15c.data.ChessData;
import cn.ovea_y.javase_class15c.window.MainStage;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public DataOutputStream getDos() {
        return dos;
    }

    private Socket s = null;
    private DataOutputStream dos = null;
    private DataInputStream dis = null;
    private boolean bConnected = false;

    public void connect(){
        try {
            s = new Socket("122.152.216.29", 18097);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            System.out.println("connected!");
            bConnected = true;
//            dos.writeUTF("1 2 OVEA");
//            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            if(dis != null) {
                dis.close();
                dis = null;
            }
            if(dos != null) {
                dos.close();
                dos = null;
            }
            if(s != null) {
                s.close();
                s = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        connect();
        new Thread(new RecvThread()).start();
    }

    private class RecvThread implements Runnable{

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
//                    System.out.println(str);
                    if(str.equals("AI1Select") && ChessData.isAI2){
                        ChessData.outerIsStart = true;
//                        System.out.println("AI2" + ChessData.isAI2);
//                        System.out.println("AI1 " + ChessData.ai2FlagUsed + " " + ChessData.ai1FlagUsed);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(!ChessData.ai2FlagUsed){
                                    ChessData.ai2FlagUsed = true;
                                    MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
                                }
                                if(ChessData.ai1FlagUsed){
                                    ChessData.ai1FlagUsed = false;
                                    MainStage.mainWindow.pane.getChildren().remove(MainStage.mainWindow.ai1Flag);
                                }
                            }
                        });
                    }else if(str.equals("AI2Select") && ChessData.isAI1){
                        ChessData.outerIsStart = true;
//                        System.out.println("AI1" + ChessData.isAI1);
//                        System.out.println("AI2 " + ChessData.ai2FlagUsed + " " + ChessData.ai1FlagUsed);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(!ChessData.ai1FlagUsed){
                                    ChessData.ai1FlagUsed = true;
                                    MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai1Flag);
                                }
                                if(ChessData.ai2FlagUsed){
                                    ChessData.ai2FlagUsed = false;
                                    MainStage.mainWindow.pane.getChildren().remove(MainStage.mainWindow.ai2Flag);
                                }
                            }
                        });
                    }else if(str.equals("AI1WIN") || str.equals("AI2WIN")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainStage.mainWindow.printLoseResult();
                            }
                        });
                    }else if(str.equals("GIVEUP")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainStage.mainWindow.printWinResult();
                            }
                        });
                    }else if(str.equals("AI1")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainController.ai1.setDisable(true);
                            }
                        });
                    }else if(str.equals("AI2")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainController.ai2.setDisable(true);
                            }
                        });
                    }else  {
                        String[] strings = str.split(" ");
                        int y = Integer.parseInt(strings[0]);
                        int x = Integer.parseInt(strings[1]);
                        if(ChessData.isAI1){
                            ChessData.map[x][y].setFill(Color.SKYBLUE);
                            ChessData.map[x][y].setRadius(15);
                        }else {
                            ChessData.map[x][y].setFill(Color.BLACK);
                            ChessData.map[x][y].setRadius(15);
                        }
                    }

                }
            }catch (IOException e){
                disconnect();
//                System.err.println("| Client Closed! |");
            }
        }
    }
}
