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
        this.noPhysics = true;
    }

    private SeatEntity(World world, BlockPos source, Vector3d offset,Direction direction) {
        this(world);
        this.source = source;
        this.setPos(source.getX() + 0.5 + offset.x(), source.getY() + offset.y(), source.getZ() + 0.5 + offset.z());
        this.setRot(direction.getOpposite().toYRot(), 0F);
    }

    private SeatEntity(World world, BlockPos source, Vector3d offset) {
        this(world);
        this.source = source;
        this.setPos(source.getX() + 0.5 + offset.x(), source.getY() + offset.y(), source.getZ() + 0.5 + offset.z());
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (this.getPassengers().isEmpty()) {
                this.remove();
                this.level.updateNeighbourForOutputSignal(getOnPos(), level.getBlockState(getOnPos()).getBlock());
            }
        }



    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {}

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {}

    @Override
    public double getMyRidingOffset() {
        return 0.0;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static ActionResultType create(World world, BlockPos pos, double yOffset, PlayerEntity player, boolean toilet) {
        if (!world.isClientSide) {
            List<Entity> seats = world.getEntitiesOfClass(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                SeatEntity seat = new SeatEntity(world, pos, new Vector3d(0, yOffset, 0));
                seat.toilet = toilet;
                world.addFreshEntity(seat);
                player.startRiding(seat, false);
            }
        }
        return ActionResultType.SUCCESS;
    }


    public static ActionResultType create(World world, BlockPos pos, double yOffset,double offsetAmount, PlayerEntity player, Direction direction, boolean toilet) {
        if (!world.isClientSide) {
            List<Entity> seats = world.getEntitiesOfClass(SeatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0));
            if (seats.isEmpty()) {
                Vector3d offset = new Vector3d(0, yOffset, 0);
                if(toilet){
                    offset.add(direction.getStepX()/offsetAmount,0,direction.getStepZ()/offsetAmount);
                }
                SeatEntity seat = new SeatEntity(world, pos, offset, direction);
                seat.toilet = toilet;
                world.addFreshEntity(seat);
                player.startRiding(seat, false);
                if(toilet) {
                    player.displayClientMessage(new TranslationTextComponent("Press "  + KeyBindingInit.KEY_FART.getKey().getDisplayName().getString().toUpperCase() + " for Farts"), false);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void positionRider(Entity entity) {
        super.positionRider(entity);
        this.clampYaw(entity);
    }

    @Override
    public void onPassengerTurned(Entity entity) {
        this.clampYaw(entity);
    }

    private void clampYaw(Entity passenger) {
        passenger.setYBodyRot(this.getYHeadRot());
        float wrappedYaw = MathHelper.wrapDegrees(passenger.getYHeadRot() - this.getYHeadRot());
        float clampedYaw = MathHelper.clamp(wrappedYaw, -120.0F, 120.0F);
        passenger.yRotO += clampedYaw - wrappedYaw;
        passenger.setYBodyRot(passenger.getYHeadRot() + clampedYaw - wrappedYaw);
        passenger.setYHeadRot(passenger.getYHeadRot());
    }

}

