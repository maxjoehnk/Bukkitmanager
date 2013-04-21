package org.efreak.bukkitmanager.tutorials;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Tutorial {

	private List<TutorialPage> pages;
	private String name;
	private String perm = null;
	
	public Tutorial(File file) {
		try {		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			name = doc.getDocumentElement().getAttribute("name");
			if (doc.getDocumentElement().hasAttribute("perm")) perm = doc.getDocumentElement().getAttribute("perm");
			NodeList nodeList = doc.getElementsByTagName("page");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) pages.add(new TutorialPage((Element) node));
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return perm;
	}
	
	public boolean hasPermission() {
		return perm != null;
	}
}