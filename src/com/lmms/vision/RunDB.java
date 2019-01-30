package com.lmms.vision;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TimerTask;

import org.sikuli.script.Screen;

import com.android.dx.util.FileUtils;

public class RunDB implements Runnable  {
	
	public static Boolean doneLoad = false;
	
	public RunDB(){
		

    }
	
	public void run() {
		while(true)
		{
			MainClient.lblStat3.setText("RD:001");
			SequenceDBReadParts();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void SequenceDBReadParts()
	{
		try{
			LoadDB.funReadDBParts();
			LoadDB.funReadDBMachineIO();
			//LoadInspection.funNewOCR();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
