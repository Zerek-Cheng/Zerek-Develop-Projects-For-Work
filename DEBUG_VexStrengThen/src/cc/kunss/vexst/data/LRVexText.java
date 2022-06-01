/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  lk.vexview.gui.components.VexText
 */
package cc.kunss.vexst.data;

import lk.vexview.gui.components.VexText;

import java.util.List;

public class LRVexText
extends VexText {
    private int id;
    private int size;

    public LRVexText(int id, int a, int a2, List<String> a3, int size) {
        super(a, a2, a3);
        this.setId(id);
        this.setSize(size);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

