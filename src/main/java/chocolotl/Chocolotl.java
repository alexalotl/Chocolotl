package chocolotl;

import chocolotl.blocks.ChocolateCakeBlock;
import chocolotl.items.CocoaPowderItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Chocolotl implements ModInitializer {

    public static final Item COCOA_POWDER = new CocoaPowderItem(new FabricItemSettings());
    public static final Block CHOCOLATE_CAKE = new ChocolateCakeBlock(Block.Settings.create().strength(0.5f));

    @Override
    public void onInitialize() {

        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "cocoa_powder"), COCOA_POWDER);

        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "chocolate_cake"), new BlockItem(CHOCOLATE_CAKE, new Item.Settings().maxCount(1)));
        Registry.register(Registries.BLOCK, Identifier.of("chocolotl", "chocolate_cake"), CHOCOLATE_CAKE);
    }
}
