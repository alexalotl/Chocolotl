package chocolotl.screens;

import chocolotl.ChocolotlRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class TemperingMachineScreenHandler extends AbstractRecipeScreenHandler<Inventory> {
//    private final RecipeType<? extends AbstractCookingRecipe> recipeType = RecipeType.TEMPERING;
//    private final RecipeBookCategory recipeBookCategory = RecipeBookCategory.TEMPERING_MACHINE;
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public TemperingMachineScreenHandler(
            int syncId,
            PlayerInventory playerInventory
    ){
        this(syncId, playerInventory, new ArrayPropertyDelegate(0), new SimpleInventory(0));
    }

    public TemperingMachineScreenHandler(
            int syncId,
            PlayerInventory playerInventory,
            PropertyDelegate propertyDelegate,
            Inventory inventory
    ){
        super(ChocolotlRegistry.TEMPERING_MACHINE_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;

        // TODO Map slots to texture once texture is made.
        // Fuel Slot
        addSlot(new Slot(inventory, 0, 56, 17));
        // Choco Input 1 Slot
        addSlot(new Slot(inventory, 1, 56, 17));
        // Choco Input 2 Slot
        addSlot(new Slot(inventory, 2, 56, 17));
        // Choco Input 3 Slot
        addSlot(new Slot(inventory, 3, 56, 17));
        // Choco Input 4 Slot
        addSlot(new Slot(inventory, 4, 56, 17));
        // Ingredient Input 1 Slot
        addSlot(new Slot(inventory, 5, 56, 17));
        // Ingredient Input 2 Slot
        addSlot(new Slot(inventory, 6, 56, 17));
        // Output Slot
        addSlot(new Slot(inventory, 7, 56, 17));

        // Player Inventory Slots
        for(int i = 0; i < 3; i++) for(int j = 0; j < 9; j++)
            this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for(int i = 0; i < 9; i++)
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));

        addProperties(propertyDelegate);
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

    // TODO Implement quick move
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    public boolean canUse(PlayerEntity player){ return inventory.canPlayerUse(player); }
}
