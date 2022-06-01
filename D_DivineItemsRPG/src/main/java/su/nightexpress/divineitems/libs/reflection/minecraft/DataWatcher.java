/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.minecraft;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ConstructorResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.FieldResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.MethodResolver;
import su.nightexpress.divineitems.libs.reflection.resolver.ResolverQuery;
import su.nightexpress.divineitems.libs.reflection.resolver.minecraft.NMSClassResolver;

public class DataWatcher {
    static ClassResolver classResolver = new ClassResolver();
    static NMSClassResolver nmsClassResolver = new NMSClassResolver();
    static Class<?> ItemStack = nmsClassResolver.resolveSilent("ItemStack");
    static Class<?> ChunkCoordinates = nmsClassResolver.resolveSilent("ChunkCoordinates");
    static Class<?> BlockPosition = nmsClassResolver.resolveSilent("BlockPosition");
    static Class<?> Vector3f = nmsClassResolver.resolveSilent("Vector3f");
    static Class<?> DataWatcher = nmsClassResolver.resolveSilent("DataWatcher");
    static Class<?> Entity = nmsClassResolver.resolveSilent("Entity");
    static Class<?> TIntObjectMap = classResolver.resolveSilent("gnu.trove.map.TIntObjectMap", "net.minecraft.util.gnu.trove.map.TIntObjectMap");
    static ConstructorResolver DataWacherConstructorResolver = new ConstructorResolver(DataWatcher);
    static FieldResolver DataWatcherFieldResolver = new FieldResolver(DataWatcher);
    static MethodResolver TIntObjectMapMethodResolver = new MethodResolver(TIntObjectMap);
    static MethodResolver DataWatcherMethodResolver = new MethodResolver(DataWatcher);

    public static Object newDataWatcher(Object object) {
        return DataWacherConstructorResolver.resolve(new Class[][]{{Entity}}).newInstance(object);
    }

