package noppes.npcs.containers;

import net.minecraft.entity.player.*;

public class ContainerNPCBankLarge extends ContainerNPCBankInterface
{
    public ContainerNPCBankLarge(final EntityPlayer player, final int slot, final int bankid) {
        super(player, slot, bankid);
    }
    
    @Override
    public boolean isUpgraded() {
        return true;
    }
    
    @Override
    public boolean isAvailable() {
        return true;
    }
    
    @Override
    public int getRowNumber() {
        return 6;
    }
}
