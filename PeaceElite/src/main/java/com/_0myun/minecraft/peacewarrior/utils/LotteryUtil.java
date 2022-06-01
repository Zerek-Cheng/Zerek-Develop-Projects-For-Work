package com._0myun.minecraft.peacewarrior.utils;

import java.util.*;

public class LotteryUtil {

    /**
     * 抽奖，获取中奖奖品
     *
     * @param rewards 奖品及中奖概率列表
     * @return 中奖商品
     */
    public static Award lottery(List<Award> rewards) {
        //奖品总数
        int size = rewards.size();

        //计算总概率
        double sumProbability = 0d;
        for (Award award : rewards) {
            sumProbability += award.getProbability();
        }

        //计算每个奖品的概率区间
        //例如奖品A概率区间0-0.1  奖品B概率区间 0.1-0.5 奖品C概率区间0.5-1
        //每个奖品的中奖率越大，所占的概率区间就越大
        List<Double> sortAwardProbabilityList = new ArrayList<Double>(size);
        Double tempSumProbability = 0d;
        for (Award award : rewards) {
            tempSumProbability += award.getProbability();
            sortAwardProbabilityList.add(tempSumProbability / sumProbability);
        }

        //产生0-1之间的随机数
        //随机数在哪个概率区间内，则是哪个奖品
        double randomDouble = Math.random();
        //加入到概率区间中，排序后，返回的下标则是awardList中中奖的下标
        sortAwardProbabilityList.add(randomDouble);
        Collections.sort(sortAwardProbabilityList);
        int lotteryIndex = sortAwardProbabilityList.indexOf(randomDouble);
        return rewards.get(lotteryIndex);
    }

    public static void main(String[] args) {
        List<Award> awardList = new ArrayList<Award>();
        awardList.add(new Award("0", 0.35d));
        awardList.add(new Award("1", 0.25d));
        awardList.add(new Award("2", 0.002d));
        awardList.add(new Award("3", 0.003d));
        awardList.add(new Award("4", 0.0005d));
        awardList.add(new Award("5", 0.1d));

        Map<String, Integer> result = new HashMap<String, Integer>();

        System.out.println(lottery(awardList).getAwardId());
    }

    /**
     * 奖品实体类
     */
    public static class Award {
        public Award() {
        }

        public Award(String id, double probability) {
            this.awardId = id;
            this.probability = probability;
        }

        /**
         * 奖品ID
         **/
        private String awardId;
        /**
         * 中奖概率
         **/
        private double probability;

        public String getAwardId() {
            return awardId;
        }

        public void setAwardId(String awardId) {
            this.awardId = awardId;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double probability) {
            this.probability = probability;
        }
    }
}