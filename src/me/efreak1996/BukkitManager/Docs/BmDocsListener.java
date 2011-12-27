package me.efreak1996.BukkitManager.Docs;

//java imports
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

//bukkit imports
import me.efreak1996.BukkitManager.Bukkitmanager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginDescriptionFile;

//Listener Class
public class BmDocsListener extends PlayerListener {
	
	//Some Variables for the class.
	private Bukkitmanager plugin;
	FileConfiguration config;
	public static final Logger log = Logger.getLogger("Minecraft");
	private ArrayList<String> fixedLines = new ArrayList<String>();
	
	private ArrayList<BmDocsCommands> commandsList = new ArrayList<BmDocsCommands>();
	private ArrayList<BmDocsGroups> groupsList = new ArrayList<BmDocsGroups>();
	private ArrayList<BmDocsMOTD> motdList = new ArrayList<BmDocsMOTD>();
	private ArrayList<BmDocsOnlineFiles> onlineFiles = new ArrayList<BmDocsOnlineFiles>();
	
	//Config Defaults.
	public String headerFormat = "&c%commandname - Page %current of %count &f| &7%command <page>";
	public String onlinePlayersFormat = "%prefix%name";
	public String newsFile = "news.txt";
	public int newsLines = 1;
	public boolean motdEnabled = true;
	public boolean commandLogEnabled = true;
	public boolean errorLogEnabled = true;
	public boolean permissionsEnabled = true;
	public int cacheTime = 5;
	
	/*
	 * -- Constructor for MCDocsListener --
	 * All we do here is import the instance.
	 */
	
	public BmDocsListener(Bukkitmanager instance) {
        this.plugin = instance;
    }

	/*
	 * -- Configuration Methods --
	 * We check for config.yml, if it doesn't exists we create a default (defaultCofig), then we load (loadConfig). 
	 */
	
	public void setupConfig(FileConfiguration config){
		
		this.config = config;
		
		if (!(new File(plugin.getDataFolder() + "Docs" + File.separator, "config.yml")).exists()){
			logit("[BukkitManager] Docs Configuration not found, making a default one for you!");
			defaultConfig();
		}
        loadConfig();
	}
	
	private void defaultConfig(){
		try {
			PrintWriter stream = null;
			File folder = plugin.getDataFolder();
			if (folder != null) {
				folder.mkdirs();
	        }
			String folderName = folder.getParent();
			PluginDescriptionFile pdfFile = this.plugin.getDescription();

			stream = new PrintWriter(folderName + "/MCDocs/config.yml");
			//Let's write our goods ;)
				stream.println("#MCDocs " + pdfFile.getVersion() + " by Tazzernator / Andrew Tajsic");
				stream.println("#Configuration File.");
				stream.println("#For detailed assistance please visit: http://dev.bukkit.org/server-mods/mcdocs/");
				stream.println();
				stream.println("#Here we determine which command will show which file. ");
				stream.println("commands:");
				stream.println("    /motd:");
				stream.println("        file: 'motd.txt'");
				stream.println("        groups:");
				stream.println("            Admin: 'motd-admin.txt'");
				stream.println("            Moderator: 'motd-moderator.txt'");
				stream.println("    /rules:");
				stream.println("        file: 'rules.txt'");
				stream.println("    /news:");
				stream.println("        file: 'news.txt'");
				stream.println("    /register:");
				stream.println("        file: 'register.txt'");
				stream.println("    /about:");
				stream.println("        file: 'http://tazzernator.com/files/bukkit/plugins/MCDocs/about.txt'");
				stream.println("    /help:");
				stream.println("        file: 'help/default.txt'");
				stream.println("        groups:");
				stream.println("            Admin: 'help/admin.txt'");
				stream.println("            Moderator: 'help/moderator.txt'");
				stream.println();
				stream.println("#Here we determine which files are shown when a player joins the server.");
				stream.println("motd:");
				stream.println("    file: 'motd.txt'");
				stream.println("    groups:");
				stream.println("        Admin: 'motd-admin.txt'");
				stream.println("        Moderator: 'motd-moderator.txt'");
				stream.println();
				stream.println("#This changes the pagination header that is added to MCDocs automatically when there is > 10 lines of text.");
				stream.println("header-format: '&c%commandname - Page %current of %count &f| &7%command <page>'");
				stream.println();
				stream.println("#Format to use when using %online or %online_group.");
				stream.println("online-players-format: '%prefix%name'");
				stream.println();
				stream.println("#The file to displayed when using %news.");
				stream.println("news-file: news.txt");
				stream.println();
				stream.println("#How many lines to show when using %news.");
				stream.println("news-lines: 1");
				stream.println();
				stream.println("#How long, in minutes, do you want online files to be cached locally? 0 = disable");
				stream.println("cache-time: 5");
				stream.println();
				stream.println("#Show a MOTD at login? Yes: true | No: false");
				stream.println("motd-enabled: true");
				stream.println();
				stream.println("#Inform the console when a player uses a command from the commands-list.");
				stream.println("command-log-enabled: true");
				stream.println();
				stream.println("#Send warnings and errors to the main server log? Yes: true | No: false");
				stream.println("error-log-enabled: true");
				stream.println();
				stream.println("#Do you have any permissions system installed? Yes: true | No: false");
				stream.println("permissions-enabled: false");
				stream.println();
				stream.println("#MCDocs does not hook into any permissions program to find a players group, prefix, or suffix, so please outline them here...");
				stream.println("groups:");
				stream.println("    Admin:");
				stream.println("        prefix: ''");
				stream.println("        suffix: ''");
				stream.println("        players:");
				stream.println("            - Admin1");
				stream.println("            - Admin2");
				stream.println("            - Admin3");
				stream.println("    Moderator:");
				stream.println("        prefix: ''");
				stream.println("        suffix: ''");
				stream.println("        players:");
				stream.println("            - Moderator1");
				stream.println("            - Moderator2");
				stream.println("            - Moderator3");
				stream.close();
				
		} catch (FileNotFoundException e) {
			logit("Error saving the config.yml.");
		}
	}
	
