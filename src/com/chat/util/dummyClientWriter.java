package com.chat.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class dummyClientWriter implements Runnable {

	Socket socket;
	
	public dummyClientWriter(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run() {

		String data;
		
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			while(true)
			{
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					data = br.readLine();
					
					System.out.println("sending "+ data);
					pw.println(data);
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
