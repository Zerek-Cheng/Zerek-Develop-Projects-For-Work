package su.nightexpress.divineitems.api;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager.BuffType;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.types.ActionType;
import su.nightexpress.divineitems.types.SpellType;
import su.nightexpress.divineitems.types.TargetType;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.ParticleUtils;
import su.nightexpress.divineitems.utils.Spells;
import su.nightexpress.divineitems.utils.Utils;

public class DivineItemsAPI
{
    private static DivineItems plugin = DivineItems.instance;
    private static ItemAPI ia;
    private static EntityAPI ea;

    public static ItemAPI getItemAPI()
    {
        return ia;
    }

    public static EntityAPI getEntityAPI()
    {
        return ea;
    }

    public static void executeActions(Entity paramEntity, List<String> paramList, final ItemStack paramItemStack)
    {
        final ArrayList localArrayList1 = new ArrayList(paramList);
        for (String str1 : paramList)
        {
            List<Entity> localObject1 = new ArrayList();
            if ((Hook.PLACEHOLDER_API.isEnabled()) && ((paramEntity instanceof Player))) {
            }
            String str2 = str1.split(" ")[0];
            str2 = str2.replace("[", "").replace("]", "");
            ActionType localActionType = null;
            try
            {
                localActionType = ActionType.valueOf(str2);
            }
            catch (IllegalArgumentException localIllegalArgumentException1)
            {
                continue;
            }
            Object localObject2 = paramEntity;
            TargetType localTargetType = TargetType.SELF;
            double d1 = 0.0D;
            double d2 = 100.0D;

            String[] arrayOfString = str1.split(" ");
            ArrayList localArrayList2 = new ArrayList();

            Object localObject3 = "";
            String[] localObject5=arrayOfString;
            int j = arrayOfString.length;
            String localObject4;
            for (int i = 0; i < j; i++)
            {
                localObject4 = localObject5[i];
                if (((String)localObject4).startsWith("@"))
                {
                    if (((String)localObject4).contains("-"))
                    {
                        d1 = Double.parseDouble(localObject4.split("-")[1]);
                        localObject4 = localObject4.split("-")[0];
                    }
                    try
                    {
                        localTargetType = TargetType.valueOf(((String)localObject4).replace("@", "").toUpperCase());
                        localObject3 = localObject4;
                    }
                    catch (IllegalArgumentException localIllegalArgumentException2) {}
                }
                else if (((String)localObject4).startsWith("%"))
                {
                    try
                    {
                        d2 = Double.parseDouble(((String)localObject4).replace("%", ""));
                    }
                    catch (NumberFormatException localNumberFormatException1) {}
                }
                else
                {
                    localArrayList2.add(localObject4);
                }
            }
            if (Utils.getRandDouble(0.0D, 100.0D) <= d2)
            {
                String[] localObject4_1 = (String[])localArrayList2.toArray(new String[localArrayList2.size()]);
                switch (localTargetType)
                {
                    case RADIUS:
                        localObject2 = paramEntity;
                        break;
                    case SELF:
                        if (paramEntity.hasMetadata("DI_TARGET")) {
                            localObject2 = (Entity)((MetadataValue)paramEntity.getMetadata("DI_TARGET").get(0)).value();
                        } else {
                            localObject2 = EntityAPI.getEntityTarget(paramEntity);
                        }
                        break;
                    case TARGET:
                        localObject1 = paramEntity.getNearbyEntities(d1, d1, d1);
                }
                if (localObject2 == null) {
                    return;
                }
                if (((List)localObject1).isEmpty()) {
                    ((List)localObject1).add(localObject2);
                }
                for (Object localEntity1 : (List)localObject1)
                {
                   Entity localEntity= (Entity) localEntity1;
                    localObject2 = localEntity;
                    double d10;
                    double d13;
                    double d16;
                    Object localObject7;
                    Object localObject9;
                    double d6;
                    Object localObject10;
                    Object localObject11;
                    Object localObject6;
                    Object localObject8;
                    Vector localVector1;
                    double d11;
                    double d14;
                    switch (localActionType)
                    {
                        case ACTION_BAR:
                            if ((localObject2 instanceof Player))
                            {
                               Player localObject5_1 = (Player)localObject2;

                                BuffManager.BuffType localBuffType = null;
                                try
                                {
                                    localBuffType = BuffManager.BuffType.valueOf(localObject4_1[1].toUpperCase());
                                }
                                catch (IllegalArgumentException localIllegalArgumentException3)
                                {
                                    continue;
                                }
                                String str3 = localObject4_1[2];

                                d10 = 0.0D;
                                d13 = 0.0D;
                                try
                                {
                                    String str4 = localObject4_1[3];
                                    String str5 = localObject4_1[4];
                                    d10 = Double.parseDouble(str4);
                                    d13 = Double.parseDouble(str5);
                                }
                                catch (NumberFormatException|ArrayIndexOutOfBoundsException localNumberFormatException8)
                                {
                                    continue;
                                }
                                plugin.getMM().getBuffManager().addBuff(localObject5_1, localBuffType, str3, d10, (int)d13, true);
                            }
                            break;
                        case ARROW:
                            localObject5 = localObject4_1[1].toUpperCase();
                            double d5 = 0.0D;
                            d10 = 0.0D;
                            d13 = 0.0D;
                            d16 = 0.0D;
                            int i4 = 0;
                            try
                            {
                                d5 = Double.parseDouble(localObject4_1[2]);
                                d10 = Double.parseDouble(localObject4_1[3]);
                                d13 = Double.parseDouble(localObject4_1[4]);
                                d16 = Double.parseDouble(localObject4_1[5]);
                                i4 = Integer.parseInt(localObject4_1[6]);
                            }
                            catch (NumberFormatException localNumberFormatException10)
                            {
                                continue;
                            }
                            Utils.playEffect((String)localObject5, d5, d10, d13, d16, i4, ((Entity)localObject2).getLocation());
                            break;
                        case WITHER_SKULL:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;

                                SpellType localSpellType = null;
                                try
                                {
                                    localSpellType = SpellType.valueOf(localObject4[1].toUpperCase());
                                }
                                catch (IllegalArgumentException localIllegalArgumentException4)
                                {
                                    continue;
                                }
                                switch (localSpellType)
                                {
                                    case FIRE_STORM:
                                        Spells.skillIceSnake((Player)localObject5);
                                        break;
                                    case ICE_SNAKE:
                                        Spells.skillMeteor((Player)localObject5);
                                        break;
                                    case METEOR:
                                        Spells.skillIceFireStorm((Player)localObject5, 0);
                                        break;
                                    case ICE_STORM:
                                        Spells.skillIceFireStorm((Player)localObject5, 1);
                                }
                            }
                            break;
                        case BLOCK:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject5 = PotionEffectType.getByName(localObject4[1].toUpperCase());
                                if (localObject5 != null)
                                {
                                    int k = 0;
                                    int m = 0;
                                    try
                                    {
                                        k = Integer.parseInt(localObject4[2]);
                                        m = Integer.parseInt(localObject4[3]);
                                    }
                                    catch (NumberFormatException localNumberFormatException4)
                                    {
                                        continue;
                                    }
                                    if (k >= 0)
                                    {
                                        PotionEffect localPotionEffect = new PotionEffect((PotionEffectType)localObject5, m * 20, k - 1);
                                        ((LivingEntity)localObject2).removePotionEffect((PotionEffectType)localObject5);
                                        ((LivingEntity)localObject2).addPotionEffect(localPotionEffect);
                                    }
                                }
                            }
                            break;
                        case BUFF:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = str1.replace((CharSequence)localObject3, "").replace("[", "").replace("]", "").replace(localActionType.name() + " ", "").replace("%p", paramEntity.getName()).replace("%t", ((Entity)localObject2).getName());
                                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), (String)localObject5);
                            }
                            break;
                        case DAMAGE:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = str1.replace((CharSequence)localObject3, "").replace("[", "").replace("]", "").replace(localActionType.name() + " ", "").replace("%p", paramEntity.getName()).replace("%t", ((Entity)localObject2).getName());
                                ((Player)localObject5).setOp(true);
                                plugin.getServer().dispatchCommand((CommandSender)localObject5, (String)localObject7);
                                ((Player)localObject5).setOp(false);
                            }
                            break;
                        case COMMAND:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = str1.replace((CharSequence)localObject3, "").replace("[", "").replace("]", "").replace(localActionType.name() + " ", "").replace("%p", paramEntity.getName()).replace("%t", ((Entity)localObject2).getName());
                                ((Player)localObject5).performCommand((String)localObject7);
                            }
                            break;
                        case DELAY:
                            localObject5 = localObject4[1];
                            localObject7 = plugin.getServer().getWorld((String)localObject5);
                            if (localObject7 != null)
                            {
                                double d8 = 0.0D;
                                double d12 = 0.0D;
                                double d15 = 0.0D;
                                try
                                {
                                    d8 = Double.parseDouble(localObject4[2]);
                                    d12 = Double.parseDouble(localObject4[3]);
                                    d15 = Double.parseDouble(localObject4[4]);
                                }
                                catch (NumberFormatException localNumberFormatException9)
                                {
                                    continue;
                                }
                                Location localLocation = new Location((World)localObject7, d8, d12, d15);
                                ((Entity)localObject2).teleport(localLocation);
                            }
                            break;
                        case EFFECT:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = ChatColor.translateAlternateColorCodes('&', str1.replace((CharSequence)localObject3, "").replace("[", "").replace("]", "").replace(localActionType.name() + " ", "").replace("%p", paramEntity.getName()).replace("%t", ((Entity)localObject2).getName()));
                                ((Player)localObject5).sendMessage((String)localObject7);
                            }
                            break;
                        case FIREBALL:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = ChatColor.translateAlternateColorCodes('&', str1.replace((CharSequence)localObject3, "").replace("[", "").replace("]", "").replace(localActionType.name() + " ", "").replace("%p", paramEntity.getName()).replace("%t", ((Entity)localObject2).getName()));
                                DivineItems.instance.getNMS().sendActionBar((Player)localObject5, (String)localObject7);
                            }
                            break;
                        case FIREWORK:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = ChatColor.translateAlternateColorCodes('&', localObject4[1].replace("_", " "));
                                localObject9 = ChatColor.translateAlternateColorCodes('&', localObject4[2].replace("_", " "));

                                int n = 0;
                                int i1 = 0;
                                int i3 = 0;
                                try
                                {
                                    n = Integer.parseInt(localObject4[3]);
                                    i1 = Integer.parseInt(localObject4[4]);
                                    i3 = Integer.parseInt(localObject4[5]);
                                }
                                catch (NumberFormatException localNumberFormatException7)
                                {
                                    continue;
                                }
                                DivineItems.instance.getNMS().sendTitles((Player)localObject5, (String)localObject7, (String)localObject9, n, i1, i3);
                            }
                            break;
                        case HOOK:
                            if ((localObject2 instanceof Player))
                            {
                                localObject5 = (Player)localObject2;
                                localObject7 = localObject4[1];
                                localObject9 = null;
                                try
                                {
                                    localObject9 = Sound.valueOf((String)localObject7);
                                }
                                catch (NullPointerException localNullPointerException)
                                {
                                    continue;
                                }
                                ((Player)localObject5).playSound(((Player)localObject5).getLocation(), (Sound)localObject9, 0.6F, 0.6F);
                            }
                            break;
                        case IGNITE:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject5 = (LivingEntity)localObject2;
                                d6 = Double.parseDouble(localObject4[1]);

                                localObject10 = (Arrow)((LivingEntity)localObject5).launchProjectile(Arrow.class);
                                localObject11 = ((LivingEntity)localObject5).getEyeLocation().getDirection();
                                ItemUtils.setProjectileData((Projectile)localObject10, (LivingEntity)localObject5, paramItemStack);
                                ((Arrow)localObject10).setShooter((ProjectileSource)localObject5);
                                ((Arrow)localObject10).setVelocity(((Vector)localObject11).multiply(d6));
                            }
                            break;
                        case LIGHTNING:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject5 = (LivingEntity)localObject2;
                                d6 = Double.parseDouble(localObject4[1]);

                                localObject10 = (Fireball)((LivingEntity)localObject5).launchProjectile(Fireball.class);
                                localObject11 = ((LivingEntity)localObject5).getEyeLocation().getDirection();
                                ItemUtils.setProjectileData((Projectile)localObject10, (LivingEntity)localObject5, paramItemStack);
                                ((Fireball)localObject10).setShooter((ProjectileSource)localObject5);
                                ((Fireball)localObject10).setVelocity(((Vector)localObject11).multiply(d6));
                            }
                            break;
                        case MESSAGE:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject5 = (LivingEntity)localObject2;
                                d6 = Double.parseDouble(localObject4[1]);

                                localObject10 = (WitherSkull)((LivingEntity)localObject5).launchProjectile(WitherSkull.class);
                                localObject11 = ((LivingEntity)localObject5).getEyeLocation().getDirection();
                                ItemUtils.setProjectileData((Projectile)localObject10, (LivingEntity)localObject5, paramItemStack);
                                ((WitherSkull)localObject10).setShooter((ProjectileSource)localObject5);
                                ((WitherSkull)localObject10).setVelocity(((Vector)localObject11).multiply(d6));
                            }
                            break;
                        case OP_COMMAND:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject5 = (LivingEntity)localObject2;
                                d6 = Double.parseDouble(localObject4[1]);

                                localObject10 = Material.getMaterial(localObject4[2].toUpperCase());
                                if ((localObject10 != null) && (((Material)localObject10).isBlock()))
                                {
                                    localObject11 = ((Entity)localObject2).getWorld().spawnFallingBlock(((Entity)localObject2).getLocation().add(0.0D, 0.5D, 0.0D), (Material)localObject10, (byte)0);
                                    ((FallingBlock)localObject11).setDropItem(false);
                                    ((FallingBlock)localObject11).setMetadata("DIFall", new FixedMetadataValue(DivineItems.instance, "yes"));
                                    ItemUtils.setEntityData((Entity)localObject11, (LivingEntity)localObject5, paramItemStack);
                                    Vector localVector2 = ((LivingEntity)localObject5).getEyeLocation().getDirection();
                                    ((FallingBlock)localObject11).setVelocity(localVector2.multiply(d6));
                                }
                            }
                            break;
                        case PARTICLE_LINE:
                            double d3 = 0.0D;
                            try
                            {
                                d3 = Double.parseDouble(localObject4[1]);
                            }
                            catch (NumberFormatException localNumberFormatException2)
                            {
                                continue;
                            }
                            ((Entity)localObject2).setFireTicks((int)d3 * 20);
                            break;
                        case PLAYER_COMMAND:
                            if (!localObject2.equals(paramEntity)) {
                                ((Entity)localObject2).getWorld().strikeLightning(((Entity)localObject2).getLocation());
                            }
                            break;
                        case SOUND:
                            if (!localObject2.equals(paramEntity))
                            {
                                localObject6 = ((Entity)localObject2).getLocation();
                                localObject8 = ((Location)localObject6).subtract(paramEntity.getLocation());

                                localVector1 = ((Location)localObject8).getDirection().normalize().multiply(-1.4D);
                                if (localVector1.getY() >= 1.15D) {
                                    localVector1.setY(localVector1.getY() * 0.45D);
                                } else if (localVector1.getY() >= 1.0D) {
                                    localVector1.setY(localVector1.getY() * 0.6D);
                                } else if (localVector1.getY() >= 0.8D) {
                                    localVector1.setY(localVector1.getY() * 0.85D);
                                }
                                if (localVector1.getY() <= 0.0D) {
                                    localVector1.setY(-localVector1.getY() + 0.3D);
                                }
                                if (Math.abs(((Location)localObject8).getX()) <= 1.0D) {
                                    localVector1.setX(localVector1.getX() * 1.2D);
                                }
                                if (Math.abs(((Location)localObject8).getZ()) <= 1.0D) {
                                    localVector1.setZ(localVector1.getZ() * 1.2D);
                                }
                                d11 = localVector1.getX() * 2.0D;
                                d14 = localVector1.getY() * 2.0D;
                                d16 = localVector1.getZ() * 2.0D;
                                if (d11 >= 3.0D) {
                                    d11 *= 0.5D;
                                }
                                if (d14 >= 3.0D) {
                                    d14 *= 0.5D;
                                }
                                if (d16 >= 3.0D) {
                                    d16 *= 0.5D;
                                }
                                localVector1.setX(d11);
                                localVector1.setY(d14);
                                localVector1.setZ(d16);

                                ((Entity)localObject2).setVelocity(localVector1);
                            }
                            break;
                        case SPELL:
                            if (!localObject2.equals(paramEntity))
                            {
                                localObject6 = ((Entity)localObject2).getLocation();
                                localObject8 = ((Location)localObject6).subtract(paramEntity.getLocation());

                                localVector1 = ((Location)localObject8).getDirection().normalize().multiply(-1.4D);
                                if (localVector1.getY() >= 1.15D) {
                                    localVector1.setY(localVector1.getY() * 0.45D);
                                } else if (localVector1.getY() >= 1.0D) {
                                    localVector1.setY(localVector1.getY() * 0.6D);
                                } else if (localVector1.getY() >= 0.8D) {
                                    localVector1.setY(localVector1.getY() * 0.85D);
                                }
                                if (localVector1.getY() <= 0.0D) {
                                    localVector1.setY(-localVector1.getY() + 0.3D);
                                }
                                if (Math.abs(((Location)localObject8).getX()) <= 1.0D) {
                                    localVector1.setX(localVector1.getX() * 1.2D);
                                }
                                if (Math.abs(((Location)localObject8).getZ()) <= 1.0D) {
                                    localVector1.setZ(localVector1.getZ() * 1.2D);
                                }
                                d11 = localVector1.getX() * -2.0D;
                                d14 = localVector1.getY() * -2.0D;
                                d16 = localVector1.getZ() * -2.0D;
                                if (d11 >= -3.0D) {
                                    d11 *= -0.5D;
                                }
                                if (d14 >= -3.0D) {
                                    d14 *= -0.5D;
                                }
                                if (d16 >= -3.0D) {
                                    d16 *= -0.5D;
                                }
                                localVector1.setX(d11);
                                localVector1.setY(d14);
                                localVector1.setZ(d16);

                                ((Entity)localObject2).setVelocity(localVector1);
                            }
                            break;
                        case POTION:
                            Utils.spawnRandomFirework(((Entity)localObject2).getLocation());
                            break;
                        case TELEPORT:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject6 = (LivingEntity)localObject2;
                                if (!localObject2.equals(paramEntity))
                                {
                                    localObject8 = localObject4[1].toUpperCase();

                                    double d9 = 0.0D;
                                    int i2 = 0;
                                    try
                                    {
                                        d9 = Double.parseDouble(localObject4[2]);
                                        i2 = Integer.parseInt(localObject4[3]);
                                    }
                                    catch (NumberFormatException localNumberFormatException6)
                                    {
                                        continue;
                                    }
                                    ParticleUtils.drawParticleLine((LivingEntity)paramEntity, (LivingEntity)localObject6, (String)localObject8, (float)d9, i2);
                                }
                            }
                            break;
                        case THROW:
                            ParticleUtils.wave(((Entity)localObject2).getLocation());
                            break;
                        case TITLES:
                            if ((localObject2 instanceof LivingEntity))
                            {
                                localObject6 = (LivingEntity)localObject2;
                                double d7 = 0.0D;
                                try
                                {
                                    d7 = Double.parseDouble(localObject4[1]);
                                }
                                catch (NumberFormatException localNumberFormatException5)
                                {
                                    continue;
                                }
                                if ((paramEntity instanceof Projectile))
                                {
                                    Projectile localProjectile = (Projectile)paramEntity;
                                    if ((localProjectile.getShooter() != null) && ((localProjectile.getShooter() instanceof LivingEntity)))
                                    {
                                        LivingEntity localLivingEntity = (LivingEntity)localProjectile.getShooter();
                                        ((LivingEntity)localObject6).damage(d7, localLivingEntity);
                                        continue;
                                    }
                                }
                                ((LivingEntity)localObject6).damage(d7, paramEntity);
                            }
                            break;
                        case PARTICLE_PULSE:
                            double d4 = 0.0D;
                            try
                            {
                                d4 = Double.parseDouble(localObject4[1]);
                            }
                            catch (NumberFormatException localNumberFormatException3)
                            {
                                continue;
                            }
                            localArrayList1.remove(str1);

                            new BukkitRunnable()
                            {
                                public void run()
                                {
                                    DivineItemsAPI.executeActions(DivineItemsAPI.this, localArrayList1, paramItemStack);
                                }
                            }.runTaskLater(plugin, (int)d4);

                            return;
                    }
                }
                localArrayList1.remove(str1);
            }
        }
    }
}
