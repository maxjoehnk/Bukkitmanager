package org.efreak.bukkitmanager.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.Configuration;

public class ArchiveManager {

	private static ZipOutputStream zipOut = null;
	private static Configuration config;
	
	static {
		config = Bukkitmanager.getConfiguration();
	}
	
	public static void compressZip(File input, File outputFile) {
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(outputFile));
			if (input.isFile()) compressFile(input);
			else compressFolder(input);
		} catch (FileNotFoundException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			if(zipOut!=null)
				try {
					zipOut.close();
				} catch (IOException e) {
					if (config.getDebug()) e.printStackTrace();
				}
		}
	}
	
	public static void extractZip(File inputFile, File outputFolder) {
		
	}
	
	private static void compressFile(File file) {compressFile("/", file);}
	
	private static void compressFile(String path, File file) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int avail = bis.available();
			byte[] buffer = new byte[avail] ;
			if (avail>0) bis.read(buffer, 0, avail);
			ZipEntry ze;
			if (path.endsWith(File.separator) || path == "") ze = new ZipEntry(path + file.getName());
			else ze = new ZipEntry(path + File.separator + file.getName());
			zipOut.putNextEntry(ze);
			zipOut.write(buffer, 0, buffer.length);
			zipOut.closeEntry();	
		}catch(IOException e) {
			if (config.getDebug()) e.printStackTrace();
		}finally {
			try {
				if(bis!=null) bis.close();
			}catch(Exception e) {
				if (config.getDebug()) e.printStackTrace();
			}
		}		
	}
	
	private static void compressFolder(File folder) {compressFolder("", folder);}
	
	private static void compressFolder(String path, File folder) {
		File[] fileEntries = folder.listFiles();
		for (int i = 0; i < fileEntries.length; i++) {
			if (fileEntries[i].isFile()) compressFile(path, fileEntries[i]);
			else {
				if (path == "") compressFolder(fileEntries[i].toString().split(Pattern.quote(File.separator))[folder.toString().split(Pattern.quote(File.separator)).length], fileEntries[i]);
				else compressFolder(path + File.separator + fileEntries[i].toString().split(Pattern.quote(File.separator))[folder.toString().split(Pattern.quote(File.separator)).length], fileEntries[i]);
			}
		}
	}
}