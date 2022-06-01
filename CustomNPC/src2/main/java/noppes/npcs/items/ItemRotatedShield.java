package noppes.npcs.items;

import noppes.npcs.constants.*;
import org.lwjgl.opengl.*;

public class ItemRotatedShield extends ItemShield
{
    public ItemRotatedShield(final int par1, final EnumNpcToolMaterial material) {
        super(par1, material);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        GL11.glTranslatef(0.4f, 1.0f, -0.18f);
        GL11.glRotatef(-6.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
    }
}
