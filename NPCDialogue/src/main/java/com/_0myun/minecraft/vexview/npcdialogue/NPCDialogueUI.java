package com._0myun.minecraft.vexview.npcdialogue;

import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.betoncraft.betonquest.conversation.Conversation;
import pl.betoncraft.betonquest.conversation.ConversationIO;
import pl.betoncraft.betonquest.utils.PlayerConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCDialogueUI implements ConversationIO {
    static int buttonIndex = 10000;

    NPCDialogue plugin;
    FileConfiguration config;

    private VexGui gui;
    private VexImage contentImg;
    private VexImage drawImg;
    private VexPlayerDraw draw;
    private VexText npcName;
    private VexText npcResponse;

    private List<VexButton> buttonsView = new ArrayList<>();
    private List<VexImage> buttonsBackgroundView = new ArrayList<>();

    private Conversation con;
    private Player p;

    public NPCDialogueUI(Conversation con, String playerUUID) {
        this(playerUUID);
        this.con = con;
    }

    public NPCDialogueUI(String id) {
        this.p = PlayerConverter.getPlayer(id);
        plugin = NPCDialogue.plugin;
        config = plugin.getConfig();

        this.gui = new VexGui(config.getString("res.background.src"), 0, 0
                , config.getInt("res.background.w"), config.getInt("res.background.h")
                , config.getInt("res.background.w"), config.getInt("res.background.h"));
        this.contentImg = new VexImage(config.getString("res.content.src")
                , config.getInt("res.content.x"), config.getInt("res.content.y")
                , config.getInt("res.content.w"), config.getInt("res.content.h")
                , config.getInt("res.content.w"), config.getInt("res.content.h"));
        this.drawImg = new VexImage(config.getString("res.draw.src")
                , config.getInt("res.draw.x"), config.getInt("res.draw.y")
                , config.getInt("res.draw.w"), config.getInt("res.draw.h")
                , config.getInt("res.draw.w"), config.getInt("res.draw.h"));
        this.draw = new VexPlayerDraw(config.getInt("config.draw.x"), config.getInt("config.draw.y")
                , config.getInt("config.draw.scale"), p);
    }

    public NPCDialogueUI addButton(String title) {

        VexButton button = new VexButton(10001 + buttonIndex, title
                , config.getString("res.button.src"), config.getString("res.button.src")
                , config.getInt("res.button.x"), config.getInt("res.button.y") + (this.buttonsView.size() * config.getInt("config.button.interval"))
                , config.getInt("res.button.w"), config.getInt("res.button.h"));
        button.setFunction(new NPCdialogueButton(this.con, this.buttonsView.size() + 1));
        buttonIndex++;
        if (buttonIndex > Integer.MAX_VALUE - 10000) buttonIndex = 10000;
        VexImage background = new VexImage(config.getString("res.button.background")
                , config.getInt("res.button.background-x"), config.getInt("res.button.background-y") + (this.buttonsBackgroundView.size() * config.getInt("config.button.backgroundInterval"))
                , config.getInt("res.button.background-w"), config.getInt("res.button.background-h")
                , config.getInt("res.button.background-w"), config.getInt("res.button.background-h"));
        this.buttonsView.add(button);
        this.buttonsBackgroundView.add(background);
        return this;
    }

    public VexGui getGui() {
        this.gui.addAllComponents(Arrays.asList(
                this.contentImg
                , this.drawImg
                , this.draw
        ));
        this.gui.addAllComponents(this.buttonsBackgroundView);
        this.gui.addAllComponents(this.buttonsView);

        this.gui.addComponent(this.npcName);
        this.gui.addComponent(this.npcResponse);
        return this.gui;
    }

    @Override
    public void setNpcResponse(String npcName, String response) {
        this.npcName = new VexText(config.getInt("res.npc-name.x"), config.getInt("res.npc-name.y"), Arrays.asList(npcName));
        this.npcResponse = new VexText(config.getInt("res.npc-response.x"), config.getInt("res.npc-response.y"), Arrays.asList(response.split("\\|")));

    }

    @Override
    public void addPlayerOption(String option) {
        this.addButton(option);
    }

    @Override
    public void display() {
        VexViewAPI.openGui(this.p, this.getGui());
    }

    @Override
    public void clear() {
        this.buttonsView.clear();
        this.buttonsBackgroundView.clear();
        this.buttonsView = new ArrayList<>();
        this.buttonsBackgroundView = new ArrayList<>();
    }

    @Override
    public void end() {
        this.p.closeInventory();
    }
}
