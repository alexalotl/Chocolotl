package chocolotl.mixin;

import chocolotl.ChocolotlRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin (PistonBlock.class)
public class PistonBlockMixin {
    @Inject(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V", ordinal = 0))
    private void crushCocoaLiquor(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir) {
        Direction dir = state.get(Properties.FACING);

        BlockPos pushBlockPos = pos.offset(dir);
        BlockState pushBlockState = world.getBlockState(pushBlockPos);

        BlockPos intoBlockPos = pushBlockPos.offset(dir);
        BlockState intoBlockState = world.getBlockState(intoBlockPos);

        if (pushBlockState.isOf(Blocks.MOVING_PISTON) && intoBlockState.isSolidBlock(world, intoBlockPos)) {
            List<ItemEntity> cocoaLiquorStacks = world.getEntitiesByType(EntityType.ITEM, new Box(pushBlockPos), itemEntity -> itemEntity.getStack().getItem() == ChocolotlRegistry.COCOA_LIQUOR);
            for (ItemEntity cocoaLiquorStack : cocoaLiquorStacks) {
                int stackCount = cocoaLiquorStack.getStack().getCount();
                Vec3d stackPos = cocoaLiquorStack.getPos();
                cocoaLiquorStack.discard();
                ItemEntity cocoaPowderStack = new ItemEntity(world, stackPos.x, stackPos.y, stackPos.z, new ItemStack(ChocolotlRegistry.COCOA_POWDER, stackCount));
                ItemEntity cocoaButterStack = new ItemEntity(world, stackPos.x, stackPos.y, stackPos.z, new ItemStack(ChocolotlRegistry.COCOA_BUTTER, stackCount));
                world.spawnEntity(cocoaPowderStack);
                world.spawnEntity(cocoaButterStack);
            }
        }

    }
}
