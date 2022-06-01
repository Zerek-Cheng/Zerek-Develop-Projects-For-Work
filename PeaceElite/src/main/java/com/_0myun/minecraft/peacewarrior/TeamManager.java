package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.events.team.PWTeamInviteEvent;
import com._0myun.minecraft.peacewarrior.events.team.PWTeamJoinEvent;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 队伍管理
 * 如果组队了直接记录队长UUID
 */
public class TeamManager {
    /**
     * 自己所在队伍的队长
     *
     * @TIP 当自己是队长的时候VALUE也是自己的UUID
     * @KEY 玩家UUID
     * @Value 队长UUID
     */
    public static final HashMap<UUID, UUID> captain = new HashMap<>();
    /**
     * 组队申请
     */
    public static final List<String> apply = new ArrayList<>();

    /**
     * 解散队伍并且通知
     *
     * @param captainUUID
     */
    public static void dissoluteTeam(UUID captainUUID) {
    }

    /**
     * 获取队伍成员
     *
     * @param captainUUID
     * @return
     */
    public static List<UUID> getTeamMembers(UUID captainUUID) {
        List<UUID> members = new ArrayList<>();
        TeamManager.captain.forEach((k, v) -> {
            if (v.equals(captainUUID)) members.add(k);
        });
        return members;
    }

    public static boolean apply(UUID captainUUID, UUID memberUUID) {
        int teamMax = 0;
        try {
            teamMax = MapManager.getMapByName(DBManager.playerDataDao.queryForUUID(captainUUID).getMap()).getTeam_max();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (TeamManager.getTeamMembers(captainUUID).size() >= teamMax) {
            return false;
        }

        if (TeamManager.apply.contains(captainUUID.toString() + "-" + memberUUID.toString())) return true;
        try {
            TeamManager.apply.add(captainUUID.toString() + "-" + memberUUID.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bukkit.getPluginManager().callEvent(new PWTeamInviteEvent(Bukkit.getPlayer(captainUUID), memberUUID));
        return true;
    }

    public static boolean accept(UUID memberUUID, UUID captainUUID) {
        if (!TeamManager.apply.contains(captainUUID.toString() + "-" + memberUUID.toString())) return false;//未收到请求
        if (!Bukkit.getOfflinePlayer(captainUUID).isOnline()) return false;//队长不在线
        if (TeamManager.captain.containsKey(captainUUID) && !TeamManager.captain.get(captainUUID).equals(captainUUID))
            return false;//邀请人不是队长
        if (TeamManager.captain.containsKey(memberUUID) && !TeamManager.captain.get(memberUUID).equals(memberUUID))
            return false;//已经加入队伍且队长不是自己
        quitTeam(memberUUID);
        int teamMax = 0;
        try {
            teamMax = MapManager.getMapByName(DBManager.playerDataDao.queryForUUID(captainUUID).getMap()).getTeam_max();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (TeamManager.getTeamMembers(captainUUID).size() >= teamMax) {
            return false;
        }
        if (!TeamManager.captain.containsKey(captainUUID)) {
            TeamManager.captain.put(captainUUID, captainUUID);
        }
        TeamManager.apply.remove(captainUUID.toString() + "-" + memberUUID.toString());
        TeamManager.captain.put(memberUUID, captainUUID);
        Bukkit.getPluginManager().callEvent(new PWTeamJoinEvent(Bukkit.getPlayer(captainUUID), memberUUID));
        return true;
    }

    public static void quitTeam(UUID memberUUID) {
        TeamManager.captain.remove(memberUUID);
    }
}
