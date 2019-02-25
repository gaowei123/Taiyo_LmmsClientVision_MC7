package com.lmms.vision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import org.sikuli.script.*;

import net.sourceforge.tess4j.*;


import org.sikuli.basics.Settings;


public class LoadInspection {
	
	public static String passStatus;
	public static String failStatus;
	public static Boolean inspectAvai = false;
	public static int insTotalPass = 0;
	public static int insTotalFail = 0;
	public static boolean isCheckingResult = false;
	
	public static String[] graName = new String[35];
	
	
	
	
	public static void funResultReadingFile()  throws IOException, InterruptedException
	{
		System.out.println("----Reading File Logic Debug---- In Function funResultReadingFile");
		
		//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   
		System.out.println(" LOAD INSPECTION -- 1.1.3 							LoadDB.isReflashQTY = " + String.valueOf(LoadDB.isReflashQTY));
	    while(LoadDB.isReflashQTY)
		{
			System.out.println(" LOAD INSPECTION -- 1.1.4 											LoadDB.isReflashQTY = " + String.valueOf(LoadDB.isReflashQTY));
	        
			Thread.sleep(50);
		}
		
		isCheckingResult =true;
		
		
		//1.0 get folder path
		String path =  ConfigLog.readingFilePath;
		System.out.println("----Reading File Logic Debug---- Folder path:" + path);
		
		
		//2.0 get file name
		File LatestFile = getLastModifiedFile(path);
		
		if(LatestFile == null) {
			isCheckingResult =false;
			return ;
		}
		
		String FileName = LatestFile.getName();
		System.out.println("----Reading File Logic Debug---- File Name:" + FileName);
		
	
		//3. Read file to Buffer
		BufferedReader BufferRead = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path + "\\" + FileName)), "UTF-8"));
		
		
		
    	int PassCount =0;
    	int FailCount = 0;
    	int TotalCount = 0;
		String LastMarkName = "";
		boolean PassFailFlag = true;
		
		//4. foreach each line   Main Counting Logic
		String Str_ReadLine =BufferRead.readLine();
		while(Str_ReadLine != null) 
		{
			System.out.println("----Reading File Logic Debug---- String Line : "+Str_ReadLine);
			
			if(Str_ReadLine.equals("")) {
				continue;
			}
			
			String[] ArrLine = Str_ReadLine.split(",");
			String MarkName="";
			String MarkResult ="";
			
			try {
				 MarkName = ArrLine[1].split("--")[1];
			}
			catch(Exception e)
			{
				MarkName = ArrLine[1];
			}
				
			try {
				 MarkResult = ArrLine[3];
			}
			catch(Exception e) 
			{
				 MarkResult = "NG";
			}
			
			System.out.println("----Reading File Logic Debug---- Line Result --  MarkName:"+MarkName+",  MarkResult:"+MarkResult);
			
			//when read a new file, lastmarkname is empty, set first line mark name (deflaut)
			if(LastMarkName.equals(""))
				LastMarkName = MarkName;
			
			if(MarkName.equals(LastMarkName)) 
			{
				if(MarkResult.equals("OK") && PassFailFlag!= false)
				{
					PassFailFlag = true;
				}
				else
				{
					PassFailFlag = false;
				}
			}
			else
			{
				if(PassFailFlag)
				{
					TotalCount ++;
					PassCount ++;
				}
				else
				{
					TotalCount ++;
					FailCount ++;
				}
				
				
				//Reset Flag  Mark Name For new mark
				PassFailFlag = true;
				LastMarkName = MarkName;
				
				//Check the new mark
				if(MarkResult.equals("OK")  && PassFailFlag!= false)
				{
					PassFailFlag = true;
				}
				else
				{
					PassFailFlag = false;
				}
			}
		
			
			Str_ReadLine =BufferRead.readLine();
		}
		
		//Count The Latest Mark
		if(PassFailFlag)
		{
			TotalCount ++;
			PassCount ++;
		}
		else
		{
			TotalCount ++;
			FailCount ++;
		}
		
		BufferRead.close();
	
		System.out.println("----Reading File Logic Debug---- Reading File Finish Result : TotalCount:"+TotalCount+",  PassCount:"+PassCount+",  FailCount:"+FailCount);
		
		
		//5.0 copy logic from funResultCheck()
    	try{
    		    passStatus ="false";
    		    failStatus = "false";
    		    
				if (PassCount >0 ) { 
					insTotalPass=insTotalPass + PassCount ;
					MainClient.lblTotalPass.setText(Integer.toString(insTotalPass));
					
					MainClient.lblOK.setBackground(new Color(0, 255, 0));
	            	MainClient.lblNG.setBackground(new Color(105, 105, 105)); 
	            	
	            	passStatus = "true";
	            	LoadDB.mainResult = "PASS";
	            }
				
				
	            if (FailCount > 0) {
	            	insTotalFail = 	insTotalFail + FailCount;
	             	MainClient.lblTotalFail.setText(Integer.toString(insTotalFail));
	             	
	            	MainClient.lblOK.setBackground(new Color(105, 105, 105)); 
	            	MainClient.lblNG.setBackground(new Color(255, 0, 0)); 
	            	
	            	failStatus = "true";
	            	LoadDB.mainResult = "FAIL";  //IF HAS FAIL COUNT, RECOVER THE RESULT AFTER PASS COUNT
	            }
	            
	            MainClient.lblTotalOK.setText(Integer.toString(PassCount) );
	            MainClient.lblTotalNG.setText(Integer.toString(FailCount) );
	            MainClient.lblTotalPart.setText(Integer.toString(FailCount+PassCount) );
	
	            
	            LoadDB.todayTotalQuantity =  LoadDB.todayTotalQuantity +PassCount +  FailCount;
            	LoadDB.todayOKTotalQuantity = LoadDB.todayOKTotalQuantity + PassCount;
            	LoadDB.todayNGTotalQuantity = LoadDB.todayNGTotalQuantity + FailCount;
            	
            	funGetCurrentQuantity(); 
            	LoadDB.funSendCountPassAndFailLMMS();
            	LoadDB.funSendResultLMMS();
        		
            	MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity)); 
            	
                MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
 				MainClient.lblStat.setBackground(new Color(105, 105, 105));
 				MainClient.lblStat4.setBackground(new Color(105, 105, 105));
 				MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
 				MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
        	
    	}
		catch (Exception e)
    	{
			System.out.println(e.toString());
			isCheckingResult =false;
			BufferRead.close();
    	}
    	
    	BufferRead.close();
    	
        isCheckingResult = false;
		
	}
	
	private static File getLastModifiedFile(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	public static void funGetCurrentQuantity()
	{
		int total = insTotalPass + insTotalFail;
		LoadDB.currentQuantity = total;
		MainClient.lblCQuantity.setText(Integer.toString(total));
	}
	
	
}
