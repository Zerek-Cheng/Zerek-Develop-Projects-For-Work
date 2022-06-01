/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.citizensnpcs.api.event.NPCRightClickEvent
 *  net.citizensnpcs.api.npc.NPC
 *  net.citizensnpcs.api.trait.Trait
 *  net.citizensnpcs.api.trait.TraitName
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 */
package su.nightexpress.divineitems.hooks.external.citizens.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.resolve.ResolveManager;

@TraitName(value="resolver")
public class ResolveTrait
extends Trait {
    public ResolveTrait() {
        super("resolver");
    }

    @EventHandler
    public void click(NPCRightClickEvent nPCRightClickEvent) {
        if (nPCRightClickEvent.getNPC() == this.getNPC() && DivineItems.instance.getMM().getResolveManager().isActive()) {
            DivineItems.instance.getMM().getResolveManager().openResolveGUI(nPCRightClickEvent.getClicker());
        }
    }
}

