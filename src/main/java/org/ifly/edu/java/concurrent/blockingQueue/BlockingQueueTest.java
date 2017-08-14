package org.ifly.edu.java.concurrent.blockingQueue;

import java.util.concurrent.*;


public class BlockingQueueTest
{
    public static void main(String[] args)
    {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
        java.lang.Runnable r = new MyRunnable(queue);
        Thread t = new Thread(r);
        t.start();
         
        while(true)
        {
            try
            {
                while(true)
                {
                    for(int i =0;i < 100000;i++)
                    {
                        queue.offer(i);
                    }
                }
            }
            catch ( Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
