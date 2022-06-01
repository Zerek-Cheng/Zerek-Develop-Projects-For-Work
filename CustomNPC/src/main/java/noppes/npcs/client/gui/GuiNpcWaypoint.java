package noppes.npcs.client.gui;

import noppes.npcs.blocks.tiles.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.nbt.*;
import noppes.npcs.constants.*;
import noppes.npcs.client.*;

public class GuiNpcWaypoint extends GuiNPCInterface
{
    private TileWaypoint tile;
    
    public GuiNpcWaypoint(final int x, final int y, final int z) {
        this.tile = (TileWaypoint)this.player.worldObj.getTileEntity(x, y, z);
        this.xSize = 265;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(0, "gui.name", this.guiLeft + 1, this.guiTop + 76, 16777215));
        this.addTextField(new GuiNpcTextField(0, this, this.fontRendererObj, this.guiLeft + 60, this.guiTop + 71, 200, 20, this.tile.name));
        this.addLabel(new GuiNpcLabel(1, "gui.range", this.guiLeft + 1, this.guiTop + 97, 16777215));
        this.addTextField(new GuiNpcTextField(1, this, this.fontRendererObj, this.guiLeft + 60, this.guiTop + 92, 200, 20, this.tile.range + ""));
        this.getTextField(1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(2, 60, 10);
        this.addButton(new GuiNpcButton(0, this.guiLeft + 40, this.guiTop + 190, 120, 20, "Done"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        final int id = guibutton.id;
        if (id == 0) {
            this.close();
        }
    }
    
    @Override
    public void save() {
        this.tile.name = this.getTextField(0).getText();
        this.tile.range = this.getTextField(1).getInteger();
        final NBTTagCompound compound = new NBTTagCompound();
        this.tile.writeToNBT(compound);
        Client.sendData(EnumPacketServer.SaveTileEntity, compound);
    }
}
