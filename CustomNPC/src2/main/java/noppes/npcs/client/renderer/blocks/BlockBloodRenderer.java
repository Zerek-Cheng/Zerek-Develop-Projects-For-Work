package noppes.npcs.client.renderer.blocks;

import cpw.mods.fml.client.registry.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import noppes.npcs.*;

public class BlockBloodRenderer implements ISimpleBlockRenderingHandler
{
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        renderer.renderStandardBlock(block, 0, 0, 0);
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        if (!this.shouldDraw(world, x, y, z, block)) {
            return false;
        }
        renderer.setRenderFromInside(true);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderFromInside(false);
        return true;
    }
    
    private boolean shouldDraw(final IBlockAccess world, final int x, final int y, final int z, final Block block) {
        return block.shouldSideBeRendered(world, x + 1, y, z, 0) || block.shouldSideBeRendered(world, x - 1, y, z, 0) || block.shouldSideBeRendered(world, x, y + 1, z, 0) || block.shouldSideBeRendered(world, x, y - 1, z, 0) || block.shouldSideBeRendered(world, x, y, z + 1, 0) || block.shouldSideBeRendered(world, x, y, z - 1, 0);
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return false;
    }
    
    public int getRenderId() {
        return CustomItems.blood.getRenderType();
    }
}
