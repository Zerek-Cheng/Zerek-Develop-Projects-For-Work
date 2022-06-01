package noppes.npcs.client;

import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.inventory.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import noppes.npcs.client.renderer.*;
import noppes.npcs.client.controllers.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import noppes.npcs.client.gui.player.*;
import net.minecraft.entity.player.*;

public class ClientTickHandler
{
    private World prevWorld;
    private boolean otherContainer;
    
    public ClientTickHandler() {
        this.otherContainer = false;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null && mc.thePlayer.openContainer instanceof ContainerPlayer) {
            if (this.otherContainer) {
                NoppesUtilPlayer.sendData(EnumPlayerPacket.CheckQuestCompletion, new Object[0]);
                this.otherContainer = false;
            }
        }
        else {
            this.otherContainer = true;
        }
        ++CustomNpcs.ticks;
        ++RenderNPCInterface.LastTextureTick;
        if (this.prevWorld != mc.theWorld) {
            this.prevWorld = (World)mc.theWorld;
            MusicController.Instance.stopMusic();
        }
    }
    
    @SubscribeEvent
    public void onKey(final InputEvent.KeyInputEvent event) {
        if (ClientProxy.QuestLog.isPressed()) {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                NoppesUtil.openGUI((EntityPlayer)mc.thePlayer, new GuiQuestLog((EntityPlayer)mc.thePlayer));
            }
            else if (mc.currentScreen instanceof GuiQuestLog) {
                mc.setIngameFocus();
            }
        }
    }
}
