package com.bt.BThack.api.Social.Friends;


import com.bt.BThack.api.Social.SocialUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FriendList {
    public static ArrayList<String> Friends = new ArrayList<>();

    public static void loadFriends() {
        SocialUtils.load("Friends/Friends.txt", Friends);
    }
}
