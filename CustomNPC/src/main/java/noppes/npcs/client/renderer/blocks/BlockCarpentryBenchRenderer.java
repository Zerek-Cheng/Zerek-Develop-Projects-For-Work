package noppes.npcs.client.renderer.blocks;

import net.minecraft.client.renderer.tileentity.*;
import noppes.npcs.client.model.blocks.*;
import net.minecraft.util.*;
import noppes.npcs.blocks.*;
import noppes.npcs.*;
import cpw.mods.fml.client.registry.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;

public class BlockCarpentryBenchRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    private final ModelCarpentryBench model;
    private final ModelAnvil anvil;
    private static final ResourceLocation resource3;
    private static final ResourceLocation field_110631_g;
    
    public BlockCarpentryBenchRenderer() {
        this.model = new ModelCarpentryBench();
        this.anvil = new ModelAnvil();
        ((BlockCarpentryBench)CustomItems.carpentyBench).renderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)this);
    }
    
    public void renderTileEntityAt(final TileEntity var1, final double var2, final double var4, final double var6, final float var8) {
        final int meta = var1.getBlockMetadata();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2 + 0.5f, (float)var4 + 1.4f, (float)var6 + 0.5f);
        GL11.glScalef(0.95f, 0.95f, 0.95f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef((float)(90 * (meta % 4)), 0.0f, 1.0f, 0.0f);
        if (meta >= 4) {
            this.bindTexture(BlockCarpentryBenchRenderer.resource3);
            this.anvil.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else {
            this.bindTexture(BlockCarpentryBenchRenderer.field_110631_g);
            this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.85f, 0.0f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        if (metadata == 0) {
            this.bindTexture(BlockCarpentryBenchRenderer.field_110631_g);
            this.model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        else {
            this.bindTexture(BlockCarpentryBenchRenderer.resource3);
            this.anvil.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        }
        GL11.glPopMatrix();
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        return false;
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }
    
    public int getRenderId() {
        return CustomItems.carpentyBench.getRenderType();
    }
    
    static {
        resource3 = new ResourceLocation("customnpcs", "textures/models/Steel.png");
        field_110631_g = new ResourceLocation("customnpcs", "textures/models/CarpentryBench.png");
    }
}
