package noppes.npcs.client.gui.util;

import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class GuiMenuSideButton extends GuiNpcButton
{
    public static final ResourceLocation resource;
    public boolean active;
    
    public GuiMenuSideButton(final int i, final int j, final int k, final String s) {
        this(i, j, k, 200, 20, s);
    }
    
    public GuiMenuSideButton(final int i, final int j, final int k, final int l, final int i1, final String s) {
        super(i, j, k, l, i1, s);
        this.active = false;
    }
    
    public int getHoverState(final boolean flag) {
        if (this.active) {
            return 0;
        }
        return 1;
    }
    
    public void drawButton(final Minecraft minecraft, final int i, final int j) {
        if (!this.visible) {
            return;
        }
        final FontRenderer fontrenderer = minecraft.fontRendererObj;
        minecraft.renderEngine.bindTexture(GuiMenuSideButton.resource);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int width = this.width + (this.active ? 2 : 0);
        this.hovered = (i >= this.xPosition && j >= this.yPosition && i < this.xPosition + width && j < this.yPosition + this.height);
        final int k = this.getHoverState(this.hovered);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, k * 22, width, this.height);
        this.mouseDragged(minecraft, i, j);
        String text = "";
        final float maxWidth = width * 0.75f;
        if (fontrenderer.getStringWidth(this.displayString) > maxWidth) {
            for (int h = 0; h < this.displayString.length(); ++h) {
                final char c = this.displayString.charAt(h);
                if (fontrenderer.getStringWidth(text + c) > maxWidth) {
                    break;
                }
                text += c;
            }
            text += "...";
        }
        else {
            text = this.displayString;
        }
        if (this.active) {
            this.drawCenteredString(fontrenderer, text, this.xPosition + width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
        }
        else if (this.hovered) {
            this.drawCenteredString(fontrenderer, text, this.xPosition + width / 2, this.yPosition + (this.height - 8) / 2, 16777120);
        }
        else {
            this.drawCenteredString(fontrenderer, text, this.xPosition + width / 2, this.yPosition + (this.height - 8) / 2, 14737632);
        }
    }
    
    protected void mouseDragged(final Minecraft minecraft, final int i, final int j) {
    }
    
    public void mouseReleased(final int i, final int j) {
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int i, final int j) {
        return !this.active && this.visible && this.hovered;
    }
    
    static {
        resource = new ResourceLocation("customnpcs", "textures/gui/menusidebutton.png");
    }
}
