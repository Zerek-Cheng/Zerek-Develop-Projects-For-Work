package com._0myun.minecraft.pokemongoldchallenge;

import catserver.api.bukkit.event.ForgeEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BattleListener implements Listener {
    public static List<Integer> watching = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    @EventHandler
    public void onPlayerQuit(ForgeEvent e) {
        if (!(e.getForgeEvent() instanceof PlayerEvent.PlayerLoggedOutEvent)) return;
        PlayerEvent.PlayerLoggedOutEvent event = (PlayerEvent.PlayerLoggedOutEvent) e.getForgeEvent();
        if (event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            BattleControllerBase controller = BattleRegistry.getBattle(player);
            if (controller != null && watching.contains(controller.battleIndex)) {
                PlayerParticipant playerParticipant = controller.getPlayer(event.player);
                for (BattleParticipant participant1 : controller.participants) {
                    if (participant1 instanceof PlayerParticipant) {
                        if (participant1.team == playerParticipant.team) {
                            onLose(((PlayerParticipant) participant1).player);
                        } else {
                            onWin(((PlayerParticipant) participant1).player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBattleEnd(ForgeEvent e) {
        if (!(e.getForgeEvent() instanceof BattleEndEvent)) return;
        BattleEndEvent event = (BattleEndEvent) e.getForgeEvent();
        BattleControllerBase controller = event.bc;
        if (event.cause == EnumBattleEndCause.NORMAL || event.cause == EnumBattleEndCause.FORFEIT) {
            if (watching.contains(controller.battleIndex)) {
                for (Map.Entry<BattleParticipant, BattleResults> entry : event.results.entrySet()) {
                    if (entry.getKey() instanceof PlayerParticipant) {
                        PlayerParticipant playerParticipant = (PlayerParticipant) entry.getKey();
                        switch (entry.getValue()) {
                            case VICTORY:
                                onWin(playerParticipant.player);
                                break;
                            case DEFEAT:
                            case FLEE:
                                onLose(playerParticipant.player);
                                break;
                            case DRAW:
                                onDraw(playerParticipant.player);
                                break;
                            default:
                        }
                    }
                }
            }
        }
    }

    private void onWin(EntityPlayerMP player) {
        System.out.println(player + "赢了");
        player.getBukkitEntity().sendMessage(R.INSTANCE.lang("lang6"));
        for (String cmd : R.INSTANCE.getConfig().getStringList("command.win"))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getBukkitEntity().getName()));

    }

    private void onLose(EntityPlayerMP player) {
        System.out.println(player + "输了");
        player.getBukkitEntity().sendMessage(R.INSTANCE.lang("lang5"));
        for (String cmd : R.INSTANCE.getConfig().getStringList("command.fail"))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getBukkitEntity().getName()));
    }

    private void onDraw(EntityPlayerMP player) {
        System.out.println(player + "平了");
        player.getBukkitEntity().sendMessage(R.INSTANCE.lang("lang8"));
        for (String cmd : R.INSTANCE.getConfig().getStringList("command.cancel"))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getBukkitEntity().getName()));
    }
}
