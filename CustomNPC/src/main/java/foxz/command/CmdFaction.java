package foxz.command;

import foxz.commandhelper.*;
import java.util.*;
import noppes.npcs.controllers.*;
import foxz.commandhelper.annotations.*;
import foxz.commandhelper.permissions.*;

@Command(name = "faction", desc = "operations about relationship between player and faction")
public class CmdFaction extends ChMcLogger
{
    public String playername;
    public Faction selectedFaction;
    public List<PlayerData> data;
    
    public CmdFaction(final Object sender) {
        super(sender);
    }
    
    @SubCommand(desc = "Add points", usage = "<points>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean add(final String[] args) {
        int points;
        try {
            points = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("Must be an integer");
            return false;
        }
        final int factionid = this.selectedFaction.id;
        for (final PlayerData playerdata : this.data) {
            final PlayerFactionData playerfactiondata = playerdata.factionData;
            playerfactiondata.increasePoints(factionid, points);
        }
        return true;
    }
    
    @SubCommand(desc = "Substract points", usage = "<points>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean subtract(final String[] args) {
        int points;
        try {
            points = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("Must be an integer");
            return false;
        }
        final int factionid = this.selectedFaction.id;
        for (final PlayerData playerdata : this.data) {
            final PlayerFactionData playerfactiondata = playerdata.factionData;
            playerfactiondata.increasePoints(factionid, -points);
        }
        return true;
    }
    
    @SubCommand(desc = "Reset points to default", usage = "", permissions = { OpOnly.class })
    public Boolean reset(final String[] args) {
        for (final PlayerData playerdata : this.data) {
            playerdata.factionData.factionData.put(this.selectedFaction.id, this.selectedFaction.defaultPoints);
        }
        return true;
    }
    
    @SubCommand(desc = "Set points", usage = "<points>", permissions = { OpOnly.class, ParamCheck.class })
    public Boolean set(final String[] args) {
        int points;
        try {
            points = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException ex) {
            this.sendmessage("Must be an integer");
            return false;
        }
        for (final PlayerData playerdata : this.data) {
            final PlayerFactionData playerfactiondata = playerdata.factionData;
            playerfactiondata.factionData.put(this.selectedFaction.id, points);
        }
        return true;
    }
    
    @SubCommand(desc = "Drop relationship", usage = "", permissions = { OpOnly.class })
    public Boolean drop(final String[] args) {
        for (final PlayerData playerdata : this.data) {
            playerdata.factionData.factionData.remove(this.selectedFaction.id);
        }
        return true;
    }
}
