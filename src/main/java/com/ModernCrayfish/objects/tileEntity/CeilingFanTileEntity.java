package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.blocks.CeilingFanBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class CeilingFanTileEntity extends TileEntity implements ITickableTileEntity {

    private boolean powered = false;
    private float maxSpeed = 30.0F;
    private float acceleration = 0.25F;
    public float prevFanRotation;
    public float fanRotation;
    private float currentSpeed;

    public CeilingFanTileEntity() {
        super(TileInit.CEILING_FAN_TILE.get());
    }

    @Override
    public void tick() {
        BlockState state = this.getBlockState();
        this.powered = state.getValue(CeilingFanBlock.POWERED);
        this.prevFanRotation = this.fanRotation;
        if(powered) {
            currentSpeed += acceleration;
            if(currentSpeed > maxSpeed) {
                currentSpeed = maxSpeed;
            }
        } else {
            currentSpeed *= 0.95F;
        }
        fanRotation += currentSpeed;
    }

    @Override
    public double getViewDistance() {
        return 16384;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public boolean isOn(){
        return this.powered;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {
        fanRotation = nbt.getFloat("fan_rot");
        currentSpeed = nbt.getFloat("speed");
        super.load(p_230337_1_, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putFloat("fan_rot",fanRotation);
        nbt.putFloat("speed",currentSpeed);
        return super.save(nbt);
    }
}
