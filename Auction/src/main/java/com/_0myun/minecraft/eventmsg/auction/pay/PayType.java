package com._0myun.minecraft.eventmsg.auction.pay;

import lombok.var;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class PayType {
    static {
        types = new HashMap<>();
        register(0, new Gold());
        register(1, new PlayerPoint());
    }

    private static HashMap<Integer, PayType> types;

    public abstract boolean has(OfflinePlayer p, int amount);

    public abstract boolean take(OfflinePlayer p, int amount);

    public abstract boolean refund(OfflinePlayer p, int amount);

    public abstract String getName();

    public static void register(int id, PayType payType) {
        types.put(id, payType);
    }

    public static PayType get(int id) {
        return types.get(id);
    }

    public static boolean exist(int id) {
        return types.containsKey(id);
    }


    public HashMap<Integer, Integer> getPremiums() {
        var premiums = new HashMap<Integer, Integer>();
        premiums.put(0, 1);
        premiums.put(1, 10);
        premiums.put(2, 100);
        premiums.put(3, 1000);
        premiums.put(4, 10000);
        premiums.put(5, 100000);
        premiums.put(6, 1000000);
        premiums.put(7, 10000000);
        return premiums;
    }
}

