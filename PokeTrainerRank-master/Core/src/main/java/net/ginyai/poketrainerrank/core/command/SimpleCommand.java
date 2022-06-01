package net.ginyai.poketrainerrank.core.command;

import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class SimpleCommand extends CommandBase {

    protected String permissionString;

    public String getPermissionString() {
        if(permissionString == null) {
            permissionString = "poketrainerrank.command."+getName().toLowerCase()+".base";
        }
        return permissionString;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return checkPermission(sender,getPermissionString());
    }

    public static boolean checkPermission(ICommandSender sender, String permission) {
        return PokeTrainerRankMod.getPlugin().checkPermission(
                PokeTrainerRankMod.trans(sender),
                permission
        );
    }
}
