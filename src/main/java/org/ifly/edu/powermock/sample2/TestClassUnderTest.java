package org.ifly.edu.powermock.sample2;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class TestClassUnderTest {

	/**
	 * Mock参数传递的对象
	 */
	@Test
	public void testCallArgumentInstance() {

		File file = PowerMockito.mock(File.class);

		ClassUnderTest underTest = new ClassUnderTest();

		PowerMockito.when(file.exists()).thenReturn(true);

		Assert.assertTrue(underTest.callArgumentInstance(file));

	}

	/**
	 * Mock方法内部new出来的对象
	 * 
	 * @throws Exception
	 */
	@Test
	@PrepareForTest(ClassUnderTest.class)
	public void testCallInternalInstance() throws Exception {

		File file = PowerMockito.mock(File.class);

		ClassUnderTest underTest = new ClassUnderTest();

		PowerMockito.whenNew(File.class).withArguments("bbb").thenReturn(file);

		PowerMockito.when(file.exists()).thenReturn(true);

		Assert.assertTrue(underTest.callInternalInstance("bbb"));

	}

	/**
	 * Mock普通对象的final方法
	 */
	@Test
	@PrepareForTest(ClassDependency.class)
	public void testCallFinalMethod() {
		ClassDependency depencency = PowerMockito.mock(ClassDependency.class);

		ClassUnderTest underTest = new ClassUnderTest();

		PowerMockito.when(depencency.isAlive()).thenReturn(true);

		Assert.assertTrue(underTest.callFinalMethod(depencency));

	}

	/**
	 * Mock普通类的静态方法
	 */
	@Test
	@PrepareForTest(ClassDependency.class)
	public void testCallStaticMethod() {

		ClassUnderTest underTest = new ClassUnderTest();

		PowerMockito.mockStatic(ClassDependency.class);

		PowerMockito.when(ClassDependency.isExist()).thenReturn(true);

		Assert.assertTrue(underTest.callStaticMethod());

	}

	/**
	 * 私有方法
	 * @throws Exception
	 */
	@Test
	@PrepareForTest(ClassUnderTest.class)
	public void testCallPrivateMethod() throws Exception {

		ClassUnderTest underTest = PowerMockito.mock(ClassUnderTest.class);

		PowerMockito.when(underTest.callPrivateMethod()).thenCallRealMethod();

		PowerMockito.when(underTest, "isExist").thenReturn(true);
		
		Assert.assertTrue(underTest.callPrivateMethod());

	}

	/**
	 * Mock系统类的静态和final方法 
	 */
	@Test
	@PrepareForTest(ClassUnderTest.class)
	public void testCallSystemStaticMethod() {

		ClassUnderTest underTest = new ClassUnderTest();

		PowerMockito.mockStatic(System.class);

		PowerMockito.when(System.getProperty("aaa")).thenReturn("bbb");

		Assert.assertEquals("bbb", underTest.callJDKStaticMethod("aaa"));

	}
}