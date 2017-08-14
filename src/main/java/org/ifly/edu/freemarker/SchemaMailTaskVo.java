package org.ifly.edu.freemarker;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author pangzhiwang
 *
 */
public class SchemaMailTaskVo  implements Serializable{
 
	private static final long serialVersionUID = 8240600529796365109L;
	
	private String id;
	
	private String poolId;
	private String serviceName;
	private String methodName;
	private String exceptionStr;
	private Date createTime;
	
	private Integer status;
	
	public String getPoolId() {
		return poolId;
	}
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getExceptionStr() {
		return exceptionStr;
	}
	public void setExceptionStr(String exceptionStr) {
		this.exceptionStr = exceptionStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SchemaMailTaskVo [id=" + id + ", poolId=" + poolId
				+ ", serviceName=" + serviceName + ", methodName=" + methodName
				+ ", exceptionStr=" + exceptionStr + ", createTime="
				+ createTime + ", status=" + status + "]";
	}

}
