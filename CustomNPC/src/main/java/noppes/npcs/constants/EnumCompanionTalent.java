package noppes.npcs.constants;

import net.minecraft.item.*;
import noppes.npcs.*;
import net.minecraft.init.*;

public enum EnumCompanionTalent
{
    INVENTORY(CustomItems.satchel), 
    ARMOR((Item)Items.iron_chestplate), 
    SWORD(Items.diamond_sword), 
    RANGED((Item)Items.bow), 
    ACROBATS((Item)Items.leather_boots), 
    INTEL(CustomItems.letter);
    
    public ItemStack item;
    
    private EnumCompanionTalent(final Item item) {
        this.item = new ItemStack(item);
    }
}
