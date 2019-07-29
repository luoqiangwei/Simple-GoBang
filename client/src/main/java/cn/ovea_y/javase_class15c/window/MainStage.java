package cn.ovea_y.javase_class15c.window;

import cn.ovea_y.javase_class15c.data.ChessData;
import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.network.Client;
import cn.ovea_y.javase_class15c.window.scene.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainStage extends Application {
    private static Stage stage = null;
    public static MainWindow mainWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 设置图标
        primaryStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("/pic/icon.jpg")));
        //不用强制设置，让它自动设置大小，因为这个大小还包括了标题栏
//        primaryStage.setWidth(1280);
//        primaryStage.setHeight(850);
        // 设置标题
        mainWindow = new MainWindow();
        primaryStage.setTitle("五子棋");
        primaryStage.setScene(mainWindow);
        // 设置窗口大小禁止改变
        primaryStage.setResizable(false);
        stage = primaryStage;
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                NetConn.disConnect();
            }
        });
        MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
        ChessData.ai2FlagUsed = true;
    }

    public static Stage getStage() {
        return stage;
    }
}
