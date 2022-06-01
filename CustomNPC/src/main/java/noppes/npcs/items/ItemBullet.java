package noppes.npcs.items;

import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.creativetab.*;

public class ItemBullet extends ItemNpcInterface
{
    private EnumNpcToolMaterial material;
    
    public ItemBullet(final int par1, final EnumNpcToolMaterial material) {
        super(par1);
        this.material = material;
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    public int getBulletDamage() {
        return this.material.getDamageVsEntity();
    }
}
