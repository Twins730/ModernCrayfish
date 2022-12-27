package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ModernLightBlock extends DirectionalBlock implements IWaterLoggable, Switchable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public static final VoxelShape UP_SHAPE = Block.makeCuboidShape(3, 0, 3, 13, 2, 13);
    public static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(3, 14, 3, 13, 16, 13);
    public static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(3, 3, 14, 13, 13, 16);
    public static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(3, 3, 0, 13, 13, 2);
    public static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0, 3, 3, 2, 13, 13);
    public static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(14, 3, 3, 16, 13, 13);

    public  ModernLightBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(LIT, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)){
            case UP:    return UP_SHAPE;
            case DOWN:  return DOWN_SHAPE;
            case NORTH: return NORTH_SHAPE;
            case SOUTH: return SOUTH_SHAPE;
            case EAST:  return EAST_SHAPE;
            case WEST:  return WEST_SHAPE;
        }
        return null;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        Direction direction = context.getFace();
        return this.getDefaultState().with(FACING, direction).with(LIT, Boolean.FALSE).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(blockState);
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING,LIT,WATERLOGGED);
    }

    @Override
    public PushReaction getPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }
}
