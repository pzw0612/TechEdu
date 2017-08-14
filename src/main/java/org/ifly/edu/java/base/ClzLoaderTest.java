package org.ifly.edu.java.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ClzLoaderTest {

	public static void main(String[] args) throws IOException {
		
		String fileName="abc.txt";
		
		InputStream input = null;
		ClassLoader clzLoader = ClzLoaderTest.class.getClassLoader();
		URL url = clzLoader.getSystemResource(fileName);
		if (url != null) {
			input = url.openStream();
		} else {
			input = clzLoader.getSystemResourceAsStream("/"+fileName);
		}
		
		InputStreamReader isr = new InputStreamReader(input);
		
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while((str=br.readLine())!=null){
			System.out.println(str);
		}
		
		br.close();
		isr.close();
		input.close();
	}
}
