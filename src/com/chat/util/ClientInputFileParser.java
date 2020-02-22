package com.chat.util;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public class ClientInputFileParser {
	Map<String, ClientInfo> clientList = new HashMap<>();
	static Logger log;
	static int count=0;
	static String inputFilePath;
	static ClientInputFileParser clientInputFileParserSigleObj;
	
	private ClientInputFileParser(){	
	}
	
	public boolean isTargetNameExisting(String targetName){
		return clientList.containsKey(targetName);
	}
	public static ClientInputFileParser getInstance()
	{
		if(clientInputFileParserSigleObj==null)
		{
			clientInputFileParserSigleObj = new ClientInputFileParser();			
		}
		return clientInputFileParserSigleObj;
	}
	
	
	@Override
	public String toString() {
		return "Ex1_ClientInputFileParser [clientList=" + clientList + "]";
	}

	public void fileParser(String inputFilePath){
		try{
			log = LogClass.createLogHandle();
			
			BufferedReader in = new BufferedReader(new FileReader(inputFilePath));
			
			String line;
			while( (line= in.readLine()) !=null )
			{
				String[] data = line.split(" ");
				
				ClientInfo client = new ClientInfo();
				client.setClientName(data[0]);
				client.setBindAddress(data[1]);
				client.setPort(Integer.parseInt(data[2]));
				client.setSocket(null);
				
				clientList.put(data[0], client);
			}
			//setClientList(clientList);
		}		
		catch(Exception e){
			e.printStackTrace();
		}
		log.info("From file parser :: clientLsit parsed : " + clientList);
	}
	

	public void setClientList(Map<String, ClientInfo> clientList) {
		this.clientList = clientList;
	}

	public Map<String, ClientInfo> getClientList() {
		return clientList;
	}
	
	public int getServerPort(String name){
		System.out.println(clientList);
		return clientList.get(name).getPort();
	}
	public static void main(String[] args){
		//new Ex1_ClientInputFileParser("C:/Users/Uma/client_details.txt");
	}

	public void setSocket(String name, Socket socket) {
		clientList.get(name).setSocket(socket);
		System.out.println(clientList);
		
	}

	public Socket getSocket(String clientName) {
		// TODO Auto-generated method stub
		return clientList.get(clientName).getSocket();
	}

	public boolean getIsSocketCreated(String clientName) {
		// TODO Auto-generated method stub
		return clientList.get(clientName).isSocketCreated();
	}

	public PrintWriter getPrintWriter(String clientName) {
		// TODO Auto-generated method stub
		return  clientList.get(clientName).getPw();
	}

	public void setPrintWriter(String clientName, PrintWriter pw) {
		clientList.get(clientName).setPw(pw);
	}
	public void setClientThreadID(String clientName, long threadID){
		clientList.get(clientName).setClientThreadId(threadID);
	}

	public int getPort(String string) {
		// TODO Auto-generated method stub
		return clientList.get(string).getPort();
	}
}