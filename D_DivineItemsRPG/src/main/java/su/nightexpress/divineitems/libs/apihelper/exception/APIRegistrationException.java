/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.libs.apihelper.exception;

public class APIRegistrationException
extends RuntimeException {
    private static final long serialVersionUID = 848501679758682477L;

    public APIRegistrationException() {
    }

    public APIRegistrationException(String string) {
        super(string);
    }

    public APIRegistrationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public APIRegistrationException(Throwable throwable) {
        super(throwable);
    }

    public APIRegistrationException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }
}

