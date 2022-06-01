package foxz.commandhelper.permissions;

import foxz.commandhelper.*;
import net.minecraft.entity.player.*;

public class PlayerOnly extends AbstractPermission
{
    @Override
    public String errorMsg() {
        return "Player Only";
    }
    
    @Override
    public boolean delegate(final AbstractCommandHelper parent, final String[] args) {
        return parent.pcParam instanceof EntityPlayer;
    }
}
