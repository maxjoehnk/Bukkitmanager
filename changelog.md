Changelog
=========
Beta 1.3.1
----------
* Implemented Chatfilter
* Fixed the NullPointerException thrown every time a command is run

Beta 1.3
--------
* Added new Statistics (Command Usage Donut Graph, Installed Addons Pie Chart, Total Command Usage Line Graph)
* Rewrite of the Database System (Faster, stable and added a way to add custom Database Systems with Addons)
* Finally all Player Commands are working as they should, even if the Player is offline (PlayerSynchronisation has to be enabled)
* Added Updater Black- and Whitelists
* Added an Autobackup Option to remove old backups (The count doesn't work properly if you enter 5 in most cases only 4 files are remaining, sometimes also 5 are remaining)
* Added Support for Notifications Plugin
* Temporary removed Scoreboards
* Started with a huge API for the Scripting Engine, neither finished nor documented yet

Beta 1.2
--------
* Fixed some bugs
* Implemented Option for Scoreboards (still WiP, online player count doesn't updates, not all planned options implemented)
* Added JavaScript Scripting Engine
* Switched from Metrics to EMetrics (Enhancment of the Statistics)
* Addon support implemented
* Removed some Features from the Dev Mode, will be released as addon

Beta 1.1
--------
* Permanently fixed the Alias Bug (If there is an MC update Bukkitmanager won't break but display an Error)
* Improved the Autosave and Autobackupsystem, should work way better now.
* Added FTP Backup Option
* Added new bm server commands for server information/load

Beta 1.0
--------
* Fixed the 1.4.5 Bug (reappears on every mc update so i will release a new one 1-2 days after a mc update)
* Added a toggle for player synchronisation (if a player is offline and you set a value it gates cached in the database)
* Manuell backup now uses the synchronisation settings of the autobackup
* Some small improvements
* Worked on the webinterface (to unsecure for official release, the webinterface itself can be found on github)

Alpha 1.7.1
-----------
* Changed the version from 1.7 - Dev 2 to 1.7.1
* Added an autoupdater (currently only for Bukkitmanager itself)

Alpha 1.7
---------
* Updated to Minecraft 1.3.1
* New optimized commandsystem
* Rechanged the name from BukkitManager to Bukkitmanager
* Added Commandusage Statistics

Alpha 1.6
---------
* Updated /bm help Command (Displays all Commands)
* Added mcstats.org Stats
* Added Jide Librarys and registration key
* Added Block and Enchantment Logger
* Added Features to the Local Swing GUI (Playerlist, Pluginlist, Console) (dev-mode)
* Modified the Remote Swing GUI (Looks better now, but doesn't work :D) (dev-mode)
* Added Custom-Messages
* Added Fakepluginlist
* Small Improvements
* Removed the SQLite and MySQL librarys, now using the in craftbukkit included librarys

Alpha 1.5.2
-----------
* Updated to Minecraft 1.2.5
* Added an Option for Command Aliases (/plugin instead of /bm plugin) to the Dev-Mode. Will be official implemented in Alpha 1.6.
* Added MySQL Support for Dev-Mode. Could be buggy, because its not tested yet.
* /bm player time now accepts Strings like midday or night.
* Automessage doesn't produces Errors, if the Automessage File is empty.
* The Swing Remote and Webinterfaceserver in Dev-Mode don't crash after reload
* bukkitmanager.jar can be started without Bukkit to access the Remote Swing GUI. Has no Features yet. You can only connect to the Server.

Alpha 1.5.1
-----------
* Fixed the Bug, where Displayname and Listname aren't saved.
* Added a Option to the Config, which enables the 'Dev-Mode' where Features, which aren't finished, are enabled.
* Added a Option to the Config, for single World Backups.

Alpha 1.5
---------
* Updated the Permissions Handler for more Permissionssystems and added some new Configoptions for it.
* New Stuff in the config.yml
* Implementation of the player commands

Alpha 1.4.1
-----------
* Fixed a Bug in the plugin.yml, were the Plugin doesn't start.

Alpha 1.4
---------
* Autobackup & Autosave added
* Some other small Improvements
* Bug fixes

Alpha 1.3
---------
* Config works
* Config has an Autoupdate function
* Added an Automessage function
* Update for CB 1.1-R1
* Help now much better:)
* Cleaned up the code
* Updated Events for the New Event System

Alpha 1.2
---------
* Added /bm bukkit update
* Bukkit updatecheck on start

Alpha 1.1
---------
* Added /bm plugin restart (plugin)
* Better /bm help
* Updatet to Minecraft 1.0.0
* Added Config

Alpha 1.0
---------
* First Release
* Permissions works
* Help works
* All /bm plugin commands working
* /bm config works