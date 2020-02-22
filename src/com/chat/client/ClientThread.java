package com.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.chat.util.ClientInfo;
import com.chat.util.ClientInputFileParser;

public class ClientThread implements Runnable {

	PrintWriter out;
	String[] dataSeparated;
	String  selfName;
	ClientInputFileParser parser = ClientInputFileParser.getInstance();
	int port;
	
	public ClientThread(String[] dataSeparated, String selfName){
		this.dataSeparated = dataSeparated;
		this.selfName = selfName;
	}
		
	@Override
	public void run() {
		try{
			ClientInfo client = parser.getClientList().get(dataSeparated[0]);
			Socket socket = new Socket(client.getBindAddress() , client.getPort());
			
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String serverReply = input.readLine();
			System.out.println(serverReply);
			
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(selfName);
			
			parser.setSocket(dataSeparated[0],socket);
			parser.setPrintWriter(dataSeparated[0], out);
			parser.setClientThreadID(dataSeparated[0], Thread.currentThread().getId());
			
			while(true){
				String name = input.readLine();
				if(name==null || name.equals("null"))
				{
					System.out.println("closing connection");
					socket.close();
					parser.setSocket(dataSeparated[0],null);
					parser.setPrintWriter(dataSeparated[0], null);
					parser.setClientThreadID(dataSeparated[0], 0);
					break;
				}
				System.out.println(name);
		    }
		}
		catch(SocketException e){
			//e.printStackTrace();
			System.out.println("\n\nSocket for "+dataSeparated[0]+ " does not exist.\n"
					+ " Cannot start requested connection");
		}
		catch(Exception e){
			//e.printStackTrace();
			System.out.println("Cannot start requested connection");
		}
	}
}
