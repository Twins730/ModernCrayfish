package com.ModernCrayfish.objects.entity;

import com.ModernCrayfish.client.renderer.tile.MirrorTileEntityRenderer;
import com.ModernCrayfish.init.EntityInit;
import com.ModernCrayfish.objects.blocks.MirrorBlock;
import com.ModernCrayfish.objects.tileEntity.MirrorTileEntity;
import com.ModernCrayfish.util.MirrorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(Dist.CLIENT)
public class MirrorEntity extends Entity {
    private Minecraft mc = Minecraft.getInstance();

    private int facing;
    public boolean rendering;

    public MirrorEntity(World worldIn) {
        super(EntityInit.MIRROR_ENTITY.get(), worldIn);
    }

    public MirrorEntity(World worldIn, double x, double y, double z, Direction facing) {
        super(EntityInit.MIRROR_ENTITY.get(), worldIn);
        this.facing = facing.get2DDataValue();
        this.noPhysics = true;
        this.setPostionConsideringRotation(x, y, z, facing.get2DDataValue());
    }

    public void setPostionConsideringRotation(double x, double y, double z, int rotation) {
        double offset = -0.43;
        switch (rotation) {
            case 2:
                z += offset;
                break;
            case 0:
                z -= offset;
                break;
            case 3:
                x -= offset;
                break;
            case 1:
                x += offset;
                break;
        }
        this.setPos(x + 0.5D, y + 0.5, z + 0.5D);
    }


    @Override
    public void tick() {
        super.tick();
        if (rendering) {
            double dy = this.getZ() - mc.player.getZ();
            double dx = this.getX() - mc.player.getX();
            double angleYaw = Math.atan2(dy, dx) * (180D / Math.PI);

            if (facing == 1) {
                angleYaw -= 90D;
                if (angleYaw <= -180D) {
                    angleYaw = 360D + angleYaw;
                }

            }
            if (facing > 1) {
                angleYaw += 360D - (facing * 90D);
            }

            if (angleYaw >= 135D) angleYaw = 135D;
            if (angleYaw <= 45D) angleYaw = 45D;

            this.yRot = (float) (-90F + facing * 90F - angleYaw);


            double distance = distanceTo(mc.player);
            double height = (mc.player.getEyeHeight() + mc.player.getY()) - this.getY();
            double anglePitch = Math.atan2(height, distance) * (180D / Math.PI);
            if (anglePitch > 45F) {
                anglePitch = 45F;
            }
            if (anglePitch < -45F) {
                anglePitch = -45F;
            }
            this.yRot = (float) anglePitch;
        }

        if (!(level.getBlockState(blockPosition().below()).getBlock() instanceof MirrorBlock)) {
            MirrorTileEntityRenderer.removeRegisteredMirror(this);
            this.kill();
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
