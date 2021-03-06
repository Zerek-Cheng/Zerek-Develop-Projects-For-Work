package foxz.command;

import foxz.commandhelper.*;
import net.minecraft.entity.player.*;
import noppes.npcs.entity.*;
import foxz.utils.*;
import net.minecraft.nbt.*;
import noppes.npcs.controllers.*;
import java.util.*;
import foxz.commandhelper.annotations.*;
import foxz.commandhelper.permissions.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

@Command(name = "clone", desc = "Clone operation (server side)")
public class CmdClone extends ChMcLogger
{
    public CmdClone(final Object sender) {
        super(sender);
    }
    
    @SubCommand(desc = "Add NPC(s) to clone storage", usage = "<npc> <tab> [clonedname]", permissions = { OpOnly.class, PlayerOnly.class, ParamCheck.class })
    public Boolean add(final String[] args) {
        final EntityPlayerMP player = (EntityPlayerMP)this.pcParam;
        int tab = 0;
        try {
            tab = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {}
        final List<EntityNPCInterface> list = Utils.getNearbeEntityFromPlayer((Class<? extends EntityNPCInterface>)EntityNPCInterface.class, player, 80);
        for (final EntityNPCInterface npc : list) {
            if (npc.display.name.equalsIgnoreCase(args[0])) {
                String name = npc.display.name;
                if (args.length > 2) {
                    name = args[2];
                }
                final NBTTagCompound compound = new NBTTagCompound();
                if (!npc.writeToNBTOptional(compound)) {
                    return false;
                }
                ServerCloneController.Instance.addClone(compound, name, tab);
                return true;
            }
        }
        return true;
    }
    
    @SubCommand(desc = "List NPC from clone storage", usage = "<tab>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean list(final String[] args) {
        this.sendmessage("--- Stored NPCs --- (server side)");
        int tab = 0;
        try {
            tab = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex) {}
        for (final String name : ServerCloneController.Instance.getClones(tab)) {
            this.sendmessage(name);
        }
        this.sendmessage("------------------------------------");
        return true;
    }
    
    @SubCommand(desc = "Remove NPC from clone storage", usage = "<name> <tab>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean del(final String[] args) {
        final String nametodel = args[0];
        int tab = 0;
        try {
            tab = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {}
        boolean deleted = false;
        for (final String name : ServerCloneController.Instance.getClones(tab)) {
            if (nametodel.equalsIgnoreCase(name)) {
                ServerCloneController.Instance.removeClone(name, tab);
                deleted = true;
                break;
            }
        }
        if (!ServerCloneController.Instance.removeClone(nametodel, tab)) {
            this.sendmessage(String.format("Npc '%s' wasn't found", nametodel));
            return false;
        }
        return true;
    }
    
    @SubCommand(desc = "Spawn cloned NPC", usage = "<name> <tab> [[world:]x,y,z]] [newname]", permissions = { OpOnly.class, ParamCheck.class })
    public boolean spawn(final String[] args) {
        final String name = args[0].replaceAll("%", " ");
        int tab = 0;
        try {
            tab = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex2) {}
        String newname = null;
        final NBTTagCompound compound = ServerCloneController.Instance.getCloneData(this.pcParam, name, tab);
        if (compound == null) {
            this.sendmessage("Unknown npc");
            return false;
        }
        World world = this.pcParam.getEntityWorld();
        double posX = this.pcParam.getCommandSenderPosition().posX;
        double posY = this.pcParam.getCommandSenderPosition().posY;
        double posZ = this.pcParam.getCommandSenderPosition().posZ;
        if (args.length > 2) {
            String location = args[2];
            if (location.contains(":")) {
                final String[] par = location.split(":");
                location = par[1];
                world = Utils.getWorld(par[0]);
                if (world == null) {
                    this.sendmessage(String.format("'%s' is an unknown world", par[0]));
                    return false;
                }
            }
            if (location.contains(",")) {
                final String[] par = location.split(",");
                if (par.length != 3) {
                    this.sendmessage("Location need be x,y,z");
                    return false;
                }
                try {
                    posX = CommandBase.clamp_coord(this.pcParam, posX, par[0]);
                    posY = CommandBase.clamp_double(this.pcParam, posY, par[1].trim(), 0, 0);
                    posZ = CommandBase.clamp_coord(this.pcParam, posZ, par[2]);
                }
                catch (NumberFormatException ex) {
                    this.sendmessage("Location should be in numbers");
                    return false;
                }
                if (args.length > 3) {
                    newname = args[3];
                }
            }
            else {
                newname = location;
            }
        }
        if (posX == 0.0 && posY == 0.0 && posZ == 0.0) {
            this.sendmessage("Location needed");
            return false;
        }
        final Entity entity = EntityList.createEntityFromNBT(compound, world);
        entity.setPosition(posX + 0.5, posY + 1.0, posZ + 0.5);
        if (entity instanceof EntityNPCInterface) {
            final EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ai.startPos = new int[] { MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ) };
            if (newname != null && !newname.isEmpty()) {
                npc.display.name = newname.replaceAll("%", " ");
            }
        }
        world.spawnEntityInWorld(entity);
        return true;
    }
}
