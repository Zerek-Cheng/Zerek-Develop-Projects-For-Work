package noppes.npcs.client.renderer.blocks;

import net.minecraft.client.renderer.tileentity.*;
import noppes.npcs.client.model.blocks.*;
import net.minecraft.util.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

public class BlockCandleRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    private final ModelCandle model;
    private final ModelCandleWall modelWall;
    private final ModelCandleCeiling modelCeiling;
    private static final ResourceLocation resource1;
    
    public BlockCandleRenderer() {
        this.model = new ModelCandle();
        this.modelWall = new ModelCandleWall();
        this.modelCeiling = new ModelCandleCeiling();
        ((BlockCandle)CustomItems.candle).renderId = RenderingRegistry.getNextAvailableRenderId();
        ((BlockCandle)CustomItems.candle_unlit).renderId = ((BlockCandle)CustomItems.candle).renderId;
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final TileCandle tile = (TileCandle)var1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.5f, (float)var6 + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(45 * tile.rotation), 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCandleRenderer.resource1);
        if (tile.color == 0) {
            this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else if (tile.color == 1) {
            this.modelCeiling.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else {
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            this.modelWall.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 2.6f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BlockCandleRenderer.resource1);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        return false;
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }
    
    public int getRenderId() {
        return CustomItems.candle.getRenderType();
    }
    
    static {
        resource1 = new ResourceLocation("customnpcs", "textures/models/Candle.png");
    }
}
