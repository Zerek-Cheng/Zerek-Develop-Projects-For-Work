package com.WeiBoss.bossshoptr.File;

import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;

public class Message {
    private static Main plugin = Main.instance;
    public final static String NoPermission = WeiUtil.onReplace(plugin.message.getString("Message.NoPermission"));
    public final static String Year = WeiUtil.onReplace(plugin.message.getString("Message.Year"));
    public final static String Month = WeiUtil.onReplace(plugin.message.getString("Message.Month"));
    public final static String Day = WeiUtil.onReplace(plugin.message.getString("Message.Day"));
    public final static String Hour = WeiUtil.onReplace(plugin.message.getString("Message.Hour"));
    public final static String Minute = WeiUtil.onReplace(plugin.message.getString("Message.Minute"));
    public final static String Second = WeiUtil.onReplace(plugin.message.getString("Message.Second"));
    public final static String Color = WeiUtil.onReplace(plugin.message.getString("Message.Color"));
}