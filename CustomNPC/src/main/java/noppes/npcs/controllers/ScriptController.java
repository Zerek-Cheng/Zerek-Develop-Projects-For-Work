package noppes.npcs.controllers;

import noppes.npcs.*;
import net.minecraft.world.*;
import noppes.npcs.util.*;
import java.io.*;
import javax.script.*;
import net.minecraft.nbt.*;
import java.util.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import noppes.npcs.scripted.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.world.*;
import net.minecraft.server.*;
import cpw.mods.fml.common.eventhandler.*;

public class ScriptController
{
    public static ScriptController Instance;
    public static boolean HasStart;
    private ScriptEngineManager manager;
    public Map<String, String> languages;
    public Map<String, String> scripts;
    public long lastLoaded;
    public File dir;
    public NBTTagCompound compound;
    private boolean loaded;
    public boolean shouldSave;
    
    public ScriptController() {
        this.languages = new HashMap<String, String>();
        this.scripts = new HashMap<String, String>();
        this.lastLoaded = 0L;
        this.compound = new NBTTagCompound();
        this.loaded = false;
        this.shouldSave = false;
        this.loaded = false;
        ScriptController.Instance = this;
        this.manager = new ScriptEngineManager();
        LogWriter.info("Script Engines Available:");
        for (final ScriptEngineFactory fac : this.manager.getEngineFactories()) {
            if (fac.getExtensions().isEmpty()) {
                continue;
            }
            this.manager.getEngineByName(fac.getLanguageName());
            final String ext = "." + fac.getExtensions().get(0).toLowerCase();
            LogWriter.info(fac.getLanguageName() + ": " + ext);
            this.languages.put(fac.getLanguageName(), ext);
        }
    }
    
    private void loadCategories() {
        this.dir = new File(CustomNpcs.getWorldSaveDirectory(), "scripts");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        if (!this.getSavedFile().exists()) {
            this.shouldSave = true;
        }
        new ScriptWorld(null).clearTempData();
        this.scripts.clear();
        for (final String language : this.languages.keySet()) {
            final String ext = this.languages.get(language);
            final File scriptDir = new File(this.dir, language.toLowerCase());
            if (!scriptDir.exists()) {
                scriptDir.mkdir();
            }
            else {
                this.loadDir(scriptDir, "", ext);
            }
        }
        this.lastLoaded = System.currentTimeMillis();
    }
    
    private void loadDir(final File dir, final String name, final String ext) {
        for (final File file : dir.listFiles()) {
            final String filename = name + file.getName().toLowerCase();
            if (file.isDirectory()) {
                this.loadDir(file, filename + "/", ext);
            }
            else if (filename.endsWith(ext)) {
                try {
                    this.scripts.put(filename, this.readFile(file));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public boolean loadStoredData() {
        this.loadCategories();
        final File file = this.getSavedFile();
        try {
            if (!file.exists()) {
                return false;
            }
            this.compound = NBTJsonUtil.LoadFile(file);
            this.shouldSave = false;
        }
        catch (Exception e) {
            LogWriter.error("Error loading: " + file.getAbsolutePath(), e);
            return false;
        }
        return true;
    }
    
    private File getSavedFile() {
        return new File(this.dir, "world_data.json");
    }
    
    private String readFile(final File file) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            final StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        }
        finally {
            br.close();
        }
    }
    
    public ScriptEngine getEngineByName(final String language) {
        return this.manager.getEngineByName(language);
    }
    
    public NBTTagList nbtLanguages() {
        final NBTTagList list = new NBTTagList();
        for (final String language : this.languages.keySet()) {
            final NBTTagCompound compound = new NBTTagCompound();
            final NBTTagList scripts = new NBTTagList();
            for (final String script : this.getScripts(language)) {
                scripts.appendTag((NBTBase)new NBTTagString(script));
            }
            compound.setTag("Scripts", (NBTBase)scripts);
            compound.setString("Language", language);
            list.appendTag((NBTBase)compound);
        }
        return list;
    }
    
    private List<String> getScripts(final String language) {
        final List<String> list = new ArrayList<String>();
        final String ext = this.languages.get(language);
        if (ext == null) {
            return list;
        }
        for (final String script : this.scripts.keySet()) {
            if (script.endsWith(ext)) {
                list.add(script);
            }
        }
        return list;
    }
    
    public ScriptEntity getScriptForEntity(final Entity entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof EntityNPCInterface) {
            return ((EntityNPCInterface)entity).script.dummyNpc;
        }
        ScriptEntityData data = (ScriptEntityData)entity.getExtendedProperties("ScriptedObject");
        if (data != null) {
            return data.base;
        }
        if (entity instanceof EntityPlayerMP) {
            data = new ScriptEntityData(new ScriptPlayer((EntityPlayerMP)entity));
        }
        else {
            if (PixelmonHelper.isPixelmon(entity)) {
                return new ScriptPixelmon((EntityTameable)entity);
            }
            if (entity instanceof EntityAnimal) {
                data = new ScriptEntityData(new ScriptAnimal((EntityAnimal)entity));
            }
            else if (entity instanceof EntityMob) {
                data = new ScriptEntityData(new ScriptMonster((EntityMob)entity));
            }
            else if (entity instanceof EntityLiving) {
                data = new ScriptEntityData(new ScriptLiving((EntityLiving)entity));
            }
            else if (entity instanceof EntityLivingBase) {
                data = new ScriptEntityData(new ScriptLivingBase((EntityLivingBase)entity));
            }
            else {
                data = new ScriptEntityData(new ScriptEntity(entity));
            }
        }
        entity.registerExtendedProperties("ScriptedObject", (IExtendedEntityProperties)data);
        return data.base;
    }
    
    @SubscribeEvent
    public void saveWorld(final WorldEvent.Save event) {
        if (!this.shouldSave || event.world.isRemote || event.world != MinecraftServer.getServer().worldServers[0]) {
            return;
        }
        try {
            NBTJsonUtil.SaveFile(this.getSavedFile(), this.compound);
        }
        catch (Exception e) {
            LogWriter.except(e);
        }
        this.shouldSave = false;
    }
    
    static {
        ScriptController.HasStart = false;
    }
}
