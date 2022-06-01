package com._0myun.minecraft.peacewarrior.services;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.MapManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com._0myun.minecraft.peacewarrior.utils.Replacer;
import com.comphenix.packetwrapper.WrapperPlayServerTileEntityData;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import lombok.var;
import org.bukkit.entity.Player;

import java.util.List;

public class Hall {
    public static void sendHallSignInfo(Player p, Sign sign) {
        try {

            var packet = new WrapperPlayServerTileEntityData(ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TILE_ENTITY_DATA));
            BlockPosition pos = new BlockPosition(sign.getX(), sign.getY(), sign.getZ());
            packet.setLocation(pos);

            NbtCompound signNbt = NbtFactory.ofCompound("AnyStringHere");
            signNbt.put("id", "minecraft:sign");
            signNbt.put("x", pos.getX());
            signNbt.put("y", pos.getY());
            signNbt.put("z", pos.getZ());

            packet.setAction(9);

            List<String> lines = R.INSTANCE.langs("lang1");
            try {
                lines = Replacer.replace(lines, MapManager.getMapByName(sign.getMap()));

                lines = Replacer.replace(lines, DBManager.battleDao.queryForMap(sign.getMap()));
                Battle def = new Battle();
                def.setPlayer_amount(0);
                def.setStat(Battle.Stat.STOP);
                lines = Replacer.replace(lines, def);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                signNbt.put("Text" + (i + 1), "{\"text\":\"" + line + "\"}");
            }

            packet.setNbtData(signNbt);

            packet.sendPacket(p);

            //Block bukkitBlock = Bukkit.getWorld(sign.getWorld()).getBlockAt(sign.getX(), sign.getY(), sign.getZ());

            //       packet.set
        }catch (Exception ex){
            System.err.println("在发送告示牌数据时出现异常..可能是版本不支持!");
        }
    }
}
