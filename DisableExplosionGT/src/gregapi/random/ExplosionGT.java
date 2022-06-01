package gregapi.random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ExplosionGT
        extends Explosion {
    private World mWorld;

    public static ExplosionGT explode(World aWorld, Entity aEntity, double aX, double aY, double aZ, float aPower, boolean aFlaming, boolean aSmoking) {
        ExplosionGT tExplosion = new ExplosionGT(aWorld, aEntity, aX, aY, aZ, aPower);

        return tExplosion;
    }

    public ExplosionGT(World aWorld, Entity aEntity, double aX, double aY, double aZ, float aPower) {
        super(aWorld, aEntity, aX, aY, aZ, aPower);
        this.mWorld = aWorld;
    }

    private Map field_77288_k = new HashMap();

    public void func_77278_a() {/*
        float tSize = this.field_77280_f;
        HashSet<ChunkPosition> tPositions = new HashSet();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    if ((i == 0) || (i == 15) || (j == 0) || (j == 15) || (k == 0) || (k == 15))
                    {
                        double tIncX = i / 7.5F - 1.0F;double tIncY = j / 7.5F - 1.0F;double tIncZ = k / 7.5F - 1.0F;
                        double tDist = Math.sqrt(tIncX * tIncX + tIncY * tIncY + tIncZ * tIncZ);
                        tIncX /= tDist;tIncY /= tDist;tIncZ /= tDist;
                        float tPow = tSize * (0.7F + this.mWorld.field_73012_v.nextFloat() * 0.6F);
                        double tX = this.field_77284_b;double tY = this.field_77285_c;double tZ = this.field_77282_d;
                        for (float tMul = 0.3F; tPow > 0.0F; tPow -= tMul * 0.75F)
                        {
                            int tFloorX = UT.Code.roundDown(tX);int tFloorY = UT.Code.roundDown(tY);int tFloorZ = UT.Code.roundDown(tZ);
                            Block tBlock = this.mWorld.func_147439_a(tFloorX, tFloorY, tFloorZ);
                            if (tBlock.func_149688_o() != Material.field_151579_a)
                            {
                                float f3 = this.field_77283_e != null ? this.field_77283_e.func_145772_a(this, this.mWorld, tFloorX, tFloorY, tFloorZ, tBlock) : tBlock.getExplosionResistance(this.field_77283_e, this.mWorld, tFloorX, tFloorY, tFloorZ, this.field_77284_b, this.field_77285_c, this.field_77282_d);
                                tPow -= (f3 + 0.3F) * tMul;
                            }
                            if ((tPow > 0.0F) && ((this.field_77283_e == null) || (this.field_77283_e.func_145774_a(this, this.mWorld, tFloorX, tFloorY, tFloorZ, tBlock, tPow)))) {
                                tPositions.add(new ChunkPosition(tFloorX, tFloorY, tFloorZ));
                            }
                            tX += tIncX * tMul;tY += tIncY * tMul;tZ += tIncZ * tMul;
                        }
                    }
                }
            }
        }
        this.field_77281_g.addAll(tPositions);
        tSize *= 2.0F;

        List tEntities = this.mWorld.func_72839_b(this.field_77283_e, AxisAlignedBB.func_72330_a(UT.Code.roundDown(this.field_77284_b - tSize - 1.0D), UT.Code.roundDown(this.field_77285_c - tSize - 1.0D), UT.Code.roundDown(this.field_77282_d - tSize - 1.0D), UT.Code.roundDown(this.field_77284_b + tSize + 1.0D), UT.Code.roundDown(this.field_77285_c + tSize + 1.0D), UT.Code.roundDown(this.field_77282_d + tSize + 1.0D)));
        ForgeEventFactory.onExplosionDetonate(this.mWorld, this, tEntities, tSize);
        Vec3 tVec3 = Vec3.func_72443_a(this.field_77284_b, this.field_77285_c, this.field_77282_d);
        for (int i1 = 0; i1 < tEntities.size(); i1++)
        {
            Entity tEntity = (Entity)tEntities.get(i1);
            double tEntityDist = tEntity.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / tSize;
            if ((tEntityDist <= 1.0D) && (!(tEntity instanceof EntityWither)) && (!(tEntity instanceof EntityDragon)) && (!(tEntity instanceof EntityDragonPart)) && (!tEntity.getClass().getName().toLowerCase().contains("boss")))
            {
                double tKnockX = tEntity.field_70165_t - this.field_77284_b;double tKnockY = tEntity.field_70163_u + tEntity.func_70047_e() - this.field_77285_c;double tKnockZ = tEntity.field_70161_v - this.field_77282_d;
                double tDist = MathHelper.func_76133_a(tKnockX * tKnockX + tKnockY * tKnockY + tKnockZ * tKnockZ);
                if (tDist > 0.0D)
                {
                    tKnockX /= tDist;
                    tKnockY /= tDist;
                    tKnockZ /= tDist;
                    double tKnockback = (1.0D - tEntityDist) * this.mWorld.func_72842_a(tVec3, tEntity.field_70121_D);
                    tEntity.func_70097_a(DamageSource.func_94539_a(this), (int)((tKnockback * tKnockback + tKnockback) * 4.0D * tSize + 1.0D));
                    double tBlastProtection = EnchantmentProtection.func_92092_a(tEntity, tKnockback);
                    tEntity.field_70159_w += tKnockX * tBlastProtection;
                    tEntity.field_70181_x += tKnockY * tBlastProtection;
                    tEntity.field_70179_y += tKnockZ * tBlastProtection;
                    if ((tEntity instanceof EntityPlayer)) {
                        this.field_77288_k.put(tEntity, Vec3.func_72443_a(tKnockX * tKnockback, tKnockY * tKnockback, tKnockZ * tKnockback));
                    }
                }
            }
        }*/
    }

    public void func_77279_a(boolean aEffects) {/*
        this.mWorld.func_72908_a(this.field_77284_b, this.field_77285_c, this.field_77282_d, "random.explode", 4.0F, (1.0F + (this.mWorld.field_73012_v.nextFloat() - this.mWorld.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
        this.mWorld.func_72869_a((this.field_77280_f >= 2.0F) && (this.field_82755_b) ? "hugeexplosion" : "largeexplode", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
        if (this.field_82755_b)
        {
            Iterator tIterator = this.field_77281_g.iterator();
            while (tIterator.hasNext())
            {
                ChunkPosition tPos = (ChunkPosition)tIterator.next();
                Block tBlock = this.mWorld.func_147439_a(tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c);
                if (aEffects)
                {
                    double d0 = tPos.field_151329_a + this.mWorld.field_73012_v.nextFloat();
                    double d1 = tPos.field_151327_b + this.mWorld.field_73012_v.nextFloat();
                    double d2 = tPos.field_151328_c + this.mWorld.field_73012_v.nextFloat();
                    double d3 = d0 - this.field_77284_b;
                    double d4 = d1 - this.field_77285_c;
                    double d5 = d2 - this.field_77282_d;
                    double d6 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / this.field_77280_f + 0.1D);
                    d7 *= (this.mWorld.field_73012_v.nextFloat() * this.mWorld.field_73012_v.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.mWorld.func_72869_a("explode", (d0 + this.field_77284_b) / 2.0D, (d1 + this.field_77285_c) / 2.0D, (d2 + this.field_77282_d) / 2.0D, d3, d4, d5);
                    this.mWorld.func_72869_a("smoke", d0, d1, d2, d3, d4, d5);
                }
                if (tBlock.func_149688_o() != Material.field_151579_a)
                {
                    if (tBlock.func_149659_a(this)) {
                        tBlock.func_149690_a(this.mWorld, tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c, this.mWorld.func_72805_g(tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c), 1.0F / this.field_77280_f, 0);
                    }
                    tBlock.onBlockExploded(this.mWorld, tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c, this);
                }
            }
        }
        if (this.field_77286_a)
        {
            Iterator tIterator = this.field_77281_g.iterator();
            while (tIterator.hasNext())
            {
                ChunkPosition tPos = (ChunkPosition)tIterator.next();
                Block tBlock = this.mWorld.func_147439_a(tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c);Block tAbove = this.mWorld.func_147439_a(tPos.field_151329_a, tPos.field_151327_b - 1, tPos.field_151328_c);
                if ((tBlock.func_149688_o() == Material.field_151579_a) && (tAbove.func_149730_j()) && (CS.RNGSUS.nextInt(3) == 0)) {
                    this.mWorld.func_147449_b(tPos.field_151329_a, tPos.field_151327_b, tPos.field_151328_c, Blocks.field_150480_ab);
                }
            }
        }*/
    }

    public Map func_77277_b() {
        return this.field_77288_k;
    }

    public EntityLivingBase func_94613_c() {
        return (this.field_77283_e instanceof EntityLivingBase) ? (EntityLivingBase) this.field_77283_e : (this.field_77283_e instanceof EntityTNTPrimed) ? ((EntityTNTPrimed) this.field_77283_e).func_94083_c() : this.field_77283_e == null ? null : null;
    }
}
