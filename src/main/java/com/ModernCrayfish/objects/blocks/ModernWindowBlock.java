package com.ModernCrayfish.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ModernWindowBlock extends FourWayBlock {

    public static BooleanProperty UP = BooleanProperty.create("pane_up");
    public static BooleanProperty DOWN = BooleanProperty.create("pane_down");
    public static BooleanProperty P_NORTH = BooleanProperty.create("pane_north");
    public static BooleanProperty P_SOUTH = BooleanProperty.create("pane_south");
    public static BooleanProperty P_EAST = BooleanProperty.create("pane_east");
    public static BooleanProperty P_WEST = BooleanProperty.create("pane_west");

    public ModernWindowBlock(Properties properties) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, properties);
        this.setDefaultState(this.getDefaultState()
                .with(NORTH, Boolean.FALSE).with(EAST, Boolean.FALSE).with(SOUTH, Boolean.FALSE).with(WEST, Boolean.FALSE)
                .with(P_NORTH, Boolean.FALSE).with(P_EAST, Boolean.FALSE).with(P_SOUTH, Boolean.FALSE).with(P_WEST, Boolean.FALSE)
                .with(WATERLOGGED, Boolean.FALSE).with(UP, Boolean.FALSE).with(DOWN, Boolean.FALSE));
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        IBlockReader iblockreader = p_196258_1_.getWorld();
        BlockPos blockpos = p_196258_1_.getPos();
        FluidState fluidstate = p_196258_1_.getWorld().getFluidState(p_196258_1_.getPos());
        BlockPos blockposN = blockpos.north();
        BlockPos blockposS = blockpos.south();
        BlockPos blockposW = blockpos.west();
        BlockPos blockposE = blockpos.east();
        BlockPos blockposU = blockpos.up();
        BlockPos blockposB = blockpos.down();
        BlockState blockstateN = iblockreader.getBlockState(blockposN);
        BlockState blockstateS = iblockreader.getBlockState(blockposS);
        BlockState blockstateW = iblockreader.getBlockState(blockposW);
        BlockState blockstateE = iblockreader.getBlockState(blockposE);
        BlockState blockstateU = iblockreader.getBlockState(blockposU);
        BlockState blockstateB = iblockreader.getBlockState(blockposB);
        return this.getDefaultState()
                .with(NORTH, this.attachesTo(blockstateN, blockstateN.isSolidSide(iblockreader, blockposN, Direction.SOUTH)))
                .with(SOUTH, this.attachesTo(blockstateS, blockstateS.isSolidSide(iblockreader, blockposS, Direction.NORTH)))
                .with(WEST, this.attachesTo(blockstateW, blockstateW.isSolidSide(iblockreader, blockposW, Direction.EAST)))
                .with(EAST, this.attachesTo(blockstateE, blockstateE.isSolidSide(iblockreader, blockposE, Direction.WEST)))
                .with(P_NORTH, blockstateN.getBlock() == this)
                .with(P_SOUTH, blockstateS.getBlock() == this)
                .with(P_WEST, blockstateW.getBlock() == this)
                .with(P_EAST, blockstateE.getBlock() == this)
                .with(UP, (blockstateU.getBlock() == this))
                .with(DOWN, (blockstateB.getBlock() == this))
                .with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState neighborState, IWorld level, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            level.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(level));
        }
        return direction.getAxis().isHorizontal() ? state.with(FACING_TO_PROPERTY_MAP.get(direction), this.attachesTo(neighborState, neighborState.isSolidSide(level, neighborPos, direction.getOpposite()))) : super.updatePostPlacement(state, direction, neighborState, level, pos, neighborPos);
    }

    public final boolean attachesTo(BlockState state, boolean p_220112_2_) {
        Block block = state.getBlock();
        return !cannotAttach(block) && p_220112_2_ || block == this || block.isIn(BlockTags.WALLS);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(NORTH, EAST, WEST, SOUTH, P_NORTH, P_SOUTH, P_EAST, P_WEST, UP, DOWN, WATERLOGGED);
    }

}
