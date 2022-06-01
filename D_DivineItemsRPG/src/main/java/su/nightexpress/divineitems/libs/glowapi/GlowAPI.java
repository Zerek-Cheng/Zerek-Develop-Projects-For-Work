/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package su.nightexpress.divineitems.libs.glowapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.libs.apihelper.API;
import su.nightexpress.divineitems.libs.apihelper.APIManager;
import su.nightexpress.divineitems.libs.glowapi.GlowData;
import su.nightexpress.divineitems.libs.packetlistener.PacketListenerAPI;
import su.nightexpress.divineitems.libs.packetlistener.handler.PacketHandler;
import su.nightexpress.divineitems.libs.packetlistener.handler.PacketOptions;
import su.nightexpress.divineitems.libs.packetlistener.handler.ReceivedPacket;
import su.nightexpress.divineitems.libs.packetlistener.handler.SentPacket;
import su.nightexpress.divineitems.libs.reflection.minecraft.DataWatcher;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.ConstructorResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.NMSClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.OBCClassResolver;
import su.nightexpress.divineitems.nms.VersionUtils;

public class GlowAPI
implements API,
Listener {
    private static Map<UUID, GlowData> dataMap = new HashMap<UUID, GlowData>();
    private static final NMSClassResolver NMS_CLASS_RESOLVER = new NMSClassResolver();
    private static Class<?> PacketPlayOutEntityMetadata;
    static Class<?> DataWatcher;
    static Class<?> DataWatcherItem;
    private static Class<?> Entity;
    private static FieldResolver PacketPlayOutMetadataFieldResolver;
    private static FieldResolver EntityFieldResolver;
    private static FieldResolver DataWatcherFieldResolver;
    static FieldResolver DataWatcherItemFieldResolver;
    private static ConstructorResolver DataWatcherItemConstructorResolver;
    private static MethodResolver DataWatcherMethodResolver;
    static MethodResolver DataWatcherItemMethodResolver;
    private static MethodResolver EntityMethodResolver;
    private static Class<?> PacketPlayOutScoreboardTeam;
    private static FieldResolver PacketScoreboardTeamFieldResolver;
    private static FieldResolver EntityPlayerFieldResolver;
    private static MethodResolver PlayerConnectionMethodResolver;
    public static String TEAM_TAG_VISIBILITY;
    public static String TEAM_PUSH;
    protected static NMSClassResolver nmsClassResolver;
    protected static OBCClassResolver obcClassResolver;
    private static FieldResolver CraftWorldFieldResolver;
    private static FieldResolver WorldFieldResolver;
    private static MethodResolver IntHashMapMethodResolver;

    static {
        TEAM_TAG_VISIBILITY = "always";
        TEAM_PUSH = "always";
        nmsClassResolver = new NMSClassResolver();
        obcClassResolver = new OBCClassResolver();
    }

    public static void setGlowing(Entity entity, Color color, String string, String string2, Player player) {
        Color color2;
        boolean bl;
        if (player == null) {
            return;
        }
        boolean bl2 = bl = color != null;
        if (entity == null) {
            bl = false;
        }
        if (entity instanceof OfflinePlayer && !((OfflinePlayer)entity).isOnline()) {
            bl = false;
        }
        boolean bl3 = dataMap.containsKey(entity != null ? entity.getUniqueId() : null);
        GlowData glowData = bl3 && entity != null ? dataMap.get(entity.getUniqueId()) : new GlowData();
        Color color3 = color2 = bl3 ? glowData.colorMap.get(player.getUniqueId()) : null;
        if (bl) {
            glowData.colorMap.put(player.getUniqueId(), color);
        } else {
            glowData.colorMap.remove(player.getUniqueId());
        }
        if (glowData.colorMap.isEmpty()) {
            dataMap.remove(entity != null ? entity.getUniqueId() : null);
        } else if (entity != null) {
            dataMap.put(entity.getUniqueId(), glowData);
        }
        if (color != null && color2 == color) {
            return;
        }
        if (entity == null) {
            return;
        }
        if (entity instanceof OfflinePlayer && !((OfflinePlayer)entity).isOnline()) {
            return;
        }
        if (!player.isOnline()) {
            return;
        }
        GlowAPI.sendGlowPacket(entity, bl3, bl, player);
        if (color2 != null && color2 != Color.NONE) {
            GlowAPI.sendTeamPacket(entity, color2, false, false, string, string2, player);
        }
        if (bl) {
            GlowAPI.sendTeamPacket(entity, color, false, color != Color.NONE, string, string2, player);
        }
    }

    public static void setGlowing(Entity entity, Color color, Player player) {
        GlowAPI.setGlowing(entity, color, "always", "always", player);
    }

    public static void setGlowing(Entity entity, boolean bl, Player player) {
        GlowAPI.setGlowing(entity, bl ? Color.NONE : null, player);
    }

    public static void setGlowing(Entity entity, boolean bl, Collection<? extends Player> collection) {
        for (Player player : collection) {
            GlowAPI.setGlowing(entity, bl, player);
        }
    }

    public static void setGlowing(Entity entity, Color color, Collection<? extends Player> collection) {
        for (Player player : collection) {
            GlowAPI.setGlowing(entity, color, player);
        }
    }

    public static void setGlowing(Collection<? extends Entity> collection, Color color, Player player) {
        for (Entity entity : collection) {
            GlowAPI.setGlowing(entity, color, player);
        }
    }

    public static void setGlowing(Collection<? extends Entity> collection, Color color, Collection<? extends Player> collection2) {
        for (Entity entity : collection) {
            GlowAPI.setGlowing(entity, color, collection2);
        }
    }

    public static boolean isGlowing(Entity entity, Player player) {
        if (GlowAPI.getGlowColor(entity, player) != null) {
            return true;
        }
        return false;
    }

    public static boolean isGlowing(Entity entity, Collection<? extends Player> collection, boolean bl) {
        if (bl) {
            boolean bl2 = true;
            for (Player player : collection) {
                if (GlowAPI.isGlowing(entity, player)) continue;
                bl2 = false;
            }
            return bl2;
        }
        for (Player player : collection) {
            if (!GlowAPI.isGlowing(entity, player)) continue;
            return true;
        }
        return false;
    }

    public static Color getGlowColor(Entity entity, Player player) {
        if (!dataMap.containsKey(entity.getUniqueId())) {
            return null;
        }
        GlowData glowData = dataMap.get(entity.getUniqueId());
        return glowData.colorMap.get(player.getUniqueId());
    }

    protected static void sendGlowPacket(Entity entity, boolean bl, boolean bl2, Player player) {
        try {
            if (PacketPlayOutEntityMetadata == null) {
                PacketPlayOutEntityMetadata = NMS_CLASS_RESOLVER.resolve("PacketPlayOutEntityMetadata");
            }
            if (DataWatcher == null) {
                DataWatcher = NMS_CLASS_RESOLVER.resolve("DataWatcher");
            }
            if (DataWatcherItem == null) {
                DataWatcherItem = NMS_CLASS_RESOLVER.resolve("DataWatcher$Item");
            }
            if (Entity == null) {
                Entity = NMS_CLASS_RESOLVER.resolve("Entity");
            }
            if (PacketPlayOutMetadataFieldResolver == null) {
                PacketPlayOutMetadataFieldResolver = new FieldResolver(PacketPlayOutEntityMetadata);
            }
            if (DataWatcherItemConstructorResolver == null) {
                DataWatcherItemConstructorResolver = new ConstructorResolver(DataWatcherItem);
            }
            if (EntityFieldResolver == null) {
                EntityFieldResolver = new FieldResolver(Entity);
            }
            if (DataWatcherMethodResolver == null) {
                DataWatcherMethodResolver = new MethodResolver(DataWatcher);
            }
            if (DataWatcherItemMethodResolver == null) {
                DataWatcherItemMethodResolver = new MethodResolver(DataWatcherItem);
            }
            if (EntityMethodResolver == null) {
                EntityMethodResolver = new MethodResolver(Entity);
            }
            if (DataWatcherFieldResolver == null) {
                DataWatcherFieldResolver = new FieldResolver(DataWatcher);
            }
            ArrayList arrayList = new ArrayList();
            Object object = EntityMethodResolver.resolve("getDataWatcher").invoke(Minecraft.getHandle((Object)entity), new Object[0]);
            Map map = (Map)DataWatcherFieldResolver.resolveByLastType(Map.class).get(object);
            Object object2 = DataWatcher.V1_9.ValueType.ENTITY_FLAG.getType();
            byte by = (Byte)(map.isEmpty() ? Integer.valueOf(0) : DataWatcherItemMethodResolver.resolve("b").invoke(map.get(0), new Object[0]));
            byte by2 = (byte)(bl2 ? by | 64 : by & -65);
            Object t = DataWatcherItemConstructorResolver.resolveFirstConstructor().newInstance(object2, by2);
            arrayList.add(t);
            Object obj = PacketPlayOutEntityMetadata.newInstance();
            PacketPlayOutMetadataFieldResolver.resolve("a").set(obj, - entity.getEntityId());
            PacketPlayOutMetadataFieldResolver.resolve("b").set(obj, arrayList);
            GlowAPI.sendPacket(obj, player);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static void initTeam(Player player, String string, String string2) {
        Color[] arrcolor = Color.values();
        int n = arrcolor.length;
        int n2 = 0;
        while (n2 < n) {
            Color color = arrcolor[n2];
            GlowAPI.sendTeamPacket(null, color, true, false, string, string2, player);
            ++n2;
        }
    }

    public static void initTeam(Player player) {
        GlowAPI.initTeam(player, TEAM_TAG_VISIBILITY, TEAM_PUSH);
    }

    protected static void sendTeamPacket(Entity entity, Color color, boolean bl, boolean bl2, String string, String string2, Player player) {
        try {
            Object object;
            if (PacketPlayOutScoreboardTeam == null) {
                PacketPlayOutScoreboardTeam = NMS_CLASS_RESOLVER.resolve("PacketPlayOutScoreboardTeam");
            }
            if (PacketScoreboardTeamFieldResolver == null) {
                PacketScoreboardTeamFieldResolver = new FieldResolver(PacketPlayOutScoreboardTeam);
            }
            Object obj = PacketPlayOutScoreboardTeam.newInstance();
            PacketScoreboardTeamFieldResolver.resolve("i").set(obj, bl ? 0 : (bl2 ? 3 : 4));
            PacketScoreboardTeamFieldResolver.resolve("a").set(obj, color.getTeamName());
            PacketScoreboardTeamFieldResolver.resolve("e").set(obj, string);
            PacketScoreboardTeamFieldResolver.resolve("f").set(obj, string2);
            if (bl) {
                if (VersionUtils.Version.getCurrent().isHigher(VersionUtils.Version.v1_12_R1)) {
                    Enum enum_;
                    if (color == Color.NONE) {
                        return;
                    }
                    object = color.name();
                    if (object.equalsIgnoreCase("PURPLE")) {
                        object = "LIGHT_PURPLE";
                    }
                    Class<?> class_ = DivineItems.getInstance().getVU().getNmsClass("EnumChatFormat");
                    Enum[] arrenum = (Enum[])class_.getEnumConstants();
                    Enum enum_2 = null;
                    Enum[] arrenum2 = arrenum;
                    int n = arrenum2.length;
                    int n2 = 0;
                    while (n2 < n) {
                        enum_ = arrenum2[n2];
                        if (enum_.name().equalsIgnoreCase((String)object)) {
                            enum_2 = enum_;
                        }
                        ++n2;
                    }
                    enum_ = DivineItems.getInstance().getVU().getNmsClass("ChatComponentText").getConstructor(String.class).newInstance("\u00a7" + color.colorCode);
                    Object obj2 = DivineItems.getInstance().getVU().getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(color.getTeamName());
                    Object obj3 = DivineItems.getInstance().getVU().getNmsClass("ChatComponentText").getConstructor(String.class).newInstance("");
                    PacketScoreboardTeamFieldResolver.resolve("g").set(obj, enum_2);
                    PacketScoreboardTeamFieldResolver.resolve("c").set(obj, enum_);
                    PacketScoreboardTeamFieldResolver.resolve("b").set(obj, obj2);
                    PacketScoreboardTeamFieldResolver.resolve("d").set(obj, obj3);
                } else {
                    PacketScoreboardTeamFieldResolver.resolve("g").set(obj, color.packetValue);
                    PacketScoreboardTeamFieldResolver.resolve("c").set(obj, "\u00a7" + color.colorCode);
                    PacketScoreboardTeamFieldResolver.resolve("b").set(obj, color.getTeamName());
                    PacketScoreboardTeamFieldResolver.resolve("d").set(obj, "");
                }
                PacketScoreboardTeamFieldResolver.resolve("j").set(obj, 0);
            }
            if (!bl) {
                object = (Collection)PacketScoreboardTeamFieldResolver.resolve("h").get(obj);
                if (entity instanceof OfflinePlayer) {
                    object.add(entity.getName());
                } else {
                    object.add(entity.getUniqueId().toString());
                }
            }
            GlowAPI.sendPacket(obj, player);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    protected static void sendPacket(Object object, Player player) {
        if (EntityPlayerFieldResolver == null) {
            EntityPlayerFieldResolver = new FieldResolver(NMS_CLASS_RESOLVER.resolve("EntityPlayer"));
        }
        if (PlayerConnectionMethodResolver == null) {
            PlayerConnectionMethodResolver = new MethodResolver(NMS_CLASS_RESOLVER.resolve("PlayerConnection"));
        }
        try {
            Object object2 = Minecraft.getHandle((Object)player);
            Object object3 = EntityPlayerFieldResolver.resolve("playerConnection").get(object2);
            PlayerConnectionMethodResolver.resolve("sendPacket").invoke(object3, object);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    @Override
    public void load() {
        APIManager.require(PacketListenerAPI.class, (Plugin)DivineItems.instance);
    }

    @Override
    public void init(Plugin plugin) {
        APIManager.initAPI(PacketListenerAPI.class);
        APIManager.registerEvents(this, this);
        PacketHandler.addHandler(new PacketHandler((Plugin)(DivineItems.instance != null ? DivineItems.instance : plugin)){

            @PacketOptions(forcePlayer=true)
            @Override
            public void onSend(SentPacket sentPacket) {
                if ("PacketPlayOutEntityMetadata".equals(sentPacket.getPacketName())) {
                    int n = (Integer)sentPacket.getPacketValue("a");
                    if (n < 0) {
                        sentPacket.setPacketValue("a", (Object)(- n));
                        return;
                    }
                    List list = (List)sentPacket.getPacketValue("b");
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    Entity entity = GlowAPI.getEntityById(sentPacket.getPlayer().getWorld(), n);
                    if (entity != null && GlowAPI.isGlowing(entity, sentPacket.getPlayer())) {
                        if (GlowAPI.DataWatcherItemMethodResolver == null) {
                            GlowAPI.DataWatcherItemMethodResolver = new MethodResolver(GlowAPI.DataWatcherItem);
                        }
                        if (GlowAPI.DataWatcherItemFieldResolver == null) {
                            GlowAPI.DataWatcherItemFieldResolver = new FieldResolver(GlowAPI.DataWatcherItem);
                        }
                        try {
                            for (Object e : list) {
                                Object object = GlowAPI.DataWatcherItemMethodResolver.resolve("b").invoke(e, new Object[0]);
                                if (!(object instanceof Byte)) continue;
                                byte by = (Byte)object;
                                byte by2 = (byte)(by | 64);
                                GlowAPI.DataWatcherItemFieldResolver.resolve("b").set(e, by2);
                            }
                        }
                        catch (Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    }
                }
            }

            @Override
            public void onReceive(ReceivedPacket receivedPacket) {
            }
        });
    }

    @Override
    public void disable(Plugin plugin) {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        GlowAPI.initTeam(playerJoinEvent.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!GlowAPI.isGlowing((Entity)playerQuitEvent.getPlayer(), player)) continue;
            GlowAPI.setGlowing((Entity)playerQuitEvent.getPlayer(), null, player);
        }
    }

    public static Entity getEntityById(World world, int n) {
        Object object;
        block7 : {
            try {
                if (CraftWorldFieldResolver == null) {
                    CraftWorldFieldResolver = new FieldResolver(obcClassResolver.resolve("CraftWorld"));
                }
                if (WorldFieldResolver == null) {
                    WorldFieldResolver = new FieldResolver(nmsClassResolver.resolve("World"));
                }
                if (IntHashMapMethodResolver == null) {
                    IntHashMapMethodResolver = new MethodResolver(nmsClassResolver.resolve("IntHashMap"));
                }
                if (EntityMethodResolver == null) {
                    EntityMethodResolver = new MethodResolver(nmsClassResolver.resolve("Entity"));
                }
                Object object2 = WorldFieldResolver.resolve("entitiesById").get(CraftWorldFieldResolver.resolve("world").get((Object)world));
                object = IntHashMapMethodResolver.resolve(new ResolverQuery("get", Integer.TYPE)).invoke(object2, n);
                if (object != null) break block7;
                return null;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        return (Entity)EntityMethodResolver.resolve("getBukkitEntity").invoke(object, new Object[0]);
    }

    public static enum Color {
        BLACK(0, "0"),
        DARK_BLUE(1, "1"),
        DARK_GREEN(2, "2"),
        DARK_AQUA(3, "3"),
        DARK_RED(4, "4"),
        DARK_PURPLE(5, "5"),
        GOLD(6, "6"),
        GRAY(7, "7"),
        DARK_GRAY(8, "8"),
        BLUE(9, "9"),
        GREEN(10, "a"),
        AQUA(11, "b"),
        RED(12, "c"),
        PURPLE(13, "d"),
        YELLOW(14, "e"),
        WHITE(15, "f"),
        NONE(-1, "");
        
        int packetValue;
        String colorCode;

        private Color(String string2, int n2, int n3, String string3) {
            this.packetValue = (int)string2;
            this.colorCode = (String)n2;
        }

        String getTeamName() {
            String string = String.format("GAPI#%s", this.name());
            if (string.length() > 16) {
                string = string.substring(0, 16);
            }
            return string;
        }
    }

}

