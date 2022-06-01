package foxz.commandhelper.permissions;

import foxz.commandhelper.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;

public class OpOnly extends AbstractPermission
{
    @Override
    public String errorMsg() {
        return "Op Only";
    }
    
    @Override
    public boolean delegate(final AbstractCommandHelper parent, final String[] args) {
        return !(parent.pcParam instanceof EntityPlayer) || MinecraftServer.getServer().getConfigurationManager().canSendCommands(((EntityPlayer)parent.pcParam).getGameProfile());
    }
}
