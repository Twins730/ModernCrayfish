package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.init.ItemInit;
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
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ToasterTileEntity extends TileEntity implements ITickableTileEntity {

    public Direction itemDirection = Direction.NORTH;
    public NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public boolean toasting = false;
    public double toastTime = 0;

    public ToasterTileEntity() {
        super(TileInit.TOASTER.get());
    }

    public void use(World world, PlayerEntity player, Direction direction) {
        if (!world.isRemote) {
            if (player.isSneaking() && !toasting) {
                this.toasting = true;
            } else if (!toasting) {
                if (player.getHeldItemMainhand().getItem() == ItemInit.BREAD_SLICE.get() && items.get(1).isEmpty()) {
                    if (items.get(0).isEmpty()) {
                        this.items.set(0, new ItemStack(player.getHeldItemMainhand().getItem(), 1));
                    } else {
                        this.items.set(1, new ItemStack(player.getHeldItemMainhand().getItem(), 1));
                    }
                    player.getHeldItemMainhand().shrink(1);
                    this.itemDirection = direction;
                } else {
                    if (!items.get(0).isEmpty()) {
                        player.addItemStackToInventory(items.get(0));
                        items.set(0, ItemStack.EMPTY);
                        world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                        return;
                    }
                    if (!items.get(1).isEmpty()) {
                        player.addItemStackToInventory(items.get(1));
                        items.set(1, ItemStack.EMPTY);
                    }
                }
            }
            world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    public void remove(World world) {
        if (!world.isRemote) {
            items.forEach((item) -> world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item)));
        }
    }

    @Override
    public void tick() {
        assert world != null;
        if (!world.isRemote) {
            if (toasting) {
                if (toastTime == 200) {
                    for (ItemStack item : items) {
                        if (!item.isEmpty()) {
                            item = new ItemStack(ItemInit.TOAST.get());
                            world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + 0.1, pos.getZ(), item));
                        }
                    }
                    this.items.set(0, ItemStack.EMPTY);
                    this.items.set(1, ItemStack.EMPTY);
                    world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                    this.toastTime = 0;
                    this.toasting = false;
                } else {
                    this.toastTime++;
                }
            }
        }
    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.toasting = nbt.getBoolean("toasting");
        this.toastTime = nbt.getDouble("toastTime");
        this.itemDirection = Direction.fromAngle(nbt.getDouble("Direction"));
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        nbt.putBoolean("toasting", toasting);
        nbt.putDouble("toastTime", toastTime);
        nbt.putDouble("Direction",itemDirection.getHorizontalAngle());
        ItemStackHelper.saveAllItems(nbt, items);
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
        this.read(world.getBlockState(pos), pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
        super.handleUpdateTag(state, tag);
    }
}
