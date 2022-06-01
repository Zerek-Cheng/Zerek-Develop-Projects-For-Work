package noppes.npcs.client.renderer.blocks;

import noppes.npcs.client.model.blocks.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class BlockCampfireRenderer extends BlockRendererInterface
{
    private final ModelCampfire model;
    
    public BlockCampfireRenderer() {
        this.model = new ModelCampfire();
        ((BlockRotated)CustomItems.campfire).renderId = RenderingRegistry.getNextAvailableRenderId();
        ((BlockRotated)CustomItems.campfire_unlit).renderId = ((BlockRotated)CustomItems.campfire).renderId;
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final TileColorable tile = (TileColorable)var1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.5f, (float)var6 + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(45 * tile.rotation), 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCampfireRenderer.PlanksOak);
        this.model.renderLog(0.0625f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCampfireRenderer.Stone);
        this.model.renderRock(0.0625f);
        GL11.glPopMatrix();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 1.2f, 0.0f);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCampfireRenderer.PlanksOak);
        this.model.renderLog(0.0625f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCampfireRenderer.Stone);
        this.model.renderRock(0.0625f);
        GL11.glPopMatrix();
    }
    
    public int getRenderId() {
        return CustomItems.campfire.getRenderType();
    }
}
