package noppes.npcs.scripted;

import noppes.npcs.controllers.*;
import net.minecraft.entity.*;

public class ScriptEventTarget extends ScriptEvent
{
    private ScriptLivingBase target;
    
    public ScriptEventTarget(final EntityLivingBase target) {
        if (target != null) {
            this.target = (ScriptLivingBase)ScriptController.Instance.getScriptForEntity((Entity)target);
        }
    }
    
    public ScriptLivingBase getTarget() {
        return this.target;
    }
    
    public void setTarget(final ScriptLivingBase target) {
        this.target = target;
    }
}
