package com.chat.util;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogClass {

	public static Logger createLogHandle()
	{
		Logger log = Logger.getLogger("MyLog");
		try{			
			FileHandler fileHandler;
			fileHandler = new FileHandler("logs\\Logs"+System.currentTimeMillis()+".txt");  
			log.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();  
			fileHandler.setFormatter(formatter);
		}catch(Exception e){
			
		}
		return log;
	}
	
	public static void closeLogger(){
		
	}
}
