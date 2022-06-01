package noppes.npcs.client.gui.global;

import noppes.npcs.controllers.*;
import net.minecraft.util.*;
import noppes.npcs.entity.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;

public class GuiNpcQuestReward extends GuiContainerNPCInterface implements ITextfieldListener
{
    private Quest quest;
    private ResourceLocation resource;
    
    public GuiNpcQuestReward(final EntityNPCInterface npc, final ContainerNpcQuestReward container) {
        super(npc, container);
        this.quest = GuiNPCManageQuest.quest;
        this.resource = this.getResource("questreward.png");
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(0, "quest.randomitem", this.guiLeft + 4, this.guiTop + 4));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 14, 60, 20, new String[] { "gui.no", "gui.yes" }, (int)(this.quest.randomReward ? 1 : 0)));
        this.addButton(new GuiNpcButton(5, this.guiLeft, this.guiTop + this.ySize, 98, 20, "gui.back"));
        this.addLabel(new GuiNpcLabel(1, "quest.exp", this.guiLeft + 4, this.guiTop + 45));
        this.addTextField(new GuiNpcTextField(0, (GuiScreen)this, this.fontRendererObj, this.guiLeft + 4, this.guiTop + 55, 60, 20, this.quest.rewardExp + ""));
        this.getTextField(0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, 99999, 0);
    }
    
    public void actionPerformed(final GuiButton guibutton) {
        final int id = guibutton.id;
        if (id == 5) {
            NoppesUtil.openGUI((EntityPlayer)this.player, GuiNPCManageQuest.Instance);
        }
        if (id == 0) {
            this.quest.randomReward = (((GuiNpcButton)guibutton).getValue() == 1);
        }
    }
    
    public void onGuiClosed() {
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(this.resource);
        final int l = (this.width - this.xSize) / 2;
        final int i2 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(l, i2, 0, 0, this.xSize, this.ySize);
        super.drawGuiContainerBackgroundLayer(f, i, j);
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void unFocused(final GuiNpcTextField textfield) {
        this.quest.rewardExp = textfield.getInteger();
    }
}
