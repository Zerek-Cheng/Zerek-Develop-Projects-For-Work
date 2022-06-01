package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.utils.RSAUtils;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Getter
@Setter
public class JsonRSAPackage extends JsonPackage {
    private String publicKey;
    private String privateKey;

    public JsonRSAPackage() {
        super();
        super.setType(4);
    }

    public JsonRSAPackage(String pack) {
        this.setContent(pack.getBytes());
    }

    @Override
    public JsonRSAPackage contentUpdate() {
        super.contentUpdate();
        try {
            if (this.publicKey != null && this.publicKey != "") {
                super.setContent(
                        RSAUtils.publicEncrypt(super.getJson().toJSONString(), RSAUtils.getPublicKey(this.getPublicKey())).getBytes()
                );
            } else {
                super.setContent(
                        RSAUtils.privateEncrypt(super.getJson().toJSONString(), RSAUtils.getPrivateKey(this.getPrivateKey())).getBytes()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonPackage decode() {
        try {
            if (this.publicKey != null && this.publicKey != "") {
                super.setContent(
                        RSAUtils.publicDecrypt(new String(this.getContent()), RSAUtils.getPublicKey(this.getPublicKey())).getBytes()
                );
            } else {
                super.setContent(
                        RSAUtils.privateDecrypt(new String(this.getContent()), RSAUtils.getPrivateKey(this.getPrivateKey())).getBytes()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setJson((JSONObject) JSONValue.parse(new String(super.getContent())));
        return this;
    }

}
