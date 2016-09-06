package org.jbpm.demo.rest;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.kie.services.client.api.RestRequestHelper;

import java.net.URL;


public abstract class RestEasyClientBase {
	
	String deploymentId = "org.jbpm:Evaluation:1.1";
	//http://127.0.0.1:8888/kie-server/services/rest/runtime/org.jbpm:Evaluation:1.1/process/instance/21?reason=sfdsf&

	String ip = "127.0.0.1";
	
	String port = "8888";
	
	String url = "http://" + ip + ":" + port + "/kie-server/services/rest";
	
	String root = "/runtime/" + deploymentId;
	
	String user = "krisv";
	
	String password = "krisv";

	public RestEasyClientBase(String deploymentId, String ip, String port, String user, String password) {
		this.deploymentId = deploymentId;
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	
	public RestEasyClientBase() {
		
	}
	
	public String root() {
		return root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientRequestFactory getClientRequestFactory() throws Exception {	
		return RestRequestHelper.createRequestFactory(new URL(url), user, password);
	}
	
	public ClientRequestFactory getClientRequestFactory(String user, String password) throws Exception {	
		setUser(user);
		setPassword(password);
		return RestRequestHelper.createRequestFactory(new URL(url), user, password);
	}
	
	public abstract void execute() throws Exception;
	
	ClientResponse<?> checkResponse(ClientResponse<?> responseObj) throws Exception {
        responseObj.resetStream();
        int status = responseObj.getStatus();
		System.out.println("status:" + status);
		if (status != 200) {
           throw new Exception("Response with exception:\n" + responseObj.getEntity(String.class));
        }
        return responseObj;
    }
}
