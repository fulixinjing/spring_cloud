package cn.taskSys.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class EncryptUtil {
	public static byte[] decryptAES(byte[] src, byte[] key)throws Exception{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(src);
	}
    /**
	 * base64解码
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] base64Decode(String str) throws Exception{
		BASE64Decoder dec = new BASE64Decoder();
		return (dec.decodeBuffer(str));
	}
}
