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
        this.registerDefaultState(this.stateDefinition.any().setValue(LEFT, Boolean.FALSE).setValue(RIGHT, Boolean.FALSE));
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);
        boolean left = (context.getLevel().getBlockState(blockpos.relative(direction.getCounterClockWise())).getBlock() == this);
        boolean right = (context.getLevel().getBlockState(blockpos.relative(direction.getClockWise())).getBlock() == this);
        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, direction).setValue(LEFT, left).setValue(RIGHT, right) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockStateBuilder) {
        super.createBlockStateDefinition(blockStateBuilder);
        blockStateBuilder.add(LEFT, RIGHT);
    }
}
