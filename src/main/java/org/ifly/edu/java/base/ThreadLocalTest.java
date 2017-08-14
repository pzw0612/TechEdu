package org.ifly.edu.java.base;

public class ThreadLocalTest {

	public static void main(String[] args) {
		String str = WriteMethodUtil.getInputArgs().get();
		System.out.println(str);

		if (null == str) {
			WriteMethodUtil.getInputArgs().set("aaa");
		}
		System.out.println(WriteMethodUtil.getInputArgs().get());
	}
}

class WriteMethodUtil {
	private static ThreadLocal<String> inputArgs = new ThreadLocal<String>();

	public static ThreadLocal<String> getInputArgs() {
		return inputArgs;
	}

	public static void setInputArgs(ThreadLocal<String> inputArgs) {
		WriteMethodUtil.inputArgs = inputArgs;
	}
}