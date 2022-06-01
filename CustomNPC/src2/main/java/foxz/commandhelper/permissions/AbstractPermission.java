package foxz.commandhelper.permissions;

import foxz.commandhelper.*;

public abstract class AbstractPermission
{
    public abstract String errorMsg();
    
    public abstract boolean delegate(final AbstractCommandHelper p0, final String[] p1);
}
