package co.johnrowley.gtceu_extra.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * BackportUtils contains utilities that exist in newer versions of GTCEu Modern, but not in the oldest version that
 * this mod supports.
 */
public class BackportUtils {
    // Same as getAdjacentFluidHandler, but for ItemHandler
    public static LazyOptional<IItemHandler> getAdjacentItemHandler(Level level, BlockPos pos, Direction facing) {
        return getItemHandler(level, pos.relative(facing), facing.getOpposite());
    }

    /**
     * Get the ItemHandler Capability from the given block
     *
     * @param level Level of block
     * @param pos   BlockPos of block
     * @param side  Side of block
     * @return LazyOpt of ItemHandler of given block
     */
    public static LazyOptional<IItemHandler> getItemHandler(Level level, BlockPos pos, @Nullable Direction side) {
        BlockState state = level.getBlockState(pos);
        if (state.hasBlockEntity()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                return blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, side);
            }
        }

        return LazyOptional.empty();
    }

    // Same as above, but returns the presence
    public static boolean hasAdjacentItemHandler(Level level, BlockPos pos, Direction facing) {
        return getAdjacentItemHandler(level, pos, facing).isPresent();
    }
}
