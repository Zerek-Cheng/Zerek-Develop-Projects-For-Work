/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.Validate
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.utils;

import org.apache.commons.lang3.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ResourceExtractor {
    protected final JavaPlugin plugin;
    protected final File extractfolder;
    protected final String folderpath;
    protected final String regex;

    public ResourceExtractor(JavaPlugin javaPlugin, File file, String string, String string2) {
        Validate.notNull((Object)javaPlugin, (String)"The plugin cannot be null!");
        Validate.notNull((Object)javaPlugin, (String)"The extract folder cannot be null!");
        Validate.notNull((Object)javaPlugin, (String)"The folder path cannot be null!");
        this.extractfolder = file;
        this.folderpath = string;
        this.plugin = javaPlugin;
        this.regex = string2;
    }

    public void extract() {
        this.extract(false, true);
    }

    public void extract(boolean bl) {
        this.extract(bl, true);
    }

    public void extract(boolean bl, boolean bl2) {
        Method object;
        File file = null;
        try {
            object = JavaPlugin.class.getDeclaredMethod("getFile", new Class[0]);
            object.setAccessible(true);
            file = (File)object.invoke((Object)this.plugin, new Object[0]);
        }
        catch (Exception exception) {
            
        }
        if (!this.extractfolder.exists()) {
            this.extractfolder.mkdirs();
        }
        JarFile jar = null;
        try {
            jar = new JarFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> enumeration = jar.entries();
        while (enumeration.hasMoreElements()) {
            File file2;
            JarEntry jarEntry = enumeration.nextElement();
            String string = jarEntry.getName();
            if (!string.startsWith(this.folderpath)) continue;
            if (jarEntry.isDirectory()) {
                if (!bl2 || (file2 = new File(this.extractfolder, jarEntry.getName().replaceFirst(this.folderpath, ""))).exists()) continue;
                file2.mkdirs();
                continue;
            }
            file2 = bl2 ? new File(this.extractfolder, string.replaceFirst(this.folderpath, "")) : new File(this.extractfolder, string.substring(string.indexOf(File.separatorChar), string.length()));
            String string2 = file2.getName();
            if (this.regex != null && !string2.matches(this.regex)) continue;
            if (file2.exists() && bl) {
                file2.delete();
            }
            if (file2.exists()) continue;

            try {
                InputStream inputStream = jar.getInputStream(jarEntry);
                FileOutputStream fileOutputStream = null;
                fileOutputStream = new FileOutputStream(file2);
                while (inputStream.available() > 0) {
                    fileOutputStream.write(inputStream.read());
                }
                fileOutputStream.close();
                inputStream.close();
                jar.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

