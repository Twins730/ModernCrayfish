package com.ModernCrayfish.objects.blocks;

import com.ModernCrayfish.objects.tileEntity.PlateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PlateBlock extends Block {

    public PlateBlock(Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return Block.box(3, 0, 3, 13, 2, 13);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PlateTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult) {
        if(world.isClientSide){
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = world.getBlockEntity(pos);
            if(tileentity instanceof PlateTileEntity){
                ((PlateTileEntity) tileentity).use(world,player);
            }
            return ActionResultType.CONSUME;
        }
    }
}
