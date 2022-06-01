/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.abyssdust;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.AbyssDustCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.runes.RuneManager;
import su.nightexpress.divineitems.modules.soulbound.SoulboundManager;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class AbyssDustManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private HashMap<String, AbyssDust> dusts;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_DUST = "DIVINE_ADUST";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType;

    public AbyssDustManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.dusts = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.setup();
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public boolean isResolvable() {
        return true;
    }

    @Override
    public String name() {
        return "Abyss Dust";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new AbyssDustCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.dusts.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void setup() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("AbyssDusts").getKeys(false)) {
            String string2 = "AbyssDusts." + string.toString() + ".";
            String string3 = string.toString().toLowerCase();
            String string4 = fileConfiguration.getString(String.valueOf(string2) + "Material");
            String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Display"));
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "Lore");
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enchanted");
            AbyssType abyssType = AbyssType.valueOf(fileConfiguration.getString(String.valueOf(string2) + "Type").toUpperCase());
            int n = fileConfiguration.getInt(String.valueOf(string2) + "Amount");
            String string6 = fileConfiguration.getString(String.valueOf(string2) + "OnUse.Effect");
            Sound sound = Sound.ITEM_FIRECHARGE_USE;
            try {
                sound = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string2) + "OnUse.Sound"));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ErrorLog.sendError(this, String.valueOf(string2) + "OnUse.Sound", "Invalid Sound Type!", true);
                fileConfiguration.set(String.valueOf(string2) + "OnUse.Sound", (Object)"ITEM_FIRECHARGE_USE");
            }
            AbyssDust abyssDust = new AbyssDust(string3, string4, string5, list, bl, abyssType, n, sound, string6);
            this.dusts.put(string3, abyssDust);
        }
        this.settingsCfg.save();
    }

    public Collection<AbyssDust> getDusts() {
        return this.dusts.values();
    }

    public List<String> getDustNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (AbyssDust abyssDust : this.getDusts()) {
            arrayList.add(abyssDust.getId());
        }
        return arrayList;
    }

    public AbyssDust getDustById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<AbyssDust>(this.getDusts()).get(this.r.nextInt(this.getDusts().size()));
        }
        return this.dusts.get(string.toLowerCase());
    }

    public boolean isAbyssDust(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_ADUST");
    }

    public String getDustId(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("DIVINE_ADUST");
    }

    public ItemStack applyDust(ItemStack itemStack, AbyssType abyssType, int n) {
        switch (AbyssDustManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType()[abyssType.ordinal()]) {
            case 1: {
                return this.plugin.getMM().getSoulboundManager().removeSoulbound(itemStack);
            }
            case 2: {
                return this.plugin.getMM().getGemManager().removeGem(itemStack, n);
            }
            case 4: {
                return this.plugin.getMM().getEnchantManager().removeEnchant(itemStack, n);
            }
            case 3: {
                return this.plugin.getMM().getRuneManager().removeRune(itemStack, n);
            }
            case 5: {
                return this.plugin.getMM().getAbilityManager().removeAbility(itemStack, n);
            }
        }
        return itemStack;
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCursor();
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || itemStack2 == null) {
            return;
        }
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.ARMOR || inventoryClickEvent.getSlot() == 40) {
            return;
        }
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!itemStack2.hasItemMeta() || !itemStack2.getItemMeta().hasLore()) {
            return;
        }
        if (ItemAPI.hasOwner(itemStack2) && !ItemAPI.isOwner(itemStack2, player)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_NotOwner.toMsg());
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_ADUST").booleanValue()) {
            return;
        }
        String string = nBTItem.getString("DIVINE_ADUST");
        AbyssDust abyssDust = this.getDustById(string);
        if (abyssDust == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        AbyssType abyssType = abyssDust.getType();
        inventoryClickEvent.setCancelled(true);
        String string2 = "";
        switch (AbyssDustManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType()[abyssType.ordinal()]) {
            case 2: {
                if (this.plugin.getMM().getGemManager().getItemGemsAmount(itemStack2) < 1) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.AbyssDust_NoGems.toMsg());
                    return;
                }
                string2 = Lang.AbyssDust_Applied_Gem.toMsg();
                break;
            }
            case 4: {
                if (this.plugin.getMM().getEnchantManager().getItemEnchantAmount(itemStack2) < 1) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.AbyssDust_NoEnchants.toMsg());
                    return;
                }
                string2 = Lang.AbyssDust_Applied_Enchant.toMsg();
                break;
            }
            case 3: {
                if (this.plugin.getMM().getRuneManager().getItemRunesAmount(itemStack2) < 1) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.AbyssDust_NoRunes.toMsg());
                    return;
                }
                string2 = Lang.AbyssDust_Applied_Rune.toMsg();
                break;
            }
            case 5: {
                if (this.plugin.getMM().getAbilityManager().getItemAbsAmount(itemStack2) < 1) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.AbyssDust_NoAbilities.toMsg());
                    return;
                }
                string2 = Lang.AbyssDust_Applied_Ability.toMsg();
                break;
            }
            case 1: {
                if (!this.plugin.getMM().getSoulboundManager().hasSoulbound(itemStack2)) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.AbyssDust_NoSoulbound.toMsg());
                    return;
                }
                string2 = Lang.AbyssDust_Applied_Soul.toMsg();
            }
        }
        inventoryClickEvent.setCurrentItem(this.applyDust(itemStack2, abyssType, abyssDust.getAmount()));
        if (itemStack.getAmount() == 1) {
            inventoryClickEvent.setCursor(null);
        } else {
            itemStack.setAmount(itemStack.getAmount() - 1);
            inventoryClickEvent.setCursor(itemStack);
        }
        String string3 = this.plugin.getCM().getDefaultItemName(itemStack2);
        if (itemStack2.hasItemMeta() && itemStack2.getItemMeta().hasDisplayName()) {
            string3 = itemStack2.getItemMeta().getDisplayName();
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + string2.replace("%s", new StringBuilder(String.valueOf(abyssDust.getAmount())).toString()).replace("%item%", string3));
        player.playSound(player.getLocation(), abyssDust.getSound(), 0.6f, 0.6f);
        Utils.playEffect(abyssDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.85, 0.0));
        Utils.playEffect(abyssDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.65, 0.0));
        Utils.playEffect(abyssDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.25, 0.0));
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[AbyssType.values().length];
        try {
            arrn[AbyssType.ABILITY.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AbyssType.ENCHANT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AbyssType.GEM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AbyssType.RUNE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[AbyssType.SOULBOUND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$modules$abyssdust$AbyssDustManager$AbyssType;
    }

    public class AbyssDust {
        private String id;
        private String mat;
        private String name;
        private List<String> lore;
        private boolean ench;
        private AbyssType type;
        private int amount;
        private Sound sound;
        private String effect;

        public AbyssDust(String string, String string2, String string3, List<String> list, boolean bl, AbyssType abyssType, int n, Sound sound, String string4) {
            this.setId(string);
            this.setMaterial(string2);
            this.setDisplay(string3);
            this.setLore(list);
            this.setEnchanted(bl);
            this.setType(abyssType);
            this.setAmount(n);
            this.setSound(sound);
            this.setEffect(string4);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public String getMaterial() {
            return this.mat;
        }

        public void setMaterial(String string) {
            this.mat = string;
        }

        public String getDisplay() {
            return this.name;
        }

        public void setDisplay(String string) {
            this.name = string;
        }

        public List<String> getLore() {
            return this.lore;
        }

        public void setLore(List<String> list) {
            this.lore = list;
        }

        public boolean isEnchanted() {
            return this.ench;
        }

        public void setEnchanted(boolean bl) {
            this.ench = bl;
        }

        public AbyssType getType() {
            return this.type;
        }

        public void setType(AbyssType abyssType) {
            this.type = abyssType;
        }

        public int getAmount() {
            return this.amount;
        }

        public void setAmount(int n) {
            this.amount = n;
        }

        public Sound getSound() {
            return this.sound;
        }

        public void setSound(Sound sound) {
            this.sound = sound;
        }

        public String getEffect() {
            return this.effect;
        }

        public void setEffect(String string) {
            this.effect = string;
        }

        public ItemStack create() {
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.id);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String object2 : this.getLore()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2));
            }
            itemMeta.setDisplayName(this.getDisplay());
            itemMeta.setLore(arrayList);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            if (this.isEnchanted()) {
                itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_ADUST", this.getId());
            return new ItemStack(nBTItem.getItem());
        }
    }

    public static enum AbyssType {
        SOULBOUND,
        GEM,
        RUNE,
        ENCHANT,
        ABILITY;
        

        private AbyssType(String string2, int n2) {
        }
    }

}

