package com._0myun.minecraft.pokemoneditprotect;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.util.PixelmonPlayerUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.reloadConfig();
            sender.sendMessage("reload ok");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage("参数缺少");
            return true;
        }

        if (commandInterceptor((Player) sender
                , Integer.parseInt(args[1].replaceAll("[^0-9]", ""))
                , sender.getName())) {
            return true;
        }
        String commandStr = "";
        commandStr += label + " ";
        for (int i = 0; i < args.length; i++) {
            commandStr += args[i];
            if (i != args.length - 1) {
                commandStr += " ";
            }
        }
        commandStr = commandStr.replace(label, "PokemonIvExtention");
        ((Player) sender).performCommand(commandStr);
        return true;
    }


    private boolean commandInterceptor(Player p, int slot, String senderName) {
        EntityPlayerMP entityPlayer = PixelmonPlayerUtils.getUniquePlayerStartingWith(senderName);
        if (entityPlayer == null) {
            String message = "&7[&6错误&7]&r玩家名无效".replace("&", "§");
            p.sendMessage(message);
            return true;
        }
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(entityPlayer);
        //Optional playerStorage = PixelmonStorage.pokeBallManager.getPlayerStorage(entityPlayer);
        if (party == null) {
            String message = "&7[&6错误&7]&r加载背包数据失败".replace("&", "§");
            p.sendMessage(message);
            return true;
        }
        if (slot < 1 || slot > 6) {
            String message = "&7[&6错误&7]&r背包序号输入错误".replace("&", "§");
            p.sendMessage(message);
            return true;
        }
        //PlayerStorage storage = (PlayerStorage) playerStorage.get();
        Pokemon pokemon = party.get(slot - 1);
        //NBTTagCompound nbt = storage.getList()[slot - 1];
        NBTTagCompound nbt = new NBTTagCompound();
        pokemon.writeToNBT(nbt);
        String displayName = nbt.func_74779_i("Name");
        if (pokemon == null) {
            String message = "&7[&6错误&7]&r那个序号的背包是空的".replace("&", "§");
            p.sendMessage(message);
            return true;
        }
/*        try {
            Class<?> clazz = Class.forName("net.minecraft.nbt.NBTTagCompound");
            Method method = clazz.getMethod("func_74779_i", String.class);
            name = (String) method.invoke((Object) nbt, "Name");
        } catch (Exception exc) {
            exc.printStackTrace();
            String message = "&7[&6错误&7]&r插件内部错误".replace("&", "§");
            p.sendMessage(message);
            return true;
        }*/
        List<String> list = getConfig().getStringList("pixelmon");
        if (list != null && !list.isEmpty()) {
            for (String item : list) {
                if (!item.equals(displayName)) continue;
                String message = "&7[&6错误&7]&r这个精灵被禁止使用PokeEdit".replace("&", "§");
                p.sendMessage(message);
                return true;
            }
        }
        return false;
    }
}
