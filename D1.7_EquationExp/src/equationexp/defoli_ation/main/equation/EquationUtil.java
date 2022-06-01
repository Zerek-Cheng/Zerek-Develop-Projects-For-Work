/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.equation;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class EquationUtil {
    //private static final ScriptEngine SE = new ScriptEngineManager().getEngineByName("nashorn");
    private static final ScriptEngine SE = new NashornScriptEngineFactory().getScriptEngine();

    public static boolean isEquation(String equation) {
        if (EquationUtil.compute(equation, 1) == -1) {
            return false;
        }
        return true;
    }

    public static boolean isEquation(Equation equation) {
        return EquationUtil.isEquation(equation.equation);
    }

    public static int compute(String equation, int level) {
        try {
            Object o = SE.eval(EquationUtil.replace(equation, level));
            if (o instanceof Double) {
                Double d = (double) ((Double) o);
                return d.intValue();
            }
            return (Integer) o;
        } catch (ScriptException o) {
            return -1;
        }
    }

    public static int compute(Equation equation, int level) {
        return EquationUtil.compute(equation.equation, level);
    }

    private static String replace(String equation, int level) {
        StringBuilder sb = new StringBuilder();
        String[] arrstring = equation.split("[$]");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String str = arrstring[n2];
            sb.append(str.equals("level") ? Integer.valueOf(level) : str);
            ++n2;
        }
        return sb.toString();
    }
}

