package cn.ovea_y.javase_class15c.util.security;

import java.security.MessageDigest;

public class MD5 {
	// 传入一个明文字符串，返回加密后的md5字符串
	public static String stringToMd5(String info) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			info = "Q　 +㎡③㈣Ⅻ8/℉〖" + info + "⏱я╊▩∰Ж✈◙↔◕ぎ";
			byte[] strTemp = info.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
