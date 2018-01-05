package cn.taskSys.utils;

public class CommonUtil {
	public static final String tokEncryptKey = "rBISLeFLqxei6EUEkB2brA==";	
	
	/**
	 * 解密
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String decryptSSOToken(String value)throws Exception {
		return new String(EncryptUtil.decryptAES(
				EncryptUtil.base64Decode(value),EncryptUtil.base64Decode(tokEncryptKey)));
	}

}
