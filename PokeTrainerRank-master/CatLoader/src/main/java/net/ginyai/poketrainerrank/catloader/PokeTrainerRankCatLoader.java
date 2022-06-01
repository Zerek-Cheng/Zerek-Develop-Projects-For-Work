package net.ginyai.poketrainerrank.catloader;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("PTRCatLoader")
@IFMLLoadingPlugin.TransformerExclusions("net.ginyai.poketrainerrank.catloader")
public class PokeTrainerRankCatLoader implements IFMLLoadingPlugin {
    static File self;

    public static File[] getPluginFiles(File[] origin) {
        if (self != null && origin != null) {
            File[] files = Arrays.copyOf(origin, origin.length + 1);
            files[origin.length] = self;
            return files;
        } else {
            return origin;
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
//                "net.ginyai.poketrainerrank.catloader.LoadPluginTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "net.ginyai.poketrainerrank.catloader.ModContainer";
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        self = (File) data.get("coremodLocation");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
