package org.ifly.edu.zk.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 
/**
 * 
 * @author Allen Hu 
 * 2015-4-17
 */
public class ConcurrentTest {
     
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentTest.class);
    private ZkClientHolder zkClientHolder = new ZkClientHolder("172.16.3.24:2181", 60000);
    private Synchronous synchronous;
     
    public ConcurrentTest() {
       // zkClientHolder.init();
        //synchronous = new MutexLockSynchronous(zkClientHolder);
    }
 
    public class Service implements Runnable {
         
        private final String id;
        private final long sleepInMillis;
         
        public Service(String id, long sleepInMillis) {
            this.id = id;
            this.sleepInMillis = sleepInMillis;
        }
 
        @Override
        public void run() {
            synchronous.execute(ZKPaths.makePath("/nvwa/zookeeper/concurrent", id), new SynchronousProcessor() {
 
                @Override
                public String process() {
                    LOGGER.info(id + " star...!");
                    try {
                        Thread.sleep(sleepInMillis);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    LOGGER.info(id + " has execution!");
                    return id;
                }
 
                @Override
                public void exceptionCaught(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }
 
    static ExecutorService executor = Executors.newCachedThreadPool();
     
    public static void main(String[] args) {
        ConcurrentTest ct = new ConcurrentTest();
        executor.submit(ct.new Service("1", 5000)); // 1号 处理5秒
        executor.submit(ct.new Service("1", 2000)); // 1号 处理2秒
        executor.submit(ct.new Service("2", 5000)); // 2号 处理5秒
        executor.submit(ct.new Service("3", 10000)); // 3号 处理10秒
        executor.submit(ct.new Service("3", 500)); // 3号 处理0.5秒
    }
}


