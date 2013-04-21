package org.efreak.bukkitmanager.remoteserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
//import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.util.NotificationsHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteConnection extends Thread {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String type = "", user = "", pass = "", action = "";
	private JSONObject args = null;
	private JSONArray actions = null;
	//private static Database db;
	private static Configuration config;
	private static IOManager io;
	
	
	static {
		//db = Bukkitmanager.getDb();
		config = Bukkitmanager.getConfiguration();
		io = Bukkitmanager.getIOManager();
	}
	
	public RemoteConnection(Socket accept) {
		socket = accept;
	}
	
	@Override
	public void run() {
		if (config.getBoolean("RemoteServer.Logging.Enabled")) io.sendConsole("Remote Connection from IP: " + socket.getInetAddress());
		if (config.getBoolean("Notifications.RemoteServer")) NotificationsHandler.notify("Bukkitmanager", "Remoteserver Connection", "IP: " + socket.getInetAddress());
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine = in.readLine();
			if (inputLine.startsWith("GET")) handleWebsocket(inputLine);
			try {
				JSONObject jsonInput = new JSONObject(inputLine);
				handleJSON(jsonInput);
				socket.close();
			}catch(JSONException e) {
				handleWebsocket(inputLine);
			}
			if (config.getBoolean("RemoteServer.Logging.Enabled")) io.sendConsole("Remote Connection from IP: " + socket.getInetAddress() + " closed");
		}catch (Exception e) {
			if (config.getDebug()) e.printStackTrace();
		}
	}
	
	private void handleJSON(JSONObject jsonInput) throws JSONException {
		type = jsonInput.getString("type");
		user = jsonInput.getString("username");
		pass = jsonInput.getString("password");
		if (!login(user, pass)) {
			out.write(new JSONObject().put("Result", "401").toString());
			return;
		}
		action = jsonInput.getString("action");
		actions = jsonInput.getJSONArray("actions");
		args = null;
		if (jsonInput.has("args")) args = jsonInput.getJSONObject("args");
		if (action != null && action != "") {
			if (args == null) out.println(RemoteCommandManager.invoke(action));
			else out.println(RemoteCommandManager.invoke(action, args));
		}
		if (actions != null && actions.length() != 0) {
			for (int i = 0; i < actions.length(); i++) out.println(RemoteCommandManager.invoke(actions.getString(i)));
		}
	}
	
	private void handleWebsocket(String inputLine) throws IOException {
		//while (in.readLine() != "");
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return null;
	}
	
	private boolean login(String username, String password) {
		if (username.equals(config.getString("RemoteServer.User.Admin.Username")) && password.equals(hashPassword(config.getString("RemoteServer.User.Admin.Password")))) return true;
		return false;
	}
}
