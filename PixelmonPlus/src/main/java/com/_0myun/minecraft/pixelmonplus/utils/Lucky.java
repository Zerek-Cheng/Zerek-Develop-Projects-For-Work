package com._0myun.minecraft.pixelmonplus.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

public class Lucky {
    // 放大倍数
    private static final int mulriple = 1000000;

    public static int rand(List<Prize> prizes) {
        int lastScope = 0;
        // 洗牌，打乱奖品次序
        Collections.shuffle(prizes);
        Map<Integer, int[]> prizeScopes = new HashMap<Integer, int[]>();
        for (Prize prize : prizes) {
            int prizeId = prize.getPrizeId();
            // 划分区间
            int currentScope = lastScope + prize.getProbability().multiply(new BigDecimal(mulriple)).intValue();
            prizeScopes.put(prizeId, new int[]{lastScope + 1, currentScope});

            lastScope = currentScope;
        }

        // 获取1-1000000之间的一个随机数
        int luckyNumber = new Random().nextInt(mulriple);
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

        return luckyPrizeId;
    }

    @Data
    @AllArgsConstructor
    public static class Prize {

        /**
         * 奖品唯一标示
         */
        private Integer prizeId;

        private String value;

        /**
         * 中奖概率
         */
        private BigDecimal probability;
    }
}
