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
		CommonFunc.writeLogFile("==Debug Counting==   In Func  funCounting()");
		
		//can't count when reflashqty
		while(LoadDB.isReflashQTY){
			Thread.sleep(50);
		}
		
		CommonFunc.writeLogFile("==Debug Counting==   Ready to start reading & counting....");
		
		
		
		
		//1.0 Get file 
	 	File countingFile = GetFile();
	 	if(countingFile== null) {
	 		//CommonFunc.writeLogFile("==Debug Counting==   1.0 Get file, No file found!");
	 		return ;
	 	}
	 	CommonFunc.writeLogFile("==Debug Counting==   1.0 Get file, file name: "+countingFile.getName());
	 	
	 	BufferedReader BufferRead = new BufferedReader(new FileReader(countingFile));
	 	CommonFunc.writeLogFile("==Debug Counting==   1.5 Read file to buffer end");
	 	
	 	
	 	
	 	
	 	//2.0 Start to counting 
	 	CommonFunc.writeLogFile("==Debug Counting==   2.0 Start to counting ");
	 	LoadDB.WatchDogModel dogModel =new LoadDB().new WatchDogModel();
	 	dogModel = countingResult(BufferRead);
	 	
		BufferRead.close();//counting end, close buffer
		
		if(dogModel == null) {
			CommonFunc.writeLogFile("==Debug Counting==   2.5 Watchdog Model is null");
			return ;
		}
		CommonFunc.writeLogFile("==Debug Counting==   2.5 Counting End");
		
		
		
		
		//3.0 updated UI
		CommonFunc.writeLogFile("==Debug Counting==   3.0 Update UI");
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
	
	//===== dwyane Step-02 Counting =====//
	public static LoadDB.WatchDogModel countingResult(BufferedReader buffer){
		
		CommonFunc.writeLogFile("==Debug Counting==   2.1 In Func countingResult  &  Start Setting Tray");
		
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
				String[] arrLabelName = labelName.split("-");
				if(arrLabelName.length < 3)
				{
					CommonFunc.writeLogFile("==Debug Counting==   2.2.2 Label Name error value:"+labelName);
					return null;
				}
				//check format
				
				
				//get jig, button, mark  name   & mark result
				String jigName = arrLabelName[0];
				String btnName = arrLabelName[1];
				String markName = arrLabelName[2];
				String strMarkResult =arrStrLine[3];
				
				
				//current mark info
				MarkInfo mark = new LoadInspection().new MarkInfo();
				mark.markName = markName;
				if(strMarkResult.equals("Pass") || strMarkResult.equals("OK") || strMarkResult.equals("PASS"))//machine6,8 PASS    machine7 OK
				{
					mark.markResult = true;
				}
				//current mark info 
				
						
				if(tray.findJig(jigName)) {
					CommonFunc.writeLogFile("==Debug Counting==   2.3.1  Has Jig : "+jigName);
					Jig jig = tray.getJig(jigName);
					
					if(jig.findButton(btnName)) {
						CommonFunc.writeLogFile("==Debug Counting==   2.3.2  Has button : "+btnName);
						Button btn = jig.getBtn(btnName);
						
						if(btn.findMark(markName)) {
							CommonFunc.writeLogFile("==Debug Counting==   2.3.3  Has mark : "+markName+", created new button");
							Button newBtn = new LoadInspection().new Button();
							newBtn.btnName = btnName;
							newBtn.markList.add(mark);
							
							jig.buttonList.add(newBtn);
						}else {
							CommonFunc.writeLogFile("==Debug Counting==   2.3.3  Hasn't mark : "+markName+", add it");
							btn.markList.add(mark);
						}
						
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
			
			
			//foreach the Tray, put the result in listMaterial
			List<MaterialResult> listMaterial = new ArrayList<>();
			
			for(Jig jig : tray.jigList) {
				
				for(Button btn : jig.buttonList) {
					
					if(existMaterial(listMaterial,btn.btnName)) {
						
						MaterialResult material = getMaterial(listMaterial,btn.btnName);
						
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
						MaterialResult material = new LoadInspection().new MaterialResult();
						material.name=btn.btnName;
						
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
						
						listMaterial.add(material);
					}
				}
			}
			
			
			
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
		
		
		setColorForlbJig(MainClient.lblJStat1,dogModel.ok1count,dogModel.ng1count,1);
		setColorForlbJig(MainClient.lblJStat2,dogModel.ok2count,dogModel.ng2count,2);
		setColorForlbJig(MainClient.lblJStat3,dogModel.ok3count,dogModel.ng3count,3);
		setColorForlbJig(MainClient.lblJStat4,dogModel.ok4count,dogModel.ng4count,4);
		setColorForlbJig(MainClient.lblJStat5,dogModel.ok5count,dogModel.ng5count,5);
		setColorForlbJig(MainClient.lblJStat6,dogModel.ok6count,dogModel.ng6count,6);
		setColorForlbJig(MainClient.lblJStat7,dogModel.ok7count,dogModel.ng7count,7);
		setColorForlbJig(MainClient.lblJStat8,dogModel.ok8count,dogModel.ng8count,8);
		setColorForlbJig(MainClient.lblJStat9,dogModel.ok9count,dogModel.ng9count,9);
		setColorForlbJig(MainClient.lblJStat10,dogModel.ok10count,dogModel.ng10count,10);
		setColorForlbJig(MainClient.lblJStat11,dogModel.ok11count,dogModel.ng11count,11);
		setColorForlbJig(MainClient.lblJStat12,dogModel.ok12count,dogModel.ng12count,12);
		setColorForlbJig(MainClient.lblJStat13,dogModel.ok13count,dogModel.ng13count,13);
		setColorForlbJig(MainClient.lblJStat14,dogModel.ok14count,dogModel.ng14count,14);
		setColorForlbJig(MainClient.lblJStat15,dogModel.ok15count,dogModel.ng15count,15);
		setColorForlbJig(MainClient.lblJStat16,dogModel.ok16count,dogModel.ng16count,16);
		
	}
	
	//===== dwyane Step-04 Updated DB watchdog & watchlog =====//
	public static void funUpdateDB(LoadDB.WatchDogModel dogModel ) {
    	
		LoadDB.funSendCountPassAndFailLMMS(dogModel);
	}
	
	
	
	
	
	//dwyane 2019-0311 common func
	public static Boolean existMaterial(List<MaterialResult> listMaterial,String name) {
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
	
	public static void setColorForlbJig(java.awt.Label lb , int Pass,int Fail, int jigNo) {
		
		String jigStatus ="J"+ Integer.toString(jigNo) + ": ";
		
		if(Fail > 0) {
			lb.setBackground(SystemColor.red);
			jigStatus+= "ng";
			lb.setText(jigStatus);
		}else if(Pass+Fail == 0) {
			lb.setBackground(SystemColor.controlDkShadow);
			jigStatus+= "off";
			lb.setText(jigStatus);
		}else {
			lb.setBackground(SystemColor.green);
			jigStatus+= "ok";
			lb.setText(jigStatus);
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
