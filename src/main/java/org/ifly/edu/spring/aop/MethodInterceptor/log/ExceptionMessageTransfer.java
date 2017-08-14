package org.ifly.edu.spring.aop.MethodInterceptor.log;

import java.util.HashSet;
import java.util.Set;

import org.aspectj.bridge.IMessage;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class ExceptionMessageTransfer {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Set<String> noCatchedExceptions = new HashSet<String>();

	public Set<String> getNoCatchedExceptions() {
		return noCatchedExceptions;
	}

	public void setNoCatchedExceptions(Set<String> noCatchedExceptions) {
		this.noCatchedExceptions = noCatchedExceptions;
	}

	/**
	 * 捕获异常进行处理
	 * 
	 * @param joinPoint
	 *            连接点
	 * @param ex
	 *            *捕获的异常
	 * 
	 */
	public void handle(JoinPoint joinPoint, Throwable ex) {
		/* 过滤无需处理的异常 */
		for (String exClassName : noCatchedExceptions) {
			try {
				Class clazz = Class.forName(exClassName);
				if (clazz.isAssignableFrom(ex.getClass()))
					return; // 类似Clazz obj = ex;
			} catch (ClassNotFoundException e1) {
				logger.error(
						"applicationContext-exception: propertity noCatchedExceptions setting error",
						e1);
			}
		}

		/* 转换成Flex可以处理的系统异常抛出 */
		if (ex instanceof IMessage)
			return;
		throw new SysException("not handlered exception!!", ex);
	}

}
