package noppes.npcs;

import noppes.npcs.scripted.constants.*;
import noppes.npcs.entity.*;
import net.minecraft.nbt.*;
import java.util.*;
import noppes.npcs.constants.*;
import net.minecraft.entity.*;
import noppes.npcs.controllers.*;
import noppes.npcs.scripted.*;
import javax.script.*;
import net.minecraft.world.*;

public class DataScript
{
    public Map<Integer, ScriptContainer> scripts;
    private static final EntityType entities;
    private static final JobType jobs;
    private static final RoleType roles;
    public String scriptLanguage;
    private EntityNPCInterface npc;
    public boolean enabled;
    public ScriptNpc dummyNpc;
    public ScriptWorld dummyWorld;
    public boolean clientNeedsUpdate;
    public boolean aiNeedsUpdate;
    public boolean hasInited;
    
    public DataScript(final EntityNPCInterface npc) {
        this.scripts = new HashMap<Integer, ScriptContainer>();
        this.scriptLanguage = "ECMAScript";
        this.enabled = false;
        this.clientNeedsUpdate = false;
        this.aiNeedsUpdate = false;
        this.hasInited = false;
        this.npc = npc;
        if (npc instanceof EntityCustomNpc) {
            this.dummyNpc = new ScriptNpc((EntityCustomNpc)npc);
        }
        if (npc.worldObj instanceof WorldServer) {
            this.dummyWorld = new ScriptWorld((WorldServer)npc.worldObj);
        }
    }
    
    public void readFromNBT(final NBTTagCompound compound) {
        this.scripts = this.readScript(compound.getTagList("ScriptsContainers", 10));
        this.scriptLanguage = compound.getString("ScriptLanguage");
        this.enabled = compound.getBoolean("ScriptEnabled");
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound compound) {
        compound.setTag("ScriptsContainers", (NBTBase)this.writeScript(this.scripts));
        compound.setString("ScriptLanguage", this.scriptLanguage);
        compound.setBoolean("ScriptEnabled", this.enabled);
        return compound;
    }
    
    private Map<Integer, ScriptContainer> readScript(final NBTTagList list) {
        final Map<Integer, ScriptContainer> scripts = new HashMap<Integer, ScriptContainer>();
        for (int i = 0; i < list.tagCount(); ++i) {
            final NBTTagCompound compoundd = list.getCompoundTagAt(i);
            final ScriptContainer script = new ScriptContainer();
            script.readFromNBT(compoundd);
            if (script.hasCode() || this.npc.isRemote()) {
                scripts.put(compoundd.getInteger("Type"), script);
            }
        }
        return scripts;
    }
    
    private NBTTagList writeScript(final Map<Integer, ScriptContainer> scripts) {
        final NBTTagList list = new NBTTagList();
        for (final Integer type : scripts.keySet()) {
            final NBTTagCompound compoundd = new NBTTagCompound();
            compoundd.setInteger("Type", (int)type);
            final ScriptContainer script = scripts.get(type);
            script.writeToNBT(compoundd);
            list.appendTag((NBTBase)compoundd);
        }
        return list;
    }
    
    public boolean callScript(final EnumScriptType type, final Object... obs) {
        if (this.aiNeedsUpdate) {
            this.npc.updateAI = true;
            this.aiNeedsUpdate = false;
        }
        if (this.clientNeedsUpdate) {
            this.npc.updateClient = true;
            this.clientNeedsUpdate = false;
        }
        if (!this.isEnabled()) {
            return false;
        }
        if (!this.hasInited) {
            this.hasInited = true;
            this.callScript(EnumScriptType.INIT, new Object[0]);
        }
        final ScriptContainer script = this.scripts.get(type.ordinal());
        if (script == null || script.errored || !script.hasCode()) {
            return false;
        }
        script.setEngine(this.scriptLanguage);
        if (script.engine == null) {
            return false;
        }
        for (int i = 0; i + 1 < obs.length; i += 2) {
            Object ob = obs[i + 1];
            if (ob instanceof Entity) {
                ob = ScriptController.Instance.getScriptForEntity((Entity)ob);
            }
            script.engine.put(obs[i].toString(), ob);
        }
        return this.callScript(script);
    }
    
    public boolean isEnabled() {
        return this.enabled && ScriptController.HasStart && !this.npc.worldObj.isRemote && !this.scripts.isEmpty();
    }
    
    private boolean callScript(final ScriptContainer script) {
        final ScriptEngine engine = script.engine;
        engine.put("npc", this.dummyNpc);
        engine.put("world", this.dummyWorld);
        ScriptEvent result = (ScriptEvent)engine.get("event");
        if (result == null) {
            engine.put("event", result = new ScriptEvent());
        }
        engine.put("EntityType", DataScript.entities);
        engine.put("RoleType", DataScript.roles);
        engine.put("JobType", DataScript.jobs);
        script.run(engine);
        if (this.clientNeedsUpdate) {
            this.npc.updateClient = true;
            this.clientNeedsUpdate = false;
        }
        if (this.aiNeedsUpdate) {
            this.npc.updateAI = true;
            this.aiNeedsUpdate = false;
        }
        return result.isCancelled();
    }
    
    public void setWorld(final World world) {
        if (world instanceof WorldServer) {
            this.dummyWorld = new ScriptWorld((WorldServer)world);
        }
    }
    
    static {
        entities = new EntityType();
        jobs = new JobType();
        roles = new RoleType();
    }
}
