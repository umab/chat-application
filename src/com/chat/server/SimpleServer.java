package com.chat.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

import com.chat.client.ClientThread;
import com.chat.constants.Constants;
import com.chat.util.ClientInfo;
import com.chat.util.ClientInputFileParser;
import com.chat.util.LogClass;


public class SimpleServer {
	static Logger log;
	static ClientInputFileParser userInput;
	static int clientCount = 0;

	public static void main(String[] args) {
		Socket socket = null;
		ClientInfo clientInfo = null;
		try{
			
			log = LogClass.createLogHandle();
			String msg="";
			userInput =  ClientInputFileParser.getInstance();
			userInput.fileParser(args[0]);
			System.out.println(userInput);
			
			Listener ListenerSocket = new Listener(userInput.getServerPort(args[1]));
            Thread ListenerThread = new Thread(ListenerSocket);
            ListenerThread.start();             

            /********
             * this code writes and waits forever *************/
            String data;
    		ClientInputFileParser parser = ClientInputFileParser.getInstance();
    		ClientThread clientThread;
    		Thread clientThreadStarter;
    		
    			System.out.println("Accepting keyboard ");
    			while(true)
    			{
    				
    					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    					data = br.readLine();
    					if(data == null || data.equals("null")){
    						System.out.println("This connection does not exist");
    						break;
    					}
    					String[] dataSeparated = data.split(" ");    					
    					
    					if(dataSeparated.length <= 1){
    						//msg=Constants.ERROR + "Invalid number of arguments.";
    						System.out.println(Constants.ERROR + "Invalid number of arguments.");
    						System.out.println(Constants.INFO + Constants.HELP_STR_CHAT);
    						System.out.println(Constants.INFO + Constants.HELP_STR_START_STOP);
    						//log.info(msg);
    						continue;
    					}
    					if(dataSeparated[0].toUpperCase().equals(args[1])){
    						msg=Constants.INFO + "Cannot connect to itself.";
    						//log.info(msg);
    						
    						System.out.println(Constants.INFO + "Cannot connect to itself.");
    						continue;
    					}
    					if(parser.getClientList().containsKey(dataSeparated[0]) == false)
    					{
    						msg=Constants.INFO + "Invalid client name";
    						//log.info(msg);
    						System.out.println(msg);
    						continue;
    					}
    					if(dataSeparated[1].toLowerCase().equals(Constants.START)){
    						//System.out.println("INSIDE START");
    						boolean socketCreated = parser.getIsSocketCreated(dataSeparated[0]);
    						if(dataSeparated.length > 2)
    						{
    							msg=Constants.ERROR + "Invalid arguments for starting connection.";
        						//log.info(msg);
    							System.out.println(msg);
    							System.out.println(Constants.INFO + Constants.HELP_STR_START_STOP);
    							continue;
    						}
    						if(socketCreated == true){
    							msg=Constants.INFO + "Connetion to " + dataSeparated[0] + " already exists.";
        						//log.info(msg);
    							System.out.println(msg);
    							continue;
    						}    						
    						clientThread = new ClientThread(dataSeparated, args[1]);
    						clientThreadStarter = new Thread(clientThread);
    						clientThreadStarter.start();
    						continue;
    					}
    					else if(dataSeparated[1].toLowerCase().equals(Constants.STOP)){
    						//System.out.println("INSIDE STOP");
    						boolean socketCreated = parser.getIsSocketCreated(dataSeparated[0]);
    						
    						if(dataSeparated.length > 2)
    						{
    							msg=Constants.ERROR + "Invalid arguments for stopping connection.";
        						//log.info(msg);
    							System.out.println(msg);
    							System.out.println(Constants.INFO + Constants.HELP_STR_START_STOP);
    							continue;
    						}
    						if(socketCreated == false){
    							msg=Constants.INFO + "Connetion to " + dataSeparated[0] + " does not exist.";
        						//log.info(msg);
    							System.out.println(msg);
    							continue;
    						}
    						//System.out.println("I am here : stopping the socket");
    						ClientInfo client = parser.getClientList().get(dataSeparated[0]);
    						clientInfo = client;   						
    						
    						socket = parser.getSocket(dataSeparated[0]);
    						System.out.println("SOCKETS ARE : " + socket + " : " + client.getSocket());
    						socket.close();
    						
    						client.setPw(null);
    						System.out.println("1");
    						client.setSocket(null);
    						System.out.println("2");
    						continue;
    					}
    					else if(dataSeparated[1].toLowerCase().equals(Constants.CHAT)){
    						System.out.println("INSIDE CHAT");
    						if(dataSeparated.length < 3)
    						{
    							msg=Constants.ERROR + "Invalid arguments for chat.";
        						//log.info(msg);
    							System.out.println(msg);
    							System.out.println(Constants.INFO + Constants.CHAT);
    							continue;
    						}
    						boolean socketCreated = parser.getIsSocketCreated(dataSeparated[0]);
	    					PrintWriter pw;
	    					
	    					if(socketCreated)
	    					{
	    						socket = parser.getSocket(dataSeparated[0]);
	    						pw = parser.getPrintWriter(dataSeparated[0]);
	    					}
	    					else
	    					{
	    						msg=Constants.ERROR + "No connection for " + dataSeparated[0] + " exists";
	    						System.out.println(msg);
	    						continue;
	    					}
	    					int count = dataSeparated[0].length() + dataSeparated[1].length() + 2;
	    					pw.println(args[1] + " says : " + data.substring(count));
	    					continue;
    					}
    					else{
    						msg=Constants.ERROR + "Invalid syntax.";
    						//log.info(msg);
    						System.out.println(msg);
    						System.out.println(Constants.HELP_STR_START_STOP);
    						System.out.println(Constants.HELP_STR_CHAT );
    						continue;
    					}
    				}
    				
    	}
		catch(SocketException e){
			e.printStackTrace();
		}		
		catch(Exception e){
			//e.printStackTrace();
			System.out.println("Trying to close sockets");
			try{			
			clientInfo.setPw(null);
			clientInfo.setSocket(null);
			if(socket != null)
				socket.close();
			}catch(Exception e1){
				System.out.println("couldnot close socket");
			}
    		//e.printStackTrace();
    	}	
	}
}