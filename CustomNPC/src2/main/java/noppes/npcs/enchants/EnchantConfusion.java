package noppes.npcs.enchants;

import noppes.npcs.items.*;

public class EnchantConfusion extends EnchantInterface
{
    public EnchantConfusion() {
        super(3, new Class[] { ItemStaff.class, ItemGun.class });
        this.func_77322_b("confusion");
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
