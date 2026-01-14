package chocolotl.blocks;

import chocolotl.ChocolotlRegistry;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TemperingMachineBlock extends BlockWithEntity {
    public TemperingMachineBlock(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
        if(world.isClient){
            return ActionResult.SUCCESS;
        }else{
            this.openScreen(world, pos, player);
            return ActionResult.CONSUME;
        }
    }

    protected void openScreen(World world, BlockPos pos, PlayerEntity player){
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof TemperingMachineBlockEntity){
            player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
        }
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state){
        return new TemperingMachineBlockEntity(pos, state);
    }

    public BlockRenderType getRenderType(BlockState state){ return BlockRenderType.MODEL; }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World _world, BlockState _state, BlockEntityType<T> type) {
        // Make sure to check world.isClient if you only want to tick only on serverside.
        return checkType(type, ChocolotlRegistry.TEMPERING_MACHINE_BE,
                (world, pos, state, entity) ->
                        entity.tick(world, pos, state, entity));
    }
}
