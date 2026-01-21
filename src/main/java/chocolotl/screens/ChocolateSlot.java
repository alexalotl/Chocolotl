package chocolotl.screens;

import chocolotl.ChocolotlRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class ChocolateSlot extends Slot {
    public ChocolateSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean canInsert(ItemStack stack){
        return (stack.isOf(ChocolotlRegistry.COCOA_BUTTER) ||
                stack.isOf(ChocolotlRegistry.COCOA_LIQUOR) ||
                stack.isOf(ChocolotlRegistry.MILK_POWDER) ||
                stack.isOf(ChocolotlRegistry.BROWN_SUGAR) ||
                stack.isOf(Items.SUGAR));
    }
}
