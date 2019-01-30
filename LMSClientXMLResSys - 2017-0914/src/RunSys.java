import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import org.leibnizcenter.xml.helpers.DomHelper;
import org.leibnizcenter.xml.NotImplemented;
import org.leibnizcenter.xml.TerseJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RunSys implements Runnable{
	 public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	 public static String TEST_XML_STRING =
	        "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";	//background process for machine 1	
	//old -- location of jar ...C:\Users\NVS\Desktop\UBCT\LMMSClientM1Navitas.jar
	//location of jar ...C:\Users\NVS\Desktop\UBCT\LMMSClientVisionBGProcess.jar
	public static String fileNameNow = "";
	
	public static String strStopFlagFileName="stop"; 
	public static String strStopFlag="false"; //encountering this file stops the program after completing the batch.
	
	//String fileloc = "C:\\Users\\Administrator\\Desktop\\TESTRESULT\\"; //location of source file ...C:\Users\NVS\Desktop\UBCT\result   */
	//String fileloc = "C:/Users/apsundram/sourcefiles/"; //location of source file ...C:\Users\NVS\Desktop\UBCT\result   */
	public static String fileloc = ConfigLog.serverdirSet;
	
	public static String strStatusFlagLoc="../sourcefiles/";   //not being used
	public static String strRunningStatusFileName="status_running"; //not being used
	public static String strStoppedStatusFileName="status_stopped"; // not being used
	
	public static String partNumber;
	public static String jobNumber;
	public static String modelName;
	public static String rmsStatus;
	public static String eventTrigger;
	
	public static String machineId = "1"; 
	public static String inspButtonVal = "false";
	public static Boolean inspButtonValRemain = false;
	public static int intOKCount = 0;
	public static int intNGCount = 0;
	public static String strPassOK;
	public static String strFailOK;
	public static int intTtlPass=0; //totalPass - Sum of all okcount
	public static int intTtlFail=0; //totalFail - Sum of all ngcount
	public static int intTtlFailC =0;
	public static int intCurrentQty=0; //Sum of totalPass + totalFail	
	public static int intTtlJig=0;
	public static int intTtlPart=0;
	public static int intTtlGraphic=0;
	public static String strOverallJigJudge="true";
	public static String strOverallGraphicJudge="true"; //Overall graphic judge - Default True, if one is False found set to  False
	public static String strOverallGraphicJudgeDistanceX="true"; //Overall graphic judge distance- Default True, if one False found set to False
	public static String strOverallGraphicJudgeFindEdgeX="true"; //Overall graphic judge find edge-Default True, if one False found set to False
	
	public static String strOverallGraphicJudgeDistanceY="true"; //Overall graphic judge distance- Default True, if one False found set to False
	public static String strOverallGraphicJudgeFindEdgeY="true"; //Overall graphic judge find edge-Default True, if one False found set to False
	
	public static int intJigCounter=0;
	public static String strJig1Judge="na";
	public static String strJig2Judge="na";
	public static String strJig3Judge="na";
	public static String strJig4Judge="na";
	public static String strJig5Judge="na";
	public static String strJig6Judge="na";
	public static String strJig7Judge="na";
	public static String strJig8Judge="na";
	public static String strJig9Judge="na";
	public static String strJig10Judge="na";
	public static String strJig11Judge="na";
	public static String strJig12Judge="na";
	public static String strJig13Judge="na";
	public static String strJig14Judge="na";
	public static String strJig15Judge="na";
	public static String strJig16Judge="na";
	public static Boolean processCheck = false;
	
	public static String model1Name;
	public static int ok1Count;
	public static int ng1Count;
	
	public static String model2Name;
	public static int ok2Count;
	public static int ng2Count;
	
	public static String model3Name;
	public static int ok3Count;
	public static int ng3Count;
	
	//2018 04 14 BY WEI LIJIA
	public static String model4Name;
	public static int ok4Count;
	public static int ng4Count;
	//2018 04 14 BY WEI LIJIA
	public static String model5Name;
	public static int ok5Count;
	public static int ng5Count;
	//2018 04 14 BY WEI LIJIA
	public static String model6Name;
	public static int ok6Count;
	public static int ng6Count;
	//2018 04 14 BY WEI LIJIA
	public static String model7Name;
	public static int ok7Count;
	public static int ng7Count;
	//2018 04 14 BY WEI LIJIA
	public static String model8Name;
	public static int ok8Count;
	public static int ng8Count;
	//2018 04 14 BY WEI LIJIA
	public static String model9Name;
	public static int ok9Count;
	public static int ng9Count;
	//2018 04 14 BY WEI LIJIA
	public static String model10Name;
	public static int ok10Count;
	public static int ng10Count;
	//2018 04 14 BY WEI LIJIA
	public static String model11Name;
	public static int ok11Count;
	public static int ng11Count;
	
	//2018 04234 BY WEI LIJIA
	public static String model12Name;
	public static int ok12Count;
	public static int ng12Count;
	
	//2018 04234 BY WEI LIJIA
	public static String model13Name;
	public static int ok13Count;
	public static int ng13Count;
	//2018 04234 BY WEI LIJIA
	public static String model14Name;
	public static int ok14Count;
	public static int ng14Count;
	//2018 04234 BY WEI LIJIA
	public static String model15Name;
	public static int ok15Count;
	public static int ng15Count;
	//2018 04234 BY WEI LIJIA
	public static String model16Name;
	public static int ok16Count;
	public static int ng16Count;
	
	public static String methodJig = "single";
	public static int unitMultiplier = 1;
	public static Boolean[][] strJigResult = new Boolean [16][16];
	public static String[] finJigResult = new String [16];
	public static Boolean[] finGfxResult = new Boolean [60];
	
	public static Boolean[] finTriplePartResult = new Boolean [60];
	static Connection conn = null;
	static Statement sqlStatement = null;
	
	public static String connectionUrl = ConfigLog.connectionstrSet;//"jdbc:sqlserver://AFIFIUBCT;instanceName=SQLEXPRESS;databaseName=LMMS_TAIYO;user=sa;password=sa0";


	public RunSys(){
		
	}
	
	public void run() {
		while(true)
		{
			while(strStopFlag == "false")
			{
		  		File statusfile = new File(strStatusFlagLoc);
		    	if(statusfile.getName().startsWith(strStopFlagFileName) )
		    	{
		    		strStopFlag = "false";
		    		
		    	}
				getInspButtonVal();  // Get Inspection Button Value
			}
		}
	}
	
	public static int inspectCount = 0;
	
	public static String getLastModifiedFileName()
	{
		File file2 = getLastModifiedFile(fileloc);
		//long lastModified = file2 != null ? file2.lastModified() : -1;
		
		String[] nameArray = file2.getName().split("-");
		String[] nameOnly = nameArray[0].split(Pattern.quote("."));
	
		String nameXML = nameOnly[0] + ".xml";
		return nameXML;
	}
	public static void readFiles()
	{
		
		try{
			File folder = new File(ConfigLog.serverdirSet);
			File[] listOfFiles = folder.listFiles();
			
			if(listOfFiles.length == 0) 
			{
				XMLResSys.lblNew.setText("CHECKING  " + inspectCount);
			}
			else
			{
				
				File file2 = getLastModifiedFile(fileloc);
				if(file2 == null) {
					return;
				}
				
				String[] nameArray = file2.getName().split("-");
				String[] nameOnly = nameArray[0].split(Pattern.quote("."));
				
				String nameXML = nameOnly[0] + ".xml";
				
				
				System.out.println("Retrieve Part Number :" + partNumber + " And Job Number is " + jobNumber);
				System.out.println("Processing XML File....");
				System.out.println(nameXML);
				String parseStatus = readXML(nameXML);
				if(!XMLResSys.enableTest)
				{
					inspectCount++;
					dbUpdateLMMSWatch();
					dbUpdateLMMSClientVision();
					dbUpdateLMMSClientVisionJigStatus();
				}
				fileNameNow = nameXML;
				inspButtonValRemain = false;
				processCheck = true;
				XMLResSys.lblNew.setText("COLLECTED " + inspectCount);
				
				XMLFileBackup();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	public static void copyFile(String oldPath, String newPath) throws IOException 
	{
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
    
        in.close();
        out.close();
    }
	
	public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();
        
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        
        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            }
            
            if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }
            
        }
    }
	 
	
	 public static boolean delAllFile(String path) 
	 {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	 }
   
	 public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	
	public static void XMLFileBackup () throws Exception 
	{
		//check backup folder whether exist   if not create
		String BackupPath = ConfigLog.BackupdirSet;
		File file =new File(BackupPath);    
		
		if(!file.exists()  && !file.isDirectory())
		{       
		    file .mkdir();    
		}
		
		
		//check today backup folder whether exist   if not create
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String TodayFolder = BackupPath + "\\\\" + sdf.format(new Date());
		File file2 =new File(TodayFolder);    
		
		if(!file2.exists()  && !file2.isDirectory())
		{       
			file2 .mkdir();    
		}
		
		//check others folder whether exist  if not create
		String path3 = TodayFolder + "\\\\"+ "ALL";
		File file3 =new File(path3);    
		
		if(!file3.exists()  && !file3.isDirectory())
		{       
			file3 .mkdir();    
		}
		
		
		
		//start backup
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HHmmss");
		String currFilePath = ConfigLog.serverdirSet + fileNameNow;
		String BkFilePath = TodayFolder + "\\\\" +  fileNameNow.split("\\.")[0] + "--" + sdf2.format(new Date())+".xml";
		
		//copy xml file
		copyFile(currFilePath,BkFilePath);
		
		//copy other file
		copyDir(ConfigLog.serverdirSet,path3);
		
		//delete all file
		delAllFile(ConfigLog.serverdirSet);
	}

	public static String readXML(String FileName) throws IOException, SAXException, ParserConfigurationException, NotImplemented
	{
			
	    Document doc = null;
	    try 
	    {
	    	doc = parseXML(fileloc + FileName);
	    } 
	    catch (ParserConfigurationException e)
	    {
	        e.printStackTrace();
	        return "ERROR";
	    } 
	    catch (SAXException e) 
	    {
	        e.printStackTrace();
	        return "ERROR";
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	        return "ERROR";
	    }
	    
	    
	    try {
	    	String content = new Scanner(new File(fileloc + FileName)).useDelimiter("\\Z").next();

            JSONObject xmlJSONObj = XML.toJSONObject(content);
            
            //JSONArray msg = (JSONArray) xmlJSONObj.get("nodes");
            JSONObject getSth = xmlJSONObj.getJSONObject("ResultData");

            modelName = (String) getSth.get("modelName");
            modelName = modelName.substring(modelName.indexOf("vision") + 7, modelName.length());
            modelName = modelName.substring(0,modelName.indexOf("\\")).toUpperCase();
            System.out.println("Model: " + modelName);
            model1Name = ""; 
            model2Name = ""; 
            model3Name = ""; 
            ok1Count = 0;
            ok2Count = 0;
            ok3Count = 0;
            ng1Count = 0;
            ng2Count = 0;
            ng3Count = 0;
          //2018 04 14 BY WEI LIJIA
            model4Name = ""; 
            model5Name = ""; 
            model6Name = ""; 
            model7Name = ""; 
            model8Name = ""; 
            model9Name = ""; 
            model10Name = ""; 
            model11Name = "";  
            model12Name = "";  
            model13Name = "";  
            model14Name = "";  
            model15Name = "";  
            model16Name = "";  
            ok4Count = 0;
            ok5Count = 0;
            ok6Count = 0;
            ok7Count = 0;
            ok8Count = 0;
            ok9Count = 0;
            ok10Count = 0;
            ok11Count = 0; 
            ok12Count = 0; 
            ok13Count = 0; 
            ok14Count = 0; 
            ok15Count = 0; 
            ok16Count = 0; 
            
            ng4Count = 0;
            ng5Count = 0;
            ng6Count = 0;
            ng7Count = 0;
            ng8Count = 0;
            ng9Count = 0;
            ng10Count = 0;
            ng11Count = 0; 
            ng12Count = 0; 
            ng13Count = 0; 
            ng14Count = 0; 
            ng15Count = 0; 
            ng16Count = 0; 
            
            inspButtonVal = "false";
    		intJigCounter = 0;
    		intOKCount = 0;
    		intNGCount = 0;
    		strPassOK = "";
    		strFailOK = "";
    		intTtlPass=0; //totalPass - Sum of all okcount
    		intTtlFail=0; //totalFail - Sum of all ngcount
    		intCurrentQty=0; //Sum of totalPass + totalFail	
    		intTtlJig=0;
    		intTtlGraphic=0;
    		strOverallJigJudge="true";
    		strOverallGraphicJudge="true"; 
    		strOverallGraphicJudgeDistanceX="true";
    		strOverallGraphicJudgeFindEdgeX="true";
    		strOverallGraphicJudgeDistanceY="true"; 
    		strOverallGraphicJudgeFindEdgeY="true"; 
            strJigResult = new Boolean[16][16];
            finTriplePartResult = new Boolean[60];
            finJigResult = new String[16];
            
            System.out.println("modelName:" + modelName  );//2018 04 14 BY WE ILIJIA TKS820|
            
            model1Name = modelName;
        	methodJig = "BOM";
        	System.out.println("methodJig = "+methodJig+" ; modelName:" + modelName  );//2018 04 25 BY WE ILIJIA
       	
        	unitMultiplier = dbGetLMMSWatch(machineId, partNumber, jobNumber);
            
        	
            XMLResSys.lblXMLpacket.setText(FileName);
            Boolean overallJudge = (Boolean) getSth.get("judge");
            
            if(overallJudge)
            {
            	strPassOK =  "true";  
                strFailOK =  "false";
            }
            else
            {
            	strPassOK =  "false";  
                strFailOK =  "true";
            
            }
          //===================================================================================================================
         
            //else if(methodJig.equals("BOM")) //2018 04 25 BY WE ILIJIA
            { 
            	System.out.println("methodJig:" + methodJig  ); 
 	           
           	    JSONObject getSth1 = getSth.getJSONObject("nodes");
	            JSONObject getSth2 = getSth1.getJSONObject("node");
	            JSONObject getSth3 = getSth2.getJSONObject("nodes");
	            JSONObject getSth4 = getSth3.getJSONObject("node");
	            JSONObject getSth5 = getSth4.getJSONObject("nodes");
	            JSONArray nodeData = getSth5.getJSONArray("node");
	          
	            int iStartSeq =0;
	        	String jigName = ((JSONObject) nodeData.get(0)).get("name").toString();
	        	
	        	System.out.println("0 jigName"+jigName+";"   );//2018 04 14 BY WE ILIJIA
            	
            	 if(!jigName.toUpperCase() .startsWith("JIG"))
            	{
            		iStartSeq =1;
            	}
            	 
            	System.out.println("0 Start Seq:"+iStartSeq+";"   );//2018 04 14 BY WE ILIJIA
            	
            	 intTtlPart = nodeData.length() - iStartSeq; 
  	            //System.out.println(intTtlPart);
  	            System.out.println("intTtlPart:" + intTtlPart  ); 
  	            
	            for(int i=1;i<(intTtlPart+1);i++)
	            {
	            	System.out.println("1:"   );//2018 04 14 BY WE ILIJIA
	  	             
		            jigName = ((JSONObject) nodeData.get(i -1 +iStartSeq)).get("name").toString();
		            JSONObject jsonObject1 = (JSONObject) nodeData.get(i-1 +iStartSeq);
		             
	            	System.out.println("2: jigName=" +jigName   );//2018 04 14 BY WE ILIJIA
		  	          
	            	JSONObject getSth6 = jsonObject1.getJSONObject("nodes");
	            	JSONArray jsonarray1 = (JSONArray) getSth6.get("node");//Jig Level
	            	int insGfxL = jsonarray1.length();  // units count
	            	
	            	System.out.println("insGfxL:" + insGfxL  );//2018 04 14 BY WE ILIJIA
	            	
		            String[]  sMateName = new String[insGfxL];
		            String[]  sMateNameList = new String[insGfxL];
		            for (int ms = 0 ; ms< sMateNameList.length; ms ++ )
            		{
		            	sMateNameList[ms] = "";
            		}
            		
	            	for(int j=0;j<insGfxL;j++)  //unit level
	                {
	            		
	            		sMateName[j] =  ((JSONObject) jsonarray1.get(j)).get("name").toString(); //E.G. BUTTON-1 , BUTTON-2, FAN-1, OFF-2
	            		System.out.println("3 UnitLevel Name: "+sMateName[j] +";"   );//2018 05 08 BY WE ILIJIA
	            		
	            		JSONObject jsonObject2 = (JSONObject) jsonarray1.get(j);
	            		JSONObject getSth7 = jsonObject2.getJSONObject("nodes");
	            		JSONArray jsonarray2 = (JSONArray) getSth7.get("node");  //unit level
	            		int insMeasureL = jsonarray2.length();  // data count per unit
	            		System.out.println("3.1 Data Count per Unit: "+ insMeasureL+ ";"   );//2018 05 08 BY WE ILIJIA 
	            		
	            		Boolean[] gfxCompare = new Boolean[insMeasureL];
	            		int[] failedUnits= new int[insGfxL];
	            		int failed = 0;
	            		for(int k=0;k<insMeasureL;k++)
		                {	
	            			//05-09 problem 1 : if multiple pic in one unit like tks707, below logic will cause error, because system should look into next node level for measure data
	                    	String sMeasureNodeType1  =  ((JSONObject) jsonarray2.get(k)).get("nodeType").toString(); //E.G. Measure
	                    	System.out.println("3.2 cycle K: sMeasureNodeType1 "+ sMeasureNodeType1.toUpperCase() +";"+ "Measure UpperCase = "+"Measure".toUpperCase()+ ";"   );//2018 05 08 BY WE ILIJIA 
			              
		                    if (sMeasureNodeType1.toUpperCase() .equals( "Measure".toUpperCase() ))
		            		{ 
		                    	String gfxJudge = ((JSONObject) jsonarray2.get(k)).get("judge").toString();    
		                    	
		                    	System.out.println("3.2.1.1 cycle K: JudgeResult= "+ gfxJudge+ ";"   );//2018 05 08 BY WE ILIJIA 
					             
		                    	
		                        gfxCompare[k] = Boolean.valueOf(gfxJudge);
		                      
		                        JSONObject jsonObject3 = (JSONObject) jsonarray2.get(k);
		                    	try
		                    	{
		                    		Double getSth8 = (Double) jsonObject3.getJSONObject("nodeMeasure").getJSONObject("measures").getJSONObject("measure").get("rowInsp");
		                    		System.out.println("3.2.1.2 cycle K: nodeMeasure.measures.measure.rowInsp = "+ getSth8 + ";"   );//2018 05 08 BY WE ILIJIA 
							          
		                    	}
		                    	catch (JSONException jex) { 
		                    		   if(failedUnits[j] ==0 )
		                    		   {
			                    		failedUnits[j]++;
		                    		   }
			                    		gfxCompare[k] = false;
			                    		failed++; 
			                    		System.out.println("3.2.1.X cycle K: nodeMeasure.measures.measure.rowInsp = can not get ;"   );//2018 05 08 BY WE ILIJIA 
									       
		                    	}	
		                      
	            			}
	            			else 
	            			{ 
	            		 		System.out.println("3.2.2.1 cycle K: sMeasureNodeType1 != Measure "   );//2018 05 08 BY WE ILIJIA 
								
	            				//PIC NAME E.G. UP, FAN, DOWN
	    	            		JSONObject jsonObject2_2 = (JSONObject) jsonarray2.get(k);
	    	            		JSONObject getSth7_2 = jsonObject2_2.getJSONObject("nodes");
	    	            		JSONArray jsonarray2_2 = (JSONArray) getSth7_2.get("node");  //PIC level
	    	            		int insMeasureL_2 = jsonarray2_2.length();  // data count per PIC
	    	            		System.out.println("3.2.2.2 cycle K: Data Count Pic(insMeasureL_2) = "+insMeasureL_2 +"; "   );//2018 05 08 BY WE ILIJIA 
								
	    	            		Boolean[] gfxCompare_2 = new Boolean[insMeasureL_2];
	    	            		int[] failedUnits_2= new int[insGfxL];
	    	            		int failed_2 = 0;
	    	            		for(int k_2=0;k_2<insMeasureL_2;k_2++)
	    		                {	
	    	            			
	    	            			//05-09 problem 1 : if multiple pic in one unit like tks707, below logic will cause error, because system should look into next node level for measure data
	    	                    	String sMeasureNodeType2  =  ((JSONObject) jsonarray2_2.get(k_2)).get("nodeType").toString(); //E.G. Measure 
	    	                    	System.out.println("3.2.2.3 cycle K_2: sMeasureNodeType2 "+ sMeasureNodeType2.toUpperCase() +";"+ "Measure UpperCase = "+"Measure".toUpperCase()+ ";"   );//2018 05 08 BY WE ILIJIA 
	    				              
	    	                    	if (sMeasureNodeType2.toUpperCase().equals("Measure".toUpperCase()) )
	    	            			{
	    			                	String gfxJudge = ((JSONObject) jsonarray2_2.get(k_2)).get("judge").toString();     	
	    			                	gfxCompare_2[k_2] = Boolean.valueOf(gfxJudge);
	    			                	System.out.println("3.2.2.4 cycle K_2: k_2 = "+ k_2 +"gfxCompare_2[k_2] "+ gfxJudge+ ";"   );//2018 05 08 BY WE ILIJIA 
		    				            
	    		                    	JSONObject jsonObject3 = (JSONObject) jsonarray2_2.get(k_2);
	    		                    	try
	    		                    	{
	    		                    		Double getSth8 = (Double) jsonObject3.getJSONObject("nodeMeasure").getJSONObject("measures").getJSONObject("measure").get("rowInsp");
	    		                    		System.out.println("3.2.2.5 cycle K_2: nodeMeasure.measures.measure.rowInsp = "+ getSth8 + ";"   );//2018 05 08 BY WE ILIJIA 
	    								       
	    		                    	}
	    		                    	catch (JSONException jex) { 
	    			                    		failedUnits_2[j]++;
	    			                    		gfxCompare_2[k_2] = false;
	    			                    		failed++; 
	    			                    		System.out.println("3.2.2.X cycle K_2: nodeMeasure.measures.measure.rowInsp = can not get ; Break K_2;"   );//2018 05 08 BY WE ILIJIA 
	    									    break;   // only one 
	    		                    	}	
	    	            			}
	    	            			else 
	    	            			{
	    	            				//
	    	            				System.out.println("3.2.2.X cycle K_2: sMeasureNodeType2 != Measure; !!!    " );//2018 05 08 BY WE ILIJIA 
  									  
	    	            			}
	    		                }
	    	            		if (failedUnits_2[j]>0)
	    	            		{
	    	            			failedUnits[j] = 1;
	    	            		}
	    	            		gfxCompare[k] =  areAllTrue(gfxCompare_2);
	    	            		System.out.println("3.2.2.6 Cycle K_2 -> Cycle K : gfxCompare[k] = "+ gfxCompare[k]+"" );//2018 05 08 BY WE ILIJIA 
								  
	            			}
		                }
	            		
	            		Boolean jigRes = areAllTrue(gfxCompare);
	            		System.out.println("4 Cycle K -> Cycle j : jigRes = "+ jigRes+"" );//2018 05 08 BY WE ILIJIA 
						
	            		finTriplePartResult[i-1] = jigRes;

	            			            		
	            		//05-09 problem 2 :  below logic is for triple type of production, 3X2 per block.  it is not fit for 1X4, 1X3 or other types.
	            		String[] UnitName = sMateName[j].split("-");   //E.G. BUTTON-1 , BUTTON-2, FAN-1, OFF-2 , remove the char after "-"
	            		
	            		System.out.println("5  Cycle j : UnitName = "+ UnitName +"" );//2018 05 08 BY WE ILIJIA 
						
	            		int mseq = 0;    // the matched material seq no. 
	            		int mlseq = -1;  // material last seq .  -1 means found the material in list.
	            		for (int ms = 0 ; ms< sMateNameList.length; ms ++ )
	            		{
	            			System.out.println("6.1  Cycle ms :  sMateNameList[ms] = "+  sMateNameList[ms] +"" );//2018 05 08 BY WE ILIJIA 
							
	            			if ( !sMateNameList[ms] .equals(""))   
	            			{
	            				// compare the material list 
		            			if( sMateNameList[ms] .equals( UnitName[0] ))
		            			{
		            				 
		            				mseq = ms + 1; 
		            				System.out.println("6.2  Cycle ms :  Material Matched ; mseq=" + mseq + ";"  );//2018 05 08 BY WE ILIJIA 
									
		            				break;     // if same then break, and count base on mseq
		            			}
		            			else
		            			{
		            				//move to next 
		            				System.out.println("6.3  Cycle ms :  Material not Match ; mseq=" + mseq + ";"  );//2018 05 08 BY WE ILIJIA 
									
		            			}
	            			}
	            			else
	            			{
	            				//if sMateNameList[ms] = "", it means checked all the current material, and move to the last material position. (note: not the array last position)
	            				
	            				mlseq = ms;
	            				mseq = ms + 1; 
	            				System.out.println("6.4  Cycle ms :  Material = empty ; mlseq=" + mlseq + "; mseq = " + mseq + ";"  );//2018 05 08 BY WE ILIJIA 
								
	            				break;
	            			}
	            		}
	            		
	            		
	            		if (mlseq != -1  )
	            		{
	            			System.out.println("6.5  Cycle j : Material Name not included in sMateNameList;  sMateNameList[mlseq] =" +sMateNameList[mlseq] + "; UnitName[0] = " + UnitName[0] + ";"  );//2018 05 08 BY WE ILIJIA 
							
	            			sMateNameList[mlseq] = UnitName[0];
	            		}
	            		 
	            		System.out.println("6.6  Cycle j : Calculate Count: ModeNo(mseq) = " +mseq + "; Result(jigRes)="+jigRes+"; failedUnits[j] = " + failedUnits[j] + ";");//2018 05 08 BY WE ILIJIA 
					 	if(mseq == 1)
		            	{
		            		if(jigRes)
		            		{
		            			ok1Count++;
		            		}
		            		else
		            		{
		            			ng1Count++;
		            			//ng1Count = ng1Count;// - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng1Count="+ng1Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
		    		        
		            		ng1Count = ng1Count - failedUnits[j];	
		            	}
		            	else if(mseq == 2)
		            	{
		            		if(jigRes)
		            		{
		            			ok2Count++;
		            		}
		            		else
		            		{
		            			ng2Count++;
		            			//ng2Count = ng2Count;// - failedUnits[j];
		            		}
		            		System.out.println("---NG Count" + " : ng2Count="+ng2Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng2Count = ng2Count - failedUnits[j];	

		            	}
		            	else if(mseq == 3)
		            	{
		            		if(jigRes)
		            		{
		            			ok3Count++;
		            		}
		            		else
		            		{
		            			ng3Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng3Count="+ng3Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng3Count = ng3Count - failedUnits[j];	

		            	}
		            	else if(mseq == 4)
		            	{
		            		if(jigRes)
		            		{
		            			ok4Count++;
		            		}
		            		else
		            		{
		            			ng4Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng4Count="+ng4Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng4Count = ng4Count - failedUnits[j];	

		            	}
		            	else if(mseq == 5)
		            	{
		            		if(jigRes)
		            		{
		            			ok5Count++;
		            		}
		            		else
		            		{
		            			ng5Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng5Count="+ng5Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
				    		  
		            		ng5Count = ng5Count - failedUnits[j];	

		            	}
		            	else if(mseq == 6)
		            	{
		            		if(jigRes)
		            		{
		            			ok6Count++;
		            		}
		            		else
		            		{
		            			ng6Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng6Count="+ng6Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng6Count = ng6Count - failedUnits[j];	

		            	}
		            	else if(mseq == 7)
		            	{
		            		if(jigRes)
		            		{
		            			ok7Count++;
		            		}
		            		else
		            		{
		            			ng7Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng7Count="+ng7Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng7Count = ng7Count - failedUnits[j];	

		            	}
		            	else if(mseq == 8)
		            	{
		            		if(jigRes)
		            		{
		            			ok8Count++;
		            		}
		            		else
		            		{
		            			ng8Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng8Count="+ng8Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng8Count = ng8Count - failedUnits[j];	

		            	}
		            	else if(mseq == 9)
		            	{
		            		if(jigRes)
		            		{
		            			ok9Count++;
		            		}
		            		else
		            		{
		            			ng9Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng9Count="+ng9Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng9Count = ng9Count - failedUnits[j];	

		            	}
		            	else if(mseq == 10)
		            	{
		            		if(jigRes)
		            		{
		            			ok10Count++;
		            		}
		            		else
		            		{
		            			ng10Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng10Count="+ng10Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng10Count = ng10Count - failedUnits[j];	

		            	}
		            	else if(mseq == 11)
		            	{
		            		if(jigRes)
		            		{
		            			ok11Count++;
		            		}
		            		else
		            		{
		            			ng11Count++;
		            			//ng3Count = ng3Count - failedUnits[j];	
		            		}
		            		System.out.println("---NG Count" + " : ng11Count="+ng11Count + ";j="+j+";failedUnits[j]="+failedUnits[j]);
			    		       
		            		ng11Count = ng11Count - failedUnits[j];	

		            	}
	                }
	             	System.out.println("jigName=" + jigName + ":" + finTriplePartResult[i-1]); 
	            	finJigResult[i-1] = Boolean.toString(finTriplePartResult[i-1]);
	            	System.out.println("i=" + i + ":" + "Length of finTriplePartResult=" + finTriplePartResult.length); 
	            	
	            }
	            System.out.println("intTtlPass" + ":" + intTtlPass);
	            System.out.println("intTtlFail" + ":" + intTtlFail);
		          
	            intTtlPass = ok1Count + ok2Count + ok3Count+ ok4Count + ok5Count + ok6Count+ ok7Count + ok8Count + ok9Count+ ok10Count + ok11Count ;
	            intTtlFail = ng1Count + ng2Count + ng3Count+ ng4Count + ng5Count + ng6Count+ ng7Count + ng8Count + ng9Count+ ng10Count + ng11Count ;
	            //intTtlFail = Math.abs(intTtlFailC);
	            System.out.println("intTtlPass" + ":" + intTtlPass);
	            System.out.println("intTtlFail" + ":" + intTtlFail);
		          
	            
	            if(intTtlFail<0)
	            {
	            	intTtlFail = 0;
	            }
            }
          
            
            intCurrentQty = intTtlPass + intTtlFailC;
            
            strJig1Judge = finJigResult[0];
            strJig2Judge = finJigResult[1];
            strJig3Judge = finJigResult[2];
            strJig4Judge = finJigResult[3];
            strJig5Judge = finJigResult[4];
            strJig6Judge = finJigResult[5];
            strJig7Judge = finJigResult[6];
            strJig8Judge = finJigResult[7];
            strJig9Judge = finJigResult[8];
            strJig10Judge = finJigResult[9];
            strJig11Judge = finJigResult[10];
            strJig12Judge = finJigResult[11];
            strJig13Judge = finJigResult[12];
            strJig14Judge = finJigResult[13];
            strJig15Judge = finJigResult[14];
            strJig16Judge = finJigResult[15];
                        
	      } catch (JSONException je) {
            //System.out.println(je.toString());
	      }

	    return "GOOD";
	}
	
	public static Boolean areAllTrue(Boolean[] gfxCompare)
	{
	    for(Boolean b : gfxCompare) if(!b) return false;
	    return true;
	}
	
	public static Boolean jigFix(Boolean[] jigGfx)
	{
		Boolean val = true;
		for(int k=0;k<intTtlGraphic;k++)
		{
			val = val && jigGfx[k];
		}
		return val;
	}
	

	public void getInspButtonVal()
	{
		ResultSet rs = null;  
		sqlStatement = null;
		
		
		inspButtonVal = "false";
		/*try {
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {
			    sqlStatement = conn.createStatement();
	
		        String SQL = "SELECT * FROM LMMSMachineIO WHERE machineID='" + machineId + "'";  
		        
		        sqlStatement = conn.createStatement();  
		        rs = sqlStatement.executeQuery(SQL);    
		        while(rs.next()) {
		        	//inspButtonVal = rs.getString("inVal14").replace(" ", "");
		        	inspButtonVal = rs.getString("inVal9").replace(" ", "");
	
		        }  
	
		        conn.close();
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		try {
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {
			    sqlStatement = conn.createStatement();
	
		        String SQL = "SELECT rmsStatus FROM LMMSWatchDog WHERE machineID='" + machineId + "'";  
		        
		        sqlStatement = conn.createStatement();  
		        rs = sqlStatement.executeQuery(SQL);    
		        while(rs.next()) {
		        	//inspButtonVal = rs.getString("inVal14").replace(" ", "");
		        	rmsStatus = rs.getString("rmsStatus").replace(" ", "");
	
		        }  
	
		        conn.close();
		
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		if(!rmsStatus.equals("technician"))
		{
			
			inspButtonValRemain = true;
			
			if(inspButtonValRemain)
			{
				//if(!processCheck)
				{
					getPartNumber();     // Get Part Number And Job Number
					readFiles();
					XMLResSys.lblInspect.setText("TRUE");
				}
			}
			else
			{
				//?????? Clear the folder 
				
				
				
				
				processCheck = false;
				XMLResSys.lblInspect.setText("FALSE");
			}
		}
		
	}

	public void getPartNumber()
	{
		
		ResultSet rs = null;  
		sqlStatement = null;
		
		try {
			
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			conn = DriverManager.getConnection(connectionUrl);
			//conn = DriverManager.getConnection(dbURL);
			if (conn != null) {
				//2018 06 08 by weilijia . wrong logic to get watchlog , it will cause error for partial job.
			    //String SQL = "SELECT TOP(1) * FROM LMMSWatchLog WHERE machineID='" + machineId + "' ORDER BY id DESC";  
			    String SQL = "SELECT TOP(1) * FROM LMMSWatchDog WHERE machineID='" + machineId + "' ORDER BY id DESC";  
		        sqlStatement = conn.createStatement();  
		        //stmt = con.createStatement();  
		        rs = sqlStatement.executeQuery(SQL);  
		        //stmt.executeUpdate(SQL); 
		        // Iterate through the data in the result set and display it.  
		        if (rs.next()) {  
		        	
		        	partNumber =  rs.getString("partNumber");
		        	jobNumber = rs.getString("jobNumber");
		        }  
	
		        //close the database connection
		        conn.close();
	
		    	//System.out.println("Retrieve Part Number :" + partNumber + " And Job Number is " + jobNumber);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void dbUpdateLMMSWatch()  //Update LMMSWatchDog And insert LMMSWatchLog
	{
		ResultSet rs = null;  
		sqlStatement = null;
		
		try {
			
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {
			    sqlStatement = conn.createStatement();
			    //20`8 04 23 by wei lijia add = isnull(ok1Count ,0) . if value is null, ok1Count += QTY keep null value.
			    //2018 04 23 by wei lijia add more model items till 16
			    String commandStringUL="Update [LMMSWatchLog] set modelName = '" 
	        		+ modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber 
	        		+ "', currentTotalPass = " + intTtlPass 
	        		+ ", currentTotalFail = " + intTtlFail 
	        		//+ ", CurrentQuantity = CurrentQuantity + " + intCurrentQty
		    		+ ", TotalPass = TotalPass + " + Integer.toString(intTtlPass)
		    		+ ", TotalFail = TotalFail + " + Integer.toString(intTtlFail)  
		    		+ ", model1Name = '" + model1Name + "'"
		    		+ ", model2Name = '" + model2Name + "'"
		    		+ ", model3Name = '" + model3Name + "'"
		            + ", model4Name = '" + model4Name + "'"
		    		+ ", model5Name = '" + model5Name + "'"
		    		+ ", model6Name = '" + model6Name + "'"
		    	    + ", model7Name = '" + model7Name + "'"
		    		+ ", model8Name = '" + model8Name + "'"
		    		+ ", model9Name = '" + model9Name + "'"
		    		+ ", model10Name = '" + model10Name + "'"
		    		+ ", model11Name = '" + model11Name + "'"
		    		+ ", model12Name = '" + model12Name + "'"
		    		+ ", model13Name = '" + model13Name + "'"
		    		+ ", model14Name = '" + model14Name + "'"
		    		+ ", model15Name = '" + model15Name + "'"
				    + ", model16Name = '" + model16Name + "'"
		    		+ ", ok1Count = isnull(ok1Count ,0) + " + Integer.toString(Math.abs(ok1Count))
    				+ ", ok2Count = isnull(ok2Count ,0) + " + Integer.toString(Math.abs(ok2Count))
    				+ ", ok3Count = isnull(ok3Count ,0) + " + Integer.toString(Math.abs(ok3Count))
    				+ ", ok4Count = isnull(ok4Count ,0) + " + Integer.toString(Math.abs(ok4Count))
    				+ ", ok5Count = isnull(ok5Count ,0) + " + Integer.toString(Math.abs(ok5Count))
    				+ ", ok6Count = isnull(ok6Count ,0) + " + Integer.toString(Math.abs(ok6Count))
    				+ ", ok7Count = isnull(ok7Count ,0) + " + Integer.toString(Math.abs(ok7Count))
    				+ ", ok8Count = isnull(ok8Count ,0) + " + Integer.toString(Math.abs(ok8Count))
    				+ ", ok9Count = isnull(ok9Count ,0) + " + Integer.toString(Math.abs(ok9Count))
    				+ ", ok10Count = isnull(ok10Count ,0) + " + Integer.toString(Math.abs(ok10Count))
    				+ ", ok11Count = isnull(ok11Count ,0) + " + Integer.toString(Math.abs(ok11Count))
    				+ ", ok12Count = isnull(ok12Count ,0) + " + Integer.toString(Math.abs(ok12Count))
    				+ ", ok13Count = isnull(ok13Count ,0) + " + Integer.toString(Math.abs(ok13Count))
    				+ ", ok14Count = isnull(ok14Count ,0) + " + Integer.toString(Math.abs(ok14Count))
    				+ ", ok15Count = isnull(ok15Count ,0) + " + Integer.toString(Math.abs(ok15Count))
    				+ ", ok16Count = isnull(ok16Count ,0) + " + Integer.toString(Math.abs(ok16Count))
    				+ ", ng1Count = isnull(ng1Count ,0) + " + Integer.toString(Math.abs(ng1Count))
    				+ ", ng2Count = isnull(ng2Count ,0) + " + Integer.toString(Math.abs(ng2Count))
    				+ ", ng3Count = isnull(ng3Count ,0) + " + Integer.toString(Math.abs(ng3Count))
    				+ ", ng4Count = isnull(ng4Count ,0) + " + Integer.toString(Math.abs(ng4Count))
    				+ ", ng5Count = isnull(ng5Count ,0) + " + Integer.toString(Math.abs(ng5Count))
    				+ ", ng6Count = isnull(ng6Count ,0) + " + Integer.toString(Math.abs(ng6Count))
    				+ ", ng7Count = isnull(ng7Count ,0) + " + Integer.toString(Math.abs(ng7Count))
    				+ ", ng8Count = isnull(ng8Count ,0) + " + Integer.toString(Math.abs(ng8Count))
    				+ ", ng9Count = isnull(ng9Count ,0) + " + Integer.toString(Math.abs(ng9Count))
    				+ ", ng10Count = isnull(ng10Count ,0) + " + Integer.toString(Math.abs(ng10Count))
    				+ ", ng11Count = isnull(ng11Count ,0) + " + Integer.toString(Math.abs(ng11Count))
    				+ ", ng12Count = isnull(ng12Count ,0) + " + Integer.toString(Math.abs(ng12Count))
    				+ ", ng13Count = isnull(ng13Count ,0) + " + Integer.toString(Math.abs(ng13Count))
    				+ ", ng14Count = isnull(ng14Count ,0) + " + Integer.toString(Math.abs(ng14Count))
    				+ ", ng15Count = isnull(ng15Count ,0) + " + Integer.toString(Math.abs(ng15Count))
    				+ ", ng16Count = isnull(ng16Count ,0) + " + Integer.toString(Math.abs(ng16Count))
    				 + " where machineID = '" + machineId + "' "
		    		+ " and jobNumber = '" + jobNumber + "'";
		    
	        	sqlStatement.executeUpdate(commandStringUL);

		        String commandStringUD="Update [LMMSWatchDog] set [datetime] = getdate(), modelName = '" 
		        		+ modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber 
		        		+ "', currentTotalPass = " + Integer.toString(intTtlPass) 
		        		+ ", currentTotalFail = " + Integer.toString(intTtlFail) 
		        		//+ ", currentQuantity = currentQuantity + " + Integer.toString(intCurrentQty)
			    		+ ", TotalPass = TotalPass + " + Integer.toString(intTtlPass)
			    		+ ", TotalFail = TotalFail + " + Integer.toString(intTtlFail) 
			    		+ ", todayTotalQuantity = todayTotalQuantity + " + Integer.toString(intTtlPass + intTtlFail)
			    		+ ", todayOKTotalQuantity = todayOKTotalQuantity + " + Integer.toString(intTtlPass)
			    		+ ", todayNGTotalQuantity = todayNGTotalQuantity + " + Integer.toString(intTtlFail)
			    		+ ", model1Name = '" + model1Name + "'"
			    		+ ", model2Name = '" + model2Name + "'"
			    		+ ", model3Name = '" + model3Name + "'"
			            + ", model4Name = '" + model4Name + "'"
			    		+ ", model5Name = '" + model5Name + "'"
			    		+ ", model6Name = '" + model6Name + "'"
			    	    + ", model7Name = '" + model7Name + "'"
			    		+ ", model8Name = '" + model8Name + "'"
			    		+ ", model9Name = '" + model9Name + "'"
			    		+ ", model10Name = '" + model10Name + "'"
			    		+ ", model11Name = '" + model11Name + "'"
			    		+ ", model12Name = '" + model12Name + "'"
			    		+ ", model13Name = '" + model13Name + "'"
			    		+ ", model14Name = '" + model14Name + "'"
			    		+ ", model15Name = '" + model15Name + "'"
					    + ", model16Name = '" + model16Name + "'"
			    		+ ", ok1Count = " + Integer.toString(Math.abs(ok1Count))
	    				+ ", ok2Count = " + Integer.toString(Math.abs(ok2Count))
	    				+ ", ok3Count = " + Integer.toString(Math.abs(ok3Count))
	    				+ ", ok4Count = " + Integer.toString(Math.abs(ok4Count))
	    				+ ", ok5Count = " + Integer.toString(Math.abs(ok5Count))
	    				+ ", ok6Count = " + Integer.toString(Math.abs(ok6Count))
	    				+ ", ok7Count = " + Integer.toString(Math.abs(ok7Count))
	    				+ ", ok8Count = " + Integer.toString(Math.abs(ok8Count))
	    				+ ", ok9Count = " + Integer.toString(Math.abs(ok9Count))
	    				+ ", ok10Count = " + Integer.toString(Math.abs(ok10Count))
	    				+ ", ok11Count = " + Integer.toString(Math.abs(ok11Count))
	    				+ ", ok12Count = " + Integer.toString(Math.abs(ok12Count))
	    				+ ", ok13Count = " + Integer.toString(Math.abs(ok13Count))
	    				+ ", ok14Count = " + Integer.toString(Math.abs(ok14Count))
	    				+ ", ok15Count = " + Integer.toString(Math.abs(ok15Count))
	    				+ ", ok16Count = " + Integer.toString(Math.abs(ok16Count))
	    				+ ", ng1Count = " + Integer.toString(Math.abs(ng1Count))
	    				+ ", ng2Count = " + Integer.toString(Math.abs(ng2Count))
	    				+ ", ng3Count = " + Integer.toString(Math.abs(ng3Count))
	    				+ ", ng4Count =  " + Integer.toString(Math.abs(ng4Count))
	    				+ ", ng5Count =  " + Integer.toString(Math.abs(ng5Count))
	    				+ ", ng6Count =  " + Integer.toString(Math.abs(ng6Count))
	    				+ ", ng7Count =  " + Integer.toString(Math.abs(ng7Count))
	    				+ ", ng8Count =  " + Integer.toString(Math.abs(ng8Count))
	    				+ ", ng9Count =  " + Integer.toString(Math.abs(ng9Count))
	    				+ ", ng10Count =  " + Integer.toString(Math.abs(ng10Count))
	    				+ ", ng11Count =  " + Integer.toString(Math.abs(ng11Count))
	    				+ ", ng12Count =  " + Integer.toString(Math.abs(ng12Count))
	    				+ ", ng13Count =  " + Integer.toString(Math.abs(ng13Count))
	    				+ ", ng14Count =  " + Integer.toString(Math.abs(ng14Count))
	    				+ ", ng15Count =  " + Integer.toString(Math.abs(ng15Count))
	    				+ ", ng16Count =  " + Integer.toString(Math.abs(ng16Count))
	    				+ " where machineID = '" + machineId + "'";
	
			    sqlStatement.executeUpdate(commandStringUD);
			    conn.close();
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	public static void dbUpdateLMMSClientVision()  //Update LMMSWatchDog And insert LMMSWatchLog
	{
		sqlStatement = null;
		
		try {
			conn = DriverManager.getConnection(connectionUrl);
			if (conn != null) {			
				
		    sqlStatement = conn.createStatement();
		    String commandString="insert into [LMMSClientVisionLog](machineId, partNumber, jobNumber, [datetime],totalGraphic,totalJig,passOk, failOk,graXYval1) values" +
		    "('" + machineId  + "','" + partNumber  + "','" + jobNumber  + "', getdate() ,'" + intTtlGraphic + "','" + intTtlJig + "','"  + strPassOK + "','" + strFailOK + "','" + strOverallGraphicJudge + "')";

		    //System.out.println("Insert LMMSClientVisionLog");
		    sqlStatement.executeUpdate(commandString);
	       //System.out.println(commandString);

		    String commandStringU="Update [LMMSClientVision] set [datetime] = getdate(),totalGraphic= '" + intTtlGraphic + "',totalJig = '" + intTtlJig + "',partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', passOk = '" + strPassOK + "',failok = '" + strFailOK + "', graXYval1 = '" + strOverallGraphicJudge + "' "
		    		+ " where machineID = '" + machineId + "'";
		   
		    //System.out.println("Update LMMSClientVision");
			  	    
		    sqlStatement.executeUpdate(commandStringU);   
		    //System.out.println(commandStringU);
		    String commandString1="insert into [LMMSClientGraphicMeasureLog](measureType, machineId, partNumber, jobNumber, [datetime],graJdgDst,graFndEdge) values" +
		    "('X','" + machineId  + "','" + partNumber  + "','" + jobNumber  + "', getdate() ,'" + strOverallGraphicJudgeDistanceX + "','" + strOverallGraphicJudgeFindEdgeX + "')";
		   
		    //System.out.println("Insert LMMSClientGraphicMeasureLog for X");
		    
		    sqlStatement.executeUpdate(commandString1);
		    //System.out.println(commandString1);
		    
		    commandString1="insert into [LMMSClientGraphicMeasureLog](measureType, machineId, partNumber, jobNumber, [datetime],graJdgDst,graFndEdge) values" +
		    "('Y','" + machineId  + "','" + partNumber  + "','" + jobNumber  + "', getdate() ,'" + strOverallGraphicJudgeDistanceY + "','" + strOverallGraphicJudgeFindEdgeY + "')";
		    
		    //System.out.println("Insert LMMSClientGraphicMeasureLog for Y");
		    sqlStatement.executeUpdate(commandString1);
		    //System.out.println(commandString1);
		    
		    String commandString1U="Update [LMMSClientGraphicMeasure] set [datetime] = getdate(),partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "' "
		    		+ ",graJdgDst = '" + strOverallGraphicJudgeDistanceX + "', graFndEdge = '" + strOverallGraphicJudgeFindEdgeX + "' "
		    		+ " where measureType = 'X' and machineID = '" + machineId + "'";

		    //System.out.println("Insert LMMSClientGraphicMeasure for Y");
		    
		    sqlStatement.executeUpdate(commandString1U);
		    //System.out.println(commandString1U); 
		    
		    commandString1U="Update [LMMSClientGraphicMeasure] set [datetime] = getdate(),partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "' "
		    		+ ",graJdgDst = '" + strOverallGraphicJudgeDistanceY + "', graFndEdge = '" + strOverallGraphicJudgeFindEdgeY + "' "
		    		+ " where measureType = 'Y' and machineID = '" + machineId + "'";
		   		    
		    //System.out.println("Insert LMMSClientGraphicMeasure for Y");
		    
		    sqlStatement.executeUpdate(commandString1U);
		    //System.out.println(commandString1U);
		    
		    conn.close();
		    
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static void dbUpdateLMMSClientVisionJigStatus()  //Update LMMSWatchDog And insert LMMSWatchLog
	{
		sqlStatement = null;
		
		try {
			
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			conn = DriverManager.getConnection(connectionUrl);
			//conn = DriverManager.getConnection(dbURL);
			if (conn != null) {			
				
		    //declare the statement object
		    sqlStatement = conn.createStatement();
		    
			
		    //Build the command string
		    String commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 1, jigStatus = '" + strJig1Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 1";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 2, jigStatus = '" + strJig2Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 2";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 3, jigStatus = '" + strJig3Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 3";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 4, jigStatus = '" + strJig4Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 4";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 5, jigStatus = '" + strJig5Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 5";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 6, jigStatus = '" + strJig6Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 6";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 7, jigStatus = '" + strJig7Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 7";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);
		    
		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 8, jigStatus = '" + strJig8Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 8";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 9, jigStatus = '" + strJig9Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 9";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 10, jigStatus = '" + strJig10Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 10";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 11, jigStatus = '" + strJig11Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 11";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 12, jigStatus = '" + strJig12Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 12";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 13, jigStatus = '" + strJig13Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 13";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);

		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 14, jigStatus = '" + strJig14Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 14";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 15, jigStatus = '" + strJig15Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 15";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);
		    
		    //Build the command string
		    commandStringJigStatus="Update [LMMSClientVisionJigStatus] set [datetime] = getdate(), modelName='" + modelName + "', partNumber = '" + partNumber  + "', jobNumber = '" + jobNumber  + "', totalJig = " + intTtlJig + ", jigSeqNum = 16, jigStatus = '" + strJig16Judge + "' "
		    		+ " where machineID = '" + machineId + "' and jigSeqNum = 16";
		    //execute the command using the execute method
		    sqlStatement.executeUpdate(commandStringJigStatus);
		    //System.out.println("Update LMMSClientVisionJigStatus"); 
		    //System.out.println(commandStringJigStatus);
		    //close the database connection
		    conn.close();
		    
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private static File getLastModifiedFile(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	public static void funToggleTest()
	{
	  Connection con = null;  
	  Statement stmt = null;  
	  ResultSet rs = null;  
	
	  try {  
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		 con = DriverManager.getConnection(connectionUrl);  
		 String SQL = "UPDATE [LMMSMachineIO] SET [inVal9]='" + Boolean.toString(XMLResSys.manualInspect)+ "' WHERE machineID='" + machineId + "'";
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




	public static Document parseXML(String filePath) throws ParserConfigurationException, SAXException, IOException
	{
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(filePath);
	    doc.getDocumentElement().normalize();
	    return doc;
	}
	
		//2018 04 25 by wei lijia , get model name from BOM table
		public static int dbGetLMMSWatch(String sMachineID, String sPartNo, String sJobNo)  //2018 04 25 by wei lijia for BOM information
		{
			System.out.println("1.CALL dbGetLMMSWatch :sPartNo = " + sPartNo + "; sJobNo = "+ sJobNo +";"  ); 
	        
			ResultSet rs = null;  
			sqlStatement = null;
			int iPartCount = Integer.MAX_VALUE;
			int i = 0;
			try {
				
				conn = DriverManager.getConnection(connectionUrl);
				if (conn != null) {
				    sqlStatement = conn.createStatement();
				    
				    String commandStringUL="select materialPartNo, partCount from  [LMMSBomDetail] " 
				    	+" where PartNumber = '" + sPartNo + "'" ; 
			        rs = sqlStatement.executeQuery(commandStringUL);  
			        
			        System.out.println("2. start read rs " ); 
			        
			        while (rs.next()) {
			        	
			        	// get min set qty per block
			        	if (iPartCount > rs.getInt(2))
			        	{
			        		iPartCount =  rs.getInt(2);
			        		System.out.println("2.1. get Min partCount = " + iPartCount  +";" );  
			        	}
			        	
			        	System.out.println("3. Row Number = " + i  +";" );  
				        
			        	i++;
			        	switch (i)
			        	{
				        	case(1):
				        	{
				        		model1Name =  rs.getString("materialPartNo");
				        		System.out.println("4.01. model1Name = " + model1Name  +";" );   
				        		break;
				        	}
				        	case(2):
				        	{
				        		model2Name =  rs.getString("materialPartNo");
				        		System.out.println("4.02. model2Name = " + model2Name  +";" );   
				        		break;
				        	}case(3):
				        	{
				        		model3Name =  rs.getString("materialPartNo");
				        		System.out.println("4.03. model3Name = " + model3Name  +";" ); 
				        		break;
				        	}case(4):
				        	{
				        		model4Name =  rs.getString("materialPartNo");
				        		System.out.println("4.4. model4Name = " + model4Name  +";" ); 
				        		break;
				        	}case(5):
				        	{
				        		model5Name =  rs.getString("materialPartNo");
				        		System.out.println("4.05. model5Name = " + model5Name  +";" ); 
				        		break;
				        	}case(6):
				        	{
				        		model6Name =  rs.getString("materialPartNo");
				        		System.out.println("4.06. model6Name = " + model6Name  +";" ); 
				        		break;
				        	}case(7):
				        	{
				        		model7Name =  rs.getString("materialPartNo");
				        		System.out.println("4.07. model7Name = " + model7Name  +";" ); 
				        		break;
				        	}case(8):
				        	{
				        		model8Name =  rs.getString("materialPartNo");
				        		System.out.println("4.08. model8Name = " + model8Name  +";" ); 
				        		break;
				        	}case(9):
				        	{
				        		model9Name =  rs.getString("materialPartNo");
				        		System.out.println("4.09. model9Name = " + model9Name  +";" ); 
				        		break;
				        	}case(10):
				        	{
				        		model10Name =  rs.getString("materialPartNo");
				        		System.out.println("4.10. model10Name = " + model10Name  +";" ); 
				        		break;
				        	}case(11):
				        	{
				        		model11Name =  rs.getString("materialPartNo");
				        		System.out.println("4.11. model11Name = " + model11Name  +";" ); 
				        		break;
				        	}case(12):
				        	{
				        		model12Name =  rs.getString("materialPartNo");
				        		System.out.println("4.12. model12Name = " + model12Name  +";" ); 
				        		break;
				        	}case(13):
				        	{
				        		model13Name =  rs.getString("materialPartNo");
				        		System.out.println("4.13. model13Name = " + model13Name  +";" ); 
				        		break;
				        	}case(14):
				        	{
				        		model14Name =  rs.getString("materialPartNo");
				        		System.out.println("4.14. model14Name = " + model14Name  +";" ); 
				        		break;
				        	}case(15):
				        	{
				        		model15Name =  rs.getString("materialPartNo");
				        		System.out.println("4.15. model15Name = " + model15Name  +";" ); 
				        		break;
				        	}case(16):
				        	{
				        		model16Name =  rs.getString("materialPartNo");
				        		System.out.println("4.16. model16Name = " + model16Name  +";" ); 
				        		break;
				        	}
			        	} 
			        }  
		
			        //close the database connection
			        conn.close(); 
			        System.out.println("5. Close Connection;" ); 
	        		
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
			System.out.println("6. return iPartCount" + iPartCount + ";" );  
			if ( iPartCount == Integer.MAX_VALUE ) 
				{
				return 0;
				}
			else
			{
				return iPartCount;
			}

		}
}
