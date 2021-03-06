package noppes.npcs.items;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class ItemBattleAxe extends ItemNpcWeaponInterface
{
    public ItemBattleAxe(final int par1, final Item.ToolMaterial tool) {
        super(par1, tool);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(1.0f, 0.8f, 1.0f);
        GL11.glTranslatef(-0.04f, 0.2f, -0.16f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
    }
}
