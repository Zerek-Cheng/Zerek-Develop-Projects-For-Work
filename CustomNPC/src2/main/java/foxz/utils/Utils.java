package foxz.utils;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.world.*;

public class Utils
{
    public static <T> List<T> getNearbeEntityFromPlayer(final Class<? extends T> cls, final EntityPlayerMP player, final int dis) {
        final AxisAlignedBB range = player.boundingBox.expand((double)dis, (double)dis, (double)dis);
        final List<T> list = (List<T>)player.worldObj.getEntitiesWithinAABB((Class)cls, range);
        return list;
    }
    
    public static EntityPlayer getOnlinePlayer(final String playername) {
        return (EntityPlayer)MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(playername);
    }
    
    public static World getWorld(final String t) {
        final WorldServer[] worldServers;
        final WorldServer[] ws = worldServers = MinecraftServer.getServer().worldServers;
        for (final WorldServer w : worldServers) {
            if (w != null && (w.provider.dimensionId + "").equalsIgnoreCase(t)) {
                return (World)w;
            }
        }
        return null;
    }
}
