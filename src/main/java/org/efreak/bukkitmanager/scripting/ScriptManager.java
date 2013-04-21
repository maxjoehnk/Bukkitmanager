package org.efreak.bukkitmanager.scripting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;
import org.efreak.bukkitmanager.Database;
import org.efreak.bukkitmanager.IOManager;
import org.efreak.bukkitmanager.listener.ScriptListener;
import org.efreak.bukkitmanager.scripting.api.*;
import org.efreak.bukkitmanager.util.FileFilter;
import org.efreak.bukkitmanager.util.FileHelper;

public class ScriptManager {

	private static ScriptEngine engine;
	
	private static Configuration config;
	private static IOManager io;
	private static Database db;
	private static APIManager api;
	private static List<CompiledScript> scripts;
	
	static {
		io = Bukkitmanager.getIOManager();
		config = Bukkitmanager.getConfiguration();
		db = Bukkitmanager.getDb();
		api = new APIManager();
	}
	
	public void init() {
		scripts = new ArrayList<CompiledScript>();
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("js");
		api.addAPI("plugin", new PluginAPI());
		api.addAPI("player", new PlayerAPI());
		api.addAPI("world", new WorldAPI());
		engine.put("io", io);
		engine.put("config", config);
		engine.put("api", api);
		engine.put("db", db);
		if (config.getBoolean("General.Scripting")) {
			io.sendConsole("Loading scripts...");
			File scriptDir = new File(FileHelper.getPluginDir(), "scripts");
			for (File scriptFile : scriptDir.listFiles(new FileFilter(".js"))) {
				try {
					Compilable compilingEngine = (Compilable) engine;
					CompiledScript script = compilingEngine.compile(new FileReader(scriptFile));
					if (script != null) scripts.add(script);
				}catch (Exception e) {
					io.sendConsoleError("Error loading script: " + scriptFile.getName());
					if (config.getDebug()) e.printStackTrace();
				}
			}
			for (CompiledScript script : scripts) {
				try {
					script.eval();
					Invocable inv = (Invocable) engine;
					inv.invokeFunction("onLoad");
				}catch (ScriptException e) {
					if (config.getDebug()) e.printStackTrace();
				} catch (NoSuchMethodException e) {
				}
			}
			Bukkitmanager.getInstance().getServer().getPluginManager().registerEvents(new ScriptListener(), Bukkitmanager.getInstance());
			io.sendConsole(scripts.size() + " Script(s) loaded");
		}
	}
	
	public static boolean runScript(File file) {
		try {
			engine.eval(new FileReader(file));
			return true;
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		} catch (ScriptException e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return false;
	}
	
	public static void runFunction(String name, Object... args) {
		for (CompiledScript script : scripts) {
			try {
				script.eval();
				Invocable inv = (Invocable) engine;
				inv.invokeFunction(name, args);
			}catch (NoSuchMethodException e) {
			}catch (Exception e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}
	}
	
	public static void reloadScripts() {
		io.sendConsole("Reloading scripts...");
		scripts.clear();
		File scriptDir = new File(FileHelper.getPluginDir(), "scripts");
		for (File scriptFile : scriptDir.listFiles(new FileFilter(".js"))) {
			try {
				Compilable compilingEngine = (Compilable) engine;
				CompiledScript script = compilingEngine.compile(new FileReader(scriptFile));
				if (script != null) scripts.add(script);
			}catch (Exception e) {
				io.sendConsoleError("Error loading script: " + scriptFile.getName());
				if (config.getDebug()) e.printStackTrace();
			}
		}
		Bukkitmanager.getInstance().getServer().getPluginManager().registerEvents(new ScriptListener(), Bukkitmanager.getInstance());
		io.sendConsole(scripts.size() + " Scripts loaded");
	}
	
	public static APIManager getAPI() {
		return api;
	}
}