	private void loadConfig(){
		commandsList.clear();
		groupsList.clear();
		motdList.clear();
		
	    String folderName = plugin.getDataFolder().getParent();
			    
		try {
			config.load(folderName + "/MCDocs/config.yml");
		} catch (FileNotFoundException e1) {
			logit("Error: MCDocs configuration file 'config.yml' was not found!");
		} catch (IOException e) {
			logit("Error: MCDocs IOException on config.yml load!");
		} catch (InvalidConfigurationException e) {
			logit("Error: Invalid Configuration");
		}
		
		headerFormat = config.getString("header-format", headerFormat);
		onlinePlayersFormat = config.getString("online-players-format", onlinePlayersFormat);	
		motdEnabled = config.getBoolean("motd-enabled", motdEnabled);
		commandLogEnabled = config.getBoolean("command-log-enabled", commandLogEnabled);
		errorLogEnabled = config.getBoolean("error-log-enabled", errorLogEnabled);
		permissionsEnabled = config.getBoolean("permissions-enabled", permissionsEnabled);
		newsFile = config.getString("news-file", newsFile);
		newsLines = config.getInt("news-lines", newsLines);
		cacheTime = config.getInt("cache-time", cacheTime);
		
		//import our data and force find groups, commands, and motd information.
		Map<String, Object> map = config.getValues(true);
		
		//Default MOTD import
		try{
			BmDocsMOTD motdRecord = new BmDocsMOTD(map.get("motd.file").toString(), "MCDocsGlobal");
			motdList.add(motdRecord);
		}
		catch(Exception e){
			logit("motd not defined in the config. No default motd will be shown...");
		}
		
		try{
			for (String key : map.keySet()){
				
				//Commands Import
				if(key.startsWith("commands.")){
					String[] split = key.split("\\.");
					if(split.length == 2){
						BmDocsCommands commandRecord = new BmDocsCommands(split[1].toString(), map.get(key + ".file").toString(), "MCDocsGlobal");
						commandsList.add(commandRecord);
					}
					if(split.length == 4){
						BmDocsCommands commandRecord = new BmDocsCommands(split[1].toString(), map.get(key).toString(), split[3].toString());
						commandsList.add(commandRecord);
					}
				}
				
				//MOTD Import
				if(key.startsWith("motd.groups.")){
					String[] split = key.split("\\.");
					BmDocsMOTD motdGroupRecord = new BmDocsMOTD(map.get(key).toString(), split[2].toString());
					motdList.add(motdGroupRecord);
				}
				
				//Groups Import
				if(key.startsWith("groups.")){
					String[] split = key.split("\\.");
					if(split.length == 2){
						BmDocsGroups groupRecord = new BmDocsGroups(split[1].toString(), map.get(key + ".prefix").toString(), map.get(key + ".suffix").toString(), map.get(key + ".players").toString());
						groupsList.add(groupRecord);
					}
				}
			}
		}
		catch(Exception e){
			logit("Your config.yml is incorrect." + e);
		}
		
		//reverse the list so that the group files are placed before the global files.
		Collections.reverse(commandsList);
	}
		
