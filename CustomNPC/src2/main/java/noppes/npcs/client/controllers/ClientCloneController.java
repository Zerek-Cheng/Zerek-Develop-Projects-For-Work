package noppes.npcs.client.controllers;

import noppes.npcs.controllers.*;
import java.io.*;
import noppes.npcs.*;

public class ClientCloneController extends ServerCloneController
{
    public static ClientCloneController Instance;
    
    @Override
    public File getDir() {
        final File dir = new File(CustomNpcs.Dir, "clones");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
