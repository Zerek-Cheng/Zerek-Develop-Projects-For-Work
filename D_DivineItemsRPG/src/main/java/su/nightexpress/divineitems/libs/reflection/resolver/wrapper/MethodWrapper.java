/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.reflection.resolver.wrapper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import su.nightexpress.divineitems.libs.reflection.resolver.wrapper.WrapperAbstract;

public class MethodWrapper<R>
extends WrapperAbstract {
    private final Method method;

    public MethodWrapper(Method method) {
        this.method = method;
    }

    @Override
    public boolean exists() {
        if (this.method != null) {
            return true;
        }
        return false;
    }

    public String getName() {
        return this.method.getName();
    }

    public /* varargs */ R invoke(Object object, Object ... arrobject) {
        try {
            return (R)this.method.invoke(object, arrobject);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public /* varargs */ R invokeSilent(Object object, Object ... arrobject) {
        try {
            return (R)this.method.invoke(object, arrobject);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Method getMethod() {
        return this.method;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        MethodWrapper methodWrapper = (MethodWrapper)object;
        return this.method != null ? this.method.equals(methodWrapper.method) : methodWrapper.method == null;
    }

    public int hashCode() {
        return this.method != null ? this.method.hashCode() : 0;
    }

    public static String getMethodSignature(Method method, boolean bl) {
        return MethodSignature.of(method, bl).getSignature();
    }

    public static String getMethodSignature(Method method) {
        return MethodWrapper.getMethodSignature(method, false);
    }

    public static class MethodSignature {
        static final Pattern SIGNATURE_STRING_PATTERN = Pattern.compile("(.+) (.*)\\((.*)\\)");
        private final String returnType;
        private final Pattern returnTypePattern;
        private final String name;
        private final Pattern namePattern;
        private final String[] parameterTypes;
        private final String signature;

        public MethodSignature(String string, String string2, String[] arrstring) {
            this.returnType = string;
            this.returnTypePattern = Pattern.compile(string.replace("?", "\\w").replace("*", "\\w*").replace("[", "\\[").replace("]", "\\]"));
            this.name = string2;
            this.namePattern = Pattern.compile(string2.replace("?", "\\w").replace("*", "\\w*"));
            this.parameterTypes = arrstring;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string).append(" ").append(string2).append("(");
            boolean bl = true;
            String[] arrstring2 = arrstring;
            int n = arrstring2.length;
            int n2 = 0;
            while (n2 < n) {
                String string3 = arrstring2[n2];
                if (!bl) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(string3);
                bl = false;
                ++n2;
            }
            this.signature = stringBuilder.append(")").toString();
        }

        public static MethodSignature of(Method method, boolean bl) {
            Class<?> class_ = method.getReturnType();
            Class<?>[] arrclass = method.getParameterTypes();
            String string = class_.isPrimitive() ? class_.toString() : (bl ? class_.getName() : class_.getSimpleName());
            String string2 = method.getName();
            String[] arrstring = new String[arrclass.length];
            int n = 0;
            while (n < arrstring.length) {
                arrstring[n] = arrclass[n].isPrimitive() ? arrclass[n].toString() : (bl ? arrclass[n].getName() : arrclass[n].getSimpleName());
                ++n;
            }
            return new MethodSignature(string, string2, arrstring);
        }

        public static MethodSignature fromString(String string) {
            if (string == null) {
                return null;
            }
            Matcher matcher = SIGNATURE_STRING_PATTERN.matcher(string);
            if (matcher.find()) {
                if (matcher.groupCount() != 3) {
                    throw new IllegalArgumentException("invalid signature");
                }
                return new MethodSignature(matcher.group(1), matcher.group(2), matcher.group(3).split(","));
            }
            throw new IllegalArgumentException("invalid signature");
        }

        public String getReturnType() {
            return this.returnType;
        }

        public boolean isReturnTypeWildcard() {
            if (!"?".equals(this.returnType) && !"*".equals(this.returnType)) {
                return false;
            }
            return true;
        }

        public String getName() {
            return this.name;
        }

        public boolean isNameWildcard() {
            if (!"?".equals(this.name) && !"*".equals(this.name)) {
                return false;
            }
            return true;
        }

        public String[] getParameterTypes() {
            return this.parameterTypes;
        }

        public String getParameterType(int n) {
            return this.parameterTypes[n];
        }

        public boolean isParameterWildcard(int n) {
            if (!"?".equals(this.getParameterType(n)) && !"*".equals(this.getParameterType(n))) {
                return false;
            }
            return true;
        }

        public String getSignature() {
            return this.signature;
        }

        public boolean matches(MethodSignature methodSignature) {
            if (methodSignature == null) {
                return false;
            }
            if (!this.returnTypePattern.matcher(methodSignature.returnType).matches()) {
                return false;
            }
            if (!this.namePattern.matcher(methodSignature.name).matches()) {
                return false;
            }
            if (this.parameterTypes.length != methodSignature.parameterTypes.length) {
                return false;
            }
            int n = 0;
            while (n < this.parameterTypes.length) {
                if (!Pattern.compile(this.getParameterType(n).replace("?", "\\w").replace("*", "\\w*")).matcher(methodSignature.getParameterType(n)).matches()) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            MethodSignature methodSignature = (MethodSignature)object;
            if (!this.returnType.equals(methodSignature.returnType)) {
                return false;
            }
            if (!this.name.equals(methodSignature.name)) {
                return false;
            }
            if (!Arrays.equals(this.parameterTypes, methodSignature.parameterTypes)) {
                return false;
            }
            return this.signature.equals(methodSignature.signature);
        }

        public int hashCode() {
            int n = this.returnType.hashCode();
            n = 31 * n + this.name.hashCode();
            n = 31 * n + Arrays.hashCode(this.parameterTypes);
            n = 31 * n + this.signature.hashCode();
            return n;
        }

        public String toString() {
            return this.getSignature();
        }
    }

}

