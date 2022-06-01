package noppes.npcs.client.gui.util;

import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiButtonNextPage extends GuiNpcButton
{
    private final boolean field_146151_o;
    private static final String __OBFID = "CL_00000745";
    private static final ResourceLocation field_110405_a;
    
    public GuiButtonNextPage(final int par1, final int par2, final int par3, final boolean par4) {
        super(par1, par2, par3, 23, 13, "");
        this.field_146151_o = par4;
    }
    
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            final boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(GuiButtonNextPage.field_110405_a);
            int k = 0;
            int l = 192;
            if (flag) {
                k += 23;
            }
            if (!this.field_146151_o) {
                l += 13;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 23, 13);
        }
    }
    
    static {
        field_110405_a = new ResourceLocation("textures/gui/book.png");
    }
}
