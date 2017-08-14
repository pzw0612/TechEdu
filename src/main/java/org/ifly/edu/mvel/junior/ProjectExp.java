/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ifly.edu.mvel.model.User;
import org.mvel2.MVEL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-6 上午12:53:06
 */
public class ProjectExp {

	public static void main(String[] args) {
        User u1 = new User("张三" );
        User u2 = new User("李四" );
        User u3 = new User("王二" );
        List<User> l = new ArrayList<User>();
        l.add(u1);
        l.add(u2);
        l.add(u3);
         Map vars = new HashMap();
         vars.put("users",l);
        String expression = "foo=(name in users);foo";
        Object obj = MVEL. eval(expression,vars );
        System. out.println(obj);
	}
}
