package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.*;

public class SubGuiInterface extends GuiNPCInterface
{
    public GuiScreen parent;
    
    @Override
    public void save() {
    }
    
    @Override
    public void close() {
        if (this.parent instanceof ISubGuiListener) {
            ((ISubGuiListener)this.parent).subGuiClosed(this);
        }
        if (this.parent instanceof GuiNPCInterface) {
            ((GuiNPCInterface)this.parent).closeSubGui(this);
        }
        else if (this.parent instanceof GuiContainerNPCInterface) {
            ((GuiContainerNPCInterface)this.parent).closeSubGui(this);
        }
        else {
            super.close();
        }
    }
    
    public GuiScreen getParent() {
        if (this.parent instanceof SubGuiInterface) {
            return ((SubGuiInterface)this.parent).getParent();
        }
        return this.parent;
    }
    
    @Override
    public void drawScreen(final int i, final int j, final float f) {
        super.drawScreen(i, j, f);
    }
}
