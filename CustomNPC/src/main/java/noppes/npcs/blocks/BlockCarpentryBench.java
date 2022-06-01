package noppes.npcs.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.*;
import noppes.npcs.constants.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockCarpentryBench extends BlockContainer
{
    public int renderId;
    
    public BlockCarpentryBench() {
        super(Material.wood);
        this.renderId = -1;
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (!par1World.isRemote) {
            player.openGui((Object)CustomNpcs.instance, EnumGuiType.PlayerAnvil.ordinal(), par1World, i, j, k);
        }
        return true;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int getRenderType() {
        return this.renderId;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
    }
    
    public int damageDropped(final int par1) {
        return par1 / 4;
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLiving, final ItemStack item) {
        final int var6 = MathHelper.floor_double(par5EntityLiving.rotationYaw / 90.0f + 0.5) & 0x3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 + item.getMetadata() * 4, 2);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileBlockAnvil();
    }
}
