package chocolotl.blocks;

import chocolotl.ChocolotlRegistry;
import chocolotl.screens.TemperingMachineScreenHandler;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class TemperingMachineBlockEntity extends BlockEntity implements SidedInventory, NamedScreenHandlerFactory {
    enum ChocoType {
        MILK,
        DARK,
        WHITE,
        NONE
    }

    protected static final int FUEL_SLOT_INDEX = 0;
    protected static final int CHOCO_INPUT_SLOT_INDEX_1 = 1;
    protected static final int CHOCO_INPUT_SLOT_INDEX_2 = 2;
    protected static final int CHOCO_INPUT_SLOT_INDEX_3 = 3;
    protected static final int CHOCO_INPUT_SLOT_INDEX_4 = 4;
    protected static final int INGREDIENT_INPUT_SLOT_INDEX_1 = 5;
    protected static final int INGREDIENT_INPUT_SLOT_INDEX_2 = 6;
    protected static final int OUTPUT_SLOT_INDEX = 7;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
    private static final Set<Item> milkChocoInputs1 = Set.of(ChocolotlRegistry.COCOA_LIQUOR, ChocolotlRegistry.COCOA_BUTTER,
            ChocolotlRegistry.MILK_POWDER, Items.SUGAR);
    private static final Set<Item> milkChocoInputs2 = Set.of(ChocolotlRegistry.COCOA_LIQUOR, ChocolotlRegistry.COCOA_BUTTER,
            ChocolotlRegistry.MILK_POWDER, ChocolotlRegistry.BROWN_SUGAR);
    private static final Set<Item> darkChocoInputs1 = Set.of(ChocolotlRegistry.COCOA_LIQUOR, ChocolotlRegistry.COCOA_BUTTER,
            Items.AIR, Items.SUGAR);
    private static final Set<Item> darkChocoInputs2 = Set.of(ChocolotlRegistry.COCOA_LIQUOR, ChocolotlRegistry.COCOA_BUTTER,
            Items.AIR, ChocolotlRegistry.BROWN_SUGAR);
    private static final Set<Item> whiteChocoInputs1 = Set.of(Items.AIR, ChocolotlRegistry.COCOA_LIQUOR, ChocolotlRegistry.COCOA_BUTTER,
            ChocolotlRegistry.MILK_POWDER, Items.SUGAR);
    private static final Set<Item> whiteChocoInputs2 = Set.of(Items.AIR, ChocolotlRegistry.COCOA_BUTTER,
            ChocolotlRegistry.MILK_POWDER, ChocolotlRegistry.BROWN_SUGAR);
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    private ChocoType outputChocoType;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        public int get(int index) {
            return switch (index) {
                case 0 -> TemperingMachineBlockEntity.this.burnTime;
                case 1 -> TemperingMachineBlockEntity.this.fuelTime;
                case 2 -> TemperingMachineBlockEntity.this.cookTime;
                case 3 -> TemperingMachineBlockEntity.this.cookTimeTotal;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    TemperingMachineBlockEntity.this.burnTime = value;
                    break;
                case 1:
                    TemperingMachineBlockEntity.this.fuelTime = value;
                    break;
                case 2:
                    TemperingMachineBlockEntity.this.cookTime = value;
                    break;
                case 3:
                    TemperingMachineBlockEntity.this.cookTimeTotal = value;
            }

        }

        public int size() { return 4; }
    };
    public TemperingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ChocolotlRegistry.TEMPERING_MACHINE_BE, pos, state);
    }

    private int getFuelTime(ItemStack fuel) { return FuelRegistry.INSTANCE.get(fuel.getItem()); }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    private boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        return slots.get(7).isEmpty();
    }

    private boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, ChocoType chocoType) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack chocoInput1 = slots.get(1);
            ItemStack chocoInput2 = slots.get(2);
            ItemStack chocoInput3 = slots.get(3);
            ItemStack chocoInput4 = slots.get(4);
            ItemStack outputTarget = recipe.getOutput(registryManager);
            ItemStack output = slots.get(7);
            if (output.isEmpty()) {
                slots.set(7, outputTarget.copy());
            } else if (output.isOf(outputTarget.getItem())) {
                output.increment(1);
            }

            if (!chocoInput1.isEmpty()) {
                chocoInput1.decrement(1);
            }
            if (!chocoInput2.isEmpty()) {
                chocoInput2.decrement(1);
            }
            if (!chocoInput3.isEmpty()) {
                chocoInput3.decrement(1);
            }
            if (!chocoInput4.isEmpty()) {
                chocoInput4.decrement(1);
            }

            return true;
        } else {
            return false;
        }
    }

    public void tick(World world, BlockPos pos, BlockState state, TemperingMachineBlockEntity blockEntity) {
        boolean shouldMarkDirty = false;
        if (blockEntity.isBurning()) {
            burnTime--;
        }

        ItemStack chocoInput1 = blockEntity.inventory.get(1);
        ItemStack chocoInput2 = blockEntity.inventory.get(2);
        ItemStack chocoInput3 = blockEntity.inventory.get(3);
        ItemStack chocoInput4 = blockEntity.inventory.get(4);
        Set<Item> chocoInputs = Set.of(chocoInput1.getItem(), chocoInput2.getItem(), chocoInput3.getItem(), chocoInput4.getItem());

        ChocoType chocoType;
        if (chocoInputs.equals(milkChocoInputs1) || chocoInputs.equals((milkChocoInputs2))) {
            chocoType = ChocoType.MILK;
        } else if (chocoInputs.equals(darkChocoInputs1) || chocoInputs.equals((darkChocoInputs2))) {
            chocoType = ChocoType.DARK;
        } else if (chocoInputs.equals(whiteChocoInputs1) || chocoInputs.equals((whiteChocoInputs2))) {
            chocoType = ChocoType.WHITE;
        } else {
            chocoType = ChocoType.NONE;
        }

        ItemStack fuel = blockEntity.inventory.get(0);
        boolean hasFuel = !fuel.isEmpty();
        if (blockEntity.isBurning() || chocoType != ChocoType.NONE && hasFuel) {
            // TODO Setup recipes properly
            Recipe<?> recipe;
            if (hasFuel) {
                recipe = null;
//                recipe = (Recipe<?>)blockEntity.matchGetter.getFirstMatch(blockEntity, world).orElse(null);
            } else {
                recipe = null;
            }

            int maxCountPerStack = getMaxCountPerStack();
            if (!isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.burnTime = getFuelTime(fuel);
                blockEntity.fuelTime = blockEntity.burnTime;
                if (isBurning()) {
                    shouldMarkDirty = true;
                    if (hasFuel) {
                        fuel.decrement(1);
                        if (fuel.isEmpty()) {
                            Item remainder = fuel.getItem().getRecipeRemainder();
                            blockEntity.inventory.set(0, remainder == null ? ItemStack.EMPTY : new ItemStack(remainder));
                        }
                        outputChocoType = chocoType;
                    }
                }
            }

            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory, maxCountPerStack)) {
                blockEntity.cookTime++;
                if (blockEntity.cookTime == blockEntity.cookTimeTotal) {
                    blockEntity.cookTime = 0;
                    // TODO Replace with minigame timer.
                    blockEntity.cookTimeTotal = 200;
                    if (craftRecipe(world.getRegistryManager(), blockEntity.inventory, maxCountPerStack, blockEntity.outputChocoType)) {

                        // set as previous recipe??
                    }

                    shouldMarkDirty = true;
                }
            } else {
                blockEntity.cookTime = 0;
            }
        } else if (blockEntity.cookTime > 0) {
            blockEntity.cookTime = MathHelper.clamp(blockEntity.cookTime - 2, 0, blockEntity.cookTimeTotal);
        }

        if (shouldMarkDirty) {
            markDirty(world, pos, state);
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.burnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.cookTimeTotal = nbt.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putShort("CookTime", (short)this.cookTime);
        nbt.putShort("CookTimeTotal", (short)this.cookTimeTotal);
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
