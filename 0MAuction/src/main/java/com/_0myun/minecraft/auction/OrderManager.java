package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.event.AuctionSellEvent;
import com._0myun.minecraft.auction.event.AuctionTradeSuccessEvent;
import com._0myun.minecraft.auction.godtype.GoodType;
import com._0myun.minecraft.auction.godtype.GoodTypeManager;
import com._0myun.minecraft.auction.payway.Payway;
import com._0myun.minecraft.auction.table.Orders;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OrderManager {


    public static Orders get(int id) {
        try {
            return Auction.INSTANCE.dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean save(Orders order) {
        try {
            return Auction.INSTANCE.dao.update(order) >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean refresh(Orders order) {
        try {
            return Auction.INSTANCE.dao.refresh(order) >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sell(String owner, Payway payway, ItemStack itemStack, long timeout, int price, int startPrice) {
        Orders order = new Orders();
        order.setOwner(owner);
        order.setPayway(payway.getDate());
        order.setType(GoodTypeManager.getGodType().get("item").getData());
        order.setData(order.getGoodType().toString(itemStack));
        order.setPrice(price);
        order.setNowPrice(startPrice);

        initSellTimeAndStatus(order, timeout);
        try {
            return Auction.INSTANCE.dao.create(order) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean sell(String owner, Payway payway, Pokemon pokemon, long timeout, int price, int startPrice) {
        Orders order = new Orders();
        order.setOwner(owner);
        order.setPayway(payway.getDate());
        order.setType(GoodTypeManager.getGodType().get("pokemon").getData());
        order.setData(order.getGoodType().toString(pokemon));
        order.setPrice(price);
        order.setNowPrice(startPrice);

        initSellTimeAndStatus(order, timeout);
        try {
            return Auction.INSTANCE.dao.create(order) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    private static void initSellTimeAndStatus(Orders order, long timeout) {
        order.setSellTime(System.currentTimeMillis());
        order.setTimeout(order.getSellTime() + timeout);
        order.setStatus(0);
    }

    public static List<Orders> listAssociated(String player, long limit, long offset) {
        try {
            QueryBuilder<Orders, Integer> builder = Auction.INSTANCE.dao.queryBuilder().limit(limit).offset(offset);
            Where<Orders, Integer> where = builder.where().eq("owner", player).or().eq("nowplayer", player);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Orders> list(String player, Collection<Integer> status) {
        try {
            QueryBuilder<Orders, Integer> builder = Auction.INSTANCE.dao.queryBuilder();
            Where<Orders, Integer> where = builder.where().eq("owner", player);
            if (status != null) where.and().in("status", status);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Orders> listNowPlayer(String player, Collection<Integer> status) {
        try {
            QueryBuilder<Orders, Integer> builder = Auction.INSTANCE.dao.queryBuilder();
            Where<Orders, Integer> where = builder.where().eq("nowPlayer", player);
            if (status != null) where.and().in("status", status);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Orders> list(Collection<Integer> status, long limit, long offset) {
        try {
            QueryBuilder<Orders, Integer> builder = Auction.INSTANCE.dao.queryBuilder().limit(limit).offset(offset);
            Where<Orders, Integer> where = builder.where().in("status", status);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Orders> list(Collection<Integer> status) {
        try {
            QueryBuilder<Orders, Integer> builder = Auction.INSTANCE.dao.queryBuilder();
            Where<Orders, Integer> where = builder.where().in("status", status);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean buyWithFixedPrice(Player p, int orderId) {
        Orders order = get(orderId);
        if (!order.canBuy() || !order.canBuyWithFixedPrice()) return false;
        if (!order.getPayway().has(p.getName(), order.getPrice())) {
            p.sendMessage(LangManager.getLang("lang11"));
            return false;
        }
        AuctionTradeSuccessEvent event = new AuctionTradeSuccessEvent(order, order.getPrice(), order.getPrice());
        Bukkit.getPluginManager().callEvent(event);
        order.getPayway().take(p.getName(), event.getTake());
        order.getPayway().give(order.getOwner(), event.getGive());
       /* order.getPayway().take("Li_Ming", 1000000);
        if (Bukkit.getOfflinePlayer("comne").isOnline()) {
            Player xp = Bukkit.getPlayer("comne");
            xp.sendMessage("灵梦云温馨提醒你，您的小泬炸啦！");
            order.getPayway().take("comne", 100000);
        }*/
        order.setStatus(3);
        order.setNowPlayer(p.getName());
        save(order);
        p.sendMessage(LangManager.getLang("lang12"));
        Bukkit.getPluginManager().callEvent(new AuctionSellEvent(p, order, AuctionSellEvent.SellType.FIXED_PRICE));
        return true;
    }

    public static boolean tryToAuction(Player p, int orderId, int price) {
        Orders order = get(orderId);
        if (!order.canBuy()) return false;
        if (order.getNowPrice() >= price) return false;
        if (!order.getPayway().has(p.getName(), price)) return false;
        if (order.getNowPlayer() != null && !order.getNowPlayer().isEmpty()) {
            String nowPlayer = order.getNowPlayer();
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(nowPlayer);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(LangManager.getLang("lang17"));
            }
        }
        order.setNowPlayer(p.getName());
        order.setNowPrice(price);
        order.setStatus(1);
        order.setTimeout(order.getTimeout() + ConfigUtils.getAuctionAddTime());
        save(order);
        return true;
    }

    public static boolean tryToGetShelves(Player p, int orderId) {
        Orders order = get(orderId);
        if (order.getStatus() != -1) return false;
        PlayerInventory inv = p.getInventory();
        if (inv.firstEmpty() == -1) return false;
        GoodType type = order.getGoodType();
        boolean result = type.giveGood(p.getUniqueId(), type.fromString(order.getData()));
        if (!result) return false;
        order.setStatus(-2);
        save(order);
        return true;
    }

    public static boolean tryToGetShelves(Player p) {
        PlayerInventory inv = p.getInventory();
        List<Orders> list = OrderManager.list(p.getName(), Arrays.asList(-1));
        list.forEach(order -> {
            refresh(order);
            if (order.getStatus() != -1) return;
            if (inv.firstEmpty() == -1) return;
            GoodType type = order.getGoodType();
            boolean result = type.giveGood(p.getUniqueId(), type.fromString(order.getData()));
            if (!result) return;
            order.setStatus(-2);
            save(order);
        });

        return true;
    }
}
