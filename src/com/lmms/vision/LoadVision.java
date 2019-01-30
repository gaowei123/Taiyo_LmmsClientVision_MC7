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
	public static Boolean checkAdam = false;
	public static Boolean checkFail = false;
	public static Boolean checkPass = false;
	public static Boolean checkRunning = false;
	public static Boolean checkInspection = false;
	public static Boolean checkIn1 = false;
	public static Boolean checkIn2 = false;
	public static Boolean checkIn3 = false;
	public static Boolean checkIn4 = false;
	public static Boolean checkIn5 = false;
	public static Boolean checkIn6 = false;
	public static Boolean checkIn7 = false;
	public static Boolean checkIn8 = false;
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
	
	public static void funclickModel () throws AWTException {
    	try{
    		//Pattern p = new Pattern("VisionSikuli/selectModel-afifiPC.png");
    		while(true)
    		{
	    		Pattern p = new Pattern(ConfigLog.selectmodelSet);
	            if (scr.exists(p) != null) {
	            	//scr.click("VisionSikuli/selectModel-afifiPC.png");
	            	scr.click(ConfigLog.selectmodelSet);
	            	break;
	            }
    		}
    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funclickReset () throws AWTException {
    	try{
    		//while(true)
    		{
	    		//Pattern p = new Pattern("VisionSikuli/selectModel-afifiPC.png");
	    		Pattern p = new Pattern(ConfigLog.resetpointSet);
	            if (scr.exists(p) != null) {
	            	//scr.click("VisionSikuli/selectModel-afifiPC.png");
	            	scr.click(ConfigLog.resetpointSet);
	            	//break;
	            }
    		}
    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funclickConfirmReset () throws AWTException {
    	try{
    		//while(true)
    		{
	    		Pattern p = new Pattern("VisionSikuli/resetInfo-mc8.png");
	    		//Pattern p = new Pattern(ConfigLog.resetpointSet);
	            if (scr.exists(p) != null) {
	            	//scr.click("VisionSikuli/selectModel-afifiPC.png");
	            	scr.click("VisionSikuli/yesPoint-mc8.png");
	            	//break;
	            }
    		}
    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funclickPartModel() throws AWTException {
    	try{
    		while(true)
    		{
	    		Pattern p = new Pattern("VisionSikuli/selectPartIndex-5-mc8.png");
	    		//Pattern p = new Pattern(ConfigLog.resetpointSet);
	            if (scr.exists(p) != null) {
	            	//scr.click("VisionSikuli/selectModel-afifiPC.png");
	            	scr.click("VisionSikuli/selectPartIndex-5-mc8.png");
	            	break;
	            }
    		}
    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funclickYesPartModel() throws AWTException {
    	try{
    		while(true)
    		{
	    		Pattern p = new Pattern("VisionSikuli/selectPart-mc8.png");
	    		//Pattern p = new Pattern(ConfigLog.resetpointSet);
	            if (scr.exists(p) != null) {
	            	//scr.click("VisionSikuli/selectModel-afifiPC.png");
	            	scr.click("VisionSikuli/selectPart-mc8.png");
	            	break;
	            }
    		}
    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funselectPart () throws AWTException {
    	try{
    		//Pattern p = new Pattern("VisionSikuli/demoSelectModel-afifiPC.png");
    		Pattern p = new Pattern(ConfigLog.windowmodelSet);
            if (scr.exists(p) != null) {
            	//scr.click("VisionSikuli/selectPartIndex-afifiPC.png");
            	//scr.click("VisionSikuli/selectPart-afifiPC.png");
            	//scr.click("VisionSikuli/okPart-afifiPC.png");
            	scr.click(ConfigLog.selectpartmodelSet);
            	scr.click(ConfigLog.clickokmodelSet);
            	//break;
            }

    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funRunInspect () throws AWTException {
    	try{
    		while(true)
    		{
	    		Pattern p = new Pattern("VisionSikuli/runEnable-mc8.png");
	    		//Pattern p = new Pattern(ConfigLog.windowmodelSet);
	            if (scr.exists(p) != null) {
	            	scr.click("VisionSikuli/runEnable-mc8.png");
	            	scr.click("VisionSikuli/inspect-mc8.png");
	            	break;
	            }
    		}

    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	public static void funStopRunInspect () throws AWTException {
    	try{
    		while(true)
    		{
	    		Pattern p = new Pattern("VisionSikuli/stop-mc8.png");
	    		//Pattern p = new Pattern(ConfigLog.windowmodelSet);
	            if (scr.exists(p) != null) {
	            	scr.click("VisionSikuli/stop-mc8.png");
	            	break;
	            }
    		}

    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
    }
	
	
	public static void funcheckMenu () throws AWTException {
		try{
    		//Pattern p = new Pattern("VisionSikuli/menuVision-afifiPC.png");
			Pattern p = new Pattern(ConfigLog.menuvisionSet);
            if (scr.exists(p.similar(0.99f)) != null) {
            	//scr.click("VisionSikuli/runEnable-afifiPC.png");
            	scr.click(ConfigLog.runenableSet);
            	checkRunning = true;
            }
            else
            {
            	checkRunning = false;
            }

    	}
    	catch(Exception e){
            //e.printStackTrace();
        }
	}
}
