package net.minecraft.client.resources;

import java.io.*;

public class NPCResourceHelper
{
    public static File getPackFile(final AbstractResourcePack pack) {
        return pack.resourcePackFile;
    }
}
