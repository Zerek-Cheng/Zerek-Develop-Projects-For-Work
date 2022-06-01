/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package cc.kunss.vexst.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AdvancedLicense {
    private String licenseKey;
    private Plugin plugin;
    private String validationServer;
    private LogType logType = LogType.NORMAL;
    private String securityKey = "YecoF0I6M05thxLeokoHuW8iUhTdIUInjkfF";
    private boolean debug = false;

    public AdvancedLicense(String licenseKey, String validationServer, Plugin plugin) {
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }

    public AdvancedLicense setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
        return this;
    }

    public AdvancedLicense setConsoleLog(LogType logType) {
        this.logType = logType;
        return this;
    }

    public AdvancedLicense debug() {
        this.debug = true;
        return this;
    }

    public boolean register() {
        this.log(0, "[]==========[\u63d2\u4ef6\u9a8c\u8bc1]==========[]");
        this.log(0, "\u6b63\u5728\u94fe\u63a5\u9a8c\u8bc1\u670d\u52a1\u5668...");
        ValidationType vt = this.isValid();
        if (vt == ValidationType.VALID) {
            Bukkit.getConsoleSender().sendMessage("\u63d2\u4ef6\u6388\u6743\u7801\u9a8c\u8bc1\u6210\u529f\uff01");
            Bukkit.getConsoleSender().sendMessage("[]==========[\u63d2\u4ef6\u9a8c\u8bc1]==========[]");
            return true;
        }
        Bukkit.getConsoleSender().sendMessage("\u63d2\u4ef6\u6388\u6743\u7801\u9a8c\u8bc1\u5931\u8d25\uff01");
        Bukkit.getConsoleSender().sendMessage("\u9a8c\u8bc1\u5931\u8d25\u539f\u56e0\uff1a " + vt.toString());
        Bukkit.getConsoleSender().sendMessage("\u5378\u8f7d\u63d2\u4ef6....");
        Bukkit.getConsoleSender().sendMessage("[]==========[\u63d2\u4ef6\u9a8c\u8bc1]==========[]");
        Bukkit.getScheduler().cancelTasks(this.plugin);
        Bukkit.getPluginManager().disablePlugin(this.plugin);
        return false;
    }

    public boolean isValidSimple() {
        return this.isValid() == ValidationType.VALID;
    }

    public ValidationType isValid() {
        String rand = this.toBinary(UUID.randomUUID().toString());
        String sKey = this.toBinary(this.securityKey);
        String key = this.toBinary(this.licenseKey);
        String response = AdvancedLicense.sendGet(this.validationServer, "v1=" + AdvancedLicense.xor(rand, sKey) + "&v2=" + AdvancedLicense.xor(rand, key) + "&pl=" + this.plugin.getName());
        try {
            return ValidationType.valueOf(response);
        }
        catch (IllegalArgumentException exc) {
            String respRand = AdvancedLicense.xor(AdvancedLicense.xor(response, key), sKey);
            if (rand.substring(0, respRand.length()).equals(respRand)) {
                return ValidationType.VALID;
            }
            return ValidationType.WRONG_RESPONSE;
        }
    }

    private static String xor(String s1, String s2) {
        String s0 = "";
        for (int i = 0; i < (s1.length() < s2.length() ? s1.length() : s2.length()); ++i) {
            s0 = s0 + (Byte.valueOf(new StringBuilder().append("").append(s1.charAt(i)).toString()) ^ Byte.valueOf(new StringBuilder().append("").append(s2.charAt(i)).toString()));
        }
        return s0;
    }

    private String toBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        byte[] arrby = bytes;
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            int b;
            int val = b = arrby[i];
            for (int i2 = 0; i2 < 8; ++i2) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    private void log(int type, String message) {
        if (this.logType == LogType.NONE || this.logType == LogType.LOW && type == 0) {
            return;
        }
        System.out.println(message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String sendGet(String url, String param) {
        String result;
        result = "";
        BufferedReader in = null;
        try {
            String line;
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String sendPost(String url, String param) {
        String result;
        PrintWriter out = null;
        BufferedReader in = null;
        result = "";
        try {
            String line;
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static enum ValidationType {
        WRONG_RESPONSE,
        PAGE_ERROR,
        URL_ERROR,
        KEY_OUTDATED,
        KEY_NOT_FOUND,
        NOT_VALID_IP,
        INVALID_PLUGIN,
        VALID;
        

        private ValidationType() {
        }
    }

    public static enum LogType {
        NORMAL,
        LOW,
        NONE;
        

        private LogType() {
        }
    }

}

