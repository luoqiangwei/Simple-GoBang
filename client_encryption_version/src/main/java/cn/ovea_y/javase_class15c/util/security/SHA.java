package cn.ovea_y.javase_class15c.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author QiangweiLuo
 */
public class SHA{
    private static String firstStr = "Q　 +8/℉㎡③㈣Ⅻ〖";
    private static String lastStr = "↔◕я▩⏱✈╊◙∰Жぎ";
        /**
         * 传入文本内容，返回 SHA-256 串
         *
         * @param strText
         * @return
         */
        public static String SHA256Encoding(final String strText)
        {
            return SHAEncoding(firstStr + strText + lastStr, "SHA-256");
        }

        /**
         * 传入文本内容，返回 SHA-512 串
         *
         * @param strText
         * @return
         */
        public static String SHA512Encoding(final String strText)
        {
            return SHAEncoding(firstStr + strText + lastStr, "SHA-512");
        }

        /**
         * 字符串 SHA 加密
         *
         * @param strText
         * @param strType
         * @return
         */
        private static String SHAEncoding(final String strText, final String strType)
        {
            // 返回值
            String strResult = null;

            // 判断是否是有效字符串
            if (strText != null && strText.length() > 0)
            {
                try
                {
                    // SHA 加密开始
                    // 创建加密对象 并傳入加密类型
                    MessageDigest messageDigest = MessageDigest.getInstance(strType);
                    // 传入要加密的字符串
                    messageDigest.update(strText.getBytes());
                    // 得到 byte 类型结果
                    byte byteBuffer[] = messageDigest.digest();

                    // 将 byte 转化为 string
                    StringBuffer strHexString = new StringBuffer();
                    // 遍历 byte buffer
                    for (int i = 0; i < byteBuffer.length; i++)
                    {
                        String hex = Integer.toHexString(0xff & byteBuffer[i]);
                        if (hex.length() == 1)
                            strHexString.append('0');
                        strHexString.append(hex);
                    }
                    // 得到返回结果
                    strResult = strHexString.toString();
                }
                catch (NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }
            }

            return strResult;
        }

}
