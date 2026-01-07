package chocolotl;

import chocolotl.blocks.ChocolateCakeBlock;
import chocolotl.items.BrownSugarItem;
import chocolotl.items.CocoaPowderItem;
import chocolotl.items.MolassesBucketItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Chocolotl implements ModInitializer {

    public static final Item COCOA_POWDER = new CocoaPowderItem(new FabricItemSettings());
    public static final Item BROWN_SUGAR = new BrownSugarItem(new FabricItemSettings());
    public static final Item MOLASSES_BUCKET = new MolassesBucketItem(new FabricItemSettings());
    public static final Item HAZELNUT = new Item(new FabricItemSettings());

    public static final Block CHOCOLATE_CAKE = new ChocolateCakeBlock(Block.Settings.create().strength(0.5f));

    private static final Identifier BIRCH_LEAVES_LOOT_TABLE_ID = Blocks.BIRCH_LEAVES.getLootTableId();

    @Override
    public void onInitialize() {

        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "cocoa_powder"), COCOA_POWDER);
        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "brown_sugar"), BROWN_SUGAR);
        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "molasses_bucket"), MOLASSES_BUCKET);
        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "hazelnut"), HAZELNUT);

        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "chocolate_cake"), new BlockItem(CHOCOLATE_CAKE, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, Identifier.of("chocolotl", "chocolate_cake"), CHOCOLATE_CAKE);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && BIRCH_LEAVES_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Chocolotl.HAZELNUT))
                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
