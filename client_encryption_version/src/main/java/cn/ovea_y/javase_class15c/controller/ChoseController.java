package cn.ovea_y.javase_class15c.controller;

import cn.ovea_y.javase_class15c.data.NetConn;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ChoseController {
    Class ChessData = Main.classLoader.findClass("cn.ovea_y.javase_class15c.data.ChessData");
    Class MainStage = Main.classLoader.findClass("cn.ovea_y.javase_class15c.window.MainStage");
    Class MainWindow = Main.classLoader.findClass("cn.ovea_y.javase_class15c.window.scene.MainWindow");
    Class NetConn = Main.classLoader.findClass("cn.ovea_y.javase_class15c.data.NetConn");
    Object client = NetConn.getField("client").get(NetConn);
    DataOutputStream dos = (DataOutputStream) client.getClass().getMethod("getDos").invoke(client);

    public ChoseController() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    }

    public void onAI1Clicked(MouseEvent mouseEvent) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ChessData.getField("isFirst").set(ChessData, false);
        ChessData.getField("isAI2").set(ChessData, false);
        ChessData.getField("isAI1").set(ChessData, true);
        ChessData.getField("isStart").set(ChessData, true);
        Object mainWindow = MainStage.getField("mainWindow").get(MainWindow);
        mainWindow.getClass().getMethod("disableStart").invoke(mainWindow);
        try {
            Label label = (Label) mainWindow.getClass().getField("ai1").get(mainWindow);
            label.setText("我");
            label = (Label) mainWindow.getClass().getField("ai2").get(mainWindow);
            label.setText("对手");
            dos.writeUTF("AI1Select");
            dos.flush();
            dos.writeUTF("AI1");
            dos.flush();
//            MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController.stage.close();
    }

    public void onAI2Clicked(MouseEvent mouseEvent) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ChessData.getField("isFirst").set(ChessData, true);
        ChessData.getField("isAI2").set(ChessData, true);
        ChessData.getField("isAI1").set(ChessData, false);
        ChessData.getField("isStart").set(ChessData, true);
        Object mainWindow = MainStage.getField("mainWindow").get(MainWindow);
        Label label = (Label) mainWindow.getClass().getField("ai2").get(mainWindow);
        label.setText("我");
        label = (Label) mainWindow.getClass().getField("ai1").get(mainWindow);
        label.setText("对手");
        try {
            dos.writeUTF("AI2");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
//        try {
//            NetConn.client.getDos().writeUTF("AI2Select");
//            NetConn.client.getDos().flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mainWindow.getClass().getMethod("disableStart").invoke(mainWindow);
        MainController.stage.close();
    }
}
