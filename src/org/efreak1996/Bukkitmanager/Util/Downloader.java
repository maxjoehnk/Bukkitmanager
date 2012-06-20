package org.efreak1996.Bukkitmanager.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URL;

import org.efreak1996.Bukkitmanager.Configuration;
import org.efreak1996.Bukkitmanager.IOManager;


public class Downloader {

    protected static int count, total, itemCount, itemTotal;
    protected static long lastModified;
    protected static String error;
    private static IOManager io;
    private static Configuration config;

    public void initialize() {
    	io = new IOManager();
    	config = new Configuration();
    }
    
    public static Boolean fetch(Boolean silent, String location, String filename, File dir) {
        try {
            count = total = itemCount = itemTotal = 0;
            if (!silent) io.sendConsole(io.translate("Downloader.Downloading").replaceAll("%file%", filename).replaceAll("%filesize%", getFileSize(location)));
            download(location, filename, dir);
            if (!silent) io.sendConsole(io.translate("Downloader.Downloaded").replaceAll("%file%", filename));
            return true;
        } catch (IOException e) {
        	io.sendConsole(io.translate("Downloader.Error").replaceAll("%file%", filename));
			if (config.getDebug()) e.printStackTrace();
        	return false;
        }
    }

    protected static synchronized void download(String location, String filename, File dir)throws IOException {
        URLConnection connection = new URL(location).openConnection();
        connection.setUseCaches(false);
        lastModified = connection.getLastModified();
        String destination = dir + File.separator + filename;
        InputStream in = connection.getInputStream();
        OutputStream out = new FileOutputStream(destination);
        byte[] buffer = new byte[65536];
        //int currentCount = 0;

        for (;;) {

            int count = in.read(buffer);
            if (count < 0) break;
            out.write(buffer, 0, count);
            //currentCount += count;
        }

        in.close();
        out.close();
    }

    private static String getFileSize(String location) throws IOException {
        URLConnection connection = new URL(location).openConnection();
        return readableSize(connection.getContentLength());
    }   

    public long getLastModified() {
        return lastModified;
    }
    public static String readableSize(long size) {
        String[] units = new String[] { "B", "KB", "MB", "GB", "TB", "PB" };
        int mod = 1024, i;

        for (i = 0; size > mod; i++) {
            size /= mod;
        }

        return Math.round(size) + " " + units[i];
    }
}