package com.pixelmonmod.pixelmon.client.gui.pokemoneditor;

import com.pixelmonmod.pixelmon.api.pokemon.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.*;
import com.pixelmonmod.pixelmon.client.gui.elements.*;
import net.minecraft.client.resources.*;
import java.util.stream.*;
import com.pixelmonmod.pixelmon.client.gui.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import com.pixelmonmod.pixelmon.*;
import net.minecraft.client.gui.*;
import com.pixelmonmod.pixelmon.config.*;
import com.pixelmonmod.pixelmon.items.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import java.util.*;
import com.pixelmonmod.pixelmon.enums.*;

public class GuiPokemonEditorAdvanced extends GuiScreenDropDown
{
    private GuiIndividualEditorBase previousScreen;
    private Pokemon data;
    private GuiTextField[] evText;
    private GuiTextField[] ivText;
    private StatsType[] stats;
    private GuiTextField heldText;
    private ItemStack heldItem;
    private GuiTextField natureText;
    private GuiTextField friendshipText;
    private GuiDropDown<String> abilityDropDown;
    private GuiTextField[] allText;
    private static final int BUTTON_OKAY = 1;
    private static final int BUTTON_MAX_IVS = 3;
    private static final int BUTTON_MIN_IVS = 4;
    private static final int BUTTON_RANDOM_IVS = 5;
    private static final int BUTTON_RESET_EVS = 6;

    public GuiPokemonEditorAdvanced(final GuiIndividualEditorBase previousScreen) {
        this.evText = new GuiTextField[6];
        this.ivText = new GuiTextField[6];
        this.stats = new StatsType[] { StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed };
        this.heldItem = ItemStack.field_190927_a;
        this.previousScreen = previousScreen;
        this.data = this.previousScreen.p;
    }

