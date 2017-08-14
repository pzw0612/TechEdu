package org.ifly.edu.spring.aop.MethodInterceptor.log;

import org.aspectj.bridge.IMessage;


public class SysException extends RuntimeException  {
	/** 国际化框架根据msgKey转换系统错误消息提示 */
	public String getMsgKey() {
		return "";
	}

	public SysException() {
		super();
	}

	public SysException(String message) {
		super(message);
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
	}

}
