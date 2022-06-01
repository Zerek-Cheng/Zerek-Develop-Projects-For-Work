package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.inventory.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import noppes.npcs.client.*;
import org.lwjgl.opengl.*;

@SideOnly(Side.CLIENT)
public class GuiCrate extends GuiContainer
{
    private static final ResourceLocation field_147017_u;
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows;
    
    public GuiCrate(final ContainerCrate container) {
        super((Container)container);
        this.upperChestInventory = container.upperChestInventory;
        this.lowerChestInventory = container.lowerChestInventory;
        this.allowUserInput = false;
        final short short1 = 222;
        final int i = short1 - 108;
        this.inventoryRows = this.lowerChestInventory.getSizeInventory() / 9;
        this.ySize = i + this.inventoryRows * 18;
    }
    
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        this.fontRendererObj.drawString(this.lowerChestInventory.isCustomInventoryName() ? this.lowerChestInventory.getInventoryName() : I18n.format(this.lowerChestInventory.getInventoryName(), new Object[0]), 8, 6, CustomNpcResourceListener.DefaultTextColor);
        this.fontRendererObj.drawString(this.upperChestInventory.isCustomInventoryName() ? this.upperChestInventory.getInventoryName() : I18n.format(this.upperChestInventory.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, CustomNpcResourceListener.DefaultTextColor);
    }
    
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiCrate.field_147017_u);
        final int k = (this.width - this.xSize) / 2;
        final int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    static {
        field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
