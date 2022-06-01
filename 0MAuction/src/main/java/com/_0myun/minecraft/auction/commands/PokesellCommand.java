package com._0myun.minecraft.auction.commands;

import com._0myun.minecraft.auction.LangManager;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.event.AuctionPreSellEvent;
import com._0myun.minecraft.auction.godtype.GoodType;
import com._0myun.minecraft.auction.godtype.GoodTypeManager;
import com._0myun.minecraft.auction.payway.PaywayManager;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PokesellCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length != 5) {
            p.sendMessage(LangManager.getLang("lang1"));
            return true;
        }
        int pokemonSpace = Integer.valueOf(args[1]);
        int price = Integer.valueOf(args[2]);
        int startPrice = Integer.valueOf(args[3]);
        int timeout = Integer.valueOf(args[4]);
        if (pokemonSpace < 1 || pokemonSpace > 6) {
            p.sendMessage(LangManager.getLang("lang5"));
            return true;
        }
        Pokemon pokemon = Pixelmon.storageManager.getParty(p.getUniqueId()).get(pokemonSpace - 1);
        if (pokemon == null) {
            p.sendMessage(LangManager.getLang("lang4"));
            return true;
        }
        GoodType pokemonType = GoodTypeManager.getGodType().get("pokemon");
        AuctionPreSellEvent preEvent = new AuctionPreSellEvent(p
                , PaywayManager.getPayway().get("gold")
                , pokemonType.toString(pokemon)
                , timeout
                , price
                , startPrice
                , pokemonType
        );
        Bukkit.getPluginManager().callEvent(preEvent);
        if (preEvent.isCancelled()) {
            return true;
        }
        Pixelmon.storageManager.getParty(p.getUniqueId()).set(pokemonSpace-1,null);
        OrderManager.sell(p.getName()
                , PaywayManager.getPayway().get("gold")
                , pokemon
                , preEvent.getTimeout() * 60 * 60 * 1000
                , preEvent.getPrice()
                , preEvent.getStartPrice());

        p.sendMessage(LangManager.getLang("lang3"));
        p.chat(LangManager.getLang("lang34"));
        return true;
    }
}
