/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AilerCuiLianManager {
    private String ShowName;
    private String type;
    private boolean MessageEnable;
    private String Message;
    private boolean loselevel;
    private String losetype;
    private double loserandom;
    private double money;
    private double points;
    private double defaulerandom;
    private String permission;
    private String permissionprefix;
    private boolean cmdenable;
    private Map<Integer, LevelAddition> levelAdditionMap = new HashMap<Integer, LevelAddition>();
    private List<String> cmdlist;
    private String lore;
    private int level;
    private GiveExp giveExp;
    private Map<StoneManager, Integer> stoneManagerIntegerMap = new HashMap<StoneManager, Integer>();
    private Map<List<String>, List<String>> ReplaceLorelist = new HashMap<List<String>, List<String>>();

    public AilerCuiLianManager(String showName, String type, boolean messageEnable, double points, boolean loselevel, String losetype, double loserandom, String lore, double money, Map<List<String>, List<String>> replaceLorelist, Map<StoneManager, Integer> stoneManagerIntegerMap, double defaulerandom, String permission, String permissionprefix, boolean cmdenable, List<String> cmdlist, Map<Integer, LevelAddition> levelAddition, int level, GiveExp giveExp) {
        this.setLoselevel(loselevel);
        this.setGiveExp(giveExp);
        this.setLevel(level);
        this.setLevelAdditionMap(levelAddition);
        this.setLosetype(losetype);
        this.setLoserandom(loserandom);
        this.setCmdenable(cmdenable);
        this.setCmdlist(cmdlist);
        this.setPermissionprefix(permissionprefix);
        this.setLore(lore);
        this.setPoints(points);
        this.setPermission(permission);
        this.setMessageEnable(messageEnable);
        this.setDefaulerandom(defaulerandom);
        this.setShowName(showName);
        this.setType(type);
        this.setStoneManagerIntegerMap(stoneManagerIntegerMap);
        this.setMoney(money);
        this.setReplaceLorelist(replaceLorelist);
    }

    public String getShowName() {
        return this.ShowName;
    }

    public void setShowName(String showName) {
        this.ShowName = showName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMessageEnable() {
        return this.MessageEnable;
    }

    public void setMessageEnable(boolean messageEnable) {
        this.MessageEnable = messageEnable;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getLore() {
        return this.lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public Map<List<String>, List<String>> getReplaceLorelist() {
        return this.ReplaceLorelist;
    }

    public void setReplaceLorelist(Map<List<String>, List<String>> replaceLorelist) {
        this.ReplaceLorelist = replaceLorelist;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Map<StoneManager, Integer> getStoneManagerIntegerMap() {
        return this.stoneManagerIntegerMap;
    }

    public void setStoneManagerIntegerMap(Map<StoneManager, Integer> stoneManagerIntegerMap) {
        this.stoneManagerIntegerMap = stoneManagerIntegerMap;
    }

    public double getDefaulerandom() {
        return this.defaulerandom;
    }

    public void setDefaulerandom(double defaulerandom) {
        this.defaulerandom = defaulerandom;
    }

    public boolean isLoselevel() {
        return this.loselevel;
    }

    public void setLoselevel(boolean loselevel) {
        this.loselevel = loselevel;
    }

    public String getLosetype() {
        return this.losetype;
    }

    public void setLosetype(String losetype) {
        this.losetype = losetype;
    }

    public double getLoserandom() {
        return this.loserandom;
    }

    public void setLoserandom(double loserandom) {
        this.loserandom = loserandom;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionprefix() {
        return this.permissionprefix;
    }

    public void setPermissionprefix(String permissionprefix) {
        this.permissionprefix = permissionprefix;
    }

    public double getPoints() {
        return this.points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public List<String> getCmdlist() {
        return this.cmdlist;
    }

    public void setCmdlist(List<String> cmdlist) {
        this.cmdlist = cmdlist;
    }

    public boolean isCmdenable() {
        return this.cmdenable;
    }

    public void setCmdenable(boolean cmdenable) {
        this.cmdenable = cmdenable;
    }

    public Map<Integer, LevelAddition> getLevelAdditionMap() {
        return this.levelAdditionMap;
    }

    public void setLevelAdditionMap(Map<Integer, LevelAddition> levelAdditionMap) {
        this.levelAdditionMap = levelAdditionMap;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GiveExp getGiveExp() {
        return this.giveExp;
    }

    public void setGiveExp(GiveExp giveExp) {
        this.giveExp = giveExp;
    }
}

