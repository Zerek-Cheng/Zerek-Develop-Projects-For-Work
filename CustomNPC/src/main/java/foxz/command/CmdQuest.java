package foxz.command;

import foxz.commandhelper.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import java.util.*;
import foxz.commandhelper.annotations.*;
import foxz.commandhelper.permissions.*;
import noppes.npcs.controllers.*;

@Command(name = "quest", usage = "help", desc = "Quest operations")
public class CmdQuest extends ChMcLogger
{
    public CmdQuest(final Object sender) {
        super(sender);
    }
    
    @SubCommand(desc = "Start a quest", usage = "<player> <quest>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean start(final String[] args) {
        final String playername = args[0];
        int questid;
        try {
            questid = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("QuestID must be an integer");
            return false;
        }
        final List<PlayerData> data = this.getPlayersData(playername);
        if (data.isEmpty()) {
            this.sendmessage(String.format("Unknow player '%s'", playername));
            return false;
        }
        final Quest quest = QuestController.instance.quests.get(questid);
        if (quest == null) {
            this.sendmessage("Unknown QuestID");
            return false;
        }
        for (final PlayerData playerdata : data) {
            if (playerdata.questData.activeQuests.containsKey(questid)) {
                continue;
            }
            final QuestData questdata = new QuestData(quest);
            playerdata.questData.activeQuests.put(questid, questdata);
            playerdata.saveNBTData(null);
            if (playerdata.player == null) {
                continue;
            }
            Server.sendData((EntityPlayerMP)playerdata.player, EnumPacketClient.MESSAGE, "quest.newquest", quest.title);
            Server.sendData((EntityPlayerMP)playerdata.player, EnumPacketClient.CHAT, "quest.newquest", ": ", quest.title);
        }
        return true;
    }
    
    @SubCommand(desc = "Finish a quest", usage = "<player> <quest>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean finish(final String[] args) {
        final String playername = args[0];
        int questid;
        try {
            questid = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("QuestID must be an integer");
            return false;
        }
        final List<PlayerData> data = this.getPlayersData(playername);
        if (data.isEmpty()) {
            this.sendmessage(String.format("Unknow player '%s'", playername));
            return false;
        }
        final Quest quest = QuestController.instance.quests.get(questid);
        if (quest == null) {
            this.sendmessage("Unknown QuestID");
            return false;
        }
        for (final PlayerData playerdata : data) {
            playerdata.questData.finishedQuests.put(questid, System.currentTimeMillis());
            playerdata.saveNBTData(null);
        }
        return true;
    }
    
    @SubCommand(desc = "Stop a started quest", usage = "<player> <quest>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean stop(final String[] args) {
        final String playername = args[0];
        int questid;
        try {
            questid = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("QuestID must be an integer");
            return false;
        }
        final List<PlayerData> data = this.getPlayersData(playername);
        if (data.isEmpty()) {
            this.sendmessage(String.format("Unknow player '%s'", playername));
            return false;
        }
        final Quest quest = QuestController.instance.quests.get(questid);
        if (quest == null) {
            this.sendmessage("Unknown QuestID");
            return false;
        }
        for (final PlayerData playerdata : data) {
            playerdata.questData.activeQuests.remove(questid);
            playerdata.saveNBTData(null);
        }
        return true;
    }
    
    @SubCommand(desc = "Removes a quest from finished and active quests", usage = "<player> <quest>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean remove(final String[] args) {
        final String playername = args[0];
        int questid;
        try {
            questid = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("QuestID must be an integer");
            return false;
        }
        final List<PlayerData> data = this.getPlayersData(playername);
        if (data.isEmpty()) {
            this.sendmessage(String.format("Unknow player '%s'", playername));
            return false;
        }
        final Quest quest = QuestController.instance.quests.get(questid);
        if (quest == null) {
            this.sendmessage("Unknown QuestID");
            return false;
        }
        for (final PlayerData playerdata : data) {
            playerdata.questData.activeQuests.remove(questid);
            playerdata.questData.finishedQuests.remove(questid);
            playerdata.saveNBTData(null);
        }
        return true;
    }
    
    @SubCommand(desc = "reload quests from disk", permissions = { OpOnly.class })
    public boolean reload(final String[] args) {
        new DialogController();
        return true;
    }
}
