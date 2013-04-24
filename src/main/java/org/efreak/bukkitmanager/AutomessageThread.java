package org.efreak.bukkitmanager;

import org.bukkit.plugin.Plugin;

public class AutomessageThread extends Thread {

    private boolean run = true;
    private static Configuration config;
    private static AutomessageReader msgReader;
    private static Plugin plugin;

    static {
        config = Bukkitmanager.getConfiguration();
        msgReader = new AutomessageReader();
        msgReader.init();
        plugin = Bukkitmanager.getInstance();
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean getRun() {
        return run;
    }

    public void run() {
        setName("Bukkitmanager Automessage");
        while (run) {
            if (config.getInt("Automessage.Interval") == 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    if (config.getDebug()) e.printStackTrace();
                }
                continue;
            }

            for (int i = 0; i < config.getInt("Automessage.Interval"); i++) {
                try {
                    if (!run) {
                        return;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    if (config.getDebug()) e.printStackTrace();
                }
            }
            plugin.getServer().getScheduler()
                    .runTaskAsynchronously(plugin, new Runnable() {
                        public void run() {
                            msgReader.sendMsg();
                        }
                    });
        }
    }
}
