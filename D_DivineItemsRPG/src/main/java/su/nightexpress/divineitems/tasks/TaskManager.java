/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 */
package su.nightexpress.divineitems.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.attributes.BleedEffect;
import su.nightexpress.divineitems.attributes.BleedRateSettings;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.attributes.StatSettings;
import su.nightexpress.divineitems.tasks.BleedTask;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class TaskManager {
    private DivineItems plugin;
    private BleedTask global;
    private HashMap<LivingEntity, BleedEffect> bleed;

    public TaskManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.global = new BleedTask(this.plugin);
        this.bleed = new HashMap();
    }

    public void start() {
        this.global.start();
    }

    public void stop() {
        this.global.stop();
        this.bleed.clear();
    }

    public void addBleedEffect(LivingEntity livingEntity, double d) {
        BleedRateSettings bleedRateSettings = (BleedRateSettings)ItemStat.BLEED_RATE.getSettings();
        if (bleedRateSettings == null || bleedRateSettings.getFormula() == null) {
            return;
        }
        double d2 = ItemUtils.calc(bleedRateSettings.getFormula().replace("%dmg%", String.valueOf(d)));
        BleedEffect bleedEffect = new BleedEffect(bleedRateSettings.getTime(), d2);
        this.bleed.put(livingEntity, bleedEffect);
    }

    public void processBleed() {
        BleedRateSettings bleedRateSettings = (BleedRateSettings)ItemStat.BLEED_RATE.getSettings();
        HashMap<LivingEntity, BleedEffect> hashMap = new HashMap<LivingEntity, BleedEffect>(this.bleed);
        for (LivingEntity livingEntity : hashMap.keySet()) {
            BleedEffect bleedEffect = hashMap.get((Object)livingEntity);
            if (bleedEffect == null) continue;
            if (!livingEntity.isValid() || livingEntity.isDead() || bleedEffect.getTime() <= 0) {
                this.bleed.remove((Object)livingEntity);
                continue;
            }
            livingEntity.damage(bleedEffect.getDamage());
            Utils.playEffect(bleedRateSettings.getEffect(), 0.2, 0.25, 0.2, 0.1, 50, livingEntity.getEyeLocation());
            bleedEffect.setTime(bleedEffect.getTime() - 1);
            this.bleed.put(livingEntity, bleedEffect);
        }
    }
}

