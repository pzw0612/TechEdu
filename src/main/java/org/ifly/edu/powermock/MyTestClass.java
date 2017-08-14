package org.ifly.edu.powermock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class) 
@PrepareForTest(IdGenerator.class) 
public class MyTestClass { 
   @Test 
   public void demoStaticMethodMocking() throws Exception { 


       new ClassUnderTest().methodToTest(); 


       
       IdGenerator.generateNewId(); 
   } 
}
