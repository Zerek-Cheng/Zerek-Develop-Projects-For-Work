/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package cc.kunss.vexst.listeners;

import cc.kunss.vexst.api.VexStrengThenAPI;
import cc.kunss.vexst.event.StrengExpChangeEvent;
import cc.kunss.vexst.utils.Message;
import cc.kunss.vexst.utils.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExpChange
implements Listener {
    @EventHandler
    public void onexp(StrengExpChangeEvent e) {
        Player p = e.getPlayer();
        double StrengMaxExp = e.getPlayerMaxExp();
        double PlayerExp = e.getPlayerExp();
        if (PlayerExp > StrengMaxExp) {
            int givelevel = e.getPlayerLevel() + 1;
            int MaxLevel = Methods.getStrengLevelMap().get(Methods.getStrengLevelMap().keySet().size() - 1).getLevel();
            if (e.getPlayerLevel() >= MaxLevel && e.getPlayerExp() >= e.getPlayerMaxExp()) {
                VexStrengThenAPI.setPlayerStrengExp(p, StrengMaxExp);
                p.sendMessage(Message.getMessageString("PlayerLevel.max"));
                return;
            }
            VexStrengThenAPI.setPlayerStrengLevel(p, givelevel);
            VexStrengThenAPI.setPlayerStrengExp(p, 0.0);
            VexStrengThenAPI.setTPlayerStrengPrefix(p, Methods.getStrengLevelMap().get(VexStrengThenAPI.getPlayerStrengLevel(p)).getPrefix());
            e.getPlayer().sendMessage(Message.getMessageString("PlayerLevel.up").replace("{l}", String.valueOf(VexStrengThenAPI.getPlayerStrengLevel(p))).replace("{p}", VexStrengThenAPI.getPlayerStrengPrefix(p)));
        }
    }
}

