package noppes.npcs.items;

import org.lwjgl.opengl.*;

public class ItemThrowingShuriken extends ItemThrowingWeapon
{
    public ItemThrowingShuriken(final int par1) {
        super(par1);
    }
    
    @Override
    public void renderSpecial() {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glTranslatef(-0.1f, 0.3f, 0.0f);
    }
    
    public boolean func_77629_n_() {
        return true;
    }
}
