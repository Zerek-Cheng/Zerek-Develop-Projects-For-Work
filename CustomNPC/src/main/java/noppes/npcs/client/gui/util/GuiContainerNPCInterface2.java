package noppes.npcs.client.gui.util;

import net.minecraft.util.*;
import noppes.npcs.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public abstract class GuiContainerNPCInterface2 extends GuiContainerNPCInterface
{
    private ResourceLocation background;
    private final ResourceLocation defaultBackground;
    private GuiNpcMenu menu;
    public int menuYOffset;
    
    public GuiContainerNPCInterface2(final EntityNPCInterface npc, final Container cont) {
        this(npc, cont, -1);
    }
    
    public GuiContainerNPCInterface2(final EntityNPCInterface npc, final Container cont, final int activeMenu) {
        super(npc, cont);
        this.background = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
        this.defaultBackground = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
        this.menuYOffset = 0;
        this.xSize = 420;
        this.menu = new GuiNpcMenu((GuiScreen)this, activeMenu, npc);
        this.title = "";
    }
    
    public void setBackground(final String texture) {
        this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }
    
    @Override
    public ResourceLocation getResource(final String texture) {
        return new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.menu.initGui(this.guiLeft, this.guiTop + this.menuYOffset, this.xSize);
    }
    
    @Override
    protected void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (!this.hasSubGui()) {
            this.menu.mouseClicked(i, j, k);
        }
    }
    
    public void delete() {
        this.npc.delete();
        this.displayGuiScreen(null);
        this.mc.setIngameFocus();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.background);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 256, 256);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.defaultBackground);
        this.drawTexturedModalRect(this.guiLeft + this.xSize - 200, this.guiTop, 26, 0, 200, 220);
        this.menu.drawElements(this.fontRendererObj, i, j, this.mc, f);
        super.drawGuiContainerBackgroundLayer(f, i, j);
    }
}
