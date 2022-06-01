/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.potion.PotionEffectType
 */
package yo;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.ad_0;
import yo.ah_1;
import yo.aj_1;
import yo.ak_1;
import yo.am_0;
import yo.ao_0;
import yo.bc_0;
import yo.bg_1;
import yo.bi_1;
import yo.bj_1;
import yo.bk_1;
import yo.bl_1;
import yo.bm_1;
import yo.bn_1;
import yo.bo_1;
import yo.bp_1;
import yo.bq_1;
import yo.br_1;
import yo.bs_0;
import yo.bt_0;
import yo.bu_0;
import yo.bv_1;
import yo.bw_0;
import yo.bx_1;
import yo.by_0;
import yo.by_1;
import yo.bz_0;
import yo.ca_1;
import yo.cb_1;
import yo.cc_1;
import yo.cd_1;
import yo.ce_1;
import yo.cf_1;
import yo.cg_1;
import yo.ch;
import yo.ci;
import yo.ck;
import yo.cl;
import yo.cm;
import yo.cn;
import yo.co;
import yo.cp;
import yo.ct;

public class ag_0
implements ad_0 {
    @ak_1(a="rpgitem $n[] power arrow $e[#EventType]")
    @ah_1(a="$command.rpgitem.arrow")
    @aj_1(a="item_power_arrow")
    public void arrow(CommandSender sender, RPGItem item, am_0 eventType) {
        this.arrow(sender, item, eventType, 0.0, 20L);
    }

    @ak_1(a="rpgitem $n[] power arrow $e[#EventType] $power:f[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.arrow.full")
    @aj_1(a="item_power_arrow")
    public void arrow(CommandSender sender, RPGItem item, am_0 eventType, double damage, long cooldown) {
        String locale = ao_0.a((Object)sender);
        bk_1 pow = new bk_1();
        pow.d = eventType;
        pow.h = damage;
        pow.c = item;
        pow.g = cooldown;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power arrowrain $e[#EventType]")
    @ah_1(a="$command.rpgitem.arrowrain")
    @aj_1(a="item_power_arrowrain")
    public void arrowrain(CommandSender sender, RPGItem item, am_0 eventType) {
        this.arrowrain(sender, item, eventType, 5.0, 10, 0.0, "0:39|8:60", 20L, 1);
    }

    @ak_1(a="rpgitem $n[] power arrowrain $e[#EventType] $radius:f[1.0,1000.0] $height:i[1,100] $power:f[] $arrowrainwave:s[] $cooldown:l[] $chance:i[]")
    @ah_1(a="$command.rpgitem.arrowrain.full")
    @aj_1(a="item_power_arrowrain")
    public void arrowrain(CommandSender sender, RPGItem item, am_0 eventType, double radius, int height, double damage, String detail, long cooldown, int chance) {
        String locale = ao_0.a((Object)sender);
        bl_1 pow = new bl_1();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        pow.e = chance;
        pow.h = height;
        pow.i = radius;
        pow.j = damage;
        pow.l = detail;
        pow.k = bl_1.c(detail);
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power throw $e[#EventType] $power:f[] $fire:o[t,f]")
    @ah_1(a="$command.rpgitem.throw")
    @aj_1(a="item_power_throw")
    public void throwPower(CommandSender sender, RPGItem item, am_0 eventType, double power, String fire) {
        this.throwPower(sender, item, eventType, power, fire, "f", 60, 20L);
    }

    @ak_1(a="rpgitem $n[] power throw $e[#EventType] $power:f[] $fire:o[t,f] $destoryblock:o[t,f] $delay:i[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.throw.full")
    @aj_1(a="item_power_throw")
    public void throwPower(CommandSender sender, RPGItem item, am_0 eventType, double power, String fire, String destory, int delay, long cooldown) {
        String locale = ao_0.a((Object)sender);
        cp pow = new cp();
        pow.d = eventType;
        pow.g = cooldown;
        pow.c = item;
        pow.h = delay * 50;
        pow.i = (float)power;
        pow.j = "t".equalsIgnoreCase(fire);
        pow.k = "t".equalsIgnoreCase(destory);
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power command $e[#EventType] $cooldown:l[] $display:s[] $command:s[]")
    @ah_1(a="$command.rpgitem.command")
    @aj_1(a="item_power_command_b")
    public void command(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, String displayText, String command) {
        this.command(sender, item, eventType, cooldown, displayText, command, "", 1);
    }

    @ak_1(a="rpgitem $n[] power command $e[#EventType] $cooldown:l[] $display:s[] $command:s[] $permission:s[] $chance:i[]")
    @ah_1(a="$command.rpgitem.command.full")
    @aj_1(a="item_power_command_a")
    public void command(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, String displayText, String command, String permission, int chance) {
        String locale = ao_0.a((Object)sender);
        br_1 pow = new br_1();
        pow.d = eventType;
        pow.g = cooldown;
        if ((command = command.trim()).charAt(0) == '/') {
            command = command.substring(1);
        }
        pow.e = chance;
        pow.i = displayText;
        pow.h = command;
        pow.j = permission;
        pow.c = item;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power command $e[#EventType] $cooldown:l[] $details:s[]")
    @ah_1(a="$command.rpgitem.command.old")
    @aj_1(a="item_power_command_c")
    public void command(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, String details) {
        String locale = ao_0.a((Object)sender);
        String[] pArgs = details.split("\\|");
        if (pArgs.length < 2) {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.command.format", locale));
            return;
        }
        String display = pArgs[0].trim();
        String command = pArgs[1].trim();
        if (command.charAt(0) == '/') {
            command = command.substring(1);
        }
        String permission = "";
        if (pArgs.length > 2) {
            permission = pArgs[2].trim();
        }
        this.command(sender, item, eventType, cooldown, display, command, permission, 1);
    }

    @ak_1(a="rpgitem $n[] power consume $e[#EventType]")
    @ah_1(a="$command.rpgitem.consume")
    @aj_1(a="item_power_consume")
    public void consume(CommandSender sender, RPGItem item, am_0 eventType) {
        String locale = ao_0.a((Object)sender);
        bs_0 pow = new bs_0();
        pow.d = eventType;
        pow.c = item;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power projectile $e[#EventType] $type:o[smallfireball,bigfireball,snowball,egg,witherskull,fishhook]")
    @ah_1(a="$command.rpgitem.projectile")
    @aj_1(a="item_power_projectile")
    public void projectile(CommandSender sender, RPGItem item, am_0 eventType, String type) {
        this.projectile(sender, item, eventType, type, 0, 0.0, 20L);
    }

    @ak_1(a="rpgitem $n[] power projectile $e[#EventType] $type:o[smallfireball,bigfireball,snowball,egg,witherskull,fishhook] $bounce:i[0,100000000] $power:f[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.projectile.full")
    @aj_1(a="item_power_projectile")
    public void projectile(CommandSender sender, RPGItem item, am_0 eventType, String type, int bounceCount, double damage, long cooldown) {
        String locale = ao_0.a((Object)sender);
        cg_1 pow = null;
        switch (type.toLowerCase()) {
            case "smallfireball": {
                pow = new cl();
                break;
            }
            case "bigfireball": {
                pow = new bn_1();
                break;
            }
            case "snowball": {
                pow = new cm();
                break;
            }
            case "egg": {
                pow = new bu_0();
                break;
            }
            case "witherskull": {
                pow = new ct();
                break;
            }
            case "fishhook": {
                pow = new bv_1();
            }
        }
        pow.d = eventType;
        pow.i = bounceCount;
        pow.h = damage;
        pow.c = item;
        pow.g = cooldown;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power flame $e[#EventType]")
    @ah_1(a="$command.rpgitem.flame")
    @aj_1(a="item_power_flame")
    public void flame(CommandSender sender, RPGItem item, am_0 eventType) {
        this.flame(sender, item, eventType, 20);
    }

    @ak_1(a="rpgitem $n[] power flame $e[#EventType] $burntime:i[]")
    @ah_1(a="$command.rpgitem.flame.full")
    @aj_1(a="item_power_flame")
    public void flame(CommandSender sender, RPGItem item, am_0 eventType, int burnTime) {
        String locale = ao_0.a((Object)sender);
        bw_0 pow = new bw_0();
        pow.d = eventType;
        pow.c = item;
        pow.h = burnTime;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power aoe $e[#EventType] $radius:f[1.0,20.0]")
    @ah_1(a="$command.rpgitem.aoe")
    @aj_1(a="item_power_aoe")
    public void aoe(CommandSender sender, RPGItem item, am_0 eventType, double range) {
        String locale = ao_0.a((Object)sender);
        bj_1 pow = new bj_1();
        pow.d = eventType;
        pow.c = item;
        pow.h = range;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power block $e[#EventType] $type:o[heal,buff,rpgitem]")
    @ah_1(a="$command.rpgitem.block")
    @aj_1(a="item_power_block")
    public void blockbuff(CommandSender sender, RPGItem item, am_0 eventType, String type) {
        this.blockbuff(sender, item, eventType, type, 60, 20);
    }

    @ak_1(a="rpgitem $n[] power block $e[#EventType] $type:o[heal,buff,rpgitem] $duration:i[] $chance:i[]")
    @ah_1(a="$command.rpgitem.block.full")
    @aj_1(a="item_power_block")
    public void blockbuff(CommandSender sender, RPGItem item, am_0 eventType, String type, int duration, int chance) {
        bo_1 pow;
        String locale = ao_0.a((Object)sender);
        switch (type) {
            case "heal": {
                pow = new bp_1();
                break;
            }
            case "rpgitem": {
                pow = new bq_1();
                break;
            }
            default: {
                pow = new bo_1();
            }
        }
        pow.d = eventType;
        pow.c = item;
        pow.e = chance;
        pow.h = duration;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power lifesteal $e[#EventType] $quotiety:f[] $chance:i[]")
    @ah_1(a="$command.rpgitem.lifesteal")
    @aj_1(a="item_power_lifesteal")
    public void lifesteal(CommandSender sender, RPGItem item, am_0 eventType, double multiple, int chance) {
        String locale = ao_0.a((Object)sender);
        ca_1 pow = new ca_1();
        pow.d = eventType;
        pow.c = item;
        pow.h = multiple;
        pow.e = chance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power backstab $e[#EventType] $quotiety:f[] $chance:i[]")
    @ah_1(a="$command.rpgitem.backstab")
    @aj_1(a="item_power_backstab")
    public void backstab(CommandSender sender, RPGItem item, am_0 eventType, double multiple, int chance) {
        String locale = ao_0.a((Object)sender);
        bm_1 pow = new bm_1();
        pow.d = eventType;
        pow.c = item;
        pow.h = multiple;
        pow.e = chance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power crit $e[#EventType] $quotiety:f[] $chance:i[]")
    @ah_1(a="$command.rpgitem.crit")
    @aj_1(a="item_power_crit")
    public void crit(CommandSender sender, RPGItem item, am_0 eventType, double multiple, int chance) {
        String locale = ao_0.a((Object)sender);
        bt_0 pow = new bt_0();
        pow.d = eventType;
        pow.c = item;
        pow.e = chance;
        pow.h = multiple;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power ice $e[#EventType]")
    @ah_1(a="$command.rpgitem.ice")
    @aj_1(a="item_power_ice")
    public void ice(CommandSender sender, RPGItem item, am_0 eventType) {
        this.ice(sender, item, eventType, 20L);
    }

    @ak_1(a="rpgitem $n[] power ice $e[#EventType] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.ice.full")
    @aj_1(a="item_power_ice")
    public void ice(CommandSender sender, RPGItem item, am_0 eventType, long cooldown) {
        String locale = ao_0.a((Object)sender);
        by_1 pow = new by_1();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power knockup $e[#EventType]")
    @ah_1(a="$command.rpgitem.knockup")
    @aj_1(a="item_power_knockup")
    public void knockup(CommandSender sender, RPGItem item, am_0 eventType) {
        String locale = ao_0.a((Object)sender);
        bz_0 pow = new bz_0();
        pow.d = eventType;
        pow.c = item;
        pow.e = 20;
        pow.h = 2.0;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power knockup $e[#EventType] $power:f[] $chance:i[]")
    @ah_1(a="$command.rpgitem.knockup.full")
    @aj_1(a="item_power_knockup")
    public void knockup(CommandSender sender, RPGItem item, am_0 eventType, double power, int chance) {
        String locale = ao_0.a((Object)sender);
        bz_0 pow = new bz_0();
        pow.d = eventType;
        pow.c = item;
        pow.e = chance;
        pow.h = power;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power lightning $e[#EventType]")
    @ah_1(a="$command.rpgitem.lightning")
    @aj_1(a="item_power_lightning")
    public void lightning(CommandSender sender, RPGItem item, am_0 eventType) {
        this.lightning(sender, item, eventType, 0.0, 20);
    }

    @ak_1(a="rpgitem $n[] power lightning $e[#EventType] $power:f[] $chance:i[]")
    @ah_1(a="$command.rpgitem.lightning.full")
    @aj_1(a="item_power_lightning")
    public void lightning(CommandSender sender, RPGItem item, am_0 eventType, double damage, int chance) {
        String locale = ao_0.a((Object)sender);
        cb_1 pow = new cb_1();
        pow.d = eventType;
        pow.h = damage;
        pow.c = item;
        pow.e = chance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power potionhit $e[#EventType] $duration:i[] $amplifier:i[] $effect:s[] $chance:i[]")
    @ah_1(a="$command.rpgitem.potionhit+PotionEffectType")
    @aj_1(a="item_power_potionhit")
    public void potionhit(CommandSender sender, RPGItem item, am_0 eventType, int duration, int amplifier, String effect, int chance) {
        String locale = ao_0.a((Object)sender);
        cc_1 pow = new cc_1();
        pow.d = eventType;
        pow.c = item;
        pow.e = chance;
        pow.i = duration;
        pow.j = amplifier;
        pow.h = bg_1.a(effect, locale);
        if (pow.h == null) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.effect", locale), effect));
            return;
        }
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power potionpurge $e[#EventType] $duration:l[] $type:o[buff,debuff,all]")
    @ah_1(a="$command.rpgitem.potionpurge")
    @aj_1(a="item_power_potionpurge")
    public void potionpurge(CommandSender sender, RPGItem item, am_0 eventType, long duration, String type) {
        this.potionpurge(sender, item, eventType, duration, type, 20L);
    }

    @ak_1(a="rpgitem $n[] power potionpurge $e[#EventType] $duration:l[] $type:o[buff,debuff,all] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.potionpurge.full")
    @aj_1(a="item_power_potionpurge")
    public void potionpurge(CommandSender sender, RPGItem item, am_0 eventType, long duration, String type, long cooldown) {
        String locale = ao_0.a((Object)sender);
        cd_1 pow = new cd_1();
        pow.c = item;
        pow.d = eventType;
        pow.h = duration;
        pow.g = cooldown;
        pow.i = bc_0.getPurgeType(type);
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power potionthrow $e[#EventType] $duration:i[] $amplifier:i[] $effect:s[]")
    @ah_1(a="$command.rpgitem.potionthrow+PotionEffectType")
    @aj_1(a="item_power_potionthrow")
    public void potionthrow(CommandSender sender, RPGItem item, am_0 eventType, int duration, int amplifier, String effect) {
        this.potionthrow(sender, item, eventType, duration, amplifier, effect, 20L);
    }

    @ak_1(a="rpgitem $n[] power potionthrow $e[#EventType] $duration:i[] $amplifier:i[] $effect:s[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.potionthrow.full+PotionEffectType")
    @aj_1(a="item_power_potionthrow")
    public void potionthrow(CommandSender sender, RPGItem item, am_0 eventType, int duration, int amplifier, String effect, long cooldown) {
        String locale = ao_0.a((Object)sender);
        cf_1 pow = new cf_1();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        pow.i = duration;
        pow.j = amplifier;
        pow.h = bg_1.a(effect, locale);
        if (pow.h == null) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.effect", locale), effect));
            return;
        }
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power potionself $e[#EventType] $amplifier:i[] $effect:s[]")
    @ah_1(a="$command.rpgitem.potionself+PotionEffectType")
    @aj_1(a="item_power_potionself")
    public void potionself(CommandSender sender, RPGItem item, am_0 eventType, int amplifier, String effect) {
        this.potionself(sender, item, eventType, 0L, 30, amplifier, effect);
    }

    @ak_1(a="rpgitem $n[] power potionself $e[#EventType] $cooldown:l[] $duration:i[] $amplifier:i[] $effect:s[]")
    @ah_1(a="$command.rpgitem.potionself+PotionEffectType")
    @aj_1(a="item_power_potionself")
    public void potionself(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, int duration, int amplifier, String effect) {
        String locale = ao_0.a((Object)sender);
        ce_1 pow = new ce_1();
        pow.d = eventType;
        pow.j = bg_1.a(effect, locale);
        if (pow.j == null) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.effect", locale), effect));
            return;
        }
        pow.c = item;
        pow.g = cooldown;
        pow.i = duration;
        pow.h = amplifier;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power rainbow $e[#EventType]")
    @ah_1(a="$command.rpgitem.rainbow")
    @aj_1(a="item_power_rainbow")
    public void rainbow(CommandSender sender, RPGItem item, am_0 eventType) {
        this.rainbow(sender, item, eventType, 20L, 5);
    }

    @ak_1(a="rpgitem $n[] power rainbow $e[#EventType] $cooldown:l[] $count:i[]")
    @ah_1(a="$command.rpgitem.rainbow.full")
    @aj_1(a="item_power_rainbow")
    public void rainbow(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, int count) {
        String locale = ao_0.a((Object)sender);
        ch pow = new ch();
        pow.d = eventType;
        pow.g = cooldown;
        pow.h = count;
        pow.c = item;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power rumble $e[#EventType] $cooldown:l[] $power:i[] $distance:i[]")
    @ah_1(a="$command.rpgitem.rumble")
    @aj_1(a="item_power_rumble")
    public void rumble(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, int power, int distance) {
        String locale = ao_0.a((Object)sender);
        ci pow = new ci();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        pow.h = power;
        pow.i = distance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power teleport $e[#EventType]")
    @ah_1(a="$command.rpgitem.teleport")
    @aj_1(a="item_power_teleport")
    public void teleport(CommandSender sender, RPGItem item, am_0 eventType) {
        this.teleport(sender, item, eventType, 20L, 5);
    }

    @ak_1(a="rpgitem $n[] power teleport $e[#EventType] $cooldown:l[] $distance:i[]")
    @ah_1(a="$command.rpgitem.teleport.full")
    @aj_1(a="item_power_teleport")
    public void teleport(CommandSender sender, RPGItem item, am_0 eventType, long cooldown, int distance) {
        String locale = ao_0.a((Object)sender);
        co pow = new co();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        pow.h = distance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power tntcannon $e[#EventType]")
    @ah_1(a="$command.rpgitem.tntcannon")
    @aj_1(a="item_power_tntcannon")
    public void tntcannon(CommandSender sender, RPGItem item, am_0 eventType) {
        this.tntcannon(sender, item, eventType, 0.0, 20, 20L);
    }

    @ak_1(a="rpgitem $n[] power tntcannon $e[#EventType] $power:f[] $chance:i[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.tntcannon.full")
    @aj_1(a="item_power_tntcannon")
    public void tntcannon(CommandSender sender, RPGItem item, am_0 eventType, double damage, int chance, long cooldown) {
        String locale = ao_0.a((Object)sender);
        cn pow = new cn();
        pow.e = chance;
        pow.h = damage;
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power skyhook $e[#EventType] $m[] $distance:i[] $cooldown:l[]")
    @ah_1(a="$command.rpgitem.skyhook")
    @aj_1(a="item_power_skyhook")
    public void skyHook(CommandSender sender, RPGItem item, am_0 eventType, Material material, int distance, long cooldown) {
        String locale = ao_0.a((Object)sender);
        ck pow = new ck();
        pow.d = eventType;
        pow.c = item;
        pow.g = cooldown;
        pow.h = material;
        pow.i = distance;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }

    @ak_1(a="rpgitem $n[] power food $e[#EventType] $foodpoints:i[]")
    @ah_1(a="$command.rpgitem.food")
    @aj_1(a="item_power_food")
    public void food(CommandSender sender, RPGItem item, am_0 eventType, int foodpoints) {
        String locale = ao_0.a((Object)sender);
        bx_1 pow = new bx_1();
        pow.d = eventType;
        pow.c = item;
        pow.h = foodpoints;
        item.a(pow);
        by_0.c(Plugin.c);
        sender.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.power.ok", locale));
    }
}

