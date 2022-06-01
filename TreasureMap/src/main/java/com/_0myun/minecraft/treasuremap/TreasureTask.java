package com._0myun.minecraft.treasuremap;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Data
@AllArgsConstructor
public class TreasureTask extends BukkitRunnable {
    Player p;
    double wait = 0;

    @Override
    public void run() {
        if (!p.isOnline() || !R.INSTANCE.getSearching().containsKey(p.getUniqueId())) {
            this.cancel();
            return;
        }
        if (wait < 0) wait = 0;
        Location loc = p.getLocation();
        if (!R.INSTANCE.getPosition().containsKey(p.getUniqueId())) return;
        TreasureMap map = (TreasureMap) R.INSTANCE.getConfig().get("map." + R.INSTANCE.getSearching().get(p.getUniqueId()));
        Position pos = R.INSTANCE.getPosition().get(p.getUniqueId());
        double distance = -1;
        if (loc.getWorld().getName().equalsIgnoreCase(pos.getWorld()))
            distance = pos.getLocation().distance(loc);
        String actionbar = "";
        if (distance == -1 || distance >= R.INSTANCE.getConfig().getInt("max-distance")) {
            actionbar = map.getActionbar()
                    .replace("{world}", pos.getWorld())
                    .replace("{x}", String.valueOf(pos.getX()))
                    .replace("{y}", String.valueOf(pos.getY()))
                    .replace("{z}", String.valueOf(pos.getZ()))
                    .replace("{dis}",
                            pos.getWorld().equalsIgnoreCase(loc.getWorld().getName())
                                    ? String.valueOf((int) distance)
                                    : "不在" + pos.getWorld() + "世界");
        } else {
            wait -= 0.05;
            //§a 绿色  §c 红色
            actionbar = R.INSTANCE.lang("lang2");
            for (int i = 0; i < R.INSTANCE.getConfig().getInt("wait", 5) - wait; i++) {
                actionbar += "§a█";
            }
            for (int i = 0; i < wait; i++) {
                actionbar += "§c█";
            }
            if (wait <= 0) {
                R.INSTANCE.giveReward(p, R.INSTANCE.getSearching().get(p.getUniqueId()));
                R.INSTANCE.getSearching().remove(p.getUniqueId());
                R.INSTANCE.getPosition().remove(p.getUniqueId());
                this.cancel();
            }
        }
        R.INSTANCE.sendActionBar(p, WrappedChatComponent.fromText(actionbar));

    }
}
