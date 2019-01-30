package com.lmms.vision;

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

public class RunSys implements Runnable{
	
	public RunSys(){
		
	}
	
	public void run() {
		MainClient.lblStat.setText("RS:001");
		while(true)
		{
			SequenceVision();
		}
	}
	
	public static void SequenceVision()
	{
		try{
			LoadVision.funcheckVision();
			Thread.sleep(100);
		}
		 catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
}
