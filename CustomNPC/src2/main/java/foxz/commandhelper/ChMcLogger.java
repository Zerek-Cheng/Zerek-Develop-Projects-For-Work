package foxz.commandhelper;

import net.minecraft.util.*;
import net.minecraft.command.*;

public class ChMcLogger extends AbstractCommandHelper
{
    public ChMcLogger(final Object sender) {
        super(sender);
    }
    
    public void sendmessage(final String msg) {
        final ICommandSender sender = this.pcParam;
        sender.addChatMessage((IChatComponent)new ChatComponentText(msg));
    }
    
    @Override
    public void help(final String cmd, final String desc, final String usa) {
        if (usa.isEmpty()) {
            this.sendmessage(String.format("%s = %s", cmd, desc));
        }
        else {
            this.sendmessage(String.format("%s %s = %s", cmd, usa, desc));
        }
    }
    
    @Override
    public void cmdError(final String cmd) {
        this.sendmessage(String.format("Unknow command '%s'", cmd));
    }
    
    @Override
    public void error(final String err) {
        this.sendmessage(String.format("Error: %s", err));
    }
}
