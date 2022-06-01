package com._0myun.minecraft.treasuremap;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public final class R extends JavaPlugin {
    public static R INSTANCE;
    @Getter
    private HashMap<UUID, String> searching = new HashMap<>();
    @Getter
    private HashMap<UUID, Position> position = new HashMap<>();

    static {
        ConfigurationSerialization.registerClass(Position.class, "com._0myun.minecraft.treasuremap.Position");
        ConfigurationSerialization.registerClass(RandCommand.class, "com._0myun.minecraft.treasuremap.RandCommand");
        ConfigurationSerialization.registerClass(TreasureMap.class, "com._0myun.minecraft.treasuremap.TreasureMap");
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        Player p = (Player) sender;
        try {
            if (args.length == 2 && args[0].equalsIgnoreCase("reward")) {
                TreasureMap map = (TreasureMap) getConfig().get("map." + args[1]);
                if (map == null) {
                    sender.sendMessage("地图不存在");
                    return true;
                }
                giveReward(p, args[1]);
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("setitem")) {
                ItemStack itemInHand = p.getItemInHand().clone();
                itemInHand.setAmount(1);
                TreasureMap map = (TreasureMap) getConfig().get("map." + args[1]);
                if (map == null) {
                    sender.sendMessage("地图不存在");
                    return true;
                }
                map.setItem(StreamSerializer.getDefault().serializeItemStack(itemInHand));
                getConfig().set("map." + args[1], map);
                sender.sendMessage("ok");
                saveConfig();
                return true;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("getitem")) {
                TreasureMap map = (TreasureMap) getConfig().get("map." + args[1]);
                if (map == null) {
                    sender.sendMessage("地图不存在");
                    return true;
                }
                ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(map.getItem());
                NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(itemStack));
                nbt.put("com._0myun.minecraft.treasuremap.R.map", args[1]);
                NbtFactory.setItemTag(itemStack, nbt);
                p.getInventory().addItem(itemStack);
                sender.sendMessage("ok");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
                if (!getSearching().containsKey(p.getUniqueId())) return true;
                getSearching().remove(p.getUniqueId());
                getPosition().remove(p.getUniqueId());
                p.sendMessage(lang("lang3"));
                boolean op = p.isOp();
                try {
                    if (!op) p.setOp(true);
                    for (String cmd : getConfig().getStringList("cancel-commands")) {
                        p.performCommand(cmd.replace("{player}", p.getName()));
                    }
                } finally {
                    if (!op) p.setOp(false);
                }
            }
        } catch (Exception ex) {
            p.sendMessage("操作有异常！（可能是数据未设定或设定错误）");
            ex.printStackTrace();
        }
        return true;
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang);
    }

    public void sendTitle(Player p) {
        if (!searching.containsKey(p.getUniqueId()) || !position.containsKey(p.getUniqueId())) return;
        TreasureMap map = (TreasureMap) getConfig().get("map." + this.searching.get(p.getUniqueId()));
        Position pos = this.position.get(p.getUniqueId());
        p.sendTitle(map.getTitle(), map.getSubtitle()
                .replace("{world}", pos.getWorld())
                .replace("{x}", String.valueOf(pos.getX()))
                .replace("{y}", String.valueOf(pos.getY()))
                .replace("{z}", String.valueOf(pos.getZ())));
    }

    public void sendActionBar(Player player, WrappedChatComponent text) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer chatPacket = protocolManager.createPacket(PacketType.Play.Server.CHAT);
        chatPacket.getChatComponents().write(0, text);
        chatPacket.getChatTypes().write(0, EnumWrappers.ChatType.GAME_INFO);
        try {
            protocolManager.sendServerPacket(player, chatPacket);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public RandCommand randCommand(List<RandCommand> prizes) {
        int lastScope = 0;
        // 洗牌，打乱奖品次序
        Collections.shuffle(prizes);
        Map<Integer, int[]> prizeScopes = new HashMap<Integer, int[]>();
        int i = 0;
        for (RandCommand prize : prizes) {
            int prizeId = i;
            // 划分区间
            int currentScope = lastScope + BigDecimal.valueOf(prize.getRand()).multiply(new BigDecimal(1000000)).intValue();
            prizeScopes.put(prizeId, new int[]{lastScope + 1, currentScope});

            lastScope = currentScope;
            i++;
        }

        // 获取1-1000000之间的一个随机数
        int luckyNumber = new Random().nextInt(1000000);
        int luckyPrizeId = 0;
        // 查找随机数所在的区间
        if ((null != prizeScopes) && !prizeScopes.isEmpty()) {
            Set<Map.Entry<Integer, int[]>> entrySets = prizeScopes.entrySet();
            for (Map.Entry<Integer, int[]> m : entrySets) {
                int key = m.getKey();
                if (luckyNumber >= m.getValue()[0] && luckyNumber <= m.getValue()[1]) {
                    luckyPrizeId = key;
                    break;
                }
            }
        }
        return prizes.get(luckyPrizeId);
    }


    public void giveReward(Player p, String mapName) {
        TreasureMap map = (TreasureMap) getConfig().get("map." + mapName);
        List<RandCommand> commands = map.getCommand();
        if (Math.random() > map.getReward_1()) {
            p.sendMessage(map.getReward_none());
            return;
        }
        RandCommand randCommand = randCommand(commands);
        runCommand(p, randCommand.getCmd());
        p.sendMessage(randCommand.msg.replace("{player}", p.getName()));

        if (Math.random() > map.getReward_2()) {
            //   p.sendMessage(map.getReward_none());
            return;
        }
        p.sendMessage(map.getReward_more().replace("{player}", p.getName()));
        randCommand = randCommand(commands);
        runCommand(p, randCommand.getCmd());
        p.sendMessage(randCommand.msg.replace("{player}", p.getName()));

        if (Math.random() > map.getReward_3()) {
            //  p.sendMessage(map.getReward_none());
            return;
        }
        p.sendMessage(map.getReward_more().replace("{player}", p.getName()));
        randCommand = randCommand(commands);
        runCommand(p, randCommand.getCmd());
        p.sendMessage(randCommand.msg.replace("{player}", p.getName()));
    }

    public void runCommand(Player p, String command) {
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            p.performCommand(command.replace("{player}", p.getName()));
        } finally {
            if (!op) p.setOp(false);
        }
    }
}
