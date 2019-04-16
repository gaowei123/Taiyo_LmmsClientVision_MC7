package com.lmms.vision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.SystemColor;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.*;
import net.sourceforge.tess4j.*;
import org.sikuli.basics.Settings;


public class LoadInspection {
	
	public static Boolean inspectAvai = false;
	public static int insTotalPass = 0;
	public static int insTotalFail = 0;
	public static boolean isCheckingResult = false;
	
	
	//dwyane 2019-0308  for logic: file reading & counting
	public class Tray{
		List<Jig> jigList = new ArrayList<>();
		
		public Boolean findJig(String jigName) {
			Boolean result = false;
			
			for(Jig jig :jigList) {
				if(jig.jigName.equals(jigName)) {
					result = true;
					break;
				}
			}
			return result;
		}
		
		public Jig getJig(String jigName) {
			Jig jig =new Jig();
			
			for(Jig temp :jigList) {
				if(temp.jigName.equals(jigName)) {
					jig = temp;
					break;
				}
			}
			
			return jig;
		}
	}
	
	public class Jig{
		
		String jigName = "";
		
		List<Button> buttonList = new ArrayList<>();
		
		
		public Boolean findButton(String btnName) {
			Boolean result = false;
			for(Button btn :buttonList) {
				if(btn.btnName.equals(btnName)) {
					result = true;
					break;
				}
			}
			return result;
		}
		
		public Button getBtn(String btnName) {
			Button btn =new Button();
			
			for(Button temp :buttonList) {
				if(temp.btnName.equals(btnName)) {
					btn = temp;  //find but not break out ,  to get lastest btn
				}
			}
			
			return btn;
		}
		
	}
	
	public class Button{
		
		String btnName = "";
		
		List<MarkInfo> markList = new ArrayList<>();
		
		public Boolean findMark(String markName) {
			Boolean result = false;
			for(MarkInfo mark :markList) {
				if(mark.markName.equals(markName)) {
					result = true;
					break;
				}
			}
			
			return result;
		}
		
	}
	
	public class MarkInfo{
		String markName = "";
		Boolean markResult  = false;
	}
	
	public class MaterialResult{
		String name = "";
		int Pass = 0;
		int Fail = 0;
	}
	//dwyane 2019-0308  for logic: file reading & counting
	
	
	
	
	
	public static void funCounting()  throws IOException, InterruptedException
	{
		//CommonFunc.writeLogFile("==Debug Counting==   In Func  funCounting()");
		
		//can't count when reflashqty
		while(LoadDB.isReflashQTY){
			Thread.sleep(50);
		}
		
		//CommonFunc.writeLogFile("==Debug Counting==   Ready to start reading & counting....");
		
		
		
		
		//1.0 Get file
	 	File countingFile = GetFile();
	 	if(countingFile== null) {
	 		//CommonFunc.writeLogFile("==Debug Counting==   1.0 Get file, No file found!");
	 		return ;
	 	}
	 	CommonFunc.writeLogFile("==Debug Counting==   1.0 Get file, file name: "+countingFile.getName());
	 	
	 	BufferedReader BufferRead = new BufferedReader(new FileReader(countingFile));
	 	//CommonFunc.writeLogFile("==Debug Counting==   1.5 Read file to buffer end");
	 	
	 	
	 	
	 	
	 	//2.0 Counting **Core Logic**
	 	LoadDB.WatchDogModel dogModel =new LoadDB().new WatchDogModel();
	 	dogModel = countingResult(BufferRead);
	 	
		BufferRead.close();//counting end, close buffer
		
		if(dogModel == null) {
			CommonFunc.writeLogFile("==Debug Counting==   2.5 Counting error, watchdog model is null");
			return ;
		}
		//CommonFunc.writeLogFile("==Debug Counting==   2.5 Counting End");
		
		
		
		
		//3.0 updated UI
		//CommonFunc.writeLogFile("==Debug Counting==   3.0 Update UI");
		funInitUI(dogModel);
		
		
		
		
		//4.0 updated DB
		CommonFunc.writeLogFile("==Debug Counting==   4.0 Update WatchDog & Log");
		funUpdateDB(dogModel);
		
	}
	
	
	
	
	
