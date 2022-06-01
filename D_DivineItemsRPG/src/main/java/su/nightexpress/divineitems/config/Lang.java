/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.text.WordUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.EntityType
 */
package su.nightexpress.divineitems.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import su.nightexpress.divineitems.config.MyConfig;

public enum Lang {
    Prefix("&aDivineItems &8\u203a\u203a &7"),
    Help_Main("&8&m-----------&8&l[ &aDivine Items RPG &8&l]&8&m-----------\n&2> &a/di gems &7- Gems module.\n&2> &a/di enchants &7- Enchants module.\n&2> &a/di runes &7- Runes module.\n&2> &a/di abilities &7- Abilities module.\n&2> &a/di abyssdust &7- Abyss dust module.\n&2> &a/di magicdust &7- Magic dust module.\n&2> &a/di tiers &7- Tiers module.\n&2> &a/di repair &7- Repair module.\n&2> &a/di identify &7- Identify module.\n&2> &a/di buffs &7- Buffs module.\n&2> &a/di customitems &7- Custom items module.\n&2> &a/di soulbound &7- Soulbound module.\n&2> &a/di scrolls &7- Scrolls module.\n&2> &a/di sets &7- Item sets module.\n&2> &a/di resolve &7- Open resolve GUI.\n&2> &a/di modify &7- Modify the item in hand.\n&2> &a/di reload full &7- Reload the plugin and modules.\n&2> &a/di reload cfg &7- Reload the configs only.\n&2> &a/di info &7- Plugin info."),
    Help_Gems("&8&m-----------&8&l[ &aDivine Items RPG &7- &aGems &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di gems get <ID> <Level> <Amount> &7- Get a specified gem.\n&2> &a/di gems give <Player> <ID> <Level> <Amount> &7- Give a gem to player.\n&2> &a/di gems drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop a gem in the world.\n&2> &a/di gems list &7- List of all gems."),
    Help_Enchants("&8&m-----------&8&l[ &aDivine Items RPG &7- &aEnchants &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di enchants get <ID> <Level> <Amount> &7- Get an specified enchant.\n&2> &a/di enchants give <Player> <ID> <Level> <Amount> &7- Give an enchant to player.\n&2> &a/di enchants drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop an enchant in the world.\n&2> &a/di enchants list &7- List of all enchants."),
    Help_Runes("&8&m-----------&8&l[ &aDivine Items RPG &7- &aRunes &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di runes get <ID> <Level> <Amount> &7- Get an specified rune.\n&2> &a/di runes give <Player> <ID> <Level> <Amount> &7- Give an rune to player.\n&2> &a/di runes drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop an rune in the world.\n&2> &a/di runes list &7- List of all runes."),
    Help_Abilities("&8&m-----------&8&l[ &aDivine Items RPG &7- &aAbilities &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di abilities get <ID> <Level> <Amount> &7- Get an specified ability.\n&2> &a/di abilities give <Player> <ID> <Level> <Amount> &7- Give an ability to player.\n&2> &a/di abilities drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop an ability in the world.\n&2> &a/di abilities list &7- List of all abilities."),
    Help_AbyssDust("&8&m-----------&8&l[ &aDivine Items RPG &7- &aAbyss Dust &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di abyssdust get <ID> <Amount> &7- Get a specified dust.\n&2> &a/di abyssdust give <Player> <ID> <Amount> &7- Give a dust to player.\n&2> &a/di abyssdust drop <World> <X> <Y> <Z> <ID> <Amount> &7- Drop a dust in the world.\n&2> &a/di abyssdust list &7- List of all dusts."),
    Help_Consumables("&8&m-----------&8&l[ &aDivine Items RPG &7- &aConsumables &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di consumables get <ID> <Amount> &7- Get a specified consumable.\n&2> &a/di consumables give <Player> <ID> <Amount> &7- Give a consumable to player.\n&2> &a/di consumables drop <World> <X> <Y> <Z> <ID> <Amount> &7- Drop a consumable in the world.\n&2> &a/di consumables list &7- List of all consumables."),
    Help_MagicDust("&8&m-----------&8&l[ &aDivine Items RPG &7- &aMagic Dust &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di magicdust get <ID> <Level> <Amount> &7- Get a specified dust.\n&2> &a/di magicdust give <Player> <ID> <Level> <Amount> &7- Give a dust to player.\n&2> &a/di magicdust drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop a dust in the world.\n&2> &a/di magicdust list &7- List of all dusts."),
    Help_Tiers("&8&m-----------&8&l[ &aDivine Items RPG &7- &aTiers &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di tiers get <ID> <Level> <Amount> &7- Get an item with specified tier.\n&2> &a/di tiers give <Player> <ID> <Level> <Amount> &7- Give an item to player.\n&2> &a/di tiers drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop an item in the world.\n&2> &a/di tiers list &7- List of all tiers."),
    Help_Repair("&8&m-----------&8&l[ &aDivine Items RPG &7- &aRepair &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di repair get <Level> <Amount> &7- Get a repair gem.\n&2> &a/di repair give <Player> <Level> <Amount> &7- Give a repair gem to player.\n&2> &a/di repair drop <World> <X> <Y> <Z> <Level> <Amount> &7- Drop a repair gem in the world.\n&2> &a/di repair item [Amount] &7- Repair an item in your hand."),
    Help_Identify("&8&m-----------&8&l[ &aDivine Items RPG &7- &aIdentify &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di identify tome get <ID> <Amount> &7- Get an identify tome.\n&2> &a/di identify tome give <Player> <ID> <Amount> &7- Give an identify tome to player.\n&2> &a/di identify tome drop <World> <X> <Y> <Z> <ID> <Amount> &7- Drop an identify tome in the world.\n&2> &a/di identify tome list &7- List of all identify tomes.\n&2> &a/di identify item get <ID> <Level> <Amount> &7- Get an unidentified item.\n&2> &a/di identify item give <Player> <ID> <Level> <Amount> &7- Give an unidentified item to player.\n&2> &a/di identify item drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop an unidentified item in the world.\n&2> &a/di identify item list &7- List of all unidentified items.\n&2> &a/di identify force &7- Force identify the item in your hand."),
    Help_Buffs("&8&m-----------&8&l[ &aDivine Items RPG &7- &aBuffs &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di buffs add <Player> <BuffType> <Name> <Amount> <Duration> &7- Adds a buff to a player.\n&2> &a/di buffs reset <Player> <BuffType> <Name> &7- Reset a buff from a player.\n&2> &a/di buffs resetall &7- Reset all buffs from a player."),
    Help_CustomItems("&8&m-----------&8&l[ &aDivine Items RPG &7- &aCustom Items &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di customitems get <ID> <Amount> &7- Get a specified custom item.\n&2> &a/di customitems give <Player> <ID> <Amount> &7- Give a custom item to a player.\n&2> &a/di customitems drop <World> <X> <Y> <Z> <ID> <Amount> &7- Drop a custom item in the world.\n&2> &a/di customitems save <Name/Path> &7- Saves a custom item in your hand.\n&2> &a/di customitems delete <Name/Path> &7- Deletes a custom item.\n&2> &a>/di customitems list &7- List of all custom items."),
    Help_Soulbound("&8&m-----------&8&l[ &aDivine Items RPG &7- &aSoulbound &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di soulbound set <True/False> [Position] &7- Marks/Unmarks an item with soulbound.\n&2> &a/di soulbound untrade <True/False> [Position] &7- Marks/Unmarks the item as untradable."),
    Help_Scrolls("&8&m-----------&8&l[ &aDivine Items RPG &7- &aScrolls &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di scrolls get <ID> <Level> <Amount> &7- Get a specified scroll.\n&2> &a/di scrolls give <Player> <ID> <Level> <Amount> &7- Give a scroll to player.\n&2> &a/di scrolls drop <World> <X> <Y> <Z> <ID> <Level> <Amount> &7- Drop a scroll in the world.\n&2> &a/di scrolls list &7- List of all scrolls."),
    Help_Modify("&8&m-----------&8&l[ &aDivine Items RPG &7- &aModify &8&l]&8&m-----------\n&7&l&nCommand List:\n&2> &a/di modify name <Name> &7- Change display name of the item.\n&2> &a/di modify lore add [String] [Position] &7- Add lore line.\n&2> &a/di modify lore del [Position] &7- Remove lore line.\n&2> &a/di modify lore clear &7- Clear item lore.\n&2> &a/di modify flag add <ItemFlag> &7- Add a flag to item.\n&2> &a/di modify flag del <ItemFlag> &7- Remove a flag from the item.\n&2> &a/di modify nbt add <NBTTag> <Value> &7- Add an NBT Tag to item.\n&2> &a/di modify nbt del <NBTTag> &7- Remove NBT Tag from item.\n&2> &a/di modify enchant <Enchantment> <Level> &7- Enchants item.\n&2> &a/di modify potion <Effect> <Level> <Duration> [Ambient(true/false)] [Particles(true/false)] [Icon(true/false)] &7- Modify potion effects.\n&2> &a/di modify eggtype <EntityType> &7- Change the monster egg type.\n&2> &a/di modify color <R,G,B> &7- Change the color of leather armor."),
    Help_Set("&8&m-----------&8&l[ &aDivine Items RPG &7- &aSet &8&l]&8&m-----------\n&7&l&nCommand List:\n&2> &a/di set attribute <Atrribute> <Value> <Second Value> [Position] &7- Set the attribute to item.\n&2> &a/di set bonus <Atrribute> <Value> [Position] &7- Set the attribute to item.\n&2> &a/di set level <Number> [Position] &7- Add level requirements to item.\n&2> &a/di set class <Class1,Class2,Etc> [Position] &7- Add class requirements to item.\n&2> &a/di set ability <Ability> <Level> <LEFT/RIGHT> <true/false> <Cooldown> [Position] &7- Add an ability to item.\n&2> &a/di set damagetype <Type> <Min> <Max> [Position] &7- Add the damage type to item.\n&2> &a/di set defensetype <Type> <Value> [Position] &7- Add the defense type to item.\n&2> &a/di set ammotype <Type> [Position] &7- Set the type of ammo to bow.\n&2> &a/di set slot <SlotType> [Position] &7- Add an empty slot to item."),
    Help_Sets("&8&m-----------&8&l[ &aDivine Items RPG &7- &aSets &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di sets get <ID> <Item> <Amount> &7- Get an specified set item.\n&2> &a/di sets give <Player> <ID> <Item> <Amount> &7- Give an item to player.\n&2> &a/di sets drop <World> <X> <Y> <Z> <ID> <Item> <Amount> &7- Drop an item in the world.\n&2> &a/di sets list &7- List of all sets."),
    Help_Arrows("&8&m-----------&8&l[ &aDivine Items RPG &7- &aArrows &8&l]&8&m-----------\n&2&l&nModule Info: \n&7> &fFull name: &a&l%m_name%\n&7> &fStatus: %m_state%\n&7> &fVersion: &a&l%m_ver%\n&2\n&7&l&nCommand List:\n&2> &a/di arrows get <ID> <Amount> &7- Get a specified arrow.\n&2> &a/di arrows give <Player> <ID> <Amount> &7- Give an arrow to player.\n&2> &a/di arrows drop <World> <X> <Y> <Z> <ID> <Amount> &7- Drop an arrow in the world.\n&2> &a/di arrows list &7- List of all arrows."),
    Buffs_Get("You just got the %type% buff for %time% seconds. Your %type% has been increased for %value%%"),
    Buffs_End("Your %type% buff's time ended."),
    Buffs_Give("You just gave the %value%% %mod% buff for %time% seconds to %p."),
    Buffs_Invalid("It seems you did a mistake in buff name."),
    CombatLog_Deal("&e*** You dealt &7%s &edamage to &7%e&e. ***"),
    CombatLog_DealCrit("&e*** &lCritical! &eYou dealt &7%s &edamage to &7%e&e. ***"),
    CombatLog_Get("&c*** &7%e &cdealt &7%s &cdamage to you. ***"),
    CombatLog_GetCrit("&d*** &lCritical! &7%e &ddealt &7%s &ddamage to you. ***"),
    CombatLog_DodgeEnemy("&6*** &7%e &6has dodged your attack! ***"),
    CombatLog_Dodge("&6*** You has dodged &7%e &6attack! ***"),
    CombatLog_BlockEnemy("&6*** &7%e &6has blocked &7%d% &6of your attack! ***"),
    CombatLog_Block("&6*** You blocked &7%d% &6of &7%e &6attack! ***"),
    Restrictions_NotOwner("&7You &ccan not &7use this item because you are not the owner of them."),
    Restrictions_Level("&7Your level is too low &7(&6%s&7) for using this item."),
    Restrictions_Class("&7Your class &c(%s) &7is not allowed to use this item!"),
    Restrictions_NoCommands("&cYou can not type this command while holding untradable item."),
    Restrictions_Usage("&7You must set the &6Soulbound&7 to use this item. Do &6Right-Click &7on this item in your inventory (press E)."),
    Restrictions_SoulAccept("&7Soulbound have been set &asuccessfully&7!"),
    Restrictions_SoulDecline("&4&lCanceled."),
    Soulbound_NoDrop("&cYou can not drop untradeable item!"),
    CustomItems_Invalid("&cItem &7%s &cdoes not exists."),
    Gems_Invalid("&cGem &7%s &cdoes not exists."),
    Gems_Enchanting_MultipleNotAllowed("Your item is already have gem &c%gem%&7!"),
    Gems_Enchanting_InvalidType("&c&lOops! &7This gem can not be applied to this item type!"),
    Gems_Enchanting_NoSlots("&c&lOops! &7This item do not contains empty gem slots!"),
    Gems_Enchanting_FullInventory("&c&lOops! Your inventory must contains at least 1 free slot to do it!"),
    Gems_Enchanting_Result("&a&l[RESULT]"),
    Gems_Enchanting_Cancel("&4&lCanceled!"),
    Gems_Enchanting_Success("&a&lSuccess! &7Your item have been improved!"),
    Gems_Enchanting_Failure_Item("&c&lFailure! &7Your item have been destroyed!"),
    Gems_Enchanting_Failure_Source("&c&lFailure! &7Your gem have been destroyed!"),
    Gems_Enchanting_Failure_Both("&c&lFailure! &7Your item and gem has been destroyed!"),
    Gems_Enchanting_Failure_Clear("&c&lFailure! &7Your item have lost all active gems!"),
    Gems_Enchanting_Failure_None("&c&lFailure! &7Try again!"),
    Gems_Enchanting_NoItem("&c&lOops! &7It seems your inventory does not contains an item to enchanting anymore."),
    Enchants_Invalid("&cEnchant &7%s &cdoes not exists."),
    Enchants_Enchanting_InvalidType("&c&lOops! &7This enchant can not be applied to this item type!"),
    Enchants_Enchanting_NoSlots("&c&lOops! &7This item do not contains empty enchant slots!"),
    Enchants_Enchanting_AlreadyHave("&c&lOops! &7This item already have this enchant with higher level!"),
    Enchants_Enchanting_FullInventory("&c&lOops! Your inventory must contains at least 1 free slot to do it!"),
    Enchants_Enchanting_Result("&a&l[RESULT]"),
    Enchants_Enchanting_Cancel("&4&lCanceled!"),
    Enchants_Enchanting_Success("&a&lSuccess! &7Your item have been enchanted!"),
    Enchants_Enchanting_Failure_Item("&c&lFailure! &7Your item have been destroyed!"),
    Enchants_Enchanting_Failure_Source("&c&lFailure! &7Your enchant have been destroyed!"),
    Enchants_Enchanting_Failure_Both("&c&lFailure! &7Your item and enchant has been destroyed!"),
    Enchants_Enchanting_Failure_Clear("&c&lFailure! &7Your item have lost all active enchants!"),
    Enchants_Enchanting_Failure_None("&c&lFailure! &7Try again!"),
    Enchants_Enchanting_NoItem("&c&lOops! &7It seems your inventory does not contains an item to enchanting anymore."),
    Enchants_Misc_SpawnerName("&c&lMob Spawner &7(%type%)"),
    Enchants_Misc_GTM_Steal("&aYou stealed %s%$ from &2%p&a!"),
    Enchants_Misc_GTM_Robbed("&4%p &cstealed %s$ from you!"),
    Enchants_Misc_SkullName("&c&l%name%"),
    Runes_Invalid("&cRune &7%s &cdoes not exists."),
    Runes_Enchanting_InvalidType("&c&lOops! &7This rune can not be applied to this item type!"),
    Runes_Enchanting_NoSlots("&c&lOops! &7This item do not contains empty rune slots!"),
    Runes_Enchanting_AlreadyHave("&c&lOops! &7This item already have this rune with higher level!"),
    Runes_Enchanting_FullInventory("&c&lOops! Your inventory must contains at least 1 free slot to do it!"),
    Runes_Enchanting_Result("&a&l[RESULT]"),
    Runes_Enchanting_Cancel("&4&lCanceled!"),
    Runes_Enchanting_Success("&a&lSuccess! &7Your item have been enchanted!"),
    Runes_Enchanting_Failure_Item("&c&lFailure! &7Your item have been destroyed!"),
    Runes_Enchanting_Failure_Source("&c&lFailure! &7Your rune have been destroyed!"),
    Runes_Enchanting_Failure_Both("&c&lFailure! &7Your item and rune has been destroyed!"),
    Runes_Enchanting_Failure_Clear("&c&lFailure! &7Your item have lost all active runes!"),
    Runes_Enchanting_Failure_None("&c&lFailure! &7Try again!"),
    Runes_Enchanting_NoItem("&c&lOops! &7It seems your inventory does not contains an item to enchanting anymore."),
    Abilities_Invalid("&cAbility &7%s &cdoes not exists."),
    Abilities_Cooldown("&eYou must wait &c%s &eseconds to use this ability again."),
    Abilities_Enchanting_InvalidType("&c&lOops! &7This ability can not be applied to this item type!"),
    Abilities_Enchanting_NoSlots("&c&lOops! &7This item do not contains empty ability slots!"),
    Abilities_Enchanting_AlreadyHave("&c&lOops! &7This item already have this ability with higher level!"),
    Abilities_Enchanting_FullInventory("&c&lOops! Your inventory must contains at least 1 free slot to do it!"),
    Abilities_Enchanting_Result("&a&l[RESULT]"),
    Abilities_Enchanting_Cancel("&4&lCanceled!"),
    Abilities_Enchanting_Success("&a&lSuccess! &7Your item have been enchanted!"),
    Abilities_Enchanting_Failure_Item("&c&lFailure! &7Your item have been destroyed!"),
    Abilities_Enchanting_Failure_Source("&c&lFailure! &7Your ability have been destroyed!"),
    Abilities_Enchanting_Failure_Both("&c&lFailure! &7Your item and ability has been destroyed!"),
    Abilities_Enchanting_Failure_Clear("&c&lFailure! &7Your item have lost all active abilities!"),
    Abilities_Enchanting_Failure_None("&c&lFailure! &7Try again!"),
    Abilities_Enchanting_NoItem("&c&lOops! &7It seems your inventory does not contains an item to enchanting anymore."),
    Identify_Invalid_Item("&cItem &7%s &cdoes not exists."),
    Identify_Invalid_Tome("&cTome &7%s &cdoes not exists."),
    Identify_WrongTome("&clOops! &7This &cIdentify Tome &7can not identify this item."),
    Identify_NoEquip("You can not equip the unidentified item!"),
    Scrolls_Invalid("&cScroll &7%s &cdoes not exists."),
    Scrolls_Cooldown("Scroll is on cooldown. You need to wait &c%s &7seconds to use it again."),
    Scrolls_Using("&r&lUsing scroll... Don't move."),
    Scrolls_Cancelled("&4&lCanceled."),
    Sets_Invalid("&cSet &7%s &cdoes not exitst."),
    Tiers_Invalid("&cTier &7%s &cdoes not exists."),
    Arrows_Invalid("&cArrow &7%s &cdoes not exists."),
    AbyssDust_Invalid("&cAbyss dust &7%s &cdoes not exists."),
    AbyssDust_NoGems("&c&lOops! &7This item does not have any gems."),
    AbyssDust_NoEnchants("&c&lOops! &7This item does not have any enchants."),
    AbyssDust_NoRunes("&c&lOops! &7This item does not have any runes."),
    AbyssDust_NoAbilities("&c&lOops! &7This item does not have any abilities."),
    AbyssDust_NoSoulbound("&c&lOops! &7This item does not have soulbound."),
    AbyssDust_Applied_Gem("&a&lSuccess! &7Your %item% &7have lost &a%s Gem(s)&7."),
    AbyssDust_Applied_Enchant("&a&lSuccess! &7Your %item% &7have lost &a%s Enchant(s)&7."),
    AbyssDust_Applied_Rune("&a&lSuccess! &7Your %item% &7have lost &a%s Rune(s)&7."),
    AbyssDust_Applied_Ability("&a&lSuccess! &7Your %item% &7have lost &a%s Ability(es)&7."),
    AbyssDust_Applied_Soul("&a&lSuccess! &7Your %item% &7have lost &aSoulbound&7."),
    Repair_Cancel("&4&lCancelled."),
    Repair_Result("&a&l[RESULT]"),
    Repair_FullInventory("&c&lOops! Your inventory must contains at least 1 free slot to do it!"),
    Repair_NoItem("&cYou must hold an item to repair!"),
    Repair_NoDurability("&cItem does not have custom durability."),
    Repair_Done("&aYour item have been repaired."),
    Repair_CantAfford("&cYou can't afford the repair!"),
    MagicDust_Done("&a&lSuccess! &7The success rate have been increased!"),
    MagicDust_Invalid("&cMagic dust &7%s &cdoes not exists."),
    MagicDust_Maximum("The success rate of the item equals &c%s%&7. You cant add more!"),
    MagicDust_NoStack("You can not apply Magic Dust on more than 1 item in stack!"),
    MagicDust_WrongItem("&c&lOops! &7This &eMagic Dust&7 can not be applied to this item."),
    Consumables_Invalid("&cConsume &7%s &cdoes not exists."),
    Consumables_Cooldown("&cYou must wait &f%s &cseconds to consume it again."),
    Consumables_FullHunger("&cYou can not consume it because your food level is full."),
    Consumables_FullHp("&cYou can not consume it because your health is full."),
    Resolve_Title_Resolved("&a&lResolved!"),
    Resolve_SubTitle_Resolved("&7You resolved &f%item%&7!"),
    Other_true("&aTrue"),
    Other_false("&cFalse"),
    Other_Right("&fRight"),
    Other_Left("&fLeft"),
    Other_BrokenItem("Your item is broken! You can not use it!"),
    Other_Broadcast("Player &6%p &7found the item &6%item%&7!"),
    Other_Disabled("This item is disabled!"),
    Other_NoPerm("You dont have permissions to do that!"),
    Other_Get("You received: &7<%item%&7>"),
    Other_NotAPotion("The item must be a potion!"),
    Other_NotAnEgg("The item must be an monster egg!"),
    Other_NotALeather("The item must be a leather armor!"),
    Other_InvalidType("&cInvalid type! Available types: %s"),
    Other_InvalidPlayer("&cPlayer not found!"),
    Other_InvalidNumber("&f%s &cis invalid number!"),
    Other_InvalidWorld("&cWorld &7%s &cdoes not exists!"),
    Other_InvalidSender("&cYou must be a player!"),
    Other_InvalidItem("&cYou must hold an item!"),
    Other_InvalidCoordinates("&7%s &care not a valid coordinates!"),
    Other_InvalidRGB("&7%s &care not a valid RGB colors!"),
    Other_Internal("&cInternal error! Contact administration."),
    Admin_NBTSet("NBT Tag &a%s &7successfully setted!"),
    Admin_AttributeSet("Attribute &a%s &7successfully setted!"),
    Admin_Set("Done!"),
    Admin_WrongUsage("Wrong usage! Type &a/di help&7 for help."),
    Admin_Reload_Full("Reload complete!"),
    Admin_Reload_Cfg("Config files are reloaded!"),
    ItemTypes_ALL("Any"),
    ItemTypes_WEAPON("Weapon"),
    ItemTypes_ARMOR("Armor"),
    ItemTypes_TOOL("Tool"),
    ItemTypes_ROD("Fishing Rod"),
    ItemTypes_SWORD("Sword"),
    ItemTypes_PICKAXE("Pickaxe"),
    ItemTypes_AXE("Axe"),
    ItemTypes_SPADE("Spade"),
    ItemTypes_HOE("Hoe"),
    ItemTypes_BOW("Bow"),
    ItemTypes_SHIELD("Shield"),
    ItemTypes_HELMET("Helmet"),
    ItemTypes_CHESTPLATE("Chestplate"),
    ItemTypes_LEGGINGS("Leggings"),
    ItemTypes_BOOTS("Boots"),
    ItemTypes_ELYTRA("Elytra");
    
