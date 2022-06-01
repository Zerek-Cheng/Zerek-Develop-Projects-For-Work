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
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.magicdust;

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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.MagicDustCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.runes.RuneManager;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class MagicDustManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private HashMap<String, MagicDust> mds;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_DUST = "DIVINE_DUST_ID";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType;

    public MagicDustManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.mds = new HashMap();
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
        return "Magic Dust";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new MagicDustCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.mds.clear();
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
        for (String string : fileConfiguration.getConfigurationSection("MagicDusts").getKeys(false)) {
            String string2 = string.toString().toLowerCase();
            String string3 = "MagicDusts." + string.toString() + ".";
            String string4 = fileConfiguration.getString(String.valueOf(string3) + "Material");
            String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Display"));
            List list = fileConfiguration.getStringList(String.valueOf(string3) + "Lore");
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string3) + "Enchanted");
            int n = fileConfiguration.getInt(String.valueOf(string3) + "MaxLevel");
            double d = fileConfiguration.getDouble(String.valueOf(string3) + "MaxSuccess");
            DustType dustType = DustType.valueOf(fileConfiguration.getString(String.valueOf(string3) + "Type").toUpperCase());
            boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string3) + "Values.Random");
            String string6 = fileConfiguration.getString(String.valueOf(string3) + "Values.RandomRange");
            double d2 = fileConfiguration.getDouble(String.valueOf(string3) + "Values.Default");
            double d3 = fileConfiguration.getDouble(String.valueOf(string3) + "Values.PerLevel");
            String string7 = fileConfiguration.getString(String.valueOf(string3) + "OnUse.Effect");
            Sound sound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
            try {
                sound = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string3) + "OnUse.Sound"));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                ErrorLog.sendError(this, String.valueOf(string3) + "OnUse.Sound", "Invalid Sound Type!", true);
                fileConfiguration.set(String.valueOf(string3) + "OnUse.Sound", (Object)"ENTITY_EXPERIENCE_ORB_PICKUP");
            }
            MagicDust magicDust = new MagicDust(string2, string4, string5, list, bl, n, d, dustType, bl2, string6, d2, d3, sound, string7);
            this.mds.put(string2, magicDust);
        }
        this.settingsCfg.save();
    }

    public Collection<MagicDust> getDusts() {
        return this.mds.values();
    }

    public List<String> getDustNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (MagicDust magicDust : this.getDusts()) {
            arrayList.add(magicDust.getId());
        }
        return arrayList;
    }

    public MagicDust getDustById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<MagicDust>(this.getDusts()).get(this.r.nextInt(this.getDusts().size()));
        }
        return this.mds.get(string.toLowerCase());
    }

    public boolean isMagicDust(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_DUST_ID");
    }

    public String getDustId(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String string = nBTItem.getString("DIVINE_DUST_ID").split(":")[0];
        return string;
    }

    public int getDustMod(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n = Integer.parseInt(nBTItem.getString("DIVINE_DUST_ID").split(":")[1]);
        return n;
    }

    private List<String> getTypeLore(DustType dustType) {
        switch (MagicDustManager.$SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType()[dustType.ordinal()]) {
            case 1: {
                return new ArrayList<String>(this.plugin.getMM().getGemManager().getSettings().getLore());
            }
            case 2: {
                return new ArrayList<String>(this.plugin.getMM().getEnchantManager().getSettings().getLore());
            }
            case 3: {
                return new ArrayList<String>(this.plugin.getMM().getRuneManager().getSettings().getLore());
            }
            case 4: {
                return new ArrayList<String>(this.plugin.getMM().getAbilityManager().getSettings().getLore());
            }
            case 5: {
                return new ArrayList<String>();
            }
        }
        return new ArrayList<String>();
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
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_DUST_ID").booleanValue()) {
            return;
        }
        String[] arrstring = nBTItem.getString("DIVINE_DUST_ID").split(":");
        String string = arrstring[0];
        int n = Math.max(0, Integer.parseInt(arrstring[1]));
        NBTItem nBTItem2 = new NBTItem(itemStack2);
        if (!nBTItem2.hasKey("DIVINE_CHANCE").booleanValue()) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        int n2 = Math.max(0, nBTItem2.getInteger("DIVINE_CHANCE"));
        int n3 = 100 - n2;
        if (n2 >= 100) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_Maximum.toMsg().replace("%s", new StringBuilder(String.valueOf(n2)).toString()));
            return;
        }
        if (itemStack2.getAmount() > 1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_NoStack.toMsg());
            return;
        }
        MagicDust magicDust = this.getDustById(string);
        DustType dustType = magicDust.getType();
        List<Object> list = new ArrayList();
        if (this.plugin.getMM().getGemManager().isGem(itemStack2)) {
            if (dustType != DustType.GEM && dustType != DustType.ANY) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_WrongItem.toMsg());
                return;
            }
            list = this.getTypeLore(DustType.GEM);
        } else if (this.plugin.getMM().getEnchantManager().isEnchant(itemStack2)) {
            if (dustType != DustType.ENCHANT && dustType != DustType.ANY) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_WrongItem.toMsg());
                return;
            }
            list = this.getTypeLore(DustType.ENCHANT);
        } else if (this.plugin.getMM().getRuneManager().isRune(itemStack2)) {
            if (dustType != DustType.RUNE && dustType != DustType.ANY) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_WrongItem.toMsg());
                return;
            }
            list = this.getTypeLore(DustType.RUNE);
        } else if (this.plugin.getMM().getAbilityManager().isAbility(itemStack2)) {
            if (dustType != DustType.ABILITY && dustType != DustType.ANY) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_WrongItem.toMsg());
                return;
            }
            list = this.getTypeLore(DustType.ABILITY);
        }
        if ((double)n2 >= magicDust.getMaxSuccess()) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_Maximum.toMsg().replace("%s", new StringBuilder(String.valueOf(n2)).toString()));
            return;
        }
        int n4 = 0;
        int n5 = 0;
        String string2 = "";
        String string3 = "";
        for (String string4 : list) {
            if (string4.contains("%s%")) {
                string2 = string4;
                n4 = list.indexOf(string4);
            }
            if (!string4.contains("%d%")) continue;
            string3 = string4;
            n5 = list.indexOf(string4);
        }
        int n6 = n2 + n;
        int n7 = n3 - n;
        ItemMeta itemMeta = itemStack2.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        if (n4 == n5) {
            arrayList.remove(n4);
            string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)string2.replace("%s%", String.valueOf(n6)).replace("%d%", String.valueOf(n7)));
            arrayList.add(n4, string2);
        } else {
            arrayList.remove(n4);
            string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)string2.replace("%s%", String.valueOf(n6)));
            arrayList.add(n4, string2);
            arrayList.remove(n5);
            string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)string3.replace("%d%", String.valueOf(n7)));
            arrayList.add(n5, string3);
        }
        itemMeta.setLore(arrayList);
        itemStack2.setItemMeta(itemMeta);
        NBTItem nBTItem3 = new NBTItem(itemStack2);
        nBTItem3.setInteger("DIVINE_CHANCE", n6);
        player.getInventory().removeItem(new ItemStack[]{itemStack2});
        player.getInventory().addItem(new ItemStack[]{nBTItem3.getItem()});
        if (itemStack.getAmount() == 1) {
            inventoryClickEvent.setCursor(null);
        } else {
            itemStack.setAmount(itemStack.getAmount() - 1);
            inventoryClickEvent.setCursor(itemStack);
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.MagicDust_Done.toMsg());
        player.playSound(player.getLocation(), magicDust.getSound(), 0.5f, 0.5f);
        Utils.playEffect(magicDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.85, 0.0));
        Utils.playEffect(magicDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.65, 0.0));
        Utils.playEffect(magicDust.getEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.0, 5, player.getLocation().add(0.0, 0.25, 0.0));
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[DustType.values().length];
        try {
            arrn[DustType.ABILITY.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DustType.ANY.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DustType.ENCHANT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DustType.GEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DustType.RUNE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$modules$magicdust$MagicDustManager$DustType;
    }

    public static enum DustType {
        GEM,
        ENCHANT,
        RUNE,
        ABILITY,
        ANY;
        

        private DustType(String string2, int n2) {
        }
    }

    public class MagicDust {
        private String id;
        private String mat;
        private String name;
        private List<String> lore;
        private boolean ench;
        private int max_lvl;
        private double max_suc;
        private DustType type;
        private boolean v_random;
        private String v_range;
        private double v_default;
        private double v_lvl;
        private Sound sound;
        private String effect;

        public MagicDust(String string, String string2, String string3, List<String> list, boolean bl, int n, double d, DustType dustType, boolean bl2, String string4, double d2, double d3, Sound sound, String string5) {
            this.setId(string);
            this.setMaterial(string2);
            this.setDisplay(string3);
            this.setLore(list);
            this.setEnchanted(bl);
            this.setMaxLevel(n);
            this.setMaxSuccess(d);
            this.setType(dustType);
            this.setValuesRandom(bl2);
            this.setValuesRandomRange(string4);
            this.setValuesDefault(d2);
            this.setValuesPerLevel(d3);
            this.setSound(sound);
            this.setEffect(string5);
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

        public int getMaxLevel() {
            return this.max_lvl;
        }

        public void setMaxLevel(int n) {
            this.max_lvl = n;
        }

        public double getMaxSuccess() {
            return this.max_suc;
        }

        public void setMaxSuccess(double d) {
            this.max_suc = d;
        }

        public DustType getType() {
            return this.type;
        }

        public void setType(DustType dustType) {
            this.type = dustType;
        }

        public boolean isValuesRandom() {
            return this.v_random;
        }

        public void setValuesRandom(boolean bl) {
            this.v_random = bl;
        }

        public String getValuesRandomRange() {
            return this.v_range;
        }

        public void setValuesRandomRange(String string) {
            this.v_range = string;
        }

        public double getValuesDefault() {
            return this.v_default;
        }

        public void setValuesDefault(double d) {
            this.v_default = d;
        }

        public double getValuesPerLevel() {
            return this.v_lvl;
        }

        public void setValuesPerLevel(double d) {
            this.v_lvl = d;
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

        public ItemStack create(int n) {
            String string;
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.id);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (n == -1) {
                n = Utils.randInt(1, this.getMaxLevel());
            } else {
                if (n < 1 || n > 3999) {
                    n = 1;
                }
                if (n > this.getMaxLevel()) {
                    n = this.getMaxLevel();
                }
            }
            double d = 0.0;
            if (this.isValuesRandom()) {
                string = this.getValuesRandomRange();
                double d2 = Double.parseDouble(string.split(":")[0]);
                double d3 = Double.parseDouble(string.split(":")[1]);
                d = Utils.getRandDouble(d2, d3);
            } else {
                d = this.getValuesDefault();
            }
            d += this.getValuesPerLevel() * (double)(n - 1);
            string = this.getDisplay().replace("%s", Utils.IntegerToRomanNumeral(n));
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String object2 : this.getLore()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2.replace("%level%", Utils.IntegerToRomanNumeral(n)).replace("%value%", "" + (int)d)));
            }
            itemMeta.setDisplayName(string);
            itemMeta.setLore(arrayList);
            itemMeta.spigot().setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.values());
            itemStack.setItemMeta(itemMeta);
            if (this.isEnchanted()) {
                itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_DUST_ID", String.valueOf(this.getId()) + ":" + (int)d);
            return new ItemStack(nBTItem.getItem());
        }
    }

}

