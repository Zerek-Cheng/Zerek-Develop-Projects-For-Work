package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import noppes.npcs.*;

public class ItemKunaiReversed extends ItemKunai
{
    public ItemKunaiReversed(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.4f, 0.4f, 0.4f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(-0.4f, -0.9f, 0.2f);
    }
    
    public void registerIcons(final IIconRegister par1IconRegister) {
        this.itemIcon = CustomItems.kunai.getIconFromDamage(0);
    }
}
