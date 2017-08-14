package org.ifly.edu.java.concurrent.executors;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;


public class MonitorJmsSendUtil {

	/** 初始化实例 */
	private static final Logger logger = Logger.getLogger(MonitorJmsSendUtil.class);
	private static final MonitorJmsSendUtil INSTANCE = new MonitorJmsSendUtil();
	private static ExecutorService executor;
	private static ScheduledExecutorService scheduledExecutor;
	//private static Map<String, BatchMessage> batchSendMap = new HashMap<String, BatchMessage>();
	private static Set<String> logFilterApps = new HashSet<String>();
	private static BlockingQueue<Runnable> queueToUse;
	private static Random random = new Random();

	/** 当前MQ类型，activeMQ , jumper **/
	private MonitorJmsSendUtil() {
		try {
			ThreadFactory tf = new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "bizLog message sender thread");
					t.setDaemon(true);
					return t;
				}
			};
			queueToUse = new LinkedBlockingQueue<Runnable>(8);
			/** corePoolSize类似连接池的maxActive的概念 */
			executor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, queueToUse, tf, new ThreadPoolExecutor.DiscardOldestPolicy());
			ThreadFactory tf2 = new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "MonitorJmsSendUtil-scheduledExecutor-thread");
					// t.setDaemon(true);
					return t;
				}
			};
			scheduledExecutor = Executors.newSingleThreadScheduledExecutor(tf2);
			scheduledExecutor.scheduleAtFixedRate(getScheduleBizLogTask(), 3000, 8000, TimeUnit.MILLISECONDS);
			/** 添加JVM运行时Hook，保证在JVM停止前，将所有的队列消息发送出去 */
			Runtime.getRuntime().addShutdownHook(new Thread(getDistoryTask()));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public static void destroy() {
		try {
			if (null != executor) {
				executor.shutdown();
				executor.awaitTermination(1, TimeUnit.MINUTES);
			}
		} catch (Exception e) {
			if (null != logger) {
				logger.error("Exception occurs when shuting down the executor:\n{}", e);
			}
		}
		try {
			if (null != scheduledExecutor) {
				scheduledExecutor.shutdown();
				scheduledExecutor.awaitTermination(1, TimeUnit.MINUTES);
			}
		} catch (Exception e) {
			if (null != logger) {
				logger.error("Exception occurs when shuting down the scheduledExecutor:\n{}", e);
			}
		}
//		JumperMessageSender.destroy();
	}
	
	
	/**
	 * 获取需定时执行Task， 日志发送规则：每次往队列发送不超过defaultCount
	 * 
	 * @return
	 */
	private static Runnable getScheduleBizLogTask() {
		return new Runnable() {
			public void run() {
				try {
					// 批量异步发送业务端日志
//					for (Entry<String, BatchMessage> entry : batchSendMap.entrySet()) {
//						//asyncSendMessage(entry.getValue());
//						entry.getValue().getMessagecounts().set(0);
//					}

				} catch (Exception e) {
					if (null != logger) {
						logger.error("Error occurs for asyncSendMessage for batchSendMap.");
					}
				}
			}
		};
	}
	
	/**
	 * 获取需定时执行Task， 日志发送规则：每次往队列发送不超过defaultCount
	 * 
	 * @return
	 */
	private static Runnable getDistoryTask() {
		return new Runnable() {
			public void run() {
				try {
				//	if (null != batchSendMap) {
						// 批量异步发送业务端日志
//						for (Entry<String, BatchMessage> entry : batchSendMap.entrySet()) {
//							asyncSendMessage(entry.getValue());
//							entry.getValue().getMessagecounts().set(0);
//						}
					//}
				} catch (Exception e) {
					if (null != logger) {
						logger.error("Error occurs for asyncSendMessage for batchSendMap.");
					}
				} finally {
					try {
						//MonitorJmsSendUtil.destroy();
						if (null != logger) {
							logger.info("MonitorJmsSendUtil.destroy() was called.");
						}
					} catch (Exception e) {
						if (null != logger) {
							logger.error("Error occurs for MonitorJmsSendUtil.destroy().", e);
						}
					}
				}
			}
		};
	}
}
