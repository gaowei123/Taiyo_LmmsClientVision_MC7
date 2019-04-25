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
	public static int newQuantity;
	public static int currentQuantity;
	public static int totalQuantity;
	public static int todayTotalQuantity;
	public static int todayTotalQuantityFirstStart;//2018 06 18
	public static int todayOKTotalQuantity;
	public static int todayNGTotalQuantity;
	public static String currentOperation = "";
	public static String currentTotalPass;
	public static String currentTotalFail;
	public static String rmsStatus;//technician runinspect standby loadlaser
	public static String eventID = "0";
	public static String eventTrigger = "POWER ON";
	public static String[] ioName = new String[12];
	public static String[] ioVal = new String[12];
	public static Boolean[] ioValBool = new Boolean[12];
	public static String adamOnline = "false";
	
	public static Boolean runTechnician = false;
	public static Boolean runFirstTime = true;
	public static String connectionUrl = ConfigLog.connectionstrSet;
	
	
	
	
	//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty. 
	public static boolean startJobFlag = true; 
	//2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty. 
	public static boolean isReflashQTY = false;
	

	//Dwyane - 2019-0304
	public class WatchDogModel{
		
		String machineID = "";
		
		String partNumber = "";
		String jobNumber = "";
		
		String currentOperation = "";
		
		int totalPass = 0;
		int totalFail = 0;
		int totalQty = 0;
		
		int todayTotalQuantity = 0;
		int todayOKTotalQuantity = 0;
		int todayNGTotalQuantity = 0;
		
		String modeName = "";
		int currentTotalPass = 0;
		int currentTotalFail = 0;
		
		
		String model1name = "";
		String model2name = "";
		String model3name = "";
		String model4name = "";
		String model5name = "";
		String model6name = "";
		String model7name = "";
		String model8name = "";
		String model9name = "";
		String model10name = "";
		String model11name = "";
		String model12name = "";
		String model13name = "";
		String model14name = "";
		String model15name = "";
		String model16name = "";
		int ok1count = 0;
		int ok2count = 0;
		int ok3count = 0;
		int ok4count = 0;
		int ok5count = 0;
		int ok6count = 0;
		int ok7count = 0;
		int ok8count = 0;
		int ok9count = 0;
		int ok10count = 0;
		int ok11count = 0;
		int ok12count = 0;
		int ok13count = 0;
		int ok14count = 0;
		int ok15count = 0;
		int ok16count = 0;
		int ng1count = 0;
		int ng2count = 0;
		int ng3count = 0;
		int ng4count = 0;
		int ng5count = 0;
		int ng6count = 0;
		int ng7count = 0;
		int ng8count = 0;
		int ng9count = 0;
		int ng10count = 0;
		int ng11count = 0;
		int ng12count = 0;
		int ng13count = 0;
		int ng14count = 0;
		int ng15count = 0;
		int ng16count = 0;
	}

	public static void main(String[] args) {  
		
	}
	
	//Dwyane - 2019-0304
	public WatchDogModel GetMaterialPart(String partNumber) {
		
		WatchDogModel dogModel = new WatchDogModel();
		ResultSet rs = null;
		Connection con = null;  
	    Statement stmt = null;  
	    
		try {
			String sqlStr = "SELECT sn, materialPartNo FROM LMMSBomDetail  where partNumber = '"+partNumber+"' order by sn ";
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			con = DriverManager.getConnection(connectionUrl);
		 	stmt = con.createStatement();
		 	rs = stmt.executeQuery(sqlStr);  

			int i = 1;
		
			while(rs.next()) {
				switch(i) {
				case 1:
					dogModel.model1name = rs.getString("materialPartNo");
					break;
				case 2:
					dogModel.model2name = rs.getString("materialPartNo");
					break;
				case 3:
					dogModel.model3name = rs.getString("materialPartNo");
					break;
				case 4:
					dogModel.model4name = rs.getString("materialPartNo");
					break;
				case 5:
					dogModel.model5name = rs.getString("materialPartNo");
					break;
				case 6:
					dogModel.model6name = rs.getString("materialPartNo");
					break;
				case 7:
					dogModel.model7name = rs.getString("materialPartNo");
					break;
				case 8:
					dogModel.model8name = rs.getString("materialPartNo");
					break;
				case 9:
					dogModel.model9name = rs.getString("materialPartNo");
					break;
				case 10:
					dogModel.model10name = rs.getString("materialPartNo");
					break;
				case 11:
					dogModel.model11name = rs.getString("materialPartNo");
					break;
				case 12:
					dogModel.model12name = rs.getString("materialPartNo");
					break;
				case 13:
					dogModel.model13name = rs.getString("materialPartNo");
					break;
				case 14:
					dogModel.model14name = rs.getString("materialPartNo");
					break;
				case 15:
					dogModel.model15name = rs.getString("materialPartNo");
					break;
				case 16:
					dogModel.model16name = rs.getString("materialPartNo");
					break;
				}
				
				i++;
			}
		    
		}
		catch (Exception e) {  
			e.printStackTrace();  
		}  
		finally {  
			 if (rs != null) try { rs.close(); } catch(Exception e) {}  
	         if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	         if (con != null) try { con.close(); } catch(Exception e) {}  
	    }
		
		return dogModel;
	}
	
	
	
	
    
	
	public static void funReadDBParts()
	{
		if(LoadInspection.isCheckingResult )
        {
        	return;
        }//can't assign value when counting.
		
		
		Connection con = null;
		Statement stmt = null;
	    ResultSet rs = null;
	      
	    try {
	    	  
			String str_light  = "";
			String str_camera = "";
			String str_power = "";
	    	  
	    	  
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			con = DriverManager.getConnection(connectionUrl);  
			
			String SQL = "SELECT a.*, isnull( b.Lighting,'UNKNOWN') as Lighting  "; 
			SQL += " , isnull(b.Camera,'UNKNOWN') as Camera ";
			SQL += " , case when b.CurrentPower is null then 'UNKNOWN' else b.CurrentPower + '%' end  as CurrentPower ";
			SQL += "  FROM LMMSWatchDog a left join LMMSBom b on a.partNumber = b.partNumber and a.machineID = b.machineID ";
			SQL += "  WHERE a.machineID= '"+ConfigLog.machinenoSet+"' ";
			 
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			  while (rs.next()) {
	        	 
	        	 currentJobNumber = rs.getString("jobNumber");
	        	 currentPartNumber = rs.getString("partNumber");
	        	 totalQuantity = Integer.parseInt(rs.getString("totalQuantity"));
	        	 currentTotalPass = rs.getString("totalPass");
		         currentTotalFail = rs.getString("totalFail");
		         newQuantity = Integer.parseInt(rs.getString("currentQuantity"));
		         currentOperation = rs.getString("currentOperation");
		         rmsStatus = rs.getString("rmsStatus");
		         todayTotalQuantityFirstStart =Integer.parseInt(rs.getString("todayTotalQuantity")) ; //only machine 7 no need to +1
		         startJobFlag = rs.getString("goodOK").equals("true")?true:false;  //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   

		         
		         str_light = rs.getString("Lighting");
		         str_camera = rs.getString("Camera");
		         str_power = rs.getString("CurrentPower");
			  }
			  
			  
			//======= job, part, process, total quantity=======//
			MainClient.lblpartNumber.setText(currentPartNumber);
		    MainClient.lbljobNumber.setText(currentJobNumber);
		    MainClient.lblProcess.setText(currentOperation);
		    MainClient.lblQuantity.setText(Integer.toString(totalQuantity));
		    //======= job, part, process, total quantity=======//
		    
		    //2018 06 18 by wei lijia move below logic from if(runFirstTime), for the partial job. 
	        //2018 06 19 by wei lijia add ischeckresult interlok
	        //2018 07 03 barcode app will update goodOK=true while scan jobnumber,update goodok=false after update qty .  system will auto capture the new qty.   
		    if(startJobFlag || runFirstTime)
	        {
				isReflashQTY = true;
				
		        LoadInspection.insTotalPass = Integer.parseInt(currentTotalPass);
		        LoadInspection.insTotalFail = Integer.parseInt(currentTotalFail);
		        MainClient.lblTotalPass.setText(currentTotalPass);
		        MainClient.lblTotalFail.setText(currentTotalFail);
		         
		        currentQuantity = LoadInspection.insTotalPass + LoadInspection.insTotalFail;
		        MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
	            todayTotalQuantity = todayTotalQuantityFirstStart;
	            
	            
	            isReflashQTY= false;
	        }
		   
		     
		    
			  
		    MainClient.lblRMS.setText(rmsStatus);
	        if(LoadDB.rmsStatus.equals("technician"))
	        {
	        	 if(!runTechnician)
	        	 {
		        	 funReadLastEvent();
		        	 MainClient.selectTechnician();
	        	 }
	        }else {
	        	runTechnician = false;
	        }
	       
	        if(MainClient.inHelpMode == true) {
	        	MainClient.lblCompleteStatus.setBackground(new Color(255, 0, 0));
	        	MainClient.lblCompleteStatus.setVisible(true);
	        }else if((LoadInspection.insTotalPass + LoadInspection.insTotalFail) >= LoadDB.totalQuantity && !MainClient.lbljobNumber.getText().equals("")) {
	        	MainClient.lblCompleteStatus.setBackground(new Color(181, 230, 29));
				MainClient.lblCompleteStatus.setText("JOB COMPLETE!");
				MainClient.lblCompleteStatus.setVisible(true);
	        }else if(MainClient.lbljobNumber.getText().equals("")){
	        	MainClient.lblCompleteStatus.setBackground(new Color(181, 230, 29));
	        	MainClient.lblCompleteStatus.setText("Please Scan Next Job!");
				MainClient.lblCompleteStatus.setVisible(true);
	        }else{
	        	MainClient.lblCompleteStatus.setVisible(false);
	        }
	        
	         
	         if(runFirstTime)
	         {
	        	 currentQuantity = newQuantity;
		         runFirstTime = false;
	         }
	        
	         
	         MainClient.lblTotalQuantity.setText(Integer.toString(todayTotalQuantity));
	         
	         
	         if(totalQuantity == 0)
	         {
	         	currentQuantity = 0;
	         	LoadInspection.insTotalPass = 0;
	         	LoadInspection.insTotalFail = 0;
	         	MainClient.lblTotalPass.setText(currentTotalPass);
		        MainClient.lblTotalFail.setText(currentTotalFail);
		        MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
	         }
	         
	        
	         
	         
			//Light, Camera, CurrentPER
			MainClient.lb_Light.setText(str_light);
			MainClient.lb_Camera.setText(str_camera);
			MainClient.lb_CurrentPWR.setText(str_power);
			if(str_light.equals("UNKNOWN")) 
				MainClient.lb_Light.setForeground(Color.red);
			else
				MainClient.lb_Light.setForeground(Color.white);
			     
			if(str_camera.equals("UNKNOWN")) 
				MainClient.lb_Camera.setForeground(Color.red);
			else
				MainClient.lb_Camera.setForeground(Color.white);
			 
			if(str_power.equals("UNKNOWN") ) 
				MainClient.lb_CurrentPWR.setForeground(Color.red);
			else
				MainClient.lb_CurrentPWR.setForeground(Color.white);
			//Light, Camera, CurrentPER
 
	      }  

	    
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
	
	public static void funUpdateLMMSEventTechnicianStart(String sEvent)
	{
		Connection con = null;  
		  Statement stmt = null;  
		  ResultSet rs = null;  
		  	      
	      LoadInspection.funGetCurrentQuantity(); 
		  
		  try {  
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			 
			 String SQL = " insert into [LMMSEventLog]  ";
			 SQL+= 	    " ([dateTime],[machineID],[currentOperation],[eventTrigger],[startTime],[stopTime],[ipSetting])  VALUES (" ;
			 SQL+=     "  convert(date, getdate()) ,'" + ConfigLog.machinenoSet  + "','TECHNICIAN_OEE','"+sEvent+"',GETDATE(),GETDATE(),'NA')";
				
		 	 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL);
		  }
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
	
	
	
	public static void funStartQuantityChecker()
	{
		/*
		 * int result = JOptionPane.showConfirmDialog(null, MainClient.myPanel2,
		 * "Manual Quantity:" + LoadDB.currentJobNumber, JOptionPane.OK_CANCEL_OPTION);
		 * if (result == JOptionPane.OK_OPTION) {
		 * if(!MainClient.quantityField.getText().isEmpty()) { totalQuantity =
		 * Integer.parseInt(MainClient.quantityField.getText()); currentQuantity = 0;
		 * LoadInspection.insTotalPass = 0; LoadInspection.insTotalFail = 0;
		 * MainClient.lblTotalPass.setText(currentTotalPass);
		 * MainClient.lblTotalFail.setText(currentTotalFail);
		 * MainClient.lblQuantity.setText(Integer.toString(totalQuantity));
		 * MainClient.lblCQuantity.setText(Integer.toString(currentQuantity));
		 * funUpdateLMMSQuantity(); //runQty = true; } else { runQty = false; }
		 * 
		 * //System.out.println(currentQuantity); // funUpdateLMMSQuantity(); //runQty =
		 * false; //runTechnician = false; } else { runQty = false; }
		 */
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
	        //System.out.println(df.format(new Date()) + "  0.                 "+ String.valueOf(LoadDB.ioValBool[6])); 
	 		if(LoadDB.ioValBool[6])
			{
	 			//RunInspection.inspectReady = true;
				MainClient.lblStat4.setBackground(new Color(0, 255, 0));
			}
	 		else
	 		{
	 			//RunInspection.inspectReadyBuf = false; //2018 01 20 by Wei LiJia
				//RunInspection.inspectReady = false;
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
	
	public static void funSendResultLMMS()
	{
	  Connection con = null;
	  Statement stmt = null;
	  ResultSet rs = null;
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);
		
		 String SQL ="";
		 SQL += " UPDATE LMMSClientVision SET ";
		 SQL += "  [dateTime]=GETDATE() ";
		 //SQL += " ,passValue='" + LoadInspection.passStatus.toString() + "' ";
		 //SQL += " ,failValue='" + LoadInspection.failStatus.toString() + "' ";
		 SQL += " WHERE ";
		 SQL += " jobNumber='" + currentJobNumber + "' ";
		 SQL += " AND partNumber='" + currentPartNumber + "' ";
		 SQL += " AND machineID='" + ConfigLog.machinenoSet + "' ";
		 
		 
		 stmt = con.createStatement();  
		 stmt.executeUpdate(SQL); 
		 
	  }
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
	public static void funSendCountPassAndFailLMMS(LoadDB.WatchDogModel dogModel)
	{
		CommonFunc.writeLogFile("==Debug Counting==   4.1 In Func funSendCountPassAndFailLMMS");
		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null;  
		try {
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			 con = DriverManager.getConnection(connectionUrl);  
			
			 
			 String SQL = "";
			 SQL += " UPDATE [LMMSWatchDog] SET "; 
			 SQL += "  [goodOK] ='false' "; 
			 SQL += " ,[dateTime]=getdate() "; 
			 SQL += " ,[currentQuantity]="+LoadDB.currentQuantity+" "; 
			 SQL += " ,[totalQuantity]= " + LoadDB.totalQuantity  + " "; 
			 SQL += " ,[totalPass]= " + LoadInspection.insTotalPass + " "; 
			 SQL += " ,[totalFail]= " + LoadInspection.insTotalFail + " "; 
			 SQL += " ,[todayTotalQuantity]= " + todayTotalQuantity + " "; 
			 SQL += " ,[todayOKTotalQuantity]= " + todayOKTotalQuantity + " "; 
			 SQL += " ,[todayNGTotalQuantity]= " + todayNGTotalQuantity + " ";
			 
			 
			 SQL += ", model1Name = '" + dogModel.model1name + "'";
			 SQL += ", model2Name = '" + dogModel.model2name + "'";
			 SQL += ", model3Name = '" + dogModel.model3name + "'";
			 SQL += ", model4Name = '" + dogModel.model4name + "'";
			 SQL += ", model5Name = '" + dogModel.model5name + "'";
			 SQL += ", model6Name = '" + dogModel.model6name + "'";
			 SQL += ", model7Name = '" + dogModel.model7name + "'";
			 SQL += ", model8Name = '" + dogModel.model8name + "'";
			 SQL += ", model9Name = '" + dogModel.model9name + "'";
			 SQL += ", model10Name = '" + dogModel.model10name + "'";
			 SQL += ", model11Name = '" + dogModel.model11name + "'";
			 SQL += ", model12Name = '" + dogModel.model12name + "'";
			 SQL += ", model13Name = '" + dogModel.model13name + "'";
			 SQL += ", model14Name = '" + dogModel.model14name + "'";
			 SQL += ", model15Name = '" + dogModel.model15name + "'";
			 SQL += ", model16Name = '" + dogModel.model16name + "'";
			 SQL += ", ok1Count = " + Integer.toString(Math.abs(dogModel.ok1count));
			 SQL += ", ok2Count = " + Integer.toString(Math.abs(dogModel.ok2count));
			 SQL += ", ok3Count = " + Integer.toString(Math.abs(dogModel.ok3count));
			 SQL += ", ok4Count = " + Integer.toString(Math.abs(dogModel.ok4count));
			 SQL += ", ok5Count = " + Integer.toString(Math.abs(dogModel.ok5count));
			 SQL += ", ok6Count = " + Integer.toString(Math.abs(dogModel.ok6count));
			 SQL += ", ok7Count = " + Integer.toString(Math.abs(dogModel.ok7count));
			 SQL += ", ok8Count = " + Integer.toString(Math.abs(dogModel.ok8count));
			 SQL += ", ok9Count = " + Integer.toString(Math.abs(dogModel.ok9count));
			 SQL += ", ok10Count = " + Integer.toString(Math.abs(dogModel.ok10count));
			 SQL += ", ok11Count = " + Integer.toString(Math.abs(dogModel.ok11count));
			 SQL += ", ok12Count = " + Integer.toString(Math.abs(dogModel.ok12count));
			 SQL += ", ok13Count = " + Integer.toString(Math.abs(dogModel.ok13count));
			 SQL += ", ok14Count = " + Integer.toString(Math.abs(dogModel.ok14count));
			 SQL += ", ok15Count = " + Integer.toString(Math.abs(dogModel.ok15count));
			 SQL += ", ok16Count = " + Integer.toString(Math.abs(dogModel.ok16count));
			 SQL += ", ng1Count = " + Integer.toString(Math.abs(dogModel.ng1count));
			 SQL += ", ng2Count = " + Integer.toString(Math.abs(dogModel.ng2count));
			 SQL += ", ng3Count = " + Integer.toString(Math.abs(dogModel.ng3count));
			 SQL += ", ng4Count = " + Integer.toString(Math.abs(dogModel.ng4count));
			 SQL += ", ng5Count = " + Integer.toString(Math.abs(dogModel.ng5count));
			 SQL += ", ng6Count = " + Integer.toString(Math.abs(dogModel.ng6count));
			 SQL += ", ng7Count = " + Integer.toString(Math.abs(dogModel.ng7count));
			 SQL += ", ng8Count = " + Integer.toString(Math.abs(dogModel.ng8count));
			 SQL +=	", ng9Count = " + Integer.toString(Math.abs(dogModel.ng9count));
			 SQL += ", ng10Count = " + Integer.toString(Math.abs(dogModel.ng10count));
			 SQL += ", ng11Count = " + Integer.toString(Math.abs(dogModel.ng11count));
			 SQL += ", ng12Count = " + Integer.toString(Math.abs(dogModel.ng12count));
			 SQL += ", ng13Count = " + Integer.toString(Math.abs(dogModel.ng13count));
			 SQL += ", ng14Count = " + Integer.toString(Math.abs(dogModel.ng14count));
			 SQL += ", ng15Count = " + Integer.toString(Math.abs(dogModel.ng15count));
			 SQL += ", ng16Count = " + Integer.toString(Math.abs(dogModel.ng16count));
			 
			 
			 
			 SQL += " WHERE ";
			 SQL += " jobNumber='" + currentJobNumber + "' ";
			 SQL += " AND partNumber='" + currentPartNumber + "' ";
			 SQL += " AND machineID='" + ConfigLog.machinenoSet + "' ";
	
			 
			 stmt = con.createStatement();  
			 stmt.executeUpdate(SQL);
		}
		catch (Exception e) {  
		     e.printStackTrace();  
		     MainClient.errorInfo.append("\nfunSendCountPassAndFailLMMS: " + e.getMessage());
		}  
		finally {  
		     if (rs != null) try { rs.close(); } catch(Exception e) {}
		     if (stmt != null) try { stmt.close(); } catch(Exception e) {}
		     if (con != null) try { con.close(); } catch(Exception e) {}
		}  
			funSendCountPassAndFailLMMSLog(dogModel);
	}
	
	
	
	//2018 06 28 added by wei lijia for Machine 6+7
	public static void funSendCountPassAndFailLMMSLog(LoadDB.WatchDogModel dogModel)
	{
		 Connection con = null;
		  Statement stmt = null;
		  ResultSet rs = null;
	  try {
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		
		 
		 
		 
		//2019-04-25  get ok/ng from lmmswatchlog
		 
		 
		 String strSql = "Select * from LMMSWatchLog where jobnumber = '"+currentJobNumber+"'";
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			con = DriverManager.getConnection(connectionUrl);
		 	stmt = con.createStatement();
		 	rs = stmt.executeQuery(strSql);  

			while(rs.next()) {
				
				if(!rs.getString("ok1Count").equals(""))  dogModel.ok1count += Integer.parseInt(rs.getString("ok1Count"));
				if(!rs.getString("ok2Count").equals(""))  dogModel.ok2count += Integer.parseInt(rs.getString("ok2Count"));
				if(!rs.getString("ok3Count").equals(""))  dogModel.ok3count += Integer.parseInt(rs.getString("ok3Count"));
				if(!rs.getString("ok4Count").equals(""))  dogModel.ok4count += Integer.parseInt(rs.getString("ok4Count"));
				if(!rs.getString("ok5Count").equals(""))  dogModel.ok5count += Integer.parseInt(rs.getString("ok5Count"));
				if(!rs.getString("ok6Count").equals(""))  dogModel.ok6count += Integer.parseInt(rs.getString("ok6Count"));
				if(!rs.getString("ok7Count").equals(""))  dogModel.ok7count += Integer.parseInt(rs.getString("ok7Count"));
				if(!rs.getString("ok8Count").equals(""))  dogModel.ok8count += Integer.parseInt(rs.getString("ok8Count"));
				if(!rs.getString("ok9Count").equals(""))  dogModel.ok9count += Integer.parseInt(rs.getString("ok9Count"));
				if(!rs.getString("ok10Count").equals(""))  dogModel.ok10count += Integer.parseInt(rs.getString("ok10Count"));
				if(!rs.getString("ok11Count").equals(""))  dogModel.ok11count += Integer.parseInt(rs.getString("ok11Count"));
				if(!rs.getString("ok12Count").equals(""))  dogModel.ok12count += Integer.parseInt(rs.getString("ok12Count"));
				if(!rs.getString("ok13Count").equals(""))  dogModel.ok13count += Integer.parseInt(rs.getString("ok13Count"));
				if(!rs.getString("ok14Count").equals(""))  dogModel.ok14count += Integer.parseInt(rs.getString("ok14Count"));
				if(!rs.getString("ok15Count").equals(""))  dogModel.ok15count += Integer.parseInt(rs.getString("ok15Count"));
				if(!rs.getString("ok16Count").equals(""))  dogModel.ok16count += Integer.parseInt(rs.getString("ok16Count"));
				
				
				if(!rs.getString("ng1Count").equals(""))  dogModel.ng1count += Integer.parseInt(rs.getString("ng1Count"));
				if(!rs.getString("ng2Count").equals(""))  dogModel.ng2count += Integer.parseInt(rs.getString("ng2Count"));
				if(!rs.getString("ng3Count").equals(""))  dogModel.ng3count += Integer.parseInt(rs.getString("ng3Count"));
				if(!rs.getString("ng4Count").equals(""))  dogModel.ng4count += Integer.parseInt(rs.getString("ng4Count"));
				if(!rs.getString("ng5Count").equals(""))  dogModel.ng5count += Integer.parseInt(rs.getString("ng5Count"));
				if(!rs.getString("ng6Count").equals(""))  dogModel.ng6count += Integer.parseInt(rs.getString("ng6Count"));
				if(!rs.getString("ng7Count").equals(""))  dogModel.ng7count += Integer.parseInt(rs.getString("ng7Count"));
				if(!rs.getString("ng8Count").equals(""))  dogModel.ng8count += Integer.parseInt(rs.getString("ng8Count"));
				if(!rs.getString("ng9Count").equals(""))  dogModel.ng9count += Integer.parseInt(rs.getString("ng9Count"));
				if(!rs.getString("ng10Count").equals(""))  dogModel.ng10count += Integer.parseInt(rs.getString("ng10Count"));
				if(!rs.getString("ng11Count").equals(""))  dogModel.ng11count += Integer.parseInt(rs.getString("ng11Count"));
				if(!rs.getString("ng12Count").equals(""))  dogModel.ng12count += Integer.parseInt(rs.getString("ng12Count"));
				if(!rs.getString("ng13Count").equals(""))  dogModel.ng13count += Integer.parseInt(rs.getString("ng13Count"));
				if(!rs.getString("ng14Count").equals(""))  dogModel.ng14count += Integer.parseInt(rs.getString("ng14Count"));
				if(!rs.getString("ng15Count").equals(""))  dogModel.ng15count += Integer.parseInt(rs.getString("ng15Count"));
				if(!rs.getString("ng16Count").equals(""))  dogModel.ng16count += Integer.parseInt(rs.getString("ng16Count"));
				
				
			}
	
				
		 
		 
		 
		 
		 
		 
		 
		 String SQL = "";
		 SQL += " UPDATE [LMMSWatchLog] SET ";
		 SQL += "  [machineID]='" + ConfigLog.machinenoSet + "' ";
		 SQL += " ,[dateTime]=GETDATE() ";
		 SQL += " ,[currentQuantity]=" + LoadDB.currentQuantity + " ";
		 SQL += " ,[totalQuantity]=" + LoadDB.totalQuantity  + " ";
		 SQL += " ,[totalPass]=" + LoadInspection.insTotalPass + " ";
		 SQL += " ,[totalFail]=" + LoadInspection.insTotalFail + " ";
		 
		 SQL += ", model1Name = '" + dogModel.model1name + "'";
		 SQL += ", model2Name = '" + dogModel.model2name + "'";
		 SQL += ", model3Name = '" + dogModel.model3name + "'";
		 SQL += ", model4Name = '" + dogModel.model4name + "'";
		 SQL += ", model5Name = '" + dogModel.model5name + "'";
		 SQL += ", model6Name = '" + dogModel.model6name + "'";
		 SQL += ", model7Name = '" + dogModel.model7name + "'";
		 SQL += ", model8Name = '" + dogModel.model8name + "'";
		 SQL += ", model9Name = '" + dogModel.model9name + "'";
		 SQL += ", model10Name = '" + dogModel.model10name + "'";
		 SQL += ", model11Name = '" + dogModel.model11name + "'";
		 SQL += ", model12Name = '" + dogModel.model12name + "'";
		 SQL += ", model13Name = '" + dogModel.model13name + "'";
		 SQL += ", model14Name = '" + dogModel.model14name + "'";
		 SQL += ", model15Name = '" + dogModel.model15name + "'";
		 SQL += ", model16Name = '" + dogModel.model16name + "'";
		 SQL += ", ok1Count = " + Integer.toString(Math.abs(dogModel.ok1count));
		 SQL += ", ok2Count = " + Integer.toString(Math.abs(dogModel.ok2count));
		 SQL += ", ok3Count = " + Integer.toString(Math.abs(dogModel.ok3count));
		 SQL += ", ok4Count = " + Integer.toString(Math.abs(dogModel.ok4count));
		 SQL += ", ok5Count = " + Integer.toString(Math.abs(dogModel.ok5count));
		 SQL += ", ok6Count = " + Integer.toString(Math.abs(dogModel.ok6count));
		 SQL += ", ok7Count = " + Integer.toString(Math.abs(dogModel.ok7count));
		 SQL += ", ok8Count = " + Integer.toString(Math.abs(dogModel.ok8count));
		 SQL += ", ok9Count = " + Integer.toString(Math.abs(dogModel.ok9count));
		 SQL += ", ok10Count = " + Integer.toString(Math.abs(dogModel.ok10count));
		 SQL += ", ok11Count = " + Integer.toString(Math.abs(dogModel.ok11count));
		 SQL += ", ok12Count = " + Integer.toString(Math.abs(dogModel.ok12count));
		 SQL += ", ok13Count = " + Integer.toString(Math.abs(dogModel.ok13count));
		 SQL += ", ok14Count = " + Integer.toString(Math.abs(dogModel.ok14count));
		 SQL += ", ok15Count = " + Integer.toString(Math.abs(dogModel.ok15count));
		 SQL += ", ok16Count = " + Integer.toString(Math.abs(dogModel.ok16count));
		 SQL += ", ng1Count = " + Integer.toString(Math.abs(dogModel.ng1count));
		 SQL += ", ng2Count = " + Integer.toString(Math.abs(dogModel.ng2count));
		 SQL += ", ng3Count = " + Integer.toString(Math.abs(dogModel.ng3count));
		 SQL += ", ng4Count = " + Integer.toString(Math.abs(dogModel.ng4count));
		 SQL += ", ng5Count = " + Integer.toString(Math.abs(dogModel.ng5count));
		 SQL += ", ng6Count = " + Integer.toString(Math.abs(dogModel.ng6count));
		 SQL += ", ng7Count = " + Integer.toString(Math.abs(dogModel.ng7count));
		 SQL += ", ng8Count = " + Integer.toString(Math.abs(dogModel.ng8count));
		 SQL +=	", ng9Count = " + Integer.toString(Math.abs(dogModel.ng9count));
		 SQL += ", ng10Count = " + Integer.toString(Math.abs(dogModel.ng10count));
		 SQL += ", ng11Count = " + Integer.toString(Math.abs(dogModel.ng11count));
		 SQL += ", ng12Count = " + Integer.toString(Math.abs(dogModel.ng12count));
		 SQL += ", ng13Count = " + Integer.toString(Math.abs(dogModel.ng13count));
		 SQL += ", ng14Count = " + Integer.toString(Math.abs(dogModel.ng14count));
		 SQL += ", ng15Count = " + Integer.toString(Math.abs(dogModel.ng15count));
		 SQL += ", ng16Count = " + Integer.toString(Math.abs(dogModel.ng16count));
		
		 SQL += " WHERE  ";
		 SQL += " jobNumber='" + currentJobNumber + "' ";
		
		 
	
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
	
	
}
