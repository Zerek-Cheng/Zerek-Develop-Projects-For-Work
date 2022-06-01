package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemDagger extends ItemNpcWeaponInterface
{
    public ItemDagger(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        GL11.glTranslatef(0.14f, 0.22f, 0.06f);
    }
}
