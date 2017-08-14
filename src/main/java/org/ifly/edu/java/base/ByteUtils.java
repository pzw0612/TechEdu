package org.ifly.edu.java.base;

/**
 * 
 * @author pangzhiwang
 * @date 2017-7-19
 */
public class ByteUtils {

	public static void main(String[] args) {
		
		byte[] b= long2buff(100000000);
		for( byte a : b){
			System.out.print(a+" ");
			
		}
	}
	public static byte[] long2buff(long n)
	{
		byte[] bs;
		
		bs = new byte[8];
		bs[0] = (byte)((n >> 56) & 0xFF);
		bs[1] = (byte)((n >> 48) & 0xFF);
		bs[2] = (byte)((n >> 40) & 0xFF);
		bs[3] = (byte)((n >> 32) & 0xFF);
		bs[4] = (byte)((n >> 24) & 0xFF);
		bs[5] = (byte)((n >> 16) & 0xFF);
		bs[6] = (byte)((n >> 8) & 0xFF);
		bs[7] = (byte)(n & 0xFF);
		
		return bs;
	}
}