    private String msg;
    private static MyConfig config;

    private Lang(String string2, int n2, String string3) {
        this.msg = string2;
    }

    public String getPath() {
        return this.name().replace("_", ".");
    }

    public String getMsg() {
        return this.msg;
    }

    public String toMsg() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)config.getConfig().getString(this.getPath()));
    }

    public List<String> getList() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string : config.getConfig().getStringList(this.getPath())) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string));
        }
        return arrayList;
    }

    public static boolean hasPath(String string) {
        return config.getConfig().contains(string);
    }

    public static String getCustom(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)config.getConfig().getString(string));
    }

    public static void setup(MyConfig myConfig) {
        config = myConfig;
        Lang.load();
    }

    public static String getHalfType(Material material) {
        String string = material.name().split("_")[1];
        return ChatColor.translateAlternateColorCodes((char)'&', (String)config.getConfig().getString("ItemTypes." + string));
    }

    private static void load() {
        Object object;
        Lang lang;
        Lang[] arrlang = Lang.values();
        int n = arrlang.length;
        int n2 = 0;
        while (n2 < n) {
            lang = arrlang[n2];
            if (config.getConfig().getString(lang.getPath()) == null) {
                if (lang.getMsg().contains("\n")) {
                    String[] arrstring;
                    object = new ArrayList();
                    String[] arrstring2 = arrstring = lang.getMsg().split("\n");
                    int n3 = arrstring2.length;
                    int n4 = 0;
                    while (n4 < n3) {
                        String string = arrstring2[n4];
                        object.add(string);
                        ++n4;
                    }
                    config.getConfig().set(lang.getPath(), object);
                } else {
                    config.getConfig().set(lang.getPath(), (Object)lang.getMsg());
                }
            }
            ++n2;
        }
        arrlang = EntityType.values();
        n = arrlang.length;
        n2 = 0;
        while (n2 < n) {
            lang = arrlang[n2];
            if (lang.isAlive()) {
                object = lang.name();
                if (config.getConfig().getString("EntityNames." + (String)object) == null) {
                    config.getConfig().set("EntityNames." + (String)object, (Object)WordUtils.capitalizeFully((String)object.replace("_", " ")));
                }
            }
            ++n2;
        }
        config.save();
    }
}

