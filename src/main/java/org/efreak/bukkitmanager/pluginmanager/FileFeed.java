package org.efreak.bukkitmanager.pluginmanager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class FileFeed {
		
	private URL feedUrl;
	private HashMap<String, File> files;
	
	public FileFeed(String pluginName) throws IOException, XMLStreamException {
		files = new HashMap<String, File>();
		feedUrl = new URL("http://dev.bukkit.org/bukkit-plugins/" + pluginName + "/files.rss");
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		InputStream in = feedUrl.openStream();
		XMLEventReader eventReader = xmlFactory.createXMLEventReader(in);
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextTag();
			if (event.isStartElement()) {
				if (event.asStartElement().getName().toString().equals("item")) {
					String title = eventReader.nextTag().asStartElement().getName().toString();
					URL link = new URL(eventReader.nextTag().asStartElement().getName().toString());
					files.put(title, new File(title, link));
				}
			}
		}
	}
	
	public HashMap<String, File> getFilesMap() {
		return files;
	}
	
	public List<File> getFiles() {
		return new ArrayList<File>(files.values());
	}
	
	public List<String> getFileNames() {
		return new ArrayList<String>(files.keySet());
	}
	
	public File getFile(String name) {
		return files.get(name);
	}
	
	public File getNewestFile() {
		return null;
	}
	
	public String getNewestFileName() {
		return "";
	}
}
