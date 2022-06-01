package noppes.npcs.scripted;

public class ScriptEvent
{
    private boolean isCancelled;
    
    public ScriptEvent() {
        this.isCancelled = false;
    }
    
    public void setCancelled(final boolean bo) {
        this.isCancelled = bo;
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
