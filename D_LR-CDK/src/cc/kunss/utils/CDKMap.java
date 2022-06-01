/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.utils;

import java.util.ArrayList;
import java.util.List;

public class CDKMap {
    private String Rename;
    private String Cmd;
    private String Message;
    private List<String> cdk = new ArrayList<String>();

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getCmd() {
        return this.Cmd;
    }

    public void setCmd(String cmd) {
        this.Cmd = cmd;
    }

    public String getRename() {
        return this.Rename;
    }

    public void setRename(String rename) {
        this.Rename = rename;
    }

    public List<String> getCdk() {
        return this.cdk;
    }

    public void setCdk(List<String> cdk) {
        this.cdk = cdk;
    }
}

