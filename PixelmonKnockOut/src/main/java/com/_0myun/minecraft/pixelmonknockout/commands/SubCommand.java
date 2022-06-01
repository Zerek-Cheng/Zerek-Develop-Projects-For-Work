package com._0myun.minecraft.pixelmonknockout.commands;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    String label();

    int args() default 0;

    String permission() default "";
}