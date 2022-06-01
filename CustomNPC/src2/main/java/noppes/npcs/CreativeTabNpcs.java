package noppes.npcs;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class CreativeTabNpcs extends CreativeTabs
{
    public Item item;
    public int meta;
    
    public CreativeTabNpcs(final String label) {
        super(label);
        this.item = Items.bowl;
        this.meta = 0;
    }
    
    public Item getTabIconItem() {
        return this.item;
    }
    
    public int getIconItemDamage() {
        return this.meta;
    }
}
