package org.ifly.edu.zk.lock;

public interface Synchronous<T> {

    /**
     * 同步执行，根据path标识来区分同步工作。不同的path将不会同步进行。
     * 
     * @param处理结果类型
     * @param path 任务节点
     * e.g. "/project/synchronous/0000001"
     * @param processor 业务处理器
     * @return 处理结果
     */
	T execute(String path, SynchronousProcessor processor);

}
