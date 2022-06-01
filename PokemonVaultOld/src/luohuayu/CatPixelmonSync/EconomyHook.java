/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount
 *  net.milkbowl.vault.economy.AbstractEconomy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  net.milkbowl.vault.economy.EconomyResponse$ResponseType
 */
package luohuayu.CatPixelmonSync;

import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import java.util.ArrayList;
import java.util.List;
import luohuayu.CatPixelmonSync.CatPixelmonSync;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyHook
        extends AbstractEconomy {
    private String name = "CatPixelmonSync";

    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return true;
    }

    public double getBalance(String playerName) {
        IPixelmonBankAccount account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        return account != null ? account.getMoney() : 0;
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        IPixelmonBankAccount account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        if (account == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "");
        }
        double balance = account.getMoney();
        if (amount < 0.0) {
            return new EconomyResponse(0.0, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (balance - amount < 0.0) {
            return new EconomyResponse(0.0, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (CatPixelmonSync.instance.changeMoney(account, (int)(amount * -1.0))) {
            return new EconomyResponse(amount, (double)account.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0, balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        IPixelmonBankAccount account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        if (account == null) {
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "");
        }
        double balance = account.getMoney();
        if (amount < 0.0) {
            return new EconomyResponse(0.0, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (CatPixelmonSync.instance.changeMoney(account, (int)amount)) {
            return new EconomyResponse(amount, (double)account.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0, (double)account.getMoney(), EconomyResponse.ResponseType.FAILURE, "");
    }

    public String currencyNamePlural() {
        return "";
    }

    public String currencyNameSingular() {
        return "";
    }

    public String format(double amount) {
        return String.valueOf(amount);
    }

    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public boolean has(String playerName, double amount) {
        if (this.getBalance(playerName) >= amount) {
            return true;
        }
        return false;
    }

    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    public boolean hasBankSupport() {
        return false;
    }

    public boolean hasAccount(String playerName) {
        if (CatPixelmonSync.instance.getPixelmonBankAccount(playerName) != null) {
            return true;
        }
        return false;
    }

    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    public int fractionalDigits() {
        return -1;
    }

    public boolean hasAccount(String playerName, String worldName) {
        return this.hasAccount(playerName);
    }

    public double getBalance(String playerName, String world) {
        return this.getBalance(playerName);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return this.has(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return this.withdrawPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return this.depositPlayer(playerName, amount);
    }

    public boolean createPlayerAccount(String playerName, String worldName) {
        return this.createPlayerAccount(playerName);
    }
}

