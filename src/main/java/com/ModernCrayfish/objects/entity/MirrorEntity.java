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
        this.facing = facing.getHorizontalIndex();
        this.noClip = true;
        this.setPostionConsideringRotation(x, y, z, facing.getHorizontalIndex());
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
        this.setPosition(x + 0.5D, y + 0.5, z + 0.5D);
    }


    @Override
    protected void registerData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (rendering) {
            double dy = this.getPosZ() - mc.player.getPosZ();
            double dx = this.getPosX() - mc.player.getPosX();
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

            this.rotationYaw = (float) (-90F + facing * 90F - angleYaw);


            double distance = getDistance(mc.player);
            double height = (mc.player.getEyeHeight() + mc.player.getPosY()) - this.getPosY();
            double anglePitch = Math.atan2(height, distance) * (180D / Math.PI);
            if (anglePitch > 45F) {
                anglePitch = 45F;
            }
            if (anglePitch < -45F) {
                anglePitch = -45F;
            }
            this.rotationYaw = (float) anglePitch;
        }

        if (!(world.getBlockState(getPosition().down()).getBlock() instanceof MirrorBlock)) {
            //MirrorTileEntityRenderer.removeRegisteredMirror(this);
            this.remove();
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
