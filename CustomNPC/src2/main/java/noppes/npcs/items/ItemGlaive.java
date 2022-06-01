package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemGlaive extends ItemNpcWeaponInterface
{
    public ItemGlaive(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glTranslatef(0.03f, -0.4f, 0.08f);
    }
}
