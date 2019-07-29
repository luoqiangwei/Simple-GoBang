package cn.ovea_y.javase_class15c.window.scene;

import cn.ovea_y.javase_class15c.controller.Main;
import cn.ovea_y.javase_class15c.data.ChessData;
import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.window.MainStage;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class MainWindow extends Scene {
    public Polygon ai1Flag = new Polygon();
    public Polygon ai2Flag = new Polygon();
    public Label ai1;
    public Label ai2;
    public Pane pane;

    public void disableStart(){
        Button button = (Button) lookup("#start");
        button.setDisable(true);
    }

    public void enableStart(){
        Button button = (Button) lookup("#start");
        button.setDisable(false);
    }

    public MainWindow() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        super((Parent) Main.classLoader.findClass("javafx.fxml.FXMLLoader").getMethod("load", URL.class).invoke(Main.classLoader.findClass("javafx.fxml.FXMLLoader"), Main.class.getResource("/window/main.fxml")));

        ai1Flag.getPoints().addAll(new Double[]{
                900.0, 210.0,
                880.0, 240.0,
                920.0, 240.0 });
        ai2Flag.setFill(Color.SKYBLUE);
        ai2Flag.getPoints().addAll(new Double[]{
                900.0, 720.0,
                880.0, 750.0,
                920.0, 750.0 });

        pane = (Pane) lookup("#pane");
        ai1 = (Label) lookup("#ai1");
        ai2 = (Label) lookup("#ai2");
        // 70    R : 15
        for(int i = 0; i <= 20; i++){
            Line lineH = new Line();
            lineH.setStartX(25);
            lineH.setStartY(i * 35 + 25);
            lineH.setEndX(750 - 25);
            lineH.setEndY(i * 35 + 25);
            lineH.setStroke(Color.GRAY);
            lineH.setStrokeWidth(3);
            Line lineV = new Line();
            lineV.setStartX(i * 35 + 25);
            lineV.setStartY(25);
            lineV.setEndX(i * 35 + 25);
            lineV.setEndY(750 - 25);
            lineV.setStroke(Color.GRAY);
            lineV.setStrokeWidth(3);
            pane.getChildren().add(lineH);
            pane.getChildren().add(lineV);
        }
        for(int i = 1; i <= 19; i++){
            for(int j = 1; j <= 19; j++){
                ChessData.map[i-1][j-1] = new Circle();
                ChessData.map[i-1][j-1].setFill(new Color(1, 1, 1, 0));
                ChessData.map[i-1][j-1].setRadius(17.5);
                ChessData.map[i-1][j-1].setCenterX(25 + 35 * i);
                ChessData.map[i-1][j-1].setCenterY(25 + 35 * j);
                ChessData.map[i-1][j-1].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (ChessData.isStart && ChessData.outerIsStart) {
                            Circle circle = (Circle) event.getSource();
                            if (circle.getFill().equals(Color.BLACK) || circle.getFill().equals(Color.SKYBLUE)) return;
                            int y = (int) ((circle.getCenterX() - 25) / 35) - 1;
                            int x = (int) ((circle.getCenterY() - 25) / 35) - 1;
//                        System.out.println(x + "  " + y);
                            if (ChessData.isAI1) {
                                if(!ChessData.ai2FlagUsed){
                                    ChessData.ai2FlagUsed = true;
                                    MainStage.mainWindow.pane.getChildren().add(MainStage.mainWindow.ai2Flag);
                                }
                                if(ChessData.ai1FlagUsed){
                                    ChessData.ai1FlagUsed = false;
                                    MainStage.mainWindow.pane.getChildren().remove(MainStage.mainWindow.ai1Flag);
                                }
                                ChessData.userFill[x][y] = 1;
                                circle.setFill(Color.BLACK);
                                circle.setRadius(15);
                                try {
                                    ChessData.outerIsStart = false;
                                    NetConn.client.getDos().writeUTF("AI1Select");
                                    NetConn.client.getDos().flush();
                                    NetConn.client.getDos().writeUTF(x + " " + y + " " + "AI1");
                                    NetConn.client.getDos().flush();
                                    if(checkWin(x, y)){
                                        new Thread(new Task<Void>() {
                                            @Override
                                            protected Void call() throws Exception {
                                                NetConn.client.getDos().writeUTF("AI1WIN");
                                                NetConn.client.getDos().flush();

                                                return null;
                                            }
                                        }).start();
                                        printWinResult();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (ChessData.isAI2) {
                                if(!ChessData.ai1FlagUsed){
                                    ChessData.ai1FlagUsed = true;
                                    MainStage.mainWindow.pane.getChildren().add(ai1Flag);
                                }
                                if(ChessData.ai2FlagUsed){
                                    ChessData.ai2FlagUsed = false;
                                    MainStage.mainWindow.pane.getChildren().remove(ai2Flag);
                                }
                                ChessData.userFill[x][y] = -1;
                                circle.setFill(Color.SKYBLUE);
                                circle.setRadius(15);
                                try {
                                    ChessData.outerIsStart = false;
                                    NetConn.client.getDos().writeUTF("AI2Select");
                                    NetConn.client.getDos().flush();
                                    NetConn.client.getDos().writeUTF(x + " " + y + " " + "AI2");
                                    NetConn.client.getDos().flush();
                                    if(checkWin(x, y)){
                                        new Thread(new Task<Void>() {
                                            @Override
                                            protected Void call() throws Exception {
                                                NetConn.client.getDos().writeUTF("AI2WIN");
                                                NetConn.client.getDos().flush();

                                                return null;
                                            }
                                        }).start();
                                        printWinResult();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

//                        System.out.println(circle.getFill().equals(Color.BLACK));
                        }
                    }
                });
                pane.getChildren().add(ChessData.map[i-1][j-1]);

            }
        }
    }

    public void printWinResult(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("比赛结果");
        alert.setHeaderText("胜利");
        alert.setContentText("您获胜了！");
        alert.showAndWait();
        NetConn.disConnect();
        System.exit(0);
    }

    public void printLoseResult(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("比赛结果");
        alert.setHeaderText("败北");
        alert.setContentText("您被打败了");
        alert.showAndWait();
        NetConn.disConnect();
        System.exit(0);
    }

    private boolean checkWin(int x, int y){
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for(int i = 1; i < 5; i++){
            if(x + i >= 0 && y + i >= 0 && x + i < 19 && y + i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x+i][y+i]){
                count0++;
            }else{
                break;
            }
        }
        for(int i = -1; i > -5; i--){
            if(x + i >= 0 && y + i >= 0 && x + i < 19 && y + i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x+i][y+i]){
                count0++;
            }else{
                break;
            }
        }
        if(count0 >= 4) return true;

        for(int i = 1; i < 5; i++){
            if(x + i >= 0 && y - i >= 0 && x + i < 19 && y - i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x+i][y-i]){
                count1++;
            }else{
                break;
            }
        }
        for(int i = -1; i > -5; i--){
//            System.out.println(x+i + "  " + (y-i) + "   " + (ChessData.userFill[x][y] == ChessData.userFill[x+i][y-i]) + "  " + (x + i >= 0 && y - i >= 0 && x + i < 19 && y - i < 19) + "  " + (x + i >= 0 && y - i >= 0 && x + i < 19 && y - i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x][y-i]));
            if(x + i >= 0 && y - i >= 0 && x + i < 19 && y - i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x+i][y-i]){
                count1++;
            }else{
                break;
            }
        }
        if(count1 >= 4) return true;

        for(int i = 1; i < 5; i++){
            if(y + i >= 0 && y + i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x][y+i]){
                count2++;
            }else{
                break;
            }
        }
        for(int i = -1; i > -5; i--){
            if(y + i >= 0 && y + i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x][y+i]){
                count2++;
            }else{
                break;
            }
        }
        if(count2 >= 4) return true;

        for(int i = 1; i < 5; i++){
            if(x + i >= 0 && x + i < 19&& ChessData.userFill[x][y] == ChessData.userFill[x+i][y]){
                count3++;
            }else{
                break;
            }
        }
        for(int i = -1; i > -5; i--){
            if(x + i >= 0 && x + i < 19 && ChessData.userFill[x][y] == ChessData.userFill[x+i][y]){
                count3++;
            }else{
                break;
            }
        }
        if(count3 >= 4) return true;
//        System.out.println(x + "   " + y + "   " + count0 + "   " + count1 + "  " + count2 + "  " + count3);
        return false;
    }
}
