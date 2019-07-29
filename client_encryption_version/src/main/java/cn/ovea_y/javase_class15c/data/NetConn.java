package cn.ovea_y.javase_class15c.data;

import cn.ovea_y.javase_class15c.network.Client;

public class NetConn {
    public static Client client = new Client();
    public static void connect(){
        client.start();
    }

    public static void disConnect(){
        client.disconnect();
    }

}
