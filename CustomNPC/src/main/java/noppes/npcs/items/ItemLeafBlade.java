package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemLeafBlade extends ItemNpcWeaponInterface
{
    public ItemLeafBlade(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.8f, 0.8f, 0.8f);
        GL11.glTranslatef(-0.2f, 0.28f, -0.12f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-16.0f, 0.0f, 0.0f, 1.0f);
    }
}
