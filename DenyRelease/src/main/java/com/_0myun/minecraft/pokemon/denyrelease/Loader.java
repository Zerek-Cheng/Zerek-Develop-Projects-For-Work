package com._0myun.minecraft.pokemon.denyrelease;

import catserver.api.bukkit.event.ForgeEvent;
import com.pixelmonmod.pixelmon.api.events.PixelmonSendOutEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class Loader implements Listener {
    public Loader() {
        Bukkit.getPluginManager().registerEvents(this, Main.INSTANCE);
    }

    @EventHandler
    public void onRelease(ForgeEvent forgeEvent) {
        Event event = forgeEvent.getForgeEvent();
        if (!(event instanceof PixelmonSendOutEvent)) return;
        PixelmonSendOutEvent e = (PixelmonSendOutEvent) event;
        //System.out.println(e.pokemon.getPixelmonIfExists());
        EntityPlayerMP emp = e.player;
        Pokemon pokemon = e.pokemon;
        if (emp == null || pokemon == null) return;
        if (pokemon.getPixelmonIfExists() != null) return;
        Player player = Bukkit.getPlayer(emp.getPersistentID());
        List<String> rules = Main.INSTANCE.getConfig().getStringList("rules." + player.getWorld().getName());
        if (rules == null || rules.isEmpty()) return;

        e.setCanceled(true);
        e.setResult(Event.Result.DENY);
        player.sendMessage(Main.INSTANCE.getConfig().getString("lang"));
/*        NBTTagCompound nbt = new NBTTagCompound();
        pokemon.writeToNBT(nbt);
        String name = nbt.func_74779_i("Name");
        //System.out.println(name);
        if (rules.contains(name)) {
            e.setCanceled(true);
            e.setResult(Event.Result.DENY);
            player.sendMessage(Main.INSTANCE.getConfig().getString("lang"));
        }*/
    }
}
