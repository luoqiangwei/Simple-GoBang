package cn.ovea_y.javase_class15c.controller;

import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.window.MainStage;
import javafx.application.Application;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NetConn.connect();
        Application.launch(MainStage.class);
        // 122.152.216.29
    }

}
