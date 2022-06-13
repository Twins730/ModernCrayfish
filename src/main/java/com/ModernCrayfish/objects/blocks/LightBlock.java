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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LightBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public LightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return Block.box(5, 7, 5, 11, 16, 11);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        FluidState fluidstate = p_196258_1_.getLevel().getFluidState(p_196258_1_.getClickedPos());
        return this.defaultBlockState().setValue(LIT, Boolean.FALSE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            p_225533_2_.updateNeighborsAt(p_225533_3_, this);
            BlockState blockstate = this.pull(p_225533_1_, p_225533_2_, p_225533_3_);
            float f = blockstate.getValue(LIT) ? 0.6F : 0.5F;
            return ActionResultType.CONSUME;
        }
    }

    public BlockState pull(BlockState p_226939_1_, World p_226939_2_, BlockPos p_226939_3_) {
        p_226939_1_ = p_226939_1_.cycle(LIT);
        p_226939_2_.setBlock(p_226939_3_, p_226939_1_, 3);
        this.updateNeighbours(p_226939_1_, p_226939_2_, p_226939_3_);
        return p_226939_1_;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    private void updateNeighbours(BlockState p_196378_1_, World p_196378_2_, BlockPos p_196378_3_) {
        p_196378_2_.updateNeighborsAt(p_196378_3_, this);
    }
}
