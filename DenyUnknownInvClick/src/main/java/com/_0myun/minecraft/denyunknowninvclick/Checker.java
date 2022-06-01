package com._0myun.minecraft.denyunknowninvclick;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.inventory.ItemStack;

public class Checker extends PacketAdapter {
    public Checker() {
        super(DenyUnknownInvClick.INSTANCE, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK);
    }

    PacketContainer handle;

    @Override
    public void onPacketReceiving(PacketEvent event) {
        handle = event.getPacket();
        System.out.println("this.getWindowId() = " + this.getWindowId());
        System.out.println("this.getSlot() = " + this.getSlot());
        System.out.println("this.getButton() = " + this.getButton());
        System.out.println("this.getActionNumber() = " + this.getActionNumber());
        event.setCancelled(false);
        if (this.getSlot()>=0)return;
        System.out.println("检测到非法点击尝试忽略...");
        event.setCancelled(true);
    }

    public int getWindowId() {
        return handle.getIntegers().read(0);
    }

    /**
     * Set Window ID.
     *
     * @param value - new value.
     */
    public void setWindowId(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Retrieve Slot.
     * <p>
     * Notes: the clicked slot. See below.
     *
     * @return The current Slot
     */
    public int getSlot() {
        return handle.getIntegers().read(1);
    }

    /**
     * Set Slot.
     *
     * @param value - new value.
     */
    public void setSlot(int value) {
        handle.getIntegers().write(1, value);
    }

    /**
     * Retrieve Button.
     * <p>
     * Notes: the button used in the click. See below.
     *
     * @return The current Button
     */
    public int getButton() {
        return handle.getIntegers().read(2);
    }

    /**
     * Set Button.
     *
     * @param value - new value.
     */
    public void setButton(int value) {
        handle.getIntegers().write(2, value);
    }

    /**
     * Retrieve Action number.
     * <p>
     * Notes: a unique number for the action, used for transaction handling (See
     * the Transaction packet).
     *
     * @return The current Action number
     */
    public short getActionNumber() {
        return handle.getShorts().read(0);
    }

    /**
     * Set Action number.
     *
     * @param value - new value.
     */
    public void setActionNumber(short value) {
        handle.getShorts().write(0, value);
    }

    /**
     * Retrieve Clicked item.
     *
     * @return The current Clicked item
     */
    public ItemStack getClickedItem() {
        return handle.getItemModifier().read(0);
    }

    /**
     * Set Clicked item.
     *
     * @param value - new value.
     */
    public void setClickedItem(ItemStack value) {
        handle.getItemModifier().write(0, value);
    }
}
