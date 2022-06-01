package tconstruct.client.tabs;

import noppes.npcs.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import noppes.npcs.client.gui.player.*;
import net.minecraft.client.gui.*;

public class InventoryTabFactions extends AbstractTab
{
    public InventoryTabFactions() {
        super(0, 0, 0, new ItemStack(CustomItems.wallBanner, 1, 1));
        if (CustomItems.wallBanner == null) {
            this.renderStack = new ItemStack(Blocks.tnt);
        }
    }
    
    @Override
    public void onTabClicked() {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final Minecraft mc = Minecraft.getMinecraft();
                mc.displayGuiScreen((GuiScreen)new GuiFaction());
            }
        };
        t.start();
    }
    
    @Override
    public boolean shouldAddToList() {
        return true;
    }
}
