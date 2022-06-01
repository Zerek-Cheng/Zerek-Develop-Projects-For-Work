/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_13_R2.ChatMessageType
 *  net.minecraft.server.v1_13_R2.EntityPlayer
 *  net.minecraft.server.v1_13_R2.IChatBaseComponent
 *  net.minecraft.server.v1_13_R2.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_13_R2.ItemStack
 *  net.minecraft.server.v1_13_R2.NBTBase
 *  net.minecraft.server.v1_13_R2.NBTTagCompound
 *  net.minecraft.server.v1_13_R2.NBTTagDouble
 *  net.minecraft.server.v1_13_R2.NBTTagInt
 *  net.minecraft.server.v1_13_R2.NBTTagList
 *  net.minecraft.server.v1_13_R2.NBTTagString
 *  net.minecraft.server.v1_13_R2.Packet
 *  net.minecraft.server.v1_13_R2.PacketPlayOutChat
 *  net.minecraft.server.v1_13_R2.PacketPlayOutTitle
 *  net.minecraft.server.v1_13_R2.PacketPlayOutTitle$EnumTitleAction
 *  net.minecraft.server.v1_13_R2.PlayerConnection
 *  org.bukkit.ChatColor
 *  org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer
 *  org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.nms;

import net.minecraft.server.v1_13_R2.ChatMessageType;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.NBTBase;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.NBTTagDouble;
import net.minecraft.server.v1_13_R2.NBTTagInt;
import net.minecraft.server.v1_13_R2.NBTTagList;
import net.minecraft.server.v1_13_R2.NBTTagString;
import net.minecraft.server.v1_13_R2.Packet;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R2.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.nms.NBTAttribute;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class V1_13_R2
implements NMS {
    @Override
    public ItemStack setNBTAtt(ItemStack itemStack, NBTAttribute nBTAttribute, double d) {
        net.minecraft.server.v1_13_R2.ItemStack itemStack2 = CraftItemStack.asNMSCopy((ItemStack)itemStack);
        NBTTagCompound nBTTagCompound = itemStack2.hasTag() ? itemStack2.getTag() : new NBTTagCompound();
        NBTTagList nBTTagList = nBTTagCompound.getList("AttributeModifiers", 10);
        String[] arrstring = ItemUtils.getAllNBTSlots(itemStack);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string = arrstring[n2];
            NBTTagCompound nBTTagCompound2 = new NBTTagCompound();
            int n3 = 0;
            while (n3 < nBTTagList.size()) {
                NBTTagCompound nBTTagCompound3 = (NBTTagCompound)nBTTagList.get(n3);
                String string2 = nBTTagCompound3.getString("AttributeName");
                if (string2.equals("generic." + (Object)((Object)nBTAttribute)) && nBTTagCompound3.getString("Slot").equalsIgnoreCase(string)) {
                    nBTTagCompound2 = nBTTagCompound3;
                    nBTTagList.remove((Object)nBTTagCompound3);
                    break;
                }
                ++n3;
            }
            if (nBTAttribute == NBTAttribute.movementSpeed) {
                d = 0.1 * (1.0 + d / 100.0) - 0.1;
            } else if (nBTAttribute == NBTAttribute.attackSpeed) {
                double d2 = ItemAPI.getDefaultAttackSpeed(itemStack) * d;
                d = - ItemAPI.getDefaultAttackSpeed(itemStack) - d2;
            }
            n3 = Utils.randInt(1, 333);
            nBTTagCompound2.set("AttributeName", (NBTBase)new NBTTagString("generic." + nBTAttribute.att()));
            nBTTagCompound2.set("Name", (NBTBase)new NBTTagString("generic." + nBTAttribute.att()));
            nBTTagCompound2.set("Amount", (NBTBase)new NBTTagDouble(d));
            nBTTagCompound2.set("Operation", (NBTBase)new NBTTagInt(0));
            nBTTagCompound2.set("UUIDLeast", (NBTBase)new NBTTagInt(n3));
            nBTTagCompound2.set("UUIDMost", (NBTBase)new NBTTagInt(n3));
            nBTTagCompound2.set("Slot", (NBTBase)new NBTTagString(string));
            nBTTagList.add((NBTBase)nBTTagCompound2);
            ++n2;
        }
        nBTTagCompound.set("AttributeModifiers", (NBTBase)nBTTagList);
        itemStack2.setTag(nBTTagCompound);
        itemStack = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_13_R2.ItemStack)itemStack2);
        return itemStack;
    }

    @Override
    public void sendActionBar(Player player, String string) {
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)string);
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + string2 + "\"}"));
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.GAME_INFO);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetPlayOutChat);
    }

    @Override
    public void sendTitles(Player player, String string, String string2, int n, int n2, int n3) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + string + "\"}"));
        IChatBaseComponent iChatBaseComponent2 = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + string2 + "\"}"));
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent);
        PacketPlayOutTitle packetPlayOutTitle2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iChatBaseComponent2);
        PacketPlayOutTitle packetPlayOutTitle3 = new PacketPlayOutTitle(n, n2, n3);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetPlayOutTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetPlayOutTitle2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packetPlayOutTitle3);
    }
}

