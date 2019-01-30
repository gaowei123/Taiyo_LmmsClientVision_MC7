package com.lmms.vision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

public class LoadDir {
	public static void funCopyFileFromLMMS()
	{
		File inFile = new File( "\\\\AFIFIUBCT\\server\\PARTVISION\\");
		File[] listOfFiles = inFile.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	        File servFile = new File("\\\\AFIFIUBCT\\server\\PARTVISION\\" + listOfFiles[i].getName());
	        File outFile = new File("C:\\data\\PARTVISION\\" + listOfFiles[i].getName());
	        try {
	        	funcopyDirectory(servFile,outFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	      }
	        
	    }

	
	}
	    
    public static void funcopyDirectory(File sourceLocation , File targetLocation) throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                funcopyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }
    
    public static void funReadPartSPT()
    {
    	try{
    	   Wini ini;
		   /* Load the ini file. */
		   ini = new Wini(new File("C:\\data\\VISION\\"));
		   
		   System.out.println("Number of sections: "+ini.size()+"\n");
	        for (String sectionName: ini.keySet()) {
	            System.out.println("["+sectionName+"]");
	            Section section = ini.get(sectionName);
	            for (String optionKey: section.keySet()) {
	                System.out.println("\t"+optionKey+"="+section.get(optionKey));
	            }
	        }
		   
		   
		   /* Extract the window color value.*/
//		   int windowColor = ini.get("main", "window-color", int.class);
//		   /* Extract the splash screen status. */
//		   boolean splashScreen = ini.get("main", "splash", boolean.class);
//		   
//		   /* Show to user the output. */
//		   System.out.println("Your default window color is: " + windowColor);
//		   if(splashScreen){
//		    System.out.println("You have your splash screen activated.");
//		   }else{
//		    System.out.println("You have your splash disabled.");
//		   }
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
}