	/*
	 * -- Main Methods --
	 * ~ onPlayerCommandPreprocess:
	 * Is checked whenever a user uses /$
	 * check to see if the user's command matches
	 * Performs a Permissions Node check - if failed, do nothing.
	 * if pass: read command's file to list lines, then forward the player, command, and page number to linesProcess.
	 *  
	 * ~ variableSwap
	 * For each line in a txt file, various %variables are replaced with their corresponding match.
	 *  
	 * ~ linesProcess:				
	 * How many lines in a document are determined, and thus how many pages to split it into.
	 * The header is loaded from header-format and the variables are replaced
	 * Finally the lines are sent to the player.
	 * 
	 * ~ onlineFile
	 * Takes in a url that is wanted to be parsed, and returns a list of lines that are to be used.
	 * It also includes a basic cache, as to not constantly request the file from the net.
	 * The cache time limit can be modified in the config.yml
	 */
	
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		//List of lines we read our first file into.
		ArrayList<String> lines = new ArrayList<String>();
		
		//Find the current Player, Message
		String[] split = event.getMessage().split(" ");
        Player player = event.getPlayer();
        boolean groupMessageSent = false;
        
        
        //Here we are going to support spaces in commands.
		int count = split.length;
		int lastInput = count - 1;
		String playerCommand = "";
		if(checkIfNumber(split[lastInput])){
			for (int i=0; i<lastInput; i++){
				playerCommand = playerCommand + split[i] + " ";
			}
		}
		else {
			for (int i=0; i<count; i++){
				playerCommand = playerCommand + split[i] + " ";
			}
		}
		
		//Cut off the final space.
		playerCommand = playerCommand.trim();
		
        
		for (BmDocsCommands r : commandsList){
        	lines.clear();
        	fixedLines.clear();
        	String command = r.getCommand();
        	int page = 0;
        	String permission = "allow";
        	
        	if (playerCommand.equalsIgnoreCase(command)){
        		
    			String[] groupInfo = getGroupInfo(player.getName());
    			if((r.getGroup().equalsIgnoreCase(groupInfo[0])) || (r.getGroup().equals("MCDocsGlobal"))){
    				permission = "allow";
    			}
    			else{
    				permission = "deny";
    			}
    			
    			//Bukkit Permissions
    			if(permissionsEnabled){
    				if(player.hasPermission("mcdocs.*")){
    					//dolly
    				}
    				else{
    					if(!player.hasPermission("mcdocs." + command)){
    						permission = "deny";
    					}
    				}
    			}
    			
    			if ((permission == "allow") && (!groupMessageSent)){
    				if(!r.getGroup().equals("MCDocsGlobal")){
    					groupMessageSent = true;
    				}
    				
        			String fileName = r.getFile();
        			fileName = basicVariableSwap(player, fileName);

        			
        			//Online file use
        			if(fileName.contains("http")){
        				ArrayList<String> onlineLines = new ArrayList<String>();
        				onlineLines = onlineFile(fileName);
        				for(String o : onlineLines){
        					lines.add(o);
        				}
        			}
        			//Regular Files
        			else{
            			lines = fileReader(fileName);  		                
        			}
        			
        			
                    //If split[lastInput] does not exist, or has a letter, page = 1
                    try{
                        page = Integer.parseInt(split[lastInput]);
                    }
                    catch(Exception ex){
                    	page = 1;
                    }
                    
                    //Finally - Process our lines!
                    variableSwap(player, lines);
                    linesProcess(player, command, page, false);
                    
                    if (commandLogEnabled){
            			log.info("MCDocs: " + player.getName() + ": " + event.getMessage());
            		}
        		}
    			event.setCancelled(true);
        	}
        }
           
