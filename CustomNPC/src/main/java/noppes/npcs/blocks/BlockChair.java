package noppes.npcs.blocks;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import noppes.npcs.entity.*;
import java.util.*;

public class BlockChair extends BlockRotated
{
    public BlockChair() {
        super(Blocks.planks);
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 1.0f, 0.9f);
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLivingBase par5EntityLivingBase, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, par6ItemStack.getMetadata(), 2);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World worldIn, final int x, final int y, final int z) {
        return AxisAlignedBB.getBoundingBox((double)(x + 0.1f), (double)y, (double)(z + 0.1f), (double)(x + 0.9f), (double)(y + 0.5f), (double)(z + 0.9f));
    }
    
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 5));
    }
    
    public int damageDropped(final int par1) {
        return par1;
    }
    
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return new TileChair();
    }
    
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float subX, final float subY, final float subZ) {
        return MountBlock(world, x, y, z, player);
    }
    
    public static boolean MountBlock(final World world, final int x, final int y, final int z, final EntityPlayer player) {
        if (world.isRemote) {
            return true;
        }
        final List<Entity> list = (List<Entity>)world.getEntitiesWithinAABB((Class)Entity.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)));
        for (final Entity entity : list) {
            if (entity instanceof EntityChairMount || entity instanceof EntityCustomNpc) {
                return false;
            }
        }
        final EntityChairMount mount = new EntityChairMount(world);
        mount.setPosition((double)(x + 0.5f), (double)y, z + 0.5);
        player.mountEntity((Entity)mount);
        world.spawnEntityInWorld((Entity)mount);
        player.mountEntity((Entity)mount);
        return true;
    }
}
