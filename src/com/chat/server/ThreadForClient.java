package com.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import com.chat.constants.Constants;
import com.chat.util.ClientInputFileParser;
import com.chat.util.LogClass;


public class ThreadForClient implements Runnable{
	private Socket socket;
	private String name;
	private PrintWriter out;
	
	Logger log = LogClass.createLogHandle();
	
	public ThreadForClient(Socket s, int clientId , String name) {
		this.socket = s;		
		this.name = name;
	}
	
	@Override
	public void run() {
		String name = "";
		ClientInputFileParser parser = ClientInputFileParser.getInstance();
		try {
			out = new PrintWriter(socket.getOutputStream(), true);			
			out.println("?");
			
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			name = input.readLine();
			System.out.println(name);
			
			
			if(parser.getClientList() == null){
				log.info("client list is null ... should never come here");
				try {
					throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			PrintWriter pw  = new PrintWriter(socket.getOutputStream(), true);
			parser.setSocket(name,socket);
			parser.setPrintWriter(name, pw);
			parser.setClientThreadID(name, Thread.currentThread().getId());
			
	        while(true){        	
				String dataInput = input.readLine();
				if(dataInput==null || dataInput.equals("null"))
				{
					System.out.println("Closing connection");
					parser.setSocket(name,null);
					parser.setPrintWriter(name, null);
					parser.setClientThreadID(name, 0);
					socket.close();					
					break;
				}
				System.out.println(dataInput);
	        }
		} catch (IOException e) {
			System.out.println(Constants.ERROR + "Connection for client " + name + " is now closed.");
			System.out.println("Continuing self execution .... "); 
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println("In class Thread for client in IOException 3");
				e1.printStackTrace();
			}
			parser.setSocket(name,null);
			parser.setPrintWriter(name, null);
			parser.setClientThreadID(name, 0);			
			//e.printStackTrace();
		} finally{
			try {
				socket.close();
				parser.setSocket(name,null);
				parser.setPrintWriter(name, null);
				parser.setClientThreadID(name, 0);
			} catch (IOException e) {
				System.out.println("In class Thread for client in IOException 2");
				//e.printStackTrace();
			}
		}        
	}	
}

