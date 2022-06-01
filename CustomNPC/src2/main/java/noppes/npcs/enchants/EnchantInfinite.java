package noppes.npcs.enchants;

import noppes.npcs.items.*;

public class EnchantInfinite extends EnchantInterface
{
    public EnchantInfinite() {
        super(3, new Class[] { ItemStaff.class, ItemGun.class });
        this.func_77322_b("infinite");
    }
    
    public int func_77321_a(final int par1) {
        return 20;
    }
    
    public int func_77317_b(final int par1) {
        return 50;
    }
    
    public int func_77325_b() {
        return 1;
    }
}
