package com.staxter.task1.player.config;

public class ClientConfig {
	
	String hostToCall;
	int portToCall;
	
	public ClientConfig(String hostToCall, int portToCall) {
		super();
		this.hostToCall = hostToCall;
		this.portToCall = portToCall;
	}
	public String getHostToCall() {
		return hostToCall;
	}
	public void setHostToCall(String hostToCall) {
		this.hostToCall = hostToCall;
	}
	public int getPortToCall() {
		return portToCall;
	}
	public void setPortToCall(int portToCall) {
		this.portToCall = portToCall;
	}
	
}
