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
	
	
	public RunDB(){
		

    }
	
	public void run() {
		while(true)
		{
			try {

				MainClient.lblStat3.setText("RD:001");
				
				LoadDB.funReadDBParts();
				
				LoadDB.funReadDBMachineIO();
				
				Thread.sleep(100);
				
			} catch (InterruptedException e) { }
		}
	}

	
}
