package cn.ovea_y.javase_class15c.controller;

import cn.ovea_y.javase_class15c.data.ChessData;
import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.window.MainStage;
import cn.ovea_y.javase_class15c.window.scene.MainWindow;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChoseController {
    public void onAI1Clicked(MouseEvent mouseEvent) {
        ChessData.isFirst = false;
        ChessData.isAI2 = false;
        ChessData.isAI1 = true;
        ChessData.isStart = true;
        MainStage.mainWindow.disableStart();
        try {
            MainStage.mainWindow.ai1.setText("我");
            MainStage.mainWindow.ai2.setText("对手");
            NetConn.client.getDos().writeUTF("AI1Select");
            NetConn.client.getDos().flush();
            NetConn.client.getDos().writeUTF("AI1");
            NetConn.client.getDos().flush();
//            MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController.stage.close();
    }

    public void onAI2Clicked(MouseEvent mouseEvent) {
        ChessData.isFirst = true;
        ChessData.isAI2 = true;
        ChessData.isAI1 = false;
        ChessData.isStart = true;
        MainStage.mainWindow.ai2.setText("我");
        MainStage.mainWindow.ai1.setText("对手");
        try {
            NetConn.client.getDos().writeUTF("AI2");
            NetConn.client.getDos().flush();
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
        MainStage.mainWindow.disableStart();
        MainController.stage.close();
    }
}
