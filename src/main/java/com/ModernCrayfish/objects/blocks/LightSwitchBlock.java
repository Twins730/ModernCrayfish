package com.ModernCrayfish.objects.blocks;

import com.ModernCrayfish.init.SoundsInit;
import com.ModernCrayfish.objects.tileEntity.LightSwitchTileEntity;
import com.ModernCrayfish.objects.tileEntity.MirrorTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class LightSwitchBlock extends Block implements IWaterLoggable {

    private static final VoxelShape SHAPE_S = Block.makeCuboidShape(4, 3, 0, 12, 13, 2);
    private static final VoxelShape SHAPE_N = Block.makeCuboidShape(4, 3, 14, 12, 13, 16);
    private static final VoxelShape SHAPE_E = Block.makeCuboidShape(0, 3, 4, 2, 13, 12);
    private static final VoxelShape SHAPE_W = Block.makeCuboidShape(14, 3, 4, 16, 13, 12);

    private static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
        public static final BooleanProperty ON = BooleanProperty.create("on");

    public LightSwitchBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(ON, Boolean.FALSE).with(WATERLOGGED, Boolean.FALSE).with(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LightSwitchTileEntity();
    }


    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        switch (blockState.get(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
        }
        return SHAPE_N;
    }

    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if (world.isRemote) {
            world.playSound(player, pos, SoundsInit.LIGHT_SWITCH.get(), SoundCategory.BLOCKS, 0.4f, 0.9f);
            return ActionResultType.SUCCESS;
        } else {
            world.notifyNeighborsOfStateChange(pos, this);
            this.pull(blockState, world, pos);
            ((LightSwitchTileEntity) (Objects.requireNonNull(world.getTileEntity(pos)))).setState(blockState.get(ON));
            return ActionResultType.CONSUME;
        }
    }

    public void pull(BlockState state, World world, BlockPos pos) {
        state = state.func_235896_a_(ON);
        world.setBlockState(pos, state, 3);
        world.notifyNeighborsOfStateChange(pos, this);
    }

    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return !p_196260_2_.getBlockState(p_196260_3_.offset(p_196260_1_.get(FACING))).isSolid();
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld
            p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        return p_196271_2_.getOpposite() == p_196271_1_.get(FACING) && !p_196271_1_.isValidPosition(p_196271_4_, p_196271_5_) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.with(FACING, p_185499_2_.rotate(p_185499_1_.get(FACING)));
    }

    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.toRotation(p_185471_1_.get(FACING)));
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(blockState);
    }

    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ON).add(WATERLOGGED).add(FACING);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        BlockState blockstate = this.getDefaultState();
        FluidState fluidstate = p_196258_1_.getWorld().getFluidState(p_196258_1_.getPos());
        IWorldReader iworldreader = p_196258_1_.getWorld();
        BlockPos blockpos = p_196258_1_.getPos();
        Direction[] adirection = p_196258_1_.getNearestLookingDirections();

        for (Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(FACING, direction1);
                if (blockstate.isValidPosition(iworldreader, blockpos)) {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }

        return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }



}
