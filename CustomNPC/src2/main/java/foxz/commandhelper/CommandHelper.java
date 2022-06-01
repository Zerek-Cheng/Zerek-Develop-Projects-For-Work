package foxz.commandhelper;

import net.minecraft.command.*;
import java.util.*;

public abstract class CommandHelper
{
    public Helper commandHelper;
    
    public CommandHelper() {
        this.commandHelper = new Helper();
    }
    
    public List addTabCompletion(final ICommandSender par1, final String[] args) {
        return null;
    }
    
    public class Helper
    {
        public String name;
        public String usage;
        public String desc;
        public boolean hasEmptyCall;
    }
}
