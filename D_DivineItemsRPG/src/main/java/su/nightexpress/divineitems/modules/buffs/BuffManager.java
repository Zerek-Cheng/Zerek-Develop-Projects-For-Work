/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems.modules.buffs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.BuffCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.types.ArmorType;
import su.nightexpress.divineitems.types.DamageType;
import su.nightexpress.divineitems.utils.Utils;

public class BuffManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig config;
    private boolean e;
    private int taskId;
    private boolean resetDeath;
    private boolean buffMsg;
    private HashMap<UUID, List<Buff>> buffs;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType;

    public BuffManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.buffs = new HashMap();
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.config = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.resetDeath = this.config.getConfig().getBoolean("ResetBuffsOnDeath");
        if (!this.config.getConfig().contains("BuffMessages")) {
            this.config.getConfig().set("BuffMessages", (Object)true);
        }
        this.buffMsg = this.config.getConfig().getBoolean("BuffMessages");
        this.config.save();
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isResolvable() {
        return false;
    }

    @Override
    public String name() {
        return "Buffs";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new BuffCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
            this.startTask();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.buffs.clear();
            this.e = false;
            this.unregisterListeners();
            this.stopTask();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void startTask() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                HashMap<UUID, List<Buff>> hashMap = new HashMap<UUID, List<Buff>>(BuffManager.this.getBuffs());
                for (UUID uUID : hashMap.keySet()) {
                    for (Buff buff : hashMap.get(uUID)) {
                        if (buff.getTimeSec() - 1 < 0) {
                            BuffManager.this.resetBuff(uUID, buff.getType(), buff.getValue());
                            continue;
                        }
                        buff.setTimeSec(buff.getTimeSec() - 1);
                        BuffManager.this.update(uUID, buff);
                    }
                }
            }
        }, 10L, 20L);
    }

    public void stopTask() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
    }

    public boolean addBuff(Player player, BuffType buffType, String string, double d, int n, boolean bl) {
        if (!this.isValidBuff(buffType, string)) {
            return false;
        }
        if (bl) {
            double d2 = this.getBuff(player, buffType, string);
            double d3 = d + d2;
            this.addBuff(player, buffType, string, d3, n, false);
            return true;
        }
        this.resetBuff(player, buffType, string);
        List<Object> list = new ArrayList();
        if (this.getBuffs().containsKey(player.getUniqueId())) {
            list = this.buffs.get(player.getUniqueId());
        }
        Buff buff = new Buff(buffType, string, d, n);
        list.add(buff);
        this.buffs.put(player.getUniqueId(), list);
        if (this.buffMsg) {
            String string2 = this.getBuffName(buffType, string);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Buffs_Get.toMsg().replace("%type%", string2).replace("%time%", new StringBuilder(String.valueOf(n)).toString()).replace("%value%", new StringBuilder(String.valueOf(Utils.round3(d))).toString()));
        }
        return true;
    }

    public void resetBuff(Player player, BuffType buffType, String string) {
        if (!this.getBuffs().containsKey(player.getUniqueId())) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.getBuffs().get(player.getUniqueId()));
        for (Buff buff : arrayList) {
            if (buff.getType() != buffType || !buff.getValue().equalsIgnoreCase(string)) continue;
            arrayList.remove(buff);
            if (!this.buffMsg || player == null || !player.isOnline()) break;
            String string2 = this.getBuffName(buffType, string);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Buffs_End.toMsg().replace("%type%", string2));
            break;
        }
        if (arrayList.isEmpty()) {
            this.buffs.remove(player.getUniqueId());
            return;
        }
        this.buffs.put(player.getUniqueId(), arrayList);
    }

    public void resetBuff(UUID uUID, BuffType buffType, String string) {
        if (!this.getBuffs().containsKey(uUID)) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.getBuffs().get(uUID));
        for (Buff buff : arrayList) {
            if (buff.getType() != buffType || !buff.getValue().equalsIgnoreCase(string)) continue;
            arrayList.remove(buff);
            if (!this.buffMsg || this.plugin.getServer().getPlayer(uUID) == null) break;
            String string2 = this.getBuffName(buffType, string);
            this.plugin.getServer().getPlayer(uUID).sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Buffs_End.toMsg().replace("%type%", string2));
            break;
        }
        if (arrayList.isEmpty()) {
            this.buffs.remove(uUID);
            return;
        }
        this.buffs.put(uUID, arrayList);
    }

    public void update(UUID uUID, Buff buff) {
        ArrayList<Buff> arrayList = new ArrayList<Buff>((Collection)this.getBuffs().get(uUID));
        for (Buff buff2 : arrayList) {
            if (buff2.getType() != buff.getType()) continue;
            arrayList.remove(buff2);
            break;
        }
        arrayList.add(buff);
        this.buffs.put(uUID, arrayList);
    }

    public double getBuff(Player player, BuffType buffType, String string) {
        double d = 0.0;
        if (!this.getBuffs().containsKey(player.getUniqueId())) {
            return d;
        }
        ArrayList arrayList = new ArrayList(this.getBuffs().get(player.getUniqueId()));
        for (Buff buff : arrayList) {
            if (buff.getType() != buffType || !buff.getValue().equalsIgnoreCase(string)) continue;
            d = buff.getModifier();
            break;
        }
        return d;
    }

    private String getBuffName(BuffType buffType, String string) {
        String string2 = string;
        switch (BuffManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType()[buffType.ordinal()]) {
            case 1: {
                try {
                    ItemStat itemStat = ItemStat.valueOf(string.toUpperCase());
                    string2 = itemStat.getName();
                }
                catch (IllegalArgumentException illegalArgumentException) {}
                break;
            }
            case 2: {
                DamageType damageType = this.plugin.getCFG().getDamageTypeById(string);
                if (damageType == null) break;
                string2 = damageType.getName();
                break;
            }
            case 3: {
                ArmorType armorType = this.plugin.getCFG().getArmorTypeById(string);
                if (armorType == null) break;
                string2 = armorType.getName();
            }
        }
        return string2;
    }

    public boolean isValidBuff(BuffType buffType, String string) {
        switch (BuffManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType()[buffType.ordinal()]) {
            case 1: {
                try {
                    ItemStat.valueOf(string.toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return false;
                }
                return true;
            }
            case 2: {
                DamageType damageType = this.plugin.getCFG().getDamageTypeById(string);
                if (damageType != null) {
                    return true;
                }
                return false;
            }
            case 3: {
                ArmorType armorType = this.plugin.getCFG().getArmorTypeById(string);
                if (armorType != null) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public HashMap<UUID, List<Buff>> getBuffs() {
        return this.buffs;
    }

    public List<Buff> getPlayerBuffs(Player player) {
        if (this.buffs.containsKey(player.getUniqueId())) {
            return this.buffs.get(player.getUniqueId());
        }
        return new ArrayList<Buff>();
    }

    public List<Buff> getEntityBuffs(LivingEntity livingEntity) {
        if (this.buffs.containsKey(livingEntity.getUniqueId())) {
            return this.buffs.get(livingEntity.getUniqueId());
        }
        return new ArrayList<Buff>();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
        if (this.resetDeath) {
            Player player = playerDeathEvent.getEntity();
            for (Buff buff : this.getPlayerBuffs(player)) {
                this.resetBuff(player, buff.getType(), buff.getValue());
            }
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[BuffType.values().length];
        try {
            arrn[BuffType.DAMAGE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[BuffType.DEFENSE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[BuffType.ITEM_STAT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$modules$buffs$BuffManager$BuffType;
    }

    public class Buff {
        private BuffType type;
        private String value;
        private double modifier;
        private int time;

        public Buff(BuffType buffType, String string, double d, int n) {
            this.setType(buffType);
            this.setValue(string);
            this.setModifier(d);
            this.setTimeSec(n);
        }

        public BuffType getType() {
            return this.type;
        }

        public void setType(BuffType buffType) {
            this.type = buffType;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String string) {
            this.value = string;
        }

        public double getModifier() {
            return this.modifier;
        }

        public void setModifier(double d) {
            this.modifier = d;
        }

        public int getTimeSec() {
            return this.time;
        }

        public void setTimeSec(int n) {
            this.time = n;
        }
    }

    public static enum BuffType {
        ITEM_STAT,
        DAMAGE,
        DEFENSE;
        

        private BuffType(String string2, int n2) {
        }
    }

}

