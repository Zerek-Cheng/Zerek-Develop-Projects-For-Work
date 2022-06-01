package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemWarhammer extends ItemNpcWeaponInterface
{
    public ItemWarhammer(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(1.2f, 1.4f, 1.0f);
        GL11.glTranslatef(0.2f, -0.08f, 0.08f);
    }
}
