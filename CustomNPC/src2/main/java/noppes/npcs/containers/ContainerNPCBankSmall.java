package noppes.npcs.containers;

import net.minecraft.entity.player.*;

public class ContainerNPCBankSmall extends ContainerNPCBankInterface
{
    public ContainerNPCBankSmall(final EntityPlayer player, final int slot, final int bankid) {
        super(player, slot, bankid);
    }
    
    @Override
    public boolean isAvailable() {
        return true;
    }
    
    @Override
    public int getRowNumber() {
        return 3;
    }
}
