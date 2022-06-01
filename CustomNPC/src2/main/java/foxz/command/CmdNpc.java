package foxz.command;

import foxz.commandhelper.*;
import net.minecraft.util.*;
import foxz.commandhelper.annotations.*;
import noppes.npcs.roles.*;
import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.world.*;
import foxz.commandhelper.permissions.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.server.*;

@Command(name = "npc", desc = "NPC manipulation", usage = "<name> <command>")
public class CmdNpc extends ChMcLogger
{
    public EntityNPCInterface selectedNpc;
    
    CmdNpc(final Object ctorParm) {
        super(ctorParm);
    }
    
    @SubCommand(desc = "Set Home (respawn place)", usage = "")
    public void home(final String[] args) {
        double posX = this.pcParam.getCommandSenderPosition().posX;
        double posY = this.pcParam.getCommandSenderPosition().posY;
        double posZ = this.pcParam.getCommandSenderPosition().posZ;
        if (args.length == 3) {
            posX = CommandBase.clamp_coord(this.pcParam, this.selectedNpc.posX, args[0]);
            posY = CommandBase.clamp_double(this.pcParam, this.selectedNpc.posY, args[1].trim(), 0, 0);
            posZ = CommandBase.clamp_coord(this.pcParam, this.selectedNpc.posZ, args[2]);
        }
        this.selectedNpc.ai.startPos = new int[] { MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ) };
    }
    
    @SubCommand(desc = "Set npc visibility", usage = "")
    public void visible(final String[] args) {
        if (args.length < 1) {
            return;
        }
        final boolean bo = args[0].equalsIgnoreCase("true");
        final boolean semi = args[0].equalsIgnoreCase("semi");
        final int current = this.selectedNpc.display.visible;
        if (semi) {
            this.selectedNpc.display.visible = 2;
        }
        else if (bo) {
            this.selectedNpc.display.visible = 0;
        }
        else {
            this.selectedNpc.display.visible = 1;
        }
        if (current != this.selectedNpc.display.visible) {
            this.selectedNpc.updateClient = true;
        }
    }
    
    @SubCommand(desc = "Delete an NPC", usage = "")
    public void delete(final String[] args) {
        this.selectedNpc.delete();
    }
    
    @SubCommand(desc = "Owner", usage = "")
    public void owner(final String[] args) {
        if (args.length < 1) {
            EntityPlayer player = null;
            if (this.selectedNpc.roleInterface instanceof RoleFollower) {
                player = ((RoleFollower)this.selectedNpc.roleInterface).owner;
            }
            if (this.selectedNpc.roleInterface instanceof RoleCompanion) {
                player = ((RoleCompanion)this.selectedNpc.roleInterface).owner;
            }
            if (player == null) {
                this.sendmessage("No owner");
            }
            else {
                this.sendmessage("Owner is: " + player.getCommandSenderName());
            }
        }
        else {
            final EntityPlayerMP player2 = CommandBase.getPlayer(this.pcParam, args[0]);
            if (this.selectedNpc.roleInterface instanceof RoleFollower) {
                ((RoleFollower)this.selectedNpc.roleInterface).setOwner((EntityPlayer)player2);
            }
            if (this.selectedNpc.roleInterface instanceof RoleCompanion) {
                ((RoleCompanion)this.selectedNpc.roleInterface).setOwner((EntityPlayer)player2);
            }
        }
    }
    
    @SubCommand(desc = "Set npc name", usage = "")
    public void name(final String[] args) {
        if (args.length < 1) {
            return;
        }
        String name = args[0];
        for (int i = 1; i < args.length; ++i) {
            name = name + " " + args[i];
        }
        if (!this.selectedNpc.display.name.equals(name)) {
            this.selectedNpc.display.name = name;
            this.selectedNpc.updateClient = true;
        }
    }
    
    @SubCommand(desc = "Creates an NPC", usage = "[name]", permissions = { PlayerOnly.class, OpOnly.class })
    public void create(final String[] args) {
        final EntityPlayerMP player = (EntityPlayerMP)this.pcParam;
        final World pw = player.getEntityWorld();
        final EntityCustomNpc npc = new EntityCustomNpc(pw);
        if (args.length > 0) {
            npc.display.name = args[0];
        }
        npc.setPositionAndRotation(player.posX, player.posY, player.posZ, player.cameraYaw, player.cameraPitch);
        npc.ai.startPos = new int[] { MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ) };
        pw.spawnEntityInWorld((Entity)npc);
        npc.setHealth(npc.getMaxHealth());
        NoppesUtilServer.sendOpenGui((EntityPlayer)player, EnumGuiType.MainMenuDisplay, npc);
    }
    
    @Override
    public List addTabCompletion(final ICommandSender par1, final String[] args) {
        if (args.length == 2) {
            return CommandBase.getListOfStringsMatchingLastWord(args, new String[] { "create", "home", "visible", "delete", "owner", "name" });
        }
        if (args.length == 3 && args[1].equalsIgnoreCase("owner")) {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return super.addTabCompletion(par1, args);
    }
}
