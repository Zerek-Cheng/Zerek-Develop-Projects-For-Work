package noppes.npcs.client.gui.roles;

import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.constants.*;
import net.minecraft.nbt.*;
import noppes.npcs.client.*;

public class GuiNpcHealer extends GuiNPCInterface2
{
    private JobHealer job;
    
    public GuiNpcHealer(final EntityNPCInterface npc) {
        super(npc);
        this.job = (JobHealer)npc.jobInterface;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(1, "Healing Speed:", this.guiLeft + 60, this.guiTop + 110));
        this.addTextField(new GuiNpcTextField(1, this, this.fontRendererObj, this.guiLeft + 130, this.guiTop + 105, 40, 20, this.job.speed + ""));
        this.getTextField(1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(1, 10, 8);
        this.addLabel(new GuiNpcLabel(2, "Range:", this.guiLeft + 60, this.guiTop + 133));
        this.addTextField(new GuiNpcTextField(2, this, this.fontRendererObj, this.guiLeft + 130, this.guiTop + 128, 40, 20, this.job.range + ""));
        this.getTextField(2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(2, 20, 5);
    }
    
    @Override
    public void elementClicked() {
    }
    
    @Override
    public void save() {
        this.job.speed = this.getTextField(1).getInteger();
        this.job.range = this.getTextField(2).getInteger();
        Client.sendData(EnumPacketServer.JobSave, this.job.writeToNBT(new NBTTagCompound()));
    }
}
