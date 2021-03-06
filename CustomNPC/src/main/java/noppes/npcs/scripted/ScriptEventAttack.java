package noppes.npcs.scripted;

import noppes.npcs.controllers.*;
import net.minecraft.entity.*;

public class ScriptEventAttack extends ScriptEvent
{
    private float damage;
    private ScriptLivingBase target;
    private boolean isRanged;
    
    public ScriptEventAttack(final float damage, final EntityLivingBase target, final boolean isRanged) {
        this.damage = damage;
        this.isRanged = isRanged;
        this.target = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity((Entity)target);
    }
    
    public ScriptLivingBase getTarget() {
        return this.target;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public boolean isRange() {
        return this.isRanged;
    }
}
