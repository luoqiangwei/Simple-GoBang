package cn.ovea_y.javase_class15c;

import cn.ovea_y.javase_class15c.controller.Main;
import cn.ovea_y.javase_class15c.data.NetConn;
import cn.ovea_y.javase_class15c.util.file.DecodeingClassLoader;
import cn.ovea_y.javase_class15c.util.security.AES;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class OutPutEncodedClass {
    private static HashSet<String> classesName = new HashSet<>();
    public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        findAllClass();
        for(String className : classesName){
            if (className.lastIndexOf("Main.class") > 0 || className.lastIndexOf("AES.class" ) > 0 || className.lastIndexOf("FindAllClass.class") > 0 || className.lastIndexOf("DecodeingClassLoader.class") > 0 || className.lastIndexOf("OutPutEncodedClass.class") > 0 || className.lastIndexOf("Controller.class") > 0) continue;
            String writeClass;
            try (FileInputStream fileInputStream = new FileInputStream(className)){
                writeClass = AES.ASEEncoding("@jf3(jf2Ad2x(idw".getBytes(), "asqf).dwn};dwQif".getBytes(), fileInputStream.readAllBytes());
            }
            try (FileWriter fileWriter = new FileWriter(className)){
                fileWriter.write(writeClass);
            }
        }
//        DecodeingClassLoader classLoader = new DecodeingClassLoader("@jf3(jf2Ad2x(idw", "asqf).dwn};dwQif");
        Main.main(new String[]{"@jf3(jf2Ad2x(idw", "asqf).dwn};dwQif"});

    }

    public static void findAllClass(){
        String path = OutPutEncodedClass.class.getResource("/").getPath();
        File file = new File(path);
        if(file.isDirectory()){
            String[] store = file.list();
            for(String item : store){
                if(new File(path + item).isDirectory()){
                    findAllClassDepth(path + item, "");
                }else if (item.lastIndexOf(".class") >= 0){
                    String className = item.split(".class")[0];
                    System.out.println("Find Class : " + className);
                    classesName.add(className);
                }
            }
        }

    }

    private static void findAllClassDepth(String path, String packagePath){
        File file = new File(path);
        if(file.isDirectory()){
            String[] store = file.list();
            for(String item : store){
                if(new File(path + "/" + item).isDirectory()){
                    findAllClassDepth(path + "/" + item, packagePath + "." + file.getName());
                }else if (item.lastIndexOf(".class") >= 0){
                    String className = path + "/" + item;
                    System.out.println("Find Class : " + className);
                    classesName.add(className);
                }
            }
        }
    }
}
