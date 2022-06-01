package noppes.npcs.blocks;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;

public class BlockMailbox extends BlockContainer
{
    public int renderId;
    
    public BlockMailbox() {
        super(Material.iron);
        this.renderId = -1;
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    
    public boolean onBlockActivated(final World par1World, final int i, final int j, final int k, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
        if (!par1World.isRemote) {
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI, EnumGuiType.PlayerMailbox, i, j, k);
        }
        return true;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        final int damage = this.getDamageValue(world, x, y, z);
        ret.add(new ItemStack((Block)this, 1, damage));
        return ret;
    }
    
    public int damageDropped(final int par1) {
        return par1 >> 2;
    }
    
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        l %= 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l | par6ItemStack.getMetadata() << 2, 2);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return this.renderId;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return Blocks.soul_sand.getBlockTextureFromSide(side);
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileMailbox();
    }
}
