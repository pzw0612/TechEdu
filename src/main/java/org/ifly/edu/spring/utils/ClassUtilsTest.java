/**
 * 
 */
package org.ifly.edu.spring.utils;

import org.springframework.util.ClassUtils;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-4-3 下午11:07:11
 */
public class ClassUtilsTest {
 public static void main(String[] args) {
	
	 // short name 
	 System.out.println(ClassUtils.getShortName(ClassUtilsTest.class));
	 
	 // class FileName
	 System.out.println(ClassUtils.getClassFileName(ClassUtilsTest.class));
	 
	 System.out.println(ClassUtils.getShortNameAsProperty(ClassUtilsTest.class));
	 
	 
	 System.out.println(ClassUtils.getPackageName(ClassUtilsTest.class));
	 
}
}
