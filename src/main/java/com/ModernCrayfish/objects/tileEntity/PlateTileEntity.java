package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.TileInit;
import net.minecraft.block.BarrelBlock;
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
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class PlateTileEntity extends TileEntity {

    public Direction itemDirection = Direction.NORTH;
    public NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public PlateTileEntity() {
        super(TileInit.PLATE_TILE.get());
    }

    public void remove(World world){
        if(!world.isRemote){
            items.forEach((item)->{
                world.addEntity(new ItemEntity(world,pos.getX(),pos.getY(),pos.getZ(),item));
            });
        }
    }

    public void use(World world, PlayerEntity player) {
        if(!world.isRemote) {
            if (!items.get(0).isEmpty()) {
                player.addItemStackToInventory(items.get(0));
                this.items.set(0, ItemStack.EMPTY);
            } else if ((player.getHeldItemMainhand().isFood() || player.getHeldItemMainhand().getItem() == Items.CAKE) && items.get(0).isEmpty()) {
                this.itemDirection = player.getHorizontalFacing();
                this.items.set(0,new ItemStack(player.getHeldItemMainhand().getItem(),1));
                player.getHeldItemMainhand().shrink(1);
            }
            world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        } else {
            if(!items.get(0).isEmpty()){
                player.playSound(SoundEvents.ENTITY_ITEM_PICKUP,1,1);
            }
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
        this.itemDirection = Direction.fromAngle(nbt.getDouble("Direction"));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        ItemStackHelper.saveAllItems(nbt, items);
        nbt.putDouble("Direction",itemDirection.getHorizontalAngle());
        return nbt;
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
