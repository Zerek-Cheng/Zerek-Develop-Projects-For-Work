package noppes.npcs.client.renderer.blocks;

import noppes.npcs.client.model.blocks.*;
import net.minecraft.util.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import noppes.npcs.blocks.tiles.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class BlockTallLampRenderer extends BlockRendererInterface
{
    private final ModelTallLamp model;
    private final ModelTallLampTop top;
    public static final ResourceLocation resourceTop;
    
    public BlockTallLampRenderer() {
        this.model = new ModelTallLamp();
        this.top = new ModelTallLampTop();
        ((BlockTallLamp)CustomItems.tallLamp).renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final TileTallLamp tile = (TileTallLamp)var1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.5f, (float)var6 + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(90 * tile.rotation), 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        BlockRendererInterface.setMaterialTexture(var1.getBlockMetadata());
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.bindTexture(BlockTallLampRenderer.resourceTop);
        final float[] color = BlockBannerRenderer.colorTable[tile.color];
        GL11.glColor3f(color[0], color[1], color[2]);
        this.top.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.42f, 0.0f);
        GL11.glScalef(0.76f, 0.66f, 0.76f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        BlockRendererInterface.setMaterialTexture(metadata);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.bindTexture(BlockTallLampRenderer.resourceTop);
        final float[] color = BlockBannerRenderer.colorTable[15 - metadata];
        GL11.glColor3f(color[0], color[1], color[2]);
        this.top.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public int getRenderId() {
        return CustomItems.tallLamp.getRenderType();
    }
    
    static {
        resourceTop = new ResourceLocation("customnpcs", "textures/cache/wool_colored_white.png");
    }
}
