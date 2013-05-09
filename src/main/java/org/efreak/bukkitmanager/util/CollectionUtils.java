package org.efreak.bukkitmanager.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

	public static boolean containsIgnoreCase(Collection<String> collection, String string) {
		for (String entry : collection) {
			if (entry.equalsIgnoreCase(string)) return true;
		}
		return false;
	}
	
	public static String getIgnoreCase(Collection<String> collection, String string) {
		for (String entry : collection) {
			if (entry.equalsIgnoreCase(string)) return entry;
		}
		return null;
	}
	
	public static boolean containsIgnoreCase(Map<String, Object> map, String key) {
		for (String entry : map.keySet()) {
			if (entry.equalsIgnoreCase(key)) return true;
		}
		return false;
	}
	
	public static Object getIgnoreCase(Map<String, Object> map, String key) {
		for (String entry : map.keySet()) {
			if (entry.equalsIgnoreCase(key)) return map.get(entry);
		}
		return null;		
	}
}
