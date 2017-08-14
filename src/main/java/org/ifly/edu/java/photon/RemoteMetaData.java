package org.ifly.edu.java.photon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yhd.arch.photon.common.HostInfo;
import com.yhd.arch.photon.constants.Constants;
import com.yhd.arch.photon.constants.PhotonStatus;
import com.yhd.arch.photon.emitter.router.BalancerType;
import com.yhd.arch.photon.util.PhotonUtil;
import com.yhd.arch.zone.RoutePriority;


public class RemoteMetaData {

	private String serviceName;
	private String callMode;
	private long timeout;
	private long readTimeout;
	private Boolean flowControl;
	private Long limit;
	@SuppressWarnings("rawtypes")
	private Class objType;
	private String hosts;
	private Map<String, HostInfo> hostInfos = new HashMap<String, HostInfo>();

	private boolean retry;
	private BalancerType lbType = BalancerType.WEIGHT_ROUNDROBIN;
	private String target;
	private String authorization;
	private Set<String> nonRetryMethods;
	private String lookupPath;
	private String profileUUId;

	@SuppressWarnings("rawtypes")
	public RemoteMetaData(String serviceName, String callMode, long timeout, long readTimeout, Class objType, String hosts,
			boolean isRetry, int senderCount) {
		this.serviceName = serviceName;
		this.callMode = callMode;
		this.timeout = timeout;
		this.objType = objType;
		this.hosts = hosts;
		this.readTimeout = readTimeout;
		this.retry = isRetry;
		checkHost();
	}

	@SuppressWarnings("rawtypes")
	public RemoteMetaData(String serviceName, String callMode, long timeout, long readTimeout, Class objType, boolean isRetry,
			int senderCount, BalancerType type) {
		this.serviceName = serviceName;
		this.callMode = callMode;
		this.timeout = timeout;
		this.objType = objType;
		this.readTimeout = readTimeout;
		this.retry = isRetry;
		this.lbType = type;
		checkHost();
	}

	@SuppressWarnings("rawtypes")
	public RemoteMetaData(String serviceName, String callMode, long timeout, long readTimeout, Class objType, String hosts,
			int senderCount, BalancerType type, boolean isRetry) {
		this.serviceName = serviceName;
		this.callMode = callMode;
		this.timeout = timeout;
		this.objType = objType;
		this.hosts = hosts;
		this.readTimeout = readTimeout;
		this.retry = isRetry;
		this.lbType = type;
		checkHost();
	}

	@SuppressWarnings("rawtypes")
	public RemoteMetaData(String serviceName, String callMode, int timeout, Class objType, Boolean flowControl, Long limit, boolean isRetry) {
		this.serviceName = serviceName;
		this.callMode = callMode;
		this.timeout = timeout;
		this.objType = objType;
		this.flowControl = flowControl;
		this.limit = limit;
		this.retry = isRetry;
	}

	public Set<String> getNonRetryMethods() {
		return nonRetryMethods;
	}

	public void setNonRetryMethods(Set<String> nonRetryMethods) {
		this.nonRetryMethods = nonRetryMethods;
	}

	private void checkHost() {
		if (this.hosts != null) {
			String[] hostArray = this.hosts.split(Constants.HOSTS_CONNECTOR);
			for (String host : hostArray) {
				if (PhotonUtil.isHostStr(host)) {
					// hostInfos.put(host, new HostInfo(host));
				} else {
					throw new IllegalArgumentException("Hosts is illegalArgument：" + hosts);
				}
			}
		}
	}

	public String getLookupPath() {
		return lookupPath;
	}

	public void setLookupPath(String lookupPath) {
		this.lookupPath = lookupPath;
	}

	public Map<String, HostInfo> getHostInfos() {
		return hostInfos;
	}

	public void setHostInfos(List<HostInfo> newList) {
		if (newList != null) {
			for (HostInfo h : newList) {
				hostInfos.put(h.getHostUrl(), h);
			}
		} else {
			hostInfos = new HashMap<String, HostInfo>();
		}

	}

	public void setHostInfos(Map<String, HostInfo> hostInfos) {
		this.hostInfos = hostInfos;
	}

	public boolean isRetry() {
		return retry;
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCallMode() {
		return callMode;
	}

	public void setCallMode(String callMode) {
		this.callMode = callMode;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Boolean getFlowControl() {
		return flowControl;
	}

	public void setFlowControl(Boolean flowControl) {
		this.flowControl = flowControl;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	@SuppressWarnings("rawtypes")
	public Class getObjType() {
		return objType;
	}

	@SuppressWarnings("rawtypes")
	public void setObjType(Class objType) {
		this.objType = objType;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public BalancerType getLbType() {
		return lbType;
	}

	public void setLbType(BalancerType lbType) {
		this.lbType = lbType;
	}

	public long getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void putHostInfo(HostInfo hostInfo) {
		hostInfos.put(hostInfo.getHostUrl(), hostInfo);
	}

	public void putHostInfoList(List<HostInfo> hostInfoList) {
		if (hostInfoList != null && hostInfoList.size() > 0) {
			for (HostInfo hostInfo : hostInfoList) {
				hostInfos.put(hostInfo.getHostUrl(), hostInfo);
			}
		}
	}

	public void rmHostInfo(HostInfo hostInfo) {
		hostInfos.remove(hostInfo.getHostUrl());
	}

	public HostInfo getHostInfo(String host) {
		HostInfo h = null;
		if (hostInfos != null && hostInfos.size() > 0) {
			h = hostInfos.get(host);
		}
		return h;
	}

	public int getHostWeight(String host) {
		int w = 1;
		HostInfo h = hostInfos.get(host);
		if (h != null) {
			w = h.getWeight() > 0 ? h.getWeight() : w;
		}
		return w;
	}

	public PhotonStatus getHostStatus(String host) {
		PhotonStatus s = PhotonStatus.ENABLE;
		HostInfo h = hostInfos.get(host);
		if (h != null) {
			s = h.getStatus();
		}
		return s;
	}

	public List<HostInfo> getHostInfoList() {
		List<HostInfo> l = new ArrayList<HostInfo>(hostInfos.values());
		return l;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "RemoteMetaData [serviceName=" + serviceName + ", callMode=" + callMode + ", timeout=" + timeout + ", readTimeout="
				+ readTimeout + ", flowControl=" + flowControl + ", limit=" + limit + ", objType=" + objType + ", hosts=" + hosts
				+ ", hostInfos=" + hostInfos + ", retry=" + retry + ", lbType=" + lbType + ", target=" + target + "]";
	}

	public RoutePriority getHostRoutePriority(String uniqMethodName, String host) {
		RoutePriority rp = RoutePriority.None;
		HostInfo info = hostInfos.get(host);
		if (info != null) {
			rp = info.getHostRoutePriority(uniqMethodName);
		}
		return rp;
	}

	public String getProfileUUId() {
		return profileUUId;
	}

	public void setProfileUUId(String profileUUId) {
		this.profileUUId = profileUUId;
	}
}