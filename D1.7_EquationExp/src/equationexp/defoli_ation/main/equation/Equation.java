/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.equation;

import equationexp.defoli_ation.main.equation.LevelScope;

public class Equation {
    public final String equation;
    public final LevelScope levelScope;

    public Equation(String equation, LevelScope levelScope) {
        this.equation = equation;
        this.levelScope = levelScope;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.equation == null ? 0 : this.equation.hashCode());
        result = 31 * result + (this.levelScope == null ? 0 : this.levelScope.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Equation)) {
            return false;
        }
        Equation other = (Equation)obj;
        if (this.equation == null ? other.equation != null : !this.equation.equals(other.equation)) {
            return false;
        }
        if (this.levelScope == null ? other.levelScope != null : !this.levelScope.equals(other.levelScope)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "\u516c\u5f0f:" + this.equation + this.levelScope.toString();
    }
}

