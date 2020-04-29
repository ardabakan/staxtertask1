package com.staxter.task1.player.config;

public class ServerConfig {

	public ServerConfig(String hostToListen, int portToListen) {
		super();
		this.hostToListen = hostToListen;
		this.portToListen = portToListen;
	}
	public String getHostToListen() {
		return hostToListen;
	}
	public void setHostToListen(String hostToListen) {
		this.hostToListen = hostToListen;
	}
	public int getPortToListen() {
		return portToListen;
	}
	public void setPortToListen(int portToListen) {
		this.portToListen = portToListen;
	}
	String hostToListen;
	int portToListen;

}
