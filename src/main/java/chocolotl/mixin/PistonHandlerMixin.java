package chocolotl.mixin;

import chocolotl.ChocolotlRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin (PistonHandler.class)
public class PistonHandlerMixin {
    @Shadow
    @Final
    private World world;

    @Inject(method = "tryMove", at = @At(value = "HEAD"))
    private void crushCocoaLiquor(BlockPos pushBlockPos, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        BlockState pushBlockState = world.getBlockState(pushBlockPos);
        BlockPos intoBlockPos = pushBlockPos.offset(dir.getOpposite(), 1);
        BlockState intoBlockState = world.getBlockState(intoBlockPos);

        if (pushBlockState.isAir() && intoBlockState.isSolidBlock(world, intoBlockPos)) {
            List<ItemEntity> cocoaLiquorStacks = world.getEntitiesByType(EntityType.ITEM, new Box(pushBlockPos), itemEntity -> itemEntity.getStack().getItem() == ChocolotlRegistry.COCOA_LIQUOR);
            for (ItemEntity cocoaLiquorStack : cocoaLiquorStacks) {
                int stackCount = cocoaLiquorStack.getStack().getCount();
                cocoaLiquorStack.getPos();
                cocoaLiquorStack.discard();

            }
        }

    }
}
