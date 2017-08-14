package org.ifly.edu.java.concurrent.proCon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * 
 * @author pangzhiwang
 * @date 2016-7-24
 */
public class Producer extends Thread {
	private BufferedReader reader;
	Buffer buffer;

	public Producer(Buffer buffer) {
		reader = new BufferedReader(new InputStreamReader(System.in));
		this.buffer = buffer;
	}

	void send(String s) {
		//buffer.send(s);
		
	}

	public void run() {
		try {
			while (true) {
				String line = reader.readLine();
				send(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
