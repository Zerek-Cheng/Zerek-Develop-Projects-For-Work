/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import su.nightexpress.divineitems.DivineItems;

public class Files {
    public static void copy(InputStream inputStream, File file) {
        try {
            int n;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] arrby = new byte[1024];
            while ((n = inputStream.read(arrby)) > 0) {
                fileOutputStream.write(arrby, 0, n);
            }
            fileOutputStream.close();
            inputStream.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void mkdir(File file) {
        try {
            file.mkdir();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void create(File file) {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static List<String> getFilesFolder(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        File file = new File(DivineItems.instance.getDataFolder() + "/modules/" + string + "/");
        File[] arrfile = file.listFiles();
        if (arrfile == null) {
            return arrayList;
        }
        int n = 0;
        while (n < arrfile.length) {
            if (arrfile[n].isFile()) {
                arrayList.add(arrfile[n].getName());
            }
            ++n;
        }
        return arrayList;
    }
}

