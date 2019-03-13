package com.lmms.vision;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import com.lmms.vision.MainClient;
 
public class ConfigLog {
	String result = "";
	InputStream inputStream;
	
	public static String machinenoSet;
	public static String connectionstrSet;
	public static String serverdirSet;
	public static String visiondirSet;
	public static String visionloadSet;
	public static String selectmodelSet;
	public static String windowmodelSet;
	public static String selectpartmodelSet;
	public static String clickokmodelSet;
	public static String menuvisionSet;
	public static String runenableSet;
	public static String passiconSet;
	public static String failiconSet;
	public static String inspectiontextSet;
	public static String passpointSet;
	public static String failpointSet;
	public static String resetpointSet;
	public static String readingFilePath;
	public static String backupPath;
	
	public static void getPropValues() throws IOException {
		 
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			machinenoSet = prop.getProperty("machinenoSet");
			connectionstrSet = prop.getProperty("connectionstrSet");
			serverdirSet = prop.getProperty("serverdirSet");
			visiondirSet = prop.getProperty("visiondirSet");
			visionloadSet = prop.getProperty("visionloadSet");
			selectmodelSet = prop.getProperty("selectmodelSet");
			windowmodelSet = prop.getProperty("windowmodelSet");
			selectpartmodelSet = prop.getProperty("selectpartmodelSet");
			clickokmodelSet = prop.getProperty("clickokmodelSet");
			menuvisionSet = prop.getProperty("menuvisionSet");
			runenableSet = prop.getProperty("runenableSet");
			passiconSet = prop.getProperty("passiconSet");
			failiconSet = prop.getProperty("failiconSet");
			inspectiontextSet = prop.getProperty("inspectiontextSet");
			passpointSet = prop.getProperty("passpointSet");
			failpointSet = prop.getProperty("failpointSet");
			resetpointSet = prop.getProperty("resetpointSet"); 
			readingFilePath = prop.getProperty("readingFilePath");
			backupPath = prop.getProperty("backupPath");

			/*
			 * MainClient.machinenoField.setText(machinenoSet);
			 * MainClient.connectionstrField.setText(connectionstrSet);
			 * MainClient.serverdirField.setText(serverdirSet);
			 * MainClient.visiondirField.setText(visiondirSet);
			 * MainClient.visionloadField.setText(visionloadSet);
			 * MainClient.selectmodelField.setText(selectmodelSet);
			 * MainClient.windowmodelField.setText(windowmodelSet);
			 * MainClient.selectpartmodelField.setText(selectpartmodelSet);
			 * MainClient.clickokmodelField.setText(clickokmodelSet);
			 * MainClient.menuvisionField.setText(menuvisionSet);
			 * MainClient.runenableField.setText(runenableSet);
			 * MainClient.passiconField.setText(passiconSet);
			 * MainClient.failiconField.setText(failiconSet);
			 * MainClient.inspectiontextField.setText(inspectiontextSet);
			 * MainClient.passpointField.setText(passpointSet);
			 * MainClient.failpointField.setText(failpointSet);
			 * MainClient.resetpartField.setText(resetpointSet);
			 */

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
	}
	
	public static void updateDatePropValues() throws IOException
	{
		
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("config.properties");

			prop.setProperty("machinenoSet", MainClient.machinenoField.getText() );
			prop.setProperty("connectionstrSet", MainClient.connectionstrField.getText() );
			prop.setProperty("serverdirSet", MainClient.serverdirField.getText() );
			prop.setProperty("visiondirSet", MainClient.visiondirField.getText() );
			prop.setProperty("visionloadSet", MainClient.visionloadField.getText());
			prop.setProperty("selectmodelSet", MainClient.selectmodelField.getText());
			prop.setProperty("windowmodelSet", MainClient.windowmodelField.getText());
			prop.setProperty("selectpartmodelSet", MainClient.selectpartmodelField.getText());
			prop.setProperty("clickokmodelSet", MainClient.clickokmodelField.getText());
			prop.setProperty("menuvisionSet", MainClient.menuvisionField.getText());
			prop.setProperty("runenableSet", MainClient.runenableField.getText());
			prop.setProperty("passiconSet", MainClient.passiconField.getText());
			prop.setProperty("failiconSet", MainClient.failiconField.getText());
			prop.setProperty("inspectiontextSet", MainClient.inspectiontextField.getText());
			prop.setProperty("passpointSet", MainClient.passpointField.getText());
			prop.setProperty("failpointSet", MainClient.failpointField.getText());
			prop.setProperty("resetpointSet", MainClient.resetpartField.getText());
			
			prop.store(output, null);

		} catch (IOException io) {
			//io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}

		}
	}
}
