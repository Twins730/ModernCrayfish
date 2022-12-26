package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.TileInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class CookieJarTileEntity extends TileEntity {


    public int count = 0;
    public NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);


    public CookieJarTileEntity() {
        super(TileInit.COOKIE_JAR_TILE.get());
    }


    public void remove(World world){
        if(!world.isRemote){
            items.forEach((item)->{
                world.addEntity(new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),item));
            });
        }
    }

    public void use(World world, PlayerEntity player){
        if(!world.isRemote) {
            this.count = 0;
            items.forEach((item) -> {
                if (!item.isEmpty()) {
                    count++;
                }
            });
            int itemCount = count - 1;

            if (count < 6 && player.getHeldItemMainhand().isFood()) {
                this.items.set(count, new ItemStack(player.getHeldItemMainhand().getItem(), 1));
                player.getHeldItemMainhand().shrink(1);
            } else if (count > 0) {
                player.addItemStackToInventory(items.get(itemCount));
                this.items.set(itemCount, ItemStack.EMPTY);
            }
            world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        } else {
            if(count > 0){
                player.playSound(SoundEvents.ENTITY_ITEM_PICKUP,1,1);
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        ItemStackHelper.saveAllItems(nbt, items);
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(6, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
    }

    // Synchronization

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(pos, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert world != null;
        this.read(world.getBlockState(pos),pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state,tag);
        super.handleUpdateTag(state, tag);
    }

}
