/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 */
package equationexp.defoli_ation.main.file.equationfile;

import equationexp.defoli_ation.main.equation.Equation;
import equationexp.defoli_ation.main.equation.LevelScope;
import equationexp.defoli_ation.main.file.equationfile.EquationFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;

public class EquationYaml
implements EquationFile {
    private FileConfiguration config;

    public EquationYaml(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public Equation[] getEquation() {
        ArrayList<Equation> equationList = new ArrayList<Equation>();
        List<String> levelList = this.getEquationLevel();
        int keyListSize = levelList.size();
        int i = 0;
        while (i < keyListSize) {
            String level = levelList.get(i);
            String s = this.config.getString("equation." + level);
            int intLevel = Integer.parseInt(level);
            Equation equation = i + 1 >= keyListSize ? new Equation(s, new LevelScope(intLevel, intLevel + 40)) : new Equation(s, new LevelScope(intLevel, Integer.parseInt(levelList.get(i + 1))));
            equationList.add(equation);
            ++i;
        }
        return equationList.toArray(new Equation[0]);
    }

    public List<String> getEquationLevel() {
        ArrayList<String> keyList = new ArrayList<String>();
        for (String key : this.config.getKeys(true)) {
            if (!key.startsWith("equation.")) continue;
            keyList.add(key.substring(9));
        }
        return keyList;
    }
}

