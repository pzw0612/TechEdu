package org.ifly.edu.powermock;

import org.mockito.Mockito;

public class ClassUnderTest {
	
	MockTest2 tt = Mockito.mock(MockTest2.class);
			
	 public void methodToTest() { 
	       
	        final long id = IdGenerator.generateNewId(); 
	     } 
	 public void methodToTest2() { 
		 
		 Mockito.when(tt.getStr()).thenReturn("2");
		 
		 System.out.println(tt.getStr());
	 } 
	 
	 public static void main(String[] args) {
		new ClassUnderTest().methodToTest2();
	}
}
