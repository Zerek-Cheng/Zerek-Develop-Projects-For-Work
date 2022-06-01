// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie.Beans;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class Settings
{
    private String id;
    private ItemStack fenjieItem;
    private List<ItemStack> materialItems;
    
    public Settings() {
        this.materialItems = new ArrayList<ItemStack>();
    }
    
    public Settings(final String id, final ItemStack fenjieItem, final List<ItemStack> materialItems) {
        this.materialItems = new ArrayList<ItemStack>();
        this.id = id;
        this.fenjieItem = fenjieItem;
        this.materialItems = materialItems;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public ItemStack getFenjieItem() {
        return this.fenjieItem;
    }
    
    public void setFenjieItem(final ItemStack fenjieItem) {
        this.fenjieItem = fenjieItem;
    }
    
    public List<ItemStack> getMaterialItems() {
        return this.materialItems;
    }
    
    public void setMaterialItems(final List<ItemStack> materialItems) {
        this.materialItems = materialItems;
    }
}
