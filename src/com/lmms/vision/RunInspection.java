package com.lmms.vision;

import java.awt.AWTException;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimerTask;

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
			MainClient.lblStat4.setText("RI:001");	
			SequenceInspection();
			try {
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
	
	public static void ProcessInspect() throws IOException, InterruptedException
	{
		//LoadInspection.funCheckInspectionPos();
		//LoadInspection.funResultGraphicsList();
		//LoadDB.funReadDBParts();
		//2018 01 24 by Wei LiJia for Decrease Counting Issue
		try { 
			//LoadDB.funStatusLog("LoadDB.currentOperation","LoadDB.rmsStatus","RunInspection.startNew","inspectReady","inspectReadyBuf","","","","","","","","","","",""
			//		,LoadDB.currentOperation,LoadDB.rmsStatus, String.valueOf(startNew),String.valueOf(inspectReady),String.valueOf(inspectReadyBuf),"","","","","","","","","","","");
		} catch (Exception e) {
			//e.printStackTrace();
		} 
		//if(LoadDB.currentOperation.equals("VISION"))
		{
			
			if(LoadDB.rmsStatus.equals("loadlaser") || LoadDB.rmsStatus.equals("runinspect")||LoadDB.rmsStatus.equals("adjust"))
			{
				if(!CheckJobComplete())
				{
					//2018 01 20 by Wei LiJia add inspectReadyBuf
					//if(inspectReady)
					//System.out.println("1. "+String.valueOf(inspectReady) + " " + String.valueOf(inspectReadyBuf)) ;
					if(inspectReady && !inspectReadyBuf)
					{
						
						MainClient.lblStat.setBackground(new Color(0, 255, 0));
						//LoadInspection.funResultCheck(); //2018 06 28 MARKED BY WEI LIJIA. ADD THIS LOGIC INTO LoadInspection.funResultGraphicsList() FOR MULTI-PART IN ONE JIG FOR MACHINE 6+7
						
						LoadInspection.funCheckInspectionPos();
						try {
							
							LoadInspection.funResultGraphicsList();
						} catch (IOException e) {
							//e.printStackTrace();
						} catch (InterruptedException e) {
							//e.printStackTrace();
						}
						inspectReady = false;
						inspectReadyBuf = true;
						MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
						MainClient.lblStat.setBackground(new Color(105, 105, 105));
						MainClient.lblStat4.setBackground(new Color(105, 105, 105));
						MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
						MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
					}
				
				}
			}
			
		}

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
