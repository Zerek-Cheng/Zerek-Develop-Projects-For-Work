/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.equation;

import equationexp.defoli_ation.main.equation.Equation;
import equationexp.defoli_ation.main.equation.EquationUtil;
import equationexp.defoli_ation.main.equation.LevelScope;
import java.util.ArrayList;
import java.util.List;

public class EquationManager {
    private final List<Equation> equationList = new ArrayList<Equation>();

    public boolean addEquation(Equation equation) {
        if (!EquationUtil.isEquation(equation)) {
            return false;
        }
        Equation[] arrequation = this.searchEquation(equation);
        int n = arrequation.length;
        int n2 = 0;
        while (n2 < n) {
            Equation e = arrequation[n2];
            if (e != null) {
                this.resetEquationScope(equation, e);
            }
            ++n2;
        }
        this.equationList.add(equation);
        return true;
    }

    private Equation[] searchEquation(Equation equation) {
        ArrayList<Equation> list = new ArrayList<Equation>();
        for (Equation e : this.equationList) {
            LevelScope mainScope = equation.levelScope;
            LevelScope minorScope = e.levelScope;
            if (LevelScope.isIntersect(mainScope, minorScope)) {
                list.add(e);
                continue;
            }
            if (!LevelScope.isInscribe(mainScope, minorScope) && !LevelScope.isInscribe(minorScope, mainScope)) continue;
            list.add(e);
        }
        return list.toArray(new Equation[0]);
    }

    public void removeEquation(int level) {
        Equation e = this.getEquationIfInScope(level);
        if (e == null) {
            return;
        }
        this.equationList.remove(e);
    }

    public void removeEquation(Equation equation) {
        this.equationList.remove(equation);
    }

    public Equation getEquation(int level) {
        return this.getEquationIfInScope(level);
    }

    public int getResult(int level) {
        Equation e = this.getEquationIfInScope(level);
        if (e == null) {
            e = this.equationList.get(this.equationList.size() - 1);
            LevelScope scope = e.levelScope;
            e = new Equation(e.equation, new LevelScope(scope.getLead(), scope.getEnd() + 20));
            this.addEquation(e);
            return this.getResult(level);
        }
        return EquationUtil.compute(e, level);
    }

    private Equation getEquationIfInScope(int level) {
        for (Equation e : this.equationList) {
            if (!LevelScope.inScope(e.levelScope, level)) continue;
            return e;
        }
        return null;
    }

    private void resetEquationScope(Equation main, Equation minor) {
        LevelScope mainScope = main.levelScope;
        LevelScope minorScope = minor.levelScope;
        int mainLead = mainScope.getLead();
        int mainEnd = mainScope.getEnd();
        int minorLead = minorScope.getLead();
        int minorEnd = minorScope.getEnd();
        if (LevelScope.isInscribe(mainScope, minorScope)) {
            this.removeEquation(minor);
            return;
        }
        if (LevelScope.isInscribe(minorScope, mainScope)) {
            boolean repeat = mainScope.equals(minorScope);
            if (repeat) {
                this.removeEquation(minor);
                return;
            }
            if (mainLead == minorLead) {
                minorScope.setLead(mainEnd);
                return;
            }
            if (mainEnd == minorEnd) {
                minorScope.setEnd(mainLead);
                return;
            }
            minorScope.setEnd(mainLead);
            this.addEquation(new Equation(minor.equation, new LevelScope(mainEnd, minorEnd)));
            return;
        }
        if (LevelScope.inScope(mainScope, minorEnd)) {
            minorScope.setEnd(mainLead);
        }
        if (LevelScope.inScope(mainScope, minorLead)) {
            minorScope.setLead(mainEnd);
        }
    }
}

