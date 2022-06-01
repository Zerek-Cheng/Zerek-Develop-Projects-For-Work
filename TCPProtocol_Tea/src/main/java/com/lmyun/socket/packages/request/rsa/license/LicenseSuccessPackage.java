package com.lmyun.socket.packages.request.rsa.license;

import com.lmyun.socket.packages.JsonRSAPackage;

public class LicenseSuccessPackage extends JsonRSAPackage {
    public LicenseSuccessPackage() {
        super();
        super.setAction("2002");
        super.setType(2002);
    }
}
