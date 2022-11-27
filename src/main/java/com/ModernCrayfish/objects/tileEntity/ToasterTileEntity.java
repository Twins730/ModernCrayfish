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
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ToasterTileEntity extends TileEntity implements ITickableTileEntity {

    public NonNullList<ItemStack> bread = NonNullList.withSize(2, ItemStack.EMPTY);

    public ToasterTileEntity() {
        super(TileInit.TOASTER.get());
    }

    public void use(World world, PlayerEntity player){

    }

    public void remove(World world){
        if(!world.isClientSide){
            bread.forEach((item)->{
                world.addFreshEntity(new ItemEntity(world,worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),item));
            });
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.bread = NonNullList.withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, bread);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, bread);
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
