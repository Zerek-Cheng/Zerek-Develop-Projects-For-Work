/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.exp;

import equationexp.defoli_ation.main.equation.EquationManager;
import java.util.HashMap;

public class Exp {
    private HashMap<Integer, Integer> levelExp = new HashMap();
    private EquationManager manager;
    private int loadMax = 0;

    public Exp(EquationManager manager) {
        this(40, manager);
    }

    public Exp(int max, EquationManager manager) {
        this.manager = manager;
        this.loadExp(max);
    }

    private void loadExp(int max) {
        int i = this.loadMax;
        while (i < max + 1) {
            this.levelExp.put(i, this.manager.getResult(i));
            ++i;
        }
        this.loadMax = max;
    }

    public int getLevel(int exp) {
        this.checkExp(exp);
        int i = 0;
        while (i < this.loadMax) {
            if (exp >= this.levelExp.get(i) && exp < this.levelExp.get(i + 1)) {
                return i;
            }
            ++i;
        }
        return this.getLevel(exp);
    }

    public int getExp(int level) {
        this.checkLevel(level);
        return this.levelExp.get(level);
    }

    private void checkLevel(int level) {
        if (level > this.loadMax) {
            this.loadExp(level + 20);
        }
    }

    private void checkExp(int exp) {
        int maxExp = this.levelExp.get(this.levelExp.size() - 1);
        if (exp >= maxExp) {
            this.loadExp(this.loadMax + 20);
        }
    }

    public int getLoadMax() {
        return this.loadMax;
    }
}

