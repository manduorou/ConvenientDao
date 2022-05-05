package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.config.Config;
import junit.framework.TestCase;
import org.junit.Test;

public class PtyMasterTest extends TestCase {
    @Test
    public void test1(){
        PtyMaster.autoConfig(null, Config.class);
    }
    @Test
    public void test2(){
        PtyMaster.getInstance(null).autoConfig(null);
    }
    @Test
    public void test3(){
    }
}