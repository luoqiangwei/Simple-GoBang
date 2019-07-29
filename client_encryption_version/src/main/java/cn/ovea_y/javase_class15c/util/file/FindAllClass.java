package cn.ovea_y.javase_class15c.util.file;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class FindAllClass {
    private static HashMap<String, String> classesName;
    public static HashMap findAllClass(){
        classesName = new HashMap<>();
        String path = FindAllClass.class.getResource("/").getPath();
        File file = new File(path);
        if(file.isDirectory()){
            String[] store = file.list();
            for(String item : store){
                if(new File(path + item).isDirectory()){
                    findAllClassDepth(path + item, "");
                }else if (item.lastIndexOf(".class") >= 0){
                    String className = item.split(".class")[0];
                    String realPath = path + item;
                    System.out.println("Find Class : " + className);
                    classesName.put(className, realPath);
                }
            }
        }
        return classesName;
    }

    private static void findAllClassDepth(String path, String packagePath){
        File file = new File(path);
        if(file.isDirectory()){
            String[] store = file.list();
            for(String item : store){
                if(new File(path + "/" + item).isDirectory()){
                    findAllClassDepth(path + "/" + item, packagePath + "." + file.getName());
                }else if (item.lastIndexOf(".class") >= 0){
                    String realPath = path + "/" + item;
                    String className = packagePath + "." + file.getName() + "." + item.split(".class")[0];
                    className = className.substring(1);
                    System.out.println("Find Class : " + className);
                    classesName.put(className, realPath);
                }
            }
        }
    }
}
