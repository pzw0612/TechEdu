package org.ifly.edu.zk.cfcenter;

import com.yihaodian.architecture.zkclient.ZkClient;

/**
 * 
 * @author pangzhiwang
 * @date 2016-10-19
 */
public class ZKUtil {
	 
    public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";
 
    public static ZkClient getZkClient() {
        return new ZkClient("localhost:2181");
    }
 
 
}
