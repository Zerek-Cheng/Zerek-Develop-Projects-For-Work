//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package _new.custom.cuilian.particleseffect;

import _new.custom.cuilian.NewCustomCuiLian;
import _new.custom.cuilian.level.Level;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ParticlesEffectManager {
    public ParticlesEffectManager() {
    }

    public void Draw_Wings(LivingEntity le, int dj) {
       /* int[][] a = ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj)).wings.EffectList;

        for (int i = a.length - 1; i >= 0; --i) {
            for (int j = a[i].length - 1; j >= 0; --j) {
                if (a[i][j] != 0) {
                    if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+")) {
                        this.Draw(le.getLocation(), le.getLocation().add((double) (-a[i].length) * 1.0D / 10.0D + (double) j * 1.0D / 5.0D + 0.1D, 0.2D, 0.0D), 1.8D - (double) i * 1.0D / 5.0D + ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj)).wings.z, ParticlesFor1_9.getParticle(a[i][j]));
                    } else if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.8")) {
                        this.Draw(le.getLocation(), le.getLocation().add((double) (-a[i].length) * 1.0D / 10.0D + (double) j * 1.0D / 5.0D + 0.1D, 0.2D, 0.0D), 1.8D - (double) i * 1.0D / 5.0D + ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj)).wings.z, ParticlesFor1_8.getParticleEffectType(a[i][j]));
                    } else if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.7")) {
                        this.Draw(le.getLocation(), le.getLocation().add((double) (-a[i].length) * 1.0D / 10.0D + (double) j * 1.0D / 5.0D + 0.1D, 0.2D, 0.0D), 1.8D - (double) i * 1.0D / 5.0D + ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(dj)).wings.z, ParticlesFor1_7.getEffect(a[i][j]));
                    }
                }
            }
        }*/

    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, Particle pe) {
/*        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = this.getAngle(Ploc, Eloc);
        if (jdc < 90.0D) {
            jdc += 20.0D;
        } else {
            jdc -= 20.0D;
        }

        double jl = this.getDistance(Ploc, Eloc);
        double radians = Math.toRadians(jdc + (double) Ploc.getYaw() - 180.0D);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Ploc.getWorld().spawnParticle(pe, Ploc, 0);
        Ploc.subtract(x, g, y);*/
    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, ParticleEffect1_8 pe) {
/*        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = this.getAngle(Ploc, Eloc);
        if (jdc < 90.0D) {
            jdc += 20.0D;
        } else {
            jdc -= 20.0D;
        }

        double jl = this.getDistance(Ploc, Eloc);
        double radians = Math.toRadians(jdc + (double) Ploc.getYaw() - 180.0D);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Iterator var18 = Bukkit.getOnlinePlayers().iterator();

        while (var18.hasNext()) {
            Player p = (Player) var18.next();
            pe.display(Ploc.getDirection(), 0.0F, Ploc, new Player[]{p});
        }

        Ploc.subtract(x, g, y);*/
    }

    public void Draw(Location PlayerLocation, Location EffectLocation, double g, ParticleEffect1_7 pe) {
/*        Location Ploc = PlayerLocation.clone();
        Location Eloc = EffectLocation.clone();
        double jdc = this.getAngle(Ploc, Eloc);
        if (jdc < 90.0D) {
            jdc += 20.0D;
        } else {
            jdc -= 20.0D;
        }

        double jl = this.getDistance(Ploc, Eloc);
        double radians = Math.toRadians(jdc + (double) Ploc.getYaw() - 180.0D);
        double x = Math.cos(radians) * jl;
        double y = Math.sin(radians) * jl;
        Ploc.add(x, g, y);
        Iterator var18 = Bukkit.getOnlinePlayers().iterator();

        while (var18.hasNext()) {
            Player p = (Player) var18.next();
            pe.display(Ploc.getDirection(), 0.0F, Ploc, new Player[]{p});
        }

        Ploc.subtract(x, g, y);*/
    }

    public double getDistance(Location loc1, Location loc2) {
        double _x = Math.abs(loc2.getX() - loc1.getX());
        double _y = Math.abs(loc2.getY() - loc1.getY());
        return Math.sqrt(_x * _x + _y * _y);
    }

    public double getAngle(Location loc1, Location loc2) {
        double x = loc2.getX() - loc1.getX();
        double y = loc2.getY() - loc1.getY();
        double hypotenuse = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D));
        double cos = x / hypotenuse;
        double radian = Math.acos(cos);
        double angle = 180.0D / (3.141592653589793D / radian);
        if (y < 0.0D) {
            angle = -angle;
        } else if (y == 0.0D && x < 0.0D) {
            angle = 180.0D;
        }

        return angle;
    }
}
