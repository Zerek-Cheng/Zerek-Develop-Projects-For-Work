/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.equation;

import java.io.PrintStream;

public class LevelScope {
    private int lead;
    private int end;

    public LevelScope(int lead, int end) {
        this.lead = lead;
        this.end = end;
        this.check();
    }

    public static boolean inScope(LevelScope scope, int value) {
        if (value >= scope.getLead() && value < scope.getEnd()) {
            return true;
        }
        return false;
    }

    public static boolean isIntersect(LevelScope scope1, LevelScope scope2) {
        if (LevelScope.inScope(scope1, scope2.getLead()) || LevelScope.inScope(scope1, scope2.getEnd())) {
            return true;
        }
        return false;
    }

    public static boolean isInscribe(LevelScope mainScope, LevelScope minorScope) {
        if (LevelScope.inScope(mainScope, minorScope.lead) && LevelScope.inScope(mainScope, minorScope.end)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.getEnd();
        result = 31 * result + this.getLead();
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LevelScope)) {
            return false;
        }
        LevelScope other = (LevelScope)obj;
        if (this.getEnd() != other.getEnd()) {
            return false;
        }
        if (this.getLead() != other.getLead()) {
            return false;
        }
        return true;
    }

    public int getLead() {
        return this.lead;
    }

    public void setLead(int lead) {
        this.lead = lead;
        this.check();
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
        this.check();
    }

    private void check() {
        if (this.lead <= this.end) {
            return;
        }
        System.out.println("lead:" + this.lead + "end" + this.end);
        int max = Math.max(this.lead, this.end);
        int min = Math.min(this.lead, this.end);
        this.setLead(min);
        this.setEnd(max);
    }

    public String toString() {
        return "\u8303\u56f4:" + this.lead + "-" + this.end;
    }
}

