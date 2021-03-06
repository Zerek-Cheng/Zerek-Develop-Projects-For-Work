package noppes.npcs.client.model.part;

import noppes.npcs.client.model.util.*;
import noppes.npcs.client.model.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import noppes.npcs.*;

public class ModelHair extends ModelPartInterface
{
    private Model2DRenderer model;
    
    public ModelHair(final ModelMPM base) {
        super(base);
        (this.model = new Model2DRenderer((ModelBase)base, 56.0f, 20.0f, 8, 12, 64.0f, 32.0f)).setRotationPoint(-4.0f, 12.0f, 3.0f);
        this.model.setScale(0.75f);
        this.addChild((ModelRenderer)this.model);
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity entity) {
        final ModelRenderer parent = this.base.bipedHead;
        if (parent.rotateAngleX < 0.0f) {
            this.rotateAngleX = -parent.rotateAngleX * 1.2f;
            if (parent.rotateAngleX > -1.0f) {
                this.rotationPointY = -parent.rotateAngleX * 1.5f;
                this.rotationPointZ = -parent.rotateAngleX * 1.5f;
            }
        }
        else {
            this.rotateAngleX = 0.0f;
            this.rotationPointY = 0.0f;
            this.rotationPointZ = 0.0f;
        }
    }
    
    @Override
    public void initData(final ModelData data) {
        final ModelPartData config = data.getPartData("hair");
        if (config == null) {
            this.isHidden = true;
            return;
        }
        this.color = config.color;
        this.isHidden = false;
        if (!config.playerTexture) {
            this.location = config.getResource();
        }
        else {
            this.location = null;
        }
    }
}
