package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.license;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.JsonRSAPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LicenseSendClassPackage extends JsonRSAPackage {
    private String version = "1.0.0";
    private String classFile;

    public LicenseSendClassPackage() {
        super();
        super.setAction("2004");
        super.setType(2004);
    }

    public LicenseSendClassPackage(String pack) {
        super(pack);
        this.setAction("2004");
        this.setType(2004);
    }

    @Override
    public LicenseSendClassPackage contentUpdate() {
        Map<String, Object> data = this.getData();
        data.put("version", this.version);
        data.put("classFile", this.classFile);
        this.setData(data);
        super.contentUpdate();
        return this;
    }
}
