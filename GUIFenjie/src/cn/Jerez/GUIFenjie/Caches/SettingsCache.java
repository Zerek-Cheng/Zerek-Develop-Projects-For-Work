// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie.Caches;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import cn.Jerez.Library.Caches.IEntityCacheHandler;
import java.io.File;
import cn.Jerez.GUIFenjie.Beans.Settings;
import cn.Jerez.Library.Caches.Disk.AbstractDiskOperation;

public class SettingsCache extends AbstractDiskOperation<Settings>
{
    private static File folder;
    
    public SettingsCache(final IEntityCacheHandler<Settings> handler) {
        super((IEntityCacheHandler)handler);
        SettingsCache.folder = handler.getEntityFolder();
    }
    
    public void save(final Map<String, Settings> caches) {
        if (caches != null) {
            final Set<Map.Entry<String, Settings>> entrySet = caches.entrySet();
            if (entrySet != null && entrySet.size() > 0) {
                this.clearFile(SettingsCache.folder, true);
                for (final Map.Entry<String, Settings> entry : entrySet) {
                    final String key = entry.getKey();
                    final Settings value = entry.getValue();
                    final File settingsData = new File(SettingsCache.folder, key);
                    settingsData.mkdir();
                    final File zbData = new File(settingsData, "zb.it");
                    this.writeItemStack(zbData, value.getFenjieItem());
                    final File materialFolder = new File(settingsData, "Material");
                    materialFolder.mkdir();
                    final List<ItemStack> materialItems = value.getMaterialItems();
                    if (materialItems != null && materialItems.size() > 0) {
                        for (int i = 0; i < materialItems.size(); ++i) {
                            final ItemStack materialItem = materialItems.get(i);
                            final File materialData = new File(materialFolder, i + ".it");
                            this.writeItemStack(materialData, materialItem);
                        }
                    }
                }
            }
        }
    }
    
    private void clearFile(final File file, final boolean deep) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        }
        else {
            final File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (final File childFile : files) {
                    if (!deep) {
                        if (childFile.isFile()) {
                            childFile.delete();
                        }
                    }
                    else {
                        this.clearFile(childFile, deep);
                    }
                    childFile.delete();
                }
            }
        }
    }
    
    private void writeItemStack(final File file, final ItemStack item) {
        if (item == null) {
            return;
        }
        final FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        config.set("SLOT", (Object)item);
        try {
            config.save(file);
        }
        catch (Exception ex) {
            System.out.println("\u4fdd\u5b58\u88c5\u5907\u5931\u8d25!");
        }
    }
    
    private ItemStack readItemStack(final File file) {
        final FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        return config.getItemStack("SLOT");
    }
    
    public Map<String, Settings> reload() {
        final Map<String, Settings> caches = new HashMap<String, Settings>();
        final File[] fjFolders = SettingsCache.folder.listFiles();
        if (fjFolders != null) {
            for (final File fjFolder : fjFolders) {
                final Settings settings = new Settings();
                final String id = fjFolder.getName();
                settings.setId(id);
                final File[] listFiles;
                final File[] metas = listFiles = fjFolder.listFiles();
                for (final File meta : listFiles) {
                    if (meta.isFile() && "zb.it".equalsIgnoreCase(meta.getName())) {
                        final ItemStack zbItem = this.readItemStack(meta);
                        if (zbItem != null) {}
                        settings.setFenjieItem(zbItem);
                    }
                    else {
                        final File[] meFiles = meta.listFiles();
                        if (meFiles != null && meFiles.length > 0) {
                            final List<ItemStack> meItems = new ArrayList<ItemStack>();
                            for (final File meFile : meFiles) {
                                meItems.add(this.readItemStack(meFile));
                            }
                            settings.setMaterialItems(meItems);
                        }
                    }
                }
                caches.put(id, settings);
            }
        }
        return caches;
    }
}
