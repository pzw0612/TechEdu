/**
 * 
 */
package org.ifly.edu.webservice.java;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class Hi {

	@WebMethod
	public String call(String name){
		return name + ",你妈喊你挨打了";
	}
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/webservice/Hi", new Hi());
	}
}
