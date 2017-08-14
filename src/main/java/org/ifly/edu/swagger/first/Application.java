package org.ifly.edu.swagger.first;

/**
 * 
 * @author pangzhiwang
 * @date 2016-11-7
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication        //same as @Configuration+@EnableAutoConfiguration+@ComponentScan
@EnableSwagger2             //启动swagger注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
