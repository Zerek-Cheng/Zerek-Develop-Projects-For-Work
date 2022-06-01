package noppes.npcs.items;

import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import noppes.npcs.*;
import org.lwjgl.opengl.*;
import cpw.mods.fml.common.registry.*;

public class ItemNpcWeaponInterface extends ItemSword implements ItemRenderInterface
{
    public ItemNpcWeaponInterface(final int par1, final Item.ToolMaterial material) {
        this(material);
    }
    
    public ItemNpcWeaponInterface(final Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab((CreativeTabs)CustomItems.tab);
        CustomNpcs.proxy.registerItem((Item)this);
        this.setCreativeTab((CreativeTabs)CustomItems.tabWeapon);
    }
    
    public void renderSpecial() {
        GL11.glScalef(0.66f, 0.66f, 0.66f);
        GL11.glTranslatef(0.16f, 0.26f, 0.06f);
    }
    
    public Item setUnlocalizedName(final String name) {
        GameRegistry.registerItem((Item)this, name);
        return super.setUnlocalizedName(name);
    }
}
