package tconstruct.client.tabs;

import noppes.npcs.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import noppes.npcs.client.gui.player.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;

public class InventoryTabQuests extends AbstractTab
{
    public InventoryTabQuests() {
        super(0, 0, 0, new ItemStack(CustomItems.letter));
        if (CustomItems.letter == null) {
            this.renderStack = new ItemStack(Items.book);
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
                mc.displayGuiScreen((GuiScreen)new GuiQuestLog((EntityPlayer)mc.thePlayer));
            }
        };
        t.start();
    }
    
    @Override
    public boolean shouldAddToList() {
        return true;
    }
}
