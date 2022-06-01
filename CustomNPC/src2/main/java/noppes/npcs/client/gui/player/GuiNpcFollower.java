package noppes.npcs.client.gui.player;

import noppes.npcs.entity.*;
import noppes.npcs.roles.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.util.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.nbt.*;

public class GuiNpcFollower extends GuiContainerNPCInterface implements IGuiData
{
    private final ResourceLocation resource;
    private EntityNPCInterface npc;
    private RoleFollower role;
    
    public GuiNpcFollower(final EntityNPCInterface npc, final ContainerNPCFollower container) {
        super(npc, container);
        this.resource = new ResourceLocation("customnpcs", "textures/gui/follower.png");
        this.npc = npc;
        this.role = (RoleFollower)npc.roleInterface;
        this.closeOnEsc = true;
        NoppesUtilPlayer.sendData(EnumPlayerPacket.RoleGet, new Object[0]);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.addButton(new GuiNpcButton(4, this.guiLeft + 100, this.guiTop + 110, 50, 20, new String[] { StatCollector.translateToLocal("follower.waiting"), StatCollector.translateToLocal("follower.following") }, (int)(this.role.isFollowing ? 1 : 0)));
        if (!this.role.infiniteDays) {
            this.addButton(new GuiNpcButton(5, this.guiLeft + 8, this.guiTop + 30, 50, 20, StatCollector.translateToLocal("follower.hire")));
        }
    }
    
    public void actionPerformed(final GuiButton guibutton) {
        super.actionPerformed(guibutton);
        final int id = guibutton.id;
        if (id == 4) {
            NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerState, new Object[0]);
        }
        if (id == 5) {
            NoppesUtilPlayer.sendData(EnumPlayerPacket.FollowerExtend, new Object[0]);
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("follower.health") + ": " + this.npc.getHealth() + "/" + this.npc.getMaxHealth(), 62, 70, CustomNpcResourceListener.DefaultTextColor);
        if (!this.role.infiniteDays) {
            if (this.role.getDaysLeft() <= 1) {
                this.fontRendererObj.drawString(StatCollector.translateToLocal("follower.daysleft") + ": " + StatCollector.translateToLocal("follower.lastday"), 62, 94, CustomNpcResourceListener.DefaultTextColor);
            }
            else {
                this.fontRendererObj.drawString(StatCollector.translateToLocal("follower.daysleft") + ": " + (this.role.getDaysLeft() - 1), 62, 94, CustomNpcResourceListener.DefaultTextColor);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.resource);
        final int l = this.guiLeft;
        final int i2 = this.guiTop;
        this.drawTexturedModalRect(l, i2, 0, 0, this.xSize, this.ySize);
        int index = 0;
        if (!this.role.infiniteDays) {
            for (final int id : this.role.inventory.items.keySet()) {
                final ItemStack itemstack = this.role.inventory.items.get(id);
                if (itemstack == null) {
                    continue;
                }
                int days = 1;
                if (this.role.rates.containsKey(id)) {
                    days = this.role.rates.get(id);
                }
                final int yOffset = index * 20;
                final int x = this.guiLeft + 68;
                final int y = this.guiTop + yOffset + 4;
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glEnable(32826);
                GL11.glEnable(2903);
                GL11.glEnable(2896);
                GuiNpcFollower.itemRender.renderItemIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemstack, x + 11, y);
                GuiNpcFollower.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, itemstack, x + 11, y);
                RenderHelper.disableStandardItemLighting();
                GL11.glDisable(32826);
                final String daysS = days + " " + ((days == 1) ? StatCollector.translateToLocal("follower.day") : StatCollector.translateToLocal("follower.days"));
                this.fontRendererObj.drawString(" = " + daysS, x + 27, y + 4, CustomNpcResourceListener.DefaultTextColor);
                ++index;
            }
        }
        this.drawNpc(33, 131);
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void setGuiData(final NBTTagCompound compound) {
        this.npc.roleInterface.readFromNBT(compound);
        this.initGui();
    }
}
