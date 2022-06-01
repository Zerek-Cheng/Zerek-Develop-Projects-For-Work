package noppes.npcs.client.gui.global;

import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.gui.*;
import noppes.npcs.client.gui.util.*;
import java.util.*;

public class GuiNPCManageLinkedNpc extends GuiNPCInterface2 implements IScrollData, ISubGuiListener
{
    private GuiCustomScroll scroll;
    private List<String> data;
    public static GuiScreen Instance;
    
    public GuiNPCManageLinkedNpc(final EntityNPCInterface npc) {
        super(npc);
        this.data = new ArrayList<String>();
        GuiNPCManageLinkedNpc.Instance = this;
        Client.sendData(EnumPacketServer.LinkedGetAll, new Object[0]);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addButton(new GuiNpcButton(1, this.guiLeft + 358, this.guiTop + 38, 58, 20, "gui.add"));
        this.addButton(new GuiNpcButton(2, this.guiLeft + 358, this.guiTop + 61, 58, 20, "gui.remove"));
        if (this.scroll == null) {
            (this.scroll = new GuiCustomScroll(this, 0)).setSize(143, 208);
        }
        this.scroll.guiLeft = this.guiLeft + 214;
        this.scroll.guiTop = this.guiTop + 4;
        this.scroll.setList(this.data);
        this.addScroll(this.scroll);
    }
    
    @Override
    public void buttonEvent(final GuiButton button) {
        if (button.id == 1) {
            this.save();
            this.setSubGui(new SubGuiEditText("New"));
        }
        if (button.id == 2 && this.scroll.hasSelected()) {
            Client.sendData(EnumPacketServer.LinkedRemove, this.scroll.getSelected());
        }
    }
    
    @Override
    public void subGuiClosed(final SubGuiInterface subgui) {
        if (!((SubGuiEditText)subgui).cancelled) {
            Client.sendData(EnumPacketServer.LinkedAdd, ((SubGuiEditText)subgui).text);
        }
    }
    
    @Override
    public void setData(final Vector<String> list, final HashMap<String, Integer> data) {
        this.data = new ArrayList<String>(list);
        this.initGui();
    }
    
    @Override
    public void setSelected(final String selected) {
    }
    
    @Override
    public void save() {
    }
}
