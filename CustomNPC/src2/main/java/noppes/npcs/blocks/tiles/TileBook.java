package noppes.npcs.blocks.tiles;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class TileBook extends TileColorable
{
    public ItemStack book;
    
    public TileBook() {
        this.book = new ItemStack(Items.writable_book);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.book = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Items"));
        if (this.book == null) {
            this.book = new ItemStack(Items.writable_book);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("Items", (NBTBase)this.book.writeToNBT(new NBTTagCompound()));
    }
}
