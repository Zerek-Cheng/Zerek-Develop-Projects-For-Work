package noppes.npcs.controllers;

import net.minecraftforge.common.*;
import net.minecraft.entity.*;
import noppes.npcs.entity.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class ChunkController implements ForgeChunkManager.LoadingCallback
{
    public static ChunkController instance;
    private HashMap<Entity, ForgeChunkManager.Ticket> tickets;
    
    public ChunkController() {
        this.tickets = new HashMap<Entity, ForgeChunkManager.Ticket>();
        ChunkController.instance = this;
    }
    
    public void clear() {
        this.tickets = new HashMap<Entity, ForgeChunkManager.Ticket>();
    }
    
    public ForgeChunkManager.Ticket getTicket(final EntityNPCInterface npc) {
        ForgeChunkManager.Ticket ticket = this.tickets.get(npc);
        if (ticket != null) {
            return ticket;
        }
        if (this.size() >= CustomNpcs.ChuckLoaders) {
            return null;
        }
        ticket = ForgeChunkManager.requestTicket((Object)CustomNpcs.instance, npc.worldObj, ForgeChunkManager.Type.ENTITY);
        if (ticket == null) {
            return null;
        }
        ticket.bindEntity((Entity)npc);
        ticket.setChunkListDepth(6);
        this.tickets.put((Entity)npc, ticket);
        return null;
    }
    
    public void deleteNPC(final EntityNPCInterface npc) {
        final ForgeChunkManager.Ticket ticket = this.tickets.get(npc);
        if (ticket != null) {
            this.tickets.remove(npc);
            ForgeChunkManager.releaseTicket(ticket);
        }
    }
    
    public void ticketsLoaded(final List<ForgeChunkManager.Ticket> tickets, final World world) {
        for (final ForgeChunkManager.Ticket ticket : tickets) {
            if (!(ticket.getEntity() instanceof EntityNPCInterface)) {
                continue;
            }
            final EntityNPCInterface npc = (EntityNPCInterface)ticket.getEntity();
            if (npc.advanced.job != EnumJobType.ChunkLoader || tickets.contains(npc)) {
                continue;
            }
            this.tickets.put((Entity)npc, ticket);
            final double x = npc.posX / 16.0;
            final double z = npc.posZ / 16.0;
            ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(MathHelper.floor_double(x), MathHelper.floor_double(z)));
            ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(MathHelper.ceiling_double_int(x), MathHelper.ceiling_double_int(z)));
            ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(MathHelper.floor_double(x), MathHelper.ceiling_double_int(z)));
            ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(MathHelper.ceiling_double_int(x), MathHelper.floor_double(z)));
        }
    }
    
    public int size() {
        return this.tickets.size();
    }
    
    public void unload(final int toRemove) {
        final Iterator<Entity> ite = this.tickets.keySet().iterator();
        int i = 0;
        while (ite.hasNext()) {
            if (i >= toRemove) {
                return;
            }
            final Entity entity = ite.next();
            ForgeChunkManager.releaseTicket((ForgeChunkManager.Ticket)this.tickets.get(entity));
            ite.remove();
            ++i;
        }
    }
}
