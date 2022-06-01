/*
 * Decompiled with CFR 0_133.
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Util;
import java.util.List;

public class Messages {
    public static String plugin_header = "&e\u00ab--------[&7&l&o MineCraft Wew &e]--------\u00bb";
    public static String plugin_footer = "&e&e&m-------------------------------------";

    public static String plugin_heade() {
        return Util.rColor(plugin_header);
    }

    public static String plugin_footer() {
        return Util.rColor(plugin_footer);
    }

    public static String No_Console() {
        return Util.rColor(ReferralSystem.messages.getString("No-Console"));
    }

    public static String Claim_No_Permission() {
        return ReferralSystem.messages.getString("Claim-No-Permission");
    }

    public static String Claim_No_Points() {
        return ReferralSystem.messages.getString("Claim-No-Points");
    }

    public static String Top_Players() {
        return ReferralSystem.messages.getString("Top-Player");
    }

    public static String Reward_Given() {
        return ReferralSystem.messages.getString("Reward-Given");
    }

    public static String No_Permission() {
        return Util.rColor(ReferralSystem.messages.getString("No-Permission"));
    }

    public static String Command_Use() {
        return Util.rColor(ReferralSystem.messages.getString("Command-Use"));
    }

    public static String Description_Use() {
        return Util.rColor(ReferralSystem.messages.getString("Description-Use"));
    }

    public static String Help_CommandList() {
        return Util.rColor(ReferralSystem.messages.getString("Help-CommandList"));
    }

    public static String Help_Command_Help() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Help"));
    }

    public static String Help_Description_Help() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Help"));
    }

    public static String Help_Command_Code() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Code"));
    }

    public static String Help_Description_Code() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Code"));
    }

    public static String Help_Command_Claim() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Claim"));
    }

    public static String Help_Description_Claim() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Claim"));
    }

    public static String Help_Command_Info() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Info"));
    }

    public static String Help_Description_Info() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Info"));
    }

    public static String Help_Command_Top() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Top"));
    }

    public static String Help_Description_Top() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Top"));
    }

    public static String Help_Command_AddPoints() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-AddPoints"));
    }

    public static String Help_Description_AddPoints() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-AddPoints"));
    }

    public static String Help_Command_SetPoints() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-SetPoints"));
    }

    public static String Help_Description_SetPoints() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-SetPoints"));
    }

    public static String Help_Command_Reload() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Reload"));
    }

    public static String Help_Description_Reload() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Reload"));
    }

    public static String Help_Command_Activate() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Command-Activate"));
    }

    public static String Help_Description_Activate() {
        return Util.rColor(ReferralSystem.messages.getString("Help-Description-Activate"));
    }

    public static String Generating_Code() {
        return ReferralSystem.messages.getString("Generating-Code");
    }

    public static String Your_Code() {
        return ReferralSystem.messages.getString("Your-Code");
    }

    public static String Your_Referrals() {
        return ReferralSystem.messages.getString("Your-Referrals");
    }

    public static String Your_Referral_Points() {
        return ReferralSystem.messages.getString("Your-Referral-Points");
    }

    public static String Error_Code() {
        return ReferralSystem.messages.getString("Error-Code");
    }

    public static String Max_IP_Limit() {
        return ReferralSystem.messages.getString("Max-IP-Limit");
    }

    public static String No_Use_Your_Code() {
        return ReferralSystem.messages.getString("No-Use-Your-Code");
    }

    public static String Checking_Code() {
        return ReferralSystem.messages.getString("Checking-Code");
    }

    public static String Activate_Code() {
        return ReferralSystem.messages.getString("Activate-Code");
    }

    public static String Reward_Player() {
        return ReferralSystem.messages.getString("Reward-Player");
    }

    public static String Already_Used() {
        return ReferralSystem.messages.getString("Already-Used");
    }

    public static String Reward_Referrer() {
        return ReferralSystem.messages.getString("Reward-Referrer");
    }

    public static String No_Claim() {
        return ReferralSystem.messages.getString("No-Claim");
    }

    public static String Command_AddPoints_Use() {
        return ReferralSystem.messages.getString("Command-AddPoints-Use");
    }

    public static String Command_SetPoints_Use() {
        return ReferralSystem.messages.getString("Command-SetPoints-Use");
    }

    public static String Command_Error() {
        return ReferralSystem.messages.getString("Command-Error");
    }

    public static String No_Register() {
        return ReferralSystem.messages.getString("No-Register");
    }

    public static String No_Numb() {
        return ReferralSystem.messages.getString("No-Numb");
    }

    public static String Points_Added() {
        return ReferralSystem.messages.getString("Points-Added");
    }

    public static String Points_Set() {
        return ReferralSystem.messages.getString("Points-Set");
    }

    public static String Requires_PlayTime() {
        return ReferralSystem.messages.getString("Requires-PlayTime");
    }

    public static String PlayTime() {
        return ReferralSystem.messages.getString("PlayTime");
    }

    public static String MenuList() {
        return ReferralSystem.messages.getString("MenuList");
    }

    public static List<String> MenuList_Lore() {
        return ReferralSystem.messages.getStringList("MenuList-Lore");
    }

    public static String MenuList_Next() {
        return ReferralSystem.messages.getString("MenuList-Next");
    }

    public static String MenuList_Back() {
        return ReferralSystem.messages.getString("MenuList-Back");
    }

    public static String Help_Command_List() {
        return ReferralSystem.messages.getString("Help-Command-List");
    }

    public static String Help_Description_List() {
        return ReferralSystem.messages.getString("Help-Description-List");
    }

    public static String MenuList_Name() {
        return ReferralSystem.messages.getString("MenuList-Name");
    }
}

