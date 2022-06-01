package noppes.npcs.util;

import noppes.npcs.entity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public interface IProjectileCallback
{
    boolean onImpact(final EntityProjectile p0, final EntityLivingBase p1, final ItemStack p2);
}