    public static Object setValue(Object object, int n, Object object2, Object object3) {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.setValue(object, n, object3);
        }
        return V1_9.setValue(object, object2, object3);
    }

    public static Object setValue(Object object, int n, V1_9.ValueType valueType, Object object2) {
        return DataWatcher.setValue(object, n, valueType.getType(), object2);
    }

    public static /* varargs */ Object setValue(Object object, int n, Object object2, FieldResolver fieldResolver, String ... arrstring) {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.setValue(object, n, object2);
        }
        Object object3 = fieldResolver.resolve(arrstring).get(null);
        return V1_9.setValue(object, object3, object2);
    }

    @Deprecated
    public static Object getValue(DataWatcher dataWatcher, int n) {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.getValue(dataWatcher, n);
        }
        return V1_9.getValue((Object)dataWatcher, n);
    }

    public static Object getValue(Object object, int n, V1_9.ValueType valueType) {
        return DataWatcher.getValue(object, n, valueType.getType());
    }

    public static Object getValue(Object object, int n, Object object2) {
        if (Minecraft.VERSION.olderThan(Minecraft.Version.v1_9_R1)) {
            return V1_8.getWatchableObjectValue(V1_8.getValue(object, n));
        }
        return V1_9.getValue(object, object2);
    }

    public static int getValueType(Object object) {
        int n = 0;
        if (object instanceof Number) {
            if (object instanceof Byte) {
                n = 0;
            } else if (object instanceof Short) {
                n = 1;
            } else if (object instanceof Integer) {
                n = 2;
            } else if (object instanceof Float) {
                n = 3;
            }
        } else if (object instanceof String) {
            n = 4;
        } else if (object != null && object.getClass().equals(ItemStack)) {
            n = 5;
        } else if (object != null && (object.getClass().equals(ChunkCoordinates) || object.getClass().equals(BlockPosition))) {
            n = 6;
        } else if (object != null && object.getClass().equals(Vector3f)) {
            n = 7;
        }
        return n;
    }

    private DataWatcher() {
    }

    public static class V1_8 {
        static Class<?> WatchableObject = DataWatcher.nmsClassResolver.resolveSilent("WatchableObject", "DataWatcher$WatchableObject");
        static ConstructorResolver WatchableObjectConstructorResolver;
        static FieldResolver WatchableObjectFieldResolver;

        public static Object newWatchableObject(int n, Object object) {
            return V1_8.newWatchableObject(DataWatcher.getValueType(object), n, object);
        }

        public static Object newWatchableObject(int n, int n2, Object object) {
            if (WatchableObjectConstructorResolver == null) {
                WatchableObjectConstructorResolver = new ConstructorResolver(WatchableObject);
            }
            return WatchableObjectConstructorResolver.resolve(new Class[][]{{Integer.TYPE, Integer.TYPE, Object.class}}).newInstance(n, n2, object);
        }

        public static Object setValue(Object object, int n, Object object2) {
            int n2 = DataWatcher.getValueType(object2);
            Map map = (Map)DataWatcher.DataWatcherFieldResolver.resolveByLastType(Map.class).get(object);
            map.put(n, V1_8.newWatchableObject(n2, n, object2));
            return object;
        }

        public static Object getValue(Object object, int n) {
            Map map = (Map)DataWatcher.DataWatcherFieldResolver.resolveByLastType(Map.class).get(object);
            return map.get(n);
        }

        public static int getWatchableObjectIndex(Object object) {
            if (WatchableObjectFieldResolver == null) {
                WatchableObjectFieldResolver = new FieldResolver(WatchableObject);
            }
            return WatchableObjectFieldResolver.resolve("b").getInt(object);
        }

        public static int getWatchableObjectType(Object object) {
            if (WatchableObjectFieldResolver == null) {
                WatchableObjectFieldResolver = new FieldResolver(WatchableObject);
            }
            return WatchableObjectFieldResolver.resolve("a").getInt(object);
        }

        public static Object getWatchableObjectValue(Object object) {
            if (WatchableObjectFieldResolver == null) {
                WatchableObjectFieldResolver = new FieldResolver(WatchableObject);
            }
            return WatchableObjectFieldResolver.resolve("c").get(object);
        }
    }

    public static class V1_9 {
        static Class<?> DataWatcherItem = DataWatcher.nmsClassResolver.resolveSilent("DataWatcher$Item");
        static Class<?> DataWatcherObject = DataWatcher.nmsClassResolver.resolveSilent("DataWatcherObject");
        static ConstructorResolver DataWatcherItemConstructorResolver;
        static FieldResolver DataWatcherItemFieldResolver;
        static FieldResolver DataWatcherObjectFieldResolver;

        public static Object newDataWatcherItem(Object object, Object object2) {
            if (DataWatcherItemConstructorResolver == null) {
                DataWatcherItemConstructorResolver = new ConstructorResolver(DataWatcherItem);
            }
            return DataWatcherItemConstructorResolver.resolveFirstConstructor().newInstance(object, object2);
        }

        public static Object setItem(Object object, int n, Object object2, Object object3) {
            return V1_9.setItem(object, n, V1_9.newDataWatcherItem(object2, object3));
        }

        public static Object setItem(Object object, int n, Object object2) {
            Map map = (Map)DataWatcher.DataWatcherFieldResolver.resolveByLastTypeSilent(Map.class).get(object);
            map.put(n, object2);
            return object;
        }

        public static Object setValue(Object object, Object object2, Object object3) {
            DataWatcher.DataWatcherMethodResolver.resolve("set").invoke(object, object2, object3);
            return object;
        }

        public static Object getItem(Object object, Object object2) {
            return DataWatcher.DataWatcherMethodResolver.resolve(new ResolverQuery("c", DataWatcherObject)).invoke(object, object2);
        }

        public static Object getValue(Object object, Object object2) {
            return DataWatcher.DataWatcherMethodResolver.resolve("get").invoke(object, object2);
        }

        public static Object getValue(Object object, ValueType valueType) {
            return V1_9.getValue(object, valueType.getType());
        }

        public static Object getItemObject(Object object) {
            if (DataWatcherItemFieldResolver == null) {
                DataWatcherItemFieldResolver = new FieldResolver(DataWatcherItem);
            }
            return DataWatcherItemFieldResolver.resolve("a").get(object);
        }

        public static int getItemIndex(Object object, Object object2) {
            int n = -1;
            Map map = (Map)DataWatcher.DataWatcherFieldResolver.resolveByLastTypeSilent(Map.class).get(object);
            for (Map.Entry entry : map.entrySet()) {
                if (!entry.getValue().equals(object2)) continue;
                n = (Integer)entry.getKey();
                break;
            }
            return n;
        }

        public static Type getItemType(Object object) {
            Type[] arrtype;
            Type type;
            if (DataWatcherObjectFieldResolver == null) {
                DataWatcherObjectFieldResolver = new FieldResolver(DataWatcherObject);
            }
            Object object2 = V1_9.getItemObject(object);
            Object object3 = DataWatcherObjectFieldResolver.resolve("b").get(object2);
            Type[] arrtype2 = object3.getClass().getGenericInterfaces();
            if (arrtype2.length > 0 && (type = arrtype2[0]) instanceof ParameterizedType && (arrtype = ((ParameterizedType)type).getActualTypeArguments()).length > 0) {
                return arrtype[0];
            }
            return null;
        }

        public static Object getItemValue(Object object) {
            if (DataWatcherItemFieldResolver == null) {
                DataWatcherItemFieldResolver = new FieldResolver(DataWatcherItem);
            }
            return DataWatcherItemFieldResolver.resolve("b").get(object);
        }

        public static void setItemValue(Object object, Object object2) {
            DataWatcherItemFieldResolver.resolve("b").set(object, object2);
        }

        public static enum ValueType {
            ENTITY_FLAG("Entity", 57, 0),
            ENTITY_AIR_TICKS("Entity", 58, 1),
            ENTITY_NAME("Entity", 59, 2),
            ENTITY_NAME_VISIBLE("Entity", 60, 3),
            ENTITY_SILENT("Entity", 61, 4),
            ENTITY_as("EntityLiving", 2, 0),
            ENTITY_LIVING_HEALTH("EntityLiving", new String[]{"HEALTH"}),
            ENTITY_LIVING_f("EntityLiving", 4, 2),
            ENTITY_LIVING_g("EntityLiving", 5, 3),
            ENTITY_LIVING_h("EntityLiving", 6, 4),
            ENTITY_INSENTIENT_FLAG("EntityInsentient", 0, 0),
            ENTITY_SLIME_SIZE("EntitySlime", 0, 0),
            ENTITY_WITHER_a("EntityWither", 0, 0),
            ENTITY_WIHER_b("EntityWither", 1, 1),
            ENTITY_WITHER_c("EntityWither", 2, 2),
            ENTITY_WITHER_bv("EntityWither", 3, 3),
            ENTITY_WITHER_bw("EntityWither", 4, 4),
            ENTITY_AGEABLE_CHILD("EntityAgeable", 0, 0),
            ENTITY_HORSE_STATUS("EntityHorse", 3, 0),
            ENTITY_HORSE_HORSE_TYPE("EntityHorse", 4, 1),
            ENTITY_HORSE_HORSE_VARIANT("EntityHorse", 5, 2),
            ENTITY_HORSE_OWNER_UUID("EntityHorse", 6, 3),
            ENTITY_HORSE_HORSE_ARMOR("EntityHorse", 7, 4),
            ENTITY_HUMAN_ABSORPTION_HEARTS("EntityHuman", 0, 0),
            ENTITY_HUMAN_SCORE("EntityHuman", 1, 1),
            ENTITY_HUMAN_SKIN_LAYERS("EntityHuman", 2, 2),
            ENTITY_HUMAN_MAIN_HAND("EntityHuman", 3, 3);
            
            private Object type;

            private /* varargs */ ValueType(String string2, int n2, String string3, String ... arrstring) {
                block2 : {
                    try {
                        this.type = new FieldResolver(DataWatcher.nmsClassResolver.resolve(string2)).resolve((String[])n2).get(null);
                    }
                    catch (Exception exception) {
                        if (!Minecraft.VERSION.newerThan(Minecraft.Version.v1_9_R1)) break block2;
                        System.err.println("[ReflectionHelper] Failed to find DataWatcherObject for " + string2 + " " + Arrays.toString((Object[])n2));
                    }
                }
            }

            private ValueType(String string2, int n2, String string3, int n3) {
                block2 : {
                    try {
                        this.type = new FieldResolver(DataWatcher.nmsClassResolver.resolve(string2)).resolveIndex(n2).get(null);
                    }
                    catch (Exception exception) {
                        if (!Minecraft.VERSION.newerThan(Minecraft.Version.v1_9_R1)) break block2;
                        System.err.println("[ReflectionHelper] Failed to find DataWatcherObject for " + string2 + " #" + n2);
                    }
                }
            }

            private ValueType(String string2, int n2, String string3, int n3, int n4) {
                block3 : {
                    n3 = 0;
                    try {
                        Class<?> class_ = DataWatcher.nmsClassResolver.resolve(string2);
                        Field[] arrfield = class_.getDeclaredFields();
                        int n5 = arrfield.length;
                        int n6 = 0;
                        while (n6 < n5) {
                            Field field = arrfield[n6];
                            if ("DataWatcherObject".equals(field.getType().getSimpleName())) break;
                            ++n3;
                            ++n6;
                        }
                        this.type = new FieldResolver(class_).resolveIndex(n3 + string3).get(null);
                    }
                    catch (Exception exception) {
                        if (!Minecraft.VERSION.newerThan(Minecraft.Version.v1_9_R1)) break block3;
                        System.err.println("[ReflectionHelper] Failed to find DataWatcherObject for " + string2 + " #" + n2 + " (" + n3 + "+" + (int)string3 + ")");
                    }
                }
            }

            public boolean hasType() {
                if (this.getType() != null) {
                    return true;
                }
                return false;
            }

            public Object getType() {
                return this.type;
            }
        }

    }

}

