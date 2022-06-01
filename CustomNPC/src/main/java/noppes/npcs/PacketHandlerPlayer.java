package noppes.npcs;

import cpw.mods.fml.common.network.*;
import net.minecraft.network.*;
import java.io.*;
import io.netty.buffer.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import noppes.npcs.roles.*;
import noppes.npcs.constants.*;
import noppes.npcs.containers.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import noppes.npcs.entity.*;
import noppes.npcs.controllers.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class PacketHandlerPlayer
{
    @SubscribeEvent
    public void onServerPacket(final FMLNetworkEvent.ServerCustomPacketEvent event) {
        final EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
        final ByteBuf buffer = event.packet.payload();
        try {
            this.player(buffer, player, EnumPlayerPacket.values()[buffer.readInt()]);
        }
        catch (IOException e) {
            LogWriter.except(e);
        }
    }
    
    private void player(final ByteBuf buffer, final EntityPlayerMP player, final EnumPlayerPacket type) throws IOException {
        if (type == EnumPlayerPacket.CompanionTalentExp) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Companion || player != npc.getOwner()) {
                return;
            }
            final int id = buffer.readInt();
            final int exp = buffer.readInt();
            final RoleCompanion role = (RoleCompanion)npc.roleInterface;
            if (exp <= 0 || !role.canAddExp(-exp) || id < 0 || id >= EnumCompanionTalent.values().length) {
                return;
            }
            final EnumCompanionTalent talent = EnumCompanionTalent.values()[id];
            role.addExp(-exp);
            role.addTalentExp(talent, exp);
        }
        else if (type == EnumPlayerPacket.CompanionOpenInv) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Companion || player != npc.getOwner()) {
                return;
            }
            NoppesUtilServer.sendOpenGui((EntityPlayer)player, EnumGuiType.CompanionInv, npc);
        }
        else if (type == EnumPlayerPacket.FollowerHire) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Follower) {
                return;
            }
            NoppesUtilPlayer.hireFollower(player, npc);
        }
        else if (type == EnumPlayerPacket.FollowerExtend) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Follower) {
                return;
            }
            NoppesUtilPlayer.extendFollower(player, npc);
            Server.sendData(player, EnumPacketClient.GUI_DATA, npc.roleInterface.writeToNBT(new NBTTagCompound()));
        }
        else if (type == EnumPlayerPacket.FollowerState) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Follower) {
                return;
            }
            NoppesUtilPlayer.changeFollowerState(player, npc);
            Server.sendData(player, EnumPacketClient.GUI_DATA, npc.roleInterface.writeToNBT(new NBTTagCompound()));
        }
        else if (type == EnumPlayerPacket.RoleGet) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role == EnumRoleType.None) {
                return;
            }
            Server.sendData(player, EnumPacketClient.GUI_DATA, npc.roleInterface.writeToNBT(new NBTTagCompound()));
        }
        else if (type == EnumPlayerPacket.Transport) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Transporter) {
                return;
            }
            NoppesUtilPlayer.transport(player, npc, Server.readString(buffer));
        }
        else if (type == EnumPlayerPacket.BankUpgrade) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Bank) {
                return;
            }
            NoppesUtilPlayer.bankUpgrade(player, npc);
        }
        else if (type == EnumPlayerPacket.BankUnlock) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Bank) {
                return;
            }
            NoppesUtilPlayer.bankUnlock(player, npc);
        }
        else if (type == EnumPlayerPacket.BankSlotOpen) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null || npc.advanced.role != EnumRoleType.Bank) {
                return;
            }
            final int slot = buffer.readInt();
            final int bankId = buffer.readInt();
            final BankData data = PlayerDataController.instance.getBankData((EntityPlayer)player, bankId).getBankOrDefault(bankId);
            data.openBankGui((EntityPlayer)player, npc, bankId, slot);
        }
        else if (type == EnumPlayerPacket.Dialog) {
            final EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((EntityPlayer)player);
            if (npc == null) {
                return;
            }
            NoppesUtilPlayer.dialogSelected(buffer.readInt(), buffer.readInt(), player, npc);
        }
        else if (type == EnumPlayerPacket.CheckQuestCompletion) {
            final PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData((EntityPlayer)player).questData;
            playerdata.checkQuestCompletion((EntityPlayer)player, null);
        }
        else if (type == EnumPlayerPacket.QuestLog) {
            NoppesUtilPlayer.sendQuestLogData(player);
        }
        else if (type == EnumPlayerPacket.QuestCompletion) {
            NoppesUtilPlayer.questCompletion(player, buffer.readInt());
        }
        else if (type == EnumPlayerPacket.FactionsGet) {
            final PlayerFactionData data2 = PlayerDataController.instance.getPlayerData((EntityPlayer)player).factionData;
            Server.sendData(player, EnumPacketClient.GUI_DATA, data2.getPlayerGuiData());
        }
        else if (type == EnumPlayerPacket.MailGet) {
            final PlayerMailData data3 = PlayerDataController.instance.getPlayerData((EntityPlayer)player).mailData;
            Server.sendData(player, EnumPacketClient.GUI_DATA, data3.saveNBTData(new NBTTagCompound()));
        }
        else if (type == EnumPlayerPacket.MailDelete) {
            final long time = buffer.readLong();
            final String username = Server.readString(buffer);
            final PlayerMailData data4 = PlayerDataController.instance.getPlayerData((EntityPlayer)player).mailData;
            final Iterator<PlayerMail> it = data4.playermail.iterator();
            while (it.hasNext()) {
                final PlayerMail mail = it.next();
                if (mail.time == time && mail.sender.equals(username)) {
                    it.remove();
                }
            }
            Server.sendData(player, EnumPacketClient.GUI_DATA, data4.saveNBTData(new NBTTagCompound()));
        }
        else if (type == EnumPlayerPacket.MailSend) {
            if (!(player.openContainer instanceof ContainerMail)) {
                return;
            }
            final String username2 = PlayerDataController.instance.hasPlayer(Server.readString(buffer));
            if (username2.isEmpty()) {
                NoppesUtilServer.sendGuiError((EntityPlayer)player, 0);
                return;
            }
            final PlayerMail mail2 = new PlayerMail();
            String s = player.getDisplayName();
            if (!s.equals(player.getCommandSenderName())) {
                s = s + "(" + player.getCommandSenderName() + ")";
            }
            mail2.readNBT(Server.readNBT(buffer));
            mail2.sender = s;
            mail2.items = ((ContainerMail)player.openContainer).mail.items;
            if (mail2.subject.isEmpty()) {
                NoppesUtilServer.sendGuiError((EntityPlayer)player, 1);
                return;
            }
            PlayerDataController.instance.addPlayerMessage(username2, mail2);
            final NBTTagCompound comp = new NBTTagCompound();
            comp.setString("username", username2);
            NoppesUtilServer.sendGuiClose(player, 1, comp);
        }
        else if (type == EnumPlayerPacket.MailboxOpenMail) {
            final long time = buffer.readLong();
            final String username = Server.readString(buffer);
            player.closeContainer();
            final PlayerMailData data4 = PlayerDataController.instance.getPlayerData((EntityPlayer)player).mailData;
            for (final PlayerMail mail : data4.playermail) {
                if (mail.time == time && mail.sender.equals(username)) {
                    ContainerMail.staticmail = mail;
                    player.openGui((Object)CustomNpcs.instance, EnumGuiType.PlayerMailman.ordinal(), player.worldObj, 0, 0, 0);
                    break;
                }
            }
        }
        else if (type == EnumPlayerPacket.MailRead) {
            final long time = buffer.readLong();
            final String username = Server.readString(buffer);
            final PlayerMailData data4 = PlayerDataController.instance.getPlayerData((EntityPlayer)player).mailData;
            for (final PlayerMail mail : data4.playermail) {
                if (mail.time == time && mail.sender.equals(username)) {
                    mail.beenRead = true;
                    if (!mail.hasQuest()) {
                        continue;
                    }
                    PlayerQuestController.addActiveQuest(mail.getQuest(), (EntityPlayer)player);
                }
            }
        }
        else if (type == EnumPlayerPacket.SignSave) {
            final int x = buffer.readInt();
            final int y = buffer.readInt();
            final int z = buffer.readInt();
            final TileEntity tile = player.worldObj.getTileEntity(x, y, z);
            if (tile == null || !(tile instanceof TileBigSign)) {
                return;
            }
            final TileBigSign sign = (TileBigSign)tile;
            if (sign.canEdit) {
                sign.setText(Server.readString(buffer));
                sign.canEdit = false;
                player.worldObj.markBlockForUpdate(x, y, z);
            }
        }
        else if (type == EnumPlayerPacket.SaveBook) {
            final int x = buffer.readInt();
            final int y = buffer.readInt();
            final int z = buffer.readInt();
            final TileEntity tileentity = player.worldObj.getTileEntity(x, y, z);
            if (!(tileentity instanceof TileBook)) {
                return;
            }
            final TileBook tile2 = (TileBook)tileentity;
            if (tile2.book.getItem() == Items.written_book) {
                return;
            }
            final boolean sign2 = buffer.readBoolean();
            final ItemStack book = ItemStack.loadItemStackFromNBT(Server.readNBT(buffer));
            if (book == null) {
                return;
            }
            if (book.getItem() == Items.writable_book && !sign2 && ItemWritableBook.validBookPageTagContents(book.getTagCompound())) {
                tile2.book.setTagInfo("pages", (NBTBase)book.getTagCompound().getTagList("pages", 8));
            }
            if (book.getItem() == Items.written_book && sign2 && ItemEditableBook.validBookTagContents(book.getTagCompound())) {
                tile2.book.setTagInfo("author", (NBTBase)new NBTTagString(player.getCommandSenderName()));
                tile2.book.setTagInfo("title", (NBTBase)new NBTTagString(book.getTagCompound().getString("title")));
                tile2.book.setTagInfo("pages", (NBTBase)book.getTagCompound().getTagList("pages", 8));
                tile2.book.setItem(Items.written_book);
            }
        }
    }
}
