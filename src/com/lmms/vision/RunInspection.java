package com.lmms.vision;

import java.awt.AWTException;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.python.modules.gc;
import org.sikuli.script.Screen;

//import com.lmms.vision.LoadVision;
import com.lmms.vision.MainClient;

public class RunInspection implements Runnable{
	
	public static Boolean startNew = false;
	
	public RunInspection(){
		
	}

	public void run() {
		while(true)
		{
			try {
				
				Thread.sleep(100);
				
				MainClient.lblStat4.setText("RI:001");
				
				ProcessInspect();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public static void ProcessInspect() throws Exception
	{
		try 
		{
			if(!LoadDB.rmsStatus.equals("technician")) {
				if(!CheckJobComplete())
				{
					MainClient.lblStat.setBackground(new Color(0, 255, 0));
					LoadInspection.isCheckingResult = true;
					LoadInspection.funCounting();
					LoadInspection.isCheckingResult = false;
				}
			}
		}
		catch (IOException e) { 
			LoadInspection.isCheckingResult = false;
		} catch (InterruptedException e) {
			LoadInspection.isCheckingResult = false;
		}
		
		//whatever count or not, backup & remove file.
	    CommonFunc.BackUpFile();
	}
	
	
	
	
	public static Boolean CheckJobComplete() throws InterruptedException
	{
		if((LoadInspection.insTotalPass + LoadInspection.insTotalFail) >= LoadDB.totalQuantity)
		{
			if(startNew)
			{
				startNew = false;
				LoadDB.funSendCompleteLMMS();
			}
			
			return true;
		}
		
		else
		{
			if(!startNew)
			{
				startNew = true;
			}
			return false;
		}
	}
	
	
	
	
}
