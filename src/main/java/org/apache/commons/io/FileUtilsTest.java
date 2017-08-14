/**
 * 
 */
package org.apache.commons.io;

import java.io.File;
import java.net.URL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-6-29 上午7:23:55
 */
public class FileUtilsTest {
    public static void main(String[] args) throws Exception{  
        // TODO Auto-generated method stub  
          
        URL url = new URL("http://d.hiphotos.baidu.com/image/h%3D200/sign=01b7f3087a8b4710d12ffaccf3cec3b2/42a98226cffc1e17319487184290f603738de9f4.jpg");  
          
        File file = new File("d:\\aaaa.jpg");  
          
        FileUtils.copyURLToFile(url, file);  
        System.out.println("success");
  
    } 
}
