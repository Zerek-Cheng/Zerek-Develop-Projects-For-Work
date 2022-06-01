package com.lmyun.socket.packages.request.rsa.license;

import com.lmyun.socket.packages.JsonRSAPackage;

public class LicenseFailedPackage extends JsonRSAPackage {
    public LicenseFailedPackage() {
        super();
        super.setAction("2003");
        super.setType(2003);
    }
}
