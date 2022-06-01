package com._0myun.minecraft.pokemongoldchallenge;

import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class QueueChecker extends BukkitRunnable {
    @Override
    public void run() {
        try {
            if (R.queue.size() < 4) return;
            UUID p1 = R.queue.poll();
            UUID p2 = R.queue.poll();
            UUID p3 = R.queue.poll();
            UUID p4 = R.queue.poll();
            List<UUID> players = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
            p1 = players.remove(new Random().nextInt(players.size()));
            p2 = players.remove(new Random().nextInt(players.size()));
            p3 = players.remove(new Random().nextInt(players.size()));
            p4 = players.remove(new Random().nextInt(players.size()));
/*            UUID p1 = R.queue.poll();
            UUID p2 = R.queue.poll();
            List<UUID> players = new ArrayList<>(Arrays.asList(p1, p2));
            p1 = players.remove(new Random().nextInt(players.size()));
            p2 = players.remove(new Random().nextInt(players.size()));*/

            R.INSTANCE.getLogger().info("金币挑战人数足够");
            BattleControllerBase battle = BattleRegistry.startBattle(
                    new CoupleParty(p1, p2).createParticipant()
                    , new CoupleParty(p3, p4).createParticipant()
                    , new BattleRules());
            BattleListener.watching.add(battle.battleIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
