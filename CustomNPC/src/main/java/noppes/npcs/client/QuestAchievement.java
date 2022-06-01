package noppes.npcs.client;

import net.minecraft.stats.*;
import noppes.npcs.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class QuestAchievement extends Achievement
{
    private String description;
    private String message;
    
    public QuestAchievement(final String message, final String description) {
        super("", message, 0, 0, (CustomItems.letter == null) ? Items.paper : CustomItems.letter, (Achievement)null);
        this.description = description;
        this.message = message;
    }
    
    public IChatComponent getStatName() {
        return (IChatComponent)new ChatComponentText(this.message);
    }
    
    public String getDescription() {
        return this.description;
    }
}
