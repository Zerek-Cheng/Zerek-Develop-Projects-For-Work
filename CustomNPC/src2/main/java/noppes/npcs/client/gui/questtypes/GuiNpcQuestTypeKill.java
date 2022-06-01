package noppes.npcs.client.gui.questtypes;

import noppes.npcs.quests.*;
import noppes.npcs.entity.*;
import noppes.npcs.controllers.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.lang.reflect.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiNpcQuestTypeKill extends SubGuiInterface implements ITextfieldListener, ICustomScrollListener
{
    private GuiScreen parent;
    private GuiCustomScroll scroll;
    private QuestKill quest;
    private GuiNpcTextField lastSelected;
    
    public GuiNpcQuestTypeKill(final EntityNPCInterface npc, final Quest q, final GuiScreen parent) {
        this.npc = npc;
        this.parent = parent;
        this.title = "Quest Kill Setup";
        this.quest = (QuestKill)q.questInterface;
        this.setBackground("menubg.png");
        this.xSize = 356;
        this.ySize = 216;
        this.closeOnEsc = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        int i = 0;
        this.addLabel(new GuiNpcLabel(0, "You can fill in npc or player names too", this.guiLeft + 4, this.guiTop + 50));
        for (final String name : this.quest.targets.keySet()) {
            this.addTextField(new GuiNpcTextField(i, this, this.fontRendererObj, this.guiLeft + 4, this.guiTop + 70 + i * 22, 180, 20, name));
            this.addTextField(new GuiNpcTextField(i + 3, this, this.fontRendererObj, this.guiLeft + 186, this.guiTop + 70 + i * 22, 24, 20, this.quest.targets.get(name) + ""));
            this.getTextField(i + 3).numbersOnly = true;
            this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            ++i;
        }
        while (i < 3) {
            this.addTextField(new GuiNpcTextField(i, this, this.fontRendererObj, this.guiLeft + 4, this.guiTop + 70 + i * 22, 180, 20, ""));
            this.addTextField(new GuiNpcTextField(i + 3, this, this.fontRendererObj, this.guiLeft + 186, this.guiTop + 70 + i * 22, 24, 20, "1"));
            this.getTextField(i + 3).numbersOnly = true;
            this.getTextField(i + 3).setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            ++i;
        }
        final Map<?, ?> data = (Map<?, ?>)EntityList.stringToClassMapping;
        final ArrayList<String> list = new ArrayList<String>();
        for (final Object name2 : data.keySet()) {
            final Class<?> c = (Class<?>)data.get(name2);
            try {
                if (!EntityLivingBase.class.isAssignableFrom(c) || EntityNPCInterface.class.isAssignableFrom(c) || c.getConstructor(World.class) == null || Modifier.isAbstract(c.getModifiers())) {
                    continue;
                }
                list.add(name2.toString());
            }
            catch (SecurityException e) {
                e.printStackTrace();
            }
            catch (NoSuchMethodException ex) {}
        }
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
        }
        this.scroll.setList(list);
        this.scroll.setSize(130, 198);
        this.scroll.guiLeft = this.guiLeft + 220;
        this.scroll.guiTop = this.guiTop + 14;
        this.addScroll(this.scroll);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 4, this.guiTop + 140, 98, 20, "gui.back"));
        this.scroll.visible = GuiNpcTextField.isActive();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            this.close();
        }
    }
    
    @Override
    public void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        this.scroll.visible = GuiNpcTextField.isActive();
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void unFocused(final GuiNpcTextField guiNpcTextField) {
        if (guiNpcTextField.id < 3) {
            this.lastSelected = guiNpcTextField;
        }
        this.saveTargets();
    }
    
    private void saveTargets() {
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < 3; ++i) {
            final String name = this.getTextField(i).getText();
            if (!name.isEmpty()) {
                map.put(name, this.getTextField(i + 3).getInteger());
            }
        }
        this.quest.targets = map;
    }
    
    @Override
    public void customScrollClicked(final int i, final int j, final int k, final GuiCustomScroll guiCustomScroll) {
        if (this.lastSelected == null) {
            return;
        }
        this.lastSelected.setText(guiCustomScroll.getSelected());
        this.saveTargets();
    }
}
