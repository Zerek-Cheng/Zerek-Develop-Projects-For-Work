package noppes.npcs.client.renderer.blocks;

import net.minecraft.client.model.*;
import noppes.npcs.client.model.blocks.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class BlockCouchWoodRenderer extends BlockRendererInterface
{
    private final ModelBase model;
    private final ModelBase modelLeft;
    private final ModelBase modelRight;
    private final ModelBase modelCorner;
    
    public BlockCouchWoodRenderer() {
        this.model = new ModelCouchWoodMiddle();
        this.modelLeft = new ModelCouchWoodLeft();
        this.modelRight = new ModelCouchWoodRight();
        this.modelCorner = new ModelCouchWoodSingle();
        ((BlockCouchWood)CustomItems.couchWood).renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final TileCouchWood tile = (TileCouchWood)var1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.5f, (float)var6 + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(90 * tile.rotation), 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.setWoodTexture(var1.getBlockMetadata());
        if (tile.hasLeft && tile.hasRight) {
            this.model.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else if (tile.hasLeft) {
            this.modelLeft.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else if (tile.hasRight) {
            this.modelRight.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else {
            this.modelCorner.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.9f, 0.1f);
        GL11.glScalef(0.9f, 0.9f, 0.9f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        this.setWoodTexture(metadata);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.modelCorner.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public int getRenderId() {
        return CustomItems.couchWood.getRenderType();
    }
}
