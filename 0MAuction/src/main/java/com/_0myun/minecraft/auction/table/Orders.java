package com._0myun.minecraft.auction.table;

import com._0myun.minecraft.auction.Auction;
import com._0myun.minecraft.auction.LangManager;
import com._0myun.minecraft.auction.PokemonUtils;
import com._0myun.minecraft.auction.godtype.GoodType;
import com._0myun.minecraft.auction.godtype.GoodTypeManager;
import com._0myun.minecraft.auction.godtype.ItemGood;
import com._0myun.minecraft.auction.godtype.PokemonGood;
import com._0myun.minecraft.auction.payway.Payway;
import com._0myun.minecraft.auction.payway.PaywayManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@DatabaseTable(tableName = "Orders")
public class Orders {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    private int id;
    /**
     * 上架者
     */
    @DatabaseField(columnName = "owner", dataType = DataType.STRING)
    private String owner;
    /**
     * 货币类型
     */
    @DatabaseField(columnName = "payway", dataType = DataType.STRING, canBeNull = false, defaultValue = "gold")
    private String payway;
    /**
     * 物品类型
     */
    @DatabaseField(columnName = "type", dataType = DataType.STRING, canBeNull = false, defaultValue = "item")
    private String type;
    /**
     * 物品/精灵数据
     */
    @DatabaseField(columnName = "data", dataType = DataType.STRING, canBeNull = false)
    private String data;
    /**
     * 上架时间
     */
    @DatabaseField(columnName = "sellTime", dataType = DataType.LONG)
    private long sellTime;
    /**
     * 超时时间
     */
    @DatabaseField(columnName = "timeout", dataType = DataType.LONG)
    private long timeout;
    /**
     * 一口价
     */
    @DatabaseField(columnName = "price", dataType = DataType.INTEGER, defaultValue = "100", canBeNull = false)
    private int price;
    /**
     * 现在价格
     */
    @DatabaseField(columnName = "nowPrice", dataType = DataType.INTEGER, defaultValue = "100", canBeNull = false)
    private int nowPrice;
    /**
     * 当前最高价玩家
     */
    @DatabaseField(columnName = "nowPlayer", dataType = DataType.STRING)
    private String nowPlayer;
    /**
     * 是否结束
     * -2 到期已取出
     * -1 到期待取出
     * 0 新上架
     * 1 竞拍出价
     * 2 成交(竞拍)
     * 3 成交(一口价)
     * 10 成交已发货
     */
    @DatabaseField(columnName = "status", dataType = DataType.INTEGER, defaultValue = "0", canBeNull = false)
    private int status;

    public void save() throws SQLException {
        Auction.INSTANCE.getDao().update(this);
    }

    public Payway getPayway() {
        return PaywayManager.getPayway().get(this.payway);
    }

    public GoodType getGoodType() {
        return GoodTypeManager.getGodType().get(this.type);
    }

    public boolean canBuy() {
        return this.status == 0 || this.status == 1;
    }

    public boolean canBuyWithFixedPrice() {
        return this.nowPrice <= this.price;
    }

    public ItemStack getItemShow() {
        try {

            GoodType goodType = this.getGoodType();
            ItemStack show = null;
            Pokemon pokemon = null;
            if (goodType.getData().equalsIgnoreCase("item")) {
                show = new ItemGood().fromString(getData());
            }
            if (goodType.getData().equalsIgnoreCase("pokemon")) {
                pokemon = new PokemonGood().fromString(getData());
                show = PokemonUtils.getPokemonPhotoItem(pokemon);
            }
            //if (!show.hasItemMeta()) return show;
            ItemMeta itemMeta = show.getItemMeta();
            List<String> lores = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

            if (goodType.getData().equalsIgnoreCase("pokemon")) {
                lores.addAll(LangManager.getLangs("lang26"));
                lores = PokemonUtils.pokemonReplace(lores, pokemon);
            }

            lores.add(LangManager.getLang("lang22"));//  lang22: "当前一口价：{buynowprice}"
            lores.add(LangManager.getLang("lang23"));//  lang23: "当前竞拍价：{currentprice}"
            lores.add(LangManager.getLang("lang24"));//  lang24: "当前出价玩家：{highestbidder}"
            lores.add(LangManager.getLang("lang27"));//  lang27: "剩余时间：{timeleft}"
            lores.addAll(LangManager.getLangs("lang25"));//  lang25: "左键以一口价购买,右键进入竞拍模式"

            for (int i = 0; i < lores.size(); i++) {
                String lore = lores.get(i);
                lore = lore.replace("{buynowprice}", String.valueOf(getPrice()))
                        .replace("{currentprice}", String.valueOf(getNowPrice()))
                        .replace("{highestbidder}", getNowPlayer() != null && !getNowPlayer().isEmpty() ? getNowPlayer() : "暂无")
                        .replace("{owner}", this.getOwner())
                        .replace("{timeleft}", formatDuring(getTimeout() - System.currentTimeMillis()));
                lores.set(i, lore);
            }

            itemMeta.setLore(lores);
            show.setItemMeta(itemMeta);
            return show;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ItemStack(Material.AIR);
        }
    }

    public ItemStack getItemLogShow() {
        GoodType goodType = this.getGoodType();
        ItemStack show = null;
        Pokemon pokemon = null;
        if (goodType.getData().equalsIgnoreCase("item")) {
            show = new ItemGood().fromString(getData());
        }
        if (goodType.getData().equalsIgnoreCase("pokemon")) {
            pokemon = new PokemonGood().fromString(getData());
            show = PokemonUtils.getPokemonPhotoItem(pokemon);
        }

        ItemMeta itemMeta = show.getItemMeta();
        List<String> lores = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

        if (goodType.getData().equalsIgnoreCase("pokemon")) {
            lores.addAll(LangManager.getLangs("lang26"));
            lores = PokemonUtils.pokemonReplace(lores, pokemon);
        }

        lores.addAll(LangManager.getLangs("lang32"));

        for (int i = 0; i < lores.size(); i++) {
            String lore = lores.get(i);
            lore = lore.replace("{buynowprice}", String.valueOf(getPrice()))
                    .replace("{id}", String.valueOf(getId()))
                    .replace("{owner}", getOwner())
                    .replace("{currentprice}", String.valueOf(getNowPrice()))
                    .replace("{status}", transStatus(getStatus()))
                    .replace("{highestbidder}", getNowPlayer() != null && !getNowPlayer().isEmpty() ? getNowPlayer() : "无");
            lores.set(i, lore);
        }

        itemMeta.setLore(lores);
        show.setItemMeta(itemMeta);
        return show;
    }

    public static String transStatus(int status) {
        if (status == -2) return "到期已取出";
        if (status == -1) return "到期";
        if (status == 0) return "已上架";

        if (status == 1) return "已出价";
        if (status == 2) return "交易成功(竞拍成交)";
        if (status == 3) return "交易成功(一口价)";
        if (status == 10) return "交易成功 发货成功";
        return "未知" + status;
    }

    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " 天 " + hours + " 时 " + minutes + " 钟 "
                + seconds + " 秒 ";
    }
}
