package noppes.npcs.client;

import noppes.npcs.constants.*;
import noppes.npcs.*;
import cpw.mods.fml.common.network.internal.*;
import java.io.*;
import io.netty.buffer.*;

public class Client
{
    public static void sendData(final EnumPacketServer enu, final Object... obs) {
        final ByteBuf buffer = Unpooled.buffer();
        try {
            if (!Server.fillBuffer(buffer, enu, obs)) {
                return;
            }
            CustomNpcs.Channel.sendToServer(new FMLProxyPacket(buffer, "CustomNPCs"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
