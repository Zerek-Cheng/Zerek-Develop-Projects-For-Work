package com.lmyun.socket.packages.request.rsa.license;

import com.lmyun.socket.packages.JsonRSAPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LicenseCheckPackage extends JsonRSAPackage {
    private String license;
    private String licenseType;
    private String version = "1.2.1";

    public LicenseCheckPackage() {
        super();
        super.setAction("2001");
        super.setType(2001);
    }

    public LicenseCheckPackage(String pack) {
        super(pack);
        this.setAction("2001");
        this.setType(2001);
    }

    @Override
    public LicenseCheckPackage contentUpdate() {
        Map<String, Object> data = this.getData();
        data.put("license", this.license);
        data.put("licenseType", this.licenseType);
        data.put("version", this.version);
        this.setData(data);
        super.contentUpdate();
        return this;
    }
}
