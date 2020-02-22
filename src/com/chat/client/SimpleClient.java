package com.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

import com.chat.util.ClientInfo;
import com.chat.util.LogClass;
import com.chat.util.dummyClientWriter;

public class SimpleClient {
	static Map<String, ClientInfo> clientList;
	public static void main(String[] args) throws IOException {
		
        Logger log = LogClass.createLogHandle();
		
        // ****************** input validation ******************
        switch(args.length)
		{
		case 0: log.info("Client :: Exception ::::: command line arguments not present");
				System.exit(1);
		case 1: log.info("Client :: Exception ::::: client name not present");
				System.exit(1);
		}
		log.info("From client :: input : " + args[0] + ", " + args[1]);
		
		// ****************** parsing file ******************
		/*ClientInputFileParser parser =  ClientInputFileParser.getInstance();
		parser.fileParser(args[0]);
		clientList = parser.getClientList();
		ClientInfo client = clientList.get(args[1]);		
		log.info("From client :: list : "+ clientList + "\n match : " + client);
		System.out.println(parser);
		*/
		
		// ****************** binding to server and communication ******************
		Socket socket = new Socket("", 9191);

		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String serverReply = input.readLine();
		System.out.println(serverReply);
		  
        log.info("Client ::  " + serverReply);
        
        pw.println("C");
        
        dummyClientWriter t = new dummyClientWriter(socket);
        Thread thread = new Thread(t);
        thread.start(); 
             
         while(true){        	
			String name = input.readLine();
			System.out.println(name);
        }
        
        /*while(true){
        	try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	break;
        }*/
	}

}
