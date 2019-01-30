package com.lmms.vision;
import java.awt.Color;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.lmms.vision.ConfigLog;

public class LoadDB {
	public static String currentPartNumber;
	public static String currentJobNumber;
	public static String currentDescription;
	public static String currentOpsDateTime;
	public static int newQuantity;
	public static int currentQuantity;
	public static int totalQuantity;
	public static int todayTotalQuantity;
	public static int todayTotalQuantityFirstStart; //2018 06 18
	public static int todayOKTotalQuantity;
	public static int todayNGTotalQuantity;
	public static String currentOperation = "";
	public static String currentPrepDateOut;
	public static String currentPrepDateIn;
	public static String currentPaintDateOut;
	public static String currentPaintDateIn;
	public static String currentLaserDateOut;
	public static String currentLaserDateIn;
	public static String currentProductDateOut;
	public static String currentProductDateIn;
	public static String currenOwnerID;
	public static String currentLaserFileA;
	public static String currentLaserFileB;
	public static String currentTotalPass;
	public static String currentTotalFail;
	public static String currentLastUpdated;
	public static String rmsStatus;
	public static String mainResult = "FAIL";
	public static Boolean[] eventCheck = new Boolean[5];
	public static String productionID = "0";
	public static String eventID = "0";
	public static String eventTrigger = "POWER ON";
	public static String[] ioName = new String[12];
	public static String[] ioVal = new String[12];
	public static Boolean[] ioValBool = new Boolean[12];
	public static String adamOnline = "false";
	public static Boolean runQty = false;
	public static Boolean runTechnician = false;
	public static Boolean runFirstTime = true;
	
	public static String connectionUrl = ConfigLog.connectionstrSet ;//"jdbc:sqlserver://192.168.137.1:1433;instanceName=SQLEXPRESS;databaseName=LMMS_TAIYO;user=sa;password=sa0";
	//public static String connectionUrl = "jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESS;databaseName=LMMS_TAIYO;user=sa;password=sa0";

	public static void main(String[] args) {  
		//funReadDBParts();
	}
	
