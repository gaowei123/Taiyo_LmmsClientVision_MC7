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
	public static Boolean inspectReady = false;
	
	public static Boolean inspectReadyBuf = false; //2018 01 20 by Wei LiJia
	
	public RunInspection(){
		
	}

	public void run() {
		
		while(true)
		{
			
			try {
				
				MainClient.lblStat4.setText("RI:001");	
				
				SequenceInspection();
				
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void SequenceInspection()
	{
		try{
			
			ProcessInspect();
		}
		 catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void ProcessInspect() throws Exception
	{
	
		if(LoadDB.rmsStatus.equals("loadlaser") || LoadDB.rmsStatus.equals("runinspect")||LoadDB.rmsStatus.equals("adjust"))
		{
			if(!CheckJobComplete())
			{
				//==================================== New Logic  reading txt file. dwyane 2019-1-30 ====================================//
				
				MainClient.lblStat.setBackground(new Color(0, 255, 0));
				
				try {
					LoadInspection.funResultReadingFile();
				} catch (IOException e) {
					//e.printStackTrace();
				} catch (InterruptedException e) {
					//e.printStackTrace();
				}
				
				
				MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
				MainClient.lblStat.setBackground(new Color(105, 105, 105));
				MainClient.lblStat4.setBackground(new Color(105, 105, 105));
				MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
				
				//==================================== New Logic  reading txt file. dwyane 2019-1-30 ====================================//
				
				
				
				
				
				//==================================== old logic base on catch pic  no use ====================================//
				/*
				 * if(inspectReady && !inspectReadyBuf) {
				 * 
				 * MainClient.lblStat.setBackground(new Color(0, 255, 0));
				 * 
				 * LoadInspection.funCheckInspectionPos();
				 * 
				 * try {
				 * 
				 * LoadInspection.funResultGraphicsList(); } catch (IOException e) {
				 * //e.printStackTrace(); } catch (InterruptedException e) {
				 * //e.printStackTrace(); }
				 * 
				 * inspectReady = false; inspectReadyBuf = true;
				 * 
				 * MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
				 * MainClient.lblStat.setBackground(new Color(105, 105, 105));
				 * MainClient.lblStat4.setBackground(new Color(105, 105, 105));
				 * MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
				 * MainClient.lblstatFail.setBackground(new Color(105, 105, 105)); }
				 */
				
				//==================================== old logic base on catch pic  no use ====================================//
			
			}
		}
			
		
		// whatever remove file.
		BackUpFile();
			  
	
	}
	
	public static void BackUpFile () throws Exception 
	{
		String currFilePath = ConfigLog.readingFilePath;
		
		File dir = new File(currFilePath);
		File[] files = dir.listFiles();
		if(files.length == 0)
			return;
		
		
		//check backup folder whether exist   if not create
		String BackupPath = currFilePath + "\\\\Backup";
		File file =new File(BackupPath);    
		
		if(!file.exists()  && !file.isDirectory())
		{       
		    file .mkdir();    
		}
		
		
		//check today backup folder whether exist   if not create
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String TodayFolder = BackupPath + "\\\\" + sdf.format(new Date());
		File file2 =new File(TodayFolder);
		
		if(!file2.exists()  && !file2.isDirectory())
		{       
			file2 .mkdir();    
		}
		
		
		//copy file
		copyFile(currFilePath,TodayFolder);
		
		//delete file
		delFile(currFilePath);
		
	}
	
	public static void copyFile(String FileFromPath, String FileToPath) throws IOException
	{
		//nothing return
		File FileFrom = new File(FileFromPath);
		if(!FileFrom.exists()  && !FileFrom.isDirectory())
			 return;
		
		
        File FileTo = new File(FileToPath);
        if(!FileTo.exists()  && !FileTo.isDirectory()) {
        	FileTo.mkdir();
        }
        
        
        File[] AllTxtFile = FileFrom.listFiles();
        
        for(File file : AllTxtFile) {
        	
        	if(file.isDirectory())
        		continue;
        	
        	FileInputStream in = new FileInputStream(file);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        	String FileToName = FileTo + "\\\\" + file.getName().split("\\.")[0] + "_" + sdf.format(new Date()) + ".txt";
            FileOutputStream out = new FileOutputStream(FileToName);
        	
            byte[] buffer=new byte[102400];
            int readByte = 0;
            while((readByte = in.read(buffer)) != -1){
                out.write(buffer, 0, readByte);
            }
        
            in.close();
            out.close();
        }
        
    }
	
	 public static void delFile(String path) throws InterruptedException 
	 {
		File delPath = new File(path);
		if(!delPath.exists()  && !delPath.isDirectory())
			return ;
		
		 
		File[] delFiles = delPath.listFiles();
		
		for(File file : delFiles) {
			
			if(file.isDirectory())
				continue;
		
			Boolean Result = file.delete();
			
			if(Result) {
				Thread.sleep(100);
				gc.collect();
				Thread.sleep(50);
				file.delete();
			}
			
		}
		 
		return;
	 }
	
	
	public static Boolean CheckJobComplete() throws InterruptedException
	{
		if((LoadInspection.insTotalPass + LoadInspection.insTotalFail) >= LoadDB.totalQuantity)
		{
			//if(!LoadDB.runTechnician)
			{
				MainClient.lblCompleteStatus.setVisible(true);
				MainClient.lblCompleteStatus.setText("JOB COMPLETE! - PLEASE SCAN NEXT JOB NUMBER");
				MainClient.lblCompleteStatus.setBackground(new Color(105, 105, 105));
				Thread.sleep(1000);
				MainClient.lblCompleteStatus.setBackground(new Color(181, 230, 29));
			}
			if(startNew)
			{
				startNew = false;
				LoadDB.funSendCompleteLMMS();
			}
			return true;
		}
		else
		{
			//if(!LoadDB.runTechnician)
			{
				MainClient.lblCompleteStatus.setVisible(false);
			}
			if(!startNew)
			{
				startNew = true;
				LoadDB.funReadLastWatchLog();
				LoadDB.funSendStartVisionLMMS();
			}
			return false;
		}
	}
	
	
}
