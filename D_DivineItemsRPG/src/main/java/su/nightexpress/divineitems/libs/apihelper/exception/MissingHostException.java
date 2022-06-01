/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.apihelper.exception;

public class MissingHostException
extends RuntimeException {
    private static final long serialVersionUID = 542397736184222384L;

    public MissingHostException() {
    }

    public MissingHostException(String string) {
        super(string);
    }

    public MissingHostException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MissingHostException(Throwable throwable) {
        super(throwable);
    }

    public MissingHostException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }
}

