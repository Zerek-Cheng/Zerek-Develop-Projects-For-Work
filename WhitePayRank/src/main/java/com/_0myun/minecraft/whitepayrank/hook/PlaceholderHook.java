/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.mc9y.pixelmonpvp.PixelmonPvp
 *  com.mc9y.pixelmonpvp.data.PlayerData
 *  me.clip.placeholderapi.external.EZPlaceholderHook
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com._0myun.minecraft.whitepayrank.hook;

import com._0myun.minecraft.whitepayrank.WhitePayRank;
import com.github.shawhoi.whitepay.WhitePay;
import com.github.shawhoi.whitepay.api.WhitePayAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderHook extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "whitepayrank";
    }

    @Override
    public String getAuthor() {
        return "0MYUN";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    public String onPlaceholderRequest(Player player, String s) {
        try {
            Integer amount = new WhitePayAPI(WhitePay.getInstance()).getPlayerRechargeAmount(player.getName());
            if (amount == null) amount = 0;
            int max = 0;
            for (String key : WhitePayRank.INSTANCE.getConfig().getConfigurationSection("rank").getKeys(false)) {
                if (amount >= Integer.valueOf(key) && Integer.valueOf(key) > max) {
                    max = Integer.valueOf(key);
                }
            }
            return WhitePayRank.INSTANCE.getConfig().getString("rank." + max + ".name");
        } catch (Exception ex) {
            return "";
        }
    }
}

