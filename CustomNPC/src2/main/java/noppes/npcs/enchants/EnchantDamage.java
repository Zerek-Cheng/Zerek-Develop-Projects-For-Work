package noppes.npcs.enchants;

import noppes.npcs.items.*;

public class EnchantDamage extends EnchantInterface
{
    public EnchantDamage() {
        super(10, new Class[] { ItemStaff.class, ItemGun.class });
        this.func_77322_b("damage");
    }
    
    public int func_77321_a(final int par1) {
        return 1 + (par1 - 1) * 10;
    }
    
    public int func_77317_b(final int par1) {
        return this.func_77321_a(par1) + 15;
    }
    
    public int func_77325_b() {
        return 5;
    }
}
