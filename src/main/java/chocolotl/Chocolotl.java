package chocolotl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class Chocolotl implements ModInitializer {

    public static final String MOD_ID = "chocolotl";
    private static final Identifier BIRCH_LEAVES_LOOT_TABLE_ID = Blocks.BIRCH_LEAVES.getLootTableId();

    @Override
    public void onInitialize() {

        ChocolotlRegistry.setup();

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && BIRCH_LEAVES_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ChocolotlRegistry.HAZELNUT))
                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))
                        .conditionally(SurvivesExplosionLootCondition.builder());
                tableBuilder.pool(poolBuilder);
            }
        });
    }

    public static Identifier chocId(String s) {
        return new Identifier(MOD_ID, s);
    }
}
