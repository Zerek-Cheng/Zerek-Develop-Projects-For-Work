package com._0myun.minecraft.sponge.privateblock;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlaceListener {
    @Listener
    public void onPlace(ChangeBlockEvent.Place e, @First Player p) {
        Cause cause = e.getCause();
        Transaction<BlockSnapshot> bst = e.getTransactions().get(0);
        if (e.getTransactions().size() != 1) return;
        String id = bst.getFinal().getLocation().get().getBlock().getType().getId();
        if (!Main.plugin.getList().contains(id)) return;
        Optional<Location<World>> loc = bst.getFinal().getLocation();
        UUID uuid = p.getUniqueId();
        Main.plugin.setOwner(loc.get(), uuid);
    }

    @Listener
    public void onBreak(ChangeBlockEvent.Break e, @First Player p) {
        Cause cause = e.getCause();
        List<Transaction<BlockSnapshot>> bts = e.getTransactions();

        bts.forEach(trans -> {
                    Location<World> loc = trans.getFinal().getLocation().get();
                    if (!Main.plugin.hasOwner(loc)) return;
                    UUID ownerUUID = Main.plugin.getOwner(loc);
                    if (!ownerUUID.equals(p.getUniqueId())) {
                        e.setCancelled(true);
                        p.sendMessage(Text.of(Main.plugin.getLang("lang1")));
                    } else {
                        p.sendMessage(Text.of(Main.plugin.getLang("lang2")));
                        Main.plugin.removeOwner(loc);
                    }
                }
        );
    }

    @Listener
    public void onInteract(InteractBlockEvent e, @First Player p) {
        BlockSnapshot block = e.getTargetBlock();
        Location<World> loc = block.getLocation().get();
        if (!Main.plugin.hasOwner(loc)) return;
        UUID ownerUUID = Main.plugin.getOwner(loc);
        if (!ownerUUID.equals(p.getUniqueId())) {
            e.setCancelled(true);
            p.sendMessage(Text.of(Main.plugin.getLang("lang1")));
        }
    }
}
