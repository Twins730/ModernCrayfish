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
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LightBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public LightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos pos) {
       return Block.canSupportCenter(worldReader, pos.relative(Direction.UP), Direction.DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return Block.box(5, 7, 5, 11, 16, 11);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            world.updateNeighborsAt(pos, this);
            BlockState blockstate = this.pull(blockState, world, pos);
            float f = blockstate.getValue(LIT) ? 0.6F : 0.5F;
            return ActionResultType.CONSUME;
        }
    }

    public BlockState pull(BlockState state, World world, BlockPos pos) {
        state = state.cycle(LIT);
        world.setBlock(pos, state, 3);
        this.updateNeighbours(world, pos);
        return state;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    private void updateNeighbours(World world, BlockPos pos) {
        world.updateNeighborsAt(pos, this);
    }
}
