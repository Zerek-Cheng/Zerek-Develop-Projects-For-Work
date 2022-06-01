package cn.slfeng.bbstoper;

import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class GetterRunnable extends BukkitRunnable {
    @Override
    public void run() {
        try {
            BBSToper.INSTANCE.Getter();
            BBSToper.INSTANCE.savePoster();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
