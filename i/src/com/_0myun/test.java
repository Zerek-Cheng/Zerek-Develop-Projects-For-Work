package com._0myun;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class test {
    public static void main(String[] args) {
        System.out.println("ok");
/*        decryptByBase64("yv66vgAAADQASAEAImNhdHNlcnZlci9zZXJ2ZXIvdmVyeS9MYXVuY2hTZXJ2ZXIHAAEBABBqYXZhL2xhbmcvT2JqZWN0BwADAQARTGF1bmNoU2VydmVyLmphdmEBAAY8aW5pdD4BAAMoKVYMAAYABwoABAAIAQAEdGhpcwEAJExjYXRzZXJ2ZXIvc2VydmVyL3ZlcnkvTGF1bmNoU2VydmVyOwEADGxhdW5jaFNlcnZlcgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAE2phdmEvbGFuZy9FeGNlcHRpb24HAA4BAEZ0NXhvN2RtZzBMZEQ5dmpYZXRzSEpmUHdpTmZZSE9vaTV6d2Y3UEszMTNrV2RueTN3dDhqV2M5WUdIZDFMaFlqSUY2SmQyCAAQAQAQamF2YS9sYW5nL1N0cmluZwcAEgEABmVxdWFscwEAFShMamF2YS9sYW5nL09iamVjdDspWgwAFAAVCgATABYBABpqYXZhL2xhbmcvUnVudGltZUV4Y2VwdGlvbgcAGAoAGQAIAQAybmV0Lm1pbmVjcmFmdGZvcmdlLmZtbC5yZWxhdW5jaGVyLkZNTExhdW5jaEhhbmRsZXIIABsBAA9qYXZhL2xhbmcvQ2xhc3MHAB0BAAdmb3JOYW1lAQAlKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL0NsYXNzOwwAHwAgCgAeACEBAAhJTlNUQU5DRQgAIwEAEGdldERlY2xhcmVkRmllbGQBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsMACUAJgoAHgAnAQAXamF2YS9sYW5nL3JlZmxlY3QvRmllbGQHACkBAA1zZXRBY2Nlc3NpYmxlAQAEKFopVgwAKwAsCgAqAC0BAAlzZXR1cEhvbWUIAC8BABFnZXREZWNsYXJlZE1ldGhvZAEAQChMamF2YS9sYW5nL1N0cmluZztbTGphdmEvbGFuZy9DbGFzczspTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsMADEAMgoAHgAzAQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kBwA1CgA2AC0BAANnZXQBACYoTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwwAOAA5CgAqADoBAAZpbnZva2UBADkoTGphdmEvbGFuZy9PYmplY3Q7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsMADwAPQoANgA+AQAEbnVsbAEAEkxqYXZhL2xhbmcvU3RyaW5nOwEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAA1TdGFja01hcFRhYmxlAQAKRXhjZXB0aW9ucwEAClNvdXJjZUZpbGUAIQACAAQAAAAAAAIAAQAGAAcAAQBCAAAALwABAAEAAAAFKrcACbEAAAACAEMAAAAGAAEAAAAGAEQAAAAMAAEAAAAFAAoACwAAAAkADAANAAIAQgAAAJEAAwAEAAAAQioSEbYAF5oAC7sAGVm3ABq/Ehy4ACJMKxIktgAoTSwEtgAuKxIwA70AHrYANE4tBLYANy0sAbYAOwO9AAS2AD9XsQAAAAMARQAAAAMAAREAQwAAACIACAAAAAgAEQAJABcACgAeAAsAIwAMAC4ADQAzAA4AQQAPAEQAAAAMAAEAAABCAEAAQQAAAEYAAAAEAAEADwABAEcAAAACAAU"
        ,"C://a.class");*/
        try {
            System.out.println(encodeBase64File("C://b.class"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decryptByBase64(String base64, String filePath) {
        if (base64 == null && filePath == null) {
            return "生成文件失败，请给出相应的数据。";
        }
        try {
            Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "指定路径下生成文件成功！";
    }
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
}
