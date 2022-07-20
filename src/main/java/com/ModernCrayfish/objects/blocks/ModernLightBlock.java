package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

public class ModernLightBlock extends HorizontalFaceBlock implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public ModernLightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }


    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED,FACING,LIT);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }
}
