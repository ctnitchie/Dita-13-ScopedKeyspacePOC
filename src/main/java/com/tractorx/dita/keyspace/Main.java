package com.tractorx.dita.keyspace;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out
                    .println("Specify the location of a root map to process for key scopes.");
            return;
        }
        KeyScope keyspace = new KeyScope(args[0]);
        
        writeKeySpace(keyspace);
    }
    
    private static void writeKeySpace(KeyScope keySpace) {
        String name = getKeySpaceName(keySpace);
        System.out.println(name);
        System.out.println(StringUtils.repeat('-', name.length()));
        Iterator<String> keys = keySpace.getKeyNames(true);
        
        // Sort first, for legibility.
        Set<String> keySet = new TreeSet<String>();
        while (keys.hasNext()) {
            keySet.add(keys.next());
        }
        
        for (String key : keySet) {
            KeyDefinition keydef = keySpace.getKeydef(key);
            System.out.println("* " + key + ": " + keydef);
        }
        System.out.println();
        
        for (KeyScope child : keySpace.getChildren()) {
            if (!child.isPeerMode())
                writeKeySpace(child);
        }
    }
    
    private static String getKeySpaceName(KeyScope keyspace) {
        if (keyspace.getParent() == null)
            return "ROOT (anonymous)";
        StringBuilder sb = new StringBuilder(keyspace.getNames()[0]);
        boolean hasMulti = keyspace.getNames().length > 1;
        for (KeyScope p = keyspace.getParent() ; p.getParent() != null ; p = p.getParent()) {
            sb.insert(0, ".");
            sb.insert(0, p.getNames()[0]);
            if (p.getNames().length > 1)
                hasMulti = true;
        }
        if (hasMulti)
            sb.append(" (and other names too)");
        return sb.toString();
    }
}
