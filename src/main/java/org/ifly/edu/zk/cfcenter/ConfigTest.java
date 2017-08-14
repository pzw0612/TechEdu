package org.ifly.edu.zk.cfcenter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author pangzhiwang
 * @date 2016-10-19
 */
public class ConfigTest {
	 
	 
    @Test
    public void testZkConfig() throws JsonProcessingException, InterruptedException {
 
        ConfigManager cfgManager = new ConfigManager();
        ClientApp clientApp = new ClientApp();
 
        //模拟【配置管理中心】初始化时，从db加载配置初始参数
        cfgManager.loadConfigFromDB();
        //然后将配置同步到ZK
        cfgManager.syncFtpConfigToZk();
 
        //模拟客户端程序运行
        clientApp.run();
 
        //模拟配置修改
        cfgManager.updateFtpConfigToDB(23, "localhost", "newUser", "newPwd");
        cfgManager.syncFtpConfigToZk();
 
        //模拟客户端自动感知配置变化
        clientApp.run();
 
    }
 
 
}
