package org.ifly.edu.memcached.basic;

import org.ifly.edu.memcached.SockIOPool;
import org.ifly.edu.memcached.MemcachedClient;

/**
 * 
 * @author pangzhiwang
 * @date 2016-10-30
 */
public class TestMemcached {
	public static void main(String[] args) {    
        /*初始化SockIOPool，管理memcached的连接池*/    
        String[] servers = { "localhost:11211","localhost:11221","localhost:11231" };  
        Integer[] weights       = { new Integer(3), new Integer(2) , new Integer(1)};
        SockIOPool pool = SockIOPool.getInstance();    
        pool.setServers(servers);    
        pool.setFailover(true);    
        pool.setInitConn(10);    
        pool.setMinConn(5);    
        pool.setMaxConn(250);    
        pool.setMaintSleep(30);    
        pool.setNagle(false);    
        pool.setSocketTO(3000);    
        pool.setAliveCheck(true);    
        pool.setWeights(weights);
        		pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH );
        		pool.setAliveCheck( true );
        pool.initialize();    
        /*建立MemcachedClient实例*/    
        MemcachedClient memCachedClient = new MemcachedClient();    
        
        for (int i = 0; i < 10; i++) {    
        	
            /*将对象加入到memcached缓存*/    
            boolean success = memCachedClient.set("" + i, "Hello!");    
            /*从memcached缓存中按key值取对象*/    
            String result = (String) memCachedClient.get("" + i);    
            System.out.println(String.format("set( %d ): %s", i, success));    
            System.out.println(String.format("get( %d ): %s", i, result));    
        }    
    }    
}
