package org.ifly.edu.zk.lock;

/**
 * 同步业务处理器
 * 
 * @author Allen Hu 
 * 2015-4-17
 */
public interface SynchronousProcessor<T>{
 
    /**
     * 处理具体的业务
     * 
     * @return
     */
    T process();
     
    /**
     * 异常捕获
     * 
     * @param throwable
     */
    void exceptionCaught(Throwable throwable);
}
