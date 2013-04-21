package org.efreak.bukkitmanager.remoteserver;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

public class RemoteCommandManager {

	private static HashMap<String, Method> methods = new HashMap<String, Method>();
	private static Configuration config;
	
	static {
		config = Bukkitmanager.getConfiguration();
	}
	
	public static String invoke(String cmd, Object... args) {
		if (!methods.containsKey(cmd)) return ReturnCodes.echo404();
		try {
			RemoteCommandHandler handler = (RemoteCommandHandler) methods.get(cmd).getDeclaringClass().newInstance();
			return methods.get(cmd).invoke(handler, args).toString();
		}catch (Exception e) {
			if (config.getDebug()) e.printStackTrace();
		}
		return ReturnCodes.echo404();
	}
	
	public static void registerHandler(final Class<?> class_) {
		for (final Method method : class_.getMethods()) {
			if (!method.isAnnotationPresent(RemoteCommand.class)) continue;
			final RemoteCommand variable = method.getAnnotation(RemoteCommand.class);
			methods.put(variable.name(), method);
		}
	}
	
}