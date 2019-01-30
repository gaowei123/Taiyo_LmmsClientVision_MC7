package com.lmms.vision;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JButton;
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
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.sikuli.basics.Settings;
import org.sikuli.script.Screen;

import com.lmms.vision.*;
import com.tigervnc.rfb.Point;

import org.sikuli.script.App;
import org.sikuli.script.Key;
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

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
//import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainClient {

	public static JLabel jlabelFrame;
	public static Label lblstatVision; 
	public static Label lblStat;
	public static Label lblStat2;
	public static Label lblStat3;
	public static Label lblStat4;
	public static Label lblAdam;
	public static Label lblSig1;
	public static Label lblSig2;
	public static Label lblSig3;
	public static Label lblSig4;
	public static Label lblSig5;
	public static Label lblSig6;
	public static Label lblSig7;
	public static Label lblSig8;
	public static Label lblSig9;
	public static Label lblSig10;
	public static Label lblstatFail;
	public static Label lblstatPass;
	public static Label lblstatInspect;
	public static JLabel lblTotalFail;
	public static JLabel lblTotalPass;
	public static JLabel lblProcess;
	public static JLabel lbljobNumber;
	public static JLabel lblpartNumber;
	public static Label lblRunning ;
	public static Label lblRMS;
	public static JLabel lblCQuantity;
	public static JLabel lblQuantity;
	
	public static JTextField machinenoField;
	public static JTextField connectionstrField;
	public static JTextField serverdirField;
	public static JTextField visiondirField;
	public static JTextField visionloadField;
	public static JTextField selectmodelField;
	public static JTextField windowmodelField;
	public static JTextField selectpartmodelField;
	public static JTextField clickokmodelField;
	public static JTextField menuvisionField;
	public static JTextField runenableField;
	public static JTextField passiconField;
	public static JTextField failiconField;
	public static JTextField inspectiontextField;
	public static JTextField passpointField;
	public static JTextField failpointField;
	public static JTextField quantityField;
	public static JTextField resetpartField;
	public static JTextArea errorInfo;
	
	public static JPanel myPanel;
	public static JPanel myPanel2;
	public static JPanel myPanel3;
	public static JPanel myPanel4;
	
	private JLabel lbl1;
	private JLabel lblC;
	public static Label lblCompleteStatus;
	public static JLabel lblTotalQuantity;
	public static JComboBox selectInputTechnician;
	public static JButton btnReset;
	public static Boolean resetClick = false;
	
	public static void main (String[] args) throws AWTException {
		try {
			
			new MainClient();
			
			ConfigLog.getPropValues();
			
			
			//Set Background cover
			String CoverPath = "";
	        if(ConfigLog.machinenoSet.equals("6")) {
	        	CoverPath = ".\\TAIYO\\lmms_vision_back_no6.jpg";
	        }else if(ConfigLog.machinenoSet.equals("7")) {
	        	CoverPath = ".\\TAIYO\\lmms_vision_back_no7.jpg";
	        }else if(ConfigLog.machinenoSet.equals("8")) {
	        	CoverPath = ".\\TAIYO\\lmms_vision_back_no8.jpg";
	        }else {CoverPath = ".\\TAIYO\\lmms_vision_back_no8.jpg"; }
			
	        jlabelFrame.setIcon(new ImageIcon(CoverPath));
	        //Set Background cover
			
	        workerSystem();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void workerSystem()
	{
		//LoadVision.scr = new Screen();
		//RunSys task1 = new RunSys();
		//Thread t1 = new Thread(task1);
		//t1.start();
		
		RunInspection task2 = new RunInspection();
		Thread t2 = new Thread(task2);
	    t2.start();
		
	    RunDB task3 = new RunDB();
        Thread t3 = new Thread(task3);
        t3.start();
	}
	
	
	private void showPopup(ActionEvent ae, JPopupMenu xd)
    {
        Component b=(Component)ae.getSource();
        java.awt.Point p=b.getLocationOnScreen();
        xd.show(b, 0, 0);;
        xd.setLocation(p.x,p.y+b.getHeight());
    }
	public MainClient()
    {
        JFrame guiFrame = new JFrame();
        guiFrame.setAlwaysOnTop(true);
        guiFrame.setUndecorated(true);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("LMMS: Laser Client");
        guiFrame.setSize(1129,42);
        guiFrame.setLocationRelativeTo(null);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - guiFrame.getWidth();
        int y = 0;
        guiFrame.setLocation(x, y);

        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(65, 105, 225));
        
        
        guiFrame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        lblpartNumber = new JLabel("{PART NUMBER}");
        lblpartNumber.setForeground(Color.WHITE);
        lblpartNumber.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblpartNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblpartNumber.setBounds(138, 0, 169, 28);
        panel.add(lblpartNumber);
        
        lbljobNumber = new JLabel("{JOB NUMBER}");
        lbljobNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lbljobNumber.setForeground(Color.WHITE);
        lbljobNumber.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lbljobNumber.setBounds(357, 0, 161, 28);
        panel.add(lbljobNumber);
        
        lblProcess = new JLabel("{PROCESS}");
        lblProcess.setHorizontalAlignment(SwingConstants.CENTER);
        lblProcess.setForeground(Color.WHITE);
        lblProcess.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblProcess.setBounds(578, 0, 90, 28);
        panel.add(lblProcess);
        
        lblTotalPass = new JLabel("{PASS}");
        lblTotalPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPass.setForeground(Color.WHITE);
        lblTotalPass.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalPass.setBounds(726, 0, 68, 28);
        panel.add(lblTotalPass);
        
        lblTotalFail = new JLabel("{FAIL}");
        lblTotalFail.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFail.setForeground(Color.WHITE);
        lblTotalFail.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalFail.setBounds(852, 0, 60, 28);
        panel.add(lblTotalFail);
        
        lblCQuantity = new JLabel("1000000");
        lblCQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCQuantity.setForeground(Color.WHITE);
        lblCQuantity.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblCQuantity.setBounds(950, 15, 42, 12);
        panel.add(lblCQuantity);
        
        lblQuantity = new JLabel("1000000");
        lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuantity.setForeground(Color.WHITE);
        lblQuantity.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblQuantity.setBounds(950, 3, 45, 12);
        panel.add(lblQuantity);
        
        lblTotalQuantity = new JLabel("1000000");
        lblTotalQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalQuantity.setForeground(Color.WHITE);
        lblTotalQuantity.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblTotalQuantity.setBounds(1016, 3, 63, 25);
        panel.add(lblTotalQuantity);
        
        lbl1 = new JLabel("T:");
        lbl1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lbl1.setBounds(940, 3, 16, 12);
        panel.add(lbl1);
        
        lblC = new JLabel("C:");
        lblC.setHorizontalAlignment(SwingConstants.LEFT);
        lblC.setForeground(Color.WHITE);
        lblC.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblC.setBounds(938, 15, 16, 12);
        panel.add(lblC);
        
        JButton btnX = new JButton("...");
        btnX.setFont(new Font("Century Gothic", Font.BOLD, 9));
        btnX.setBounds(1082, 1, 47, 29);
        panel.add(btnX);
        
        JPopupMenu popupMenu = new JPopupMenu();
        addPopup(btnX, popupMenu);
        
        JMenuItem mntmManual = new JMenuItem("Manual");
        popupMenu.add(mntmManual);
        
        JMenuItem mntmSettings = new JMenuItem("Settings");
        popupMenu.add(mntmSettings);
        
        JMenuItem mntmVer = new JMenuItem("Ver.1.1");
        popupMenu.add(mntmVer);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        popupMenu.add(mntmExit);
        
        ActionListener a1=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                showPopup(ae, popupMenu);
            }
        };
        
        ActionListener manualPanel=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	selectManual();
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
            	selectVer();
                //showPopup(ae, popupMenu);
            }
        };
        
        ActionListener exitEvent=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	System.exit(0);
            }
        };
        
        
        btnX.addActionListener(a1);
        mntmSettings.addActionListener(settingPanel);
        mntmManual.addActionListener(manualPanel);
        mntmVer.addActionListener(infoPanel);
        mntmExit.addActionListener(exitEvent);
        
        lblstatVision = new Label("VISOFT OK");
        lblstatVision.setAlignment(Label.CENTER);
        lblstatVision.setForeground(new Color(255, 255, 255));
        lblstatVision.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblstatVision.setBackground(new Color(105, 105, 105));
        lblstatVision.setBounds(1061, 28, 68, 13);
        panel.add(lblstatVision);
        
        lblstatFail = new Label("FAIL");
        lblstatFail.setForeground(Color.WHITE);
        lblstatFail.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblstatFail.setBackground(SystemColor.controlDkShadow);
        lblstatFail.setAlignment(Label.CENTER);
        lblstatFail.setBounds(964, 28, 33, 13);
        panel.add(lblstatFail);
        
        lblstatPass = new Label("PASS");
        lblstatPass.setForeground(Color.WHITE);
        lblstatPass.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblstatPass.setBackground(SystemColor.controlDkShadow);
        lblstatPass.setAlignment(Label.CENTER);
        lblstatPass.setBounds(929, 28, 33, 13);
        panel.add(lblstatPass);
        
        lblstatInspect = new Label("INSPECT");
        lblstatInspect.setForeground(Color.WHITE);
        lblstatInspect.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblstatInspect.setBackground(SystemColor.controlDkShadow);
        lblstatInspect.setAlignment(Label.CENTER);
        lblstatInspect.setBounds(817, 28, 50, 13);
        panel.add(lblstatInspect);
        
        lblCompleteStatus = new Label("JOB COMPLETE!");
        lblCompleteStatus.setForeground(Color.WHITE);
        lblCompleteStatus.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblCompleteStatus.setBackground(SystemColor.controlDkShadow);
        lblCompleteStatus.setAlignment(Label.CENTER);
        lblCompleteStatus.setBounds(82, 28, 303, 13);
        lblCompleteStatus.setVisible(false);
        panel.add(lblCompleteStatus);
       
        
        lblStat = new Label("RS:000");
        lblStat.setForeground(Color.WHITE);
        lblStat.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblStat.setBackground(SystemColor.controlDkShadow);
        lblStat.setAlignment(Label.CENTER);
        lblStat.setBounds(387, 28, 50, 13);
        panel.add(lblStat);
         
	    lblStat2 = new Label("RL:000");
	    lblStat2.setForeground(Color.WHITE);
	    lblStat2.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    lblStat2.setBackground(SystemColor.controlDkShadow);
	    lblStat2.setAlignment(Label.CENTER);
	    lblStat2.setBounds(439, 28, 50, 13);
	    panel.add(lblStat2);
	     
	    lblStat3 = new Label("RD:000");
	    lblStat3.setForeground(Color.WHITE);
	    lblStat3.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    lblStat3.setBackground(SystemColor.controlDkShadow);
	    lblStat3.setAlignment(Label.CENTER);
	    lblStat3.setBounds(491, 28, 50, 13);
	    panel.add(lblStat3);
        
        lblStat4 = new Label("RI:000");
        lblStat4.setForeground(Color.WHITE);
        lblStat4.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblStat4.setBackground(SystemColor.controlDkShadow);
        lblStat4.setAlignment(Label.CENTER);
        lblStat4.setBounds(543, 28, 50, 13);
        panel.add(lblStat4);
        
        lblAdam = new Label("ADAM OK");
        lblAdam.setForeground(Color.WHITE);
        lblAdam.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblAdam.setBackground(SystemColor.controlDkShadow);
        lblAdam.setAlignment(Label.CENTER);
        lblAdam.setBounds(999, 28, 60, 13);
        panel.add(lblAdam);
        
        lblSig1 = new Label("ST");
        lblSig1.setForeground(Color.WHITE);
        lblSig1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig1.setBackground(SystemColor.controlDkShadow);
        lblSig1.setAlignment(Label.CENTER);
        lblSig1.setBounds(595, 28, 15, 13);
        panel.add(lblSig1);
        
        lblSig2 = new Label("RT");
        lblSig2.setForeground(Color.WHITE);
        lblSig2.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig2.setBackground(SystemColor.controlDkShadow);
        lblSig2.setAlignment(Label.CENTER);
        lblSig2.setBounds(611, 28, 15, 13);
        panel.add(lblSig2);
        
        lblSig3 = new Label("AM");
        lblSig3.setForeground(Color.WHITE);
        lblSig3.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig3.setBackground(SystemColor.controlDkShadow);
        lblSig3.setAlignment(Label.CENTER);
        lblSig3.setBounds(627, 28, 15, 13);
        panel.add(lblSig3);
        
        lblSig4 = new Label("SP");
        lblSig4.setForeground(Color.WHITE);
        lblSig4.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig4.setBackground(SystemColor.controlDkShadow);
        lblSig4.setAlignment(Label.CENTER);
        lblSig4.setBounds(643, 28, 15, 13);
        panel.add(lblSig4);
        
        lblSig5 = new Label("PS");
        lblSig5.setForeground(Color.WHITE);
        lblSig5.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig5.setBackground(SystemColor.controlDkShadow);
        lblSig5.setAlignment(Label.CENTER);
        lblSig5.setBounds(659, 28, 15, 13);
        panel.add(lblSig5);
        
        lblSig6 = new Label("CN");
        lblSig6.setForeground(Color.WHITE);
        lblSig6.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig6.setBackground(SystemColor.controlDkShadow);
        lblSig6.setAlignment(Label.CENTER);
        lblSig6.setBounds(675, 28, 15, 13);
        panel.add(lblSig6);
        
        lblSig7 = new Label("TK");
        lblSig7.setForeground(Color.WHITE);
        lblSig7.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig7.setBackground(SystemColor.controlDkShadow);
        lblSig7.setAlignment(Label.CENTER);
        lblSig7.setBounds(691, 28, 15, 13);
        panel.add(lblSig7);
        
        lblSig8 = new Label("SR");
        lblSig8.setForeground(Color.WHITE);
        lblSig8.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig8.setBackground(SystemColor.controlDkShadow);
        lblSig8.setAlignment(Label.CENTER);
        lblSig8.setBounds(707, 28, 15, 13);
        panel.add(lblSig8);
        
        lblSig9 = new Label("RG");
        lblSig9.setForeground(Color.WHITE);
        lblSig9.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig9.setBackground(SystemColor.controlDkShadow);
        lblSig9.setAlignment(Label.CENTER);
        lblSig9.setBounds(723, 28, 15, 13);
        panel.add(lblSig9);
        
        lblSig10 = new Label("PY");
        lblSig10.setForeground(Color.WHITE);
        lblSig10.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig10.setBackground(SystemColor.controlDkShadow);
        lblSig10.setAlignment(Label.CENTER);
        lblSig10.setBounds(739, 28, 15, 13);
        panel.add(lblSig10);
        
        lblRunning = new Label("RUNNING");
        lblRunning.setForeground(Color.WHITE);
        lblRunning.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblRunning.setBackground(SystemColor.controlDkShadow);
        lblRunning.setAlignment(Label.CENTER);
        lblRunning.setBounds(868, 28, 60, 13);
        panel.add(lblRunning);
        
        lblRMS = new Label("loadVision");
        lblRMS.setForeground(Color.WHITE);
        lblRMS.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblRMS.setBackground(SystemColor.controlDkShadow);
        lblRMS.setAlignment(Label.CENTER);
        lblRMS.setBounds(756, 28, 60, 13);
        panel.add(lblRMS);
        
        machinenoField = new JTextField(5);
    	connectionstrField = new JTextField(5);
    	serverdirField = new JTextField(5);
    	visiondirField = new JTextField(5);
    	visionloadField = new JTextField(5);
    	selectmodelField = new JTextField(5);
    	windowmodelField = new JTextField(5);
    	selectpartmodelField = new JTextField(5);
    	clickokmodelField = new JTextField(5);
    	menuvisionField = new JTextField(5);
    	runenableField = new JTextField(5);
    	passiconField = new JTextField(5);
    	failiconField = new JTextField(5);
    	inspectiontextField = new JTextField(5);
    	passpointField = new JTextField(5);
    	failpointField = new JTextField(5);
    	resetpartField = new JTextField(5);
    	
    	myPanel  = new JPanel(new BorderLayout(5,5));
    	myPanel.setSize(2000,200);
    	
    	JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Machine-No", SwingConstants.RIGHT)); 		//1
        labels.add(new JLabel("Connection-Str", SwingConstants.RIGHT)); 	//2
        labels.add(new JLabel("Server-Dir", SwingConstants.RIGHT)); 		//3
        labels.add(new JLabel("Vision-Dir", SwingConstants.RIGHT)); 		//4
        labels.add(new JLabel("VisionLoad-Img", SwingConstants.RIGHT)); 	//5
        labels.add(new JLabel("SelectModel-Time", SwingConstants.RIGHT)); 	//6
        labels.add(new JLabel("WindowModel-Img", SwingConstants.RIGHT)); 	//7
        labels.add(new JLabel("ClickOk-Img", SwingConstants.RIGHT)); 		//9
        labels.add(new JLabel("MenuVision-Img", SwingConstants.RIGHT)); 	//10
        labels.add(new JLabel("RunEnable-Img", SwingConstants.RIGHT)); 		//11
        labels.add(new JLabel("PassIcon-Img", SwingConstants.RIGHT)); 		//12
        labels.add(new JLabel("FailIcon-Img", SwingConstants.RIGHT)); 		//13
        labels.add(new JLabel("InspectText-Img ", SwingConstants.RIGHT));	//14
        labels.add(new JLabel("PassPoint-Img", SwingConstants.RIGHT)); 		//15
        labels.add(new JLabel("FailPoint-Img", SwingConstants.RIGHT)); 		//16
        labels.add(new JLabel("Reset-Img", SwingConstants.RIGHT)); 			//17
        myPanel.add(labels, BorderLayout.WEST);
        
        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
        controls.setSize(300, 200);
        
        machinenoField = new JTextField();								//1 ok
        controls.add(machinenoField);									//1 ok
        
        connectionstrField = new JTextField();							//2 ok
        controls.add(connectionstrField);								//2 ok
        
        serverdirField = new JTextField();								//3 ok
        controls.add(serverdirField);									//3 ok
        
        visiondirField = new JTextField();								//4 ok
        controls.add(visiondirField);									//4 ok
        
        visionloadField = new JTextField();								//5 ok
        controls.add(visionloadField);									//5 ok
        
        selectmodelField = new JTextField();							//6 ok
        controls.add(selectmodelField);									//6 ok
        
        clickokmodelField = new JTextField();							//7 ok
        controls.add(clickokmodelField);								//7 ok
        
        windowmodelField = new JTextField();							//8 ok
        controls.add(windowmodelField);									//8 ok
        
        menuvisionField = new JTextField();								//9 ok
        controls.add(menuvisionField);									//9 ok
        
        runenableField = new JTextField();								//10 ok
        controls.add(runenableField);									//10 ok
        
        passiconField = new JTextField();								//11 ok
        controls.add(passiconField);									//11 ok
        
        failiconField = new JTextField();								//12 ok
        controls.add(failiconField);									//12 ok
        
        inspectiontextField = new JTextField();							//13 ok
        controls.add(inspectiontextField);								//14 ok
        
        passpointField = new JTextField();								//15 ok
        controls.add(passpointField);									//15 ok
        
        failpointField = new JTextField();								//16 ok
        controls.add(failpointField);									//16 ok
        
        resetpartField = new JTextField();								//17 ok
        controls.add(resetpartField);									//17 ok
        
        myPanel.add(controls, BorderLayout.CENTER);
        
        myPanel2  = new JPanel(new BorderLayout(5,5));
    	myPanel2.setSize(2000,200);

        JPanel labels2 = new JPanel(new GridLayout(0,1,2,2));
        labels2.add(new JLabel("Part-No", SwingConstants.RIGHT)); //1
        labels2.add(new JLabel("Job-Str", SwingConstants.RIGHT)); //1
        labels2.add(new JLabel("Quantity", SwingConstants.RIGHT)); //2
        
        JPanel controls2 = new JPanel(new GridLayout(0,1,2,2));
        controls2.setSize(300, 200);
        
        quantityField = new JTextField();
        quantityField.addAncestorListener( new RequestFocusListener() );


        quantityField.setFont(new Font("Century Gothic", Font.BOLD, 50));
        controls2.add(quantityField);
        
        myPanel2.add(controls2, BorderLayout.CENTER);
        
        jlabelFrame = new JLabel("LMMS: VISION");
        jlabelFrame.setBounds(0, 0, 1129, 42);
        panel.add(jlabelFrame);
        
        myPanel3  = new JPanel(new BorderLayout(5,5));
    	myPanel3.setSize(2000,800);
    	errorInfo = new JTextArea();		
        myPanel3.add(errorInfo, BorderLayout.NORTH);
        
        JPanel controls3 = new JPanel(new GridLayout(0,1,2,2));
        controls3.setSize(300, 200);
        
        selectInputTechnician = new JComboBox();
        selectInputTechnician.addItem("BUYOFF");
        selectInputTechnician.addItem("MAINTAINENCE");
        selectInputTechnician.addItem("SETUP");
        selectInputTechnician.addItem("NO SCHEDULE");
        selectInputTechnician.addItem("BREAKDOWN");
        selectInputTechnician.addItem("TESTING");
        
        selectInputTechnician.addAncestorListener( new RequestFocusListener() );
        selectInputTechnician.setFont(new Font("Century Gothic", Font.BOLD, 25));
        controls3.add(selectInputTechnician);
        
        myPanel4  = new JPanel(new BorderLayout(5,5));
        myPanel4.setSize(2000,200);
   
        
        ActionListener technicianCancelEvent=new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
            	LoadDB.funUpdateLMMSEventTechnicianStop();
            	inHelpMode = false;
            	LoadDB.runTechnician = false;
            	resetClick = true;
            	selectInputTechnician.setBackground(new Color(255,255,255));
            	MainClient.lblCompleteStatus.setVisible(false);
            	dialog.setAlwaysOnTop(false);
        		dialog.setVisible(false);
        		//LoadDB.rmsStatus = "runinspect";
            	//LoadDB.runQty = false;
            	//controls3.setVisible(false);
            }
        };
        
        
        
        
        btnReset = new JButton("Stop Technician");
        btnReset.setFont(new Font("Century Gothic", Font.BOLD, 20));
        btnReset.addActionListener(technicianCancelEvent);
        controls3.add(btnReset);
        myPanel4.add(controls3);

        guiFrame.setVisible(true);
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
	
	private static void selectManual()
	{
		
		
		int result = JOptionPane.showConfirmDialog(null, myPanel2,
	            "Manual Quantity:" + LoadDB.currentJobNumber, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
        	LoadDB.totalQuantity =  Integer.parseInt(quantityField.getText());
        	LoadDB.currentQuantity = 0;
        	
        	LoadDB.funUpdateLMMSQuantity();
          //System.out.println("x value: " + xField.getText());
          //System.out.println("y value: " + yField.getText());
        }
	}
	public static JDialog dialog;
	public static boolean inHelpMode = false;
	public static void selectTechnician()
	{
		JOptionPane optionPane = new JOptionPane(myPanel4);
		optionPane.setWantsInput(true);
		optionPane.setOptionType(JOptionPane.OK_OPTION);
		dialog = optionPane.createDialog("Technician Control");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
		
		//int result = JOptionPane.showConfirmDialog(null, myPanel4, "Technician Control", JOptionPane.OK_CANCEL_OPTION);
		int result = JOptionPane.OK_OPTION;
        if (result == 0) {
        	if(resetClick == false)
        	{
        		if( inHelpMode == false )
        		{
	            String tech = selectInputTechnician.getSelectedItem().toString();
	        	MainClient.lblCompleteStatus.setText("TECHNICIAN: " + tech);
	        	MainClient.lblCompleteStatus.setBackground(new Color(255, 0, 0));
	        	MainClient.lblCompleteStatus.setVisible(true);
	        	LoadDB.eventTrigger = tech;
	        	LoadDB.funUpdateLMMSEventTechnicianStart();
	        	selectInputTechnician.setBackground(new Color(255,0,0));
	        	LoadDB.runTechnician = true;
	        	inHelpMode = true;
        		}
        	}
        	
        	resetClick = false;
          //System.out.println("x value: " + xField.getText());
          //System.out.println("y value: " + yField.getText());
        }
//        else
//        {
//        	LoadDB.funUpdateLMMSStandby();
//        	MainClient.lblCompleteStatus.setVisible(false);
//        	LoadDB.runTechnician = false;
//        	LoadDB.runQty = false;
//        	resetClick = false;
//        }
        
	}
	
	private static void selectVer()
	{
		
		
		int result = JOptionPane.showConfirmDialog(null, myPanel3,
	            "Information Progress:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
        	
        }
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


}

class RequestFocusListener implements AncestorListener
{
	private boolean removeListener;

	/*
	 *  Convenience constructor. The listener is only used once and then it is
	 *  removed from the component.
	 */
	public RequestFocusListener()
	{
		this(true);
	}

	/*
	 *  Constructor that controls whether this listen can be used once or
	 *  multiple times.
	 *
	 *  @param removeListener when true this listener is only invoked once
	 *                        otherwise it can be invoked multiple times.
	 */
	public RequestFocusListener(boolean removeListener)
	{
		this.removeListener = removeListener;
	}

	@Override
	public void ancestorAdded(AncestorEvent e)
	{
		JComponent component = e.getComponent();
		component.requestFocusInWindow();

		if (removeListener)
			component.removeAncestorListener( this );
	}

	@Override
	public void ancestorMoved(AncestorEvent e) {}

	@Override
	public void ancestorRemoved(AncestorEvent e) {}
}
