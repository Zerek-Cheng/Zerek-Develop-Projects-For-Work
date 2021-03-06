package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.registry.*;

public class ItemNpcArmor extends ItemArmor
{
    private String texture;
    
    public ItemNpcArmor(final int par1, final ItemArmor.ArmorMaterial par2EnumArmorMaterial, final int par4, final String texture) {
        super(par2EnumArmorMaterial, 0, par4);
        this.texture = texture;
        this.setCreativeTab((CreativeTabs)CustomItems.tabArmor);
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        if (this.armorType == 2) {
            return "customnpcs:textures/armor/" + this.texture + "_2.png";
        }
        return "customnpcs:textures/armor/" + this.texture + "_1.png";
    }
    
    public Item setUnlocalizedName(final String name) {
        GameRegistry.registerItem((Item)this, name);
        return super.setUnlocalizedName(name);
    }
}
