package noppes.npcs.client.renderer.blocks;

import noppes.npcs.client.model.blocks.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class BlockBeamRenderer extends BlockRendererInterface
{
    private final ModelBeam model;
    
    public BlockBeamRenderer() {
        this.model = new ModelBeam();
        ((BlockRotated)CustomItems.beam).renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final TileColorable tile = (TileColorable)var1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.5f, (float)var6 + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(90 * tile.rotation), 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.setWoodTexture(var1.getBlockMetadata());
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    private boolean shouldDraw(final World world, final int x, final int y, final int z, final int rotation) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        return tile == null || !(tile instanceof TileShelf) || ((TileShelf)tile).rotation != rotation;
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.9f, 0.0f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        this.setWoodTexture(metadata);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public int getRenderId() {
        return CustomItems.beam.getRenderType();
    }
}
