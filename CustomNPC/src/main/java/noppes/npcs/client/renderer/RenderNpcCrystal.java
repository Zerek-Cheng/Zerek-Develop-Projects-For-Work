package noppes.npcs.client.renderer;

import noppes.npcs.client.model.*;
import net.minecraft.client.model.*;

public class RenderNpcCrystal extends RenderNPCInterface
{
    ModelNpcCrystal mainmodel;
    
    public RenderNpcCrystal(final ModelNpcCrystal model) {
        super(model, 0.0f);
        this.mainmodel = model;
    }
}
