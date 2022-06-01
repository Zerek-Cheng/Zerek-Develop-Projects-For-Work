/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  com.herocraftonline.heroes.Heroes
 *  com.herocraftonline.heroes.characters.CharacterManager
 *  com.herocraftonline.heroes.characters.Hero
 *  com.sucy.skill.SkillAPI
 *  com.sucy.skill.api.player.PlayerClass
 *  com.sucy.skill.api.player.PlayerData
 *  me.leothepro555.skills.database.managers.PlayerDataManager
 *  me.leothepro555.skills.database.managers.PlayerInfo
 *  me.leothepro555.skills.main.Skills
 *  me.robin.battlelevels.api.BattleLevelsAPI
 *  net.flamedek.rpgme.RPGme
 *  net.flamedek.rpgme.player.RPGPlayer
 *  net.flamedek.rpgme.player.SkillSet
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  ru.Capitalism.DivineClassesRPG.API.DivineAPI
 */
package su.nightexpress.divineitems.hooks;

import com.gmail.nossr50.api.ExperienceAPI;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import java.util.UUID;
import me.leothepro555.skills.database.managers.PlayerDataManager;
import me.leothepro555.skills.database.managers.PlayerInfo;
import me.leothepro555.skills.main.Skills;
import me.robin.battlelevels.api.BattleLevelsAPI;
import net.flamedek.rpgme.RPGme;
import net.flamedek.rpgme.player.RPGPlayer;
import net.flamedek.rpgme.player.SkillSet;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import ru.Capitalism.DivineClassesRPG.API.DivineAPI;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;

public class LevelsHook {
    private HookManager hm;
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;

    public LevelsHook(HookManager hookManager) {
        this.hm = hookManager;
    }

    public int getPlayerLevel(Player player) {
        switch (LevelsHook.$SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook()[this.hm.getLevelPlugin().ordinal()]) {
            case 4: {
                PlayerData playerData = SkillAPI.getPlayerData((OfflinePlayer)player);
                return playerData.hasClass() ? playerData.getMainClass().getLevel() : 0;
            }
            case 5: {
                return Skills.get().getPlayerDataManager().getOrLoadPlayerInfo((OfflinePlayer)player).getLevel();
            }
            case 2: {
                return BattleLevelsAPI.getLevel((UUID)player.getUniqueId());
            }
            case 3: {
                return DivineAPI.getSkillPlayer((UUID)player.getUniqueId()).getLevel();
            }
            case 9: {
                return Heroes.getInstance().getCharacterManager().getHero(player).getLevel();
            }
            case 13: {
                return ExperienceAPI.getPowerLevel((Player)player);
            }
            case 15: {
                return RPGme.getAPI().get(player).getSkillSet().getCombatLevel();
            }
        }
        return player.getLevel();
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