	public static boolean startJobFlag = true;    //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.  
	public static boolean isReflashQTY = false;   //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.    
    public static void funReadDBParts()
	{
	      Connection con = null;  
	      Statement stmt = null;  
	      ResultSet rs = null;  

	      try {  
	         // Establish the connection.  
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	         con = DriverManager.getConnection(connectionUrl);  

	         // Create and execute an SQL statement that returns some data.  
	         String SQL = "SELECT * FROM LMMSWatchDog WHERE machineID='" + ConfigLog.machinenoSet + "'";  
	         stmt = con.createStatement();  
	         //stmt = con.createStatement();  
	         rs = stmt.executeQuery(SQL);  
	         //stmt.executeUpdate(SQL); 
	         // Iterate through the data in the result set and display it.  
	         while (rs.next()) {  
	           //System.out.println(rs.getString(1) + " " + rs.getString(2));  
	           currentOpsDateTime = rs.getString("dateTime");
	           currentPartNumber = rs.getString("partNumber");
	           currentJobNumber = rs.getString("jobNumber");
	           currentDescription = rs.getString("description");
	           newQuantity = Integer.parseInt(rs.getString("currentQuantity"));
	           totalQuantity = Integer.parseInt(rs.getString("totalQuantity"));
	           currentOperation = rs.getString("currentOperation");
	           currentPrepDateOut = rs.getString("prepDateOut");
	           currentPrepDateIn = rs.getString("prepDateIn");
	           currentLaserDateOut = rs.getString("laserDateOut");
	           currentLaserDateIn = rs.getString("laserDateIn");
	           currentProductDateOut = rs.getString("productDateOut");
	           currentProductDateIn = rs.getString("productDateIn");
	           currenOwnerID = rs.getString("ownerID");
	           currentTotalPass = rs.getString("totalPass");
	           currentTotalFail = rs.getString("totalFail");
	           currentLastUpdated = rs.getString("lastUpdated");
	           rmsStatus = rs.getString("rmsStatus");
	           todayTotalQuantityFirstStart =Integer.parseInt(rs.getString("todayTotalQuantity")) ; //only machine 7 no need to +1
	           startJobFlag = rs.getString("goodOK").equals("true")?true:false;  //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   
		        System.out.println(" LOAD DB -- 1.1: DB DATA: TOTAL QTY = "+String.valueOf(todayTotalQuantityFirstStart) + "][rs.getString(goodOK="+rs.getString("goodOK")+"][startJobFlag="+String.valueOf( startJobFlag)+"]");
	         }  
	         //System.out.println(currentPartNumber);  
	         //2018 06 18 by wei lijia move below logic from if(runFirstTime), for the partial job. 
	         //2018 06 19 by wei lijia add ischeckresult interlok
	         System.out.println(" LOAD DB -- 1.1.2 LoadInspection.isCheckingResult = " + String.valueOf(LoadInspection.isCheckingResult));
	        if(LoadInspection.isCheckingResult  )
	        {
	        	return;
	        }
	        //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   
			if( startJobFlag || runFirstTime )
	        {
				isReflashQTY = true;
		         LoadInspection.insTotalPass = Integer.parseInt(currentTotalPass);
		         LoadInspection.insTotalFail = Integer.parseInt(currentTotalFail);
		         MainClient.lblTotalPass.setText(currentTotalPass);
		         MainClient.lblTotalFail.setText(currentTotalFail);
		         MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
		         LoadInspection.funGetCurrentQuantity();
	             todayTotalQuantity = todayTotalQuantityFirstStart;
	             System.out.println(" LOAD DB -- 1.2: isCheckingResult=FALSE; TOTAL QTY = "+ String.valueOf(todayTotalQuantity)); 
	             isReflashQTY= false;
	        }
	        
	         //System.out.println(currentPartNumber);  
	         MainClient.lblpartNumber.setText(currentPartNumber);
	         MainClient.lbljobNumber.setText(currentJobNumber);
	         MainClient.lblProcess.setText(currentOperation);
	         MainClient.lblQuantity.setText(Integer.toString(totalQuantity));
	         
	         MainClient.lblRMS.setText(rmsStatus);
	         
	         if(LoadDB.rmsStatus.equals("adjust"))
	         {
				MainClient.lblStat2.setText("ADJ ON");
				MainClient.lblStat2.setBackground(new Color(0, 0, 255));
				
	         }
	         else
	         {
	        	 MainClient.lblStat2.setText("ADJ OFF");
				 MainClient.lblStat2.setBackground(new Color(105, 105, 105));
	         }
	         
	         if(LoadDB.rmsStatus.equals("technician"))
	         {
	        	 if(!runTechnician)
	        	 {
		        	 funReadLastEvent();
		        	 MainClient.selectTechnician();
		        	 MainClient.lblCompleteStatus.setVisible(true);        	 
	        	 }
	        	 else
	        	 {
	        		 //MainClient.lblCompleteStatus.setVisible(false);     
	        	 }
	         }
	         else
	         {
	        	 runTechnician = false;
	        	 MainClient.lblCompleteStatus.setVisible(false);
	        	 MainClient.lblCompleteStatus.setText("JOB COMPLETE!");
	         }
			
	         
	         if(runFirstTime)
	         {
	        	 currentQuantity = newQuantity;
		         //LoadInspection.insTotalPass = Integer.parseInt(currentTotalPass);
		         //LoadInspection.insTotalFail = Integer.parseInt(currentTotalFail);
		         //MainClient.lblTotalPass.setText(currentTotalPass);
		         //MainClient.lblTotalFail.setText(currentTotalFail);
		         //MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
		         //LoadInspection.funGetCurrentQuantity();
		         funGraphicNameInit();
		         runFirstTime = false;
		         if(!runTechnician)
		         {
		        	 //MainClient.lblCompleteStatus.setVisible(false);
		         }
		         //todayTotalQuantity = currentQuantity; 
		         //MainClient.lblTotalQuantity.setText(Integer.toString(todayTotalQuantity));
	         }
	        
	         
	         MainClient.lblTotalQuantity.setText(Integer.toString(todayTotalQuantity));
	         
	         if(currentJobNumber.isEmpty())
	         {
	        	 runQty = false;
	         }
	         
	         if(totalQuantity == 0)
	         {
	         	 if(!runQty)
	         	 {
	         		 if(!runTechnician)
	         		 {
	         			 //MainClient.lblCompleteStatus.setVisible(false);
	         		 }
	         		 
	         		 //funStartQuantityChecker();
	         		
	         	 }
	         	currentQuantity = 0;
	         	LoadInspection.insTotalPass = 0;
	         	LoadInspection.insTotalFail = 0;
	         	MainClient.lblTotalPass.setText(currentTotalPass);
		         MainClient.lblTotalFail.setText(currentTotalFail);
		         MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
	         }
	      }  

	      // Handle any errors that may have occurred.  
	      catch (Exception e) {  
	         e.printStackTrace();  
	         MainClient.errorInfo.append("\nfunReadDBParts: " + e.getMessage());
	      }  
	      finally {  
	         if (rs != null) try { rs.close(); } catch(Exception e) {}  
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	         if (con != null) try { con.close(); } catch(Exception e) {}  
	      }  
	}
	
