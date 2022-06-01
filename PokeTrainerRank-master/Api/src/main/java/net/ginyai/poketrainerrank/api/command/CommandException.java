package net.ginyai.poketrainerrank.api.command;

public class CommandException extends Exception{

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
