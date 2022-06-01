package com._0myun;

import com._0myun.minecraft.pixelmonknockout.data.Game;

public class GameTest {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.setTimeType("day");
        System.out.println(
                game.getNextStartTime("2019年6月2日 16:00"));
    }
}
