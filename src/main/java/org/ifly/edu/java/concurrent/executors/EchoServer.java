package org.ifly.edu.java.concurrent.executors;

public class EchoServer implements Runnable {  
    @Override  
    public void run() {  
        try {  
            Thread.sleep(50);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("This is a echo server. The current time is " +  
                System.currentTimeMillis() + ".");  
    }  
}  
