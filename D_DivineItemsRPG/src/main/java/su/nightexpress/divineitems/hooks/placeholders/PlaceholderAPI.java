/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.hooks.placeholders;

import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.hooks.placeholders.PlaceholderAPIHook;

public class PlaceholderAPI {
    public static void registerPlaceholderAPI() {
        new PlaceholderAPIHook(DivineItems.instance, "dirpg").hook();
    }
}

