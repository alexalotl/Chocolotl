package chocolotl;

import chocolotl.items.CocoaPowderItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Chocolotl implements ModInitializer {

    public static final Item COCOA_POWDER = new CocoaPowderItem(new FabricItemSettings());

    @Override
    public void onInitialize() {

        Registry.register(Registries.ITEM, Identifier.of("chocolotl", "cocoa_powder"), COCOA_POWDER);
    }
}
