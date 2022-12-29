package com.ModernCrayfish.objects.blocks;

import com.ModernCrayfish.init.SoundsInit;
import com.ModernCrayfish.objects.tileEntity.MicrowaveTileEntity;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class MicrowaveBlock extends HorizontalBlock implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE_EW = Block.makeCuboidShape(2, 0, 0, 14, 9, 17);
    private static final VoxelShape SHAPE_NS = Block.makeCuboidShape(0, 0, 2, 16, 9, 14);
    private int tick;

    public MicrowaveBlock(Properties properties){
        super(properties);
        this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED,false));
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MicrowaveTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch (state.get(HORIZONTAL_FACING)){
            case SOUTH:
            case NORTH:
                return SHAPE_NS;
            case WEST:
            case EAST:
                return SHAPE_EW;
        }
        return super.getShape(state, p_220053_2_, p_220053_3_, p_220053_4_);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING,WATERLOGGED);
        super.fillStateContainer(builder);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        MicrowaveTileEntity tileEntity = (MicrowaveTileEntity) worldIn.getTileEntity(pos);
        assert tileEntity != null;
        if(tileEntity.finishedCooking) {
            worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundsInit.MICROWAVE_FINISH.get(), SoundCategory.BLOCKS, 0.75F, 1.0F, false);
            tileEntity.finishedCooking = false;
            tileEntity.tick = 0;
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult p_225533_6_) {
        if (!world.isRemote()) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof MicrowaveTileEntity) {
                player.openContainer((INamedContainerProvider) tileentity);
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
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
                blockstate = blockstate.with(HORIZONTAL_FACING, direction);
                if (blockstate.isValidPosition(iworldreader, blockpos)) {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }
        return null;
    }
}
