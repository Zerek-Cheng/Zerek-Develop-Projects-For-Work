package noppes.npcs.scripted;

import net.minecraft.util.*;
import noppes.npcs.controllers.*;
import net.minecraft.entity.*;

public class ScriptEventKilled extends ScriptEvent
{
    private ScriptLivingBase source;
    private DamageSource damagesource;
    
    public ScriptEventKilled(final EntityLivingBase target, final DamageSource damagesource) {
        this.damagesource = damagesource;
        this.source = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity((Entity)target);
    }
    
    public ScriptLivingBase getSource() {
        return this.source;
    }
    
    public String getType() {
        return this.damagesource.damageType;
    }
}
