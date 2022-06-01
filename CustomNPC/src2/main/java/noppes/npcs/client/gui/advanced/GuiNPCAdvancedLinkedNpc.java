package noppes.npcs.client.gui.advanced;

import noppes.npcs.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiNPCAdvancedLinkedNpc extends GuiNPCInterface2 implements IScrollData, ICustomScrollListener
{
    private GuiCustomScroll scroll;
    private List<String> data;
    public static GuiScreen Instance;
    
    public GuiNPCAdvancedLinkedNpc(final EntityNPCInterface npc) {
        super(npc);
        this.data = new ArrayList<String>();
        GuiNPCAdvancedLinkedNpc.Instance = this;
        Client.sendData(EnumPacketServer.LinkedGetAll, new Object[0]);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addButton(new GuiNpcButton(1, this.guiLeft + 358, this.guiTop + 38, 58, 20, "gui.clear"));
        if (this.scroll == null) {
            (this.scroll = new GuiCustomScroll(this, 0)).setSize(143, 208);
        }
        this.scroll.guiLeft = this.guiLeft + 137;
        this.scroll.guiTop = this.guiTop + 4;
        this.scroll.setSelected(this.npc.linkedName);
        this.scroll.setList(this.data);
        this.addScroll(this.scroll);
    }
    
    @Override
    public void buttonEvent(final GuiButton button) {
        if (button.id == 1) {
            Client.sendData(EnumPacketServer.LinkedSet, "");
        }
    }
    
    @Override
    public void setData(final Vector<String> list, final HashMap<String, Integer> data) {
        this.data = new ArrayList<String>(list);
        this.initGui();
    }
    
    @Override
    public void setSelected(final String selected) {
        this.scroll.setSelected(selected);
    }
    
    @Override
    public void save() {
    }
    
    @Override
    public void customScrollClicked(final int i, final int j, final int k, final GuiCustomScroll guiCustomScroll) {
        Client.sendData(EnumPacketServer.LinkedSet, guiCustomScroll.getSelected());
    }
}
