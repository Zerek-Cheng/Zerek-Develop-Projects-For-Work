package com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1默认包
 * 2心跳包
 * 3JSON默认包
 * 4JSON_RSA默认包
 * <p>
 * 1001请求RSA加密公钥
 * 2001授权请求
 * 2002授权成功
 * 2003授权失败
 * 2004类发送
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasePackage {

    private String action = "1";
    private byte[] content;
    private boolean cancel = false;
    private int type = 1;

}
