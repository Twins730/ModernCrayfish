package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CeilingLightBlock extends Block implements IWaterLoggable, Switchable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CeilingLightBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(LIT, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE));
    }

    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos pos) {
       return Block.hasEnoughSolidSide(worldReader, pos.offset(Direction.UP), Direction.DOWN);
    }



    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(5, 7, 5, 11, 16, 11);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(LIT, Boolean.FALSE).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (world.isRemote()) {
            return ActionResultType.SUCCESS;
        } else {
            world.notifyNeighborsOfStateChange(pos, this);
            BlockState blockstate = this.pull(blockState, world, pos);
            float f = blockstate.get(LIT) ? 0.6F : 0.5F;
            return ActionResultType.CONSUME;
        }
    }

    public BlockState pull(BlockState state, World world, BlockPos pos) {
        state = state.func_235896_a_(LIT);
        world.setBlockState(pos, state, 3);
        this.updateNeighbours(world, pos);
        return state;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(blockState);
    }

    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState state, IWorld world, BlockPos pos, BlockPos pos1) {
        if (blockState.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return !blockState.isValidPosition(world, pos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, state, world, pos, pos1);
    }

    private void updateNeighbours(World world, BlockPos pos) {
        world.notifyNeighborsOfStateChange(pos, this);
    }
}
