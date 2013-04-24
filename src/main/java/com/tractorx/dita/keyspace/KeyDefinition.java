package com.tractorx.dita.keyspace;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

public class KeyDefinition {

    private Element element;

    private String[] keyNames;
    
    private int mapDepth;
    
    private boolean peer;
    
    public KeyDefinition(String keyNames, Element element, int mapDepth, boolean peer) {
        this.element = element;
        this.keyNames = StringUtils.split(keyNames);
        this.mapDepth = mapDepth;
        this.peer = peer;
    }

    public Element getElement() {
        return element;
    }
    
    public boolean isPeer() {
        return peer;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getHref() {
        return element.getAttribute("href");
    }

    public String getId() {
        return element.getAttribute("id");
    }

    public String[] getKeyNames() {
        return keyNames;
    }
    
    public int getMapDepth() {
        return mapDepth;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getHref() != null && !getHref().isEmpty())
            sb.append(getHref());
        else if (getId() != null && !getId().isEmpty()) {
            sb.append("@id=\"");
            sb.append(getId());
            sb.append("\"");
            
        } else if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            sb.append(element.getTextContent().trim());
            
        } else {
            sb.append("no href or id or text");
        }
        if (peer) {
            sb.append(" (peer)");
        }
        return sb.toString();
    }
}
