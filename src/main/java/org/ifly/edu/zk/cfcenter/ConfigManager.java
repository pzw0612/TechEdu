package org.ifly.edu.zk.cfcenter;

/**
 * 
 * @author pangzhiwang
 * @date 2016-10-19
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yihaodian.architecture.zkclient.ZkClient;

 
 
public class ConfigManager {
 
    private FtpConfig ftpConfig;
 
 
    /**
     * 模拟从db加载初始配置
     */
    public void loadConfigFromDB() {
        //query config from database
        //TODO...
        ftpConfig = new FtpConfig(21, "localhost", "test", "123456");
    }
 
 
    /**
     * 模拟更新DB中的配置
     *
     * @param port
     * @param host
     * @param user
     * @param password
     */
    public void updateFtpConfigToDB(int port, String host, String user, String password) {
        if (ftpConfig == null) {
            ftpConfig = new FtpConfig();
        }
        ftpConfig.setPort(port);
        ftpConfig.setHost(host);
        ftpConfig.setUser(user);
        ftpConfig.setPassword(password);
        //write to db...
        //TODO...
    }
 
    /**
     * 将配置同步到ZK
     */
    public void syncFtpConfigToZk() throws JsonProcessingException {
        ZkClient zk = ZKUtil.getZkClient();
        if (!zk.exists(ZKUtil.FTP_CONFIG_NODE_NAME)) {
            zk.createPersistent(ZKUtil.FTP_CONFIG_NODE_NAME, true);
        }
        zk.writeData(ZKUtil.FTP_CONFIG_NODE_NAME, ftpConfig);
        zk.close();
    }
 
 
}
