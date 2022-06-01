package com._0myun.eventmsg.minecraft.redenvelope.bin;

import com._0myun.eventmsg.minecraft.redenvelope.LangUtil;
import com._0myun.eventmsg.minecraft.redenvelope.ConfigManager;
import com._0myun.eventmsg.minecraft.redenvelope.DataManager;
import com._0myun.eventmsg.minecraft.redenvelope.bin.api.Payer;
import com._0myun.eventmsg.minecraft.redenvelope.event.PlayerRedPackageGetEvent;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

@Data
public class RedPackage {
    /**
     * 红包类型
     */
    ConfigManager.RedPacketType type;
    /**
     * 红包个数
     */
    int amount;
    /**
     * 红包总金额
     */
    int total;
    /**
     * 物品红包的物品
     */
    String item;
    /**
     * 显示名称
     */
    String title;

    /**
     * 剩余红包
     */
    int balanceAmount = -1;
    /**
     * 剩余金额
     */
    List<Integer> issueData;

    List<UUID> receiver = new ArrayList<>();
    /**
     * 发送者
     */
    UUID owner;
    /**
     * 口令
     */
    String word;

    /**
     * 玩家能不能领取,目前只有物品状态会出现不能领取的情况(玩家背包已满)
     *
     * @param p
     * @return 是否可以领取
     */
    public boolean canReceive(Player p) {
        if (this.getReceiver().contains(p.getUniqueId())) {
            p.sendMessage(LangUtil.get("lang17"));
            return false;
        }
        if (this.getBalanceAmount() <= 0 && this.getBalanceAmount() != -1) return false;
        return true;
    }

    /**
     * 直接给玩家做领取操作
     *
     * @return 领取到数量
     */
    public int receive(Player p) {
        balanceInit();
        if (!canReceive(p)) return -1;
        int amount = this.getAmount();
        int balanceAmount = this.getBalanceAmount();
        List<Integer> issueData = this.getIssueData();
        int get = issueData.get(amount - balanceAmount);

        PlayerRedPackageGetEvent event = new PlayerRedPackageGetEvent();
        event.setRedPackage(this);
        event.setIndex(this.getTotal() - this.getBalanceAmount());
        event.setAmount(get);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancel()) {
            return -1;
        }
        try {
            Payer payer = DataManager.getPayer(this.getType());
            payer.give(p, get);
            return get;
        } finally {
            this.setBalanceAmount(this.getBalanceAmount() - 1);
            this.getReceiver().add(p.getUniqueId());
            p.sendMessage(LangUtil.get("lang15").replace("<red.title>", this.getTitle())
                    .replace("<red.get>", String.valueOf(get)));
            return -1;
        }
    }

    private void balanceInit() {
        if (this.getBalanceAmount() != -1) return;
        this.setBalanceAmount(this.getAmount());
        this.setIssueData(allocateRedPacket(this.getTotal(), this.getAmount()));
    }

    private List<Integer> allocateRedPacket(int total, int num) {
        List<Integer> result = new ArrayList<>();
        int avg = total / num;
        for (int i = 0; i < num; i++) {
            result.add(avg);
        }
        return result;
    }


}
