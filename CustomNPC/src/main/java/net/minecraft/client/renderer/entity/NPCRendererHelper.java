package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class NPCRendererHelper
{
    public static ModelBase getMainModel(final RendererLivingEntity render) {
        return render.mainModel;
    }
    
    public static String getTexture(final RendererLivingEntity render, final Entity entity) {
        final ResourceLocation location = render.getEntityTexture(entity);
        return location.toString();
    }
    
    public static int shouldRenderPass(final EntityLivingBase entity, final int par2, final float par3, final RendererLivingEntity renderEntity) {
        return renderEntity.shouldRenderPass(entity, par2, par3);
    }
    
    public static void preRenderCallback(final EntityLivingBase entity, final float f, final RendererLivingEntity renderEntity) {
        renderEntity.preRenderCallback(entity, f);
    }
    
    public static ModelBase getPassModel(final RendererLivingEntity render) {
        return render.renderPassModel;
    }
    
    public static float handleRotationFloat(final EntityLivingBase entity, final float par2, final RendererLivingEntity renderEntity) {
        return renderEntity.handleRotationFloat(entity, par2);
    }
    
    public static void renderEquippedItems(final EntityLivingBase entity, final float f, final RendererLivingEntity renderEntity) {
        renderEntity.renderEquippedItems(entity, f);
    }
}
