package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.license;


import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.JsonRSAPackage;

public class LicenseSuccessPackage extends JsonRSAPackage {
    public LicenseSuccessPackage() {
        super();
        super.setAction("2002");
        super.setType(2002);
    }
}
