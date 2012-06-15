package com.efreak1996.BukkitManager.Swing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.efreak1996.BukkitManager.Swing.Remote.BmSwingRemoteConnector;

import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class BmMainGUI {

	private JFrame frmBukkitmanagerGui;	
	
	public static void main(String[] args) {
		com.jidesoft.utils.Lm.verifyLicense("Max Joehnk", "Bukkitmanager", "YrreBQlj.lyFAbpwcAUamoLF3Moy0zN");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					BmMainGUI window = new BmMainGUI();
					window.frmBukkitmanagerGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BmMainGUI() {
		initialize();
	}
	
	private void initialize() {
		frmBukkitmanagerGui = new JFrame();
		frmBukkitmanagerGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBukkitmanagerGui.setIconImage(Toolkit.getDefaultToolkit().getImage(BmMainGUI.class.getResource("/img/swing/icon.png")));
		frmBukkitmanagerGui.setTitle("Bukkitmanager GUI");
		frmBukkitmanagerGui.setBounds(100, 100, 800, 600);
		frmBukkitmanagerGui.getContentPane().setLayout(null);
		
		JButton createServer = new JButton();
		createServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BmSwingRemoteConnector.main();
				frmBukkitmanagerGui.setVisible(false);
			}
		});
		createServer.setSelectedIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/Connect.png")));
		createServer.setIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/Connect.png")));
		createServer.setBounds(265, 11, 250, 250);
		frmBukkitmanagerGui.getContentPane().add(createServer);
		
		JButton connectBukkit = new JButton();
		connectBukkit.setForeground(Color.BLACK);
		connectBukkit.setSelectedIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/createnewserver_big.png")));
		connectBukkit.setIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/createnewserver_big.png")));
		connectBukkit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		connectBukkit.setBounds(10, 11, 250, 250);
		frmBukkitmanagerGui.getContentPane().add(connectBukkit);
		
		JButton loadServer = new JButton();
		loadServer.setSelectedIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/loadServer.png")));
		loadServer.setIcon(new ImageIcon(BmMainGUI.class.getResource("/img/swing/loadServer.png")));
		loadServer.setBounds(520, 11, 250, 250);
		frmBukkitmanagerGui.getContentPane().add(loadServer);
		
		JLabel lblCreateServer = new JLabel("<html>\r\nCreate a new Server<br>\r\n<br>\r\nAllows you to create a new Server, either Vanilla or Bukkit, with a Wizard and pre-configured Runscripts.\r\n</html>");
		lblCreateServer.setVerticalAlignment(SwingConstants.TOP);
		lblCreateServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateServer.setBounds(10, 272, 250, 279);
		frmBukkitmanagerGui.getContentPane().add(lblCreateServer);
		
		JLabel lblConnectBukkit = new JLabel("<html>Connect to a Bukkit Server<br><br>\r\nAllows you to connect to a Bukkit Server, on which Bukkitmanager with configured Remote Server runs, to control and ");
		lblConnectBukkit.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectBukkit.setVerticalAlignment(SwingConstants.TOP);
		lblConnectBukkit.setBounds(265, 272, 250, 279);
		frmBukkitmanagerGui.getContentPane().add(lblConnectBukkit);
		
		JLabel lblLoadServer = new JLabel("<html>Load an existing Server<br><br>\r\nAllows you to load an existing Server to manage and </html>");
		lblLoadServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoadServer.setVerticalAlignment(SwingConstants.TOP);
		lblLoadServer.setBounds(520, 272, 250, 279);
		frmBukkitmanagerGui.getContentPane().add(lblLoadServer);
	}

	public void close() {
		frmBukkitmanagerGui.setVisible(false);
	}
}