import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Timer;
import java.util.TimerTask;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
//import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLResSys  extends DefaultHandler{
	public static JLabel lblInspect; 
	public static JLabel lblXMLpacket; 
	public static boolean manualInspect =false;
	public static JTextField machinenoField;
	public static JTextField connectionstrField;
	public static JTextField serverdirField;
	public static JPanel myPanel;
	public static JCheckBox chkUpdate;
	public static boolean enableTest = false;
	public static JLabel lblNew;

	public static void main (String[] args)
	{
		new XMLResSys();
		
		try {
			ConfigLog.getPropValues();
			RunSys.fileNameNow=RunSys.getLastModifiedFileName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RunSys task1 = new RunSys();
		Thread t1 = new Thread(task1);
		t1.start();
	
	}
	
	public XMLResSys(){
		JFrame guiFrame = new JFrame();
        guiFrame.setAlwaysOnTop(true);
        guiFrame.setUndecorated(true);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("LMMS: XML PARSER");
        guiFrame.setSize(80,100);
        guiFrame.setLocationRelativeTo(null);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = ((int) rect.getMaxX() - guiFrame.getWidth()) - 150;
        int y = 600;
        guiFrame.setLocation(x, y);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(128, 128, 128));
                
        guiFrame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        machinenoField = new JTextField(5);
    	connectionstrField = new JTextField(5);
    	serverdirField = new JTextField(5);
    	
    	myPanel  = new JPanel(new BorderLayout(5,5));
    	myPanel.setSize(122, 818);
    	
    	JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Machine-No", SwingConstants.RIGHT)); 		//1
        labels.add(new JLabel("Connection-Str", SwingConstants.RIGHT)); 	//2
        labels.add(new JLabel("Server-Dir", SwingConstants.RIGHT)); 		//3
        myPanel.add(labels, BorderLayout.WEST);
        
        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        controls.setSize(299,1854);
        
        machinenoField = new JTextField();								//1 ok
        controls.add(machinenoField);									//1 ok
        
        connectionstrField = new JTextField();							//2 ok
        controls.add(connectionstrField);								//2 ok
        
        serverdirField = new JTextField();								//3 ok
        controls.add(serverdirField);									//3 ok
        
        myPanel.add(controls, BorderLayout.CENTER);
        
        JPopupMenu popupMenu = new JPopupMenu();

        
        JMenuItem mntmManual = new JMenuItem("Manual");
        popupMenu.add(mntmManual);
        
        JMenuItem mntmSettings = new JMenuItem("Settings");
        popupMenu.add(mntmSettings);
        
        JMenuItem mntmVer = new JMenuItem("Ver.1.1");
        popupMenu.add(mntmVer);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        popupMenu.add(mntmExit);
        

        JButton button = new JButton("...");
        button.setFont(new Font("Century Gothic", Font.BOLD, 9));
        button.setBounds(0, 0, 80, 21);
        panel.add(button);
        
        JButton buttonTest = new JButton("INSPECT-OFF");
        buttonTest.setFont(new Font("Century Gothic", Font.BOLD, 7));
        buttonTest.setBounds(0, 20, 80, 21);
        panel.add(buttonTest);
        
        JButton buttonTest2 = new JButton("TEST-OFF");
        buttonTest2.setFont(new Font("Century Gothic", Font.BOLD, 7));
        buttonTest2.setBounds(0, 40, 80, 21);
        panel.add(buttonTest2);
        
        lblInspect = new JLabel("FALSE");
        lblInspect.setForeground(Color.WHITE);
        lblInspect.setBounds(0, 23, 80, 14);
        panel.add(lblInspect);
        lblInspect.setBackground(Color.PINK);
        
        
        lblXMLpacket = new JLabel(".xml");
        lblXMLpacket.setForeground(Color.WHITE);
        lblXMLpacket.setBounds(0, 60, 80, 14);
        panel.add(lblXMLpacket);
        lblXMLpacket.setBackground(Color.PINK);
        
        lblNew = new JLabel("COLLECT");
        lblNew.setForeground(Color.WHITE);
        lblNew.setBounds(0, 80, 80, 14);
        panel.add(lblNew);
        lblNew.setBackground(Color.PINK);
        
        button.addMouseListener(new MouseAdapter() {
        	
        	public void mouseClicked(MouseEvent ae) {
        		
        		showPopup(ae, popupMenu);
        	}
        });
        
        buttonTest.addMouseListener(new MouseAdapter() {
        	
        	public void mouseClicked(MouseEvent ae) {
        		
        		if(!manualInspect)
        		{
        			RunSys.processCheck = true;
        			manualInspect = true;
        			buttonTest.setText("INSPECT-ON");
        			RunSys.funToggleTest();
        			
        			manualInspect = false;
        			buttonTest.setText("INSPECT-OFF");
        			RunSys.funToggleTest();
        			RunSys.processCheck = false;
        		}
        		
        	}
        });
        
        buttonTest2.addMouseListener(new MouseAdapter() {
        	
        	public void mouseClicked(MouseEvent ae) {
        		
        		if(!manualInspect)
        		{
        			enableTest =true;
        			RunSys.processCheck = true;
        			manualInspect = true;
        			buttonTest2.setText("TEST-ON");
        			RunSys.funToggleTest();
        			
        			manualInspect = false;
        			buttonTest2.setText("TEST-OFF");
        			RunSys.funToggleTest();
        			RunSys.processCheck = false;
        			enableTest = false;
        		}
        		
        	}
        });
        
        ActionListener manualPanel=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	//RunSys.testXMLLearn();
            	//selectManual();
                //showPopup(ae, popupMenu);
            }
        };
        
        ActionListener settingPanel=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	selectSetting();
                //showPopup(ae, popupMenu);
            }
        };
        
        ActionListener infoPanel=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	//selectVer();
                //showPopup(ae, popupMenu);
            }
        };
        
        ActionListener exitEvent=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	System.exit(0);
            }
        };
        
        mntmSettings.addActionListener(settingPanel);
        mntmManual.addActionListener(manualPanel);
        mntmVer.addActionListener(infoPanel);
        mntmExit.addActionListener(exitEvent);
        
        guiFrame.setVisible(true);
	}
	
	private static void selectSetting()
	{
		
		int result = JOptionPane.showConfirmDialog(null, myPanel,
	            "Settings | LMMS | Vision", JOptionPane.OK_CANCEL_OPTION);
	        if (result == JOptionPane.OK_OPTION) {
	          //System.out.println("x value: " + xField.getText());
	          //System.out.println("y value: " + yField.getText());
	        	try {
					ConfigLog.updateDatePropValues();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	private void showPopup(MouseEvent ae, JPopupMenu xd)
    {
        Component b=(Component)ae.getSource();
        java.awt.Point p=b.getLocationOnScreen();
        xd.show(b, 0, 0);;
        xd.setLocation(p.x,p.y+b.getHeight());
    }
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}