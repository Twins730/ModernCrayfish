package com.ModernCrayfish.objects.blocks;

import com.ModernCrayfish.objects.tileEntity.CeilingFanTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CeilingFanBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public CeilingFanBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
    }

    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos pos) {
        return Block.canSupportCenter(worldReader, pos.relative(Direction.UP), Direction.DOWN);
    }

    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            world.updateNeighborsAt(pos, this);
            BlockState blockstate = this.pull(blockState, world, pos);
            float f = blockstate.getValue(POWERED) ? 0.6F : 0.5F;
            return ActionResultType.CONSUME;
        }
    }

    public BlockState pull(BlockState state, World world, BlockPos pos) {
        state = state.cycle(POWERED);
        world.setBlock(pos, state, 3);
        this.updateNeighbours(world, pos);
        return state;
    }

    private void updateNeighbours(World world, BlockPos pos) {
        world.updateNeighborsAt(pos, this);
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState state, IWorld world, BlockPos pos, BlockPos pos1) {
        if (blockState.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return !blockState.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, state, world, pos, pos1);
    }


    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED).add(WATERLOGGED);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Nullable @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CeilingFanTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return Block.box(5, 7, 5, 11, 16, 11);
    }



    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(POWERED, Boolean.FALSE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
