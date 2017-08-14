package org.ifly.edu.java.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author pangzhiwang
 * @date 2017-7-13
 */
public class MessageDigestTest {
	
	private static ThreadLocal<MessageDigest> MD5 = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance( "MD5" );
			}
			catch ( NoSuchAlgorithmException e ) {
			
				throw new IllegalStateException( "++++ no md5 algorythm found");			
			}
		}
	};
	 
	public static void main(String[] args) {
		String aa="123.123.123.123:7001,123.123.123.123:7001,123.123.123.123:7001,123.123.123.123:7001";
		
		String d[]=aa.split(",");
//		System.out.println(md5("1"));
		for(int i=0; i<d.length; i++){
			test(md5(d[i]));
		}
	
	}

	
	public static void test(byte[] d ){
		for ( int h = 0 ; h < 4; h++ ) {
			Long k = 
				  ((long)(d[3+h*4]&0xFF) << 24)
				| ((long)(d[2+h*4]&0xFF) << 16)
				| ((long)(d[1+h*4]&0xFF) << 8)
				| ((long)(d[0+h*4]&0xFF));
			System.out.print(" " +k);
		}	
		System.out.println("");
	}
	public static byte[] md5(String msg) {
		try {
			MessageDigest md= MD5.get();
			byte md5[] = md.digest(msg.getBytes());
			
			return md5;// 解释md5码成明文字符串
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
