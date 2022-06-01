// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie.Caches;

import cn.Jerez.GUIFenjie.Main;
import java.io.File;
import cn.Jerez.GUIFenjie.Beans.Settings;
import cn.Jerez.Library.Caches.IEntityCacheHandler;

public class SettingsCacheHandler implements IEntityCacheHandler<Settings>
{
    public Class<Settings> getEntityClass() {
        return Settings.class;
    }
    
    public File getEntityFolder() {
        return new File(Main.getInstance().getDataFolder(), "Settings");
    }
    
    public String getEntityPutingKey() {
        return "id";
    }
}
