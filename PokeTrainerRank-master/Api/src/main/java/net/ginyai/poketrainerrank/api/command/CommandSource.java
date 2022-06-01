package net.ginyai.poketrainerrank.api.command;

public class CommandSource {
    private final String name;
    private final Object source;

    public CommandSource(String name, Object source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public <T> T getSource() {
        return (T)source;
    }
}