	public static void funReadLastWatchLog()
	{
	      Connection con = null;  
	      Statement stmt = null;  
	      ResultSet rs = null;  

	      try {  
	         // Establish the connection.  
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	         con = DriverManager.getConnection(connectionUrl);  

	         // Create and execute an SQL statement that returns some data.  
	         String SQL = "SELECT TOP 1 id FROM LMMSWatchLog WHERE machineID='" + ConfigLog.machinenoSet + "' ORDER BY dateTime DESC";  
	         stmt = con.createStatement();  
	         rs = stmt.executeQuery(SQL);  
	         while (rs.next()) {  
	        	 productionID = rs.getString("id");
	         }  
	         
	      }  

	      // Handle any errors that may have occurred.  
	      catch (Exception e) {  
	         e.printStackTrace();  
	      }  
	      finally {  
	         if (rs != null) try { rs.close(); } catch(Exception e) {}  
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	         if (con != null) try { con.close(); } catch(Exception e) {}  
	      }  
	}
	
	public static void funReadLastEvent()
	{
	      Connection con = null;  
	      Statement stmt = null;  
	      ResultSet rs = null;  

	      try {  
	         // Establish the connection.  
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	         con = DriverManager.getConnection(connectionUrl);  

	         // Create and execute an SQL statement that returns some data.  
	         // 2018 02 09 by wei lijia, CHANGE TO insert function.
	         //String SQL = "SELECT TOP 1 id FROM LMMSEventLog WHERE machineID='" + ConfigLog.machinenoSet + "' ORDER BY dateTime DESC";  
	         String SQL = "SELECT TOP 1 id FROM LMMSEventLog WHERE machineID='" + ConfigLog.machinenoSet + "' AND currentOperation = 'TECHNICIAN_OEE'  ORDER BY id DESC";  
	        
	         stmt = con.createStatement();  
	         rs = stmt.executeQuery(SQL);  
	         while (rs.next()) {  
	        	 eventID = rs.getString("id");
	         }  
	         
	      }  

	      // Handle any errors that may have occurred.  
	      catch (Exception e) {  
	         e.printStackTrace();  
	      }  
	      finally {  
	         if (rs != null) try { rs.close(); } catch(Exception e) {}  
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	         if (con != null) try { con.close(); } catch(Exception e) {}  
	      }  
	}
	
