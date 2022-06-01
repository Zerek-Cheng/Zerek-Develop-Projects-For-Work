package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages;


import java.util.HashMap;

public class HeartPackage extends JsonPackage {

    public HeartPackage() {
        super();
        super.setAction("2");
        super.setData(new HashMap());
        this.setType(2);
    }
}
