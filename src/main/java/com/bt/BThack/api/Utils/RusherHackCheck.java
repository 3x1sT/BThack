package com.bt.BThack.api.Utils;

import com.bt.BThack.BThack;

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class RusherHackCheck {

    public static boolean isRusherHack = false;

    public static void check() {
        try {
            Enumeration<URL> rusherHackCheckList = RusherHackCheck.class.getClassLoader().getResources("org.rusherhack.client.RusherHack.class");
            List<URL> urlList = Collections.list(rusherHackCheckList);
            if (!urlList.isEmpty()) {
                BThack.initErr("RusherHack has been found! All functions conflicting with RusherHack have been disabled and blocked!");
                isRusherHack = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
