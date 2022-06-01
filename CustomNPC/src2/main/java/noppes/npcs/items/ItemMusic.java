package noppes.npcs.items;

import noppes.npcs.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ItemMusic extends ItemNpcInterface
{
    private boolean shouldRotate;
    
    public ItemMusic() {
        this.shouldRotate = false;
        this.setCreativeTab((CreativeTabs)CustomItems.tabMisc);
    }
    
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer player) {
        if (par2World.isRemote) {
            return par1ItemStack;
        }
        final int note = par2World.rand.nextInt(24);
        final float var7 = (float)Math.pow(2.0, (note - 12) / 12.0);
        final String var8 = "harp";
        par2World.playSoundEffect(player.posX, player.posY, player.posZ, "note." + var8, 3.0f, var7);
        par2World.spawnParticle("note", player.posY, player.posY + 1.2, player.posY, note / 24.0, 0.0, 0.0);
        return par1ItemStack;
    }
    
    public Item setRotated() {
        this.shouldRotate = true;
        return this;
    }
    
    public boolean shouldRotateAroundWhenRendering() {
        return this.shouldRotate;
    }
}
