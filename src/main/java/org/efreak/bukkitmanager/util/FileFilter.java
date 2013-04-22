package org.efreak.bukkitmanager.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {
	
	String suffix;
	
	public FileFilter(String suffix) {
		this.suffix = suffix;
	}
	
    public boolean accept(File dir, String name) {
        return name.endsWith(suffix);
    }
}