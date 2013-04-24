package com.tractorx.dita.keyspace;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class KeyScope {

    /**
     * The key definitions in this scope.
     */
    private Map<String, List<KeyDefinition>> keydefs = new HashMap<String, List<KeyDefinition>>();

    /**
     * The parent scope. Will be null for the root scope.
     */
    private KeyScope parent;

    /**
     * Child scopes.
     */
    private List<KeyScope> children = new ArrayList<KeyScope>();

    /**
     * The names for this scope.
     */
    private String[] names;
    
    /** The URI where this scope starts. */
    private String uri;

    /**
     * Whether this is a 'peer' key scope for cross-deliverable linking.
     */
    private boolean peerMode = false;

    public KeyScope(String uri) {
        this(XMLUtils.load(uri));
    }

    public KeyScope(URI uri) {
        this(XMLUtils.load(uri));
    }

    public KeyScope(Document doc) {
        this(null, doc.getDocumentElement(), 0, false);
    }

    private KeyScope(KeyScope parent, Element el, int startingDepth,
            boolean peerMode) {
        this.parent = parent;
        this.names = StringUtils.split(el.getAttribute("keyscope"));
        this.peerMode = peerMode;
        this.uri = el.getOwnerDocument().getDocumentURI();

        // Look for keydefs on the node itself.
        checkForKeydef(el, startingDepth);

        // If this is a mapref, traverse.
        checkForMapref(el, startingDepth);

        // Scan the subtree.
        processChildren(el, startingDepth);

    }
    
    public boolean isPeerMode() {
        return peerMode;
    }

    public List<KeyScope> getChildren() {
        return children;
    }

    public String[] getNames() {
        return names;
    }

    public KeyScope getParent() {
        return parent;
    }

    /**
     * Gets the effective definition for the given key.
     * 
     * @param keyName The key name
     * @return The effective definition.
     */
    public KeyDefinition getKeydef(String keyName) {
        // First delegate to the parent.
        KeyDefinition keydef = parent == null ? null : parent
                .getKeydef(keyName);

        // If the parent doesn't override, get the local definition. First one
        // wins.
        if (keydef == null && keydefs.containsKey(keyName)) {
            keydef = keydefs.get(keyName).get(0);
        }
        return keydef;
    }

    public Iterator<String> getKeyNames(boolean includeParents) {
        if (includeParents && parent != null) {
            Set<String> parentKeys = new HashSet<String>();
            for (KeyScope p = this; p != null; p = p.parent) {
                parentKeys.addAll(p.keydefs.keySet());
            }
            return parentKeys.iterator();

        } else {
            return keydefs.keySet().iterator();
        }
    }

    /**
     * Checks whether the given element is a key definition and adds it if so.
     * 
     * @param el The element to check.
     */
    private boolean checkForKeydef(Element el, int mapDepth) {
        if (!el.hasAttribute("keys")) return false;

        KeyDefinition keydef = new KeyDefinition(el.getAttribute("keys"), el,
                mapDepth, peerMode);
        for (String key : keydef.getKeyNames()) {
            addKeydef(key, keydef, mapDepth);
        }
        return true;
    }

    /**
     * Adds the given key definition to the key space.
     * 
     * @param key The key name.
     * @param keydef The definition.
     */
    private void addKeydef(String key, KeyDefinition keydef, int mapDepth) {
        List<KeyDefinition> defs = keydefs.get(key);
        if (defs == null) {
            defs = new ArrayList<KeyDefinition>();
            keydefs.put(key, defs);

        }

        // If this definition is shallower in the map
        // structure, it takes precedence.
        if (defs.size() > 0 && mapDepth < defs.get(0).getMapDepth()) {
            defs.add(0, keydef);
        } else {
            defs.add(keydef);
        }
    }

    /**
     * Since this is just a proof of concept we're not going to validate or load
     * DTDs, so we'll play some dirty tricks to figure out if a given element is
     * a mapref instead of doing it 'right'.
     * 
     * @param el The element.
     * @return Whether to treat it as a mapref.
     */
    private boolean isMapref(Element el) {
        if (!el.hasAttribute("href")) return false;
        return "mapref".equals(el.getNodeName())
                || "ditamap".equals(el.getAttribute("format"))
                || el.getAttribute("href").endsWith(".ditamap");
    }

    /**
     * Checks whether the element is a mapref and traverses it.
     * 
     * @param el The element
     */
    private void checkForMapref(Element el, int mapDepth) {
        if (!isMapref(el)) {
            return;
        }

        URI baseUri;
        try {
            baseUri = new URI(el.getBaseURI());
        } catch (URISyntaxException e) {
            baseUri = new File(el.getBaseURI()).toURI();
        }
        URI fileUri = baseUri.resolve(el.getAttribute("href"));
        
        for (KeyScope p = this ; p != null ; p = p.getParent()) {
            if (p.uri.equals(fileUri.toString())) {
                // IT'S A TRAP!!!
                return;
            }
        }
        
        Document doc = XMLUtils.load(fileUri);

        Element root = doc.getDocumentElement();
        // If the root is a key scope and the reference is a key scope, don't
        // define a new scope. Just merge the names.
        if (root.hasAttribute("keyscope") && el.hasAttribute("keyscope")) {
            // Just merge the names; we're processing a new scope
            // on a mapref.
            String[] newNames = StringUtils
                    .split(root.getAttribute("keyscope"));
            for (String newName : newNames) {
                if (!ArrayUtils.contains(this.names, newName)) {
                    this.names = ArrayUtils.add(this.names, newName);
                }
            }
            processChildren(root, mapDepth + 1);

        } else {
            // Not a scope-from-a-scope; process normally.
            processNode(root, mapDepth + 1);
        }
    }

    /**
     * Processes a child scope, adding implicitly-defined qualified key names to
     * this scope.
     * 
     * @param el The scope-defining element.
     */
    private void processChildScope(Element el, int mapDepth) {
        // Constructor walks the key scope's subtree.
        boolean childIsPeer = peerMode || isMapref(el)
                && "peer".equals(el.getAttribute("scope"));
        KeyScope childScope = new KeyScope(this, el, mapDepth, childIsPeer);
        children.add(childScope);

        // Now add 'implicit' definitions for those keys to
        // this key space, prefixed by the key scope name(s)
        Iterator<String> scopedKeys = childScope.getKeyNames(false);
        while (scopedKeys.hasNext()) {
            String scopedKey = scopedKeys.next();
            KeyDefinition keydef = childScope.getKeydef(scopedKey);
            for (String scopeName : childScope.getNames()) {
                String qualifiedKey = scopeName + "." + scopedKey;
                // Create a new keydef with MAX_INT depth to ensure that
                // parent scoped keys take precedence.
                KeyDefinition newKeydef = new KeyDefinition(qualifiedKey,
                        keydef.getElement(), Integer.MAX_VALUE, childScope.peerMode);
                addKeydef(qualifiedKey, newKeydef, mapDepth);
            }
        }
    }

    /**
     * Processes the given element and its children.
     * 
     * @param el The element.
     */
    private void processNode(Element el, int mapDepth) {
        if (el.hasAttribute("keyscope")) {
            processChildScope(el, mapDepth);

        } else {
            checkForKeydef(el, mapDepth);
            checkForMapref(el, mapDepth);
            processChildren(el, mapDepth);
        }
    }

    /**
     * Processes the children of the given element.
     * 
     * @param el The element.
     */
    private void processChildren(Element el, int mapDepth) {
        for (Node c = el.getFirstChild(); c != null; c = c.getNextSibling()) {
            if (c.getNodeType() == Node.ELEMENT_NODE) {
                processNode((Element) c, mapDepth);
            }
        }
    }
}
