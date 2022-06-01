package net.ginyai.poketrainerrank.api.command;

import net.ginyai.poketrainerrank.api.util.Pos;

import javax.annotation.Nullable;
import java.util.List;

public interface ICommand {

    void process(CommandSource source, String arguments) throws CommandException;

    List<String> tabComp(CommandSource source, String arguments, @Nullable Pos pos);

}
