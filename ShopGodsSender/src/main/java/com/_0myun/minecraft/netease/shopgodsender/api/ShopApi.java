package com._0myun.minecraft.netease.shopgodsender.api;

import com._0myun.minecraft.netease.shopgodsender.Main;
import com._0myun.minecraft.netease.shopgodsender.bin.Order;
import com._0myun.minecraft.netease.shopgodsender.bin.api.ApiResult;
import com._0myun.minecraft.netease.shopgodsender.bin.api.ListParams;
import com._0myun.minecraft.netease.shopgodsender.bin.api.ShipParams;
import com._0myun.minecraft.netease.shopgodsender.util.Encrypter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopApi {
    private static String gateway = "https://x19mclobt.nie.netease.com:9090";
    private static String gatewayTest = "https://x19mclexpr.nie.netease.com:9090";

    private static String gameId;
    private static String key;

    public static String getGateway() {
        int mode = Main.getPlugin().pluginConfig.get("Config").getInt("mode");
        if (mode == 0) {
            return gatewayTest;
        } else if (mode == 1) {
            return gateway;
        }
        return gatewayTest;
    }

    public static String getGameId() {
        return gameId;
    }

    public static void setGameId(String gameId) {
        ShopApi.gameId = gameId;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        ShopApi.key = key;
    }

    private static String sign(String url, String params) {
        String signStr = "POST";
        signStr += url;
        signStr += params;
        return Encrypter.sha256_HMAC(signStr, getKey());
    }

    public static List<Order> requestOrders(UUID uuid) {
        try {
            String subName = "/get-mc-item-order-list";
            HttpPost client = new HttpPost(getGateway() + subName);
            ListParams paramsObj = new ListParams();
            paramsObj.setGameid(getGameId());
            paramsObj.setUuid(uuid.toString());
            String paramsStr = new Gson().toJson(paramsObj);

            client.setEntity(new StringEntity(paramsStr));

            String sign = sign(subName, paramsStr);
            client.addHeader("Netease-Server-Sign", sign);
            client.setHeader("Content-Type", "application/json");
            client.setHeader("Accept", "application/json");

            CloseableHttpResponse resultRes = new DefaultHttpClient().execute(client);
            String resultStr = EntityUtils.toString(resultRes.getEntity());
            ApiResult apiResult = new Gson().fromJson(resultStr, ApiResult.class);
            if (apiResult.getCode() != 0) {
                return null;
            }
            List<Order> orders = new ArrayList<>();
            List<JsonObject> entitiesJsonObj = apiResult.getEntities();
            entitiesJsonObj.forEach((entityJson) -> {
                Order order = new Gson().fromJson(entityJson, Order.class);
                orders.add(order);
            });
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean shipOrder(UUID uuid, List<Long> orders) {
        try {
            String subName = "/ship-mc-item-order";
            HttpPost client = new HttpPost(getGateway() + subName);
            ShipParams paramsObj = new ShipParams();
            paramsObj.setGameid(getGameId());
            paramsObj.setUuid(uuid.toString());
            paramsObj.getOrderid_list().addAll(orders);
            String paramsStr = new Gson().toJson(paramsObj);

            client.setEntity(new StringEntity(paramsStr));

            String sign = sign(subName, paramsStr);
            client.addHeader("Netease-Server-Sign", sign);
            client.setHeader("Content-Type", "application/json");
            client.setHeader("Accept", "application/json");

            CloseableHttpResponse resultRes = new DefaultHttpClient().execute(client);
            String resultStr = EntityUtils.toString(resultRes.getEntity());
            ApiResult apiResult = new Gson().fromJson(resultStr, ApiResult.class);
            return apiResult.getCode() == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
