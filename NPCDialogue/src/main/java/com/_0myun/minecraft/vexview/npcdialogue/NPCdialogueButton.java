package com._0myun.minecraft.vexview.npcdialogue;

import lk.vexview.gui.components.ButtonFunction;
import org.bukkit.entity.Player;
import pl.betoncraft.betonquest.conversation.Conversation;

public class NPCdialogueButton implements ButtonFunction {
    Conversation con;
    int index;

    public NPCdialogueButton(Conversation con, int index) {
        this.con = con;
        this.index = index;
    }

    @Override
    public void run(Player player) {
        this.con.passPlayerAnswer(this.index);
    }
}
