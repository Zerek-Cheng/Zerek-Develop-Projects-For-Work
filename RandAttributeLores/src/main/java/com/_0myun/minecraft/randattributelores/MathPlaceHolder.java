package com._0myun.minecraft.randattributelores;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Random;

public class MathPlaceHolder extends EZPlaceholderHook {
    public MathPlaceHolder() {
        super(Main.INSTANCE, "LMMATH");
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (params.equalsIgnoreCase("level")) return String.valueOf(p.getLevel());
        String[] args = params.split("_");
        if (args.length < 3) return "0";
        Double min = Double.valueOf(args[1]);
        Double max = Double.valueOf(args[2]);
        DecimalFormat df = new DecimalFormat("#.00");
        min = Double.valueOf(df.format(min));
        max = Double.valueOf(df.format(max));
        if (args[0].equalsIgnoreCase("int") || args[0].equalsIgnoreCase("integer")) {
            return String.valueOf(new Random().nextInt(max.intValue() - min.intValue()) + min.intValue());
        } else if (args[0].equalsIgnoreCase("double")) {

            return String.valueOf(Double.valueOf(df.format(new Random().nextDouble() * (max - min) + min)));
        }

        return "0";
    }
}
