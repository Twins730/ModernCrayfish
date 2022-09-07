package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.TileInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LightSwitchTileEntity extends TileEntity implements ITickableTileEntity {

    public List<BlockPos> targets = new ArrayList<>();

    public LightSwitchTileEntity() {
        super(TileInit.LIGHT_SWITCH_TILE.get());
    }

    @Override
    public void tick() {

    }


    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        List<Integer> xPoses = new ArrayList<>();
        List<Integer> yPoses = new ArrayList<>();
        List<Integer> zPoses = new ArrayList<>();
        this.targets.forEach((target)->{
            xPoses.add(target.getX());
            yPoses.add(target.getX());
            zPoses.add(target.getX());
        });
        nbt.putIntArray("xPoses", xPoses);
        nbt.putIntArray("yPoses", yPoses);
        nbt.putIntArray("zPoses", zPoses);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        int[] xPoses = nbt.getIntArray("xPoses");
        int[] yPoses = nbt.getIntArray("yPoses");
        int[] zPoses = nbt.getIntArray("zPoses");
        for(int i = 0;i <= xPoses.length;i++){
            targets.add(new BlockPos(xPoses[i],yPoses[i],zPoses[i]));
        }
        super.load(state, nbt);
    }
}