	public static void funUpdateLMMSEventTechnicianStart()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			// 2018 02 09 by wei lijia, CHANGE TO insert function.
				// String SQL = "UPDATE [LMMSEventLog] SET [eventTrigger]='" + eventTrigger + "', [startTime]=GETDATE(), [stopTime]=GETDATE() WHERE machineID='" + ConfigLog.machinenoSet + "' AND id=" + eventID;
				String SQL = " insert into [LMMSEventLog]  "
			          + " ([dateTime],[machineID],[currentOperation],[eventTrigger],[startTime],[stopTime],[ipSetting])  VALUES (" 
					  + "  convert(date, getdate()) ,'" + ConfigLog.machinenoSet  + "','TECHNICIAN_OEE','"+eventTrigger+"',GETDATE(),GETDATE(),'NA')";
				
		 	 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
			 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		
	}
	
	public static void funUpdateLMMSEventTechnicianStop()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			 String SQL = "UPDATE [LMMSEventLog] SET [dateTime]=GETDATE(), [eventTrigger]='" + eventTrigger + "', [stopTime]=GETDATE() WHERE machineID='" + ConfigLog.machinenoSet + "' AND id=" + eventID;
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 runTechnician = false;
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		  funUpdateLMMSStandby();
	}
	
	public static void funUpdateLMMSStandby()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			 String SQL = "UPDATE [LMMSWatchDog] SET [rmsStatus]='runinspect' WHERE machineID='" + ConfigLog.machinenoSet + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		
	}
	
	public static void funGraphicNameInit()
	{
		LoadInspection.graName[0] = "L Track X";
		LoadInspection.graName[1] = "L Track Y";
		LoadInspection.graName[2] = "L Seek X";
		LoadInspection.graName[3] = "L Seek Y";
		LoadInspection.graName[4] = "L Phone X";
		LoadInspection.graName[5] = "L Phone Y";
		LoadInspection.graName[6] = "R App X";
		LoadInspection.graName[7] = "R App Y";
		LoadInspection.graName[8] = "R Home X";
		LoadInspection.graName[9] = "R Home Y";
		LoadInspection.graName[10] = "R Audio X";
		LoadInspection.graName[11] = "R Audio Y";
		LoadInspection.graName[12] = "L Win X";
		LoadInspection.graName[13] = "L Win Y";
		LoadInspection.graName[14] = "R Win X";
		LoadInspection.graName[15] = "R Win Y";
	}
	
	public static void funStartQuantityChecker()
	{
		int result = JOptionPane.showConfirmDialog(null, MainClient.myPanel2,
	            "Manual Quantity:" + LoadDB.currentJobNumber, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
        	if(!MainClient.quantityField.getText().isEmpty())
        	{
        		totalQuantity = Integer.parseInt(MainClient.quantityField.getText());
        		currentQuantity = 0;
        		LoadInspection.insTotalPass = 0;
		        LoadInspection.insTotalFail = 0;
		        MainClient.lblTotalPass.setText(currentTotalPass);
		        MainClient.lblTotalFail.setText(currentTotalFail);
		        MainClient.lblQuantity.setText(Integer.toString(totalQuantity));
				MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
				funUpdateLMMSQuantity();
		        runQty = true;
        	}
        	else
        	{
        		runQty = false;
        	}
        	
        	//System.out.println(currentQuantity);
//        	funUpdateLMMSQuantity();
        	//runQty = false;
        	//runTechnician = false;
        }
        else
        {
        	runQty = false;
        }
	}
	
	public static void funReadDBMachineIO()
	{
	      Connection con = null;  
	      Statement stmt = null;  
	      ResultSet rs = null;  

	      try {  
	         // Establish the connection.  
	         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	         con = DriverManager.getConnection(connectionUrl);  

	         // Create and execute an SQL statement that returns some data.  
	         String SQL = "SELECT * FROM LMMSMachineIO WHERE machineID='" + ConfigLog.machinenoSet + "'";  
	         stmt = con.createStatement();  
	         //stmt = con.createStatement();  
	         rs = stmt.executeQuery(SQL);  
	         //stmt.executeUpdate(SQL); 
	         // Iterate through the data in the result set and display it.  
	         
	         while (rs.next()) {  
	        	 for(int k=5;k<15;k++)
	        	 {
	        		 ioName[k-5] = rs.getString(k);
	        	 }
	        	 for(int k=21;k<31;k++)
	        	 {
	        		 ioVal[k-21] = rs.getString(k);
	        		 ioValBool[k-21] =  Boolean.parseBoolean(rs.getString(k));
	        	 }
	        	 adamOnline = rs.getString(25);
	         } 
	         
	         MainClient.lblSig1.setBackground(funColorSelect(ioVal[0]));
	         MainClient.lblSig2.setBackground(funColorSelect(ioVal[1]));
	         MainClient.lblSig3.setBackground(funColorSelect(ioVal[2]));
	         MainClient.lblSig4.setBackground(funColorSelect(ioVal[3]));
	         MainClient.lblSig5.setBackground(funColorSelect(ioVal[4]));
	         MainClient.lblSig6.setBackground(funColorSelect(ioVal[5]));
	         MainClient.lblSig7.setBackground(funColorSelect(ioVal[6]));
	         MainClient.lblSig8.setBackground(funColorRed(ioVal[7]));
	         MainClient.lblSig9.setBackground(funColorGreen(ioVal[8]));
	         MainClient.lblSig10.setBackground(funColorYellow(ioVal[9]));
	         MainClient.lblAdam.setBackground(funColorSelect(adamOnline));
	         
	         SimpleDateFormat df = new  SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	        System.out.println(df.format(new Date()) + "  0.                 "+ String.valueOf(LoadDB.ioValBool[6])); 
	 		if(LoadDB.ioValBool[6])
			{
	 			RunInspection.inspectReady = true;
				MainClient.lblStat4.setBackground(new Color(0, 255, 0));
			}
	 		else
	 		{
	 			RunInspection.inspectReadyBuf = false; //2018 01 20 by Wei LiJia
				RunInspection.inspectReady = false;
				MainClient.lblStat4.setBackground(new Color(105, 105, 105));
	 		}
	         
	         if(ioValBool[0])
	         {
	        	 //funUpdateLMMSStartDate();
	         }
	         
	         if(ioValBool[3])
	         {
	        	 //funUpdateLMMSStopDate();
	         }
	         //System.out.println(Arrays.toString(ioName));
	         //System.out.println(Arrays.toString(ioVal));
	      }  

	      // Handle any errors that may have occurred.  
	      catch (Exception e) {  
	         e.printStackTrace();  
	      }  
	      finally {  
	         if (rs != null) try { rs.close(); } catch(Exception e) {}  
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	         if (con != null) try { con.close(); } catch(Exception e) {}  
	      }  
	}
	
	public static void funUpdateLMMSQuantity()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			
			 String SQL = "UPDATE [LMMSWatchDog] SET [dateTime]=GETDATE(), [totalQuantity]=" + totalQuantity + ", [currentQuantity]=" + currentQuantity + ",[todayTotalQuantity]=" + todayTotalQuantity + " WHERE machineID='" + ConfigLog.machinenoSet + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		  
		  funUpdateLMMSQuantityLog();
		
	}
	public static void funUpdateLMMSQuantityLog()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "',[dateTime]=GETDATE(), [totalQuantity]=" + totalQuantity + ", [currentQuantity]=" + currentQuantity + " WHERE jobNumber='" + currentJobNumber + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
			 MainClient.lblQuantity.setText(Integer.toString(totalQuantity));
			 MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		     MainClient.errorInfo.append("\nfunUpdateLMMSQuantity: " + e.getMessage());
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		
	}
	
	public static void funUpdateLMMSStartDate()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			
			 String SQL = "UPDATE [LMMSWatchDog] SET [dateTime]=GETDATE(), [productDateIn]=GETDATE() WHERE machineID='" + ConfigLog.machinenoSet + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		     MainClient.errorInfo.append("\nfunUpdateLMMSStartDate: " + e.getMessage());
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		  funUpdateLMMSStartDateLog();
	}
	
	public static void funUpdateLMMSStartDateLog()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "', [dateTime]=GETDATE(), [productDateIn]=GETDATE() WHERE [jobNumber]='" + currentJobNumber + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		     MainClient.errorInfo.append("\nfunUpdateLMMSStartDate: " + e.getMessage());
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		
	}
	
	public static void funUpdateLMMSStopDate()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			
			 String SQL = "UPDATE [LMMSWatchDog] SET [dateTime]=GETDATE(), [stopTime]=GETDATE() WHERE machineID='" + ConfigLog.machinenoSet + "'";
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
			 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		  funUpdateLMMSStopDateLog();
	}
	
	public static void funUpdateLMMSStopDateLog()
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			
			 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "', [dateTime]=GETDATE(), [stopTime]=GETDATE() WHERE  [jobNumber]='" + currentJobNumber + "'" ;
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL); 
		  }  
		
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
		
	}
	
	public static Color funColorSelect(String valRes)
	{
		
		if(Boolean.valueOf(valRes))
		{
			return new Color(0, 0, 255);
		}
		else
		{
			return new Color(105, 105, 105);
		}
		
	}
	
	public static Color funColorRed(String valRes)
	{
		
		if(Boolean.valueOf(valRes))
		{
			return new Color(255, 0, 0);
		}
		else
		{
			return new Color(105, 105, 105);
		}
		
	}
	
	public static Color funColorGreen(String valRes)
	{
		
		if(Boolean.valueOf(valRes))
		{
			return new Color(0, 128, 0);
		}
		else
		{
			return new Color(105, 105, 105);
		}
		
	}
	
	public static Color funColorYellow(String valRes)
	{
		
		if(Boolean.valueOf(valRes))
		{
			return new Color(196, 196, 0);
		}
		else
		{
			return new Color(105, 105, 105);
		}
		
	}
	
	public static void funLogResultLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  
      String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
      

	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "INSERT INTO [dbo].[LMMSClientVisionLog] ([dateTime],[machineID],[partNumber],[jobNumber]" +
           ",[modeStatus],[currentStatus],[totalGraphic],[totalQuantity],[currentQuantity],[mainResult]" + 
           ",[graXYname1],[graXYname2],[graXYname3],[graXYname4],[graXYname5],[graXYname6],[graXYname7],[graXYname8]" + 
           ",[graXYname9],[graXYname10],[graXYname11],[graXYname12],[graXYname13],[graXYname14],[graXYname15],[graXYname16]" +	 
           ",[graXYval1],[graXYval2],[graXYval3],[graXYval4],[graXYval5],[graXYval6],[graXYval7],[graXYval8]" + 
           ",[graXYval9],[graXYval10],[graXYval11],[graXYval12],[graXYval13],[graXYval14],[graXYval15],[graXYval16]" +
           ",[graXYres1],[graXYres2],[graXYres3],[graXYres4],[graXYres5],[graXYres6],[graXYres7],[graXYres8]" +
           ",[graXYres9],[graXYres10],[graXYres11],[graXYres12],[graXYres13],[graXYres14],[graXYres15],[graXYres16]" +
           ",[vSystemOk],[runningOk],[inspectOk],[passOk],[failOk])" +
           "VALUES (" + 
           "GETDATE()" + 
           ",'" + ConfigLog.machinenoSet + "'" +
           ",'" + currentPartNumber  + "'" +
           ",'" + currentJobNumber   + "'" +
           ",'result'" +
           ",'vision'" +
           "," + "'16'"  +
           "," + totalQuantity  +
           "," + currentQuantity  +
           ",'" + mainResult  + "'" +
           ",'" + LoadInspection.graName[0].toString() + "'" +
           ",'" + LoadInspection.graName[1].toString() + "'" +
           ",'" + LoadInspection.graName[2].toString() + "'" +
           ",'" + LoadInspection.graName[3].toString() + "'" +
           ",'" + LoadInspection.graName[4].toString() + "'" +
           ",'" + LoadInspection.graName[5].toString() + "'" +
           ",'" + LoadInspection.graName[6].toString() + "'" +
           ",'" + LoadInspection.graName[7].toString() + "'" +
           ",'" + LoadInspection.graName[8].toString() + "'" +
           ",'" + LoadInspection.graName[9].toString() + "'" +
           ",'" + LoadInspection.graName[10].toString() + "'" +
           ",'" + LoadInspection.graName[11].toString() + "'" +
           ",'" + LoadInspection.graName[12].toString() + "'" +
           ",'" + LoadInspection.graName[13].toString() + "'" +
           ",'" + LoadInspection.graName[14].toString() + "'" +
           ",'" + LoadInspection.graName[15].toString() + "'" +
           ",'" + LoadInspection.graPos[0].toString() + "'" +
           ",'" + LoadInspection.graPos[1].toString() + "'" +
           ",'" + LoadInspection.graPos[2].toString() + "'" +
           ",'" + LoadInspection.graPos[3].toString() + "'" +
           ",'" + LoadInspection.graPos[4].toString() + "'" +
           ",'" + LoadInspection.graPos[5].toString() + "'" +
           ",'" + LoadInspection.graPos[6].toString() + "'" +
           ",'" + LoadInspection.graPos[7].toString() + "'" +
           ",'" + LoadInspection.graPos[8].toString() + "'" +
           ",'" + LoadInspection.graPos[9].toString() + "'" +
           ",'" + LoadInspection.graPos[10].toString() + "'" +
           ",'" + LoadInspection.graPos[11].toString() + "'" +
           ",'" + LoadInspection.graPos[12].toString() + "'" +
           ",'" + LoadInspection.graPos[13].toString() + "'" +
           ",'" + LoadInspection.graPos[14].toString() + "'" +
           ",'" + LoadInspection.graPos[15].toString() + "'" +
           ",'" + LoadInspection.graResult[0].toString() + "'" +
           ",'" + LoadInspection.graResult[1].toString() + "'" +
           ",'" + LoadInspection.graResult[2].toString() + "'" +
           ",'" + LoadInspection.graResult[3].toString() + "'" +
           ",'" + LoadInspection.graResult[4].toString() + "'" +
           ",'" + LoadInspection.graResult[5].toString() + "'" +
           ",'" + LoadInspection.graResult[6].toString() + "'" +
           ",'" + LoadInspection.graResult[7].toString() + "'" +
           ",'" + LoadInspection.graResult[8].toString() + "'" +
           ",'" + LoadInspection.graResult[9].toString() + "'" +
           ",'" + LoadInspection.graResult[10].toString() + "'" +
           ",'" + LoadInspection.graResult[11].toString() + "'" +
           ",'" + LoadInspection.graResult[12].toString() + "'" +
           ",'" + LoadInspection.graResult[13].toString() + "'" +
           ",'" + LoadInspection.graResult[14].toString() + "'" +
           ",'" + LoadInspection.graResult[15].toString() + "'" +
           ",'" + LoadVision.checkVision.toString() + "'" +
           ",'" + LoadVision.checkVision.toString() + "'" +
           ",'" + LoadInspection.inspectAvai.toString() + "'" +
           ",'" + LoadInspection.passStatus + "'" +
           ",'" + LoadInspection.failStatus + "')";
		 //System.out.println(SQL);
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace(); 
	     MainClient.errorInfo.append("\nfunLogResultLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendResultLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSClientVision SET [dateTime]=GETDATE(), passValue='" + LoadInspection.passStatus.toString() + "', failValue='" + LoadInspection.failStatus.toString() + "' WHERE jobNumber='" + currentJobNumber + "' AND partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendResultLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendCountPassLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.  
		 String SQL = "UPDATE [LMMSWatchDog] SET [goodOK] ='false' , [dateTime]='" + dateNow.toString() + "',[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ", [totalPass]=" + LoadInspection.insTotalPass + ",[todayTotalQuantity]=" + todayTotalQuantity + ",[todayOKTotalQuantity]=" + todayOKTotalQuantity + ",[todayNGTotalQuantity]=" + todayNGTotalQuantity + " WHERE jobNumber='" + currentJobNumber + "' AND partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountPassLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	  funSendCountPassLMMSLog();
	}
	
	//2018 06 28 added by wei lijia for Machine 6+7
	public static void funSendCountPassAndFailLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.  
		 String SQL = "UPDATE [LMMSWatchDog] SET [goodOK] ='false' , [dateTime]=getdate(),[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ", [totalPass]=" + LoadInspection.insTotalPass + ", [totalFail]=" + LoadInspection.insTotalFail + ",[todayTotalQuantity]=" + todayTotalQuantity + ",[todayOKTotalQuantity]=" + todayOKTotalQuantity + ",[todayNGTotalQuantity]=" + todayNGTotalQuantity + " WHERE jobNumber='" + currentJobNumber + "' AND partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountPassAndFailLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	  funSendCountPassAndFailLMMSLog();
	}
	//2018 06 28 added by wei lijia for Machine 6+7
	public static void funSendCountPassAndFailLMMSLog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "', [dateTime]=GETDATE(),[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ",  [totalPass]=" + LoadInspection.insTotalPass +  ", [totalFail]=" + LoadInspection.insTotalFail + " WHERE jobNumber='" + currentJobNumber + "'" ;
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountPassAndFailLMMSLog: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendCountPassLMMSLog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "', [dateTime]=GETDATE(),[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ",  [totalPass]=" + LoadInspection.insTotalPass + " WHERE jobNumber='" + currentJobNumber + "'" ;
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountPassLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendCountFailLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.  
		 String SQL = "UPDATE [LMMSWatchDog] SET [goodOK] ='false' , [dateTime]=GETDATE(),[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ",  [totalFail]=" + LoadInspection.insTotalFail + ",[todayTotalQuantity]=" + todayTotalQuantity + ",[todayOKTotalQuantity]=" + todayOKTotalQuantity + ",[todayNGTotalQuantity]=" + todayNGTotalQuantity + " WHERE jobNumber='" + currentJobNumber + "' AND partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		
		
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountFailLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	  funSendCountFailLMMSLog();
	}
	
	public static void funSendCountFailLMMSLog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE [LMMSWatchLog] SET [machineID]='" + ConfigLog.machinenoSet + "', [dateTime]=GETDATE(),[currentQuantity]=" + LoadDB.currentQuantity + ",[totalQuantity]=" + LoadDB.totalQuantity  + ",  [totalFail]=" + LoadInspection.insTotalFail + " WHERE jobNumber='" + currentJobNumber + "'" ;
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCountFailLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	
	public static void funSQLUpdate(String query)
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = query;
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSQLUpdate: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendInspectLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSClientVision SET inspectOk='" + LoadInspection.inspectAvai.toString() + "' WHERE partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendInspectLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funSendStartVisionLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSWatchDog SET [dateTime]=GETDATE(), [productDateIn]=GETDATE() WHERE partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendStartVisionLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	  funSendStartVisionLMMSLog();
	}
	
	public static void funSendStartVisionLMMSLog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		 
		 String SQL = "UPDATE LMMSWatchLog SET [dateTime]=GETDATE(), [productDateIn]=GETDATE() WHERE [jobNumber]='" + currentJobNumber + "'" ;
		 stmt = con.createStatement();  
		 
		 stmt.executeUpdate(SQL); 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendStartVisionLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	//2018 01 24 by wei lijia for decrease counting case
	public static void funStatusLog(String pn1, String pn2,String pn3, String pn4,String pn5, String pn6,String pn7, String pn8,String pn9, String pn10,String pn11, String pn12,String pn13, String pn14, String pn15, String pn16
			,String pv1, String pv2,String pv3, String pv4,String pv5, String pv6,String pv7, String pv8,String pv9, String pv10,String pv11, String pv12,String pv13, String pv14, String pv15, String pv16)
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  

	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "insert into LMMSStatus_His  ( [id] ,[updatedtime] ,[TransType],[machineID]  ,[inName1] ,[inName2],[inName3] ,[inName4] ,[inName5] ,[inName6],[inName7]  ,[inName8],[inName9],[inName10] ,[inName11] ,[inName12] ,[inName13] ,[inName14],[inName15] ,[inName16]  "
				 +" ,[inVal1],[inVal2] ,[inVal3] ,[inVal4],[inVal5] ,[inVal6] ,[inVal7] ,[inVal8],[inVal9],[inVal10] ,[inVal11] ,[inVal12],[inVal13] ,[inVal14] ,[inVal15] ,[inVal16]  ) " 
		         + " values ( '100' ,GETDATE(),'LOG','8','" + pn1 + "' ,'" + pn2 + "','" + pn3 + "','" + pn4 + "' ,'" + pn5 + "','" + pn6 + "'  ,'" + pn7 + "'  ,'" + pn8 + "' ,'" + pn9 + "'  ,'" + pn10 + "'  ,'" + pn11 + "'   ,'" + pn12 + "' ,'" + pn13 + "' ,'" + pn14 + "'  ,'" + pn15 + "'  ,'" + pn16 + "'  " 
		         + " ,'" + pv1 + "'  ,'" + pv2 + "'  ,'" + pv3 + "' ,'" + pv4 + "'  ,'" + pv5 + "'  ,'" + pv6 + "' ,'" + pv7 + "'  ,'" + pv8 + "'  ,'" + pv9 + "' ,'" + pv10 + "'  ,'" + pv11 + "'   ,'" + pv12 + "'  ,'" + pv13 + "' ,'" + pv14 + "'  ,'" + pv15 + "'  ,'" + pv16 + "' ) ";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
	      }
		  // Handle any errors that may have occurred.  
		  catch (Exception e) {  
		     e.printStackTrace();  
		     MainClient.errorInfo.append("\funStatusLog: " + e.getMessage());
		  }  
		  finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}  
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
		     if (con != null) try { con.close(); } catch(Exception e) {}  
		  }  
	  }  
	public static void funSendCompleteLMMS()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  

	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSWatchDog SET [dateTime]=GETDATE(), [productDateOut]=GETDATE(), [stopTime]=GETDATE() WHERE partNumber='" + currentPartNumber + "' AND machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCompleteLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	  funSendCompleteLMMSLog();
	}
	
	public static void funSendCompleteLMMSLog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSWatchLog SET [dateTime]=GETDATE(), [productDateOut]=GETDATE(), [stopTime]=GETDATE() WHERE [jobNumber]='" + currentJobNumber + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	     MainClient.errorInfo.append("\nfunSendCompleteLMMS: " + e.getMessage());
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
	
	public static void funUpdateRMSWatchdog()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  

	  
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 String SQL = "UPDATE LMMSWatchDog SET [rmsStatus]='runinspect' WHERE machineID='" + ConfigLog.machinenoSet + "'";
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }  
	
	  // Handle any errors that may have occurred.  
	  catch (Exception e) {  
	     e.printStackTrace();  
	  }  
	  finally {  
	     if (rs != null) try { rs.close(); } catch(Exception e) {}  
	     if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	     if (con != null) try { con.close(); } catch(Exception e) {}  
	  }  
	}
}
