package cn.ovea_y.javase_class15c.controller;

import cn.ovea_y.javase_class15c.util.file.DecodeingClassLoader;
import javafx.application.Application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class Main {
    private static HashSet<String> classesName = new HashSet<>();
    public static DecodeingClassLoader classLoader;
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        classLoader = new DecodeingClassLoader(args[0], args[1]);
        Class NetConn = classLoader.findClass("cn.ovea_y.javase_class15c.data.NetConn");
        NetConn.getMethod("connect").invoke(NetConn);
        Class Application = classLoader.findClass("javafx.application.Application");
        Application.getMethod("launch", Class.class, String[].class).invoke(Application, classLoader.findClass("cn.ovea_y.javase_class15c.window.MainStage"), null);
    }
}
