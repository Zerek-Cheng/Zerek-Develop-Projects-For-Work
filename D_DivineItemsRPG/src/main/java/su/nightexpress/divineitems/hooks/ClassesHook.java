/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.herocraftonline.heroes.Heroes
 *  com.herocraftonline.heroes.characters.CharacterManager
 *  com.herocraftonline.heroes.characters.Hero
 *  com.herocraftonline.heroes.characters.classes.HeroClass
 *  com.sucy.skill.SkillAPI
 *  com.sucy.skill.api.classes.RPGClass
 *  com.sucy.skill.api.player.PlayerClass
 *  com.sucy.skill.api.player.PlayerData
 *  com.zthana.racesofthana.Race
 *  com.zthana.racesofthana.RacesOfThana
 *  com.zthana.racesofthana.handlers.RaceHandler
 *  me.leothepro555.skills.database.managers.PlayerDataManager
 *  me.leothepro555.skills.database.managers.PlayerInfo
 *  me.leothepro555.skills.main.Skills
 *  me.leothepro555.skilltype.SkillType
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.permissions.PermissionAttachmentInfo
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  ru.Capitalism.DivineClassesRPG.API.DivineAPI
 *  ru.Capitalism.DivineClassesRPG.Classes.ClassManager
 *  ru.Capitalism.DivineClassesRPG.Classes.ClassType
 *  ru.Capitalism.DivineClassesRPG.Classes.DClass
 */
package su.nightexpress.divineitems.hooks;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import com.zthana.racesofthana.Race;
import com.zthana.racesofthana.RacesOfThana;
import com.zthana.racesofthana.handlers.RaceHandler;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.leothepro555.skills.database.managers.PlayerDataManager;
import me.leothepro555.skills.database.managers.PlayerInfo;
import me.leothepro555.skills.main.Skills;
import me.leothepro555.skilltype.SkillType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import ru.Capitalism.DivineClassesRPG.API.DivineAPI;
import ru.Capitalism.DivineClassesRPG.Classes.ClassManager;
import ru.Capitalism.DivineClassesRPG.Classes.ClassType;
import ru.Capitalism.DivineClassesRPG.Classes.DClass;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;

public class ClassesHook {
    private DivineItems plugin;
    private HookManager hm;
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;

    public ClassesHook(DivineItems divineItems, HookManager hookManager) {
        this.hm = hookManager;
        this.plugin = divineItems;
    }

    public String getClassReplacer(Material material) {
        String string = "";
        if (Hook.DIVINE_CLASSES.isEnabled()) {
            return this.getDCClasses(material);
        }
        return string;
    }

    private String getDCClasses(Material material) {
        String string = "";
        String string2 = this.plugin.getCM().getCFG().getStrClassColor();
        String string3 = this.plugin.getCM().getCFG().getStrClassSeparator();
        ClassType[] arrclassType = ClassType.values();
        int n = arrclassType.length;
        int n2 = 0;
        while (n2 < n) {
            ClassType classType = arrclassType[n2];
            DClass dClass = (DClass)ClassManager.getClasses().get(classType.name());
            if (dClass.getWeapons().contains((Object)material) || dClass.getArmors().contains((Object)material)) {
                string = String.valueOf(string) + string2 + ChatColor.stripColor((String)dClass.getName()) + string3 + string2;
            }
            ++n2;
        }
        if (string.length() > 3) {
            string = string.substring(0, string.length() - 3);
        }
        return string;
    }

    public String getPlayerClass(Player player) {
        switch (ClassesHook.$SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook()[this.hm.getClassPlugin().ordinal()]) {
            case 4: {
                PlayerData playerData = SkillAPI.getPlayerData((OfflinePlayer)player);
                if (playerData.hasClass()) {
                    return playerData.getMainClass().getData().getName();
                }
                return "";
            }
            case 5: {
                return ChatColor.stripColor((String)Skills.get().getPlayerDataManager().getOrLoadPlayerInfo((OfflinePlayer)player).getSkill().getConfigName());
            }
            case 3: {
                return ChatColor.stripColor((String)DivineAPI.getSkillPlayer((UUID)player.getUniqueId()).getDClass().getName());
            }
            case 9: {
                return Heroes.getInstance().getCharacterManager().getHero(player).getHeroClass().getName();
            }
            case 16: {
                RacesOfThana racesOfThana = (RacesOfThana)this.plugin.getPluginManager().getPlugin("RacesOfThana");
                Race race = racesOfThana.getRaceHandler().getRace(player);
                if (race == null) {
                    return "";
                }
                return race.getName();
            }
            case 10: {
                String string = "";
                for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
                    String string2 = permissionAttachmentInfo.getPermission();
                    if (!string2.startsWith("divineitems.class")) continue;
                    String string3 = string2.substring(string2.lastIndexOf("."), string2.length());
                    string = string3.replace(".", "");
                }
                return string;
            }
        }
        return "";
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Hook.values().length];
        try {
            arrn[Hook.BATTLE_LEVELS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.CITIZENS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.DIVINE_CLASSES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.HEROES.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.HOLOGRAPHIC_DISPLAYS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.MCMMO.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.MYTHIC_MOBS.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.NONE.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.PLACEHOLDER_API.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RACES_OF_THANA.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RPG_INVENTORY.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RPGme.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.SKILLS.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.SKILL_API.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.VAULT.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.WORLD_GUARD.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;
    }
}

