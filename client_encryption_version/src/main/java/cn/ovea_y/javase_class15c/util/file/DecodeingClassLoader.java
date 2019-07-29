package cn.ovea_y.javase_class15c.util.file;

import cn.ovea_y.javase_class15c.util.security.AES;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class DecodeingClassLoader extends ClassLoader {
    HashMap<String, String> allClass = FindAllClass.findAllClass();
    private String key;
    private String iv;

    public DecodeingClassLoader(String key, String iv) {
        this.key = key;
        this.iv = iv;
        allClass.remove("cn.ovea_y.javase_class15c.controller.Main"); //被排除的不被加密的必要类
        allClass.remove("cn.ovea_y.javase_class15c.util.security.AES");
        allClass.remove("cn.ovea_y.javase_class15c.util.file.DecodeingClassLoader");
        allClass.remove("cn.ovea_y.javase_class15c.OutPutEncodedClass");
        allClass.remove("cn.ovea_y.javase_class15c.util.file.FindAllClass");
        allClass.remove("cn.ovea_y.javase_class15c.util.file.FindAllClass");
        allClass.remove("cn.ovea_y.javase_class15c.controller.ChoseController"); // JavaFX图像库控制类强制使用App类加载器…… 因此也只能将其排除在加密范围之外了
        allClass.remove("cn.ovea_y.javase_class15c.controller.MainController");
    }

    public void loadAllClass(){
        // 尝试一次性解密并加载玩所有类之后，jvm会不会自动加载
        for(String key : allClass.keySet()){
            System.out.println(key + "  " + allClass.get(key));
            try (FileInputStream fileInputStream = new FileInputStream(allClass.get(key))){
                byte[] classData = AES.ASEDecoding(this.key.getBytes(), iv.getBytes(), fileInputStream.readAllBytes());
                Class clazz = defineClass(key, classData, 0, classData.length);
                loadClass(clazz.getName());
                System.out.println(clazz.getName() + "已经加载");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        return loadClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class clzzz = findLoadedClass(name);
        if(clzzz == null) {
            if (allClass.containsKey(name)) {
                try (FileInputStream fileInputStream = new FileInputStream(allClass.get(name))) {
                    byte[] classData = AES.ASEDecoding(key.getBytes(), iv.getBytes(), fileInputStream.readAllBytes());
                    System.out.println("解密加载器正在加载： " + name);
                    return super.defineClass(name, classData, 0, classData.length);
                } catch (IOException e) {
                    throw new RuntimeException("此类不存在");
                }
            } else {
                System.out.println("系统正在加载： " + name);
                return super.loadClass(name);
            }
        }
        return clzzz;
    }
}
