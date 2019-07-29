package cn.ovea_y.javase_class15c.data;

import javafx.scene.shape.Circle;

public class ChessData {
    public static boolean isAI1 = false;
    public static boolean isAI2 = true;
    public static boolean isFirst = true;
    public static Circle[][] map = new Circle[19][19];
    public static byte[][] userFill = new byte[19][19];
    public static boolean isStart = false;
    public static boolean outerIsStart = false;
    public static boolean ai1FlagUsed = false;
    public static boolean ai2FlagUsed = false;
}
