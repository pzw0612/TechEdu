package com.yhd.configcenter.password;

import com.yihaodian.configcentre.utils.Encrypter;



/**
 * 
 * @author pangzhiwang
 * @date 2016-7-29
 */
public class PasswordGen {

	public static void main(String[] args){
		
		System.out.println(Encrypter.encrypt("pic_user"));
	}
}
