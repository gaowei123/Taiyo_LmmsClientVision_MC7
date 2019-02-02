package com.lmms.vision;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;

import org.sikuli.basics.Settings;
import org.sikuli.script.Screen;

import com.lmms.vision.MainClient;

import org.sikuli.script.App;
import org.sikuli.script.Key;
import org.sikuli.script.Location;
import org.sikuli.script.Pattern;

public class LoadVision {
	
	public static Boolean checkVision = false;
	public static Screen scr;
	
	public static void main (String[] args) throws AWTException {
		
	}
	
	public static void funcheckVision () throws AWTException {
    	try{
    		MainClient.lblStat.setText("RS:003");
    		//if (scr.exists("VisionSikuli/visionLoad-afifiPC.png") != null) {
    		if (scr.exists(ConfigLog.visionloadSet) != null) {
            	checkVision = true;
            	MainClient.lblstatVision.setBackground(new Color(0, 0, 255));
            }
            else
            {
            	checkVision = false;
            	MainClient.lblstatVision.setBackground(new Color(105, 105, 105));
            }

    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
}
