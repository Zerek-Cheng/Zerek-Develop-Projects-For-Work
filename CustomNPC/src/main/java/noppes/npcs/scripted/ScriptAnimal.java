package noppes.npcs.scripted;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ScriptAnimal extends ScriptLiving
{
    public ScriptAnimal(final EntityAnimal entity) {
        super((EntityLiving)entity);
    }
    
    @Override
    public int getType() {
        return 4;
    }
    
    @Override
    public boolean typeOf(final int type) {
        return type == 4 || super.typeOf(type);
    }
}
