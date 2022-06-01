package noppes.npcs.roles;

import noppes.npcs.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.util.*;
import noppes.npcs.controllers.*;

public class RoleTransporter extends RoleInterface
{
    public int transportId;
    public String name;
    private int ticks;
    
    public RoleTransporter(final EntityNPCInterface npc) {
        super(npc);
        this.transportId = -1;
        this.ticks = 10;
    }
    
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("TransporterId", this.transportId);
        return nbttagcompound;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.transportId = nbttagcompound.getInteger("TransporterId");
        final TransportLocation loc = this.getLocation();
        if (loc != null) {
            this.name = loc.name;
        }
    }
    
    @Override
    public boolean aiShouldExecute() {
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.ticks = 10;
        if (!this.hasTransport()) {
            return false;
        }
        final TransportLocation loc = this.getLocation();
        if (loc.type != 0) {
            return false;
        }
        final List<EntityPlayer> inRange = (List<EntityPlayer>)this.npc.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, this.npc.boundingBox.expand(6.0, 6.0, 6.0));
        for (final EntityPlayer player : inRange) {
            if (!this.npc.canSee((Entity)player)) {
                continue;
            }
            this.unlock(player, loc);
        }
        return false;
    }
    
    @Override
    public void aiStartExecuting() {
    }
    
    @Override
    public void interact(final EntityPlayer player) {
        if (this.hasTransport()) {
            final TransportLocation loc = this.getLocation();
            if (loc.type == 2) {
                this.unlock(player, loc);
            }
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTransporter, this.npc);
        }
    }
    
    private void unlock(final EntityPlayer player, final TransportLocation loc) {
        final PlayerTransportData data = PlayerDataController.instance.getPlayerData(player).transportData;
        if (data.transports.contains(this.transportId)) {
            return;
        }
        data.transports.add(this.transportId);
        player.addChatMessage((IChatComponent)new ChatComponentTranslation("transporter.unlock", new Object[] { loc.name }));
    }
    
    public TransportLocation getLocation() {
        if (this.npc.isRemote()) {
            return null;
        }
        return TransportController.getInstance().getTransport(this.transportId);
    }
    
    public boolean hasTransport() {
        final TransportLocation loc = this.getLocation();
        return loc != null && loc.id == this.transportId;
    }
    
    public void setTransport(final TransportLocation location) {
        this.transportId = location.id;
        this.name = location.name;
    }
}
