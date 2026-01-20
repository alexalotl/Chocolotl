package chocolotl;

import chocolotl.blocks.ChocolateCakeBlock;
import chocolotl.blocks.TemperingMachineBlock;
import chocolotl.blocks.TemperingMachineBlockEntity;
import chocolotl.items.MolassesBucketItem;
import chocolotl.screens.TemperingMachineScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static chocolotl.Chocolotl.chocId;

public class ChocolotlRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ChocolotlRegistry.COCOA_POWDER))
            .displayName(Text.translatable("itemGroup.chocolotl"))
            .entries((context, entries) -> {
                for (Item item : ITEMS) {
                    entries.add(item);
                }
            })
            .build();

    // Items
    public static final Item COCOA_LIQUOR = new Item(new FabricItemSettings());
    public static final Item COCOA_BUTTER = new Item(new FabricItemSettings());
    public static final Item COCOA_POWDER = new Item(new FabricItemSettings());
    public static final Item BROWN_SUGAR = new Item(new FabricItemSettings());
    public static final Item MILK_POWDER = new Item(new FabricItemSettings());
    public static final Item MOLASSES_BUCKET = new MolassesBucketItem(new FabricItemSettings());
    public static final Item HAZELNUT = new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder()
                    .hunger(2)
                    .saturationModifier(0.1F)
                    .snack()
                    .build()));
    public static final Item ROASTED_HAZELNUT = new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder()
                    .hunger(4)
                    .saturationModifier(0.6F)
                    .snack()
                    .build()));

    // Blocks
    public static final Block CHOCOLATE_CAKE = new ChocolateCakeBlock(Block.Settings.create().strength(0.5f));
    // TODO: consider change to this and remove ChocolateCakeBlock.java
//  public static final Block CHOCOLATE_CAKE_2 = new CakeBlock(Block.Settings.create().strength(0.5f));
    public static final TemperingMachineBlock TEMPERING_MACHINE_BLOCK = new TemperingMachineBlock(AbstractBlock.Settings.create());

    // Block Entities
    public static final BlockEntityType<TemperingMachineBlockEntity> TEMPERING_MACHINE_BE = FabricBlockEntityTypeBuilder
            .create(TemperingMachineBlockEntity::new, ChocolotlRegistry.TEMPERING_MACHINE_BLOCK)
            .build();

    // Screen Handlers
    public static final ScreenHandlerType<TemperingMachineScreenHandler> TEMPERING_MACHINE_SCREEN_HANDLER = new ScreenHandlerType<>(TemperingMachineScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void setup() {
        // Items
        register("cocoa_liquor", COCOA_LIQUOR);
        register("cocoa_butter", COCOA_BUTTER);
        register("cocoa_powder", COCOA_POWDER);
        register("brown_sugar", BROWN_SUGAR);
        register("milk_powder", MILK_POWDER);
        register("molasses_bucket", MOLASSES_BUCKET);
        register("hazelnut", HAZELNUT);
        register("roasted_hazelnut", ROASTED_HAZELNUT);

        // Blocks
        register("chocolate_cake", CHOCOLATE_CAKE);
        register("tempering_machine", TEMPERING_MACHINE_BLOCK);

        // Block Entities
        register("tempering_machine", TEMPERING_MACHINE_BE);

        // Screen Handlers
        register("tempering_machine", TEMPERING_MACHINE_SCREEN_HANDLER);

        // Item Group
        Registry.register(Registries.ITEM_GROUP, chocId("item_group"), ITEM_GROUP);
    }

    private static void register(String name, Item item){
        Registry.register(Registries.ITEM, chocId(name), item);
        ITEMS.add(item);
    }

    private static void register(String name, Block block){ register(name, block, true); }

    private static void register(String name, Block block, boolean includeItem){
        Registry.register(Registries.BLOCK, chocId(name), block);
        BLOCKS.add(block);
        if(includeItem){
            register(name, new BlockItem(block, new Item.Settings()));
        }
    }

    private static void register(String name, BlockEntityType<?> blockEntityType){
        Registry.register(Registries.BLOCK_ENTITY_TYPE, chocId(name), blockEntityType);
    }

    private static void register(String name, ScreenHandlerType<?> screenHandler){
        Registry.register(Registries.SCREEN_HANDLER, chocId(name), screenHandler);
    }
}