	//===== dwyane Step-01 Get counting file =====//
	public static File GetFile() {
	
		String path =  ConfigLog.readingFilePath;
		
		
		if(ConfigLog.machinenoSet.equals("6") || ConfigLog.machinenoSet.equals("8")) 
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String StrDay = df.format(new Date());
			path += StrDay +"\\";
		}else if(ConfigLog.machinenoSet.equals("7")) {
			//nothing
		}
		
		
		File LatestFile = CommonFunc.getLastModifiedFile(path);
		
		return LatestFile;
	}
	
	//===== dwyane Step-02 **Core Logic**  Counting =====//
	public static LoadDB.WatchDogModel countingResult(BufferedReader buffer){
		
		//CommonFunc.writeLogFile("==Debug Counting==   2.1 In Func countingResult  &  Start Setting Tray");
		
		LoadDB.WatchDogModel dogModel =new LoadDB().new WatchDogModel();
		
		try 
		{
			Tray tray = new LoadInspection().new Tray();
			
		
			String strLine = buffer.readLine();
			while(strLine != null) {
				
				//check format
				if(strLine.equals("")) {
					strLine = buffer.readLine();
					continue;
				}
				
				String[] arrStrLine = strLine.split(",");
				if(arrStrLine.length < 4)
				{
					CommonFunc.writeLogFile("==Debug Counting==   2.2.1 Read Line error line value:"+strLine);
					return null;
				}
				
				String labelName = arrStrLine[1];
				String[] arrLabelName = labelName.split("\\|");// '|' 分隔必须要有\\
				if(arrLabelName.length < 3)
				{
					CommonFunc.writeLogFile("==Debug Counting==   2.2.2 Label Name error value:"+labelName);
					return null;
				}
				//check format
				
				
				//get jig, button, mark info
				String jigName = arrLabelName[0];
				String btnName = arrLabelName[1];
				String strMarkResult = arrStrLine[3];
				String markName = "";
				if(arrLabelName.length == 4) {
					markName = arrLabelName[2];
				}
				
				
				MarkInfo mark = new LoadInspection().new MarkInfo();
				mark.markName = markName;
				if(strMarkResult.equals("Pass") || strMarkResult.equals("OK") || strMarkResult.equals("PASS"))//machine6,8 PASS    machine7 OK
				{
					mark.markResult = true;
				}
				
				
				// Set tray info
				if(tray.findJig(jigName)) {
					CommonFunc.writeLogFile("==Debug Counting==   2.3.1  Has Jig : "+jigName);
					Jig jig = tray.getJig(jigName);
					
					if(jig.findButton(btnName)) {
						CommonFunc.writeLogFile("==Debug Counting==   2.3.2  Has button : "+btnName);
						
						Button btn = jig.getBtn(btnName);
						btn.markList.add(mark);
						
					}else {
						CommonFunc.writeLogFile("==Debug Counting==   2.3.2  Hasn't button : "+btnName+", created new button");
						Button newBtn = new LoadInspection().new Button();
						newBtn.btnName=btnName;
						newBtn.markList.add(mark);
						
						jig.buttonList.add(newBtn);
					}
				}else{
					CommonFunc.writeLogFile("==Debug Counting==   2.3.1  Hasn't Jig : "+jigName+", created new jig & button");
					Button newBtn = new LoadInspection().new Button();
					newBtn.btnName = btnName;
					newBtn.markList.add(mark);
					
					
					Jig newJig = new LoadInspection().new Jig();
					newJig.jigName=jigName;
					newJig.buttonList.add(newBtn);
					
					tray.jigList.add(newJig);
				}
				
				strLine  = buffer.readLine();
			}
			
			
			//update jig status UI
			funUpdateJigUI(tray);
			
			
			//foreach the Tray, put the result in listMaterial
			List<MaterialResult> listMaterial = new ArrayList<>();
			
			CommonFunc.writeLogFile("==Debug Counting==   2.4  foreach the Tray, put the result in listMaterial ");
			for(Jig jig : tray.jigList) {
				
				for(Button btn : jig.buttonList) {
					
					String btnSingleName = btn.btnName.split("-")[0];
					if(existMaterial(listMaterial,btnSingleName)) {
						
						MaterialResult material = getMaterial(listMaterial,btnSingleName);
						
						Boolean btnStatus = true;
						for(MarkInfo mark :btn.markList) {
							if(mark.markResult && btnStatus) {
								btnStatus = true;
							}else{
								btnStatus = false;
							}
						}
						
						if(btnStatus) {
							material.Pass++;
						}else {
							material.Fail++;
						}
					}else {
						MaterialResult NewMaterial = new LoadInspection().new MaterialResult();
						NewMaterial.name=btn.btnName.split("-")[0];
						
						Boolean btnStatus = true;
						for(MarkInfo mark :btn.markList) {
							if(mark.markResult && btnStatus) {
								btnStatus = true;
							}else{
								btnStatus = false;
							}
						}
						
						if(btnStatus) {
							NewMaterial.Pass++;
						}else {
							NewMaterial.Fail++;
						}
						
						listMaterial.add(NewMaterial);
					}
				}
			}
			
			
			//Get material part no in watchdog model
			LoadDB ldb = new LoadDB();
			String partNumber = MainClient.lblpartNumber.getText();
			if(!partNumber.equals("")) {
				dogModel =ldb.GetMaterialPart(partNumber);
				CommonFunc.writeLogFile("==Debug Counting==   2.4 Got the Material Part ");
			}else {
				CommonFunc.writeLogFile("==Debug Counting==   2.4 Got the Material Part error, return null ");
				return null;
			}
			
			//set model 1-16 name, ok 1-16 count, ng 1-16 count
			for (int i = 0 ; i < listMaterial.size() ; i++) {
				switch (i+1){
					case 1:
						dogModel.ok1count =listMaterial.get(i).Pass;
						dogModel.ng1count =listMaterial.get(i).Fail;
						break;
					case 2:
						dogModel.ok2count =listMaterial.get(i).Pass;
						dogModel.ng2count =listMaterial.get(i).Fail;
						break;
					case 3:
						dogModel.ok3count =listMaterial.get(i).Pass;
						dogModel.ng3count =listMaterial.get(i).Fail;
						break;
					case 4:
						dogModel.ok4count =listMaterial.get(i).Pass;
						dogModel.ng4count =listMaterial.get(i).Fail;
						break;
					case 5:
						dogModel.ok5count =listMaterial.get(i).Pass;
						dogModel.ng5count =listMaterial.get(i).Fail;
						break;
					case 6:
						dogModel.ok6count =listMaterial.get(i).Pass;
						dogModel.ng6count =listMaterial.get(i).Fail;
						break;
					case 7:
						dogModel.ok7count =listMaterial.get(i).Pass;
						dogModel.ng7count =listMaterial.get(i).Fail;
						break;
					case 8:
						dogModel.ok8count =listMaterial.get(i).Pass;
						dogModel.ng8count =listMaterial.get(i).Fail;
						break;
					case 9:
						dogModel.ok9count =listMaterial.get(i).Pass;
						dogModel.ng9count =listMaterial.get(i).Fail;
						break;
					case 10:
						dogModel.ok10count =listMaterial.get(i).Pass;
						dogModel.ng10count =listMaterial.get(i).Fail;
						break;
					case 11:
						dogModel.ok11count =listMaterial.get(i).Pass;
						dogModel.ng11count =listMaterial.get(i).Fail;
						break;
					case 12:
						dogModel.ok12count =listMaterial.get(i).Pass;
						dogModel.ng12count =listMaterial.get(i).Fail;
						break;
					case 13:
						dogModel.ok13count =listMaterial.get(i).Pass;
						dogModel.ng13count =listMaterial.get(i).Fail;
						break;
					case 14:
						dogModel.ok14count =listMaterial.get(i).Pass;
						dogModel.ng14count =listMaterial.get(i).Fail;
						break;
					case 15:
						dogModel.ok15count =listMaterial.get(i).Pass;
						dogModel.ng15count =listMaterial.get(i).Fail;
						break;
					case 16:
						dogModel.ok16count =listMaterial.get(i).Pass;
						dogModel.ng16count =listMaterial.get(i).Fail;
						break;
				}
			}
			
			
		} catch (IOException e) {
			CommonFunc.writeLogFile("==Debug Counting==   2.1 Exception happend during counting :"+e.toString());
			return null;
		}
		
		
		dogModel.totalPass = dogModel.ok1count+dogModel.ok2count+dogModel.ok3count+dogModel.ok4count+dogModel.ok5count+dogModel.ok6count+dogModel.ok7count+dogModel.ok8count+dogModel.ok9count+dogModel.ok10count+dogModel.ok11count+dogModel.ok12count+dogModel.ok13count+dogModel.ok14count+dogModel.ok15count+dogModel.ok16count;
		dogModel.totalFail = dogModel.ng1count+dogModel.ng2count+dogModel.ng3count+dogModel.ng4count+dogModel.ng5count+dogModel.ng6count+dogModel.ng7count+dogModel.ng8count+dogModel.ng9count+dogModel.ng10count+dogModel.ng11count+dogModel.ng12count+dogModel.ng13count+dogModel.ng14count+dogModel.ng15count+dogModel.ng16count;
		
		
		dogModel.ok1count += LoadDB.ok1Count;
		dogModel.ok2count += LoadDB.ok2Count;
		dogModel.ok3count += LoadDB.ok3Count;
		dogModel.ok4count += LoadDB.ok4Count;
		dogModel.ok5count += LoadDB.ok5Count;
		dogModel.ok6count += LoadDB.ok6Count;
		dogModel.ok7count += LoadDB.ok7Count;
		dogModel.ok8count += LoadDB.ok8Count;
		dogModel.ok9count += LoadDB.ok9Count;
		dogModel.ok10count += LoadDB.ok10Count;
		dogModel.ok11count += LoadDB.ok11Count;
		dogModel.ok12count += LoadDB.ok12Count;
		dogModel.ok13count += LoadDB.ok13Count;
		dogModel.ok14count += LoadDB.ok14Count;
		dogModel.ok15count += LoadDB.ok15Count;
		dogModel.ok15count += LoadDB.ok16Count;
		
		dogModel.ng1count += LoadDB.ng1Count;
		dogModel.ng2count += LoadDB.ng2Count;
		dogModel.ng3count += LoadDB.ng3Count;
		dogModel.ng4count += LoadDB.ng4Count;
		dogModel.ng5count += LoadDB.ng5Count;
		dogModel.ng6count += LoadDB.ng6Count;
		dogModel.ng7count += LoadDB.ng7Count;
		dogModel.ng8count += LoadDB.ng8Count;
		dogModel.ng9count += LoadDB.ng9Count;
		dogModel.ng10count += LoadDB.ng10Count;
		dogModel.ng11count += LoadDB.ng11Count;
		dogModel.ng12count += LoadDB.ng12Count;
		dogModel.ng13count += LoadDB.ng13Count;
		dogModel.ng14count += LoadDB.ng14Count;
		dogModel.ng15count += LoadDB.ng15Count;
		dogModel.ng15count += LoadDB.ng16Count;
		
		
		
		return dogModel;
	}
	
	//===== dwyane Step-03 Updated UI =====//
	public static void funInitUI(LoadDB.WatchDogModel dogModel) {
		
		CommonFunc.writeLogFile("==Debug Counting==   3.1 PassCount: "+dogModel.totalPass+",FailCount: "+dogModel.totalFail);
		
		if (dogModel.totalPass >0 ) { 
			insTotalPass=insTotalPass + dogModel.totalPass ;
			MainClient.lblTotalPass.setText(Integer.toString(insTotalPass));
			
			MainClient.lblOK.setBackground(new Color(0, 255, 0));
        	MainClient.lblNG.setBackground(new Color(105, 105, 105));
        	
        	CommonFunc.writeLogFile("==Debug Counting==   3.1 insTotalPass: "+insTotalPass);
        }
		
		
        if (dogModel.totalFail > 0) {
        	insTotalFail = 	insTotalFail + dogModel.totalFail;
         	MainClient.lblTotalFail.setText(Integer.toString(insTotalFail));
         	
        	MainClient.lblOK.setBackground(new Color(105, 105, 105)); 
        	MainClient.lblNG.setBackground(new Color(255, 0, 0));
        	
        	CommonFunc.writeLogFile("==Debug Counting==   3.1 insTotalFail: "+insTotalFail);
        }
        
        MainClient.lblTotalOK.setText(Integer.toString(dogModel.totalPass) );
        MainClient.lblTotalNG.setText(Integer.toString(dogModel.totalFail) );
        MainClient.lblTotalPart.setText(Integer.toString(dogModel.totalFail+dogModel.totalPass) );

        
        LoadDB.todayTotalQuantity =  LoadDB.todayTotalQuantity +dogModel.totalPass +  dogModel.totalFail;
    	LoadDB.todayOKTotalQuantity = LoadDB.todayOKTotalQuantity + dogModel.totalPass;
    	LoadDB.todayNGTotalQuantity = LoadDB.todayNGTotalQuantity + dogModel.totalFail;
    	
    	
    	int total = insTotalPass + insTotalFail;
		LoadDB.currentQuantity = total;
		MainClient.lblCQuantity.setText(Integer.toString(total));
    	
		
    	MainClient.lblTotalQuantity.setText(Integer.toString(LoadDB.todayTotalQuantity));
    	
        //MainClient.lblstatInspect.setBackground(new Color(105, 105, 105));
		MainClient.lblStat.setBackground(new Color(105, 105, 105));
		MainClient.lblStat4.setBackground(new Color(105, 105, 105));
		//MainClient.lblstatPass.setBackground(new Color(105, 105, 105));
		//MainClient.lblstatFail.setBackground(new Color(105, 105, 105));
	}
	public static void funUpdateJigUI(Tray tray ) {
		
		int sn = 1;
		
    	for(Jig jig : tray.jigList) {
    		setColorForlbJig(jig,sn);
    		sn++;
    	}
    	
    	for(int i = 0; i<16- tray.jigList.size();i++) {
    		setColorForlbJig(null,sn);
    		sn++;
    	}
	}
	
	//===== dwyane Step-04 Updated DB watchdog & watchlog =====//
	public static void funUpdateDB(LoadDB.WatchDogModel dogModel ) {
    	
		LoadDB.funSendCountPassAndFailLMMS(dogModel);
	}
	
	
	
	
	
	//dwyane 2019-0311 common func
	public static Boolean existMaterial(List<MaterialResult> listMaterial,String name) {
		
		CommonFunc.writeLogFile("==Debug Counting==   2.4.1  In Func ");
		
		Boolean result = false;
		
		for( int i = 0 ; i < listMaterial.size() ; i++) {
			
			if(listMaterial.get(i).name.equals(name)) {
				result = true;
				break;
			}
		}
		
		
		return result;
	}
	
	public static MaterialResult getMaterial(List<MaterialResult> listMaterial,String name) {
		
		MaterialResult material = new LoadInspection().new MaterialResult();
		
		for( int i = 0 ; i < listMaterial.size() ; i++) {
			
			
			if(listMaterial.get(i).name.equals(name)) {
				material = listMaterial.get(i);
				break;
			}
		}
		
		
		return material;
	}
	
	public static void setColorForlbJig(Jig jig, int sn) {
		
		String jigStatus ="J"+ Integer.toString(sn) + ": ";
		
		java.awt.Label lb = new java.awt.Label();
		
		switch (sn) {
		case 1:
			lb = MainClient.lblJStat1;
			break;
		case 2:
			lb = MainClient.lblJStat2;
			break;
		case 3:
			lb = MainClient.lblJStat3;
			break;
		case 4:
			lb = MainClient.lblJStat4;
			break;
		case 5:
			lb = MainClient.lblJStat5;
			break;
		case 6:
			lb = MainClient.lblJStat6;
			break;
		case 7:
			lb = MainClient.lblJStat7;
			break;
		case 8:
			lb = MainClient.lblJStat8;
			break;
		case 9:
			lb = MainClient.lblJStat9;
			break;
		case 10:
			lb = MainClient.lblJStat10;
			break;
		case 11:
			lb = MainClient.lblJStat11;
			break;
		case 12:
			lb = MainClient.lblJStat12;
			break;
		case 13:
			lb = MainClient.lblJStat13;
			break;
		case 14:
			lb = MainClient.lblJStat14;
			break;
		case 15:
			lb = MainClient.lblJStat15;
			break;
		case 16:
			lb = MainClient.lblJStat16;
			break;
		}
		
		if(jig == null) {
			lb.setBackground(SystemColor.controlDkShadow);
			jigStatus+= "off";
			lb.setText(jigStatus);
		}else {
			
			Boolean jigRes = true;
			for(Button btn : jig.buttonList) {
				
				if(jigRes) {
					
					for(MarkInfo mark : btn.markList) {
						
						if(!mark.markResult) {
							jigRes = false;
							break;
						}
					}
				}else {
					break;
				}
			}
			
			if(jigRes) {
				lb.setBackground(SystemColor.green);
				jigStatus+= "ok";
				lb.setText(jigStatus);
			}else{
				lb.setBackground(SystemColor.red);
				jigStatus+= "ng";
				lb.setText(jigStatus);
			}
		}
	}
	//dwyane 2019-0311 common func
	
	
	
	public static void funGetCurrentQuantity()
	{
		int total = insTotalPass + insTotalFail;
		LoadDB.currentQuantity = total;
		MainClient.lblCQuantity.setText(Integer.toString(total));
	}
	
}
