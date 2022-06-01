// 
// Decompiled by Procyon v0.5.30
// 

package net.mcykzg.rightclickcommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RightClickCommand extends JavaPlugin implements Listener {
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a[RightClickCommand]§e\u63d2\u4ef6\u5df2\u52a0\u8f7d");
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
        this.saveDefaultConfig();
    }

    @EventHandler
    public void onRightClick(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
            final ItemStack item = p.getItemInHand();
            if (!item.hasItemMeta() || !item.getItemMeta().hasLore())return;
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                final Set<String> keys = (Set<String>) this.getConfig().getKeys(false);
                for (final String line : item.getItemMeta().getLore()) {
                    for (final String key : keys) {
                        if (ChatColor.stripColor(line).contains(key)) {
                            final int random = this.getConfig().getInt(String.valueOf(key) + ".Random");
                            final boolean delete = this.getConfig().getBoolean(String.valueOf(key) + ".Delete");
                            final List<String> cmds = (List<String>) this.getConfig().getStringList(String.valueOf(key) + ".Commands");
                            if (delete) {
                                this.delete(p);
                            }
                            if (new Random().nextInt(101) > random) {
                                break;
                            }
                            if (p.isOp()) {
                                for (final String cmd : cmds) {
                                    p.chat(cmd.replace("{player}", p.getName()));
                                }
                                break;
                            }
                            p.setOp(true);
                            for (final String cmd : cmds) {
                                p.chat(cmd.replace("{player}", p.getName()));
                            }
                            p.setOp(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void delete(final Player p) {
        final int amount = p.getItemInHand().getAmount();
        if (amount > 1) {
            p.getItemInHand().setAmount(amount - 1);
        } else {
            p.setItemInHand((ItemStack) null);
        }
    }
}
