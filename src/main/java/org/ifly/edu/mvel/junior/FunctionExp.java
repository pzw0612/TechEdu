/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-6 上午12:59:10
 */
public class FunctionExp {
	public static void main(String[] args) throws IOException {
        File scriptFile = new File("src/main/java/org/ifly/edu/mvel/junior/test.el" );
       // VariableResolverFactory resolverFactory = new MapVariableResolverFactory();
        //参数
        Map map = new HashMap();
        map.put("a", 11);
        map.put("b", 12);
       
       MVEL.evalFile(scriptFile, ParserContext.create(), map);
       Object obj = MVEL.eval("add(a,b);", map);
       System.out.println(obj);
	}
}
