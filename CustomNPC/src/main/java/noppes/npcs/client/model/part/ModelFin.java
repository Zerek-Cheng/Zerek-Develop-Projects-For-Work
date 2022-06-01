package noppes.npcs.client.model.part;

import noppes.npcs.client.model.util.*;
import noppes.npcs.client.model.*;
import net.minecraft.client.model.*;
import noppes.npcs.*;

public class ModelFin extends ModelPartInterface
{
    private Model2DRenderer model;
    
    public ModelFin(final ModelMPM base) {
        super(base);
        (this.model = new Model2DRenderer((ModelBase)base, 56.0f, 20.0f, 8, 12, 64.0f, 32.0f)).setRotationPoint(-0.5f, 12.0f, 10.0f);
        this.model.setScale(0.74f);
        this.model.rotateAngleY = 1.5707964f;
        this.addChild((ModelRenderer)this.model);
    }
    
    @Override
    public void initData(final ModelData data) {
        final ModelPartData config = data.getPartData("fin");
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
