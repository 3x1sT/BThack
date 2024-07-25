package com.bt.BThack.api.Social.Friends;


import com.bt.BThack.BThack;
import com.bt.BThack.api.Social.SocialUtils;
import net.minecraft.entity.player.EntityPlayer;

public class FriendsUtils {
    public static boolean correctAdd = false;
    public static boolean correctRemove = false;

    public static void saveFriends() {
        SocialUtils.save("Friends/Friends.txt", FriendList.Friends);
    }

    public static void addFriend(String friend) {
        FriendList.loadFriends();
        correctAdd = false;
        if (!FriendList.Friends.contains(friend)) {
            FriendList.Friends.add(friend);
            saveFriends();
            correctAdd = true;
        } else {
            BThack.log("This friend already exists!");
        }

    }

    public static void removeFriend(String friend) {
        FriendList.loadFriends();
        correctRemove = false;
        if (FriendList.Friends.contains(friend)) {
            FriendList.Friends.remove(friend);
            saveFriends();
            correctRemove = true;
        } else {
            BThack.log("This friend doesn't exist!");
        }

    }

    public static boolean isFriend(EntityPlayer player) {
        String name = player.getDisplayNameString();
        return FriendList.Friends.contains(name);
    }

    public static boolean isFriend(String name) {
        return FriendList.Friends.contains(name);
    }
}
