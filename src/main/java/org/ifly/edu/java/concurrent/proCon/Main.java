package org.ifly.edu.java.concurrent.proCon;

import java.nio.Buffer;

/**
 * 
 * @author pangzhiwang
 * @date 2016-7-24
 */
public class Main {
public static void main(String[] args) {
	Buffer buffer = null;
	Producer producer = new Producer(buffer);
	Consumer consumer = new Consumer(buffer);
	producer.start();
	consumer.start();
}
}
