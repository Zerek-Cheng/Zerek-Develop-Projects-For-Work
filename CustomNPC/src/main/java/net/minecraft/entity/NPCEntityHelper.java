package net.minecraft.entity;

import net.minecraft.item.*;

public class NPCEntityHelper
{
    public static Item getDropItem(final EntityLiving entity) {
        return entity.getDropItem();
    }
    
    public static void setRecentlyHit(final EntityLivingBase entity) {
        entity.recentlyHit = 100;
    }
}
