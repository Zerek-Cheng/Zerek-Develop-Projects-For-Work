package com.lmyun.socket.packages;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonPackage extends BasePackage {
    private JSONObject json = new JSONObject();

    public JsonPackage() {
        super();
        super.setType(3);
    }

    public JsonPackage(String pack) {
        super();
        super.setType(3);
        this.setJson((JSONObject) JSONValue.parse(pack));
        this.setData((Map) this.json.get("data"));
        super.setAction((String) this.json.get("action"));
        this.contentUpdate();
    }

    public JsonPackage setData(Map<String, Object> data) {
        this.json.put("data", data);
        return this;
    }

    public Map<String, Object> getData() {
        if (this.json.get("data") == null) {
            this.setData(new HashMap<>());
        }
        return (Map<String, Object>) this.json.get("data");
    }

    public JsonPackage contentUpdate() {
        this.json.put("time", System.currentTimeMillis());
        this.json.put("action", this.getAction());
        super.setContent(this.json.toJSONString().getBytes());
        return this;
    }

}
