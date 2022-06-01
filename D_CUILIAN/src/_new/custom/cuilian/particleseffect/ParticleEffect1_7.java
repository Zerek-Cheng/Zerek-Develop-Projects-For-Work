//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package _new.custom.cuilian.particleseffect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public enum ParticleEffect1_7 {
    HUGEEXPLOSION,
    LARGEEXPLODE,
    FIREWORKSSPARK,
    BUBBLE,
    SUSPENDED,
    DEPTHSUSPEND,
    TOWNAURA,
    CRIT,
    MAGICCRIT,
    SMOKE,
    MOBSPELL,
    MOBSPELLAMBIENT,
    SPELL,
    INSTANTSPELL,
    WITCHMAGIC,
    NOTE,
    PORTAL,
    ENCHANTMENTTABLE,
    EXPLODE,
    FLAME,
    LAVA,
    FOOTSTEP,
    SPLASH,
    WAKE,
    LARGESMOKE,
    CLOUD,
    REDDUST,
    SNOWBALLPOOF,
    DRIPWATER,
    DRIPLAVA,
    SNOWSHOVEL,
    SLIME,
    HEART,
    ANGRYVILLAGER,
    HAPPYVILLAGER;

    private ParticleEffect1_7() {
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    private static boolean isLongDistance(Location location, List<Player> players) {
        String world = location.getWorld().getName();
        Iterator var3 = players.iterator();

        Location playerLocation;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            Player player = (Player) var3.next();
            playerLocation = player.getLocation();
        } while (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536.0D);

        return true;
    }

    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleEffect1_7.ParticleVersionException, ParticleEffect1_7.ParticleDataException, IllegalArgumentException {
        (new ParticleEffect1_7.ParticlePacket(this, direction, speed, isLongDistance(center, players), (ParticleEffect1_7.ParticleData) null)).sendTo(center, players);
    }

    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleEffect1_7.ParticleVersionException, ParticleEffect1_7.ParticleDataException, IllegalArgumentException {
        this.display(direction, speed, center, Arrays.asList(players));
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = ParticleEffect1_7.DataType.getPrimitive(parameterTypes);
        Method[] var4 = clazz.getMethods();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(methodName) && ParticleEffect1_7.DataType.compare(ParticleEffect1_7.DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
                return method;
            }
        }

        return null;
    }

    public static Method getMethod(String className, ParticleEffect1_7.PackageType packageType, String methodName, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(className), methodName, parameterTypes);
    }

    public static Object invokeMethod(Object instance, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(instance.getClass(), methodName, ParticleEffect1_7.DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, Class<?> clazz, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(clazz, methodName, ParticleEffect1_7.DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }

    public static Object invokeMethod(Object instance, String className, ParticleEffect1_7.PackageType packageType, String methodName, Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
    }

    public static Field getField(Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Field getField(String className, ParticleEffect1_7.PackageType packageType, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(clazz, declared, fieldName).get(instance);
    }

    public static Object getValue(Object instance, String className, ParticleEffect1_7.PackageType packageType, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }

    public static Object getValue(Object instance, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(instance, instance.getClass(), declared, fieldName);
    }

    public static void setValue(Object instance, Class<?> clazz, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(clazz, declared, fieldName).set(instance, value);
    }

    public static void setValue(Object instance, String className, ParticleEffect1_7.PackageType packageType, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }

    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(instance, instance.getClass(), declared, fieldName, value);
    }

    public static enum DataType {
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);

        private static final Map<Class<?>, ParticleEffect1_7.DataType> CLASS_MAP = new HashMap();
        private final Class<?> primitive;
        private final Class<?> reference;

        private DataType(Class<?> primitive, Class<?> reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public Class<?> getPrimitive() {
            return this.primitive;
        }

        public Class<?> getReference() {
            return this.reference;
        }

        public static ParticleEffect1_7.DataType fromClass(Class<?> clazz) {
            return (ParticleEffect1_7.DataType) CLASS_MAP.get(clazz);
        }

        public static Class<?> getPrimitive(Class<?> clazz) {
            ParticleEffect1_7.DataType type = fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        public static Class<?> getReference(Class<?> clazz) {
            ParticleEffect1_7.DataType type = fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        public static Class<?>[] getPrimitive(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(classes[index]);
            }

            return types;
        }

        public static Class<?>[] getReference(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class<?>[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getReference(classes[index]);
            }

            return types;
        }

        public static Class<?>[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(objects[index].getClass());
            }

            return types;
        }

        public static Class<?>[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class<?>[] types = new Class[length];

            for (int index = 0; index < length; ++index) {
                types[index] = getReference(objects[index].getClass());
            }

            return types;
        }

        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
            if (primary != null && secondary != null && primary.length == secondary.length) {
                for (int index = 0; index < primary.length; ++index) {
                    Class<?> primaryClass = primary[index];
                    Class<?> secondaryClass = secondary[index];
                    if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        }

        static {
            ParticleEffect1_7.DataType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                ParticleEffect1_7.DataType type = var0[var2];
                CLASS_MAP.put(type.primitive, type);
                CLASS_MAP.put(type.reference, type);
            }

        }
    }

    public static enum PackageType {
        MINECRAFT_SERVER("net.minecraft.server." + getServerVersion()),
        CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion()),
        CRAFTBUKKIT_ENTITY(CRAFTBUKKIT, "entity");

        private final String path;

        private PackageType(String path) {
            this.path = path;
        }

        private PackageType(ParticleEffect1_7.PackageType parent, String path) {
            this(parent + "." + path);
        }

        public String getPath() {
            return this.path;
        }

        public Class<?> getClass(String className) throws ClassNotFoundException {
            return Class.forName(this + "." + className);
        }

        public String toString() {
            return this.path;
        }

        public static String getServerVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
        }
    }

    public static final class ParticlePacket {
        private static int version;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect1_7 effect;
        private float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private Object packet;

        public ParticlePacket(ParticleEffect1_7 effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleEffect1_7.ParticleData data) throws IllegalArgumentException {
            initialize();
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
        }

        public ParticlePacket(ParticleEffect1_7 effect, Vector direction, float speed, boolean longDistance, ParticleEffect1_7.ParticleData data) throws IllegalArgumentException {
            this(effect, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, 0, data);
        }

        public static void initialize() {
            if (!initialized) {
                try {
                    Class<?> packetClass = ParticleEffect1_7.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutWorldParticles");
                    packetConstructor = getConstructor(packetClass);
                    getHandle = ParticleEffect1_7.getMethod("CraftPlayer", ParticleEffect1_7.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
                    playerConnection = ParticleEffect1_7.getField("EntityPlayer", ParticleEffect1_7.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                    sendPacket = ParticleEffect1_7.getMethod(playerConnection.getType(), "sendPacket", ParticleEffect1_7.PackageType.MINECRAFT_SERVER.getClass("Packet"));
                } catch (SecurityException | NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | NumberFormatException var1) {

                }
                initialized = true;
            }
        }

        public static Constructor<?> getConstructor(Class<?> clazz, Class... parameterTypes) throws NoSuchMethodException {
            Class<?>[] primitiveTypes = ParticleEffect1_7.DataType.getPrimitive(parameterTypes);
            Constructor[] var3 = clazz.getConstructors();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Constructor<?> constructor = var3[var5];
                if (ParticleEffect1_7.DataType.compare(ParticleEffect1_7.DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
                    return constructor;
                }
            }

            return null;
        }

        public static Constructor<?> getConstructor(String className, ParticleEffect1_7.PackageType packageType, Class... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
            return getConstructor(packageType.getClass(className), parameterTypes);
        }

        public static Object instantiateObject(Class<?> clazz, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
            return getConstructor(clazz, ParticleEffect1_7.DataType.getPrimitive(arguments)).newInstance(arguments);
        }

        public static Object instantiateObject(String className, ParticleEffect1_7.PackageType packageType, Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
            return instantiateObject(packageType.getClass(className), arguments);
        }

        public static int getVersion() {
            if (!initialized) {
                initialize();
            }

            return version;
        }

        public static boolean isInitialized() {
            return initialized;
        }

        private void initializePacket(Location center) {
            if (this.packet == null) {
                try {
                    this.packet = packetConstructor.newInstance();
                    ParticleEffect1_7.setValue(this.packet, true, "a", this.effect.getName());
                    ParticleEffect1_7.setValue(this.packet, true, "b", (float) center.getX());
                    ParticleEffect1_7.setValue(this.packet, true, "c", (float) center.getY());
                    ParticleEffect1_7.setValue(this.packet, true, "d", (float) center.getZ());
                    ParticleEffect1_7.setValue(this.packet, true, "e", this.offsetX);
                    ParticleEffect1_7.setValue(this.packet, true, "f", this.offsetY);
                    ParticleEffect1_7.setValue(this.packet, true, "g", this.offsetZ);
                    ParticleEffect1_7.setValue(this.packet, true, "h", this.speed);
                    ParticleEffect1_7.setValue(this.packet, true, "i", this.amount);
                } catch (Exception var3) {

                }

            }
        }

        public void sendTo(Location center, Player player) {
            this.initializePacket(center);

            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), this.packet);
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
            }

        }

        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (!players.isEmpty()) {
                Iterator var3 = players.iterator();

                while (var3.hasNext()) {
                    Player player = (Player) var3.next();
                    this.sendTo(center, player);
                }

            }
        }
    }

    private static final class ParticleVersionException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message) {
            super(message);
        }
    }

    private static final class ParticleDataException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message) {
            super(message);
        }
    }

    public static final class BlockData extends ParticleEffect1_7.ParticleData {
        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
        }
    }

    public static final class ItemData extends ParticleEffect1_7.ParticleData {
        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }

    public abstract static class ParticleData {
        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[]{material.getId(), data};
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }
}
