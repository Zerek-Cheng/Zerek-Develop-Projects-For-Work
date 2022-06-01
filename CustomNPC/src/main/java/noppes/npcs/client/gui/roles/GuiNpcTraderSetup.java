package noppes.npcs.client.gui.roles;

import net.minecraft.util.*;
import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.*;
import net.minecraft.nbt.*;

public class GuiNpcTraderSetup extends GuiContainerNPCInterface2 implements ITextfieldListener
{
    private final ResourceLocation slot;
    private RoleTrader role;
    
    public GuiNpcTraderSetup(final EntityNPCInterface npc, final ContainerNPCTraderSetup container) {
        super(npc, container);
        this.slot = new ResourceLocation("customnpcs", "textures/gui/slot.png");
        this.ySize = 220;
        this.menuYOffset = 10;
        this.role = container.role;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.setBackground("tradersetup.png");
        this.addLabel(new GuiNpcLabel(0, "role.marketname", this.guiLeft + 214, this.guiTop + 150));
        this.addTextField(new GuiNpcTextField(0, (GuiScreen)this, this.guiLeft + 214, this.guiTop + 160, 180, 20, this.role.marketName));
        this.addLabel(new GuiNpcLabel(1, "gui.ignoreDamage", this.guiLeft + 260, this.guiTop + 29));
        this.addButton(new GuiNpcButtonYesNo(1, this.guiLeft + 340, this.guiTop + 24, this.role.ignoreDamage));
        this.addLabel(new GuiNpcLabel(2, "gui.ignoreNBT", this.guiLeft + 260, this.guiTop + 51));
        this.addButton(new GuiNpcButtonYesNo(2, this.guiLeft + 340, this.guiTop + 46, this.role.ignoreNBT));
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.guiTop += 10;
        super.drawScreen(i, j, f);
        this.guiTop -= 10;
    }
    
    public void actionPerformed(final GuiButton guibutton) {
        if (guibutton.id == 1) {
            this.role.ignoreDamage = ((GuiNpcButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 2) {
            this.role.ignoreNBT = ((GuiNpcButtonYesNo)guibutton).getBoolean();
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        super.drawGuiContainerBackgroundLayer(f, i, j);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (int slot = 0; slot < 18; ++slot) {
            final int x = this.guiLeft + slot % 3 * 94 + 7;
            final int y = this.guiTop + slot / 3 * 22 + 4;
            this.mc.renderEngine.bindTexture(this.slot);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x - 1, y, 0, 0, 18, 18);
            this.drawTexturedModalRect(x + 17, y, 0, 0, 18, 18);
            this.fontRendererObj.drawString("=", x + 36, y + 5, CustomNpcResourceListener.DefaultTextColor);
            this.mc.renderEngine.bindTexture(this.slot);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x + 42, y, 0, 0, 18, 18);
        }
    }
    
    @Override
    public void save() {
        Client.sendData(EnumPacketServer.TraderMarketSave, this.role.marketName, false);
        Client.sendData(EnumPacketServer.RoleSave, this.role.writeToNBT(new NBTTagCompound()));
    }
    
    @Override
    public void unFocused(final GuiNpcTextField guiNpcTextField) {
        final String name = guiNpcTextField.getText();
        if (!name.equalsIgnoreCase(this.role.marketName)) {
            this.role.marketName = name;
            Client.sendData(EnumPacketServer.TraderMarketSave, this.role.marketName, true);
        }
    }
}
