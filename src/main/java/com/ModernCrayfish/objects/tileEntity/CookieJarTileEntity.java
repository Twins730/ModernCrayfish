package com.ModernCrayfish.objects.tileEntity;

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
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class CookieJarTileEntity extends TileEntity {


    public int count = 0;
    public NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);


    public CookieJarTileEntity() {
        super(TileInit.COOKIE_JAR_TILE.get());
    }


    public void remove(World world){
        if(!world.isClientSide){
            items.forEach((item)->{
                world.addFreshEntity(new ItemEntity(world,worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),item));
            });
        }
    }

    public void use(World world, PlayerEntity player){
        if(!world.isClientSide) {
            this.count = 0;
            items.forEach((item) -> {
                if (!item.isEmpty()) {
                    count++;
                }
            });
            int itemCount = count - 1;

            if (itemCount == 0) {
                itemCount = 0;
            }
            if (count < 6 && player.getMainHandItem().isEdible()) {
                this.items.set(count, new ItemStack(player.getMainHandItem().getItem(), 1));
                player.getMainHandItem().shrink(1);
            } else if (count > 0) {
                player.addItem(items.get(itemCount));
                this.items.set(itemCount, ItemStack.EMPTY);
            }
            world.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, items);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(6, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
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
