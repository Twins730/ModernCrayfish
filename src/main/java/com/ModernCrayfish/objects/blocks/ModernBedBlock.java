package com.ModernCrayfish.objects.blocks;


import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class ModernBedBlock extends BedBlock {

    public final BooleanProperty LEFT = BooleanProperty.create("left");
    public final BooleanProperty RIGHT = BooleanProperty.create("right");

    public ModernBedBlock(DyeColor color, Properties properties) {
        super(color, properties);
        this.setDefaultState(this.getDefaultState().with(LEFT, Boolean.FALSE).with(RIGHT, Boolean.FALSE));
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getNearestLookingDirection();
        BlockPos blockpos = context.getPos();
        BlockPos blockpos1 = blockpos.offset(direction);
        boolean left = (context.getWorld().getBlockState(blockpos.offset(direction.rotateYCCW())).getBlock() == this);
        boolean right = (context.getWorld().getBlockState(blockpos.offset(direction.rotateY())).getBlock() == this);
        return context.getWorld().getBlockState(blockpos1).isReplaceable(context) ? this.getDefaultState().with(HORIZONTAL_FACING, direction).with(LEFT, left).with(RIGHT, right) : null;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder) {
        super.fillStateContainer(blockStateBuilder);
        blockStateBuilder.add(LEFT, RIGHT);
    }


}
