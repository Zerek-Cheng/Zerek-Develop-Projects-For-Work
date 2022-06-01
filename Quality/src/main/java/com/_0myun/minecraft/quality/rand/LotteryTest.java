package com._0myun.minecraft.quality.rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不同概率抽奖
 *
 * @author:rex
 * @date:2014年10月20日
 * @version:1.0
 */
public class LotteryTest {
    public static void main(String[] args) {

        List<QuailityType> quailityTypes = new ArrayList<QuailityType>();
        // 序号==物品Id==物品名称==概率
        quailityTypes.add(new QuailityType("P1", 0.2d));
        quailityTypes.add(new QuailityType("P2", 0.2d));
        quailityTypes.add(new QuailityType("P3", 0.4d));
        quailityTypes.add(new QuailityType("P4", 0.3d));
        quailityTypes.add(new QuailityType("P5", 0d));
        quailityTypes.add(new QuailityType("P6", -0.1d));
        quailityTypes.add(new QuailityType("P7", 0.008d));

        List<Double> orignalRates = new ArrayList<Double>(quailityTypes.size());
        for (QuailityType quailityType : quailityTypes) {
            double probability = quailityType.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }

        // statistics
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        double num = 1000000;
        for (int i = 0; i < num; i++) {
            int orignalIndex = LotteryUtil.lottery(orignalRates);

            Integer value = count.get(orignalIndex);
            count.put(orignalIndex, value == null ? 1 : value + 1);
        }

        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            System.out.println(quailityTypes.get(entry.getKey()) + ", count=" + entry.getValue() + ", probability="
                    + entry.getValue() / num);
        }
    }

}