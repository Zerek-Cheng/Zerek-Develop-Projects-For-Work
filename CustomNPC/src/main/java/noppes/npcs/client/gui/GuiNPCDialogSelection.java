package noppes.npcs.client.gui;

import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class GuiNPCDialogSelection extends GuiNPCInterface implements IScrollData
{
    private GuiNPCStringSlot slot;
    private GuiScreen parent;
    private HashMap<String, Integer> data;
    private int dialog;
    private boolean selectCategory;
    public GuiSelectionListener listener;
    
    public GuiNPCDialogSelection(final EntityNPCInterface npc, final GuiScreen parent, final int dialog) {
        super(npc);
        this.data = new HashMap<String, Integer>();
        this.selectCategory = true;
        this.drawDefaultBackground = false;
        this.title = "Select Dialog Category";
        this.parent = parent;
        this.dialog = dialog;
        if (dialog >= 0) {
            Client.sendData(EnumPacketServer.DialogsGetFromDialog, dialog);
            this.selectCategory = false;
            this.title = "Select Dialog";
        }
        else {
            Client.sendData(EnumPacketServer.DialogCategoriesGet, dialog);
        }
        if (parent instanceof GuiSelectionListener) {
            this.listener = (GuiSelectionListener)parent;
        }
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final Vector<String> list = new Vector<String>();
        (this.slot = new GuiNPCStringSlot(list, this, false, 18)).registerScrollButtons(4, 5);
        this.addButton(new GuiNpcButton(2, this.width / 2 - 100, this.height - 41, 98, 20, "gui.back"));
        this.addButton(new GuiNpcButton(4, this.width / 2 + 2, this.height - 41, 98, 20, "mco.template.button.select"));
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.slot.drawScreen(i, j, f);
        super.drawScreen(i, j, f);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        final int id = guibutton.id;
        if (id == 2) {
            if (this.selectCategory) {
                this.close();
                NoppesUtil.openGUI((EntityPlayer)this.player, this.parent);
            }
            else {
                this.title = "Select Dialog Category";
                this.selectCategory = true;
                Client.sendData(EnumPacketServer.DialogCategoriesGet, this.dialog);
            }
        }
        if (id == 4) {
            this.doubleClicked();
        }
    }
    
    @Override
    public void doubleClicked() {
        if (this.slot.selected == null || this.slot.selected.isEmpty()) {
            return;
        }
        if (this.selectCategory) {
            this.selectCategory = false;
            this.title = "Select Dialog";
            Client.sendData(EnumPacketServer.DialogsGet, this.data.get(this.slot.selected));
        }
        else {
            this.dialog = this.data.get(this.slot.selected);
            this.close();
            NoppesUtil.openGUI((EntityPlayer)this.player, this.parent);
        }
    }
    
    @Override
    public void save() {
        if (this.dialog >= 0 && this.listener != null) {
            this.listener.selected(this.dialog, this.slot.selected);
        }
    }
    
    @Override
    public void setData(final Vector<String> list, final HashMap<String, Integer> data) {
        this.data = data;
        this.slot.setList(list);
        if (this.dialog >= 0) {
            for (final String name : data.keySet()) {
                if (data.get(name) == this.dialog) {
                    this.slot.selected = name;
                }
            }
        }
    }
    
    @Override
    public void setSelected(final String selected) {
    }
}
