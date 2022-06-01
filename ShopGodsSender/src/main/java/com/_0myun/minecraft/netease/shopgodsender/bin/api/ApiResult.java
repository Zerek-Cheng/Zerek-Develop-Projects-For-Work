package com._0myun.minecraft.netease.shopgodsender.bin.api;

import com.google.gson.JsonObject;

import java.util.List;

public class ApiResult {
    private int code = -1;
    private String message;
    private String details;
    private List<JsonObject> entities;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<JsonObject> getEntities() {
        return entities;
    }

    public void setEntities(List<JsonObject> entities) {
        this.entities = entities;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
