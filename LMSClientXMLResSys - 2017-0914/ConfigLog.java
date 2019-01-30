
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
 
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

			XMLResSys.machinenoField.setText(machinenoSet);
			XMLResSys.connectionstrField.setText(connectionstrSet);
			XMLResSys.serverdirField.setText(serverdirSet);
			

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

			prop.setProperty("machinenoSet", XMLResSys.machinenoField.getText() );
			prop.setProperty("connectionstrSet", XMLResSys.connectionstrField.getText() );
			prop.setProperty("serverdirSet", XMLResSys.serverdirField.getText() );
			
			
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
