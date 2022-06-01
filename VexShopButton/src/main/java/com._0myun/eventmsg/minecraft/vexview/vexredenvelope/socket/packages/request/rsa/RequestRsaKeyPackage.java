package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa;


import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.JsonPackage;

import java.util.Map;

public class RequestRsaKeyPackage extends JsonPackage {

    public RequestRsaKeyPackage() {
        super();
        this.setAction("1001");
        this.setType(1001);
    }

    public RequestRsaKeyPackage(String pack) {
        super(pack);
        this.setAction("1001");
        this.setType(1001);
    }

    public String getPublicKey() {
        return (String) super.getData().get("publicKey");
    }

    public RequestRsaKeyPackage setPublicKey(String publicKey) {
        Map<String, Object> data = super.getData();
        data.put("publicKey", publicKey);
        super.setData(data);
        return this;
    }
}
