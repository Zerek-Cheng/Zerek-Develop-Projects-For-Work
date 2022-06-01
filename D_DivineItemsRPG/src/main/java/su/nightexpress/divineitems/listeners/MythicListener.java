/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent
 *  io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent
 *  io.lumine.xikage.mythicmobs.mobs.MythicMob
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package su.nightexpress.divineitems.listeners;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.MythicMobsHook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.drops.DropManager;

public class MythicListener
implements Listener {
    private DivineItems plugin;

    public MythicListener(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @EventHandler
    public void onMDrop(MythicMobDeathEvent mythicMobDeathEvent) {
        if (!(mythicMobDeathEvent.getKiller() instanceof Player)) {
            return;
        }
        Player player = (Player)mythicMobDeathEvent.getKiller();
        if (player == null) {
            return;
        }
        mythicMobDeathEvent.getDrops().addAll(this.plugin.getMM().getDropManager().methodRoll(player, (LivingEntity)mythicMobDeathEvent.getEntity(), false));
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onMMSpawn(MythicMobSpawnEvent mythicMobSpawnEvent) {
        if (!(mythicMobSpawnEvent.getEntity() instanceof LivingEntity)) {
            return;
        }
        final LivingEntity livingEntity = (LivingEntity)mythicMobSpawnEvent.getEntity();
        MythicMob mythicMob = mythicMobSpawnEvent.getMobType();
        ArrayList arrayList = new ArrayList(mythicMob.getEquipment());
        if (arrayList.isEmpty()) {
            return;
        }
        for (String string : arrayList) {
            ItemStack itemStack;
            if (!string.startsWith("ditems")) continue;
            String[] arrstring = string.replace("ditems", "").split(":");
            final int n = Integer.parseInt(arrstring[1]);
            if (this.getItem(string).isEmpty() || (itemStack = this.getItem(string).get(0)) == null) continue;
            new BukkitRunnable(){

                public void run() {
                    switch (n) {
                        case 4: {
                            livingEntity.getEquipment().setHelmet(itemStack);
                            break;
                        }
                        case 3: {
                            livingEntity.getEquipment().setChestplate(itemStack);
                            break;
                        }
                        case 2: {
                            livingEntity.getEquipment().setLeggings(itemStack);
                            break;
                        }
                        case 1: {
                            livingEntity.getEquipment().setBoots(itemStack);
                            break;
                        }
                        case 0: {
                            livingEntity.getEquipment().setItemInMainHand(itemStack);
                            break;
                        }
                        case 5: {
                            livingEntity.getEquipment().setItemInOffHand(itemStack);
                        }
                    }
                }
            }.runTaskLater((Plugin)this.plugin, 5L);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onMMDeath(MythicMobDeathEvent mythicMobDeathEvent) {
        MythicMob mythicMob = mythicMobDeathEvent.getMobType();
        ArrayList<String> arrayList = new ArrayList<String>(mythicMob.getDrops());
        if (arrayList.isEmpty()) {
            return;
        }
        if (mythicMobDeathEvent.getKiller() == null || !(mythicMobDeathEvent.getKiller() instanceof Player)) {
            return;
        }
        for (String string : mythicMob.getDrops()) {
            if (!this.plugin.getHM().getMythicHook().isDropTable(string)) continue;
            arrayList.addAll(this.plugin.getHM().getMythicHook().getTableDrops(string));
        }
        for (String string : arrayList) {
            List<ItemStack> list;
            if (!string.startsWith("ditems") || (list = this.getItem(string)) == null || list.isEmpty()) continue;
            mythicMobDeathEvent.getDrops().addAll(list);
        }
    }

    /*
     * Exception decompiling
     */
    private List<ItemStack> getItem(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

}

