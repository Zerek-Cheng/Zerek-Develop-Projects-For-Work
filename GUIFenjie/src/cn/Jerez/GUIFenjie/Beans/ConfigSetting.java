// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie.Beans;

import java.util.List;

public class ConfigSetting
{
    private String id;
    private String Msg;
    private List<String> Cmds;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getMsg() {
        return this.Msg;
    }
    
    public void setMsg(final String msg) {
        this.Msg = msg;
    }
    
    public List<String> getCmds() {
        return this.Cmds;
    }
    
    public void setCmds(final List<String> cmds) {
        this.Cmds = cmds;
    }
    
    public ConfigSetting() {
    }
    
    public ConfigSetting(final String id, final String msg, final List<String> cmds) {
        this.id = id;
        this.Msg = msg;
        this.Cmds = cmds;
    }
}
