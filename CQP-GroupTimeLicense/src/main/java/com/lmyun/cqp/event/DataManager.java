package com.lmyun.cqp.event;

import com.sobte.cqp.jcq.entity.Member;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataManager {
    GroupTimeLicense plugin;
    File dataFile = new File(GroupTimeLicense.CQ.getAppDirectory() + "/date.yml");
    YamlConfiguration data = new YamlConfiguration();
    List<Long> has5 = new ArrayList<>();
    List<Long> has24 = new ArrayList<>();

    public DataManager(GroupTimeLicense groupTimeLicense) {
        this.plugin = groupTimeLicense;
    }

    public void load() {
        this.saveDefault();
        try {
            this.data.load(this.dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.data.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        try {
            if (this.dataFile.exists()) {
                return;
            }
            URL url = this.getClass().getResource("/date.yml");
            FileOutputStream os = new FileOutputStream(this.dataFile);
            os.write(("15495648: 1545116184\n" +
                    "48495654: 1999999999\n" +
                    "admin:\n" +
                    "  - 903115511\n" +
                    "  - 1198481001").getBytes());
            this.plugin.CQ.logInfo(this.plugin.appInfo(), "写入默认配置");
            //is.close();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getValidateTime(long group) {
        return this.data.getLong(String.valueOf(group));
    }

    public boolean isValidate(long group) {
        return this.getValidateTime(group) >= System.currentTimeMillis() || this.getValidateTime(group) == 0;
    }

    public void setValidateTime(long group, long time) {
        this.data.set(String.valueOf(group), time);
    }

    public void addValidateTime(long group, long time) {
        long validateTime = this.getValidateTime(group);
        validateTime = validateTime < System.currentTimeMillis() ? System.currentTimeMillis() : validateTime;
        this.setValidateTime(group, validateTime + time);
    }

    public void removeGroup(long group) {
        this.data.set(String.valueOf(group), null);
    }

    public List<Long> getAdminList() {
        return this.data.getLongList("admin");
    }

    public boolean isAdmin(Long qq) {
        return this.getAdminList().contains(qq);
    }

    public Set<String> getGroups() {
        Set<String> groups = this.data.getKeys(false);
        groups.remove("admin");
        return groups;
    }

    public List<String> getAdmins() {
        return this.data.getStringList("admin");
    }

    public void check5Day() {
        Set<String> groups = this.getGroups();
        for (String group : groups) {
            if (this.getValidateTime(Long.valueOf(group)) == 0 || this.has5.contains(Long.valueOf(group))) {
                continue;
            }
            long have = this.getValidateTime(Long.valueOf(group)) - System.currentTimeMillis();
            if (have <= 5 * 24 * 60 * 60 * 1000) {
                List<Long> adminList = this.getAdminList();
                String atStr = "";
                List<Member> groupMemberList = this.plugin.CQ.getGroupMemberList(Long.valueOf(group));
                for (int i = 0; i < groupMemberList.size(); i++) {
                    if (groupMemberList.get(i).getAuthority() == 1) {
                        continue;
                    }
                    atStr += this.plugin.CC.at(groupMemberList.get(i).getQqId());
                }
                this.plugin.CQ.sendGroupMsg(Long.valueOf(group), atStr + "本群到期时间还剩不到五天");
                this.has5.add(Long.valueOf(group));
            }
        }
    }

    public void check24Hour() {
        Set<String> groups = this.getGroups();
        for (String group : groups) {
            if (this.getValidateTime(Long.valueOf(group)) == 0 || this.has24.contains(Long.valueOf(group))) {
                continue;
            }
            long have = this.getValidateTime(Long.valueOf(group)) - System.currentTimeMillis();
            if (have <= 24 * 60 * 60 * 1000) {
                List<Long> adminList = this.getAdminList();
                String atStr = "";
                List<Member> groupMemberList = this.plugin.CQ.getGroupMemberList(Long.valueOf(group));
                for (int i = 0; i < groupMemberList.size(); i++) {
                    if (groupMemberList.get(i).getAuthority() == 1) {
                        continue;
                    }
                    atStr += this.plugin.CC.at(groupMemberList.get(i).getQqId());
                }
                this.plugin.CQ.sendGroupMsg(Long.valueOf(group), atStr + "本群到期时间还剩不到24小时");
                this.has24.add(Long.valueOf(group));
                System.out.println(group);
            } else {
                this.check5Day();
            }
        }
    }
}
