/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.data.LRVexText;
import cc.kunss.vexst.utils.GuiFile;
import java.util.List;

public class LRVexGuiComponent {
    public static LRVexText getLRVexText(String Path2, List<String> text) {
        LRVexText lrVexText = new LRVexText(GuiFile.getData().getInt(Path2 + ".id"), GuiFile.getData().getInt(Path2 + ".x"), GuiFile.getData().getInt(Path2 + ".y"), text, GuiFile.getData().getInt(Path2 + ".size"));
        return lrVexText;
    }
}

