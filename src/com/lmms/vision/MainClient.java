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
	public static Label lblSig11;
	public static Label lblSig12;
	public static Label lblSig13;
	public static Label lblSig14;
	public static Label lblSig15;
	public static Label lblSig16;
	
	public static  Label lblJStat1;
	public static  Label lblJStat2;
	public static  Label lblJStat3;
	public static  Label lblJStat4;
	public static  Label lblJStat5;
	public static  Label lblJStat6;
	public static  Label lblJStat7;
	public static  Label lblJStat8;
	public static  Label lblJStat9;
	public static  Label lblJStat10;
	public static  Label lblJStat11;
	public static  Label lblJStat12;
	public static  Label lblJStat13;
	public static  Label lblJStat14;
	public static  Label lblJStat15;
	public static  Label lblJStat16;
	
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
	
	//2019-0128 dwyane add
	public static Label lb_Light;
	public static Label lb_Camera;
	public static Label lb_CurrentPWR;
	//2019-0128 dwyane add
	
	public static Label lblModelSelect;
	public static Label lblTotalPart;
	public static Label lblTotalOK;
	public static Label lblTotalNG;
	private static JLabel lblName;
	
	public static Label lblOK;
	public static Label lblNG;
			   
	
	public static void main (String[] args) throws AWTException {
		try {
			
			new MainClient();
			
			ConfigLog.getPropValues();
			
			lblName.setText("NO."+ ConfigLog.machinenoSet);
			
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
        //guiFrame.setSize(1129,42);
        guiFrame.setSize(122,818);
        guiFrame.setLocationRelativeTo(null);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - guiFrame.getWidth();
        int y = 0;
        guiFrame.setLocation(x, y);

        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(128, 128, 128));
        
        
        guiFrame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        lblName = new JLabel("NO.--");
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Century Gothic", Font.BOLD, 10));
        lblName.setBounds(82, 27, 40, 17);
        panel.add(lblName);
        
        lblpartNumber = new JLabel("{PART NUMBER}");
        lblpartNumber.setForeground(Color.WHITE);
        lblpartNumber.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lblpartNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lblpartNumber.setBounds(0, 55, 122, 28);
        panel.add(lblpartNumber);
        
        lbljobNumber = new JLabel("{JOB NUMBER}");
        lbljobNumber.setHorizontalAlignment(SwingConstants.CENTER);
        lbljobNumber.setForeground(Color.WHITE);
        lbljobNumber.setFont(new Font("Century Gothic", Font.BOLD, 11));
        lbljobNumber.setBounds(0, 99, 122, 25);
        panel.add(lbljobNumber);
        
        lblProcess = new JLabel("{PROCESS}");
        lblProcess.setHorizontalAlignment(SwingConstants.CENTER);
        lblProcess.setForeground(Color.WHITE);
        lblProcess.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblProcess.setBounds(17, 140, 87, 28);
        panel.add(lblProcess);
        
        lblTotalPass = new JLabel("{PASS}");
        lblTotalPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalPass.setForeground(Color.WHITE);
        lblTotalPass.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalPass.setBounds(0, 189, 122, 25);
        panel.add(lblTotalPass);
        
        lblTotalFail = new JLabel("{FAIL}");
        lblTotalFail.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalFail.setForeground(Color.WHITE);
        lblTotalFail.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalFail.setBounds(0, 232, 122, 28);
        panel.add(lblTotalFail);
        
        lblCQuantity = new JLabel("500000");       // lblCQuantity = new JLabel("1000000"); 
        lblCQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCQuantity.setForeground(Color.WHITE);
        lblCQuantity.setFont(new Font("Century Gothic", Font.BOLD, 13));
        lblCQuantity.setBounds(70, 286, 50, 12);
        panel.add(lblCQuantity);
        
        lblQuantity = new JLabel("1000000");
        lblQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuantity.setForeground(Color.WHITE);
        lblQuantity.setFont(new Font("Century Gothic", Font.BOLD, 13));
        lblQuantity.setBounds(0, 284, 62, 17);
        panel.add(lblQuantity);
        
        lblTotalQuantity = new JLabel("1000000");
        lblTotalQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalQuantity.setForeground(Color.WHITE);
        lblTotalQuantity.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblTotalQuantity.setBounds(17, 325, 84, 25);
        panel.add(lblTotalQuantity);
        
        lbl1 = new JLabel("T:");
        lbl1.setHorizontalAlignment(SwingConstants.LEFT);
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lbl1.setBounds(2, 287, 16, 12);
        panel.add(lbl1);
        
        lblC = new JLabel("C:");
        lblC.setHorizontalAlignment(SwingConstants.LEFT);
        lblC.setForeground(Color.WHITE);
        lblC.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblC.setBounds(65, 287, 16, 12);
        panel.add(lblC);
        
        JButton btnX = new JButton("...");
        btnX.setFont(new Font("Century Gothic", Font.BOLD, 9));
        btnX.setBounds(82, 0, 40, 28);
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
        lblstatVision.setBounds(61, 490, 65, 13);
        panel.add(lblstatVision);
        
		/*
		 * lblstatFail = new Label("FAIL"); lblstatFail.setForeground(Color.WHITE);
		 * lblstatFail.setFont(new Font("Century Gothic", Font.BOLD, 12));
		 * lblstatFail.setBackground(SystemColor.controlDkShadow);
		 * lblstatFail.setAlignment(Label.CENTER); lblstatFail.setBounds(964, 28, 33,
		 * 13); panel.add(lblstatFail);
		 * 
		 * lblstatPass = new Label("PASS"); lblstatPass.setForeground(Color.WHITE);
		 * lblstatPass.setFont(new Font("Century Gothic", Font.BOLD, 12));
		 * lblstatPass.setBackground(SystemColor.controlDkShadow);
		 * lblstatPass.setAlignment(Label.CENTER); lblstatPass.setBounds(929, 28, 33,
		 * 13); panel.add(lblstatPass);
		 */
        
        lblstatInspect = new Label("INSPECT");
        lblstatInspect.setForeground(Color.WHITE);
        lblstatInspect.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblstatInspect.setBackground(SystemColor.controlDkShadow);
        lblstatInspect.setAlignment(Label.CENTER);
        lblstatInspect.setBounds(0, 445, 62, 13);
        panel.add(lblstatInspect);
        
        lblCompleteStatus = new Label("JOB COMPLETE!");
        lblCompleteStatus.setForeground(Color.WHITE);
        lblCompleteStatus.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblCompleteStatus.setBackground(new Color(50, 205, 50));
        lblCompleteStatus.setAlignment(Label.CENTER);
        lblCompleteStatus.setBounds(0, 416, 123, 67);
        lblCompleteStatus.setVisible(false);
       
        
        lblStat = new Label("RS:000");
        lblStat.setForeground(Color.WHITE);
        lblStat.setFont(new Font("Century Gothic", Font.BOLD, 9));
        lblStat.setBackground(SystemColor.controlDkShadow);
        lblStat.setAlignment(Label.CENTER);
        lblStat.setBounds(0, 378, 37, 11);
        panel.add(lblStat);
         
	    lblStat2 = new Label("RL:000");
	    lblStat2.setForeground(Color.WHITE);
	    lblStat2.setFont(new Font("Century Gothic", Font.BOLD, 9));
	    lblStat2.setBackground(SystemColor.controlDkShadow);
	    lblStat2.setAlignment(Label.CENTER);
	    lblStat2.setBounds(68, 378, 33, 11);
	    panel.add(lblStat2);
	     
	    lblStat3 = new Label("RD:000");
	    lblStat3.setForeground(Color.WHITE);
	    lblStat3.setFont(new Font("Century Gothic", Font.BOLD, 9));
	    lblStat3.setBackground(SystemColor.controlDkShadow);
	    lblStat3.setAlignment(Label.CENTER);
	    lblStat3.setBounds(36, 378, 33, 11);
	    panel.add(lblStat3);
        
        lblStat4 = new Label("RI:000");
        lblStat4.setForeground(Color.WHITE);
        lblStat4.setFont(new Font("Century Gothic", Font.BOLD, 9));
        lblStat4.setBackground(SystemColor.controlDkShadow);
        lblStat4.setAlignment(Label.CENTER);
        lblStat4.setBounds(102, 378, 27, 11);
        panel.add(lblStat4);
        
        lblAdam = new Label("ADAM OK");
        lblAdam.setForeground(Color.WHITE);
        lblAdam.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblAdam.setBackground(SystemColor.controlDkShadow);
        lblAdam.setAlignment(Label.CENTER);
        lblAdam.setBounds(0, 490, 62, 13);
        panel.add(lblAdam);
        
        lblSig1 = new Label("ST");
        lblSig1.setForeground(Color.WHITE);
        lblSig1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig1.setBackground(SystemColor.controlDkShadow);
        lblSig1.setAlignment(Label.CENTER);
        lblSig1.setBounds(0, 353, 15, 13);
        panel.add(lblSig1);
        
        lblSig2 = new Label("RT");
        lblSig2.setForeground(Color.WHITE);
        lblSig2.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig2.setBackground(SystemColor.controlDkShadow);
        lblSig2.setAlignment(Label.CENTER);
        lblSig2.setBounds(0, 364, 15, 13);
        panel.add(lblSig2);
        
        lblSig3 = new Label("AM");
        lblSig3.setForeground(Color.WHITE);
        lblSig3.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig3.setBackground(SystemColor.controlDkShadow);
        lblSig3.setAlignment(Label.CENTER);
        lblSig3.setBounds(17, 353, 15, 13);
        panel.add(lblSig3);
        
        lblSig4 = new Label("SP");
        lblSig4.setForeground(Color.WHITE);
        lblSig4.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig4.setBackground(SystemColor.controlDkShadow);
        lblSig4.setAlignment(Label.CENTER);
        lblSig4.setBounds(17, 364, 15, 13);
        panel.add(lblSig4);
        
        lblSig5 = new Label("PS");
        lblSig5.setForeground(Color.WHITE);
        lblSig5.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig5.setBackground(SystemColor.controlDkShadow);
        lblSig5.setAlignment(Label.CENTER);
        lblSig5.setBounds(33, 353, 15, 13);
        panel.add(lblSig5);
        
        lblSig6 = new Label("CN");
        lblSig6.setForeground(Color.WHITE);
        lblSig6.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig6.setBackground(SystemColor.controlDkShadow);
        lblSig6.setAlignment(Label.CENTER);
        lblSig6.setBounds(33, 364, 15, 13);
        panel.add(lblSig6);
        
        lblSig7 = new Label("TK");
        lblSig7.setForeground(Color.WHITE);
        lblSig7.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig7.setBackground(SystemColor.controlDkShadow);
        lblSig7.setAlignment(Label.CENTER);
        lblSig7.setBounds(50, 353, 15, 13);
        panel.add(lblSig7);
        
        lblSig8 = new Label("SR");
        lblSig8.setForeground(Color.WHITE);
        lblSig8.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig8.setBackground(SystemColor.controlDkShadow);
        lblSig8.setAlignment(Label.CENTER);
        lblSig8.setBounds(50, 364, 15, 13);
        panel.add(lblSig8);
        
        lblSig9 = new Label("RG");
        lblSig9.setForeground(Color.WHITE);
        lblSig9.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig9.setBackground(SystemColor.controlDkShadow);
        lblSig9.setAlignment(Label.CENTER);
        lblSig9.setBounds(66, 353, 15, 13);
        panel.add(lblSig9);
        
        lblSig10 = new Label("PY");
        lblSig10.setForeground(Color.WHITE);
        lblSig10.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig10.setBackground(SystemColor.controlDkShadow);
        lblSig10.setAlignment(Label.CENTER);
        lblSig10.setBounds(66, 364, 17, 13);
        panel.add(lblSig10);
        
        lblSig11 = new Label("AR");
        lblSig11.setForeground(Color.WHITE);
        lblSig11.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig11.setBackground(SystemColor.controlDkShadow);
        lblSig11.setAlignment(Label.CENTER);
        lblSig11.setBounds(82, 353, 15, 13);
        panel.add(lblSig11);
        
        lblSig12 = new Label("KT");
        lblSig12.setForeground(Color.WHITE);
        lblSig12.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig12.setBackground(SystemColor.controlDkShadow);
        lblSig12.setAlignment(Label.CENTER);
        lblSig12.setBounds(82, 364, 15, 13);
        panel.add(lblSig12);
        
        lblSig13 = new Label("IS");
        lblSig13.setForeground(Color.WHITE);
        lblSig13.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig13.setBackground(SystemColor.controlDkShadow);
        lblSig13.setAlignment(Label.CENTER);
        lblSig13.setBounds(98, 353, 15, 13);
        panel.add(lblSig13);
        
        lblSig14 = new Label("VT");
        lblSig14.setForeground(Color.WHITE);
        lblSig14.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig14.setBackground(SystemColor.controlDkShadow);
        lblSig14.setAlignment(Label.CENTER);
        lblSig14.setBounds(98, 364, 15, 13);
        panel.add(lblSig14);
        
        lblSig15 = new Label("ER");
        lblSig15.setForeground(Color.WHITE);
        lblSig15.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig15.setBackground(SystemColor.controlDkShadow);
        lblSig15.setAlignment(Label.CENTER);
        lblSig15.setBounds(114, 353, 15, 13);
        panel.add(lblSig15);
        
        lblSig16 = new Label("KL");
        lblSig16.setForeground(Color.WHITE);
        lblSig16.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblSig16.setBackground(SystemColor.controlDkShadow);
        lblSig16.setAlignment(Label.CENTER);
        lblSig16.setBounds(114, 364, 15, 13);
        panel.add(lblSig16);
        
        lblRunning = new Label("RUNNING");
        lblRunning.setForeground(Color.WHITE);
        lblRunning.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblRunning.setBackground(SystemColor.controlDkShadow);
        lblRunning.setAlignment(Label.CENTER);
        lblRunning.setBounds(60, 445, 62, 13);
        panel.add(lblRunning);
        
        lblRMS = new Label("loadVision");
        lblRMS.setForeground(Color.WHITE);
        lblRMS.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblRMS.setBackground(SystemColor.controlDkShadow);
        lblRMS.setAlignment(Label.CENTER);
        lblRMS.setBounds(0, 502, 62, 13);
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
      	myPanel.setSize(122, 818);
    	
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
        controls.setSize(299,1854);
        
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
    	myPanel2.setSize(122, 818);
    	
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
        
        
        lblOK = new Label("OK");
        lblOK.setForeground(Color.WHITE);
        lblOK.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblOK.setBackground(new Color(105, 105, 105));
        lblOK.setAlignment(Label.CENTER);
        lblOK.setBounds(0, 460, 62, 28);
        panel.add(lblOK);
        
        lblNG = new Label("NG");
        lblNG.setForeground(Color.WHITE);
        lblNG.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNG.setBackground(Color.GRAY);
        lblNG.setAlignment(Label.CENTER);
        lblNG.setBounds(61, 460, 61, 28);
        panel.add(lblNG); 
        
        lblJStat1 = new Label("J1: ---");
        lblJStat1.setForeground(Color.WHITE);
        lblJStat1.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat1.setBackground(SystemColor.controlDkShadow);
        lblJStat1.setAlignment(Label.CENTER);
        lblJStat1.setBounds(0, 558, 59, 13);
        panel.add(lblJStat1);
        
        lblJStat2 = new Label("J2: ---");
        lblJStat2.setForeground(Color.WHITE);
        lblJStat2.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat2.setBackground(SystemColor.controlDkShadow);
        lblJStat2.setAlignment(Label.CENTER);
        lblJStat2.setBounds(0, 571, 59, 13);
        panel.add(lblJStat2);
        
        lblJStat3 = new Label("J3: ---");
        lblJStat3.setForeground(Color.WHITE);
        lblJStat3.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat3.setBackground(SystemColor.controlDkShadow);
        lblJStat3.setAlignment(Label.CENTER);
        lblJStat3.setBounds(0, 585, 59, 13);
        panel.add(lblJStat3);
        
        lblJStat4 = new Label("J4: ---");
        lblJStat4.setForeground(Color.WHITE);
        lblJStat4.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat4.setBackground(SystemColor.controlDkShadow);
        lblJStat4.setAlignment(Label.CENTER);
        lblJStat4.setBounds(0, 599, 59, 13);
        panel.add(lblJStat4);
        
        lblJStat5 = new Label("J5: ---");
        lblJStat5.setForeground(Color.WHITE);
        lblJStat5.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat5.setBackground(SystemColor.controlDkShadow);
        lblJStat5.setAlignment(Label.CENTER);
        lblJStat5.setBounds(0, 613, 59, 13);
        panel.add(lblJStat5);
        
        lblJStat6 = new Label("J6: ---");
        lblJStat6.setForeground(Color.WHITE);
        lblJStat6.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat6.setBackground(SystemColor.controlDkShadow);
        lblJStat6.setAlignment(Label.CENTER);
        lblJStat6.setBounds(0, 627, 59, 13);
        panel.add(lblJStat6);
        
        lblJStat7 = new Label("J7: ---");
        lblJStat7.setForeground(Color.WHITE);
        lblJStat7.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat7.setBackground(SystemColor.controlDkShadow);
        lblJStat7.setAlignment(Label.CENTER);
        lblJStat7.setBounds(0, 641, 59, 13);
        panel.add(lblJStat7);
        
        lblJStat8 = new Label("J8: ---");
        lblJStat8.setForeground(Color.WHITE);
        lblJStat8.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat8.setBackground(SystemColor.controlDkShadow);
        lblJStat8.setAlignment(Label.CENTER);
        lblJStat8.setBounds(0, 653, 59, 13);
        panel.add(lblJStat8);
        
        lblJStat9 = new Label(" J9  : ---");
        lblJStat9.setForeground(Color.WHITE);
        lblJStat9.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat9.setBackground(SystemColor.controlDkShadow);
        lblJStat9.setAlignment(Label.CENTER);
        lblJStat9.setBounds(59, 558, 62, 13);
        panel.add(lblJStat9);
        
        lblJStat10 = new Label("J10: ---");
        lblJStat10.setForeground(Color.WHITE);
        lblJStat10.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat10.setBackground(SystemColor.controlDkShadow);
        lblJStat10.setAlignment(Label.CENTER);
        lblJStat10.setBounds(59, 571, 65, 13);
        panel.add(lblJStat10);
        
        lblJStat11 = new Label("J11: ---");
        lblJStat11.setForeground(Color.WHITE);
        lblJStat11.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat11.setBackground(SystemColor.controlDkShadow);
        lblJStat11.setAlignment(Label.CENTER);
        lblJStat11.setBounds(59, 585, 65, 13);
        panel.add(lblJStat11);
        
        lblJStat12 = new Label("J12: ---");
        lblJStat12.setForeground(Color.WHITE);
        lblJStat12.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat12.setBackground(SystemColor.controlDkShadow);
        //lblJStat12.setBackground(SystemColor.RED);
        lblJStat12.setAlignment(Label.CENTER);
        lblJStat12.setBounds(59, 599, 65, 13);
        panel.add(lblJStat12);
        
        lblJStat13 = new Label("J13: ---");
        lblJStat13.setForeground(Color.WHITE);
        lblJStat13.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat13.setBackground(SystemColor.controlDkShadow);
        lblJStat13.setAlignment(Label.CENTER);
        lblJStat13.setBounds(59, 613, 65, 13);
        panel.add(lblJStat13);
        
        lblJStat14 = new Label("J14: ---");
        lblJStat14.setForeground(Color.WHITE);
        lblJStat14.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat14.setBackground(SystemColor.controlDkShadow);
        lblJStat14.setAlignment(Label.CENTER);
        lblJStat14.setBounds(57, 627, 65, 13);
        panel.add(lblJStat14);
        
        lblJStat15 = new Label("J15: ---");
        lblJStat15.setForeground(Color.WHITE);
        lblJStat15.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat15.setBackground(SystemColor.controlDkShadow);
        lblJStat15.setAlignment(Label.CENTER);
        lblJStat15.setBounds(59, 641, 65, 13);
        panel.add(lblJStat15);
        
        lblJStat16 = new Label("J16: ---");
        lblJStat16.setForeground(Color.WHITE);
        lblJStat16.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblJStat16.setBackground(SystemColor.controlDkShadow);
        lblJStat16.setAlignment(Label.CENTER);
        lblJStat16.setBounds(60, 653, 62, 13);
        panel.add(lblJStat16);
        
      
        
        Label  label_45 = new Label("MOD: ");
        label_45.setForeground(Color.WHITE);
        label_45.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_45.setBackground(new Color(0, 0, 0));
        label_45.setBounds(0, 404, 33, 12);
        panel.add(label_45);
        
        Label label_46 = new Label("TOTAL:");
        label_46.setForeground(Color.WHITE);
        label_46.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_46.setBackground(new Color(0, 0, 0));
        label_46.setBounds(0, 418, 45, 11);
        panel.add(label_46);
        
        lblModelSelect = new Label("---");
        lblModelSelect.setForeground(Color.WHITE);
        lblModelSelect.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblModelSelect.setBackground(new Color(0, 0, 139));
        lblModelSelect.setAlignment(Label.CENTER);
        lblModelSelect.setBounds(33, 404, 89, 12);
        panel.add(lblModelSelect);
        
        lblTotalPart = new Label("---"); 
        lblTotalPart.setForeground(Color.WHITE);
        lblTotalPart.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalPart.setBackground(new Color(0, 0, 139));
        lblTotalPart.setAlignment(Label.CENTER);
        lblTotalPart.setBounds(43, 418, 79, 11);
        panel.add(lblTotalPart);
        
        lblTotalOK = new Label("---");
        lblTotalOK.setForeground(Color.WHITE);
        lblTotalOK.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalOK.setBackground(new Color(0, 0, 139));
        lblTotalOK.setAlignment(Label.CENTER);
        lblTotalOK.setBounds(24, 431, 35, 13);
        panel.add(lblTotalOK);
        
        lblTotalNG = new Label("---");
        lblTotalNG.setForeground(Color.WHITE);
        lblTotalNG.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblTotalNG.setBackground(new Color(0, 0, 139));
        lblTotalNG.setAlignment(Label.CENTER);
        lblTotalNG.setBounds(87, 431, 35, 13);
        panel.add(lblTotalNG);
        
      Label  label_50 = new Label("OK:");
        label_50.setForeground(Color.WHITE);
        label_50.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_50.setBackground(Color.BLACK);
        label_50.setBounds(0, 431, 26, 13);
        panel.add(label_50);
        
       Label label_51 = new Label("NG:");
        label_51.setForeground(Color.WHITE);
        label_51.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_51.setBackground(Color.BLACK);
        label_51.setBounds(61, 431, 26, 13);
        panel.add(label_51);
        
       Label label_55 = new Label("SCRIBA OK");
        label_55.setForeground(Color.WHITE);
        label_55.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_55.setBackground(SystemColor.controlDkShadow);
        label_55.setAlignment(Label.CENTER);
        label_55.setBounds(61, 502, 61, 13);
        panel.add(label_55);
        
     Label   label_56 = new Label("LSRMARK OK");
        label_56.setForeground(Color.WHITE);
        label_56.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_56.setBackground(SystemColor.controlDkShadow);
        label_56.setAlignment(Label.CENTER);
        label_56.setBounds(750, 71, 78, 13);
        panel.add(label_56);
        
        Label  label_57 = new Label("M OK");
        label_57.setForeground(Color.WHITE);
        label_57.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_57.setBackground(SystemColor.controlDkShadow);
        label_57.setAlignment(Label.CENTER);
        label_57.setBounds(72, 516, 50, 13);
        panel.add(label_57);
        
        Label  label_58 = new Label("B OK");
        label_58.setForeground(Color.WHITE);
        label_58.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_58.setBackground(SystemColor.controlDkShadow);
        label_58.setAlignment(Label.CENTER);
        label_58.setBounds(38, 516, 33, 13);
        panel.add(label_58);
        
        Label   label_59 = new Label("A OK");
        label_59.setForeground(Color.WHITE);
        label_59.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_59.setBackground(SystemColor.controlDkShadow);
        label_59.setAlignment(Label.CENTER);
        label_59.setBounds(0, 516, 37, 13);
        panel.add(label_59);
        
        Label   label_60 = new Label("LOADING");
        label_60.setForeground(Color.WHITE);
        label_60.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_60.setBackground(SystemColor.controlDkShadow);
        label_60.setAlignment(Label.CENTER);
        label_60.setBounds(0, 530, 62, 13);
        panel.add(label_60);
        
        Label label_61 = new Label("CALIB");
        label_61.setForeground(Color.WHITE);
        label_61.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_61.setBackground(SystemColor.controlDkShadow);
        label_61.setAlignment(Label.CENTER);
        label_61.setBounds(63, 530, 57, 13);
        panel.add(label_61);
        
        Label  label_62 = new Label("SEND OK");
        label_62.setForeground(Color.WHITE);
        label_62.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_62.setBackground(SystemColor.controlDkShadow);
        label_62.setAlignment(Label.CENTER);
        label_62.setBounds(0, 544, 59, 13);
        panel.add(label_62);
        
        Label  label_63 = new Label("SENT OK");
        label_63.setForeground(Color.WHITE);
        label_63.setFont(new Font("Century Gothic", Font.BOLD, 12));
        label_63.setBackground(SystemColor.controlDkShadow);
        label_63.setAlignment(Label.CENTER);
        label_63.setBounds(60, 544, 62, 13);
        panel.add(label_63);
        
      //2019-0128 dwyane add   ----- Lighting, Camera, current Power
      		Label lb_lg = new Label("Light :");
      		lb_lg.setAlignment(lb_lg.LEFT);
      		lb_lg.setForeground(Color.WHITE);
      		lb_lg.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_lg.setBackground(Color.BLACK);
      		lb_lg.setBounds(0, 680, 65, 18);
      		panel.add(lb_lg);
      		
      		lb_Light = new Label("-");
      		lb_Light.setAlignment(lb_Light.LEFT);
      		lb_Light.setForeground(Color.WHITE);
      		lb_Light.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_Light.setBackground(new Color(0, 0, 139));
      		lb_Light.setBounds(65, 680, 65, 18);
      		panel.add(lb_Light);
      		
      		Label lb_cm = new Label("Camera :"); 
      		lb_cm.setAlignment(lb_cm.LEFT);
      		lb_cm.setForeground(Color.WHITE);
      		lb_cm.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_cm.setBackground(Color.BLACK); 
      		lb_cm.setBounds(0, 700, 65, 18);
      		panel.add(lb_cm);
      		
      		lb_Camera = new Label("-"); 
      		lb_Camera.setAlignment(lb_Camera.LEFT);
      		lb_Camera.setForeground(Color.WHITE);
      		lb_Camera.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_Camera.setBackground(new Color(0, 0, 139));
      		lb_Camera.setBounds(65, 700, 65, 18);
      		panel.add(lb_Camera);
      		
      		
      		Label lb_pwr = new Label("Current :");
      		lb_pwr.setAlignment(lb_pwr.LEFT);
      		lb_pwr.setForeground(Color.WHITE);
      		lb_pwr.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_pwr.setBackground(Color.BLACK);
      		lb_pwr.setBounds(0, 720, 65, 18);
      		panel.add(lb_pwr);
      		
      		
      		lb_CurrentPWR = new Label("-");
      		lb_CurrentPWR.setAlignment(lb_CurrentPWR.LEFT);
      		lb_CurrentPWR.setForeground(Color.WHITE);
      		lb_CurrentPWR.setFont(new Font("Century Gothic", Font.BOLD, 13));
      		lb_CurrentPWR.setBackground(new Color(0, 0, 139));
      		lb_CurrentPWR.setBounds(65, 720, 65, 18);
      		panel.add(lb_CurrentPWR);
      		//2019-0128 dwyane add   ----- Lighting, Camera, current Power
        
        
        
        JLabel  jlabelFrame = new JLabel("LMMS: VISION");
        jlabelFrame.setIcon(new ImageIcon(".\\\\TAIYO\\\\Background_image_Cover.jpg"));
        jlabelFrame.setBounds(0, 0, 122, 818);
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
        //myPanel4.add(controls3, BorderLayout.CENTER);
        
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
