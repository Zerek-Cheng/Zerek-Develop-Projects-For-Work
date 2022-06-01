/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.citizensnpcs.api.CitizensAPI
 *  net.citizensnpcs.api.trait.TraitInfo
 *  org.bukkit.entity.Entity
 */
package su.nightexpress.divineitems.hooks.external.citizens;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.entity.Entity;
import su.nightexpress.divineitems.hooks.external.citizens.traits.ResolveTrait;

public class CitizensHook {
    private TraitInfo resolve = TraitInfo.create(ResolveTrait.class).withName("resolver");

    public boolean isNPC(Entity entity) {
        return CitizensAPI.getNPCRegistry().isNPC(entity);
    }

    public void registerTraits() {
        CitizensAPI.getTraitFactory().registerTrait(this.resolve);
    }

    public void unregisterTraits() {
        CitizensAPI.getTraitFactory().deregisterTrait(this.resolve);
    }
}

