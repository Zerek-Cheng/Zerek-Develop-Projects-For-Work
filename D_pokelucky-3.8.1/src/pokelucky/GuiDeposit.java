package pokelucky;

import com.pixelmonmod.pixelmon.storage.ClientData;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.opengl.GL11;

public class GuiDeposit
        extends GuiScreen
{
    int money;
    GuiButton button;
    GuiTextField input;

    public void func_73876_c()
    {
        this.input.func_146178_a();
        this.input.func_146195_b(true);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks)
    {
        this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("pokelucky:textures/gui/bank.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        func_146276_q_();
        func_73729_b(this.field_146294_l / 2 - 130, this.field_146295_m / 2 - 130, 0, 0, 250, 250);
        this.field_146289_q.func_78276_b("Banking Deposit", this.field_146294_l / 2 - 36, this.field_146295_m / 2 - 88, 0);
        this.field_146289_q.func_78276_b("Money : " + ClientData.playerMoney, this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 72, 16776960);
        this.field_146289_q.func_78276_b("Deposit : " + this.input.func_146179_b(), this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 50, 16776960);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public void func_73866_w_()
    {
        this.field_146292_n.clear();
        this.field_146292_n.add(this.button = new GuiButton(0, this.field_146294_l / 2 - 70, this.field_146295_m / 2 - 30, 140, 20, "Deposit"));
        this.input = new GuiTextField(0, this.field_146289_q, this.field_146294_l / 2 - 80, this.field_146295_m / 2 - 55, 45, 20);
        super.func_73866_w_();
    }

    protected void func_146284_a(GuiButton button) {
        switch (button.field_146127_k)
        {
            case 0:
                try
                {
                    Integer money = Integer.valueOf(Integer.parseInt(this.input.func_146179_b()));
                    if (ClientData.playerMoney < money.intValue()) {
                        this.field_146297_k.field_71439_g.func_145747_a(new TextComponentString("Your money is not enough."));
                    }else if (money.intValue() >= 100) {
                        NetPacket.sendToServer(new PacketGetDeposit(money.intValue()));
                    } else if (money.intValue() < 100) {
                        this.field_146297_k.field_71439_g.func_145747_a(new TextComponentString("Your can't deposit less than 100 PD"));
                    } else if (ClientData.playerMoney < money.intValue()) {
                        this.field_146297_k.field_71439_g.func_145747_a(new TextComponentString("Your money is not enough."));
                    }
                }
                catch (NumberFormatException e)
                {
                    this.field_146297_k.field_71439_g.func_145747_a(new TextComponentString("Use number only"));
                }
                this.field_146297_k.func_147108_a((GuiScreen)null);
                this.field_146297_k.func_71381_h();
        }
        super.func_146284_a(button);
    }

    protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
        super.func_73869_a(p_73869_1_, p_73869_2_);
        this.input.func_146201_a(p_73869_1_, p_73869_2_);
    }

    protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
        this.input.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    public boolean func_73868_f()
    {
        return false;
    }
}
