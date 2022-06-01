package noppes.npcs.client.gui.player;

import cpw.mods.fml.relauncher.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import noppes.npcs.client.gui.util.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import noppes.npcs.controllers.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
public class GuiRecipes extends GuiNPCInterface
{
    private static final ResourceLocation resource;
    private int page;
    private boolean npcRecipes;
    private GuiNpcLabel label;
    private GuiNpcButton left;
    private GuiNpcButton right;
    private List<IRecipe> recipes;
    
    public GuiRecipes() {
        this.page = 0;
        this.npcRecipes = true;
        this.recipes = new ArrayList<IRecipe>();
        this.ySize = 182;
        this.xSize = 256;
        this.setBackground("recipes.png");
        this.closeOnEsc = true;
        this.recipes.addAll((Collection<? extends IRecipe>)RecipeController.instance.anvilRecipes.values());
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.addLabel(new GuiNpcLabel(0, "Recipe List", this.guiLeft + 5, this.guiTop + 5));
        this.addLabel(this.label = new GuiNpcLabel(1, "", this.guiLeft + 5, this.guiTop + 168));
        this.addButton(this.left = new GuiButtonNextPage(1, this.guiLeft + 150, this.guiTop + 164, true));
        this.addButton(this.right = new GuiButtonNextPage(2, this.guiLeft + 80, this.guiTop + 164, false));
        this.updateButton();
    }
    
    private void updateButton() {
        final GuiNpcButton right = this.right;
        final GuiNpcButton right2 = this.right;
        final boolean b = this.page > 0;
        right2.enabled = b;
        right.visible = b;
        final GuiNpcButton left = this.left;
        final GuiNpcButton left2 = this.left;
        final boolean b2 = this.page + 1 < MathHelper.ceiling_float_int(this.recipes.size() / 4.0f);
        left2.enabled = b2;
        left.visible = b2;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (!button.enabled) {
            return;
        }
        if (button == this.right) {
            --this.page;
        }
        if (button == this.left) {
            ++this.page;
        }
        this.updateButton();
    }
    
    @Override
    public void drawScreen(final int xMouse, final int yMouse, final float f) {
        super.drawScreen(xMouse, yMouse, f);
        this.mc.renderEngine.bindTexture(GuiRecipes.resource);
        this.label.label = this.page + 1 + "/" + MathHelper.ceiling_float_int(this.recipes.size() / 4.0f);
        this.label.x = this.guiLeft + (256 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.label.label)) / 2;
        for (int i = 0; i < 4; ++i) {
            final int index = i + this.page * 4;
            if (index >= this.recipes.size()) {
                break;
            }
            final IRecipe irecipe = this.recipes.get(index);
            if (irecipe.getRecipeOutput() != null) {
                int x = this.guiLeft + 5 + i / 2 * 126;
                int y = this.guiTop + 15 + i % 2 * 76;
                this.drawItem(irecipe.getRecipeOutput(), x + 98, y + 28, xMouse, yMouse);
                if (irecipe instanceof RecipeCarpentry) {
                    final RecipeCarpentry recipe = (RecipeCarpentry)irecipe;
                    x += (72 - recipe.recipeWidth * 18) / 2;
                    y += (72 - recipe.recipeHeight * 18) / 2;
                    for (int j = 0; j < recipe.recipeWidth; ++j) {
                        for (int k = 0; k < recipe.recipeHeight; ++k) {
                            this.mc.renderEngine.bindTexture(GuiRecipes.resource);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            this.drawTexturedModalRect(x + j * 18, y + k * 18, 0, 0, 18, 18);
                            final ItemStack item = recipe.getCraftingItem(j + k * recipe.recipeWidth);
                            if (item != null) {
                                this.drawItem(item, x + j * 18 + 1, y + k * 18 + 1, xMouse, yMouse);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            final int index = i + this.page * 4;
            if (index >= this.recipes.size()) {
                break;
            }
            final IRecipe irecipe = this.recipes.get(index);
            if (irecipe instanceof RecipeCarpentry) {
                final RecipeCarpentry recipe2 = (RecipeCarpentry)irecipe;
                if (recipe2.getRecipeOutput() != null) {
                    int x2 = this.guiLeft + 5 + i / 2 * 126;
                    int y2 = this.guiTop + 15 + i % 2 * 76;
                    this.drawOverlay(recipe2.getRecipeOutput(), x2 + 98, y2 + 22, xMouse, yMouse);
                    x2 += (72 - recipe2.recipeWidth * 18) / 2;
                    y2 += (72 - recipe2.recipeHeight * 18) / 2;
                    for (int j = 0; j < recipe2.recipeWidth; ++j) {
                        for (int k = 0; k < recipe2.recipeHeight; ++k) {
                            final ItemStack item = recipe2.getCraftingItem(j + k * recipe2.recipeWidth);
                            if (item != null) {
                                this.drawOverlay(item, x2 + j * 18 + 1, y2 + k * 18 + 1, xMouse, yMouse);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void drawItem(final ItemStack item, final int x, final int y, final int xMouse, final int yMouse) {
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        GuiRecipes.itemRender.zLevel = 100.0f;
        GuiRecipes.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.renderEngine, item, x, y);
        GuiRecipes.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.renderEngine, item, x, y);
        GuiRecipes.itemRender.zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    private void drawOverlay(final ItemStack item, final int x, final int y, final int xMouse, final int yMouse) {
        if (this.isPointInRegion(x - this.guiLeft, y - this.guiTop, 16, 16, xMouse, yMouse)) {
            this.renderToolTip(item, xMouse, yMouse);
        }
    }
    
    protected boolean isPointInRegion(final int left, final int top, final int right, final int bottom, int pointX, int pointY) {
        final int k1 = this.guiLeft;
        final int l1 = this.guiTop;
        pointX -= k1;
        pointY -= l1;
        return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
    }
    
    @Override
    public void save() {
    }
    
    static {
        resource = new ResourceLocation("customnpcs", "textures/gui/slot.png");
    }
}
