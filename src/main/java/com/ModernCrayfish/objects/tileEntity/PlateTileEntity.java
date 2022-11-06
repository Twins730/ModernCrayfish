package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.TileInit;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
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

public class PlateTileEntity extends TileEntity {

    public Direction itemDirection = Direction.NORTH;
    public NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public PlateTileEntity() {
        super(TileInit.PLATE_TILE.get());
    }

    public void use(World world, PlayerEntity player) {
        if(!world.isClientSide) {
            if (!items.get(0).isEmpty()) {
                player.addItem(items.get(0));
                this.items.set(0, ItemStack.EMPTY);
            } else if ((player.getMainHandItem().isEdible() || player.getMainHandItem().getItem() == Items.CAKE) && items.get(0).isEmpty()) {
                this.itemDirection = player.getDirection();
                this.items.set(0,new ItemStack(player.getMainHandItem().getItem(),1));
                player.getMainHandItem().shrink(1);
            }
            world.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
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
