
package com.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Logger;

import com.chat.constants.Constants;
import com.chat.util.ClientInputFileParser;
import com.chat.util.LogClass;

public class Listener implements Runnable{

	Logger log;
	String name;
	private int serverPort;
	static private int clientCount = 0;

	
	public Listener(int serverPort,List<Integer> ports) {		
		this.serverPort = serverPort;
	}
	
	public Listener(int serverPort2, String name) {
		 this.serverPort = serverPort2;
		 this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		log = LogClass.createLogHandle();
		
		ServerSocket listener = null;
		try {
			
			listener = new ServerSocket(serverPort);
			log.info("From server :: port created at " + serverPort);
			System.out.println("created");
		
            while (true) {
                Socket socket = listener.accept();
                
                System.out.println("Accepting Client "+socket.getInetAddress());
                log.info("accepting connections ");
                
                ClientInputFileParser parser = ClientInputFileParser.getInstance();
                System.out.println("in listener :: " + parser.getClientList());
                
                ThreadForClient t = new ThreadForClient(socket, ++clientCount, name);
                Thread thread = new Thread(t);
                thread.start();          
            }
        }catch(SocketException soc){
        	//release the resources code
        	System.out.println(Constants.ERROR + "This socket is already started");
        	log.info(Constants.ERROR + "This socket is already started");
        	System.exit(1);
        	//soc.printStackTrace();        	
        }
		catch(Exception e){
        	e.printStackTrace();
        }
        finally {
        	try { 	
				listener.close();
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("failed to close listener");
			}
        }
	}	
}

