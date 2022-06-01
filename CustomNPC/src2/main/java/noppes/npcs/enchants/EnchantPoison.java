package noppes.npcs.enchants;

import noppes.npcs.items.*;

public class EnchantPoison extends EnchantInterface
{
    public EnchantPoison() {
        super(6, new Class[] { ItemStaff.class, ItemGun.class });
        this.func_77322_b("poison");
    }
    
    public int func_77321_a(final int par1) {
        return 12 + (par1 - 1) * 20;
    }
    
    public int func_77317_b(final int par1) {
        return this.func_77321_a(par1) + 25;
    }
    
    public int func_77325_b() {
        return 2;
    }
}
