/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.modules.tiers.resources;

import java.util.List;

public class Res {
    private String type;
    private List<String> prefix;
    private List<String> suffix;

    public Res(String string, List<String> list, List<String> list2) {
        this.setType(string);
        this.setPrefixes(list);
        this.setSuffixes(list2);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String string) {
        this.type = string;
    }

    public List<String> getPrefixes() {
        return this.prefix;
    }

    public void setPrefixes(List<String> list) {
        this.prefix = list;
    }

    public List<String> getSuffixes() {
        return this.suffix;
    }

    public void setSuffixes(List<String> list) {
        this.suffix = list;
    }
}

