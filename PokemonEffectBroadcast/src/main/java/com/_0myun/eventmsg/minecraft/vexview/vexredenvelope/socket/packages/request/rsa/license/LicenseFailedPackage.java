package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.license;


import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.JsonRSAPackage;

public class LicenseFailedPackage extends JsonRSAPackage {
    public LicenseFailedPackage() {
        super();
        super.setAction("2003");
        super.setType(2003);
    }
}
