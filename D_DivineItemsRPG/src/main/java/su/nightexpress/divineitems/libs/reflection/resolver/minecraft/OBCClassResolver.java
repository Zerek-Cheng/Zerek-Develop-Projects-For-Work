/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.minecraft;

import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;

public class OBCClassResolver
extends ClassResolver {
    @Override
    public /* varargs */ Class<?> resolve(String ... arrstring) {
        int n = 0;
        while (n < arrstring.length) {
            if (!arrstring[n].startsWith("org.bukkit.craftbukkit")) {
                arrstring[n] = "org.bukkit.craftbukkit." + Minecraft.getVersion() + arrstring[n];
            }
            ++n;
        }
        return super.resolve(arrstring);
    }
}

