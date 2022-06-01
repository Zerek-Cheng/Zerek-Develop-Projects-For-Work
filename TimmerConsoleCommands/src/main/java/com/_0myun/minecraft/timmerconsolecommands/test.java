package com._0myun.minecraft.timmerconsolecommands;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
    public static void main(String[] args) {

        try {
            System.out.println(new SimpleDateFormat("yyyy年MM月dd日 h:m:s").parse("2019年3月24日 22:21:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
