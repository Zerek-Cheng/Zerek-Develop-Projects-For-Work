package clear;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class Time
        implements Listener
{
    private Check check;

    private class Check
            implements Runnable
    {
        private long pre;
        private int sum;

        private Check() {}

        public void run()
        {
            long now = System.currentTimeMillis();
            if (this.pre == 0L) {
                this.pre = now;
            }
            int past = (int)(now - this.pre);
            this.pre = now;
            this.sum += past;
            if (this.sum >= 1000)
            {
                this.sum = 0;
                TimeEvent0 timeEvent = new TimeEvent0();
                Bukkit.getPluginManager().callEvent(timeEvent);
            }
        }
    }

    public Time(Main main)
    {
        this.check = new Check();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this.check, 1L, 1L);
    }
}
