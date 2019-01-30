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

public class RunMachineIO extends TimerTask{
	public static Boolean collectNew = false;
	@Override	
	public void run() {
		SequenceMachineIO();
	}
	
	public static void SequenceMachineIO()
	{
		try{
			LoadDB.funReadDBMachineIO();
		}
		 catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
}
