package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModPaneBlock extends FourWayBlock {

    public ModPaneBlock(AbstractBlock.Properties p_i48373_1_) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, p_i48373_1_);
   }

    public BlockState updatePostPlacement(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (p_196271_1_.get(WATERLOGGED)) {
            p_196271_4_.getPendingFluidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickRate(p_196271_4_));
        }

        return p_196271_2_.getAxis().isHorizontal() ? p_196271_1_.with(FACING_TO_PROPERTY_MAP.get(p_196271_2_), this.attachesTo(p_196271_3_, p_196271_3_.isSolidSide(p_196271_4_, p_196271_6_, p_196271_2_.getOpposite()))) : super.updatePostPlacement(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    /*
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState p_200122_1_, BlockState p_200122_2_, Direction p_200122_3_) {
        if (p_200122_2_.isIn(this)) {
            if (!p_200122_3_.getAxis().isHorizontal()) {
                return true;
            }

            if (p_200122_1_.get(FACING_TO_PROPERTY_MAP.get(p_200122_3_)) && p_200122_2_.get(FACING_TO_PROPERTY_MAP.get(p_200122_3_.getOpposite()))) {
                return true;
            }
        }

        return super.skipRendering(p_200122_1_, p_200122_2_, p_200122_3_);
    }

     */


    public boolean attachesTo(BlockState p_220112_1_, boolean p_220112_2_) {
        Block block = p_220112_1_.getBlock();
        return !cannotAttach(block) && p_220112_2_ || block instanceof PaneBlock || block.isIn(BlockTags.WALLS);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
