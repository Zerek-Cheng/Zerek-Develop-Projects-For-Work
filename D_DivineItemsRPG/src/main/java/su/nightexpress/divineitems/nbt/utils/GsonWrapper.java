/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package su.nightexpress.divineitems.nbt.utils;

import com.google.gson.Gson;

public class GsonWrapper {
    private static final Gson gson = new Gson();

    public static String getString(Object object) {
        return gson.toJson(object);
    }

    public static <T> T deserializeJson(String string, Class<T> class_) {
        block3 : {
            try {
                if (string != null) break block3;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
            return null;
        }
        Object object = gson.fromJson(string, class_);
        return class_.cast(object);
    }
}

