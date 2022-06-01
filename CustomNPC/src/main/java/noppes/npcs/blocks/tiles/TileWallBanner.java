package noppes.npcs.blocks.tiles;

import net.minecraft.util.*;

public class TileWallBanner extends TileBanner
{
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord - 1), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1));
    }
}
