package com.tractorx.dita.keyspace;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestMaps {

    protected void exec(String path) throws Exception {
        String heading = "KEY SPACES FOR: " + path;
        System.out.println(heading);
        System.out.println(StringUtils.repeat('=', heading.length()));
        System.out.println();
        URL mapUrl = TestMaps.class.getResource(path);
        Main.main(new String[] {mapUrl.toString()});
        System.out.println(StringUtils.repeat('*', 72) + "\n");
    }
    
    @Test
    public void runTests() throws Exception {
        exec("/Example2.1.ditamap");
        exec("/Example2.2.ditamap");
        exec("/Example3.ditamap");
        exec("/Example4.ditamap");
        exec("/Example5/map-1.ditamap");
        exec("/Example6.ditamap");
        exec("/Example7/parent.ditamap");
        exec("/Tractors/NoScopes/AllModels.ditamap");
        exec("/Tractors/BrokenScopes/AllModels.ditamap");
        exec("/Tractors/FixedScopes/AllModels.ditamap");
        exec("/CrossPubs/map-a/map-a.ditamap");
        exec("/CrossPubs/map-b/map-b.ditamap");
        exec("/CrossPubs/map-ab/map-ab-omnibus.ditamap");
        exec("/CrossPubs/map-ab/map-ab-selective-reuse.ditamap");
    }
}
