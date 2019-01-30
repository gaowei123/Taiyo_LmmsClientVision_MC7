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
	
	public static String partNumber;
	public static String modeStatus;
	public static String passStatus;
	public static String failStatus;
	public static String currentStatus;
	public static Screen scr = new Screen();
	public static int insX;
	public static int insY;
	public static Boolean inspectAvai = false;
	public static int insWidth = 40;
	public static int insHeight = 18;
	public static int insGap = 14;
	 
	public static int passPX = 0;
	public static int passPY = 0;
	public static int failPX = 0;
	public static int failPY = 0;

	
	public static String[] posGx = new String[5];
	
	public static String[] graName = new String[35];
	public static String[] graPos = new String[35];
	public static String[] graResult = new String[35];
	
	public static String[] masName = new String[35];
	public static String[] masPos = new String[35];
	public static String[] masResult = new String[35];
	
	public static String[] procName = new String[35];
	public static String[] procPos = new String[35];
	public static String[] procResult = new String[35];
	
	public static int insTotalPass = 0;
	public static int insTotalFail = 0;
	//private static OCR ocr = null;
	public static boolean isCheckingResult = false;
	public static void funResultCheck()
	{
    	try{
			while (true) {
				isCheckingResult =true;
				//if (scr.exists("VisionSikuli/passIcon.png") != null) {
				if (scr.exists(ConfigLog.passiconSet) != null) {
					
					
					
					insTotalPass++;
					MainClient.lblTotalPass.setText(Integer.toString(insTotalPass));
					//if ((scr.exists("VisionSikuli/passIcon.png") != null) || (scr.exists("VisionSikuli/passIcon2.png") != null)) {
	            	MainClient.lblstatPass.setBackground(new Color(0, 0, 255));
	            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
	            	passStatus = "true";
	            	failStatus = "false";
	            	LoadDB.mainResult = "PASS";
	            	funGetCurrentQuantity();
	            	LoadDB.funSendCountPassLMMS();
	            	LoadDB.funSendResultLMMS();
	            	
	            	LoadDB.todayTotalQuantity++;
	            	LoadDB.todayOKTotalQuantity++;
	        		MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity));
	                break;
	            }
				else
				{
					MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				}
				
	            //if (scr.exists("VisionSikuli/failIcon.png") != null) {
	            if (scr.exists(ConfigLog.failiconSet) != null) {	
	            	MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
	            	MainClient.lblstatFail.setBackground(new Color(0, 0, 255));
	            	insTotalFail++;
	            	passStatus = "false";
	            	failStatus = "true";
	            	LoadDB.mainResult = "FAIL";
	            	funGetCurrentQuantity();
	            	MainClient.lblTotalFail.setText(Integer.toString(insTotalFail));
	            	LoadDB.funSendCountFailLMMS();
	            	LoadDB.funSendResultLMMS();
	            	
	            	LoadDB.todayTotalQuantity++;
	            	LoadDB.todayNGTotalQuantity++;
	        		MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity));
	                break;
	            }
	            else
	            {
	            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
	            }
	            
	            //break;
	            isCheckingResult=false;
	        }
    	}
		catch (Exception e)
    	{
			
    	}
	}
	
	public static void funGetCurrentQuantity()
	{
		int total = insTotalPass + insTotalFail;
		LoadDB.currentQuantity = total;
		MainClient.lblCQuantity.setText(Integer.toString(total));
		
	}
	
	
	/************************2018 06 22 auto capture the Machine PASS * Failed QTY for TESTING ********************************************/
	/*** Start *******************************************/
	public static int MachinePassValue=0;
	public static int MachineFailedValue=0;
	public static int increasePassValue=0;
	public static int increaseFailedValue=0;
	
	public static boolean isCheckingMachineQTY =false;
	public static void funMachineQTYCheck()
	{
	    int tmpMachinePassValue=0;
	    int tmpMachineFailedValue=0;
		
    	try{
			while (true) {
				isCheckingMachineQTY = true; 
				
				Settings f = new Settings();
		    	f.OcrTextSearch = true;
		    	f.OcrTextRead = true;
		    	Pattern p = new Pattern("VisionSikuli/passPoint.png");
		    	Match ler = scr.exists(p);
				if (ler != null) {  
					
					int passPointX = ler.x + 70;
					int passPointY = ler.y+4;
					int passWidth =  165;
					int passHeight = ler.h-4;
					
					int failedPontYgap = 31;
		    		ScreenImage capturedRegion = scr.capture(passPointX, passPointY, passWidth, passHeight );
				    ImageIO.write(funImageContrast(capturedRegion.getImage()), "PNG", new File("C:\\data\\MachinePassQTY.png"));

			    	Region sPass = Region.create(passPointX, passPointY, passWidth, passHeight);
			        System.out.println(sPass.text() ); 
			        System.out.println(funTextRepair(sPass.text()) ); 
			    	try
			    	{ 
			    		tmpMachinePassValue =Integer.parseInt( funTextRepair(sPass.text() ));
			    	} catch (NumberFormatException e) {
			    		tmpMachinePassValue = 0;
			    	}
			    	
			    	
			    	capturedRegion = scr.capture(passPointX, passPointY+ failedPontYgap, passWidth, passHeight );
				    ImageIO.write(funImageContrast(capturedRegion.getImage()), "PNG", new File("C:\\data\\MachineFailedQTY.png"));

				    Region sFaile = Region.create(passPointX, passPointY + failedPontYgap, passWidth, passHeight);
			    	System.out.println(sFaile.text() ); 
				    System.out.println(funTextRepair(sFaile.text()) ); 
			    	try
			    	{
			    		tmpMachineFailedValue =Integer.parseInt( funTextRepair(sFaile.text() ));
			    	} catch (NumberFormatException e) {
			    		tmpMachineFailedValue = 0;
			    	}
			    	
			    	//deal with Pass data
			    	if (tmpMachinePassValue < MachinePassValue)
			    	{
			    		increasePassValue = tmpMachinePassValue; //reset by user in machine side
			    	}
			    	else
			    	{
			    		increasePassValue = tmpMachinePassValue - MachinePassValue;
			    	}
			    	MachinePassValue = tmpMachinePassValue;
			    	
			    	//deal with failed data
			    	if (tmpMachineFailedValue < MachineFailedValue)
			    	{
			    		increasePassValue = tmpMachineFailedValue;   //reset by user in machine side
			    	}
			    	else
			    	{
			    		increasePassValue = tmpMachineFailedValue - MachineFailedValue;
			    	}
			    	MachineFailedValue = tmpMachineFailedValue;
			    	
			    	
	                break;
	            }
				else
				{
					MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				}
				
			
	            isCheckingMachineQTY = false;
	            //break;
	            
	        }
    	}
		catch (Exception e)
    	{
			
    	}
	}
	/*** End *******************************************/
	
		public static void funCheckInspectionPos()
	{
		Settings f = new Settings();
    	f.OcrTextSearch = true;
    	f.OcrTextRead = true;
    	
		//Pattern p = new Pattern("VisionSikuli/inspectionTextPos.png");
    	Pattern p = new Pattern(ConfigLog.inspectiontextSet);
    	Match ler = scr.exists(p);
    	//if (scr.exists(p.similar(0.99f)) != null) {    	
    	if (ler != null) {
			//insX = scr.exists(p.similar(0.99f)).x + 182;//1;
			//insY = scr.exists(p.similar(0.99f)).y + 54;
    		insX = ler.x ;
    		insY = ler.y + 20;
			inspectAvai = true;
			MainClient.lblstatInspect.setBackground(new Color(0, 0, 255));
			//LoadDB.funSendInspectLMMS();
		}
		else
		{
			inspectAvai = false;
			MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
			//LoadDB.funSendInspectLMMS();
		}
	}
	
 
	public static String funSikuliOCRS(String urlImg)
	{
        try{
        	
        	String text = scr.find(urlImg).text();
        	System.out.println("T:" + text);
        	return text;
        	
        }
        catch(FindFailed e){
            //e.printStackTrace();
            return "";
        }
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
					
					MainClient.lblstatPass.setBackground(new Color(0, 0, 255));
	            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105)); 
	            	
	            	passStatus = "true";
	            	LoadDB.mainResult = "PASS";
	            }
				else
				{
					MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				} 
				
	            if (FailCount > 0) {	
	            	
	            	insTotalFail = 	insTotalFail + FailCount;
	             	MainClient.lblTotalFail.setText(Integer.toString(insTotalFail));
	             	
	            	MainClient.lblstatPass.setBackground(new Color(105, 105, 105)); 
	            	MainClient.lblstatFail.setBackground(new Color(0, 0, 255)); 
	            	
	            	failStatus = "true";
	            	LoadDB.mainResult = "FAIL";  //IF HAS FAIL COUNT, RECOVER THE RESULT AFTER PASS COUNT
	            }
	            else
	            {
	            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
	            }
	           
	            
	            LoadDB.todayTotalQuantity =  LoadDB.todayTotalQuantity +PassCount +  FailCount;
            	LoadDB.todayOKTotalQuantity = LoadDB.todayOKTotalQuantity + PassCount;
            	LoadDB.todayNGTotalQuantity = LoadDB.todayNGTotalQuantity + FailCount;
            	
            	funGetCurrentQuantity(); 
            	LoadDB.funSendCountPassAndFailLMMS();
            	LoadDB.funSendResultLMMS();
        		
            	MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity)); 
        	
    	}
		catch (Exception e)
    	{
			System.out.println(e.toString());
    	}
    	
        isCheckingResult = false;
		
	}
	
	
	
	
	public static void funResultGraphicsList()  throws IOException, InterruptedException
	{
		if(inspectAvai)
		{
			//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   
			System.out.println(" LOAD INSPECTION -- 1.1.3 							LoadDB.isReflashQTY = " + String.valueOf(LoadDB.isReflashQTY));
		    while(LoadDB.isReflashQTY)
			{
				System.out.println(" LOAD INSPECTION -- 1.1.4 											LoadDB.isReflashQTY = " + String.valueOf(LoadDB.isReflashQTY));
		        
				Thread.sleep(50);
			}
			
			isCheckingResult =true;
			System.out.println( " LOAD INSPECTION -- 2.1 isCheckingResult="+String.valueOf(isCheckingResult));
			  
			Thread.sleep(5000);
			/*while (true) {
				if (scr.exists(ConfigLog.passiconSet) != null) {
					break;
	            }
				else
				{
					MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				}
			
	            if (scr.exists(ConfigLog.failiconSet) != null) {	
	            	break;
	            }
	            else
	            {
	            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
	            }
	            System.out.println("Waiting For Pass-Fail Detection!");
	        }; */
			LoadInspection.scr = new Screen();
			Settings f = new Settings();
	    	f.OcrTextSearch = true;
	    	f.OcrTextRead = true;
	    	
	    	int pointX = insX;//24;
	    	int pointY = insY;
	    	int pointWidth = insWidth;
	    	int pointHeight = insHeight;
	    	    	
	    	for(int j=0;j<16;j++)
	    	{
	    		//Thread.sleep(500);
	    	    ScreenImage capturedRegion1 = scr.capture(pointX, pointY+ 3, pointWidth+70, pointHeight + 4 );
			    ImageIO.write(funImageContrast(capturedRegion1.getImage()), "PNG", new File("C:\\data\\Name" + String.valueOf(j+1) + ".png"));
			      
			    graName[j] = "";
		        Region s1 = Region.create(pointX, pointY+2 , pointWidth+70, pointHeight + 4);
		        graName[j] = funTextRepair(s1.text()); 
		    		
		        ScreenImage capturedRegion2 = scr.capture(pointX+120, pointY+2, pointWidth+40, pointHeight + 4 );
		        ImageIO.write(funImageContrast(capturedRegion2.getImage()), "PNG", new File("C:\\data\\Val" + String.valueOf(j+1) + ".png"));

		        graPos[j]="";  //2018 07 01 by wei lijia set empty to avoid database update error caused by null
	    		Region s2 = Region.create(pointX+120, pointY + 2, pointWidth+40, pointHeight + 4);
	    		graPos[j] = funTextRepair(s2.text()); 
		        
		        ScreenImage capturedRegion3 = scr.capture(pointX + 200, pointY + 2, pointWidth, pointHeight +4 );
		        ImageIO.write(funImageContrast(capturedRegion3.getImage()), "PNG", new File("C:\\data\\Res" + String.valueOf(j+1) + ".png"));
	    		
		        graResult[j] ="";  //2018 07 01 by wei lijia set empty to avoid database update error caused by null
			    Region s3 = Region.create(pointX + 200, pointY + 2, pointWidth, pointHeight +4);
	    		graResult[j] = funTextRepair(s3.text()); 
		        
		        pointY += (insGap + 8);
	    	}
	    	
	    	System.out.println(Arrays.toString(graName));
	    	System.out.println(Arrays.toString(graPos));
	    	System.out.println(Arrays.toString(graResult));
	    	
	    	/************************************************************************/
	    	/*****************Count Logic********************************************/
	    	String[][] tmpPartList = new String[16][2];
	    	for(int k=0; k< 16;k++)
	    	{
	    		String tmpPartName = graName[k].split("-")[graName[k].split("-").length-1].trim();
	    		int iSeq = 0 ;
	    		boolean beStop = false;
	    		if (!tmpPartName.isEmpty())
	    		{
		    		while((iSeq < tmpPartList.length) && !beStop )
		    		{
		    			if( tmpPartList[iSeq][0] == null || tmpPartList[iSeq][0].isEmpty())
		    			{
		    				tmpPartList[iSeq][0]= tmpPartName;
		    				if(graResult[k].trim().equals("OK"))
		    				{
		    					tmpPartList[iSeq][1]= "OK";
		    				}
		    				else if ( graResult[k].trim().equals("NG"))
		    				{
		    					tmpPartList[iSeq][1]= "NG";
		    				}
		    				beStop = true;
		    			}
		    			else
		    			{
		    				if(tmpPartList[iSeq][0].equals(tmpPartName))
		    				{
		    					if ( graResult[k].trim().equals("NG"))
			    				{
			    					tmpPartList[iSeq][1]= "NG";
			    				} 
		    					beStop = true;
		    				}
		    				else
		    				{
		    					//next 
		    				}
		    			}
		    			iSeq++;
		    		}
	    		} 
	    	}
	    	
	    	System.out.println(Arrays.toString(tmpPartList));
	    	
	    	int TotalCount = 0;
	    	int PassCount =0;
	    	int FailCount = 0;
	    	for(int m=0; m< 16;m++)
	    	{
	    		System.out.println(Arrays.toString(tmpPartList[m]));
	    		if(tmpPartList[m][1] !=null && tmpPartList[m][1].equals("OK"))
	    		{
	    			PassCount++;
	    			TotalCount++;
	    		}
	    		if(tmpPartList[m][1] !=null && tmpPartList[m][1].equals("NG"))
	    		{
	    			FailCount++;
	    			TotalCount++;
	    		}
	    	}
	    	
	    	/**********copy logic from funResultCheck()******************/
	    	try{
			 
	    		    passStatus ="false";
	    		    failStatus = "false";
					if (PassCount >0 ) { 
						
						insTotalPass=insTotalPass + PassCount ;
						MainClient.lblTotalPass.setText(Integer.toString(insTotalPass));
						
						MainClient.lblstatPass.setBackground(new Color(0, 0, 255));
		            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105)); 
		            	
		            	passStatus = "true";
		            	LoadDB.mainResult = "PASS";
		            }
					else
					{
						MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
					} 
		            if (FailCount>0) {	
		            	insTotalFail = 	insTotalFail + FailCount;
		             	MainClient.lblTotalFail.setText(Integer.toString(insTotalFail));
		            	MainClient.lblstatPass.setBackground(new Color(105, 105, 105)); 
		            	MainClient.lblstatFail.setBackground(new Color(0, 0, 255)); 
		            	
		            	failStatus = "true";
		            	LoadDB.mainResult = "FAIL";  //IF HAS FAIL COUNT, RECOVER THE RESULT AFTER PASS COUNT
		            }
		            else
		            {
		            	MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
		            }
		           	/*********************/ 
		            
		            LoadDB.todayTotalQuantity =  LoadDB.todayTotalQuantity +PassCount +  FailCount;
	            	LoadDB.todayOKTotalQuantity = LoadDB.todayOKTotalQuantity + PassCount;
	            	LoadDB.todayNGTotalQuantity = LoadDB.todayNGTotalQuantity + FailCount;
	            	
	            	funGetCurrentQuantity(); 
	            	LoadDB.funSendCountPassAndFailLMMS();
	            	LoadDB.funSendResultLMMS();
	        		
	            	MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity)); 
	        		/*********************/
	                
		      
	    	}
			catch (Exception e)
	    	{
				System.out.println(e.toString());
	    	}
	    	
	    	/***********************************************************/
	    	
	    	
	    	if(RunInspection.inspectReady)
	    	{
	    		cout_test++;
	    	}
	    	
	        System.out.println(String.valueOf(cout_test) + Arrays.toString(graResult));
	        LoadDB.funLogResultLMMS();

	        
	        
	        isCheckingResult = false;
			
	        //LoadDB
	        //funClearData();
		}    	
	}
		
	static int cout_test = 0;
	
	public static void funNewOCR()
	{
		//ImageFrame fx = new ImageFrame("SikuliFolder/images/test5.png");
		//String text = ocr.read(fx.getBounds());
		//System.out.println(text);
	}
	
	public static void funExtractData()
	{
		for(int j=0;j<16;j++)
    	{
	        //graPos[j] = funSikuliOCR("C:\\data\\Val" + String.valueOf(j+1) + ".png");      
	       	//graResult[j] = funSikuliOCRRes("C:\\data\\Res" + String.valueOf(j+1) + ".png", j);

	        //graPos[j] = funSikuliOCR("C:\\data\\Val" + String.valueOf(j+1) + ".png");      
	       	//graResult[j] = funSikuliOCRRes("C:\\data\\Res" + String.valueOf(j+1) + ".png", j);
    	}
        //System.out.println(Arrays.toString(graName));
        //System.out.println(Arrays.toString(graPos));
        //System.out.println(Arrays.toString(graResult));
       
	}
	
	public static void funClearData()
	{
		for(int j=0;j<16;j++)
    	{
	        graPos[j] = "";      
	       	File file1 = new File("C:\\data\\Val" + String.valueOf(j+1) + ".png");
	       	file1.delete();
	       	
	       	graResult[j] = "";
	       	File file2 = new File("C:\\data\\Res" + String.valueOf(j+1) + ".png");
	       	file2.delete();
    	}
		
	}
	
	public static BufferedImage funImageContrast(BufferedImage img)
	{
		float brightenFactor = 1.5f;

		RescaleOp op = new RescaleOp(brightenFactor, 0, null);
		img = op.filter(img, img);
		//img = scale(img,570,270);
		
		
		return img;
	}
	
	public static BufferedImage invertImage(BufferedImage inputFile) {

        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                inputFile.setRGB(x, y, col.getRGB());
            }
        }

        return inputFile;
    }
		
	public static String funTextRepair(String text)
	{
    	//text = text.replace('?', '7').replace('E', '6').replace("il]", "0").replace("l]", "0").replace("[]", "0").replace("1]", "0").replace("[|]","0").replace("|]","0").replace("8.88","0.00").replace("6|","0").replace("lJ", "0").replace("D", "0");
		text = text.replace('?', '7').replace('E', '6').replace("il]", "0").replace("l]", "0").replace("[]", "0").replace("1]", "0").replace("[|]","0").replace("|]","0").replace("8.88","0.00").replace("6|","0").replace("lJ", "0").replace("'", "").replace(" ", "").replace("—","-");
    	//System.out.println(text);
    	return text;
	}
	
	public static String funSikuliOCR(String urlImg)
	{
        try{
        	Match sf;
        
        	
        	String text = scr.find(urlImg).text();
        	text = text.replace('?', '7').replace('E', '6').replace("il]", "0").replace("l]", "0").replace("[]", "0").replace("1]", "0").replace("[|]","0").replace("|]","0").replace("8.88","0.00").replace("6|","0").replace("lJ", "0").replace("D", "0");
        	//System.out.println(text);
        	return text;
        	
        }
        catch(FindFailed e){
            //e.printStackTrace();
            return "";
        }
	}
	
	
	public static String funSikuliOCRRes(String urlImg,int ind)
	{
        try{
        	String text = scr.find(urlImg).text();
        	if(!text.equals("PASS"))
        	{
        		text = "FAIL";
        	}
        	//System.out.println(text);
        	return text;
        	
        }
        catch(FindFailed e){
            //e.printStackTrace();
            return "FAIL";
        }
	}
	
	public static String funTessaractTest(String urlImg)
	{
		File imageFile = new File(urlImg);
		ITesseract instance = new Tesseract();
		//System.out.println(imageFile.getName());
		try{
			String result = instance.doOCR(imageFile);
			System.out.println(result);
			return "";
		}
		catch (TesseractException e){
			//System.err.println(e.getMessage());
			return "";
		}
		
	}
	
	
	public static void funDataMatching()
	{
		for(int k=0;k<16;k++)
		{
			//procPos[k] =  Integer.toString(int(masPos[k]) - int(graPos[k]));
			
			
		}
	}
	
	public static BufferedImage funScale(String imgUrl, int dWidth, int dHeight) {
		BufferedImage imageToScale = null;
		try {
			imageToScale = ImageIO.read(new File(imgUrl));
		} catch (IOException e) {
		}
		
		BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.TYPE_BYTE_GRAY);
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
}
