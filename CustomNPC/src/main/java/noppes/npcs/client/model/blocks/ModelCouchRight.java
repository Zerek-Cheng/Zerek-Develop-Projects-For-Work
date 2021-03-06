package noppes.npcs.client.model.blocks;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class ModelCouchRight extends ModelBase
{
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Back;
    ModelRenderer Bottom;
    ModelRenderer Side;
    
    public ModelCouchRight() {
        (this.Leg1 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.Leg1.setRotationPoint(6.0f, 23.0f, 6.0f);
        (this.Leg2 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
        this.Leg2.setRotationPoint(-8.0f, 23.0f, -6.0f);
        (this.Leg3 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 2, 1, 2);
        this.Leg3.setRotationPoint(6.0f, 23.0f, -6.0f);
        (this.Leg4 = new ModelRenderer((ModelBase)this, 0, 0)).addBox(0.0f, 0.0f, 0.0f, 1, 1, 2);
        this.Leg4.setRotationPoint(-8.0f, 23.0f, 6.0f);
        this.Back = new ModelRenderer((ModelBase)this, 1, 15);
        this.Back.mirror = true;
        this.Back.addBox(0.0f, 0.0f, 0.0f, 14, 15, 1);
        this.Back.setRotationPoint(-8.0f, 6.0f, 7.0f);
        this.Bottom = new ModelRenderer((ModelBase)this, 3, 1);
        this.Bottom.mirror = true;
        this.Bottom.addBox(0.0f, 0.0f, 0.0f, 14, 2, 14);
        this.Bottom.setRotationPoint(-8.0f, 21.0f, -6.0f);
        this.Side = new ModelRenderer((ModelBase)this, 1, 28);
        this.Side.mirror = true;
        this.Side.addBox(0.0f, 0.0f, 0.0f, 2, 11, 14);
        this.Side.setRotationPoint(6.0f, 12.0f, -6.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Leg3.render(f5);
        this.Leg4.render(f5);
        this.Back.render(f5);
        this.Bottom.render(f5);
        this.Side.render(f5);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
