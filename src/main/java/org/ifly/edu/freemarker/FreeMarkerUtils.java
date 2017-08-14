package org.ifly.edu.freemarker;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Template;

/**
 * 
 * @author pangzhiwang
 *
 */
public class FreeMarkerUtils {
	

    
 
    
    /**
     * 根据模板和数据生成完整的内容
     * @param root
     * @param templateFile
     * @return
     */
    public static String genContent(Map<String,Object> root, String templateFile){
    	FreemarkConfig freeMarkerConfigurer = FreemarkConfig.getInstance();
    	if(freeMarkerConfigurer==null) {
    		System.out.println("system error");
    		return "";
    	}
        String htmlText=""; 
        
        try {    
            Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate(templateFile);    
            htmlText=FreeMarkerTemplateUtils.processTemplateIntoString(tpl,root);    
        } catch (Exception e) {    
            e.printStackTrace();    
        }   
        
        return htmlText;   
    }
    
    
    public static void main(String[] args) {
		
    	List<SchemaMailTaskVo> list = new ArrayList<SchemaMailTaskVo>();
    	
    	SchemaMailTaskVo vo = new SchemaMailTaskVo();
    	vo.setId("192.168.16.149_1442394960604_0");
    	vo.setPoolId("shareservice/product-basic");
        vo.setServiceName("QueryPmInfoRemoteService");
        vo.setMethodName("queryBasePmInfoByIdList");
        vo.setCreateTime(new Date());
        vo.setExceptionStr("org.springframework.jdbc.BadSqlGrammarException: SqlMapClient operation; bad SQL grammar []; nested exception is com.ibatis.common.jdbc.exception.NestedSQLException:"   
                    +"--- The error occurred while applying a parameter map. "
         );
        list.add(vo);
         vo = new SchemaMailTaskVo();
        vo.setId("192.168.16.149_1442394960604_1");
        vo.setPoolId("shareservice/product-basic");
        vo.setServiceName("QueryPmInfoRemoteService");
        vo.setMethodName("queryBasePmInfoByIdList");
        vo.setCreateTime(new Date());
        vo.setExceptionStr("org.springframework.jdbc.BadSqlGrammarException: SqlMapClient operation; bad SQL grammar []; nested exception is com.ibatis.common.jdbc.exception.NestedSQLException:"  
     +"--- The error occurred while applying a parameter map. "+

	"at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:204)"+
	"at $Proxy58.queryBasePmInfoByIdList(Unknown Source)"+
	"at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)" +
	"at sun.reflect.NativeMethodAcc"   
        		
        		);
        list.add(vo);
    	
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("currentDate", "2015-09-17 9:41");
        root.put("schemaMailTaskList", list);
        
        System.out.println(genContent(root,"test1.html"));
	}}

