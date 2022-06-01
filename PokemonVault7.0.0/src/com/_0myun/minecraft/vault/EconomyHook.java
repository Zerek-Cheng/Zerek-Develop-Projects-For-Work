package com._0myun.minecraft.vault;

import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.ArrayList;
import java.util.List;

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
        PlayerPartyStorage account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        return account != null ? account.getMoney() : 0;
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        PlayerPartyStorage account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        if (account == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "");
        }
        double balance = account.getMoney();
        if (amount < 0.0D) {
            return new EconomyResponse(0.0D, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (balance - amount < 0.0D) {
            return new EconomyResponse(0.0D, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (CatPixelmonSync.instance.changeMoney(account, (int) (amount * -1.0D))) {
            return new EconomyResponse(amount, account.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0D, balance, EconomyResponse.ResponseType.FAILURE, "");
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        PlayerPartyStorage account = CatPixelmonSync.instance.getPixelmonBankAccount(playerName);
        if (account == null) {
            return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.FAILURE, "");
        }
        double balance = account.getMoney();
        if (amount < 0.0D) {
            return new EconomyResponse(0.0D, balance, EconomyResponse.ResponseType.FAILURE, "");
        }
        if (CatPixelmonSync.instance.changeMoney(account, (int) amount)) {
            return new EconomyResponse(amount, account.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
        }
        return new EconomyResponse(0.0D, account.getMoney(), EconomyResponse.ResponseType.FAILURE, "");
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
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "not support bank accounts!");
    }

    public List<String> getBanks() {
        return new ArrayList();
    }

    public boolean hasBankSupport() {
        return false;
    }

    public boolean hasAccount(String playerName) {
        return CatPixelmonSync.instance.getPixelmonBankAccount(playerName) != null;
    }

    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    public int fractionalDigits() {
        return -1;
    }

    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }
}
