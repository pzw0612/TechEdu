package org.ifly.edu.freemarker;

import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 *
 * @author pangzhw
 * @date 2015年2月27日
 */
public class FreemarkConfig {

	   private static Configuration cfg=null;
	   
	   private FreemarkConfig() { 
			// Create your Configuration instance, and specify if up to what FreeMarker
			// version (here 2.3.21) do you want to apply the fixes that are not 100%
			// backward-compatible. See the Configuration JavaDoc for details.
			 cfg = new Configuration(Configuration.VERSION_2_3_21);

			// Specify the source where the template files come from. Here I set a
			// plain directory for it, but non-file-system sources are possible too:
			try {
			
				cfg.setDirectoryForTemplateLoading(new File("D:/eclipse/workspace/TechEdu/src/main/resources/template"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkConfig.class, "template/"));
			//cfg.setClassForTemplateLoading(this.getClass(), "template/");
			
			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			cfg.setDefaultEncoding("UTF-8");
			// Sets how errors will appear.
			// During development TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);  
		   
	   }

	   private static class FreemarkConfigHolder {
	      private final static FreemarkConfig INSTANCE = new FreemarkConfig();
	   }

	   public static FreemarkConfig getInstance() {
	      return FreemarkConfigHolder.INSTANCE;
	   }
	   
	   public  Configuration getConfiguration(){
		   return cfg;
	   }
}
