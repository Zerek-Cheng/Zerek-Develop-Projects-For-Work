package noppes.npcs.client.gui.questtypes;

import noppes.npcs.controllers.*;
import net.minecraft.util.*;
import noppes.npcs.entity.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;
import noppes.npcs.client.gui.global.*;
import noppes.npcs.quests.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import noppes.npcs.client.gui.util.*;

public class GuiNpcQuestTypeItem extends GuiContainerNPCInterface implements ITextfieldListener
{
    private Quest quest;
    private static final ResourceLocation field_110422_t;
    
    public GuiNpcQuestTypeItem(final EntityNPCInterface npc, final ContainerNpcQuestTypeItem container) {
        super(npc, container);
        this.quest = GuiNPCManageQuest.quest;
        this.title = "";
        this.ySize = 202;
        this.closeOnEsc = false;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(0, "quest.takeitems", this.guiLeft + 4, this.guiTop + 8));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 90, this.guiTop + 3, 60, 20, new String[] { "gui.yes", "gui.no" }, (int)(((QuestItem)this.quest.questInterface).leaveItems ? 1 : 0)));
        this.addLabel(new GuiNpcLabel(1, "gui.ignoreDamage", this.guiLeft + 4, this.guiTop + 29));
        this.addButton(new GuiNpcButtonYesNo(1, this.guiLeft + 90, this.guiTop + 24, 50, 20, ((QuestItem)this.quest.questInterface).ignoreDamage));
        this.addLabel(new GuiNpcLabel(2, "gui.ignoreNBT", this.guiLeft + 62, this.guiTop + 51));
        this.addButton(new GuiNpcButtonYesNo(2, this.guiLeft + 120, this.guiTop + 46, 50, 20, ((QuestItem)this.quest.questInterface).ignoreNBT));
        this.addButton(new GuiNpcButton(5, this.guiLeft, this.guiTop + this.ySize, 98, 20, "gui.back"));
    }
    
    public void actionPerformed(final GuiButton guibutton) {
        if (guibutton.id == 0) {
            ((QuestItem)this.quest.questInterface).leaveItems = (((GuiNpcButton)guibutton).getValue() == 1);
        }
        if (guibutton.id == 1) {
            ((QuestItem)this.quest.questInterface).ignoreDamage = ((GuiNpcButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 2) {
            ((QuestItem)this.quest.questInterface).ignoreNBT = ((GuiNpcButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 5) {
            NoppesUtil.openGUI((EntityPlayer)this.player, GuiNPCManageQuest.Instance);
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        this.drawWorldBackground(0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(GuiNpcQuestTypeItem.field_110422_t);
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
    
    static {
        field_110422_t = new ResourceLocation("customnpcs", "textures/gui/followersetup.png");
    }
}
