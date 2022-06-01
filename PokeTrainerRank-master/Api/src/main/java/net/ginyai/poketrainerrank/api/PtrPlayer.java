package net.ginyai.poketrainerrank.api;

import net.ginyai.poketrainerrank.api.command.CommandSource;

import java.util.UUID;

public class PtrPlayer extends CommandSource {
    private UUID id;

    public PtrPlayer(String name, UUID id, Object source) {
        super(name, source);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
