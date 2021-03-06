package noppes.npcs.client.gui.util;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiMenuTopIconButton extends GuiMenuTopButton
{
    private static final ResourceLocation resource;
    protected static RenderItem itemRender;
    private ItemStack item;
    
    public GuiMenuTopIconButton(final int i, final int x, final int y, final String s, final ItemStack item) {
        super(i, x, y, s);
        this.width = 28;
        this.height = 28;
        this.item = item;
    }
    
    public GuiMenuTopIconButton(final int i, final GuiButton parent, final String s, final ItemStack item) {
        super(i, parent, s);
        this.width = 28;
        this.height = 28;
        this.item = item;
    }
    
    public GuiMenuTopIconButton(final int i, final int x, final int y, final String s, final IButtonListener listener, final ItemStack item) {
        super(i, x, y, s);
        this.width = 28;
        this.height = 28;
        this.item = item;
        this.listener = listener;
    }
    
    public GuiMenuTopIconButton(final int i, final GuiButton parent, final String s, final IButtonListener listener, final ItemStack item) {
        super(i, parent, s, listener);
        this.width = 28;
        this.height = 28;
        this.item = item;
    }
    
    @Override
    public void drawButton(final Minecraft minecraft, final int i, final int j) {
        if (!this.getVisible()) {
            return;
        }
        if (this.item.getItem() == null) {
            this.item = new ItemStack(Blocks.dirt);
        }
        this.hover = (i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.getWidth() && j < this.yPosition + this.height);
        final Minecraft mc = Minecraft.getMinecraft();
        if (this.hover && !this.active) {
            final int x = i + mc.fontRendererObj.getStringWidth(this.displayString);
            GL11.glTranslatef((float)x, (float)(this.yPosition + 2), 0.0f);
            this.drawHoveringText(Arrays.asList(this.displayString), 0, 0, mc.fontRendererObj);
            GL11.glTranslatef((float)(-x), (float)(-(this.yPosition + 2)), 0.0f);
        }
        mc.getTextureManager().bindTexture(GuiMenuTopIconButton.resource);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        this.drawTexturedModalRect(this.xPosition, this.yPosition + (this.active ? 2 : 0), 0, this.active ? 32 : 0, 28, 28);
        this.zLevel = 100.0f;
        GuiMenuTopIconButton.itemRender.zLevel = 100.0f;
        GL11.glEnable(2896);
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        GuiMenuTopIconButton.itemRender.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), this.item, this.xPosition + 6, this.yPosition + 10);
        GuiMenuTopIconButton.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), this.item, this.xPosition + 6, this.yPosition + 10);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GuiMenuTopIconButton.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
        GL11.glPopMatrix();
    }
    
    protected void drawHoveringText(final List textLines, final int x, final int y, final FontRenderer font) {
        if (!textLines.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int k = 0;
            for (final String s : textLines) {
                final int l = font.getStringWidth(s);
                if (l > k) {
                    k = l;
                }
            }
            int j2 = x + 12;
            int k2 = y - 12;
            int i1 = 8;
            if (textLines.size() > 1) {
                i1 += 2 + (textLines.size() - 1) * 10;
            }
            if (j2 + k > this.width) {
                j2 -= 28 + k;
            }
            if (k2 + i1 + 6 > this.height) {
                k2 = this.height - i1 - 6;
            }
            this.zLevel = 300.0f;
            GuiMenuTopIconButton.itemRender.zLevel = 300.0f;
            final int j3 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j3, j3);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j3, j3);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j3, j3);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j3, j3);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j3, j3);
            final int k3 = 1347420415;
            final int l2 = (k3 & 0xFEFEFE) >> 1 | (k3 & 0xFF000000);
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k3, l2);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k3, l2);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k3, k3);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l2, l2);
            for (int i2 = 0; i2 < textLines.size(); ++i2) {
                final String s2 = textLines.get(i2);
                font.drawStringWithShadow(s2, j2, k2, -1);
                if (i2 == 0) {
                    k2 += 2;
                }
                k2 += 10;
            }
            this.zLevel = 0.0f;
            GuiMenuTopIconButton.itemRender.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }
    
    static {
        resource = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        GuiMenuTopIconButton.itemRender = new RenderItem();
    }
}
