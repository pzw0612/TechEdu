package org.ifly.edu.zk.basic;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class HelloZk {

	public static void main(String[] args) {  
		test3();
	} 
	public static void test2(){
	    try {  
	        ZooKeeper zk = new ZooKeeper("localhost:2181", 30000, null); 
	   
	        String name = zk.create("/company", "alibaba".getBytes(),  
	                Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);  
	        
	        Stat stat = new Stat();  
	        System.out.println(new String(zk.getData(name, null, stat)));  
	        
	        System.out.println(stat);
	        
	        zk.setData(name, "taobao".getBytes(), stat.getVersion(), null, null);  
	        System.out.println(stat.getVersion());
	        System.out.println(stat.getCversion());
	        System.out.println(new String(zk.getData(name, null, stat)));  
	        stat = zk.exists(name, null);  
	        zk.delete(name, stat.getVersion(), null, null);  
	       
	        System.out.println(stat.getVersion());
	       // System.out.println(new String(zk.getData(name, null, stat)));  
	    	//关闭session
	    	zk.close();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}
	public static void test(){
	    try {  
	    	//创建一个Zookeeper实例，第一个参数为目标服务器地址和端口，第二个参数为Session超时时间，第三个为节点变化时的回调方法
	    	ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 500000,new Watcher() {
	    	           // 监控所有被触发的事件
	    	             public void process(WatchedEvent event) {
	    	           //dosomething
	    	            	 
	    	            	// System.out.println(event.getPath() + " " + event.getState().name());
	    	           }
	    	      });
	    	zk.delete("/root", -1);
	    	
	    	//创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
	    	String data =zk.create("/root", "mydata".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

	    	System.out.println("data=" +data);
	    	
	    	//在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
	    	String data2 = zk.create("/root/childone","childone".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

	    	zk.create("/root/childone2","childone2".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
	    	System.out.println("data2=" +data2);
	    	
	    	//取得/root节点下的子节点名称,返回List<String>
	    	List<String> list =zk.getChildren("/root",true);
	    	for (String str : list) {
	    		System.out.println("str=" +str);
			}

	    	//取得/root/childone节点下的数据,返回byte[]
	    	byte[] bteArr = zk.getData("/root/childone", true, null);
	    	System.out.println("bteArr=" + new String(bteArr));

	    	//修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
	    	zk.setData("/root/childone","childonemodify".getBytes(), -1);

	    	//删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
	    	zk.delete("/root/childone", -1);
	    	      
	    	
	    	//关闭session
	    	zk.close();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	static void test3() {
	    try { 

		// 创建一个与服务器的连接
		 ZooKeeper zk = new ZooKeeper("localhost:" + 2181,  500000, new Watcher() { 
		            // 监控所有被触发的事件
		            public void process(WatchedEvent event) { 
		                System.out.println("已经触发了" + event.getType() + "事件！"); 
		            } 
		        }); 
		 if(zk.exists("/testRootPath", false) != null){
			 zk.delete("/testRootPath", -1);
		 }
		 // 创建一个目录节点
		 zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
		   CreateMode.PERSISTENT); 
		 
		 // 创建一个子目录节点
		 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
		   Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		 
		 // 取出子目录节点列表
		 System.out.println(zk.getChildren("/testRootPath",true)); 
		 // 修改子目录节点数据
		 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
		 zk.setData("/testRootPath/testChildPathOne","aaaa".getBytes(),-1); 
		 System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
		 
		 // 创建另外一个子目录节点
		 zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), 
		   Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,null))); 
		 
		 // 删除子目录节点
		 zk.delete("/testRootPath/testChildPathTwo",-1); 
		 zk.delete("/testRootPath/testChildPathOne",-1); 
		 
		 // 删除父目录节点
		 zk.delete("/testRootPath",-1); 
		 // 关闭连接
		 zk.close(); 
		 
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}
	
}
