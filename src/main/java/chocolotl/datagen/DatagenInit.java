package chocolotl.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DatagenInit implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {

        FabricDataGenerator.Pack pack = gen.createPack();

        pack.addProvider(ChocolotlModelGenerator::new);
    }
}
