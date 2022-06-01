package wew.RewardPoints;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Expansion extends PlaceholderExpansion {
    @NotNull
    @Override
    public String getIdentifier() {
        return "AboutNothing";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "Oscar_Wen";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.1";
    }

    @Override
    public String onPlaceholderRequest(@NotNull Player arg0, @NotNull String arg1) {
/*        if (arg1.equalsIgnoreCase("e")) {
            return (String)wew.E.Main.PlayerData.get(arg0.getName());
        }*/
        if (arg1.equalsIgnoreCase("yes1")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 0);
        }
        if (arg1.equalsIgnoreCase("yes2")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 1);
        }
        if (arg1.equalsIgnoreCase("yes3")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 2);
        }
        if (arg1.equalsIgnoreCase("no1")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 3);
        }
        if (arg1.equalsIgnoreCase("no2")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 4);
        }
        if (arg1.equalsIgnoreCase("no3")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 5);
        }
        if (arg1.equalsIgnoreCase("words")) {
            return wew.oscar_wen.Main.getPlayerCalendar(arg0, 6);
        }
        if (arg1.equalsIgnoreCase("points_own")) {
            return String.valueOf(wew.oscar_wen.Main.getPoints(arg0));
        }
        if (arg1.equalsIgnoreCase("pointstop1")) {
            return wew.oscar_wen.Main.getPlayerPoints(0);
        }
        if (arg1.equalsIgnoreCase("pointstop2")) {
            return wew.oscar_wen.Main.getPlayerPoints(1);
        }
        if (arg1.equalsIgnoreCase("pointstop3")) {
            return wew.oscar_wen.Main.getPlayerPoints(2);
        }
        if (arg1.equalsIgnoreCase("pointstop4")) {
            return wew.oscar_wen.Main.getPlayerPoints(3);
        }
        if (arg1.equalsIgnoreCase("pointstop5")) {
            return wew.oscar_wen.Main.getPlayerPoints(4);
        }
        if (arg1.equalsIgnoreCase("pointstop6")) {
            return wew.oscar_wen.Main.getPlayerPoints(5);
        }
        if (arg1.equalsIgnoreCase("pointstop7")) {
            return wew.oscar_wen.Main.getPlayerPoints(6);
        }
        if (arg1.equalsIgnoreCase("pointstop8")) {
            return wew.oscar_wen.Main.getPlayerPoints(7);
        }
        if (arg1.equalsIgnoreCase("yysstop1")) {
            return wew.oscar_wen.Main.getHeroes("银翼射手", 0);
        }
        if (arg1.equalsIgnoreCase("yysstop2")) {
            return wew.oscar_wen.Main.getHeroes("银翼射手", 1);
        }
        if (arg1.equalsIgnoreCase("yysstop3")) {
            return wew.oscar_wen.Main.getHeroes("银翼射手", 2);
        }
        if (arg1.equalsIgnoreCase("yysstop4")) {
            return wew.oscar_wen.Main.getHeroes("银翼射手", 3);
        }
        if (arg1.equalsIgnoreCase("yysstop5")) {
            return wew.oscar_wen.Main.getHeroes("银翼射手", 4);
        }
        if (arg1.equalsIgnoreCase("sxkmtop1")) {
            return wew.oscar_wen.Main.getHeroes("嗜血狂魔", 0);
        }
        if (arg1.equalsIgnoreCase("sxkmtop2")) {
            return wew.oscar_wen.Main.getHeroes("嗜血狂魔", 1);
        }
        if (arg1.equalsIgnoreCase("sxkmtop3")) {
            return wew.oscar_wen.Main.getHeroes("嗜血狂魔", 2);
        }
        if (arg1.equalsIgnoreCase("sxkmtop4")) {
            return wew.oscar_wen.Main.getHeroes("嗜血狂魔", 3);
        }
        if (arg1.equalsIgnoreCase("sxkmtop5")) {
            return wew.oscar_wen.Main.getHeroes("嗜血狂魔", 4);
        }
        if (arg1.equalsIgnoreCase("flqytop1")) {
            return wew.oscar_wen.Main.getHeroes("风灵轻语", 0);
        }
        if (arg1.equalsIgnoreCase("flqytop2")) {
            return wew.oscar_wen.Main.getHeroes("风灵轻语", 1);
        }
        if (arg1.equalsIgnoreCase("flqytop3")) {
            return wew.oscar_wen.Main.getHeroes("风灵轻语", 2);
        }
        if (arg1.equalsIgnoreCase("flqytop4")) {
            return wew.oscar_wen.Main.getHeroes("风灵轻语", 3);
        }
        if (arg1.equalsIgnoreCase("flqytop5")) {
            return wew.oscar_wen.Main.getHeroes("风灵轻语", 4);
        }
        if (arg1.equalsIgnoreCase("dmsltop1")) {
            return wew.oscar_wen.Main.getHeroes("堕魔圣灵", 0);
        }
        if (arg1.equalsIgnoreCase("dmsltop2")) {
            return wew.oscar_wen.Main.getHeroes("堕魔圣灵", 1);
        }
        if (arg1.equalsIgnoreCase("dmsltop3")) {
            return wew.oscar_wen.Main.getHeroes("堕魔圣灵", 2);
        }
        if (arg1.equalsIgnoreCase("dmsltop4")) {
            return wew.oscar_wen.Main.getHeroes("堕魔圣灵", 3);
        }
        if (arg1.equalsIgnoreCase("dmsltop5")) {
            return wew.oscar_wen.Main.getHeroes("堕魔圣灵", 4);
        }
        if (arg1.equalsIgnoreCase("xwxztop1")) {
            return wew.oscar_wen.Main.getHeroes("虚无行者", 0);
        }
        if (arg1.equalsIgnoreCase("xwxztop2")) {
            return wew.oscar_wen.Main.getHeroes("虚无行者", 1);
        }
        if (arg1.equalsIgnoreCase("xwxztop3")) {
            return wew.oscar_wen.Main.getHeroes("虚无行者", 2);
        }
        if (arg1.equalsIgnoreCase("xwxztop4")) {
            return wew.oscar_wen.Main.getHeroes("虚无行者", 3);
        }
        if (arg1.equalsIgnoreCase("xwxztop5")) {
            return wew.oscar_wen.Main.getHeroes("虚无行者", 4);
        }
        return arg1;
    }
}