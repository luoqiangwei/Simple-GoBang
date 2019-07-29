package cn.ovea_y.javase_class15c.window.scene;

import cn.ovea_y.javase_class15c.controller.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class ChoseWindow extends Scene {
    public ChoseWindow() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        super((Parent) Main.classLoader.findClass("javafx.fxml.FXMLLoader").getMethod("load", URL.class).invoke(Main.classLoader.findClass("javafx.fxml.FXMLLoader"), Main.class.getResource("/window/chose.fxml")));
    }
}
