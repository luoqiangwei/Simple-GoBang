package cn.ovea_y.javase_class15c.controller;

import cn.ovea_y.javase_class15c.data.ChessData;
import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.window.scene.ChoseWindow;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public static Stage stage;
    public static Button ai1;
    public static Button ai2;

    static {
        stage = new Stage();
        Scene scene = null;
        try {
            scene = new ChoseWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setAlwaysOnTop(true);
        stage.setTitle("选择角色");
        ai1 = (Button) scene.lookup("#ai1");
        ai2 = (Button) scene.lookup("#ai2");
    }
    public void onStartClick(ActionEvent actionEvent) throws IOException {

        stage.show();
    }

    public void onQuitClicked(ActionEvent actionEvent) {
        try {
            ChessData.outerIsStart = false;
            NetConn.client.getDos().writeUTF("GIVEUP");
            NetConn.client.getDos().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NetConn.disConnect();
        System.exit(0);
    }
}
