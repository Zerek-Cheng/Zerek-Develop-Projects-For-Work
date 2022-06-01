package noppes.npcs.scripted.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.scripted.*;
import noppes.npcs.controllers.*;
import net.minecraft.entity.*;
import java.util.*;

public class ScriptJobSpawner extends ScriptJobInterface
{
    private JobSpawner job;
    
    public ScriptJobSpawner(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobSpawner)npc.jobInterface;
    }
    
    @Override
    public int getType() {
        return 6;
    }
    
    public ScriptLivingBase spawnEntity(final int number) {
        final EntityLivingBase base = this.job.spawnEntity(number);
        if (base == null) {
            return null;
        }
        return (ScriptLivingBase)ScriptController.Instance.getScriptForEntity((Entity)base);
    }
    
    public void removeAllSpawned() {
        for (final EntityLivingBase entity : this.job.spawned) {
            entity.isDead = true;
        }
        this.job.spawned = new ArrayList<EntityLivingBase>();
    }
}
