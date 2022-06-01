package foxz.command;

import foxz.commandhelper.*;
import noppes.npcs.controllers.*;
import foxz.commandhelper.annotations.*;
import foxz.commandhelper.permissions.*;

@Command(name = "script", desc = "Script operation")
public class CmdScript extends ChMcLogger
{
    public CmdScript(final Object sender) {
        super(sender);
    }
    
    @SubCommand(desc = "Reload scripts and saved data from disks script folder.", permissions = { OpOnly.class })
    public Boolean reload(final String[] args) {
        if (ScriptController.Instance.loadStoredData()) {
            this.sendmessage("Reload succesful");
        }
        else {
            this.sendmessage("Failed reloading stored data");
        }
        return true;
    }
}
