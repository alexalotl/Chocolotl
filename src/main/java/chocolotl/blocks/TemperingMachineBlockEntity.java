package chocolotl.blocks;

import chocolotl.ChocolotlRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TemperingMachineBlockEntity extends BlockEntity {
    public TemperingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ChocolotlRegistry.TEMPERING_MACHINE_BE, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state, TemperingMachineBlockEntity blockEntity) {

    }
}
