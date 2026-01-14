package chocolotl.screens;

import chocolotl.blocks.TemperingMachineBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;

public class TemperingMachineScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
    private final RecipeType<? extends AbstractCookingRecipe> recipeType = RecipeType.TEMPERING;
    private final RecipeBookCategory recipeBookCategory = RecipeBookCategory.TEMPERING_MACHINE;

    public TemperingMachineScreenHandler(
            int syncId,
            PlayerInventory playerInventory,
            TemperingMachineBlockEntity blockEntity,
            PropertyDelegate propertyDelegate
    ){
        super(ScreenHandlerType.TEMPERING_MACHINE, syncId);
    }

    public void populateRecipeFinder(RecipeMatcher finder) {

    }

    public void clearCraftingSlots() {

    }

    public boolean matches(Recipe<? super Inventory> recipe) {
        return false;
    }

    public int getCraftingResultSlotIndex() {
        return 0;
    }

    public int getCraftingWidth() {
        return 0;
    }

    public int getCraftingHeight() {
        return 0;
    }

    public int getCraftingSlotCount() {
        return 0;
    }

    public RecipeBookCategory getCategory() {
        return null;
    }

    public boolean canInsertIntoSlot(int index) {
        return false;
    }

    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
