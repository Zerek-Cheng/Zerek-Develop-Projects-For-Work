package noppes.npcs.client.renderer;

import noppes.npcs.client.model.util.*;
import noppes.npcs.client.model.*;
import noppes.npcs.entity.*;
import net.minecraft.client.renderer.entity.*;
import noppes.npcs.controllers.*;
import net.minecraft.client.model.*;
import java.lang.reflect.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class RenderCustomNpc extends RenderNPCHumanMale
{
    private RendererLivingEntity renderEntity;
    private EntityLivingBase entity;
    private ModelRenderPassHelper renderpass;
    
    public RenderCustomNpc() {
        super(new ModelMPM(0.0f), new ModelMPM(1.0f), new ModelMPM(0.5f));
        this.renderpass = new ModelRenderPassHelper();
    }
    
    @Override
    public void renderPlayer(final EntityNPCInterface npcInterface, final double d, final double d1, final double d2, final float f, final float f1) {
        final EntityCustomNpc npc = (EntityCustomNpc)npcInterface;
        this.entity = npc.modelData.getEntity(npc);
        ModelBase model = null;
        this.renderEntity = null;
        if (this.entity != null) {
            this.renderEntity = (RendererLivingEntity)RenderManager.instance.getEntityRenderObject((Entity)this.entity);
            model = NPCRendererHelper.getMainModel(this.renderEntity);
            if (PixelmonHelper.isPixelmon((Entity)this.entity)) {
                try {
                    final Class c = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.Entity2HasModel");
                    final Method m = c.getMethod("getModel", (Class[])new Class[0]);
                    model = (ModelBase)m.invoke(this.entity, new Object[0]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (EntityList.getEntityString((Entity)this.entity).equals("doggystyle.Dog")) {
                try {
                    Method i = this.entity.getClass().getMethod("getBreed", (Class<?>[])new Class[0]);
                    final Object breed = i.invoke(this.entity, new Object[0]);
                    i = breed.getClass().getMethod("getModel", (Class<?>[])new Class[0]);
                    model = (ModelBase)i.invoke(breed, new Object[0]);
                    model.getClass().getMethod("setPosition", Integer.TYPE).invoke(model, 0);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.renderPassModel = this.renderpass;
            this.renderpass.renderer = this.renderEntity;
            this.renderpass.entity = this.entity;
        }
        ((ModelMPM)this.modelArmor).entityModel = model;
        ((ModelMPM)this.modelArmor).entity = this.entity;
        ((ModelMPM)this.modelArmorChestplate).entityModel = model;
        ((ModelMPM)this.modelArmorChestplate).entity = this.entity;
        ((ModelMPM)this.mainModel).entityModel = model;
        ((ModelMPM)this.mainModel).entity = this.entity;
        super.renderPlayer(npc, d, d1, d2, f, f1);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase entityliving, final float f) {
        if (this.renderEntity != null) {
            NPCRendererHelper.renderEquippedItems(this.entity, f, this.renderEntity);
        }
        else {
            super.renderEquippedItems(entityliving, f);
        }
    }
    
    @Override
    protected int shouldRenderPass(final EntityLivingBase par1EntityLivingBase, final int par2, final float par3) {
        if (this.renderEntity != null) {
            return NPCRendererHelper.shouldRenderPass(this.entity, par2, par3, this.renderEntity);
        }
        return this.func_130006_a((EntityLiving)par1EntityLivingBase, par2, par3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityliving, final float f) {
        if (this.renderEntity != null) {
            final EntityNPCInterface npc = (EntityNPCInterface)entityliving;
            final int size = npc.display.modelSize;
            if (this.entity instanceof EntityNPCInterface) {
                ((EntityNPCInterface)this.entity).display.modelSize = 5;
            }
            NPCRendererHelper.preRenderCallback(this.entity, f, this.renderEntity);
            npc.display.modelSize = size;
            GL11.glScalef(0.2f * npc.display.modelSize, 0.2f * npc.display.modelSize, 0.2f * npc.display.modelSize);
        }
        else {
            super.preRenderCallback(entityliving, f);
        }
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase par1EntityLivingBase, final float par2) {
        if (this.renderEntity != null) {
            return NPCRendererHelper.handleRotationFloat(this.entity, par2, this.renderEntity);
        }
        return super.handleRotationFloat(par1EntityLivingBase, par2);
    }
}
