/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.minecraft;

import su.nightexpress.divineitems.libs.reflection.minecraft.Minecraft;
import su.nightexpress.divineitems.libs.reflection.resolver.ClassResolver;

public class NMSClassResolver
extends ClassResolver {
    @Override
    public /* varargs */ Class<?> resolve(String ... arrstring) {
        int n = 0;
        while (n < arrstring.length) {
            if (!arrstring[n].startsWith("net.minecraft.server")) {
                arrstring[n] = "net.minecraft.server." + Minecraft.getVersion() + arrstring[n];
            }
            ++n;
        }
        return super.resolve(arrstring);
    }
}

