package org.ifly.edu.zk.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
 
 
public class LockStartup {
 
    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZooKeeperFactory.get(); 
        final InterProcessSemaphoreMutex processSemaphoreMutex = new InterProcessSemaphoreMutex(client, "/lock");
        printProcess(processSemaphoreMutex);
         
        System.out.println("Starting get lock...");
        boolean flag = processSemaphoreMutex.acquire(12, TimeUnit.SECONDS);
        System.out.println(flag ? "Getting lock successful." : "Getting failed!");
         
        printProcess(processSemaphoreMutex);
         
        Thread.sleep(20 * 1000);
         
        if (processSemaphoreMutex.isAcquiredInThisProcess()) {
            processSemaphoreMutex.release();
        }
        printProcess(processSemaphoreMutex);
        client.close();
    }
     
    private static void printProcess(final InterProcessSemaphoreMutex processSemaphoreMutex) {
        // 在本进程中锁是否激活（是否正在执行）
        System.out.println("isAcquiredInThisProcess: " + processSemaphoreMutex.isAcquiredInThisProcess());
    }
 
}
