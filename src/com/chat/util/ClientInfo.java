package com.chat.util;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientInfo {
	private String clientName;
	private String bindAddress;
	private int port;
	private Socket socket;
	
	private PrintWriter pw;
	private long clientThreadId;
	
	
	public long getClientThreadId() {
		return clientThreadId;
	}
	public void setClientThreadId(long clientThreadId) {
		this.clientThreadId = clientThreadId;
	}
	public PrintWriter getPw() {
		return pw;
	}
	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}
	public boolean isSocketCreated() {
		if(socket==null)
			return false;
		return true;
	}
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBindAddress() {
		return bindAddress;
	}
	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	@Override
	public String toString() {
		return "ClientInfo [clientName=" + clientName + ", bindAddress="
				+ bindAddress + ", port=" + port + ", socket=" + socket + " , printWriter="+ pw + " ]";
	}
	
	
}