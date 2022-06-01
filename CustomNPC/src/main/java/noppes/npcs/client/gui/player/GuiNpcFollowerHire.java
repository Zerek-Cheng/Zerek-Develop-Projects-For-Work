package noppes.npcs.client.gui.player;

import noppes.npcs.entity.*;
import noppes.npcs.containers.*;
import noppes.npcs.roles.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import noppes.npcs.client.*;
import java.util.*;

public class GuiNpcFollowerHire extends GuiContainerNPCInterface
{
    private final ResourceLocation resource;
    private EntityNPCInterface npc;
    private ContainerNPCFollowerHire container;
    private RoleFollower role;
    
    public GuiNpcFollowerHire(final EntityNPCInterface npc, final ContainerNPCFollowerHire container) {
        super(npc, container);
        this.resource = new ResourceLocation("customnpcs", "textures/gui/followerhire.png");
        this.container = container;
        this.npc = npc;
        this.role = (RoleFollower)npc.roleInterface;
        this.closeOnEsc = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addButton(new GuiNpcButton(5, this.guiLeft + 26, this.guiTop + 60, 50, 20, StatCollector.translateToLocal("follower.hire")));
    }
    
    public void actionPerformed(final GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 5) {
            NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerHire, new Object[0]);
            this.close();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.resource);
        final int l = (this.width - this.xSize) / 2;
        final int i2 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(l, i2, 0, 0, this.xSize, this.ySize);
        int index = 0;
        for (final int id : this.role.inventory.items.keySet()) {
            final ItemStack itemstack = this.role.inventory.items.get(id);
            if (itemstack == null) {
                continue;
            }
            int days = 1;
            if (this.role.rates.containsKey(id)) {
                days = this.role.rates.get(id);
            }
            final int yOffset = index * 26;
            final int x = this.guiLeft + 78;
            final int y = this.guiTop + yOffset + 10;
            GL11.glEnable(32826);
            RenderHelper.enableGUIStandardItemLighting();
            GuiNpcFollowerHire.itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemstack, x + 11, y);
            GuiNpcFollowerHire.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemstack, x + 11, y);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            final String daysS = days + " " + ((days == 1) ? StatCollector.translateToLocal("follower.day") : StatCollector.translateToLocal("follower.days"));
            this.fontRendererObj.drawString(" = " + daysS, x + 27, y + 4, CustomNpcResourceListener.DefaultTextColor);
            if (this.isPointInRegion(x - this.guiLeft + 11, y - this.guiTop, 16, 16, this.mouseX, this.mouseY)) {
                this.renderToolTip(itemstack, this.mouseX, this.mouseY);
            }
            ++index;
        }
    }
    
    @Override
    public void save() {
    }
}
