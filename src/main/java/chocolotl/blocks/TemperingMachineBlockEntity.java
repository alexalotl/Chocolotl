package chocolotl.blocks;

import chocolotl.ChocolotlRegistry;
import chocolotl.screens.TemperingMachineScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TemperingMachineBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {
    protected static final int FUEL_SLOT_INDEX = 0;
    protected static final int CHOCO_INPUT_SLOT_INDEX_1 = 1;
    protected static final int CHOCO_INPUT_SLOT_INDEX_2 = 2;
    protected static final int CHOCO_INPUT_SLOT_INDEX_3 = 3;
    protected static final int CHOCO_INPUT_SLOT_INDEX_4 = 4;
    protected static final int INGREDIENT_INPUT_SLOT_INDEX_1 = 5;
    protected static final int INGREDIENT_INPUT_SLOT_INDEX_2 = 6;
    protected static final int OUTPUT_SLOT_INDEX = 7;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        public int get(int index) {
            return 0;
        }

        public void set(int index, int value) {

        }

        public int size() {
            return 0;
        }
    };
    public TemperingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ChocolotlRegistry.TEMPERING_MACHINE_BE, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state, TemperingMachineBlockEntity blockEntity) {

    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    public int[] getAvailableSlots(Direction side) {
        // TODO Unimplemented for base version. Adjust to abstract class later.
        return new int[0];
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        // TODO Unimplemented for base version. Adjust to abstract class later.
        return false;
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        // TODO Unimplemented for base version. Adjust to abstract class later.
        return false;
    }

    public int size(){ return this.inventory.size(); }

    public boolean isEmpty(){
        for(ItemStack itemStack : this.inventory){
            if(!itemStack.isEmpty()){ return false; }
        }
        return true;
    }

    public ItemStack getStack(int slot){ return this.inventory.get(slot); }

    public ItemStack removeStack(int slot, int amount){ return Inventories.splitStack(this.inventory, slot, amount); }

    public ItemStack removeStack(int slot){ return Inventories.removeStack(this.inventory, slot); }

    public void setStack(int slot, ItemStack stack){
        ItemStack itemStack = this.inventory.get(slot);
        boolean canCombine = !stack.isEmpty() && ItemStack.canCombine(itemStack, stack);
        this.inventory.set(slot, stack);
        if(stack.getCount() > this.getMaxCountPerStack()){
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    public boolean canPlayerUse(PlayerEntity player){ return Inventory.canPlayerUse(this, player); }

    public void clear(){ this.inventory.clear(); }

    public Text getDisplayName(){ return Text.translatable("container.tempering_machine"); }

    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player){
        return new TemperingMachineScreenHandler(syncId, playerInventory, propertyDelegate, this);
    }
}
