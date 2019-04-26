package com.lmms.vision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.python.modules.gc;
import org.sikuli.script.*;
import net.sourceforge.tess4j.*;
import org.sikuli.basics.Settings;

public  class CommonFunc {
	
	
	//2018 11 19 by Wei LiJia for log file.
	public static void writeLogFile(String sLog)
	{ 
		 try {
				//check backup folder whether exist   if not create
				String BackupPath = ConfigLog.visiondirSet;   //default is c:\data\
				File file =new File(BackupPath);    
				
				if(!file.exists()  && !file.isDirectory())
				{       
				    file .mkdir();    
				}
				
				//check today backup folder whether exist   if not create
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String TodayFolder = BackupPath +  sdf.format(new Date());
				File file2 =new File(TodayFolder);    
				
				if(!file2.exists()  && !file2.isDirectory())
				{       
					file2 .mkdir();    
				}
			 
		        String  LogfilePath =  TodayFolder +"\\\\"+ "Log.txt" ; 
		        String  strLog =   new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()) ;
		        strLog = "["  + strLog + "]" + sLog + "\r\n";
		        Files.write(Paths.get(LogfilePath ) , strLog.getBytes() , StandardOpenOption.CREATE , StandardOpenOption.APPEND  );
		        
		} catch (Exception e1) {
			System.out.println("writeLogFile Exception--"+e1.toString());
		}
   }
	
	
	
	public static void BackUpFile () throws Exception 
	{
		String currFilePath = ConfigLog.readingFilePath;
		
		if(ConfigLog.machinenoSet.equals("6") || ConfigLog.machinenoSet.equals("8")) 
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String StrDay = df.format(new Date());
			currFilePath += StrDay +"\\";
		}else if(ConfigLog.machinenoSet.equals("7")) {
			//nothing
		}
		
		File dir = new File(currFilePath);
		if(!dir.exists()  && !dir.isDirectory())
		{       
			dir .mkdir();
		}
		
		
		//No file in folder return
		File[] files = dir.listFiles();
		if(files.length == 0)
			return;
		
		
		//check backup folder whether exist   if not create
		String BackupPath = ConfigLog.backupPath;
		File file =new File(BackupPath);
		if(!file.exists()  && !file.isDirectory())
		{       
		    file .mkdir();    
		}
		
		
		//check today backup folder whether exist   if not create
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String BackUp_TodayFolder = BackupPath + sdf.format(new Date());
		File file2 =new File(BackUp_TodayFolder);
		
		if(!file2.exists()  && !file2.isDirectory())
		{       
			file2 .mkdir();
		}
		
		
		//copy file
		copyFile(currFilePath,BackUp_TodayFolder);
		
		//delete file
		delFile(currFilePath);
		
	}
	
	public static void copyFile(String FileFromPath, String FileToPath) throws IOException
	{
		//nothing return
		File FileFrom = new File(FileFromPath);
		if(!FileFrom.exists()  && !FileFrom.isDirectory())
			 return;
		
		
        File FileTo = new File(FileToPath);
        if(!FileTo.exists()  && !FileTo.isDirectory()) {
        	FileTo.mkdir();
        }
        
        
        File[] AllTxtFile = FileFrom.listFiles();
        
        for(File file : AllTxtFile) {
        	
        	if(file.isDirectory())
        		continue;
        	
        	FileInputStream in = new FileInputStream(file);
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        	String FileToName = FileTo + "\\\\" + file.getName().split("\\.")[0] + "_" + sdf.format(new Date()) + ".txt";
            FileOutputStream out = new FileOutputStream(FileToName);
        	
            byte[] buffer=new byte[10240];//10k
            int readByte = 0;
            while((readByte = in.read(buffer)) != -1){
                out.write(buffer, 0, readByte);
            }
        
            in.close();
            out.close();
        }
    }
	
	public static void delFile(String path) throws InterruptedException 
	 {
		File delPath = new File(path);
		if(!delPath.exists()  && !delPath.isDirectory())
			return ;
		
		 
		File[] delFiles = delPath.listFiles();
		
		for(File file : delFiles) {
			
			if(file.isDirectory())
				continue;
		
			Boolean Result = file.delete();
			
			if(Result) {
				Thread.sleep(1000);
				gc.collect();
				Thread.sleep(1000);
				
				file.delete();
			}
			
		}
		 
		return;
	 }
	
	public static File getLastModifiedFile(String dirPath){
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
	
}
