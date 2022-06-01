package com._0myun.minecraft.pokemonspawnbroadcast;

import catserver.api.bukkit.event.ForgeEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.LegendarySpawnEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.spawning.SpawnAction;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import net.minecraft.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onSuccessLegen(ForgeEvent tmpe) {
        if (!(tmpe.getForgeEvent() instanceof LegendarySpawnEvent.DoSpawn)) return;
        LegendarySpawnEvent.DoSpawn event = (LegendarySpawnEvent.DoSpawn) tmpe.getForgeEvent();
        SpawnAction<? extends Entity> action = event.action;
        System.out.println("action.getOrCreateEntity().getClass().getName() = " + action.getOrCreateEntity().getClass().getName());
        System.out.println("action.getOrCreateEntity().getBukkitEntity() = " + action.getOrCreateEntity().getBukkitEntity());
        String worldName = action.spawnLocation.location.world.getWorld().getName();
        int x = action.spawnLocation.location.pos.func_177958_n();
        int y = action.spawnLocation.location.pos.func_177956_o();
        int z = action.spawnLocation.location.pos.func_177952_p();
        if (!(action.getOrCreateEntity() instanceof EntityPixelmon)) return;
        EntityPixelmon pixelmon = (EntityPixelmon) action.getOrCreateEntity();
        //NBTTagCompound nbt = pixelmon.serializeNBT();
        System.out.println("pixelmon = " + pixelmon.getLocalizedName());
        if (!getConfig().getStringList("allow").contains(pixelmon.getLocalizedName())) return;
        Location pixelmonLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

        double distance = Double.MAX_VALUE;
        Player player = null;
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        System.out.println(111);
        for (Player p : onlinePlayers) {
            Location loc = p.getLocation();
            if (!pixelmonLocation.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) continue;
            if (pixelmonLocation.distance(loc) < distance) {
                distance = pixelmonLocation.distance(loc);
                player = p;
            }
        }
        System.out.println(player);
        Pokemon pokemon = pixelmon.getStoragePokemonData();
        IVStore iVs = pokemon.getIVs();
        EVStore eVs = pokemon.getEVs();
        String displayName = pixelmon.getLocalizedName();
        String nature = pokemon.getNature().getLocalizedName();
        String growth = pokemon.getGrowth().getLocalizedName();
        Moveset moveset = pokemon.getMoveset();
        String moveName1 = "无", moveName2 = "无", moveName3 = "无", moveName4 = "无";
        if (moveset.get(0) != null && moveset.get(0).baseAttack != null)
            moveName1 = moveset.get(0).baseAttack.getLocalizedName();
        if (moveset.get(1) != null && moveset.get(1).baseAttack != null)
            moveName2 = moveset.get(1).baseAttack.getLocalizedName();
        if (moveset.get(2) != null && moveset.get(2).baseAttack != null)
            moveName3 = moveset.get(2).baseAttack.getLocalizedName();
        if (moveset.get(3) != null && moveset.get(3).baseAttack != null)
            moveName4 = moveset.get(3).baseAttack.getLocalizedName();

        String str = getConfig().getString("lang");
        if (str == null || str.isEmpty()) return;
        str = str.replace("<pokemon>", displayName);
        //String raw = getConfig().getString("showraw")
        str = str
                .replace("<lang>", str)
                .replace("[IVS.SPEED]", String.valueOf(iVs.speed))
                .replace("[IVS.ATTACK]", String.valueOf(iVs.attack))
                .replace("[IVS.DEFENCE]", String.valueOf(iVs.defence))
                .replace("[IVS.HP]", String.valueOf(iVs.hp))
                .replace("[IVS.SPECIALATTACK]", String.valueOf(iVs.specialAttack))
                .replace("[IVS.SPECIALDEFENCE]", String.valueOf(iVs.specialDefence))
                .replace("[EVS.SPEED]", String.valueOf(eVs.speed))
                .replace("[EVS.ATTACK]", String.valueOf(eVs.attack))
                .replace("[EVS.DEFENCE]", String.valueOf(eVs.defence))
                .replace("[EVS.HP]", String.valueOf(eVs.hp))
                .replace("[EVS.SPECIALATTACK]", String.valueOf(eVs.specialAttack))
                .replace("[EVS.SPECIALDEFENCE]", String.valueOf(eVs.specialDefence))
                .replace("[LEVEL]", String.valueOf(pokemon.getLevel()))
                .replace("[NATURE]", nature)
                .replace("[GROWTH]", growth)
                .replace("[MOVESET1]", moveName1)
                .replace("[MOVESET2]", moveName2)
                .replace("[MOVESET3]", moveName3)
                .replace("[MOVESET4]", moveName4)
                .replace("<pokemon>", displayName)
                .replace("[loc.x]", String.valueOf(x))
                .replace("[loc.y]", String.valueOf(y))
                .replace("[loc.z]", String.valueOf(z))
                .replace("[loc.world]", worldName)
                .replace("[nplayer]", player == null ? "无" : player.getName());
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a " + raw.replace("[ABLITY]", pokemon.getAbility().getLocalizedName()));
        Bukkit.broadcastMessage(str);
    }

    @EventHandler
    public void onSuccess(ForgeEvent tmpe) {
        if (!(tmpe.getForgeEvent() instanceof SpawnEvent)) return;
        SpawnEvent event = (SpawnEvent) tmpe.getForgeEvent();
        SpawnAction<? extends Entity> action = event.action;
        String worldName = action.spawnLocation.location.world.getWorld().getName();
        int x = action.spawnLocation.location.pos.func_177958_n();
        int y = action.spawnLocation.location.pos.func_177956_o();
        int z = action.spawnLocation.location.pos.func_177952_p();
        if (!(action.getOrCreateEntity() instanceof EntityPixelmon)) return;
        EntityPixelmon pixelmon = (EntityPixelmon) action.getOrCreateEntity();
        //NBTTagCompound nbt = pixelmon.serializeNBT();
        if (!getConfig().getStringList("allow").contains(pixelmon.getLocalizedName())) return;
        if (getConfig().getStringList("denynormal").contains(pixelmon.getLocalizedName())) return;
        Location pixelmonLocation = new Location(Bukkit.getWorld(worldName), x, y, z);

        double distance = Double.MAX_VALUE;
        Player player = null;
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player p : onlinePlayers) {
            Location loc = p.getLocation();
            if (!pixelmonLocation.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) continue;
            if (pixelmonLocation.distance(loc) < distance) {
                distance = pixelmonLocation.distance(loc);
                player = p;
            }
        }

        Pokemon pokemon = pixelmon.getStoragePokemonData();
        IVStore iVs = pokemon.getIVs();
        EVStore eVs = pokemon.getEVs();
        String displayName = pixelmon.getLocalizedName();
        String nature = pokemon.getNature().getLocalizedName();
        String growth = pokemon.getGrowth().getLocalizedName();
        Moveset moveset = pokemon.getMoveset();
        String moveName1 = "无", moveName2 = "无", moveName3 = "无", moveName4 = "无";
        if (moveset.get(0) != null && moveset.get(0).baseAttack != null)
            moveName1 = moveset.get(0).baseAttack.getLocalizedName();
        if (moveset.get(1) != null && moveset.get(1).baseAttack != null)
            moveName2 = moveset.get(1).baseAttack.getLocalizedName();
        if (moveset.get(2) != null && moveset.get(2).baseAttack != null)
            moveName3 = moveset.get(2).baseAttack.getLocalizedName();
        if (moveset.get(3) != null && moveset.get(3).baseAttack != null)
            moveName4 = moveset.get(3).baseAttack.getLocalizedName();

        String str = getConfig().getString("lang");
        if (str == null || str.isEmpty()) return;
        str = str.replace("<pokemon>", displayName);
        //String raw = getConfig().getString("showraw")
        str = str
                .replace("<lang>", str)
                .replace("[IVS.SPEED]", String.valueOf(iVs.speed))
                .replace("[IVS.ATTACK]", String.valueOf(iVs.attack))
                .replace("[IVS.DEFENCE]", String.valueOf(iVs.defence))
                .replace("[IVS.HP]", String.valueOf(iVs.hp))
                .replace("[IVS.SPECIALATTACK]", String.valueOf(iVs.specialAttack))
                .replace("[IVS.SPECIALDEFENCE]", String.valueOf(iVs.specialDefence))
                .replace("[EVS.SPEED]", String.valueOf(eVs.speed))
                .replace("[EVS.ATTACK]", String.valueOf(eVs.attack))
                .replace("[EVS.DEFENCE]", String.valueOf(eVs.defence))
                .replace("[EVS.HP]", String.valueOf(eVs.hp))
                .replace("[EVS.SPECIALATTACK]", String.valueOf(eVs.specialAttack))
                .replace("[EVS.SPECIALDEFENCE]", String.valueOf(eVs.specialDefence))
                .replace("[LEVEL]", String.valueOf(pokemon.getLevel()))
                .replace("[NATURE]", nature)
                .replace("[GROWTH]", growth)
                .replace("[MOVESET1]", moveName1)
                .replace("[MOVESET2]", moveName2)
                .replace("[MOVESET3]", moveName3)
                .replace("[MOVESET4]", moveName4)
                .replace("<pokemon>", displayName)
                .replace("[loc.x]", String.valueOf(x))
                .replace("[loc.y]", String.valueOf(y))
                .replace("[loc.z]", String.valueOf(z))
                .replace("[loc.world]", worldName)
                .replace("[nplayer]", player == null ? "无" : player.getName());
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a " + raw.replace("[ABLITY]", pokemon.getAbility().getLocalizedName()));
        Bukkit.broadcastMessage(str);
    }

}
