package noppes.npcs;

import net.minecraft.util.*;
import java.util.*;

public class TextBlock
{
    public List<IChatComponent> lines;
    
    public TextBlock() {
        this.lines = new ArrayList<IChatComponent>();
    }
}
