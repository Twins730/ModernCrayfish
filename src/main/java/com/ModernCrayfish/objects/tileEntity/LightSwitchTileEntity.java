package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.blocks.CeilingFanBlock;
import com.ModernCrayfish.objects.blocks.LightBlock;
import com.ModernCrayfish.objects.blocks.LightSwitchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LightSwitchTileEntity extends TileEntity {

    public List<BlockPos> targets = new ArrayList<>();

    public LightSwitchTileEntity() {
        super(TileInit.LIGHT_SWITCH_TILE.get());
    }

    public void use(World world){
        targets.forEach(target->{
            ModernCrayfish.LOGGER.info("pulled @ "+target);
            if(world.getBlockState(target).getBlock() instanceof LightBlock){
                world.getBlockState(target).func_235896_a_(LightBlock.LIT);
                world.neighborChanged(target,getBlockState().getBlock(),pos);
            }

            if(world.getBlockState(target).getBlock() instanceof CeilingFanBlock){
                world   .getBlockState(target).func_235896_a_(CeilingFanBlock.POWERED);

                world.neighborChanged(target,getBlockState().getBlock(),pos);
            }
        });
    }


    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        List<Integer> xPoses = new ArrayList<>();
        List<Integer> yPoses = new ArrayList<>();
        List<Integer> zPoses = new ArrayList<>();
        this.targets.forEach((target)->{
            xPoses.add(target.getX());
            yPoses.add(target.getY());
            zPoses.add(target.getZ());
        });
        nbt.putIntArray("xPoses", xPoses);
        nbt.putIntArray("yPoses", yPoses);
        nbt.putIntArray("zPoses", zPoses);
        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        int[] xPoses = nbt.getIntArray("xPoses");
        int[] yPoses = nbt.getIntArray("yPoses");
        int[] zPoses = nbt.getIntArray("zPoses");
        for(int i = 0;i <= xPoses.length - 1;i++){
            targets.add(new BlockPos(xPoses[i],yPoses[i],zPoses[i]));
        }
        super.read(state, nbt);
    }
}
