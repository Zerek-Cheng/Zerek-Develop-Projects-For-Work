package noppes.npcs.client.gui.roles;

import noppes.npcs.client.gui.util.*;
import noppes.npcs.roles.*;
import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import noppes.npcs.controllers.*;
import net.minecraft.nbt.*;

public class GuiNpcBankSetup extends GuiNPCInterface2 implements IScrollData, ICustomScrollListener
{
    private GuiCustomScroll scroll;
    private HashMap<String, Integer> data;
    private RoleBank role;
    
    public GuiNpcBankSetup(final EntityNPCInterface npc) {
        super(npc);
        this.data = new HashMap<String, Integer>();
        Client.sendData(EnumPacketServer.BanksGet, new Object[0]);
        this.role = (RoleBank)npc.roleInterface;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScroll(this, 0);
        }
        this.scroll.setSize(200, 152);
        this.scroll.guiLeft = this.guiLeft + 85;
        this.scroll.guiTop = this.guiTop + 20;
        this.addScroll(this.scroll);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
    }
    
    @Override
    public void setData(final Vector<String> list, final HashMap<String, Integer> data) {
        String name = null;
        final Bank bank = this.role.getBank();
        if (bank != null) {
            name = bank.name;
        }
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.setSelected(name);
        }
    }
    
    @Override
    public void mouseClicked(final int i, final int j, final int k) {
        super.mouseClicked(i, j, k);
        if (k == 0 && this.scroll != null) {
            this.scroll.mouseClicked(i, j, k);
        }
    }
    
    @Override
    public void setSelected(final String selected) {
        this.scroll.setSelected(selected);
    }
    
    @Override
    public void customScrollClicked(final int i, final int j, final int k, final GuiCustomScroll guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.role.bankId = this.data.get(this.scroll.getSelected());
            this.save();
        }
    }
    
    @Override
    public void save() {
        Client.sendData(EnumPacketServer.RoleSave, this.role.writeToNBT(new NBTTagCompound()));
    }
}
