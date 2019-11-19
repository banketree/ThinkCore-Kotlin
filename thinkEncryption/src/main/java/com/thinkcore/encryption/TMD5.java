package com.thinkcore.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对外提供getMD5(String)方法
 * 
 */
public class TMD5 {
	/** * 16进制字符集 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5(String val) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();// 加密
		return getString(m);
	}

	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToHex(b[i]));
		}
		return sb.toString();
	}

	/** * 将单个字节码转换成16进制字符串 * @param bt 目标字节 * @return 转换结果 */
	public static String byteToHex(byte bt) {
		return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
	}

	// 生成A1 的 md5
	public static String getA1Md5(String userName, String token, String realm)
			throws NoSuchAlgorithmException {
		return TMD5.getMD5(userName + ":" + realm + ":" + token);

	}

	// 生成A1 的 md5
	private static String getA2Md5(String method, String url)
			throws NoSuchAlgorithmException {
		return TMD5.getMD5(method + ":" + url);
	}

	// 生成response的Md5
	// other = ":" + nonce + ":" + nc+ ":" + cnonce + ":" + qop + ":"
	public static String getResponseMd5(String userName, String token,
			String method, String url, String realm, String other) {
		try {
			String A1 = getA1Md5(userName, token, realm);
			String A2 = getA2Md5(method, url);

			String string = A1 + other + A2;
			// String string = A1 + ":" + nonce + ":" + nc+ ":" + cnonce + ":" +
			// qop + ":" + A2;

			String response = TMD5.getMD5(string);

			return response;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return "";
		}
	}
}