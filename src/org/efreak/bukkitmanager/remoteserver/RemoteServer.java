package org.efreak.bukkitmanager.remoteserver;

import java.net.BindException;
import java.net.ServerSocket;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.IOManager;

public class RemoteServer extends Thread {

	private static Configuration config;
	private static IOManager io;
	private static ServerSocket sSocket;
	private static boolean listening = true;
	
	static {
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public void init() {
		if (config.getBoolean("RemoteServer.Enabled")) start();
	}
	
	@Override
	public void run() {
		try {
			sSocket = new ServerSocket(config.getInt("RemoteServer.Port"));
			io.sendConsole("Remote Server started! Listening on Port: " + sSocket.getLocalPort());
			while (listening) new RemoteConnection(sSocket.accept()).start();
		}catch (BindException e) {
			io.sendConsoleError("Failed to Bind to Port " + config.getInt("RemoteServer.Port") + ". Maybe another service is using it?");
			if (config.getDebug()) e.printStackTrace();
		}catch (Exception e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			if (sSocket != null) {
				try {
					sSocket.close();
				}catch (Exception e) {
					if (config.getDebug()) e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean getListening() {
		return listening;
	}
	
	public void shutdown() {
		listening = false;
		if (sSocket != null) {
			try {
				sSocket.close();
			}catch (Exception e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
	}
	
	public static ServerSocket getServerSocket() {
		return sSocket;
	}
}