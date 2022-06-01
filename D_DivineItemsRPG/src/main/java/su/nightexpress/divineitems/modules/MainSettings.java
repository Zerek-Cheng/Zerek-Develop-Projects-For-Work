/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 */
package su.nightexpress.divineitems.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Sound;
import su.nightexpress.divineitems.types.DestroyType;

public class MainSettings {
    private String display;
    private List<String> lore;
    private int min_chance;
    private int max_chance;
    private DestroyType destroy;
    private boolean eff_use;
    private String eff_de_value;
    private String eff_suc_value;
    private boolean sound_use;
    private Sound sound_de_value;
    private Sound sound_suc_value;
    private String header;
    private String empty_slot;
    private String filled_slot;

    MainSettings(MainSettings mainSettings) {
        this.display = mainSettings.getDisplay();
        this.lore = mainSettings.getLore();
        this.min_chance = mainSettings.getMinChance();
        this.max_chance = mainSettings.getMaxChance();
        this.destroy = mainSettings.getDestroyType();
        this.header = mainSettings.getHeader();
        this.empty_slot = mainSettings.getEmptySlot();
        this.filled_slot = mainSettings.getFilledSlot();
    }

    public MainSettings(String string, List<String> list, int n, int n2, DestroyType destroyType, boolean bl, String string2, String string3, boolean bl2, Sound sound, Sound sound2, String string4, String string5, String string6) {
        this.setDisplay(string);
        this.setLore(list);
        this.setMinChance(n);
        this.setMaxChance(n2);
        this.setDestroyType(destroyType);
        this.setUseEffect(bl);
        this.setDestroyEffect(string2);
        this.setSuccessEffect(string3);
        this.setUseSound(bl2);
        this.setDestroySound(sound);
        this.setSuccessSound(sound2);
        this.setHeader(string4);
        this.setEmptySlot(string5);
        this.setFilledSlot(string6);
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String string) {
        this.display = string;
    }

    public List<String> getLore() {
        return new ArrayList<String>(this.lore);
    }

    public void setLore(List<String> list) {
        this.lore = list;
    }

    public int getMinChance() {
        return this.min_chance;
    }

    public void setMinChance(int n) {
        this.min_chance = n;
    }

    public int getMaxChance() {
        return this.max_chance;
    }

    public void setMaxChance(int n) {
        this.max_chance = n;
    }

    public DestroyType getDestroyType() {
        return this.destroy;
    }

    public void setDestroyType(DestroyType destroyType) {
        this.destroy = destroyType;
    }

    public boolean useEffect() {
        return this.eff_use;
    }

    public void setUseEffect(boolean bl) {
        this.eff_use = bl;
    }

    public String getDestroyEffect() {
        return this.eff_de_value;
    }

    public void setDestroyEffect(String string) {
        this.eff_de_value = string;
    }

    public String getSuccessEffect() {
        return this.eff_suc_value;
    }

    public void setSuccessEffect(String string) {
        this.eff_suc_value = string;
    }

    public boolean useSound() {
        return this.sound_use;
    }

    public void setUseSound(boolean bl) {
        this.sound_use = bl;
    }

    public Sound getDestroySound() {
        return this.sound_de_value;
    }

    public void setDestroySound(Sound sound) {
        this.sound_de_value = sound;
    }

    public Sound getSuccessSound() {
        return this.sound_suc_value;
    }

    public void setSuccessSound(Sound sound) {
        this.sound_suc_value = sound;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String string) {
        this.header = string;
    }

    public String getEmptySlot() {
        return this.empty_slot;
    }

    public void setEmptySlot(String string) {
        this.empty_slot = string;
    }

    public String getFilledSlot() {
        return this.filled_slot;
    }

    public void setFilledSlot(String string) {
        this.filled_slot = string;
    }
}

