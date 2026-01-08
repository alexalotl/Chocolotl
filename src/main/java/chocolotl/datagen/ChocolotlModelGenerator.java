package chocolotl.datagen;

import chocolotl.ChocolotlRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static chocolotl.ChocolotlRegistry.*;
import static net.minecraft.data.client.TextureMap.getSubId;

public class ChocolotlModelGenerator extends FabricModelProvider {
    private final List<Item> noAutoGen = new ArrayList<>();

    public ChocolotlModelGenerator(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockGen) {
        registerCake(blockGen, ChocolotlRegistry.CHOCOLATE_CAKE, 7);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemGen) {
        noAutoGen.add(CHOCOLATE_CAKE.asItem());

        for (Item item : ITEMS) {
            if (!(noAutoGen.contains(item) || item instanceof BlockItem))
                itemGen.register(item, Models.GENERATED);
        }

        for(Block block : BLOCKS)
            if(!noAutoGen.contains(block.asItem()) && block.asItem() != Items.AIR)
                itemGen.writer.accept(ModelIds.getItemModelId(block.asItem()), new SimpleModelSupplier(ModelIds.getBlockModelId(block)));
    }

    private void registerCake(BlockStateModelGenerator blockGen, Block cake, Integer max_bites) {
        blockGen.registerItemModel(cake.asItem());
        final Identifier slice1Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice1")), Optional.of("_slice1"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);
        final Identifier slice2Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice2")), Optional.of("_slice2"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);
        final Identifier slice3Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice3")), Optional.of("_slice3"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);
        final Identifier slice4Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice4")), Optional.of("_slice4"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);
        final Identifier slice5Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice5")), Optional.of("_slice5"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);
        final Identifier slice6Id = TexturedModel.makeFactory(ChocolotlModelGenerator::cake, new Model(Optional.of(new Identifier("minecraft", "block/" + "cake_slice6")), Optional.of("_slice6"), TextureKey.TOP, TextureKey.INSIDE, TextureKey.BOTTOM, TextureKey.SIDE)).upload(ChocolotlRegistry.CHOCOLATE_CAKE, blockGen.modelCollector);

        blockGen.blockStateCollector
                .accept(
                        VariantsBlockStateSupplier.create(cake)
                                .coordinate(
                                        BlockStateVariantMap.create(Properties.BITES)
                                                .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(cake)))
                                                .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice1")))
                                                .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice2")))
                                                .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice3")))
                                                .register(4, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice4")))
                                                .register(5, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice5")))
                                                .register(6, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(cake, "_slice6")))
                                )
                );
    }

    public static TextureMap cake(Block cake) {
        return new TextureMap()
                .put(TextureKey.PARTICLE, getSubId(cake, "_side"))
                .put(TextureKey.SIDE, getSubId(cake, "_side"))
                .put(TextureKey.TOP, getSubId(cake, "_top"))
                .put(TextureKey.BOTTOM, getSubId(cake, "_bottom"))
                .put(TextureKey.INSIDE, getSubId(cake, "_inner"));
    }
}