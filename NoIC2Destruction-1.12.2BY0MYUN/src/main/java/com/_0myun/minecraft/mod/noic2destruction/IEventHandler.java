package com._0myun.minecraft.mod.noic2destruction;

import ic2.api.event.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IEventHandler {
    @SubscribeEvent
    public void onIC2(ExplosionEvent e) {
        //e.setCanceled(true);
        //e.getWorld().createExplosion(e.entity, e.pos.x, e.pos.y, e.pos.z, Float.valueOf(String.valueOf(e.power)), false);
        //System.out.println("你灵梦云爸爸成功帮助你工业防炸了,坐标" + e.pos.x + "," + e.pos.y + "," + e.pos.z);
    }
}