    @Override
    public void func_73866_w_() {
        if (this.abilityDropDown != null) {
            this.checkFields();
        }
        super.func_73866_w_();
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 + 155, this.field_146295_m / 2 + 90, 30, 20, I18n.func_135052_a("gui.guiItemDrops.ok", new Object[0])));
        final String[] allAbilities = this.data.getBaseStats().abilities;
        final List<String> abilityList = Arrays.stream(allAbilities).filter(ability -> ability != null).collect(Collectors.toList());
        this.addDropDown(this.abilityDropDown = new GuiDropDown<String>(abilityList, this.data.getAbilityName(), this.field_146294_l / 2 - 130, this.field_146295_m / 2 - 40, 80, 50).setGetOptionString(ability -> I18n.func_135052_a("ability." + ability + ".name", new Object[0])));
        this.field_146292_n.add(new GuiButton(3, this.field_146294_l / 2 + 125, this.field_146295_m / 2 - 50, 60, 20, I18n.func_135052_a("gui.trainereditor.maxivs", new Object[0])));
        this.field_146292_n.add(new GuiButton(4, this.field_146294_l / 2 + 125, this.field_146295_m / 2 - 25, 60, 20, I18n.func_135052_a("gui.trainereditor.minivs", new Object[0])));
        this.field_146292_n.add(new GuiButton(5, this.field_146294_l / 2 + 125, this.field_146295_m / 2, 60, 20, I18n.func_135052_a("gui.trainereditor.randomivs", new Object[0])));
        this.field_146292_n.add(new GuiButton(6, this.field_146294_l / 2 + 125, this.field_146295_m / 2 + 35, 60, 20, I18n.func_135052_a("gui.trainereditor.resetevs", new Object[0])));
        this.allText = new GuiTextField[15];
        for (int i = 0; i < this.stats.length; ++i) {
            (this.evText[i] = new GuiTextField(i, this.field_146297_k.field_71466_p, this.field_146294_l / 2 + 45, this.field_146295_m / 2 - 55 + i * 25, 30, 20)).func_146180_a(String.valueOf(this.data.getEVs().getArray()[i]));
            (this.ivText[i] = new GuiTextField(i + this.evText.length, this.field_146297_k.field_71466_p, this.field_146294_l / 2 + 85, this.field_146295_m / 2 - 55 + i * 25, 30, 20)).func_146180_a(String.valueOf(this.data.getIVs().getArray()[i]));
            this.allText[i] = this.evText[i];
            this.allText[i + this.evText.length] = this.ivText[i];
        }
        this.heldText = new GuiTextField(13, this.field_146297_k.field_71466_p, this.field_146294_l / 2 - 130, this.field_146295_m / 2 - 4, 80, 20);
        if (!this.data.getHeldItem().func_190926_b()) {
            this.heldText.func_146180_a(this.data.getHeldItem().func_82833_r());
            this.updateHeldItem();
        }
        (this.natureText = new GuiTextField(14, this.field_146297_k.field_71466_p, this.field_146294_l / 2 - 130, this.field_146295_m / 2 + 34, 80, 20)).func_146180_a(this.data.getNature().getLocalizedName());
        (this.friendshipText = new GuiTextField(14, this.field_146297_k.field_71466_p, this.field_146294_l / 2 - 130, this.field_146295_m / 2 + 70, 80, 20)).func_146180_a(String.valueOf(this.data.getFriendship()));
        this.allText[12] = this.heldText;
        this.allText[13] = this.natureText;
        this.allText[14] = this.friendshipText;
    }

    @Override
    protected void drawBackgroundUnderMenus(final float renderPartialTicks, final int mouseX, final int mouseY) {
        this.field_146297_k.field_71446_o.func_110577_a(GuiResources.cwPanel);
        GuiHelper.drawImageQuad(this.field_146294_l / 2 - 200, this.field_146295_m / 2 - 120, 400.0, 240.0f, 0.0, 0.0, 1.0, 1.0, this.field_73735_i);
        RenderHelper.func_74518_a();
        GuiHelper.drawCenteredString(this.previousScreen.titleText, this.field_146294_l / 2, this.field_146295_m / 2 - 90, 0, false);
        for (final GuiTextField textField : this.allText) {
            textField.func_146194_f();
        }
        for (int i = 0; i < this.stats.length; ++i) {
            GuiHelper.drawStringRightAligned(this.stats[i].getLocalizedName(), this.field_146294_l / 2 + 40, this.field_146295_m / 2 - 50 + i * 25, 0, false);
        }
        this.field_146297_k.field_71466_p.func_78276_b(I18n.func_135052_a("gui.trainereditor.evs", new Object[0]), this.field_146294_l / 2 + 50, this.field_146295_m / 2 - 70, 0);
        this.field_146297_k.field_71466_p.func_78276_b(I18n.func_135052_a("gui.trainereditor.ivs", new Object[0]), this.field_146294_l / 2 + 90, this.field_146295_m / 2 - 70, 0);
        GuiHelper.drawCenteredString(I18n.func_135052_a("gui.screenpokechecker.ability", new Object[0]), this.field_146294_l / 2 - 90, this.abilityDropDown.getTop() - 10, 0, false);
        GuiHelper.drawCenteredString(I18n.func_135052_a("gui.trainereditor.helditem", new Object[0]), this.field_146294_l / 2 - 90, this.heldText.field_146210_g - 10, 0, false);
        GuiHelper.drawCenteredString(I18n.func_135052_a("gui.screenpokechecker.nature", new Object[0]), this.field_146294_l / 2 - 90, this.natureText.field_146210_g - 10, 0, false);
        GuiHelper.drawCenteredString(I18n.func_135052_a("gui.screenpokechecker.happiness", new Object[0]), this.field_146294_l / 2 - 90, this.friendshipText.field_146210_g - 10, 0, false);
        if (!this.heldItem.func_190926_b()) {
            this.field_146296_j.func_180450_b(this.heldItem, this.field_146294_l / 2 - 150, this.heldText.field_146210_g);
        }
        GuiHelper.bindPokemonSprite(this.data, this.field_146297_k);
        GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
        GuiHelper.drawImageQuad(this.field_146294_l / 2 - 157, this.field_146295_m / 2 - 73, 20.0, 20.0f, 0.0, 0.0, 1.0, 1.0, 1.0f);
    }

    protected void func_73869_a(final char key, final int keyCode) {
        for (final GuiTextField textField : this.allText) {
            textField.func_146201_a(key, keyCode);
        }
        GuiHelper.switchFocus(keyCode, this.allText);
        if (this.heldText.func_146206_l()) {
            this.updateHeldItem();
        }
        if (keyCode == 1 || keyCode == 28) {
            this.saveFields();
        }
    }

    @Override
    protected void mouseClickedUnderMenus(final int mouseX, final int mouseY, final int clickedButton) throws IOException {
        for (final GuiTextField textField : this.allText) {
            textField.func_146192_a(mouseX, mouseY, clickedButton);
        }
    }

    protected void func_146284_a(final GuiButton button) {
        super.func_146284_a(button);
        if (button.field_146127_k == 1) {
            this.saveFields();
        }
        else if (button.field_146127_k == 3) {
            for (final GuiTextField iv : this.ivText) {
                iv.func_146180_a("31");
            }
        }
        else if (button.field_146127_k == 4) {
            for (final GuiTextField iv : this.ivText) {
                iv.func_146180_a("0");
            }
        }
        else if (button.field_146127_k == 5) {
            for (final GuiTextField iv : this.ivText) {
                iv.func_146180_a(String.valueOf(RandomHelper.getRandomNumberBetween(0, 31)));
            }
        }
        else if (button.field_146127_k == 6) {
            for (final GuiTextField ev : this.evText) {
                ev.func_146180_a("0");
            }
        }
    }

    private void saveFields() {
        if (this.checkFields()) {
            this.field_146297_k.func_147108_a((GuiScreen)this.previousScreen);
        }
    }

    private void updateHeldItem() {
        final Item newItem = PixelmonItems.getItemFromName(this.heldText.func_146179_b());
        if (newItem instanceof ItemHeld) {
            this.heldItem = new ItemStack(newItem);
        }
        else if (!"en_us".equals(Minecraft.func_71410_x().func_135016_M().func_135041_c().func_135034_a())) {
            for (final Item item : PixelmonItems.allItemList) {
                if (item instanceof ItemHeld && new ItemStack(item).func_82833_r().equalsIgnoreCase(this.heldText.func_146179_b())) {
                    this.heldItem = new ItemStack(item);
                    return;
                }
            }
            this.heldItem = ItemStack.field_190927_a;
        }
        else {
            this.heldItem = ItemStack.field_190927_a;
        }
    }

    private boolean checkFields() {
        boolean isValid = true;
        for (final GuiTextField iv : this.ivText) {
            try {
                final int ivNumber = Integer.parseInt(iv.func_146179_b());
                if (ivNumber < 0) {
                    isValid = false;
                    iv.func_146180_a("0");
                }
            }
            catch (NumberFormatException e) {
                isValid = false;
                iv.func_146180_a("0");
            }
        }
        int evTotal = 0;
        for (final GuiTextField ev : this.evText) {
            int evNumber;
            try {
                evNumber = Integer.parseInt(ev.func_146179_b());
                if (evNumber < 0) {
                    isValid = false;
                    ev.func_146180_a("0");
                    evNumber = 0;
                }
            }
            catch (NumberFormatException e2) {
                isValid = false;
                ev.func_146180_a("0");
                evNumber = 0;
            }
            evTotal += evNumber;
        }
        final EnumNature newNature = EnumNature.natureFromString(this.natureText.func_146179_b());
        if (newNature == null) {
            isValid = false;
            this.natureText.func_146180_a(this.data.getNature().getLocalizedName());
        }
        this.updateHeldItem();
        if (this.heldItem.func_190926_b() && !this.heldText.func_146179_b().equals("")) {
            this.heldText.func_146180_a("");
            isValid = false;
        }
        int newFriendship = this.data.getFriendship();
        try {
            newFriendship = Integer.parseInt(this.friendshipText.func_146179_b());
            if (newFriendship > 255) {
                this.friendshipText.func_146180_a(String.valueOf(255));
                isValid = false;
            }
            else if (newFriendship < 0) {
                this.friendshipText.func_146180_a("0");
                isValid = false;
            }
        }
        catch (NumberFormatException ex) {}
        if (isValid) {
            try {
                for (int i = 0; i < this.stats.length; ++i) {
                    this.data.getEVs().set(this.stats[i], Integer.parseInt(this.evText[i].func_146179_b()));
                    this.data.getIVs().set(this.stats[i], Integer.parseInt(this.ivText[i].func_146179_b()));
                }
                this.data.setNature(newNature);
                this.data.setHeldItem(this.heldItem);
                this.data.setAbility(this.abilityDropDown.getSelected());
                this.data.setFriendship(newFriendship);
            }
            catch (NumberFormatException e3) {
                isValid = false;
            }
        }
        return isValid;
    }
}
