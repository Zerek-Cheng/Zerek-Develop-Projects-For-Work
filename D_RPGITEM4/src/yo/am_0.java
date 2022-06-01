/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityTameEvent
 *  org.bukkit.event.entity.HorseJumpEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.player.PlayerBedEnterEvent
 *  org.bukkit.event.player.PlayerBedLeaveEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.event.player.PlayerLevelChangeEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerToggleSneakEvent
 *  org.bukkit.event.player.PlayerToggleSprintEvent
 *  org.bukkit.event.player.PlayerUnleashEntityEvent
 *  org.bukkit.event.weather.ThunderChangeEvent
 *  org.bukkit.event.weather.WeatherChangeEvent
 *  org.bukkit.event.world.StructureGrowEvent
 */
package yo;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

public enum am_0 {
    TICK,
    DAMAGE,
    DAMAGED,
    SNEAK(PlayerToggleSneakEvent.class),
    SNEAKON,
    SNEAKOFF,
    SPRINT(PlayerToggleSprintEvent.class),
    SPRINTON,
    SPRINTOFF,
    INTERACT(PlayerInteractEvent.class),
    RIGHTCLICK,
    LEFTCLICK,
    RIGHTCLICKAIR,
    LEFTCLICKAIR,
    RIGHTCLICKBLOCK,
    LEFTCLICKBLOCK,
    RIGHTCLICKENTITY(PlayerInteractEntityEvent.class),
    CONSUME(PlayerItemConsumeEvent.class),
    KILL,
    KILLED(PlayerDeathEvent.class),
    BLOCKBREAK(BlockBreakEvent.class),
    BLOCKPLACE(BlockPlaceEvent.class),
    ARROWHIT(ProjectileHitEvent.class),
    ARROWENTITY,
    FISH(PlayerFishEvent.class),
    WEATHER,
    STORM(WeatherChangeEvent.class),
    THUNDER(ThunderChangeEvent.class),
    BONEGROW(StructureGrowEvent.class),
    LEVELUP(PlayerLevelChangeEvent.class),
    RESPAWN(PlayerRespawnEvent.class),
    TICKGROUND,
    TICKFLY,
    TICKLIQUID,
    TICKWATER,
    TICKLAVA,
    TICKSLEEP,
    TICKSNEAK,
    TICKSPRINT,
    TICKVEHICLE,
    TICKWEATHER,
    TICKSUN,
    TICKTHUNDER,
    TICKSTORM,
    BED,
    BEDENTER(PlayerBedEnterEvent.class),
    BEDLEFT(PlayerBedLeaveEvent.class),
    LEASH(PlayerLeashEntityEvent.class),
    UNLEASH(PlayerUnleashEntityEvent.class),
    EDITBOOK(PlayerEditBookEvent.class),
    REGAIN(EntityRegainHealthEvent.class),
    HORSEJUMP(HorseJumpEvent.class),
    BUCKETEMPTY(PlayerBucketEmptyEvent.class),
    BUCKETFILL(PlayerBucketFillEvent.class),
    ITEMBREAK(PlayerItemBreakEvent.class),
    TELEPORT(PlayerTeleportEvent.class),
    COMBUST(EntityCombustEvent.class),
    TAME(EntityTameEvent.class),
    ENCHANT(EnchantItemEvent.class);
    
    private Class<? extends Event> eventClass;

    private am_0() {
        this(null);
    }

    private am_0(Class<? extends Event> eventClass) {
        this.eventClass = eventClass;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public static am_0 parse(String str) {
        try {
            return am_0.valueOf(str.toUpperCase());
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static am_0 getByEvent(Event event) {
        return am_0.getByEventClass(event.getClass());
    }

    public static am_0 getByEventClass(Class<? extends Event> eventClass) {
        for (am_0 event : am_0.values()) {
            if (event.eventClass == null || !event.eventClass.getName().equals(eventClass.getName())) continue;
            return event;
        }
        return null;
    }
}

