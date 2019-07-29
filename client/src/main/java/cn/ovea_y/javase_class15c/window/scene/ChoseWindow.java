package cn.ovea_y.javase_class15c.window.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ChoseWindow extends Scene {
    public ChoseWindow() throws IOException {
        super(FXMLLoader.load(MainWindow.class.getResource("/window/chose.fxml")));
    }
}
