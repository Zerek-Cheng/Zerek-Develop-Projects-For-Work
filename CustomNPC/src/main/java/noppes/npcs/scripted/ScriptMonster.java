package noppes.npcs.scripted;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ScriptMonster extends ScriptLiving
{
    public ScriptMonster(final EntityMob entity) {
        super((EntityLiving)entity);
    }
    
    @Override
    public int getType() {
        return 3;
    }
    
    @Override
    public boolean typeOf(final int type) {
        return type == 3 || super.typeOf(type);
    }
}
