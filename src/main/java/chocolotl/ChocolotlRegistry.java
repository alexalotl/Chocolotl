package chocolotl;

import chocolotl.blocks.ChocolateCakeBlock;
import chocolotl.items.BrownSugarItem;
import chocolotl.items.CocoaPowderItem;
import chocolotl.items.MolassesBucketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
    public static final Item COCOA_POWDER = new CocoaPowderItem(new FabricItemSettings());
    public static final Item BROWN_SUGAR = new BrownSugarItem(new FabricItemSettings());
    public static final Item MOLASSES_BUCKET = new MolassesBucketItem(new FabricItemSettings());
    public static final Item HAZELNUT = new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder()
                    .hunger(2).saturationModifier(0.1F)
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

    public static void setup() {
        // Items
        register("cocoa_liquor", COCOA_LIQUOR);
        register("cocoa_butter", COCOA_BUTTER);
        register("cocoa_powder", COCOA_POWDER);
        register("brown_sugar", BROWN_SUGAR);
        register("molasses_bucket", MOLASSES_BUCKET);
        register("hazelnut", HAZELNUT);
        register("roasted_hazelnut", ROASTED_HAZELNUT);

        // Blocks
        register("chocolate_cake", CHOCOLATE_CAKE);

        // Item Group
        Registry.register(Registries.ITEM_GROUP, chocId("item_group"), ITEM_GROUP);
    }

    private static void register(String name, Item item) {
        Registry.register(Registries.ITEM, chocId(name), item);
        ITEMS.add(item);
    }

    private static void register(String name, Block block) {
        register(name, block, true);
    }

    private static void register(String name, Block block, boolean includeItem) {
        Registry.register(Registries.BLOCK, chocId(name), block);
        BLOCKS.add(block);
        if (includeItem) {
            register(name, new BlockItem(block, new Item.Settings()));
        }
    }
}
