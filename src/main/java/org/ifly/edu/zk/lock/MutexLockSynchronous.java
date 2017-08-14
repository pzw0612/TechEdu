package org.ifly.edu.zk.lock;

import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.common.PathUtils;
 
 
/**
 * 基于普通排他锁的方式实现同步
 * 
 * @author Allen Hu 
 * 2015-4-17
 */
public class MutexLockSynchronous //implements Synchronous
{
 
    private final ZkClientHolder zkClientHolder;
     
    public MutexLockSynchronous(ZkClientHolder zkClientHolder) {
        this.zkClientHolder = zkClientHolder;
    }
 
//    @Override
//    public T execute(String path, SynchronousProcessor processor) {
//        PathUtils.validatePath(path);
//        InterProcessLock lock = new InterProcessMutex(zkClientHolder.get(), path);
//        try {
//            lock.acquire();
//            if (null != processor)
//                return processor.process();
//        }
//        catch (Exception e) {
//            if (null != processor)
//                processor.exceptionCaught(e);
//        }
//        finally {
//            try {
//                lock.release();
//            }
//            catch (Exception e) {
//            }
//        }
//        return null;
//    }
}


