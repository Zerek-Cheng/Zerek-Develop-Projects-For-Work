//
// Decompiled by Procyon v0.5.30
//

package me.dasfaust.gm.trade;

import me.dasfaust.gm.Core;
import me.dasfaust.gm.StorageHelper;
import me.dasfaust.gm.config.Config;
import me.dasfaust.gm.menus.MarketViewer;
import me.dasfaust.gm.menus.Menus;
import me.dasfaust.gm.tools.LocaleHandler;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ListingsHelper {
    public static void buy(final MarketListing listing, final UUID buyer) throws TransactionException {
        final OfflinePlayer player = Core.instance.getServer().getOfflinePlayer(buyer);
        final OfflinePlayer seller = Core.instance.getServer().getOfflinePlayer(listing.seller);
        if (!Core.instance.econ().has(player, listing.price)) {
            throw new TransactionException(LocaleHandler.get().get("general_no_money"));
        }
        if (Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            if (!player.isOnline()) {
                throw new TransactionException("Buyer is offline");
            }
        } else if (StorageHelper.isStockFull(buyer)) {
            throw new TransactionException(LocaleHandler.get().get("general_full_stock"));
        }
        StockedItem buyerStock = null;
        StockedItem stock = null;
        if (!Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            stock = StorageHelper.stockFor(listing.seller, listing.itemId);
            if (stock == null || !Core.instance.storage().verify(MarketListing.class, listing.id) || !Core.instance.storage().verify(StockedItem.class, stock.id)) {
                throw new TransactionException(LocaleHandler.get().get("general_no_stock"));
            }
            if (stock.amount < listing.amount) {
                throw new TransactionException(LocaleHandler.get().get("general_no_stock"));
            }
            buyerStock = StorageHelper.stockFor(buyer, listing.itemId);
            if (buyerStock != null && buyerStock.amount + listing.amount > Core.instance.config().get(Config.Defaults.STOCK_SLOTS_SIZE)) {
                throw new TransactionException(LocaleHandler.get().get("general_full_stock"));
            }
        }
        if (!Core.instance.econ().withdrawPlayer(player, listing.price).transactionSuccess()) {
            throw new TransactionException(LocaleHandler.get().get("general_bad_econ_response"));
        }
        double finalPrice = listing.price;
        final double cutPercentage = Core.instance.config().get(Config.Defaults.LISTINGS_CUT_AMOUNT);
        if (cutPercentage > 0.0) {
            finalPrice = listing.price - listing.price * cutPercentage;
        }
        if (!Core.instance.econ().depositPlayer(seller, round(finalPrice)).transactionSuccess()) {
            throw new TransactionException(LocaleHandler.get().get("general_bad_econ_response"));
        }
        log(listing.seller.toString(), buyer.toString(), String.valueOf(listing.price), new SimpleDateFormat().format(new Date()), String.valueOf(listing.itemId));

        if (!Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            if (buyerStock == null) {
                final StockedItem _stock = new StockedItem();
                _stock.amount = listing.amount;
                _stock.creationTime = System.currentTimeMillis();
                _stock.itemId = listing.itemId;
                _stock.owner = buyer;
                _stock.world = UUID.randomUUID();
                Core.instance.storage().store(_stock);
            } else {
                StorageHelper.updateStockAmount(buyerStock, buyerStock.amount + listing.amount);
            }
            if (stock.amount > listing.amount) {
                StorageHelper.updateStockAmount(stock, stock.amount - listing.amount);
            } else {
                Core.instance.storage().removeObject(StockedItem.class, stock.id);
                Core.instance.storage().removeObject(MarketListing.class, listing.id);
            }
            if (stock.amount > listing.amount) {
                Core.instance.storage().removeObject(MarketListing.class, listing.id);
            }
        } else {
            Core.instance.storage().removeObject(MarketListing.class, listing.id);
        }
        Core.instance.handler().rebuildAllMenus(Menus.MENU_LISTINGS);
        final MarketViewer viewer = Core.instance.handler().getViewer(listing.seller);
        if (viewer != null) {
            viewer.reset().buildMenu();
        }
    }

    public static void buy(final ServerListing listing, final UUID buyer) throws TransactionException {
        final OfflinePlayer player = Core.instance.getServer().getOfflinePlayer(buyer);
        if (!Core.instance.econ().has(player, listing.price)) {
            throw new TransactionException(LocaleHandler.get().get("general_no_money"));
        }
        if (Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            if (!player.isOnline()) {
                throw new TransactionException("Buyer is offline");
            }
        } else if (StorageHelper.isStockFull(buyer)) {
            throw new TransactionException(LocaleHandler.get().get("general_full_stock"));
        }
        StockedItem buyerStock = null;
        if (!Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            buyerStock = StorageHelper.stockFor(buyer, listing.itemId);
            if (buyerStock != null && buyerStock.amount + listing.amount > Core.instance.config().get(Config.Defaults.STOCK_SLOTS_SIZE)) {
                throw new TransactionException(LocaleHandler.get().get("general_full_stock"));
            }
        }
        if (!Core.instance.econ().withdrawPlayer(player, listing.price).transactionSuccess()) {
            throw new TransactionException(LocaleHandler.get().get("general_bad_econ_response"));
        }
        log("SERVER", buyer.toString(), String.valueOf(listing.price), new SimpleDateFormat().format(new Date()), String.valueOf(listing.itemId));
        if (!Core.instance.config().get(Config.Defaults.DISABLE_STOCK)) {
            if (buyerStock == null) {
                final StockedItem _stock = new StockedItem();
                _stock.amount = listing.amount;
                _stock.creationTime = System.currentTimeMillis();
                _stock.itemId = listing.itemId;
                _stock.owner = buyer;
                _stock.world = UUID.randomUUID();
                Core.instance.storage().store(_stock);
            } else {
                StorageHelper.updateStockAmount(buyerStock, buyerStock.amount + listing.amount);
            }
        }
    }

    public static double round(final double a) {
        return new BigDecimal(a).setScale(2, 4).doubleValue();
    }

    public static class TransactionException extends Exception {
        public TransactionException(final String message) {
            super(message);
        }
    }

    /**
     * 出售者，购买者，价格，时间,物品id
     */
    public synchronized static void log(String seller, String buyer, String price, String time, String item) {
        try {
            Core core = Core.instance;
            File dir = core.getDataFolder();
            File file = new File(dir.getParent() + "/shabidaoju.log");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            item = core.storage().get(Long.valueOf(item)).base.getType().toString();
            osw.append(seller + "出售给" + buyer + "物品" + item + ",价格>" + price + ",时间>" + time + "\r\n");
            core.getLogger().info(seller + "出售给" + buyer + "物品" + item + ",价格>" + price + ",时间>" + time);
            try {
                osw.close();
                os.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
