/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.Vector
 */
package org.bukkit.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.Configuration;
public interface ConfigurationSection {
    public Set<String> getKeys(boolean var1);

    public Map<String, Object> getValues(boolean var1);

    public boolean contains(String var1);

    public boolean isSet(String var1);

    public String getCurrentPath();

    public String getName();

    public Configuration getRoot();

    public ConfigurationSection getParent();

    public Object get(String var1);

    public Object get(String var1, Object var2);

    public void set(String var1, Object var2);

    public ConfigurationSection createSection(String var1);

    public ConfigurationSection createSection(String var1, Map<?, ?> var2);

    public String getString(String var1);

    public String getString(String var1, String var2);

    public boolean isString(String var1);

    public int getInt(String var1);

    public int getInt(String var1, int var2);

    public boolean isInt(String var1);

    public boolean getBoolean(String var1);

    public boolean getBoolean(String var1, boolean var2);

    public boolean isBoolean(String var1);

    public double getDouble(String var1);

    public double getDouble(String var1, double var2);

    public boolean isDouble(String var1);

    public long getLong(String var1);

    public long getLong(String var1, long var2);

    public boolean isLong(String var1);

    public List<?> getList(String var1);

    public List<?> getList(String var1, List<?> var2);

    public boolean isList(String var1);

    public List<String> getStringList(String var1);

    public List<Integer> getIntegerList(String var1);

    public List<Boolean> getBooleanList(String var1);

    public List<Double> getDoubleList(String var1);

    public List<Float> getFloatList(String var1);

    public List<Long> getLongList(String var1);

    public List<Byte> getByteList(String var1);

    public List<Character> getCharacterList(String var1);

    public List<Short> getShortList(String var1);

    public List<Map<?, ?>> getMapList(String var1);

    public ConfigurationSection getConfigurationSection(String var1);

    public boolean isConfigurationSection(String var1);

    public ConfigurationSection getDefaultSection();

    public void addDefault(String var1, Object var2);
}

