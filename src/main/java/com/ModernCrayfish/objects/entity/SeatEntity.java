package com.ModernCrayfish.objects.entity;

import com.ModernCrayfish.init.EntityInit;
import com.ModernCrayfish.init.KeyBindingInit;
import com.ModernCrayfish.init.SoundsInit;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class SeatEntity extends Entity {

    private BlockPos source;
    public boolean toilet = false;

    public SeatEntity(World world) {
        super(EntityInit.SEAT_ENTITY.get(), world);
        this.noClip = true;
    }

    private SeatEntity(World world, BlockPos source, Vector3d offset,Direction direction) {
        this(world);
        this.source = source;
        this.setPosition(source.getX() + 0.5 + offset.x, source.getY() + offset.y, source.getZ() + 0.5 + offset.z);
        this.setRotation(direction.getOpposite().getHorizontalAngle(), 0F);
    }

    private SeatEntity(World world, BlockPos source, Vector3d offset) {
        this(world);
        this.source = source;
        this.setPosition(source.getX() + 0.5 + offset.x, source.getY() + offset.y, source.getZ() + 0.5 + offset.z);
    }


    @Override
    protected void registerData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (this.getPassengers().isEmpty()) {
                this.remove();
                this.world.updateComparatorOutputLevel(getOnPosition(), world.getBlockState(getOnPosition()).getBlock());
            }
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    protected boolean canBeRidden(Entity p_184228_1_) {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static ActionResultType create(World world, BlockPos pos, double yOffset, PlayerEntity player, boolean toilet) {
        if (!world.isRemote) {
            List<Entity> seats = world.getEntitiesWithinAABB(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                SeatEntity seat = new SeatEntity(world, pos, new Vector3d(0, yOffset, 0));
                seat.toilet = toilet;
                world.addEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return ActionResultType.SUCCESS;
    }


    public static ActionResultType create(World world, BlockPos pos, double yOffset,double offsetAmount, PlayerEntity player, Direction direction, boolean toilet) {
        if (!world.isRemote) {
            List<Entity> seats = world.getEntitiesWithinAABB(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                Vector3d offset = new Vector3d(0, yOffset, 0);
                if(toilet){
                    offset.add(direction.getXOffset()/offsetAmount,0,direction.getZOffset()/offsetAmount);
                }
                SeatEntity seat = new SeatEntity(world, pos, offset, direction);
                seat.toilet = toilet;
                world.addEntity(seat);
                player.startRiding(seat, false);
                if(toilet) {
                    player.sendStatusMessage(new TranslationTextComponent("Press "  + KeyBindingInit.KEY_FART.getKey().func_237520_d_().getString().toUpperCase() + " for Farts"), false);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

}

