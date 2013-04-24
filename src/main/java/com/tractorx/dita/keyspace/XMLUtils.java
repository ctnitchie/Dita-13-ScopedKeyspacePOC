package com.tractorx.dita.keyspace;

import java.net.URI;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLUtils {
    
    private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    static {
        dbf.setValidating(false);
        dbf.setExpandEntityReferences(true);
        dbf.setNamespaceAware(true);
        
        try {
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Document load(String uri) {
        return load(new InputSource(uri));
    }
    
    public static Document load(URI uri) {
        return load(new InputSource(uri.toString()));
    }
    
    public static Document load(URL url) {
        return load(new InputSource(url.toString()));
    }
    
    public static Document load(InputSource is) {
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(is);
            
        } catch (Exception e) {
            // Quick and dirty. Turn it into an unchecked exception.
            throw new RuntimeException(e);
        }
    }
    
}
