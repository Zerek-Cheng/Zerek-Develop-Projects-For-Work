package noppes.npcs.client.controllers;

import noppes.npcs.*;
import net.minecraft.nbt.*;
import java.util.*;
import java.io.*;

public class PresetController
{
    public HashMap<String, Preset> presets;
    private File dir;
    public static PresetController instance;
    
    public PresetController(final File dir) {
        this.presets = new HashMap<String, Preset>();
        PresetController.instance = this;
        this.dir = dir;
        this.load();
    }
    
    public Preset getPreset(final String username) {
        if (this.presets.isEmpty()) {
            this.load();
        }
        return this.presets.get(username.toLowerCase());
    }
    
    public void load() {
        final NBTTagCompound compound = this.loadPreset();
        final HashMap<String, Preset> presets = new HashMap<String, Preset>();
        if (compound != null) {
            final NBTTagList list = compound.getTagList("Presets", 10);
            for (int i = 0; i < list.tagCount(); ++i) {
                final NBTTagCompound comp = list.getCompoundTagAt(i);
                final Preset preset = new Preset();
                preset.readFromNBT(comp);
                presets.put(preset.name.toLowerCase(), preset);
            }
        }
        Preset.FillDefault(presets);
        this.presets = presets;
    }
    
    private NBTTagCompound loadPreset() {
        final String filename = "presets.dat";
        try {
            final File file = new File(this.dir, filename);
            if (!file.exists()) {
                return null;
            }
            return CompressedStreamTools.readCompressed((InputStream)new FileInputStream(file));
        }
        catch (Exception e) {
            LogWriter.except(e);
            try {
                final File file = new File(this.dir, filename + "_old");
                if (!file.exists()) {
                    return null;
                }
                return CompressedStreamTools.readCompressed((InputStream)new FileInputStream(file));
            }
            catch (Exception e) {
                LogWriter.except(e);
                return null;
            }
        }
    }
    
    public void save() {
        final NBTTagCompound compound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        for (final Preset preset : this.presets.values()) {
            list.appendTag((NBTBase)preset.writeToNBT());
        }
        compound.setTag("Presets", (NBTBase)list);
        this.savePreset(compound);
    }
    
    private void savePreset(final NBTTagCompound compound) {
        final String filename = "presets.dat";
        try {
            final File file = new File(this.dir, filename + "_new");
            final File file2 = new File(this.dir, filename + "_old");
            final File file3 = new File(this.dir, filename);
            CompressedStreamTools.writeCompressed(compound, (OutputStream)new FileOutputStream(file));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file.renameTo(file3);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception e) {
            LogWriter.except(e);
        }
    }
    
    public void addPreset(final Preset preset) {
        while (this.presets.containsKey(preset.name.toLowerCase())) {
            preset.name += "_";
        }
        this.presets.put(preset.name.toLowerCase(), preset);
        this.save();
    }
    
    public void removePreset(final String preset) {
        if (preset == null) {
            return;
        }
        this.presets.remove(preset.toLowerCase());
        this.save();
    }
}
