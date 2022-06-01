package com._0myun.minecraft.quality.rand;

import lombok.Data;

/**
 * 奖品类
 *
 * @author:rex
 * @date:2014年10月20日
 * @version:1.0
 */
@Data
public class QuailityType {

    private String gitfId;
    private double probability;

    public QuailityType(String gitfId, double probability) {
        this.gitfId = gitfId;
        this.probability = probability;
    }

    public String getGitfId() {
        return gitfId;
    }

    public void setGitfId(String gitfId) {
        this.gitfId = gitfId;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "QuailityType [, gitfId=" + gitfId + "probability = "
                + probability + "]";
    }

}
