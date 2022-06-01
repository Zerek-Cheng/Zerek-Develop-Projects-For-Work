package noppes.npcs.client.gui.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.constants.*;
import net.minecraft.nbt.*;
import noppes.npcs.client.*;

public class GuiNpcGuard extends GuiNPCInterface2
{
    private JobGuard role;
    private GuiCustomScroll scroll1;
    private GuiCustomScroll scroll2;
    
    public GuiNpcGuard(final EntityNPCInterface npc) {
        super(npc);
        this.role = (JobGuard)npc.jobInterface;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(0, "guard.animals", this.guiLeft + 10, this.guiTop + 9));
        this.addButton(new GuiNpcButton(0, this.guiLeft + 85, this.guiTop + 4, 50, 20, new String[] { "gui.no", "gui.yes" }, (int)(this.role.attacksAnimals ? 1 : 0)));
        this.addLabel(new GuiNpcLabel(1, "guard.mobs", this.guiLeft + 140, this.guiTop + 9));
        this.addButton(new GuiNpcButton(1, this.guiLeft + 222, this.guiTop + 4, 50, 20, new String[] { "gui.no", "gui.yes" }, (int)(this.role.attackHostileMobs ? 1 : 0)));
        this.addLabel(new GuiNpcLabel(2, "guard.creepers", this.guiLeft + 275, this.guiTop + 9));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 360, this.guiTop + 4, 50, 20, new String[] { "gui.no", "gui.yes" }, (int)(this.role.attackCreepers ? 1 : 0)));
        this.getButton(2).enabled = this.role.attackHostileMobs;
        this.addLabel(new GuiNpcLabel(3, "guard.specifictargets", this.guiLeft + 10, this.guiTop + 31));
        this.addButton(new GuiNpcButton(3, this.guiLeft + 85, this.guiTop + 26, 50, 20, new String[] { "gui.no", "gui.yes" }, (int)(this.role.specific ? 1 : 0)));
        if (this.role.specific) {
            if (this.scroll1 == null) {
                (this.scroll1 = new GuiCustomScroll(this, 0)).setSize(175, 154);
            }
            this.scroll1.guiLeft = this.guiLeft + 4;
            this.scroll1.guiTop = this.guiTop + 58;
            this.addScroll(this.scroll1);
            this.addLabel(new GuiNpcLabel(11, "guard.availableTargets", this.guiLeft + 4, this.guiTop + 48));
            if (this.scroll2 == null) {
                (this.scroll2 = new GuiCustomScroll(this, 1)).setSize(175, 154);
            }
            this.scroll2.guiLeft = this.guiLeft + 235;
            this.scroll2.guiTop = this.guiTop + 58;
            this.addScroll(this.scroll2);
            this.addLabel(new GuiNpcLabel(12, "guard.currentTargets", this.guiLeft + 235, this.guiTop + 48));
            final List<String> all = new ArrayList<String>();
            for (final Object entity : EntityList.stringToClassMapping.keySet()) {
                final String name = "entity." + entity + ".name";
                final Class cl = EntityList.stringToClassMapping.get(entity);
                if (!this.role.targets.contains(name)) {
                    if (EntityNPCInterface.class.isAssignableFrom(cl)) {
                        continue;
                    }
                    if (!EntityLivingBase.class.isAssignableFrom(cl)) {
                        continue;
                    }
                    all.add(name);
                }
            }
            this.scroll1.setList(all);
            this.scroll2.setList(this.role.targets);
            this.addButton(new GuiNpcButton(11, this.guiLeft + 180, this.guiTop + 80, 55, 20, ">"));
            this.addButton(new GuiNpcButton(12, this.guiLeft + 180, this.guiTop + 102, 55, 20, "<"));
            this.addButton(new GuiNpcButton(13, this.guiLeft + 180, this.guiTop + 130, 55, 20, ">>"));
            this.addButton(new GuiNpcButton(14, this.guiLeft + 180, this.guiTop + 152, 55, 20, "<<"));
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        final GuiNpcButton button = (GuiNpcButton)guibutton;
        if (button.id == 0) {
            this.role.attacksAnimals = (button.getValue() == 1);
        }
        if (button.id == 1) {
            this.role.attackHostileMobs = (button.getValue() == 1);
            this.initGui();
        }
        if (button.id == 2) {
            this.role.attackCreepers = (button.getValue() == 1);
        }
        if (button.id == 3) {
            this.role.specific = (button.getValue() == 1);
            this.initGui();
        }
        if (button.id == 11 && this.scroll1.hasSelected()) {
            this.role.targets.add(this.scroll1.getSelected());
            this.scroll1.selected = -1;
            this.scroll1.selected = -1;
            this.initGui();
        }
        if (button.id == 12 && this.scroll2.hasSelected()) {
            this.role.targets.remove(this.scroll2.getSelected());
            this.scroll2.selected = -1;
            this.initGui();
        }
        if (button.id == 13) {
            this.role.targets.clear();
            final List<String> all = new ArrayList<String>();
            for (final Object entity : EntityList.stringToClassMapping.keySet()) {
                final String name = "entity." + entity + ".name";
                final Class cl = EntityList.stringToClassMapping.get(entity);
                if (EntityLivingBase.class.isAssignableFrom(cl)) {
                    all.add(name);
                }
            }
            this.role.targets = all;
            this.scroll1.selected = -1;
            this.scroll1.selected = -1;
            this.initGui();
        }
        if (button.id == 14) {
            this.role.targets.clear();
            this.scroll1.selected = -1;
            this.scroll1.selected = -1;
            this.initGui();
        }
    }
    
    @Override
    public void save() {
        Client.sendData(EnumPacketServer.JobSave, this.role.writeToNBT(new NBTTagCompound()));
    }
}
