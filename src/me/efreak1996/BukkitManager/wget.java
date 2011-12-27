package me.efreak1996.BukkitManager;

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
    protected static boolean cancelled;
    private static BmOutput out = new BmOutput();

    public synchronized void cancel() {
        cancelled = true;
    }

    public static void fetch(Boolean silent, String location, String filename, File dir) {
        try {
            cancelled = false;
            count = total = itemCount = itemTotal = 0;
            if (cancelled) 
                return;

            if (!silent) out.sendConsole("Downloading " + filename + " (" + getFileSize(location) + ")");
            //p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.WHITE + " Downloading " + filename + " (" + getFileSize(location) + ")");
            download(location, filename, dir);
            if (!silent) out.sendConsole("Download of " + filename + " finished.");
            //p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.GREEN + " Download of" + filename + " finished.");

        } catch (IOException ex) {
        	out.sendConsole("Error Downloading File: " + ex);
            //p.sendMessage(ChatColor.DARK_RED + "[BukkitManager]" + ChatColor.RED + " Error Downloading File: " + ex);

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
            if (cancelled)
                break;

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