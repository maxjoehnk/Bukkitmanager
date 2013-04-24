package org.efreak.bukkitmanager.tutorials;

import org.w3c.dom.Element;

public class TutorialPage {

    private String text;
    private TutorialAction action;

    public TutorialPage(Element element) {
        text = element.getElementsByTagName("text").item(0).getTextContent();
    }

    public String getText() {
        return text;
    }

}
