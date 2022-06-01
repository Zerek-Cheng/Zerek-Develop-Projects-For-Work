/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package equationexp.defoli_ation.main.file.expfile;

import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

public class LocalPlayerExpFile
implements PlayerExpFile {
    private File folder;
    private ConcurrentHashMap<String, Integer> playerExpMap = new ConcurrentHashMap();
    private final Timer timer;

    public LocalPlayerExpFile(File floder, int time) {
        this.folder = floder;
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
        this.timer = new Timer();
        this.timer.schedule(new TimerTask(){

            @Override
            public void run() {
                LocalPlayerExpFile.this.save();
            }
        }, 500000L, (long)time);
    }

    @Override
    public void savePlayerExp(Player p, int exp) {
        this.playerExpMap.put(p.getName(), exp);
    }

    @Override
    public int getPlayerExp(Player p) {
        if (this.playerExpMap.containsKey(p.getName())) {
            return this.playerExpMap.get(p.getName());
        }
        int exp = this.getPlayerExpInFile(p);
        this.playerExpMap.put(p.getName(), exp);
        return exp;
    }

    @Override
    public void disable() {
        this.timer.cancel();
        this.save();
    }

    @Override
    public boolean exist(Player p) {
        File f = this.getPlayerExpFile(p.getName());
        return f.exists();
    }

    private int getPlayerExpInFile(Player p) {
        File f = this.getPlayerExpFile(p.getName());
        FileReader read = null;
        try {
            read = new FileReader(f);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner in = new Scanner(read);
        if (in.hasNextLine()) {
            String string = in.nextLine();
            in.close();
            String stringExp = string.substring(5);
            int exp = Integer.parseInt(stringExp);
            return exp;
        }
        in.close();
        return 0;
    }

    @Override
    public void save() {
        Set<String> playerSet = this.playerExpMap.keySet();
        for (String p : playerSet) {
            File playerFile = this.getPlayerExpFile(p);
            if (!playerFile.exists()) {
                this.creaftPlayerDataFile(playerFile);
            }
            Writer write = this.getWriter(playerFile);
            try {
                write.write("exp :" + this.playerExpMap.get(p));
                write.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void creaftPlayerDataFile(File playerFile) {
        try {
            playerFile.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Writer getWriter(File f) {
        try {
            return new FileWriter(f);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getPlayerExpFile(String p) {
        File f = new File(this.folder, String.valueOf(p) + ".exp");
        if (!f.exists()) {
            this.creaftPlayerDataFile(f);
        }
        return f;
    }

}

