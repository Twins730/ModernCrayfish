package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.ItemInit;
import com.ModernCrayfish.init.TileInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class CuttingBoardTileEntity extends TileEntity {

    public Direction itemDirection = Direction.NORTH;
    public NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public CuttingBoardTileEntity() {
        super(TileInit.CUTTING_BOARD.get());
    }

    public void remove(World world){
        if(!world.isClientSide){
            items.forEach((item)->{
                world.addFreshEntity(new ItemEntity(world,worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),item));
            });
        }
    }

    public void use(World world, PlayerEntity player, Direction direction) {
        if (!world.isClientSide) {
            if (!items.get(0).isEmpty()) {
                if (player.getMainHandItem().getItem() == ItemInit.CUTTING_KNIFE.get() && items.get(0).getItem() != ItemInit.BREAD_SLICE.get()) {
                    this.items.set(0, new ItemStack(ItemInit.BREAD_SLICE.get(), 8));
                } else {
                    player.addItem(items.get(0));
                    this.items.set(0, ItemStack.EMPTY);
                }
            } else if (player.getMainHandItem().getItem() == Items.BREAD) {
                this.itemDirection = direction;
                this.items.set(0, new ItemStack(player.getMainHandItem().getItem(), 1));
                player.getMainHandItem().shrink(1);
            }
            world.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        } else {
            if(!items.get(0).isEmpty()){
                player.playSound(SoundEvents.ITEM_PICKUP,1,1);
            }
        }
    }

    private void playSound(SoundEvent p_213965_2_) {
        Vector3i vector3i = worldPosition;
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vector3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vector3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vector3i.getZ() / 2.0D;
        this.level.playSound((PlayerEntity)null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
        this.itemDirection = Direction.fromYRot(nbt.getDouble("Direction"));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, items);
        nbt.putDouble("Direction",itemDirection.toYRot());
        return nbt;
    }


    // Synchronization

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);

        return new SUpdateTileEntityPacket(worldPosition, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert level != null;
        this.load(level.getBlockState(worldPosition),pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state,tag);
        super.handleUpdateTag(state, tag);
    }
}
