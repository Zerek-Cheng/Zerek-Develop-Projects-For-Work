// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.playerdata;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import java.util.Collection;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class PlayerGameData
{
    private ItemStack[] invcontent;
    private ItemStack[] armorcontent;
    private double health;
    private int hunger;
    private GameMode gamemode;
    private boolean flying;
    private Collection<PotionEffect> potions;
    private Player p;
    private float exp;
    private int level;
    
    public PlayerGameData(final Player p) {
        this.invcontent = new ItemStack[0];
        this.armorcontent = new ItemStack[4];
        this.health = 20.0;
        this.hunger = 10;
        this.gamemode = GameMode.SURVIVAL;
        this.flying = false;
        this.exp = 0.0f;
        this.level = 0;
        this.p = p;
        this.invcontent = p.getInventory().getContents();
        this.armorcontent = p.getInventory().getArmorContents();
        this.health = p.getHealth();
        this.hunger = p.getFoodLevel();
        this.gamemode = p.getGameMode();
        this.flying = p.isFlying();
        this.potions = (Collection<PotionEffect>)p.getActivePotionEffects();
        this.exp = p.getExp();
        this.level = p.getLevel();
    }
    
    public void reset() {
        this.p.setHealth(this.health);
        this.p.setFoodLevel(this.hunger);
        this.p.getInventory().setContents(this.invcontent);
        this.p.getInventory().setArmorContents(this.armorcontent);
        this.p.setGameMode(this.gamemode);
        this.p.setFlying(this.flying);
        for (final PotionEffect pe : this.potions) {
            this.p.addPotionEffect(pe);
        }
        this.p.setLevel(this.level);
        this.p.setExp(this.exp);
    }
}
