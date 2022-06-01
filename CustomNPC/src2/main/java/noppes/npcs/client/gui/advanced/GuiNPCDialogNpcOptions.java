package noppes.npcs.client.gui.advanced;

import java.util.*;
import noppes.npcs.controllers.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;
import net.minecraft.entity.player.*;
import noppes.npcs.client.gui.*;
import net.minecraft.nbt.*;

public class GuiNPCDialogNpcOptions extends GuiNPCInterface2 implements GuiSelectionListener, IGuiData
{
    private GuiScreen parent;
    private HashMap<Integer, DialogOption> data;
    private int selectedSlot;
    
    public GuiNPCDialogNpcOptions(final EntityNPCInterface npc, final GuiScreen parent) {
        super(npc);
        this.data = new HashMap<Integer, DialogOption>();
        this.parent = parent;
        this.drawDefaultBackground = true;
        Client.sendData(EnumPacketServer.DialogNpcGet, new Object[0]);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        for (int i = 0; i < 12; ++i) {
            final int offset = (i >= 6) ? 200 : 0;
            this.addButton(new GuiNpcButton(i + 20, this.guiLeft + 20 + offset, this.guiTop + 13 + i % 6 * 22, 20, 20, "X"));
            this.addLabel(new GuiNpcLabel(i, "" + i, this.guiLeft + 6 + offset, this.guiTop + 18 + i % 6 * 22));
            String title = "dialog.selectoption";
            if (this.data.containsKey(i)) {
                title = this.data.get(i).title;
            }
            this.addButton(new GuiNpcButton(i, this.guiLeft + 44 + offset, this.guiTop + 13 + i % 6 * 22, 140, 20, title));
        }
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        super.drawScreen(i, j, f);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        final int id = guibutton.id;
        if (id == 1) {
            NoppesUtil.openGUI((EntityPlayer)this.player, this.parent);
        }
        if (id >= 0 && id < 20) {
            this.close();
            this.selectedSlot = id;
            int dialogID = -1;
            if (this.data.containsKey(id)) {
                dialogID = this.data.get(id).dialogId;
            }
            NoppesUtil.openGUI((EntityPlayer)this.player, new GuiNPCDialogSelection(this.npc, this, dialogID));
        }
        if (id >= 20 && id < 40) {
            final int slot = id - 20;
            this.data.remove(slot);
            Client.sendData(EnumPacketServer.DialogNpcRemove, slot);
            this.initGui();
        }
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void selected(final int id, final String name) {
        Client.sendData(EnumPacketServer.DialogNpcSet, this.selectedSlot, id);
    }
    
    @Override
    public void setGuiData(final NBTTagCompound compound) {
        final int pos = compound.getInteger("Position");
        final DialogOption dialog = new DialogOption();
        dialog.readNBT(compound);
        this.data.put(pos, dialog);
        this.initGui();
    }
}
