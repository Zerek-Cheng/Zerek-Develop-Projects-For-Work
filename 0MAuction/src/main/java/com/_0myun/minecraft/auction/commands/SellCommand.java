package com._0myun.minecraft.auction.commands;

import com._0myun.minecraft.auction.LangManager;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.event.AuctionPreSellEvent;
import com._0myun.minecraft.auction.godtype.GoodType;
import com._0myun.minecraft.auction.godtype.GoodTypeManager;
import com._0myun.minecraft.auction.payway.PaywayManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length != 4) {
            p.sendMessage(LangManager.getLang("lang1"));
            return true;
        }
        ItemStack itemInHand = p.getItemInHand();
        if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
            p.sendMessage(LangManager.getLang("lang2"));
            return true;
        }
        int price = Integer.valueOf(args[1]);
        int startPrice = Integer.valueOf(args[2]);
        int timeout = Integer.valueOf(args[3]);


        GoodType type = GoodTypeManager.getGodType().get("item");
        AuctionPreSellEvent preEvent = new AuctionPreSellEvent(p
                , PaywayManager.getPayway().get("gold")
                , type.toString(itemInHand)
                , timeout
                , price
                , startPrice
                , type
        );
        Bukkit.getPluginManager().callEvent(preEvent);
        if (preEvent.isCancelled()) {
            return true;
        }


        OrderManager.sell(p.getName()
                , PaywayManager.getPayway().get("gold")
                , itemInHand.clone()
                , preEvent.getTimeout() * 60 * 60 * 1000
                , preEvent.getPrice()
                , preEvent.getStartPrice());
        itemInHand.setAmount(0);
        itemInHand.setType(Material.AIR);

        p.sendMessage(LangManager.getLang("lang3"));
        p.chat(LangManager.getLang("lang34"));
        return true;
    }
}
