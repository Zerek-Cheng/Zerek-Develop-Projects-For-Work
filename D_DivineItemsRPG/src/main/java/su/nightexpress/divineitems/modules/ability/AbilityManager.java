/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.ability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.AbilitiesCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.gui.GUIType;
import su.nightexpress.divineitems.modules.MainSettings;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.types.DestroyType;
import su.nightexpress.divineitems.types.SlotType;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class AbilityManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private MyConfig settingsCfg;
    private MyConfig absCfg;
    private HashMap<String, Ability> abs;
    private HashMap<Player, List<AbilityCooldown>> cds;
    private AbilitySettings as;
    private Random r;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_ABS = "DIVINE_ABILITY_ID";
    private final String NBT_KEY_ITEM_ABS = "ABILITY_";
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;

    public AbilityManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.abs = new HashMap();
        this.cds = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.absCfg = new MyConfig(this.plugin, "/modules/" + this.n, "abilities.yml");
        this.setupSettings();
        this.setupSlot();
        this.setupAbilities();
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
        return "Abilities";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new AbilitiesCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.abs.clear();
            this.cds.clear();
            this.as = null;
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setupSlot() {
        SlotType slotType = SlotType.ABILITY;
        slotType.setModule(this);
        slotType.setHeader(this.getSettings().getHeader());
        slotType.setEmpty(this.getSettings().getEmptySlot());
        slotType.setFilled(this.getSettings().getFilledSlot());
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = "Item.";
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Display"));
        List list = fileConfiguration.getStringList(String.valueOf(string) + "Lore");
        string = "Enchant.";
        int n = fileConfiguration.getInt(String.valueOf(string) + "MinSuccessChance");
        int n2 = fileConfiguration.getInt(String.valueOf(string) + "MaxSuccessChance");
        DestroyType destroyType = DestroyType.CLEAR;
        try {
            destroyType = DestroyType.valueOf(fileConfiguration.getString(String.valueOf(string) + "DestroyType"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "DestroyType", "Invalid Destroy Type!", true);
            fileConfiguration.set(String.valueOf(string) + "DestroyType", (Object)"CLEAR");
        }
        string = "Effects.";
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        String string3 = fileConfiguration.getString(String.valueOf(string) + "Failure");
        String string4 = fileConfiguration.getString(String.valueOf(string) + "Success");
        string = "Sounds.";
        boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        Sound sound = Sound.BLOCK_ANVIL_BREAK;
        try {
            sound = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string) + "Failure"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "Failure", "Invalid Sound Type!", true);
            fileConfiguration.set(String.valueOf(string) + "Failure", (Object)"BLOCK_ANVIL_BREAK");
        }
        Sound sound2 = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
        try {
            sound2 = Sound.valueOf((String)fileConfiguration.getString(String.valueOf(string) + "Success"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, String.valueOf(string) + "Success", "Invalid Sound Type!", true);
            fileConfiguration.set(String.valueOf(string) + "Success", (Object)"ENTITY_EXPERIENCE_ORB_PICKUP");
        }
        string = "Design.";
        String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Header"));
        String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "EmptySlot"));
        String string7 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "FilledSlot"));
        String string8 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "RightClick"));
        String string9 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "LeftClick"));
        String string10 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Shift"));
        String string11 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Cooldown"));
        this.as = new AbilitySettings(string2, list, n, n2, destroyType, bl, string3, string4, bl2, sound, sound2, string5, string6, string7, string8, string9, string10, string11);
        this.settingsCfg.save();
    }

    private void setupAbilities() {
        FileConfiguration fileConfiguration = this.absCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("Abilities").getKeys(false)) {
            Object object4;
            Object object22;
            Object object32;
            String string2 = "Abilities." + string + ".";
            String string3 = string.toString().toLowerCase();
            boolean bl = fileConfiguration.getBoolean(String.valueOf(string2) + "Enabled");
            if (!bl) continue;
            String string4 = fileConfiguration.getString(String.valueOf(string2) + "Material");
            String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "Desc");
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Object object32 : list) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object32));
            }
            object32 = new HashMap();
            for (Object object4 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "Variables").getKeys(false)) {
                Iterator iterator = fileConfiguration.get(String.valueOf(string2) + "Variables." + (String)object4);
                object32.put(object4, iterator);
            }
            object4 = new HashMap();
            for (Object object22 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "VariablesLvl").getKeys(false)) {
                Object object5 = fileConfiguration.get(String.valueOf(string2) + "VariablesLvl." + (String)object22);
                object4.put(object22, object5);
            }
            object22 = fileConfiguration.getStringList(String.valueOf(string2) + "Actions");
            int n = fileConfiguration.getInt(String.valueOf(string2) + "Cooldown");
            int n2 = fileConfiguration.getInt(String.valueOf(string2) + "CooldownLvl");
            int n3 = fileConfiguration.getInt(String.valueOf(string2) + "MinLevel");
            int n4 = fileConfiguration.getInt(String.valueOf(string2) + "MaxLevel");
            Ability ability = new Ability(bl, string4, string3, string5, arrayList, (HashMap<String, Object>)object32, (HashMap<String, Object>)object4, (List<String>)object22, n, n2, n3, n4);
            this.abs.put(string3, ability);
        }
    }

    public void setCooldown(Player player, String string, String string2, String string3, boolean bl, int n) {
        AbilityCooldown abilityCooldown = new AbilityCooldown(string2, string, string3, bl, System.currentTimeMillis() + (long)n * 1000L);
        List<AbilityCooldown> list = this.cds.get((Object)player);
        if (list == null) {
            list = new ArrayList<AbilityCooldown>();
        }
        list.add(abilityCooldown);
        this.cds.put(player, list);
    }

    public boolean isCooldown(Player player, String string, String string2, String string3, boolean bl) {
        if (!this.cds.containsKey((Object)player)) {
            return false;
        }
        List<AbilityCooldown> list = this.cds.get((Object)player);
        ArrayList<AbilityCooldown> arrayList = new ArrayList<AbilityCooldown>();
        for (AbilityCooldown abilityCooldown : list) {
            if (System.currentTimeMillis() >= abilityCooldown.getCd()) continue;
            arrayList.add(abilityCooldown);
        }
        if (arrayList.isEmpty()) {
            this.cds.remove((Object)player);
            return false;
        }
        this.cds.put(player, arrayList);
        for (AbilityCooldown abilityCooldown : arrayList) {
            if (!abilityCooldown.getAbilityType().equalsIgnoreCase(string2) || !abilityCooldown.getItemName().equalsIgnoreCase(string) || !abilityCooldown.getAction().equalsIgnoreCase(string3) || abilityCooldown.isShift() != bl) continue;
            return true;
        }
        return false;
    }

    public int getCooldown(Player player, String string, String string2, String string3, boolean bl) {
        List<AbilityCooldown> list = this.cds.get((Object)player);
        ArrayList<AbilityCooldown> arrayList = new ArrayList<AbilityCooldown>();
        for (AbilityCooldown abilityCooldown : list) {
            if (System.currentTimeMillis() >= abilityCooldown.getCd()) continue;
            arrayList.add(abilityCooldown);
        }
        if (arrayList.isEmpty()) {
            this.cds.remove((Object)player);
            return 0;
        }
        this.cds.put(player, arrayList);
        for (AbilityCooldown abilityCooldown : arrayList) {
            if (!abilityCooldown.getAbilityType().equalsIgnoreCase(string2) || !abilityCooldown.getItemName().equalsIgnoreCase(string) || !abilityCooldown.getAction().equalsIgnoreCase(string3) || abilityCooldown.isShift() != bl) continue;
            return Utils.transSec(abilityCooldown.getCd());
        }
        return 0;
    }

    public AbilitySettings getSettings() {
        return this.as;
    }

    public Collection<Ability> getAbilities() {
        return this.abs.values();
    }

    public List<String> getAbilityNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Ability ability : this.getAbilities()) {
            arrayList.add(ability.getIdName());
        }
        return arrayList;
    }

    public Ability getAbilityById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Ability>(this.getAbilities()).get(this.r.nextInt(this.getAbilities().size()));
        }
        return this.abs.get(string.toLowerCase());
    }

    public boolean hasAbility(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("ABILITY_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    public int getItemAbilityLevel(ItemStack itemStack, String string) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string2 : nBTItem.getKeys()) {
            String[] arrstring;
            if (!string2.startsWith("ABILITY_") || !(arrstring = nBTItem.getString(string2).split(":"))[0].equalsIgnoreCase(string)) continue;
            return Integer.parseInt(arrstring[1]);
        }
        return 0;
    }

    public int getItemAbsAmount(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n = 0;
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ABILITY_")) continue;
            ++n;
        }
        return n;
    }

    public ItemStack removeAbility(ItemStack itemStack, int n) {
        NBTItem nBTItem = new NBTItem(itemStack);
        int n2 = this.getItemAbsAmount(itemStack);
        int n3 = n - 1;
        int n4 = n2 - n3;
        int n5 = n4 - 1;
        nBTItem.removeKey("ABILITY_" + n5);
        itemStack = new ItemStack(nBTItem.getItem());
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        int n6 = 0;
        int n7 = 0;
        for (String string : itemMeta.getLore()) {
            if (string.startsWith(this.getSettings().getFilledSlot())) {
                ++n6;
            }
            if (n6 == n4) {
                arrayList.remove(n7);
                arrayList.add(n7, this.as.getEmptySlot());
                break;
            }
            ++n7;
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private void openGUI(Player player, ItemStack itemStack, ItemStack itemStack2) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.ENCHANT_ABILITY));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        gUI.getItems().get((Object)ContentType.SOURCE).setItem(itemStack2);
        gUI.getItems().get((Object)ContentType.RESULT).setItem(this.getResult(new ItemStack(itemStack), new ItemStack(itemStack2)));
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    private ItemStack insertAbility(ItemStack itemStack, ItemStack itemStack2) {
        int n;
        Object object2;
        int n2;
        String string;
        String string2;
        String string3;
        List list;
        int n3 = 0;
        list = itemStack.getItemMeta().getLore();
        NBTItem nBTItem = new NBTItem(itemStack2);
        String[] arrstring = nBTItem.getString("DIVINE_ABILITY_ID").split(":");
        string = arrstring[0];
        n = Integer.parseInt(arrstring[1]);
        n2 = Integer.parseInt(arrstring[2]);
        string3 = arrstring[3];
        string2 = arrstring[4];
        String string4 = this.getSettings().getLeftClick();
        if (string3.equalsIgnoreCase("right")) {
            string4 = this.getSettings().getRightClick();
        }
        String string5 = "";
        if (Boolean.valueOf(string2).booleanValue()) {
            string5 = String.valueOf(this.getSettings().getShiftClick()) + " ";
        }
        Ability ability = this.getAbilityById(string);
        String string6 = ability.getName().replace("%rlevel%", "").replace("%level%", "").trim();
        String string7 = ability.getName();
        if (this.hasAbility(itemStack, string)) {
            for (Object object2 : list) {
                if (!object2.contains(string6)) continue;
                n3 = list.indexOf(object2);
                list.remove(n3);
                list.add(n3, String.valueOf(this.getSettings().getFilledSlot()) + string7.replace("%level%", new StringBuilder(String.valueOf(n)).toString()).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)) + " " + string5 + string4 + " " + this.getSettings().getCD().replace("%s", new StringBuilder().append(n2).toString()));
                break;
            }
        } else {
            for (Object object2 : list) {
                String string8;
                String string9 = ChatColor.stripColor((String)object2);
                if (!string9.contains(string8 = ChatColor.stripColor((String)this.getSettings().getEmptySlot()))) continue;
                n3 = list.indexOf(object2);
                list.remove(n3);
                list.add(n3, String.valueOf(this.getSettings().getFilledSlot()) + string7.replace("%level%", new StringBuilder(String.valueOf(n)).toString()).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)) + " " + string5 + string4 + " " + this.getSettings().getCD().replace("%s", new StringBuilder().append(n2).toString()));
                break;
            }
        }
        object2 = itemStack.getItemMeta();
        object2.setLore(list);
        itemStack.setItemMeta((ItemMeta)object2);
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("ABILITY_" + this.getItemAbsAmount(itemStack), String.valueOf(string) + ":" + n + ":" + n2 + ":" + string3.toUpperCase() + ":" + string2);
        return nBTItem.getItem();
    }

    private ItemStack getResult(ItemStack itemStack, ItemStack itemStack2) {
        ItemStack itemStack3 = this.insertAbility(itemStack, itemStack2);
        ItemMeta itemMeta = itemStack3.getItemMeta();
        itemMeta.setDisplayName(Lang.Abilities_Enchanting_Result.toMsg());
        itemStack3.setItemMeta(itemMeta);
        return new ItemStack(itemStack3);
    }

    public void useAbility(Player player, ItemStack itemStack, Action action, boolean bl, EquipmentSlot equipmentSlot) {
        NBTItem nBTItem = new NBTItem(itemStack);
        for (String string : nBTItem.getKeys()) {
            if (!string.startsWith("ABILITY_")) continue;
            String[] arrstring = nBTItem.getString(string).split(":");
            String string2 = arrstring[0];
            String string3 = arrstring[3];
            boolean bl2 = Boolean.valueOf(arrstring[4]);
            if (!action.name().contains(string3) || bl != bl2) continue;
            String string4 = this.plugin.getCM().getDefaultItemName(itemStack);
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
                string4 = itemStack.getItemMeta().getDisplayName();
            }
            if (this.isCooldown(player, string4, string, string3, bl2)) {
                this.plugin.getNMS().sendActionBar(player, Lang.Abilities_Cooldown.toMsg().replace("%s", "" + this.getCooldown(player, string4, string, string3, bl2)));
                continue;
            }
            Ability ability = this.getAbilityById(string2);
            if (ability == null) continue;
            Ability ability2 = new Ability(ability);
            int n = Integer.parseInt(arrstring[1]);
            ArrayList<String> arrayList = new ArrayList<String>(ability2.getActions());
            ArrayList<String> arrayList2 = new ArrayList<String>();
            for (String string5 : arrayList) {
                for (String string6 : ability2.getVariables().keySet()) {
                    Object object = ability2.getVariables().get(string6);
                    String string7 = object.toString();
                    if (ability2.getVariablesLvl().containsKey(string6)) {
                        if (object instanceof Double) {
                            double d = Double.parseDouble(object.toString());
                            string7 = String.valueOf(Utils.round3(d += (double)(n - 1) * Double.parseDouble(ability2.getVariablesLvl().get(string6).toString())));
                        } else if (object instanceof Integer) {
                            int n2 = Integer.parseInt(object.toString());
                            string7 = String.valueOf(n2 += (n - 1) * Integer.parseInt(ability2.getVariablesLvl().get(string6).toString()));
                        } else {
                            string7 = ability2.getVariablesLvl().get(string6).toString();
                        }
                    }
                    string5 = string5.replace("%var_" + string6 + "%", string7);
                }
                arrayList2.add(string5);
            }
            ability2.setActions(arrayList2);
            if (ItemAPI.getDurability(itemStack, 0) > 0) {
                itemStack = ItemAPI.setFinalDurability(itemStack, (Entity)player, 1);
            }
            DivineItemsAPI.executeActions((Entity)player, ability2.getActions(), itemStack);
            int n3 = Integer.parseInt(arrstring[2]);
            this.setCooldown(player, string4, string, string3, bl2, n3);
        }
    }

    public boolean isAbility(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_ABILITY_ID");
    }

    public String getAbilityId(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_ABILITY_ID").split(":");
        return arrstring[0];
    }

    public int getAbilityLevel(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String[] arrstring = nBTItem.getString("DIVINE_ABILITY_ID").split(":");
        return Integer.parseInt(arrstring[1]);
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent inventoryClickEvent) {
        Object object;
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.ENCHANT_ABILITY)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.ENCHANT_ABILITY);
        if (gUI == null || !inventoryClickEvent.getInventory().getTitle().equals(gUI.getTitle())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        GUIItem gUIItem = gUI.getItems().get((Object)ContentType.ACCEPT);
        int n = inventoryClickEvent.getRawSlot();
        if (itemStack.isSimilar(gUIItem.getItem()) || ArrayUtils.contains((int[])gUIItem.getSlots(), (int)n)) {
            object = new ItemStack(gUI.getItems().get((Object)ContentType.TARGET).getItem());
            ItemStack itemStack2 = new ItemStack(gUI.getItems().get((Object)ContentType.SOURCE).getItem());
            if (!player.getInventory().contains((ItemStack)object)) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_NoItem.toMsg());
                return;
            }
            int n2 = this.r.nextInt(100);
            NBTItem nBTItem = new NBTItem(itemStack2);
            int n3 = nBTItem.getInteger("DIVINE_CHANCE");
            AbilitySettings abilitySettings = this.getSettings();
            if (n3 < n2) {
                DestroyType destroyType = abilitySettings.getDestroyType();
                switch (AbilityManager.$SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType()[destroyType.ordinal()]) {
                    case 1: {
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Failure_Item.toMsg());
                        break;
                    }
                    case 2: {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Failure_Source.toMsg());
                        break;
                    }
                    case 4: {
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Failure_Both.toMsg());
                        break;
                    }
                    case 3: {
                        Object object22;
                        player.getInventory().removeItem(new ItemStack[]{object});
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - 1);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        ItemMeta itemMeta = object.getItemMeta();
                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (Object object22 : itemMeta.getLore()) {
                            if (object22.startsWith(abilitySettings.getFilledSlot())) {
                                arrayList.add(abilitySettings.getEmptySlot());
                                continue;
                            }
                            arrayList.add((String)object22);
                        }
                        itemMeta.setLore(arrayList);
                        object.setItemMeta(itemMeta);
                        object22 = new NBTItem((ItemStack)object);
                        for (String string : object22.getKeys()) {
                            if (!string.startsWith("ABILITY_")) continue;
                            object22.removeKey(string);
                        }
                        player.getInventory().addItem(new ItemStack[]{object22.getItem()});
                        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Failure_Clear.toMsg());
                    }
                }
                player.closeInventory();
                this.plugin.getGUIManager().reset(player);
                if (abilitySettings.useSound()) {
                    player.playSound(player.getLocation(), abilitySettings.getDestroySound(), 0.5f, 0.5f);
                }
                if (abilitySettings.useEffect()) {
                    Utils.playEffect(abilitySettings.getDestroyEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
                }
                return;
            }
            player.getInventory().removeItem(new ItemStack[]{object});
            ItemStack itemStack3 = this.insertAbility((ItemStack)object, itemStack2);
            player.getInventory().addItem(new ItemStack[]{itemStack3});
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            itemStack2.setAmount(itemStack2.getAmount() - 1);
            player.getInventory().addItem(new ItemStack[]{itemStack2});
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Success.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
            if (abilitySettings.useSound()) {
                player.playSound(player.getLocation(), abilitySettings.getSuccessSound(), 0.5f, 0.5f);
            }
            if (abilitySettings.useEffect()) {
                Utils.playEffect(abilitySettings.getSuccessEffect(), 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
            }
            player.updateInventory();
        }
        if (itemStack.isSimilar((object = gUI.getItems().get((Object)ContentType.DECLINE)).getItem()) || ArrayUtils.contains((int[])object.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_Cancel.toMsg());
            player.closeInventory();
            player.updateInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getItem();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        this.useAbility(player, itemStack, playerInteractEvent.getAction(), player.isSneaking(), playerInteractEvent.getHand());
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent inventoryClickEvent) {
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
        if (!nBTItem.hasKey("DIVINE_ABILITY_ID").booleanValue()) {
            return;
        }
        String[] arrstring = nBTItem.getString("DIVINE_ABILITY_ID").split(":");
        String string = arrstring[0];
        Ability ability = this.getAbilityById(string);
        if (ability == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        if (!ItemUtils.isWeapon(itemStack2)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_InvalidType.toMsg());
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_FullInventory.toMsg());
            return;
        }
        int n = Integer.parseInt(arrstring[1]);
        if (this.hasAbility(itemStack2, string) && this.getItemAbilityLevel(itemStack2, string) >= n) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_AlreadyHave.toMsg());
            return;
        }
        String string2 = ability.getName().replace("%level%", "").trim();
        for (String string3 : itemStack2.getItemMeta().getLore()) {
            String string4;
            String string5 = ChatColor.stripColor((String)string3);
            if (!string5.contains(string4 = ChatColor.stripColor((String)this.getSettings().getEmptySlot())) && !string5.contains(string2)) continue;
            player.getInventory().addItem(new ItemStack[]{itemStack});
            inventoryClickEvent.setCursor(null);
            inventoryClickEvent.setCancelled(true);
            this.openGUI(player, itemStack2, itemStack);
            return;
        }
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Abilities_Enchanting_NoSlots.toMsg());
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[DestroyType.values().length];
        try {
            arrn[DestroyType.BOTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.CLEAR.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.ITEM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[DestroyType.SOURCE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$types$DestroyType;
    }

    public class Ability {
        private boolean enabled;
        private String material;
        private String name;
        private String display;
        private List<String> desc;
        private HashMap<String, Object> vars;
        private HashMap<String, Object> vars_lvl;
        private List<String> actions;
        private int cd;
        private int cd_lvl;
        private int min_lvl;
        private int max_lvl;

        public Ability(boolean bl, String string, String string2, String string3, List<String> list, HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2, List<String> list2, int n, int n2, int n3, int n4) {
            this.setEnabled(bl);
            this.setMaterial(string);
            this.setIdName(string2);
            this.setName(string3);
            this.setDesc(list);
            this.setVariables(hashMap);
            this.setVariablesLvl(hashMap2);
            this.setActions(list2);
            this.setCooldown(n);
            this.setCooldownLvl(n2);
            this.setMinLevel(n3);
            this.setMaxLevel(n4);
        }

        public Ability(Ability ability) {
            this.setEnabled(ability.isEnabled());
            this.setMaterial(ability.getMaterial());
            this.setIdName(ability.getIdName());
            this.setName(ability.getName());
            this.setDesc(ability.getDesc());
            this.setVariables(new HashMap<String, Object>(ability.getVariables()));
            this.setVariablesLvl(new HashMap<String, Object>(ability.getVariablesLvl()));
            this.setActions(new ArrayList<String>(ability.getActions()));
            this.setCooldown(ability.getCooldown());
            this.setCooldownLvl(ability.getCooldownLvl());
            this.setMinLevel(ability.getMinLevel());
            this.setMaxLevel(ability.getMaxLevel());
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public String getIdName() {
            return this.name;
        }

        public void setIdName(String string) {
            this.name = string;
        }

        public String getMaterial() {
            return this.material;
        }

        public void setMaterial(String string) {
            this.material = string;
        }

        public String getName() {
            return this.display;
        }

        public void setName(String string) {
            this.display = string;
        }

        public List<String> getDesc() {
            return this.desc;
        }

        public void setDesc(List<String> list) {
            this.desc = list;
        }

        public HashMap<String, Object> getVariables() {
            return this.vars;
        }

        public void setVariables(HashMap<String, Object> hashMap) {
            this.vars = hashMap;
        }

        public HashMap<String, Object> getVariablesLvl() {
            return this.vars_lvl;
        }

        public void setVariablesLvl(HashMap<String, Object> hashMap) {
            this.vars_lvl = hashMap;
        }

        public List<String> getActions() {
            return this.actions;
        }

        public void setActions(List<String> list) {
            this.actions = list;
        }

        public int getCooldown() {
            return this.cd;
        }

        public void setCooldown(int n) {
            this.cd = n;
        }

        public int getCooldownLvl() {
            return this.cd_lvl;
        }

        public void setCooldownLvl(int n) {
            this.cd_lvl = n;
        }

        public int getMaxLevel() {
            return this.max_lvl;
        }

        public void setMaxLevel(int n) {
            this.max_lvl = n;
        }

        public int getMinLevel() {
            return this.min_lvl;
        }

        public void setMinLevel(int n) {
            this.min_lvl = n;
        }

        public ItemStack create(int n) {
            if (n == -1) {
                n = Utils.randInt(this.getMinLevel(), this.getMaxLevel());
            } else if (n > this.getMaxLevel()) {
                n = this.getMaxLevel();
            } else if (n < 1) {
                n = this.getMinLevel();
            }
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.getIdName());
            ItemMeta itemMeta = itemStack.getItemMeta();
            int n2 = this.getCooldown() + this.getCooldownLvl() * (n - 1);
            String string = this.getName().replace("%level%", String.valueOf(n)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n));
            String string2 = AbilityManager.this.getSettings().getDisplay().replace("%s", string);
            int n3 = n + 1;
            int n4 = Utils.randInt(AbilityManager.this.getSettings().getMinChance(), AbilityManager.this.getSettings().getMaxChance());
            int n5 = 100 - n4;
            String string3 = "Right";
            String string4 = "false";
            if (AbilityManager.this.r.nextInt(100) < 50) {
                string3 = "Left";
            }
            if (AbilityManager.this.r.nextInt(100) < 50) {
                string4 = "true";
            }
            List<String> list = AbilityManager.this.getSettings().getLore();
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String object22 : list) {
                if (object22.equals("%desc%")) {
                    for (String string5 : this.getDesc()) {
                        arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string5.replace("%shift%", Lang.getCustom("Other." + string4)).replace("%key%", Lang.getCustom("Other." + string3)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n3)).replace("%level%", String.valueOf(n3))));
                    }
                    continue;
                }
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object22.replace("%shift%", Lang.getCustom("Other." + string4)).replace("%key%", Lang.getCustom("Other." + string3)).replace("%d%", String.valueOf(n5)).replace("%s%", String.valueOf(n4)).replace("%rlevel%", Utils.IntegerToRomanNumeral(n)).replace("%level%", String.valueOf(n))));
            }
            ArrayList arrayList2 = new ArrayList(arrayList);
            ArrayList arrayList22 = new ArrayList();
            for (String string6 : arrayList2) {
                void var17_21;
                for (String string7 : this.getVariables().keySet()) {
                    Object object = this.getVariables().get(string7);
                    String string8 = object.toString();
                    if (this.getVariablesLvl().containsKey(string7)) {
                        if (object instanceof Double) {
                            double d = Double.parseDouble(object.toString());
                            string8 = String.valueOf(Utils.round3(d += (double)(n - 1) * Double.parseDouble(this.getVariablesLvl().get(string7).toString())));
                        } else if (object instanceof Integer) {
                            int n6 = Integer.parseInt(object.toString());
                            string8 = String.valueOf(n6 += (n - 1) * Integer.parseInt(this.getVariablesLvl().get(string7).toString()));
                        } else {
                            string8 = this.getVariablesLvl().get(string7).toString();
                        }
                    }
                    String string9 = var17_21.replace("%var_" + string7 + "%", string8);
                }
                arrayList22.add(ChatColor.translateAlternateColorCodes((char)'&', (String)var17_21));
            }
            itemMeta.setDisplayName(string2);
            itemMeta.setLore((List)arrayList22);
            itemMeta.spigot().setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.values());
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_ABILITY_ID", String.valueOf(this.getIdName()) + ":" + n + ":" + n2 + ":" + string3 + ":" + string4);
            nBTItem.setInteger("DIVINE_CHANCE", n4);
            return nBTItem.getItem();
        }
    }

    public class AbilityCooldown {
        private String type;
        private String item_name;
        private String act;
        private boolean shift;
        private long cd;

        public AbilityCooldown(String string, String string2, String string3, boolean bl, long l) {
            this.setAbilityType(string);
            this.setItemName(string2);
            this.setAction(string3);
            this.setShift(bl);
            this.setCd(l);
        }

        public String getAbilityType() {
            return this.type;
        }

        public void setAbilityType(String string) {
            this.type = string;
        }

        public String getItemName() {
            return this.item_name;
        }

        public void setItemName(String string) {
            this.item_name = string;
        }

        public String getAction() {
            return this.act;
        }

        public void setAction(String string) {
            this.act = string;
        }

        public boolean isShift() {
            return this.shift;
        }

        public void setShift(boolean bl) {
            this.shift = bl;
        }

        public long getCd() {
            return this.cd;
        }

        public void setCd(long l) {
            this.cd = l;
        }
    }

    public class AbilitySettings
    extends MainSettings {
        private String right;
        private String left;
        private String shift;
        private String cd;

        public AbilitySettings(String string, List<String> list, int n, int n2, DestroyType destroyType, boolean bl, String string2, String string3, boolean bl2, Sound sound, Sound sound2, String string4, String string5, String string6, String string7, String string8, String string9, String string10) {
            super(string, list, n, n2, destroyType, bl, string2, string3, bl2, sound, sound2, string4, string5, string6);
            this.setRightClick(string7);
            this.setLeftClick(string8);
            this.setShiftClick(string9);
            this.setCD(string10);
        }

        public String getRightClick() {
            return this.right;
        }

        public void setRightClick(String string) {
            this.right = string;
        }

        public String getLeftClick() {
            return this.left;
        }

        public void setLeftClick(String string) {
            this.left = string;
        }

        public String getShiftClick() {
            return this.shift;
        }

        public void setShiftClick(String string) {
            this.shift = string;
        }

        public String getCD() {
            return this.cd;
        }

        public void setCD(String string) {
            this.cd = string;
        }
    }

}

