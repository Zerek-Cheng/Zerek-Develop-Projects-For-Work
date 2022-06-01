package com.lmyun.cqp.event;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class LicenseManager extends JFrame {
    private JPanel rootPanel;
    private JList groupList;
    private JButton saveButton;
    private JTextField groupText;
    private JTextField validateText;
    private JButton deleteButton;
    private GroupTimeLicense plugin;

    public LicenseManager() {
        this.plugin = plugin;
        add(rootPanel);
        setTitle("傻逼乱乱要的群授权控制");
        setSize(500, 500);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultListModel groupListModel = (DefaultListModel) groupList.getModel();
        this.validateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
    }

    public LicenseManager(GroupTimeLicense plugin) {
        this.plugin = plugin;
        add(rootPanel);
        setTitle("傻逼乱乱要的群授权控制");
        setSize(500, 500);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultListModel groupListModel = (DefaultListModel) groupList.getModel();
        Set<String> groups = this.plugin.dataManager.getGroups();
        this.validateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));
        for (String group : groups) {
            groupListModel.addElement(group);
        }

        groupList.addListSelectionListener(e -> {
            String value = String.valueOf(this.groupList.getSelectedValue());
            this.groupText.setText(value);
            this.validateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(this.plugin.dataManager.getValidateTime(Long.valueOf(value))));
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse = simpleDateFormat.parse(validateText.getText());
                    long group = Long.valueOf(groupText.getText());
                    long validate = parse.getTime();
                    plugin.dataManager.setValidateTime(group, validate);
                    plugin.dataManager.save();
                    plugin.CQ.sendGroupMsg(group, "本群到期时间已被管理员设置为：" + validateText.getText());
                    JOptionPane.showMessageDialog(null, "保存成功...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    long group = Long.valueOf(groupText.getText());
                    plugin.dataManager.removeGroup(group);
                    plugin.dataManager.save();
                    plugin.CQ.sendGroupMsg(group, "本群到期时间已被删除");
                    JOptionPane.showMessageDialog(null, "保存成功...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