        if(split[0].equalsIgnoreCase("/mcdocs")){
        	if(player.hasPermission("mcdocs.reload") || player.isOp()){
	    		try{
	    			if(split[1].equalsIgnoreCase("-reload")){
	        			loadConfig();
	        			player.sendMessage("MCDocs has been reloaded.");
	        			logit("Reloaded by " + player.getName());
	        			event.setCancelled(true);
	        		}
	    		}
	    		catch(Exception ex){
	    			player.sendMessage("MCDocs");
	    			player.sendMessage("/mcdocs -reload  |  Reloads MCDocs.");
	        		event.setCancelled(true);
	    		}
        	}
    	}
	}
	
	private void variableSwap(Player player, ArrayList<String> lines){
			
		//Swaping out some variables with their respective replacement.
		for(String l : lines){
			
			//Basics
			String fixedLine = l.replace("%name", player.getDisplayName());
        	fixedLine = fixedLine.replace("%size", onlineCount());
        	fixedLine = fixedLine.replace("%world", player.getWorld().getName());
        	fixedLine = fixedLine.replace("%ip", player.getAddress().getAddress().getHostAddress());
        	
        	//Time Based
        	
        	//--> WorldTime
        	double worldTime = player.getWorld().getTime() + 6000;
        	double relativeTime = worldTime % 24000;
        	long worldHours = (long) (relativeTime / 1000);
        	long worldMinutes = (long) (((relativeTime % 1000) * 0.6) / 10); //I'm assuming this is how it works. lel.
        	String worldMinutesResult = "";
        	String worldTimeResult = "";
        	
        	if(worldMinutes < 10){
        		worldMinutesResult = "0" + worldMinutes;
        	}
        	else{
        		worldMinutesResult = worldMinutes + "";
        	}
        	
        	if(worldHours >= 12){
        		worldHours -= 12;
        		worldTimeResult = worldHours + ":" + worldMinutesResult + " PM";
        	}
        	else{
        		worldTimeResult = worldHours + ":" + worldMinutesResult + " AM";
        	}
        	
        	fixedLine = fixedLine.replace("%time", worldTimeResult);
        	
        	//Permissions related variables
    		String[] groupInfo = getGroupInfo(player.getName());
    		if(fixedLine.contains("%online_")){
    			String tempString = fixedLine.trim();
    			String[] firstSplit = tempString.split(" ");
    			for(String s : firstSplit){
    				if(s.contains("%online_")){
    					String[] secondSplit = s.split("_");
    					String groupName = secondSplit[1].toLowerCase();
    					fixedLine = fixedLine.replace("%online_" + secondSplit[1], onlineGroup(groupName));
    				}
    			}
    		}
    		fixedLine = fixedLine.replace("%group", groupInfo[0]);
    		try{
        		fixedLine = fixedLine.replace("%prefix", groupInfo[1]);
        		fixedLine = fixedLine.replace("%suffix", groupInfo[2]);
    		}
    		catch (Exception e){
    			fixedLine = fixedLine.replace("%prefix", "");
        		fixedLine = fixedLine.replace("%suffix", "");
    		}
        	
            //More Basics
        	fixedLine = fixedLine.replace("%online", onlineNames());
        	fixedLine = colourSwap(fixedLine);
        	fixedLine = fixedLine.replace("&#!", "&");
        	        	
        	//If the line currently in the for loop has "%include", we find out which file to load in by splitting the line up intesively.
        	ArrayList<String> files = new ArrayList<String>();
        	       	
			if (l.contains("%include") || l.contains("%news")){
				if (l.contains("%include")){
					String tempString = l.trim();
	    			String[] firstSplit = tempString.split(" ");
	    			for(String s : firstSplit){
	    				if(s.contains("%include")){
	    					s = " " + s;
	    					String[] secondSplit = s.split("%include_");
	    					s = s.replace(" ", "");
	    					fixedLine = fixedLine.replace(s, "");
	    					files.add(secondSplit[1]);
	    				}
	    			}
	    			if(!fixedLine.equals(" ")){
	    				fixedLines.add(fixedLine);
	    			}
	    			for(String f : files){
	    				includeAdd(f, player);
	    			}
				}
				if (l.contains("%news")){
					fixedLine = fixedLine.replace("%news", "");	
					if(!fixedLine.equals(" ")){
	    				fixedLines.add(fixedLine);
	    			}
					newsLine(player);
				}
        	}
			else{
				fixedLines.add(fixedLine);
			}
		}
	}
		
	private void linesProcess(Player player, String command, int page, boolean motd){        
        //Define our page numbers
        int size = fixedLines.size();
        int pages;
        
        if(size % 9 == 0){
        	pages = size / 9;
        }
        else{
        	pages = size / 9 + 1;
        }
        
        //This here grabs the specified 9 lines, or if it's the last page, the left over amount of lines.
        String commandName = command.replace("/", "");
        commandName = commandName.toUpperCase();
        String header = null;
        
        if(pages != 1){
        	//Custom Header
			header = headerFormat;
            
            //Replace variables.
            header = colourSwap(header);
            header = header.replace("%commandname", commandName);
            header = header.replace("%current", Integer.toString(page));
            header = header.replace("%count", Integer.toString(pages));
            header = header.replace("%command", command);
            header = header + " ";
            
            player.sendMessage(header);
        }
        //Some Maths.
        int highNum = (page * 9);
        int lowNum = (page - 1) * 9;
        for (int number = lowNum; number < highNum; number++){
        	if(number >= size){
        		if(!motd && pages != 1){
        			player.sendMessage(" ");
        		}
        	}
        	else{
        		player.sendMessage(fixedLines.get(number));     
        	}
        		   	
        }
	}
	
	private String[] getGroupInfo(String player){
		for(BmDocsGroups g : groupsList){
			ArrayList<String> players = g.getPlayers();
			for (String p : players){
				if(p.equalsIgnoreCase(player)){
					String[] ret = {g.getName(), g.getPrefix(), g.getSuffix()};
					return ret;
				}
			}
		}
		String[] ret = {"","",""};
		return ret;
	}
	
	private ArrayList<String> onlineFile(String url){
		
		//some variables for the method
		BmDocsOnlineFiles file = null;
		ArrayList<String> onlineLines = new ArrayList<String>();
		
		URL u;
	    InputStream is = null;
	    DataInputStream dis;
	    Date now = new Date();
	    long nowTime = now.getTime();
	    int foundFile = 0;
	    
	    //create a new list to store our wanted objects
	    ArrayList<BmDocsOnlineFiles> newOnlineFiles = new ArrayList<BmDocsOnlineFiles>();
	    
	    //go through all the existing online files found in the cache, and check if they are still under the cache limit.
	    //delete all files, and entries, that are old.
	    for(BmDocsOnlineFiles o : onlineFiles){
	    	long fileTimeMs = o.getMs();
			long timeDif = nowTime - fileTimeMs;
	    	int cacheTimeMs = cacheTime * 60 * 1000;
	    		    	
	    	if(timeDif > cacheTimeMs){
	    		File f = new File(plugin.getDataFolder(), "cache/" + fileTimeMs + ".txt");
	    		f.delete();
	    	}
	    	else{
	    		//add the objects we wish to keep.
	    		newOnlineFiles.add(o);
	    	}
	    }
	    
	    //clear out the old, and replace them with the objects we wanted to keep.
	    onlineFiles.clear();
	    for(BmDocsOnlineFiles n : newOnlineFiles){
	    	onlineFiles.add(n);
	    }

	    //now sinply go through our cache files and check if our url has been cached before...
	    for(BmDocsOnlineFiles o : onlineFiles){
	    	if(o.getURL().equalsIgnoreCase(url)){
	    		String fileName = "cache/" + Long.toString(o.getMs()) + ".txt";
	    		onlineLines = fileReader(fileName);
	    		foundFile = 1;
	    	}
	    }
	    
	    //finally if there was no cache, or the url was not in the cache, download the online file and cache it.
	    if(foundFile == 0){
	    	try{
	    		//import our online file
				u = new URL(url);
				is = u.openStream();  
				dis = new DataInputStream(new BufferedInputStream(is));
				Scanner scanner = new Scanner(dis, "UTF-8");
				while (scanner.hasNextLine()) {
                	try{
                		onlineLines.add(scanner.nextLine() + " ");
                	}
                	catch(Exception ex){
                		onlineLines.add(" ");
                	}
                }
				
				//Add our new file to the cache
				file = new BmDocsOnlineFiles(nowTime, url);
            	onlineFiles.add(file);
            	
            	//save our new file to the dir
            	PrintWriter stream = null;
            	File folder = plugin.getDataFolder();
        		String folderName = folder.getParent();		
        		try {
        			new File(plugin.getDataFolder() + "/cache/").mkdir();
        			stream = new PrintWriter(folderName + "/BM/cache/" + nowTime + ".txt");
        			for(String l : onlineLines){
        				stream.println(l);
                	}
        			stream.close();
        		} catch (FileNotFoundException e) {
        			log.info("[BukkitManager]: Error saving " + nowTime + ".txt");
        		}
            	
			}
			catch (MalformedURLException mue) {
				log.info("[BukkitManager] Ouch - a MalformedURLException happened.");
	        }
			catch (IOException ioe) {
				log.info("[BukkitManager] Oops - an IOException happened.");
			}
			finally {
		         try {
		            is.close();
		         } 
		         catch (IOException ioe) {
	         	}
			}
	    }
		//and finally return what we have found.
		return onlineLines;
	}
	
	private ArrayList<String> fileReader(String fileName){
		
		ArrayList<String> tempLines = new ArrayList<String>();
		String folderName = plugin.getDataFolder().getParent();
		
		try{
			FileInputStream fis = new FileInputStream(folderName + "/MCDocs/" + fileName);
	        Scanner scanner = new Scanner(fis, "UTF-8");
	            while (scanner.hasNextLine()) {
	            	try{
	            		tempLines.add(scanner.nextLine() + " ");
	            	}
	            	catch(Exception ex){
	            		tempLines.add(" ");
	            	}
	            }
	        scanner.close();
	        fis.close();
		}
		catch (Exception ex){
			logit("Error: File '" + fileName + "' could not be read.");
		}
				
		return tempLines;
		
	}
	
	private void logit(String message){
		if(errorLogEnabled){
			log.info("[MCDocs] " + message);
		}				
	}
	
	/*
	 * -- Variable Methods --
	 * The following methods are used for various %variables in the txt files.
	 * 
	 * basicVariableSwap: Used for dynamic file names.
	 * includeAdd: Is used to insert more lines into the current working doc.
	 * onlineNames: Finds the current online players, and using online-players-format, applies some permissions variables.
	 * onlineGroup: Finds the current online players, check if they're in the group specified, and using online-players-format, applies some permissions variables.
	 * onlineCount: Returns the current amount of users online.
	 * newsLine: Is used to insert the most recent lines (# defined in config.yml) from the defined news file (defined in the config.yml)
	 * checkIfNumber: simple try catch to determine if a space is in a command. Example: /help iconomy 2
	 * colorSwap: Uses the API to color swap instead of manually doing it.
	 */
	
	private String basicVariableSwap(Player player, String string){
		
		String[] groupInfo = getGroupInfo(player.getName());
		
		string = string.replace("%name", player.getName());
		string = string.replace("%size", onlineCount());
		string = string.replace("%world", player.getWorld().getName());
		string = string.replace("%ip", player.getAddress().getAddress().getHostAddress());    	
		string = string.replace("%group", groupInfo[0]);
		try{
			string = string.replace("%prefix", groupInfo[1]);
			string = string.replace("%suffix", groupInfo[2]);
		}
		catch (Exception e){
			string = string.replace("%prefix", "");
			string = string.replace("%suffix", "");
		}
		
		return string;
	}
	
	private void includeAdd(String fileName, Player player){
		//Define some variables
		ArrayList<String> tempLines = new ArrayList<String>();
		
		//Ok, let's import our new file, and then send them into another variableSwap [ I   N   C   E   P   T   I   O   N ]
		try {
			//Online file use
			if(fileName.contains("http")){
				ArrayList<String> onlineLines = new ArrayList<String>();
				onlineLines = onlineFile(fileName);
				for(String o : onlineLines){
					tempLines.add(o);
				}
			}
			//Regular files
			else{
				tempLines = fileReader(fileName);
			}
            //Methods inside of methods!
            variableSwap(player, tempLines);
         }	        		
         catch (Exception ex) {
        	 logit("Included file " + fileName + " not found!");
         }
	}
	
	private String onlineNames(){
		Player online[] = plugin.getServer().getOnlinePlayers();
        String onlineNames = null;
        String nameFinal = null;
        for (Player o : online){
        	
        	nameFinal = onlinePlayersFormat.replace("%name", o.getDisplayName());
        	
    		String[] groupInfo = getGroupInfo(o.getName());
    		nameFinal = nameFinal.replace("%group", groupInfo[0]);
    		nameFinal = nameFinal.replace("%prefix", groupInfo[1]);
    		nameFinal = nameFinal.replace("%suffix", groupInfo[2]);
    		
        	nameFinal = colourSwap(nameFinal);
        	
        	if (onlineNames == null){
        		onlineNames = nameFinal;
        	}
        	else{
        		onlineNames = onlineNames.trim() + "&f, " + nameFinal;
        	}
        	
        }
        return onlineNames;
	}

	private String onlineGroup(String group){
		Player online[] = plugin.getServer().getOnlinePlayers();
        String onlineGroup = null;
        String nameFinal = null;
        for (Player o : online){
        	String groupInfo[] = getGroupInfo(o.getName());
        	if (groupInfo[0].toLowerCase().equals(group)){

	        		nameFinal = onlinePlayersFormat.replace("%name", o.getDisplayName());
	        		nameFinal = nameFinal.replace("%group", groupInfo[0]);
	        		nameFinal = nameFinal.replace("%prefix", groupInfo[1]);
	        		nameFinal = nameFinal.replace("%suffix", groupInfo[2]);
	            	nameFinal = colourSwap(nameFinal);


            	if (onlineGroup == null){
            		onlineGroup = nameFinal;
            	}
            	else{
            		onlineGroup = onlineGroup + "&f, " + nameFinal;
            	}
        	}
        }
        if (onlineGroup == null){
        	onlineGroup = "";
        }
        return onlineGroup;
	}
	
	private String onlineCount(){
		Player online[] = plugin.getServer().getOnlinePlayers();
        int onlineCount = online.length;
        return Integer.toString(onlineCount);
	}	
	
	private void newsLine(Player player){
		
		ArrayList<String> newsLinesList = new ArrayList<String>();
		File folder = plugin.getDataFolder();
		String folderName = folder.getParent();
		int current = 0;
		
		try {
            FileInputStream fis = new FileInputStream(folderName + "/MCDocs/" + newsFile);
            Scanner scanner = new Scanner(fis, "UTF-8");
                while (current < newsLines) {
                	try{
                		newsLinesList.add(scanner.nextLine() + " ");
                	}
                	catch(Exception ex){
                		newsLinesList.add(" ");
                	}
                	current++;
                }
            scanner.close();
            fis.close();  
            variableSwap(player, newsLinesList);
		}	        		
		catch (Exception ex) {
    	 logit("news-file was not found.");
		}
	}
		
    private boolean checkIfNumber(String in) {
        
        try {

            Integer.parseInt(in);
        
        } catch (NumberFormatException ex) {
            return false;
        }
        
        return true;
    }
    
    private String colourSwap(String line){
    	String[] Colours = { 	"&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7",
						        "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f",
						      };
    	ChatColor[] cCode = {	ChatColor.BLACK, ChatColor.DARK_BLUE, ChatColor.DARK_GREEN, ChatColor.DARK_AQUA, ChatColor.DARK_RED, ChatColor.DARK_PURPLE, ChatColor.GOLD, ChatColor.GRAY,
						        ChatColor.DARK_GRAY, ChatColor.BLUE, ChatColor.GREEN, ChatColor.AQUA, ChatColor.RED, ChatColor.LIGHT_PURPLE, ChatColor.YELLOW, ChatColor.WHITE,
						      };
    	
    	for (int x = 0; x < Colours.length; x++) {
    		CharSequence cChk = null;
            String temp = null;

            cChk = Colours[x];
            if (line.contains(cChk)) {
                temp = line.replace(cChk, cCode[x].toString());
                line = temp;
            }
        }
        return line;
    }
    
    
    /*
	 * -- MOTD On Login -- 
	 * We produce motd information based on what is defined int he configuration.
	 */
	
	public void onPlayerJoin(PlayerJoinEvent event){
		if(motdEnabled){
			ArrayList<String> lines = new ArrayList<String>();
			lines.clear();
	    	fixedLines.clear();
	    	
			Player player = event.getPlayer();
			String[] group = getGroupInfo(player.getName());
			String fileName = null;
			
			for(BmDocsMOTD m : motdList){
				if(group[0].equalsIgnoreCase(m.getGroup())){
					fileName = m.getFile();
				}
			}
			
			if(fileName == null){
				fileName = motdList.get(0).getFile();
			}
			
			fileName = basicVariableSwap(player, fileName);
			
			//Online file use
	    	if(fileName.contains("http")){
				ArrayList<String> onlineLines = new ArrayList<String>();
				onlineLines = onlineFile(fileName);
				for(String o : onlineLines){
					lines.add(o);
				}
			}
			//Regular Files
			else{
				lines = fileReader(fileName);              
			}
	        variableSwap(player, lines);
	        linesProcess(player, "/motd", 1, true);		
		}
	}
}
