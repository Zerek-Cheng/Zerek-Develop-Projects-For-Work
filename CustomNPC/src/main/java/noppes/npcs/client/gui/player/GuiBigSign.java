package noppes.npcs.client.gui.player;

import noppes.npcs.client.gui.*;
import noppes.npcs.blocks.tiles.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;

public class GuiBigSign extends SubGuiNpcTextArea
{
    public TileBigSign tile;
    
    public GuiBigSign(final int x, final int y, final int z) {
        super("");
        this.tile = (TileBigSign)this.player.field_70170_p.func_147438_o(x, y, z);
        this.text = this.tile.getText();
    }
    
    @Override
    public void close() {
        super.close();
        NoppesUtilPlayer.sendData(EnumPlayerPacket.SignSave, this.tile.field_145851_c, this.tile.field_145848_d, this.tile.field_145849_e, this.text);
    }
}
