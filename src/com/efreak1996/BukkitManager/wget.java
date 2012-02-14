package com.efreak1996.BukkitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.net.URL;

public class wget {

    protected static int count, total, itemCount, itemTotal;
    protected static long lastModified;
    protected static String error;
    private static BmOutput out = new BmOutput();
    private static BmConfiguration config = new BmConfiguration();

    public static Boolean fetch(Boolean silent, String location, String filename, File dir) {
        try {
            count = total = itemCount = itemTotal = 0;
            if (!silent) out.sendConsole("Downloading " + filename + " (" + getFileSize(location) + ")");
            download(location, filename, dir);
            if (!silent) out.sendConsole("Download of " + filename + " finished.");
            return true;
        } catch (IOException e) {
        	out.sendConsole("Error Downloading File: ");
			if (config.Config.getBoolean("General.Debug")) e.printStackTrace();
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
            if (count < 0)
                break;

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