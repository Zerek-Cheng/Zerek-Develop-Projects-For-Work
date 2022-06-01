//
// Decompiled by Procyon v0.5.30
//

package com.mchim.ItemLoreOrigin.b;

import com.mchim.ItemLoreOrigin.c.a.F;
import com.mchim.ItemLoreOrigin.c.a.o;
import com.mchim.ItemLoreOrigin.c.a.z;
import com.mchim.ItemLoreOrigin.c.a.D;
import com.mchim.ItemLoreOrigin.c.a.w;
import com.mchim.ItemLoreOrigin.c.a.L;
import com.mchim.ItemLoreOrigin.c.a.f;
import com.mchim.ItemLoreOrigin.c.a.C;
import com.mchim.ItemLoreOrigin.c.a.I;
import com.mchim.ItemLoreOrigin.c.a.l;
import com.mchim.ItemLoreOrigin.c.a.B;
import com.mchim.ItemLoreOrigin.c.a.x;
import com.mchim.ItemLoreOrigin.c.a.v;
import com.mchim.ItemLoreOrigin.c.a.H;
import com.mchim.ItemLoreOrigin.c.a.G;
import com.mchim.ItemLoreOrigin.c.a.y;
import com.mchim.ItemLoreOrigin.c.a.p;
import com.mchim.ItemLoreOrigin.c.a.E;
import com.mchim.ItemLoreOrigin.c.a.e;
import com.mchim.ItemLoreOrigin.c.a.q;
import com.mchim.ItemLoreOrigin.c.a.J;
import com.mchim.ItemLoreOrigin.c.a.K;
import com.mchim.ItemLoreOrigin.c.a.A;
import com.mchim.ItemLoreOrigin.c.a.t;
import com.mchim.ItemLoreOrigin.c.a.u;
import com.mchim.ItemLoreOrigin.c.a.s;
import com.mchim.ItemLoreOrigin.c.a.r;
import com.mchim.ItemLoreOrigin.c.a.m;
import com.mchim.ItemLoreOrigin.c.a.i;
import com.mchim.ItemLoreOrigin.c.a.h;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import com.mchim.ItemLoreOrigin.ItemLorePlugin;
import org.bukkit.Bukkit;
import com.mchim.ItemLoreOrigin.c.a.k;
import com.mchim.ItemLoreOrigin.c.a.b;
import java.util.List;
import com.mchim.ItemLoreOrigin.Config.Settings;
import com.mchim.ItemLoreOrigin.c.a.j;
import com.mchim.ItemLoreOrigin.c.c;
import com.mchim.ItemLoreOrigin.c.a.d;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.entity.LivingEntity;
import com.mchim.ItemLoreOrigin.e.g;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class a implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (!p.isOp()) {
                final List<ItemStack> itemList = g.a((LivingEntity)p, false);
                final List<com.mchim.ItemLoreOrigin.c.a> ilmList = new ArrayList<com.mchim.ItemLoreOrigin.c.a>();
                for (final ItemStack item : itemList) {
                    final com.mchim.ItemLoreOrigin.c.a ilm = new com.mchim.ItemLoreOrigin.c.a();
                    ilm.a(item);
                    ilmList.add(ilm);
                }
                p.sendMessage("§e---------------------------------------------");
                p.sendMessage("§6\u73a9\u5bb6§e§l" + p.getName() + "§6\u7684\u5c5e\u6027\u4fe1\u606f:");
                d oa = a(c.a.a, ilmList).get("Attack");
                j odb = a(c.a.a, ilmList).get("DamageBonus");
                if (oa.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("Attack").get(0) + ":§c§l" + (int)(oa.c() + oa.c() * (odb.c() / 100.0)) + "-" + (int)(oa.d() + oa.d() * (odb.c() / 100.0)));
                }
                oa = a(c.a.b, ilmList).get("Attack");
                odb = a(c.a.b, ilmList).get("DamageBonus");
                if (oa.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("Attack").get(0) + ":§c§l" + (int)(oa.c() + oa.c() * (odb.c() / 100.0)) + "-" + (int)(oa.d() + oa.d() * (odb.c() / 100.0)));
                }
                b oar = a(c.a.a, ilmList).get("Armor");
                k ods = a(c.a.a, ilmList).get("DefensiveBonus");
                if (oar.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("Armor").get(0) + ":§c§l" + (int)(oar.c() + oar.c() * (ods.c() / 100.0)) + "-" + (int)(oar.d() + oar.d() * (ods.c() / 100.0)));
                }
                oar = a(c.a.b, ilmList).get("Armor");
                ods = a(c.a.b, ilmList).get("DefensiveBonus");
                if (oar.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("Armor").get(0) + ":§c§l" + (int)(oar.c() + oar.c() * (ods.c() / 100.0)) + "-" + (int)(oar.d() + oar.d() * (ods.c() / 100.0)));
                }
                com.mchim.ItemLoreOrigin.c.a.c oarp = a(c.a.a, ilmList).get("ArmorPercent");
                if (oarp.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("ArmorPercent").get(0) + ":§c§l" + oarp.c() + "%");
                }
                oarp = a(c.a.b, ilmList).get("ArmorPercent");
                if (oarp.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("ArmorPercent").get(0) + ":§c§l" + oarp.c() + "%");
                }
                a(p, a(c.a.c, ilmList));
                p.sendMessage("§e---------------------------------------------");
                return true;
            }
            if (args.length != 2) {
                p.sendMessage("§a/ilc stats [\u73a9\u5bb6]        §c§l\u67e5\u770b\u8be5\u73a9\u5bb6\u7684\u8be6\u7ec6\u5c5e\u6027");
                p.sendMessage("§a/ilc reload [\u914d\u7f6e\u6587\u4ef6]   §c§l\u91cd\u8f7d\u8be5\u914d\u7f6e\u6587\u4ef6");
                p.sendMessage("\u914d\u7f6e\u6587\u4ef6\u6709settings,inlay");
                return true;
            }
            if (!args[0].equals("reload") && !args[0].equals("stats")) {
                p.sendMessage("§a/ilc stats [\u73a9\u5bb6]        §c§l\u67e5\u770b\u8be5\u73a9\u5bb6\u7684\u8be6\u7ec6\u5c5e\u6027");
                p.sendMessage("§a/ilc reload [\u914d\u7f6e\u6587\u4ef6]   §c§l\u91cd\u8f7d\u8be5\u914d\u7f6e\u6587\u4ef6");
                p.sendMessage("\u914d\u7f6e\u6587\u4ef6\u6709settings,inlay,config");
                return true;
            }
            if (args[0].equals("stats")) {
                final String name = args[1];
                if (!a(name)) {
                    p.sendMessage("§c[\u7cfb\u7edf]§a\u8be5\u73a9\u5bb6\u4e0d\u5728\u7ebf!");
                }
                final Player target = Bukkit.getPlayer(name);
                final List<ItemStack> itemList2 = g.a((LivingEntity)target, false);
                final List<com.mchim.ItemLoreOrigin.c.a> ilmList2 = new ArrayList<com.mchim.ItemLoreOrigin.c.a>();
                for (final ItemStack item2 : itemList2) {
                    final com.mchim.ItemLoreOrigin.c.a ilm2 = new com.mchim.ItemLoreOrigin.c.a();
                    ilm2.a(item2);
                    ilmList2.add(ilm2);
                }
                p.sendMessage("§e---------------------------------------------");
                p.sendMessage("§6\u73a9\u5bb6§e§l" + target.getName() + "§6\u7684\u5c5e\u6027\u4fe1\u606f:");
                d oa2 = a(c.a.a, ilmList2).get("Attack");
                j odb2 = a(c.a.a, ilmList2).get("DamageBonus");
                if (oa2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("Attack").get(0) + ":§c§l" + (int)(oa2.c() + oa2.c() * (odb2.c() / 100.0)) + "-" + (int)(oa2.d() + oa2.d() * (odb2.c() / 100.0)));
                }
                oa2 = a(c.a.b, ilmList2).get("Attack");
                odb2 = a(c.a.b, ilmList2).get("DamageBonus");
                if (oa2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("Attack").get(0) + ":§c§l" + (int)(oa2.c() + oa2.c() * (odb2.c() / 100.0)) + "-" + (int)(oa2.d() + oa2.d() * (odb2.c() / 100.0)));
                }
                b oar2 = a(c.a.a, ilmList2).get("Armor");
                k ods2 = a(c.a.a, ilmList2).get("DefensiveBonus");
                if (oar2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("Armor").get(0) + ":§c§l" + (int)(oar2.c() + oar2.c() * (ods2.c() / 100.0)) + "-" + (int)(oar2.d() + oar2.d() * (ods2.c() / 100.0)));
                }
                oar2 = a(c.a.b, ilmList2).get("Armor");
                ods2 = a(c.a.b, ilmList2).get("DefensiveBonus");
                if (oar2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("Armor").get(0) + ":§c§l" + (int)(oar2.c() + oar2.c() * (ods2.c() / 100.0)) + "-" + (int)(oar2.d() + oar2.d() * (ods2.c() / 100.0)));
                }
                com.mchim.ItemLoreOrigin.c.a.c oarp2 = a(c.a.a, ilmList2).get("ArmorPercent");
                if (oarp2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVP") + " " + Settings.I.loreType.get("ArmorPercent").get(0) + ":§c§l" + oarp2.c() + "%");
                }
                oarp2 = a(c.a.b, ilmList2).get("ArmorPercent");
                if (oarp2.b() != null) {
                    p.sendMessage("§6\u7c7b\u578b:" + Settings.I.TypeTarget.get("PVE") + " " + Settings.I.loreType.get("ArmorPercent").get(0) + ":§c§l" + oarp2.c() + "%");
                }
                a(p, a(c.a.c, ilmList2));
                p.sendMessage("§e---------------------------------------------");
            }
            if (args[0].equals("reload")) {
                final String name = args[1];
                if (!name.equals("settings") && !name.equals("inlay")) {
                    p.sendMessage("\u914d\u7f6e\u6587\u4ef6\u4e0d\u5408\u6cd5,\u5fc5\u987b\u4e3asettings,inlay");
                    return true;
                }
                if (name.equals("settings")) {
                    com.mchim.ItemLoreOrigin.Config.a.a((Plugin)ItemLorePlugin.a, Settings.class, true);
                    p.sendMessage("\u91cd\u8f7d\u914d\u7f6e\u6587\u4ef6settings\u6210\u529f");
                }
                if (name.equals("inlay")) {
                    ItemLorePlugin.d.b();
                    p.sendMessage("\u91cd\u8f7d\u914d\u7f6e\u6587\u4ef6inlay\u6210\u529f");
                }
            }
        }
        else if (args[0].equals("reload")) {
            final String name2 = args[1];
            if (!name2.equals("settings") && !name2.equals("inlaysettings") && !name2.equals("config")) {
                ItemLorePlugin.a.getLogger().warning("\u914d\u7f6e\u6587\u4ef6\u4e0d\u5408\u6cd5,\u5fc5\u987b\u4e3asettings,inlaysettings,config");
                return true;
            }
            if (name2.equals("settings")) {
                com.mchim.ItemLoreOrigin.Config.a.a((Plugin)ItemLorePlugin.a, Settings.class, true);
                ItemLorePlugin.a.getLogger().warning("\u91cd\u8f7d\u914d\u7f6e\u6587\u4ef6settings\u6210\u529f");
            }
            if (name2.equals("inlay")) {
                ItemLorePlugin.d.b();
                ItemLorePlugin.a.getLogger().warning("\u91cd\u8f7d\u914d\u7f6e\u6587\u4ef6inlay\u6210\u529f");
            }
        }
        return true;
    }

    public static Map<String, com.mchim.ItemLoreOrigin.c.a.a> a(final c.a dt, final List<com.mchim.ItemLoreOrigin.c.a> ilmList) {
        final Map<String, com.mchim.ItemLoreOrigin.c.a.a> loMap = new HashMap<String, com.mchim.ItemLoreOrigin.c.a.a>();
        for (final String name : com.mchim.ItemLoreOrigin.c.a.a.a.keySet()) {
            final Class<? extends com.mchim.ItemLoreOrigin.c.a.a> clazz = com.mchim.ItemLoreOrigin.c.a.a.a.get(name);
            com.mchim.ItemLoreOrigin.c.a.a lo = null;
            try {
                lo = (com.mchim.ItemLoreOrigin.c.a.a)clazz.newInstance();
            }
            catch (IllegalAccessException | InstantiationException ex2) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException var6 = ex;
                var6.printStackTrace();
            }
            loMap.put(name, lo);
        }
        for (final com.mchim.ItemLoreOrigin.c.a ilm : ilmList) {
            final List<com.mchim.ItemLoreOrigin.c.a.a> loList = ilm.a();
            for (final com.mchim.ItemLoreOrigin.c.a.a localLo : loList) {
                if (dt == c.a.c && localLo.a() == null) {
                    final com.mchim.ItemLoreOrigin.c.a.a loTotal = loMap.get(localLo.b());
                    loTotal.a(localLo);
                    loMap.put(loTotal.b(), loTotal);
                }
                else {
                    if ((localLo.a() != com.mchim.ItemLoreOrigin.c.a.a.b.a && ((dt == c.a.a && localLo.a() != com.mchim.ItemLoreOrigin.c.a.a.b.b) || (dt == c.a.b && localLo.a() != com.mchim.ItemLoreOrigin.c.a.a.b.c && localLo.a() != com.mchim.ItemLoreOrigin.c.a.a.b.e))) || !loMap.containsKey(localLo.b())) {
                        continue;
                    }
                    final com.mchim.ItemLoreOrigin.c.a.a loTotal = loMap.get(localLo.b());
                    loTotal.a(localLo);
                    loMap.put(loTotal.b(), loTotal);
                }
            }
        }
        return loMap;
    }

    public static void a(final Player p, final Map<String, com.mchim.ItemLoreOrigin.c.a.a> map) {
        for (final String s : map.keySet()) {
            final com.mchim.ItemLoreOrigin.c.a.a lo = map.get(s);
            if (s.equals("CritChance") && lo.b() != null) {
                final h occ = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + occ.c() + "%");
            }
            if (s.equals("CritDamage") && lo.b() != null) {
                final i ocd = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ocd.c());
            }
            if (s.equals("Dodge") && lo.b() != null) {
                final m od = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + od.c() + "%");
            }
            if (s.equals("HealthAdd") && lo.b() != null) {
                final r oha = map.get(s);
                final int health = oha.b;
                if (s.equals("HealthBonus") && lo.b() != null) {
                    final s otc = map.get(s);
                    final int healthBouns = otc.b;
                    final int n = (int)(health + health * (healthBouns / 100.0));
                }
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oha.b);
            }
            if (s.equals("HealthRecovery") && lo.b() != null) {
                final u ohr = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ohr.b);
            }
            if (s.equals("HealthRatio") && lo.b() != null) {
                final t ohra = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ohra.b + "%");
            }
            if (s.equals("MoveSpeed") && lo.b() != null) {
                final A oms = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oms.b);
            }
            if (s.equals("VampireChance") && lo.b() != null) {
                final K ovc = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ovc.c() + "%");
            }
            if (s.equals("VampireAmount") && lo.b() != null) {
                final J ova = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ova.c());
            }
            if (s.equals("Frozen") && lo.b() != null) {
                final q of = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + of.c() + "%");
            }
            if (s.equals("Blindness") && lo.b() != null) {
                final e ob = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ob.c() + "%");
            }
            if (s.equals("Skill") && lo.b() != null) {
                final E os = map.get(s);
                if (os.d() == null) {
                    return;
                }
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + os.d().getName() + ",\u51e0\u7387" + os.g() + ",\u9b54\u6cd5" + os.f() + ",\u51e0\u7387" + os.e());
            }
            if (s.equals("Exp") && lo.b() != null) {
                final p oe = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oe.c() + "%\u589e\u52a0");
            }
            if (s.equals("MagicRecovery") && lo.b() != null) {
                final y omr = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + omr.c());
            }
            if (s.equals("ThornsAmount") && lo.b() != null) {
                final G ota = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + ota.c());
            }
            if (s.equals("ThornsChance") && lo.b() != null) {
                final H otc2 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc2.c() + "%");
            }
            if (s.equals("HitRate") && lo.b() != null) {
                final v otc3 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc3.c() + "%");
            }
            if (s.equals("IgnoreDefense") && lo.b() != null) {
                final x otc4 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc4.c());
            }
            if (s.equals("Puncture") && lo.b() != null) {
                final B otc5 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc5.c() + "%");
            }
            if (s.equals("DamageBonus") && lo.b() != null) {
                final j otc6 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc6.c() + "%");
            }
            if (s.equals("DefensiveBonus") && lo.b() != null) {
                final k otc7 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc7.c() + "%");
            }
            if (s.equals("HealthBonus") && lo.b() != null) {
                final s otc8 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc8.c() + "%");
            }
            if (s.equals("Chaos") && lo.b() != null) {
                final g otc9 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc9.c() + "%");
            }
            if (s.equals("Devastate") && lo.b() != null) {
                final l otc10 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc10.c() + "%");
            }
            if (s.equals("Toughness") && lo.b() != null) {
                final I otc11 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc11.c() + "%");
            }
            if (s.equals("RiotStrike") && lo.b() != null) {
                final C otc12 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc12.c() + "%");
            }
            if (s.equals("BlockThorns") && lo.b() != null) {
                final f otc13 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc13.c() + "%");
            }
            if (s.equals("VampireResist") && lo.b() != null) {
                final L otc14 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc14.c() + "%");
            }
            if (s.equals("HolyStrike") && lo.b() != null) {
                final w otc15 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc15.c() + "%");
            }
            if (s.equals("ShadowStrike") && lo.b() != null) {
                final D otc16 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + otc16.c() + "%");
            }
            if (s.equals("MortalStrike") && lo.b() != null) {
                final z oms2 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oms2.c() + "%");
            }
            if (s.equals("Beheaded") && lo.b() != null) {
                final com.mchim.ItemLoreOrigin.c.b oms3 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oms3.c() + "%");
            }
            if (s.equals("Executed") && lo.b() != null) {
                final o oms4 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oms4.c() + "%");
            }
            if (s.equals("Terrified") && lo.b() != null) {
                final F oms5 = map.get(s);
                p.sendMessage("§a" + Settings.I.loreType.get(s).get(0) + ":§c§l" + oms5.c() + "%");
            }
        }
    }

    public static boolean a(final String pname) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(pname)) {
                return true;
            }
        }
        return false;
    }
}
