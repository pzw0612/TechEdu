package org.ifly.edu.spring.aop.MethodInterceptor.log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogger {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Set<String> noCatchedExceptions = new HashSet<String>();

	public Set<String> getNoCatchedExceptions() {
		return noCatchedExceptions;
	}

	public void setNoCatchedExceptions(Set<String> noCatchedExceptions) {
		this.noCatchedExceptions = noCatchedExceptions;
	}

	/**
	 * 捕获异常记录日志.
	 * 
	 * @param joinPoint
	 *            连接点，可通过连接点获取目标对象，目标方法签名，及方法参数
	 * @param ex
	 *            捕获的异常
	 */
	public void log(JoinPoint joinPoint, Throwable ex) {
		/* 过滤不记录日志的异常 */
		for (String exClassName : noCatchedExceptions) {
			try {
				Class clazz = Class.forName(exClassName.trim());
				if (clazz.isAssignableFrom(ex.getClass()))
					return;
			} catch (ClassNotFoundException e1) {
				logger.error(
						"applicationContext-exception: propertity noCatchedExceptions setting error:",
						e1);
			}
		}

		List<Object> args = java.util.Arrays.asList(joinPoint.getArgs());
		String strArgs = args.toString();
		Object[] objs = new Object[] { joinPoint.getSignature().getName(),
				strArgs.substring(0, strArgs.length() - 1).substring(1) };
		String message = "{}({}) execute error!!";
		StringBuffer buf = new StringBuffer(message).append("\r\nStackTrace:")
				.append("aa");

		logger.error(buf.toString(), objs);
	}

}
