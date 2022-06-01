/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 */
package su.nightexpress.divineitems.modules.tiers.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.tiers.resources.Res;
import su.nightexpress.divineitems.modules.tiers.resources.ResourceSubType;
import su.nightexpress.divineitems.modules.tiers.resources.ResourceType;
import su.nightexpress.divineitems.utils.Files;

public class Resources {
    private static DivineItems plugin = DivineItems.instance;
    private static HashMap<String, Res> res = new HashMap();
    private static HashMap<String, Res> res_half = new HashMap();
    private static Set<Material> mats = new HashSet<Material>();

    public static void setup() {
        mats.addAll(plugin.getCM().getCFG().getWeapons());
        mats.addAll(plugin.getCM().getCFG().getArmors());
        mats.addAll(plugin.getCM().getCFG().getTools());
        Resources.setupMissingConfigs(ResourceType.PREFIX);
        Resources.setupMissingConfigs(ResourceType.SUFFIX);
        Resources.setupResourcesByTypes();
    }

    public static void setupMissingConfigs(ResourceType resourceType) {
        Object[] arrobject;
        File file;
        String string = String.valueOf(resourceType.name().toLowerCase()) + "es";
        for (String arrobject2 : Files.getFilesFolder("tiers")) {
            file = new File(plugin.getDataFolder() + "/modules/tiers/resources/names/" + string + "/tiers/", String.valueOf(arrobject2.toLowerCase().replace(".yml", "")) + ".txt");
            if (file.exists()) continue;
            if (plugin.getResource("modules/tiers/resources/names/" + string + "/tiers/" + arrobject2.toLowerCase().replace(".yml", "") + ".txt") != null) {
                plugin.saveResource("modules/tiers/resources/names/" + string + "/tiers/" + arrobject2.toLowerCase().replace(".yml", "") + ".txt", false);
                continue;
            }
            Files.create(file);
        }
        for (Material material : mats) {
            file = new File(plugin.getDataFolder() + "/modules/tiers/resources/names/" + string + "/materials/", String.valueOf(material.toString().toLowerCase()) + ".txt");
            if (file.exists()) continue;
            if (plugin.getResource("modules/tiers/resources/names/" + string + "/materials/" + material.toString().toLowerCase() + ".txt") != null) {
                plugin.saveResource("modules/tiers/resources/names/" + string + "/materials/" + material.toString().toLowerCase() + ".txt", false);
                continue;
            }
            Files.create(file);
        }
        Object[] arrobject2 = arrobject = Resources.plugin.getCM().configLang.getConfig().getConfigurationSection("ItemTypes").getKeys(false).toArray();
        int n = arrobject2.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrobject2[n2];
            String string2 = object.toString();
            File file2 = new File(plugin.getDataFolder() + "/modules/tiers/resources/names/" + string + "/types/", String.valueOf(string2.toLowerCase()) + ".txt");
            if (!file2.exists()) {
                Files.create(file2);
            }
            ++n2;
        }
    }

    public static List<String> getSource(ResourceType resourceType, ResourceSubType resourceSubType, String string) {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<String>();
        String string2 = String.valueOf(resourceType.name().toLowerCase()) + "es";
        String string3 = String.valueOf(resourceSubType.name().toLowerCase()) + "s";
        String string4 = plugin.getDataFolder() + "/modules/tiers/resources/names/" + string2 + "/" + string3 + "/" + string + ".txt";
        try {
            Throwable throwable = null;
            Object var8_10 = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(string4));
                try {
                    String string5;
                    while ((string5 = bufferedReader.readLine()) != null) {
                        arrayList.add(string5);
                    }
                }
                finally {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return arrayList;
    }

    public static void setupResourcesByTypes() {
        Object object;
        Object[] arrobject;
        Object object2;
        for (Material arrobject2 : mats) {
            String n2 = arrobject2.name().toLowerCase();
            List<String> n = Resources.getSource(ResourceType.PREFIX, ResourceSubType.MATERIAL, n2);
            object2 = Resources.getSource(ResourceType.SUFFIX, ResourceSubType.MATERIAL, n2);
            object = new Res(n2, n, (List<String>)object2);
            res.put(n2, (Res)object);
        }
        object2 = arrobject = Resources.plugin.getCM().configLang.getConfig().getConfigurationSection("ItemTypes").getKeys(false).toArray();
        int n = ((Object[])object2).length;
        int n2 = 0;
        while (n2 < n) {
            Object object3 = object2[n2];
            object = object3.toString().toLowerCase();
            List<String> list = Resources.getSource(ResourceType.PREFIX, ResourceSubType.TYPE, (String)object);
            List<String> list2 = Resources.getSource(ResourceType.SUFFIX, ResourceSubType.TYPE, (String)object);
            Res res = new Res((String)object, list, list2);
            res_half.put((String)object, res);
            ++n2;
        }
    }

    public static List<String> getSourceByFullType(ResourceType resourceType, String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (res.containsKey(string = string.toLowerCase())) {
            if (resourceType == ResourceType.PREFIX) {
                return res.get(string).getPrefixes();
            }
            return res.get(string).getSuffixes();
        }
        return arrayList;
    }

    public static List<String> getSourceByHalfType(ResourceType resourceType, String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if ((string = string.toLowerCase()).split("_").length > 1) {
            string = string.split("_")[1];
        }
        if (res_half.containsKey(string)) {
            if (resourceType == ResourceType.PREFIX) {
                return res_half.get(string).getPrefixes();
            }
            return res_half.get(string).getSuffixes();
        }
        return arrayList;
    }

    public static Set<Material> getAllMaterials() {
        return mats;
    }

    public static void clear() {
        res.clear();
        res_half.clear();
        mats.clear();
    }
}

